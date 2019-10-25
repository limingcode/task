package com.skyedu.controller.app;

import com.google.gson.Gson;
import com.image.tag.dao.ImageTagDao;
import com.image.tag.dao.impl.ImageTagBaseDao;
import com.skyedu.dao.impl.CourseDAO;
import com.skyedu.dao.impl.UserDAO;
import com.skyedu.model.StudentLoginLog;
import com.skyedu.model.app.AppUserModel;
import com.skyedu.model.app.BookInfoModel;
import com.skyedu.model.common.PageModel;
import com.skyedu.model.user.ShareBookModel;
import com.skyedu.service.CourseService;
import com.skyedu.service.UserService;
import com.util.DataUtils;

import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;

@Controller
@RequestMapping("/appUserInfo")
public class AppUserInfoController {
    @Resource
    private ImageTagDao imageTagDao;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private CourseDAO courseDao;
    @Resource
    private ImageTagBaseDao imageTagBaseDao;

//    @RequestMapping(value = "hello", method = RequestMethod.POST)
//
//    public Map<?, ?> hello(@RequestParam("zipFile") MultipartFile zipFile) {
//
//        return null;
//    }
//    public static void main(String[] args) {
//        try {
//
//            readfile("C:/Users/skyedu_beyond/Desktop/liantianjilu");
//            // deletefile("D:/file");
//        } catch (FileNotFoundException ex) {
//        } catch (IOException ex) {
//        }
//        System.out.println("ok");
//    }
//    public static boolean readfile(String filepath) throws FileNotFoundException, IOException {
//        try {
//
//            File file = new File(filepath);
//            if (!file.isDirectory()) {
//                System.out.println("文件");
//                System.out.println("path=" + file.getPath());
//                System.out.println("absolutepath=" + file.getAbsolutePath());
//                System.out.println("name=" + file.getName());
//
//            } else if (file.isDirectory()) {
//                System.out.println("文件夹");
//                String[] filelist = file.list();
//                for (int i = 0; i < filelist.length; i++) {
//                    File readfile = new File(filepath + "\\" + filelist[i]);
//                    if (!readfile.isDirectory()) {
//                        System.out.println("path=" + readfile.getPath());
//                        System.out.println("absolutepath="
//                                + readfile.getAbsolutePath());
//                        System.out.println("name=" + readfile.getName());
//
//                    } else if (readfile.isDirectory()) {
//                        readfile(filepath + "\\" + filelist[i]);
//                    }
//                }
//
//            }
//
//        } catch (FileNotFoundException e) {
//            System.out.println("readfile()   Exception:" + e.getMessage());
//        }
//        return true;
//    }
    /**
     * 手机登入
     ** @param username
     * @param password
     * @param request
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public Map<?, ?> login(String username, String password, HttpServletRequest request) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return DataUtils.getInstance(101, "请填写账号或密码");
        }
        Map<String, Object> student = userService
                .getStudentInfo(username.trim(), password);
        if (StringUtils.isEmpty(student)) {
            student = userDAO.getWjStudent(username, password);
            if(StringUtils.isEmpty(student)){
                return DataUtils.getInstance(101, "账号或密码错误");
            }

        }

        String  josn=new Gson().toJson(student);
        AppUserModel appUserModel = new Gson().fromJson(josn, AppUserModel.class);
        String header = request.getHeader("user-agent");
        StudentLoginLog studentLoginLog = new StudentLoginLog(appUserModel.getId(), new Date(), header);
        userService.saveStudentLoginLog(studentLoginLog);
        int cityId=StringUtils.isEmpty(student.get("cityId")) ? 1 : (Integer) student.get("cityId");
        appUserModel.setBookSeriesList(BookInfoModel.getBookInfoList(cityId));
        return DataUtils.getInstance(100, appUserModel);
    }
    @RequestMapping(value = "getStudentInfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<?, ?> getStudentInfo (int userStudentId,HttpServletRequest request){
        final String tag1 = request.getParameter("tag");
        final int tag = StringUtils.isEmpty(tag1)?0:Integer.valueOf(tag1);
        Map<String, Object> student=new HashMap<>();
        if (tag==0){
             student = userService.getStudent(userStudentId);
        }else{
            student = userDAO.getWjStudent(userStudentId);

        }
        if (StringUtils.isEmpty(student)) {
            return DataUtils.getInstance(101, "没有找到相关的学生信息!");
        }

        AppUserModel appUserModel = new Gson().fromJson(new Gson().toJson(student), AppUserModel.class);
        if (tag==0){
            appUserModel.setCourseList(courseService.getCourseInfoList(userStudentId));
        }else{
            appUserModel.setCourseList(courseDao.getWjCourseInfoList(userStudentId));
        }

        return DataUtils.getInstance(100, appUserModel);
    }

    /**
     * 手机新版登入
     * @param userStudentId
     * @param request
     * @return
     */
    @RequestMapping(value = "getCateCode", method = RequestMethod.GET)
    @ResponseBody
    public Map<?, ?> getCateCode (int userStudentId,HttpServletRequest request){
        final String tag1 = request.getParameter("tag");
        final int tag = StringUtils.isEmpty(tag1)?0:Integer.valueOf(tag1);
        Map<String, Object> student=new HashMap<>();
        if (tag==0){
            student = userService.getStudent(userStudentId);
        }else{
            student = userDAO.getWjStudent(userStudentId);

        }
        if (StringUtils.isEmpty(student)) {
            return DataUtils.getInstance(101, "没有找到相关的学生信息!");
        }
        AppUserModel appUserModel = new Gson().fromJson(new Gson().toJson(student), AppUserModel.class);
        int cityId=StringUtils.isEmpty(student.get("cityId")) ? 1 : (Integer) student.get("cityId");
        appUserModel.setBookSeriesList(BookInfoModel.getBookInfoList(cityId));
        return DataUtils.getInstance(100, appUserModel);
    }
    @RequestMapping(value = "getCateCodeByVisitor", method = RequestMethod.GET)
    @ResponseBody
    public Map<?, ?> getCateCodeByVisitor (){
        return DataUtils.getInstance(100, BookInfoModel.getBookInfoList());
    }

    /**
     * 我的作品
     * @param userStudentId
     * @param pageModel
     * @return
     */
    @RequestMapping(value = "getMyWorks", method = RequestMethod.GET)
    @ResponseBody
    public Map<?, ?> getMyWorks (int userStudentId, PageModel pageModel){
        return DataUtils.getInstance(100, imageTagDao.getMyWorks(userStudentId,pageModel));
    }

    /**
     * 分享书本
     * @param userStudentId
     * @param shareBookModel
     * @return
     */
    @RequestMapping(value = "shareBook", method = RequestMethod.POST)
    @ResponseBody
    public Map<?, ?> shareBook (final int userStudentId,ShareBookModel shareBookModel) {
        final int bookId = shareBookModel.getBookId();
        final Map<String, Object> shareBookInfo = imageTagDao.getShareBookInfo(bookId, userStudentId);
        if (!StringUtils.isEmpty(shareBookInfo)) {
            shareBookModel.setLastUpdateTime(new Date());
            shareBookModel.setCreateTime(new Date(shareBookInfo.get("createTime").toString()));
            shareBookModel.setShareBookId(Integer.valueOf(shareBookInfo.get("shareBookId").toString()));
        }
        shareBookModel.setStudentId(userStudentId);
        shareBookModel.setValid(1);
        imageTagDao.shareBook(shareBookModel);
//        final List<Map<String, Object>> shareAudioInfoList = imageTagDao.getShareAudioInfoList(userStudentId, shareBookModel.getBookId(), null, 1);

        imageTagBaseDao.updateUseSql("update Tk_Book_AudioRecord_t set isShare =1 ,lastUpdateTime =getdate() where isShare=0 and bookId="+bookId+" and studentid="+userStudentId+" and tag=0");
        imageTagBaseDao.updateUseSql("update Tk_Book_AudioRecord_t\n" +
                                            "set valid          =0,\n" +
                                            "    lastUpdateTime =getdate()\n" +
                                            "where studentId = "+userStudentId+"\n" +
                                            "  and bookId = "+bookId+"\n" +
                                            "  and valid = 1\n" +
                                            "  and isShare = 1\n" +
                                            "  and bookAudioRecordId in (\n" +
                                            "  (select t1.bookAudioRecordId\n" +
                                            "   from (select *\n" +
                                            "         from Tk_Book_AudioRecord_t\n" +
                                            "         where tag = 1\n" +
                                            "           and isShare = 0\n" +
                                            "           and valid = 1\n" +
                                            "           and bookId = "+bookId+"\n" +
                                            "           and studentId = "+userStudentId+") t\n" +
                                            "          left join Tk_Book_AudioRecord_t t1\n" +
                                            "                    on t.bookId = t1.bookId and t.studentId = t1.studentId and t1.valid=1 and t1.isShare=1))");
        imageTagBaseDao.updateUseSql("update Tk_Book_AudioRecord_t set isShare =1 ,lastUpdateTime =getdate() where isShare=0 and bookId="+bookId+" and studentid="+userStudentId+" and tag=1");
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("title", "");
        objectObjectHashMap.put("subtitle", "");
        objectObjectHashMap.put("shareBookUrl", "");
        objectObjectHashMap.put("pop", "");
        return DataUtils.getInstance(100, objectObjectHashMap);
    }
    @RequestMapping(value = "getBookList", method = RequestMethod.GET)
    @ResponseBody
    public Map<?, ?> getBookList(final int userStudentId, final int cityId, final Integer cateCode,HttpServletRequest request){
        try {
            final String tag1 = request.getParameter("tag");
            final int tag = StringUtils.isEmpty(tag1)?0:Integer.valueOf(tag1);
            return DataUtils.getInstance(100, new HashMap<String, Object>(){{
                put("bookSeriesList",imageTagDao.getNewBookList(userStudentId,cityId,cateCode,tag));
            }});
        }catch (Exception e){
            e.printStackTrace();
            return DataUtils.getInstance(110,"系统异常!稍后再试");
        }
    }

    /**
     * 兼容IOS发布应用(游客模式)
     * @param cityId
     * @param cateCode
     * @return
     */
    @RequestMapping(value = "getBookListByVisitor", method = RequestMethod.GET)
    @ResponseBody
    public Map<?, ?> getBookListByVisitor( int cityId,  int cateCode){
         int userStudentId=0;
        final List<Map<String, Object>> bookListByVisitor = imageTagDao.getBookListByVisitor(userStudentId, cityId, cateCode);
       bookListByVisitor.get(0).put("isBelongtoClass",1);
        final ArrayList<Map<String, Object>> maps = new ArrayList<>();
        maps.add(bookListByVisitor.get(0));
        return DataUtils.getInstance(100, new HashMap<String, Object>(){{
            put("bookSeriesList",bookListByVisitor);
        }});
    }


    @RequestMapping(value = "getAppStatus", method = RequestMethod.GET)
    @ResponseBody
    public Map<?, ?> getAppStatus(){
        return DataUtils.getInstance(100, 0);
    }
    /**
     *
     * @param type 0 Ios  1 安卓
     * @return
     */
    @RequestMapping(value = "checkVersion", method = RequestMethod.GET)
    @ResponseBody
    public Map<?, ?> checkVersion(Integer type){
        type=StringUtils.isEmpty(type)?0:type;
        return DataUtils.getInstance(100, imageTagDao.checkVersion(type));
    }
//    public static List<BookInfoModel> getGroupByAfterInfo(List<Map<String, Object>> maps,int cityId) {
//        List<BookInfoModel> bookInfoList = BookInfoModel.getBookInfoList(cityId);
//        for (BookInfoModel b:bookInfoList){
//            List<Map<String, Object>> maps1 = new ArrayList<>();
//            for (Map<String, Object> map: maps){
//                if (b.getCateCode().toString().equals(map.get("cateCode"))){
//                    maps1.add(map);
//                }
//            }
//            b.setMaps(maps1);
//        }
//        return bookInfoList;
//    }
}
