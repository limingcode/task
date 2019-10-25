package com.image.tag.controller;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.image.tag.dao.ImageTagDao;
import com.image.tag.dao.impl.ImageTagBaseDao;
import com.skyedu.model.book.*;
import com.teach.FileUploadMessage;
import com.teach.model.TpFileUpload;
import com.teach.util.FileUploadUtil;
import com.teach.util.SendFileUtil;
import com.util.DataUtils;
import com.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.types.selectors.ExtendSelector;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.image.tag.service.ImageTagService;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/appImageTag")
public class AppImageTagController {

	@Resource
	private ImageTagService imageTagService;
    @Resource
    private ImageTagBaseDao imageTagBaseDao;
    @Resource
    private ImageTagDao imageTagDao;

    /**
     * 数据修复接口,(慎用)
     * @param bookId
     */
    //@ResponseBody
    //@RequestMapping(value="/getBookJson", method=RequestMethod.GET)
    public void getBookJson(int bookId) {
        imageTagService.getBookJson(bookId);

    }

    @ResponseBody
    @RequestMapping(value="/getShareAudioInfoList", method=RequestMethod.GET)
    public  Map<?,?>  getShareAudioInfo(@RequestParam("userStudentId") Integer userStudentId,
                                        @RequestParam("bookId")Integer bookId,
                                        @RequestParam(value = "isShare",required = false,defaultValue = "1") Integer isShare,
                                        @RequestParam(value = "tag",required = false)Integer tag) {
        //return DataUtils.getInstance(100, imageTagDao.getShareAudioInfo(userStudentId,graphicalId,1));
        return DataUtils.getInstance(100, imageTagDao.getShareAudioInfoList(userStudentId,bookId,isShare,tag));
    }



    /**
     * 上传音频(手机sees)
     * @param uploadFile
     * @param request
     * @param graphicalId
     * @param userStudentId
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/fileUpload", method=RequestMethod.POST)
    public Map<?,?> fileUpload( MultipartFile uploadFile, HttpServletRequest request, int graphicalId,int userStudentId) {
        if (uploadFile.isEmpty()) {
            return DataUtils.getInstance(101, "文件为空");
        }
        Map<String, Object> mapUseSql = imageTagBaseDao.getMapUseSql("select * from IM_Graphical where graphicalId=" + graphicalId);
        if(org.springframework.util.StringUtils.isEmpty(mapUseSql)){
            return DataUtils.getInstance(101, "未找到该节点,无法为其上传音频");
        }
        final int bookId = Integer.valueOf(mapUseSql.get("bookId").toString());
        String basepath="upload/bookInfo/"+userStudentId+"/"+mapUseSql.get("bookId")+"/"+graphicalId+"/";
        String realPath = request.getSession().getServletContext().getRealPath(basepath);
        System.out.println("获取到的路径:"+realPath);
        File dir = new File(realPath);
        if (!dir.isDirectory()) {
            dir.mkdirs();
        }
        String filename = uploadFile.getOriginalFilename();
        // 判断文件类型
        String type = filename.contains(".") ? filename.substring(filename.lastIndexOf(".") + 1, filename.length()) : null;
        if (type != null&&( "MP3".equals(type.toUpperCase())||"WAV".equals(type.toUpperCase()))) {
            try {
                String filePath =basepath;
                String newFileName = graphicalId+"_"+userStudentId+"_"+System.currentTimeMillis()+ "." + type;
                filePath += newFileName;
                //服务端保存的文件对象
                File fileServer = new File(dir, newFileName);
                System.out.println("file文件真实路径:" + fileServer.getAbsolutePath());
                //2，实现上传
                uploadFile.transferTo(fileServer);
                final Map<String, Object> shareAudioInfo = imageTagDao.getShareAudioInfo(userStudentId, graphicalId,0);
                final Map<String, Object> isShare = imageTagDao.getShareAudioInfo(userStudentId, graphicalId,1);
                BookAudioRecordModel bookAudioRecordModel = new BookAudioRecordModel();
                if(!org.springframework.util.StringUtils.isEmpty(isShare)){
                    bookAudioRecordModel.setTag(1);
                }
                if(org.springframework.util.StringUtils.isEmpty(shareAudioInfo)) {
                    bookAudioRecordModel.setAudioUrl(filePath);
                    bookAudioRecordModel.setBookId(bookId);
                    bookAudioRecordModel.setGraphicalId(graphicalId);
                    bookAudioRecordModel.setStudentId(userStudentId);
                    bookAudioRecordModel.setValid(1);
                    bookAudioRecordModel.setIsShare(0);
                    imageTagDao.saveBookAudioRecordModel(bookAudioRecordModel);
                }else {
                    bookAudioRecordModel= imageTagDao.getShareAudioInfo(Integer.valueOf(shareAudioInfo.get("bookAudioRecordId").toString()));
                    bookAudioRecordModel.setAudioUrl(filePath);
                    bookAudioRecordModel.setLastUpdateTime(new Date());
                    imageTagBaseDao.saveOrUpdate(bookAudioRecordModel);
                }

                //是否已经存在音频，有，修改，无，增加
                //3，返回可供访问的网络路径
                return DataUtils.getInstance(100, filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return DataUtils.getInstance(101, "上传失败");
        } else {
            return DataUtils.getInstance(101, "只能上传MP3,WAV 格式的音频");
        }
    }
//    @ResponseBody
//    @RequestMapping(value="/saveBookJson", method=RequestMethod.GET)
    public void saveBookJson() {
        BufferedReader bufferedReader=null;
        try {
            System.out.println("同步数据开始 start---------------------");
            List<Map<String, Object>> listUseSql = imageTagBaseDao.getListUseSql("select * from IM_Book");
            for (Map<String, Object> map:listUseSql) {
                StringBuilder stringBuilder = new StringBuilder();
                String ss="F:/76/"+map.get("id")+"_"+map.get("bookName")+".json";
                //String ss="F:/76/5_LWTE-5A.json";
                File file = new File(ss);
                System.out.println("file"+ss);
                if(!file.exists()){
                    System.out.println("不存在"+ss);
                    continue;
                }
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
                 bufferedReader = new BufferedReader(isr);
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                System.out.println(stringBuilder.toString());
                JsonsRootBean jsonsRootBean=null;
                try {
                     jsonsRootBean = new Gson().fromJson(stringBuilder.toString(), JsonsRootBean.class);
                }catch (JsonSyntaxException e){

                    e.printStackTrace();
                    System.out.println("解析异常"+ss);
                    continue;
                }

                int bookId = jsonsRootBean.getBookId();
                imageTagBaseDao.updateUseSql("update IM_Book set height="+jsonsRootBean.getHeight()+",width="+jsonsRootBean.getWidth()+",type="+jsonsRootBean.getType()+" where  id=" + bookId);
                for (JsonsRootBean.Lessonlist lesson : jsonsRootBean.getLessonList()) {
                    for (JsonsRootBean.Pagelist page : lesson.getPageList()) {
                        ImPageModel imPageModel = (ImPageModel) imageTagBaseDao.saveOrUpdate(new ImPageModel(bookId, lesson.getLessonId(), page.getPageNumber(), page.getImageName()));
                        for (JsonsRootBean.Rectlist rect : page.getRectList()) {
                            ImGraphicalModel imGraphicalModel = (ImGraphicalModel) imageTagBaseDao.saveOrUpdate(new ImGraphicalModel(imPageModel.getPageId(), rect.getType(), rect.getSortNo(), rect.getAudioName(), rect.getDescribe(), rect.getDuration(), bookId, lesson.getLessonId()));
                            if(rect.getType()==2) {
                                for (JsonsRootBean.Points points : rect.getShape().getPoints()) {
                                    imageTagBaseDao.saveOrUpdate(new ImPointsModel(points.getX(), points.getY(), imPageModel.getPageId(), bookId, lesson.getLessonId(), imGraphicalModel.getGraphicalId()));
                                }
                            }else {
                                JsonsRootBean.Shape shape = rect.getShape();
                                JsonsRootBean.Points points = shape.getPoint();
                                imageTagBaseDao.saveOrUpdate(new ImPointsModel(shape.getWidthRadius(),shape.getHeightRadius(),points.getX(), points.getY(), imPageModel.getPageId(), bookId, lesson.getLessonId(), imGraphicalModel.getGraphicalId()));
                            }
                        }
                    }
                }
           }
            System.out.println("同步结束开始 end---------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                assert bufferedReader != null;
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

	@ResponseBody
	@RequestMapping(value="/getStudent", method=RequestMethod.GET, produces="text/html;charset=UTF-8")
	public String getStudent(int userStudentId) {
		Map<String, Object> student = imageTagService.getStudentClass(userStudentId);
		return JSONObject.toJSONString(student);
	}

    /**
     * 获取我的书籍ipad 显示
     * @param userStudentId
     * @param cityId
     * @return
     */
	@ResponseBody
	@RequestMapping(value="/getBook", method=RequestMethod.GET, produces="text/html;charset=UTF-8")
	public String getBook(int userStudentId, String cityId) {
		Map<String, Object> student = imageTagService.getStudentClass(userStudentId, StringUtils.isEmpty(cityId) ? 0 : Integer.valueOf(cityId)) ;
		return JSONObject.toJSONString(student);
	}

    /**
     * 获取所有书籍
     * @return
     */
	@ResponseBody
    @RequestMapping(value = "/getAllBook",method = RequestMethod.GET)
    public Map<?,?>getAllBook(){
        Map<String, Object> stringObjectHashMap = new HashMap<>();
        try {
            stringObjectHashMap.put("data",imageTagService.getAllBook());
            stringObjectHashMap.put("code",100);
        }catch (Exception e){
            stringObjectHashMap.put("data","系统异常,请稍后重试!");
            stringObjectHashMap.put("code",110);
        }
        return stringObjectHashMap;
    }
	
}
