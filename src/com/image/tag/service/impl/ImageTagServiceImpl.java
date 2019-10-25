package com.image.tag.service.impl;

import java.io.*;
import java.util.*;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.image.tag.dao.impl.ImageTagBaseDao;
import com.image.tag.dao.impl.ImageTagDaoImpl;
import com.image.tag.utils.*;
import com.skyedu.model.book.*;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.util.DataUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.image.tag.Message;
import com.image.tag.TagClass;
import com.image.tag.dao.ImageTagDao;
import com.image.tag.model.ImBook;
import com.image.tag.service.ImageTagService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sun.rmi.runtime.Log;

@Service
public class ImageTagServiceImpl implements ImageTagService {

    @Resource
    private ImageTagDao imageTagDao;
    @Resource
    private ImageTagBaseDao imageTagBaseDao;

    @Override
    public ImBook getBook(int id) {
        return imageTagDao.getBook(id);
    }

    @Override
    public SendBookJsonRecordModel getSendBookJsonRecordModel(int id) {
        return imageTagDao.getSendBookJsonRecordModel(id);
    }

    @Override
    public void getBookJson(int id) {
        List<Map<String, Object>> listUseSql = imageTagBaseDao.getListUseSql("select * from IM_Book");
        for (Map<String, Object> map1 : listUseSql) {
            getJsonFlie(map1);
        }
    }

    @Override
    public String getJsonFlie(Map<String, Object> map1) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("bookId", map1.get("id"));
        stringObjectHashMap.put("type", map1.get("type"));
        stringObjectHashMap.put("width", map1.get("width"));
        stringObjectHashMap.put("height", map1.get("height"));
        ArrayList<Map<String, Object>> maps = new ArrayList<>();
        for (Map<String, Object> map : imageTagBaseDao.getListUseSql("select * from IM_Lesson where fId =" + map1.get("id"))) {
            HashMap<String, Object> stringObjectHashMap1 = new HashMap<>();
            int lessonId = (int) map.get("id");
            stringObjectHashMap1.put("lessonId", map.get("id"));
            stringObjectHashMap1.put("lessonTitle", org.springframework.util.StringUtils.isEmpty(map.get("name")) ? "" : map.get("name").toString());
            stringObjectHashMap1.put("lessonDesc", org.springframework.util.StringUtils.isEmpty(map.get("description")) ? "" : map.get("description").toString());
            stringObjectHashMap1.put("lessonPop", org.springframework.util.StringUtils.isEmpty(map.get("lessonPop")) ? "" : map.get("lessonPop").toString());
            @SuppressWarnings("unchecked")
            List<ImPageModel> list = (List<ImPageModel>) imageTagBaseDao.getList(" from ImPageModel where lessonId=" + lessonId + " and valid=1 order by pageId");
            for (ImPageModel imPage : list) {
                @SuppressWarnings("unchecked")
                List<ImGraphicalModel> imGraphicalModelList = (List<ImGraphicalModel>) imageTagBaseDao.getList("from ImGraphicalModel where pageId=" + imPage.getPageId() + " and valid=1 order by graphicalId");
                for (ImGraphicalModel imGraphicalModel : imGraphicalModelList) {
                    if (imGraphicalModel.getType() == 2) {
                        @SuppressWarnings("unchecked") final List<ImPointsModel> imPointsModels = (List<ImPointsModel>) imageTagBaseDao.getList(" from ImPointsModel where graphicalId=" + imGraphicalModel.getGraphicalId() + " and valid=1  order by  pointsId");
                        imGraphicalModel.setShape(new Shape(imPointsModels));
                    } else {
                        class Shape {
                            private ImPointsModel point;
                            private double widthRadius;
                            private double heightRadius;

                            private ImPointsModel getPoint() {
                                return point;
                            }

                            private void setPoint(ImPointsModel point) {
                                this.point = point;
                            }

                            private double getWidthRadius() {
                                return widthRadius;
                            }

                            private void setWidthRadius(double widthRadius) {
                                this.widthRadius = widthRadius;
                            }

                            private double getHeightRadius() {
                                return heightRadius;
                            }

                            private void setHeightRadius(double heightRadius) {
                                this.heightRadius = heightRadius;
                            }
                        }
                        @SuppressWarnings("unchecked") final ImPointsModel imPointsModel = (ImPointsModel) imageTagBaseDao.getObject(" from ImPointsModel where graphicalId=" + imGraphicalModel.getGraphicalId() + " and valid=1 order by pointsId");
                        Shape shape = new Shape();
                        shape.setPoint(imPointsModel);
                        shape.setHeightRadius(imPointsModel.getHeightRadius());
                        shape.setWidthRadius(imPointsModel.getWidthRadius());
                        imGraphicalModel.setShape(shape);
                    }
                }
                imPage.setRectList(imGraphicalModelList);
            }
            stringObjectHashMap1.put("pageList", list);
            maps.add(stringObjectHashMap1);

        }
        stringObjectHashMap.put("lessonList", maps);
        try {
            String content = new Gson().toJson(stringObjectHashMap);
            String s = ImageTagUtils.getBookJson(Integer.valueOf(map1.get("id").toString()), map1.get("bookName").toString());
            File file = new File(s);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
            System.out.println("Done");
            return s;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<String, Object> getLesson(int lessonId) {
        return imageTagDao.getLesson(lessonId);
    }

    @Override
    public List<Map<String, Object>> getLessonList(String bookId) {
        return imageTagDao.getLessonList(bookId);
    }

    /**
     * 判断是否存在lessonId.json文件
     */
    @Override
    public boolean isUpdataImageTag(String bookId, String period, String bookName) {
        String jsonFile = ImageTagUtils.getHomePath() + ImageTagUtils.getJsonFile(Integer.parseInt(bookId), period, bookName);
        File file = new File(jsonFile);
        if (file.exists())
            return true;
        return false;
    }

    /**
     * 得到image列表
     */
    @Override
    public List<String> getImageList(String json, int bookId, String period, String bookName, boolean updataImageTag) {
        String filePath = ImageTagUtils.getImageTagFileUploadPath(bookId, period, bookName, 1, updataImageTag);
        List<String> imageFile = FileUtil.getDirAllFile(ImageTagUtils.getHomePath() + filePath);
        if (StringUtils.isEmpty(json)) {
            return imageFile;
        }
        List<String> list = new ArrayList<String>();
        JSONObject jsonObject = JSONObject.fromObject(json);
        JSONArray lessonList = JSONArray.fromObject(jsonObject.get("lessonList"));
        for (Object lesson : lessonList) {
            JSONObject lessonJson = JSONObject.fromObject(lesson);
            JSONArray pageList = JSONArray.fromObject(lessonJson.get("pageList"));
            for (Object page : pageList) {
                JSONObject pageJson = JSONObject.fromObject(page);
                String imageName = (String) pageJson.get("imageName");
                list.add(imageName);
            }
        }
        for (String string : imageFile) {
            if (!list.contains(string)) {
                list.add(string);
            }
        }
        return list;
    }

    /**
     * 保存图片标注信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveImageTag(int bookId, String period, String bookName, String jsonString) throws Exception {

        String formalImgFilePath = ImageTagUtils.getHomePath() + ImageTagUtils.getImageTagFileUploadPath(bookId, period, bookName, 1, true);
        String tempImgFilePath = ImageTagUtils.getHomePath() + ImageTagUtils.getImageTagFileUploadPath(bookId, period, bookName, 1, false);

        String tempAudioFilePath = ImageTagUtils.getHomePath() + ImageTagUtils.getImageTagFileUploadPath(bookId, period, bookName, 2, false);
        String formalAudioFilePath = ImageTagUtils.getHomePath() + ImageTagUtils.getImageTagFileUploadPath(bookId, period, bookName, 2, true);

        //将临时文件夹的文件移动到正式文件夹
        FileUtil.moveDirAllFile(tempImgFilePath, formalImgFilePath);
        FileUtil.moveDirAllFile(tempAudioFilePath, formalAudioFilePath);

        //删除临时文件夹
        FileUtil.deleteDir(ImageTagUtils.getHomePath() + ImageTagUtils.getBookPath(bookId, period, bookName, false));

        //删除已删除的图片
        List<String> imgList = FileUtil.getDirAllFile(formalImgFilePath);
        //删除多余的图片;
        List<String> needDelImage = new ArrayList<String>();
        for (String string : imgList) {
            if (!jsonString.contains(string)) {
                needDelImage.add(formalImgFilePath + File.separator + string);
            }
        }
        if (needDelImage.size() > 0) {
            //删除磁盘文件
            FileUtil.deleteFile(needDelImage.toArray(new String[needDelImage.size()]));
        }

        //删除已删除的音频；
        List<String> audioList = FileUtil.getDirAllFile(formalAudioFilePath);
        //删除多余的音频;
        List<String> needDelAudio = new ArrayList<String>();
        for (String string : audioList) {
            if (!jsonString.contains(string)) {
                needDelAudio.add(formalAudioFilePath + File.separator + string);
            }
        }
        if (needDelAudio.size() > 0) {
            //删除磁盘文件
            FileUtil.deleteFile(needDelAudio.toArray(new String[needDelAudio.size()]));
        }

        String basePath = ImageTagUtils.getHomePath() + ImageTagUtils.getBookPath(bookId, period, bookName, true);
        ImageTagUtils.createJsonFile(jsonString, basePath, bookId + "_" + bookName);
        boolean b = saveBookInfo(jsonString);
        System.out.println("保存结果----------" + b);
        //发送文件
        Thread findThread = ImageTagSendFileUtil.findThread(String.valueOf(bookId), bookName);
        if (findThread != null) {
            imageTagDao.updateBookStatus(bookId, ImageTagSendFileUtil.FILE_SEND_STATUS_CANCELSEND);
            findThread.interrupt();
        }
        String josnThread= bookId + "_" + bookName + "_json";
        Thread findThread1 = ImageTagSendFileUtil.findThread(josnThread);
        if (findThread1 != null) {
            imageTagDao.updateBookStatus(bookId, ImageTagSendFileUtil.FILE_SEND_STATUS_CANCELSEND);
            findThread1.interrupt();
        }
        Map<String, Object> map = imageTagBaseDao.getMapUseSql("select * from IM_Book where id=" + bookId);
        String jsonFlieUrl = getJsonFlie(map);
        System.err.println("Json储存地址-------------" + jsonFlieUrl);
        if (!StringUtils.isEmpty(jsonFlieUrl)) {
            //发送json 文件
            ImageSendJsonFileThread imageSendJsonFileThread = new ImageSendJsonFileThread(bookId, period, jsonFlieUrl);
            imageSendJsonFileThread.setName(josnThread);
            imageSendJsonFileThread.start();
        }
        String zipFile = ImageTagUtils.getZipFile(String.valueOf(bookId), period, bookName);
        String bookPath = ImageTagUtils.getHomePath() + ImageTagUtils.getBookPath(bookId, period, bookName, true);
        ImageSendFileThread thread = new ImageSendFileThread(bookId, period, zipFile, bookPath);
        thread.setName(bookId + "_" + bookName);
        thread.start();
    }

    /**
     * 保存书籍信息
     *
     * @param Json
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    boolean saveBookInfo(String Json) throws Exception {
        JsonsRootBean jsonsRootBean = new Gson().fromJson(Json, JsonsRootBean.class);
        int bookId = jsonsRootBean.getBookId();
        imageTagBaseDao.updateUseSql("update IM_Book set height=" + jsonsRootBean.getHeight() + ",width=" + jsonsRootBean.getWidth() + ",type=" + jsonsRootBean.getType() + " where  id=" + bookId);
        imageTagBaseDao.updateUseSql("update IM_Graphical set valid=0 where bookId =" + bookId + "and valid=1");
        imageTagBaseDao.updateUseSql("update IM_Page set valid=0 where bookId =" + bookId + "and valid=1");
        imageTagBaseDao.updateUseSql("update IM_Points set valid=0 where bookId =" + bookId + "and valid=1");
        for (JsonsRootBean.Lessonlist lesson : jsonsRootBean.getLessonList()) {
            for (JsonsRootBean.Pagelist page : lesson.getPageList()) {
                ImPageModel imPageModel = (ImPageModel) imageTagBaseDao.saveOrUpdate(new ImPageModel(bookId, lesson.getLessonId(), page.getPageNumber(), page.getImageName()));
                for (JsonsRootBean.Rectlist rect : page.getRectList()) {
                    ImGraphicalModel imGraphicalModel = (ImGraphicalModel) imageTagBaseDao.saveOrUpdate(new ImGraphicalModel(imPageModel.getPageId(), rect.getType(), rect.getSortNo(), rect.getAudioName(), rect.getDescribe(), rect.getDuration(), bookId, lesson.getLessonId()));
                    if (imGraphicalModel.getType() == 2) {
                        for (JsonsRootBean.Points points : rect.getShape().getPoints()) {
                            imageTagBaseDao.saveOrUpdate(new ImPointsModel(points.getX(), points.getY(), imPageModel.getPageId(), bookId, lesson.getLessonId(), imGraphicalModel.getGraphicalId()));
                        }
                    } else {
                        JsonsRootBean.Points point = rect.getShape().getPoint();
                        imageTagBaseDao.saveOrUpdate(new ImPointsModel(rect.getShape().getWidthRadius(), rect.getShape().getHeightRadius(), point.getX(), point.getY(), imPageModel.getPageId(), bookId, lesson.getLessonId(), imGraphicalModel.getGraphicalId()));
                    }
                }
            }
        }
        return true;
    }

    /**
     * 发送文件
     *
     * @param bookId
     * @param filePath
     */
    @Override
    public void sendBook(String bookId, String period, String bookName, String filePath) {
        ImBook book = imageTagDao.getBook(Integer.parseInt(bookId));
        int count = book.getSendNum() + 1;
        book.setSendNum(count);
        book.setStatus(ImageTagSendFileUtil.FILE_SEND_STATUS_SENDING);
        book.setUpdateTime(new Date());
        int id = imageTagDao.saveBook(book);
        int code = ImageTagSendFileUtil.httpPost(Integer.parseInt(bookId), period, FileUtil.getBytes(filePath), ImageTagSendFileUtil.FILE_SEND_ADDR);
        if (code != 200) {
            imageTagDao.updateBookStatus(id, ImageTagSendFileUtil.FILE_SEND_STATUS_UNSEND);
        }
    }


    @Override
    public Map<String, Object> getStudentClass(int studentId) {
        Map<String, Object> student = imageTagDao.getStudent(studentId);
        List<Map<String, Object>> bookList = imageTagDao.getBookList(studentId);
        student.put("bookList", bookList);
        return student;
    }

    /**
     * 学生登录时，得到学生的层次、课次列表
     */
    @Override
    public Map<String, Object> getStudentClass(int studentId, int cityId) {
        Map<String, Object> student = imageTagDao.getStudent(studentId);
        List<Map<String, Object>> bookList = imageTagDao.getNewBookList(studentId, cityId, null,0);
        student.put("bookList", bookList);
        return student;
    }

    @Override
    public void updateBookStatus(String bookId, String period, String bookName, byte status) {
        imageTagDao.updateBookStatus(Integer.parseInt(bookId), status);
    }

    @Override
    public List<Map<String, Object>> getSendFailBook() {
        return imageTagDao.getSendFailBook();
    }

    @Override
    public List<Map<String, Object>> getAllBook() {
        return imageTagDao.getAllBookList();
    }

    @Override
    public List<Map<String, Object>> getCateAndStatus(String subject, String grade, String period, String level) {
        return imageTagDao.getCateAndStatus(subject, grade, period, level);
    }

    @Override
    public List<Map<String, Object>> getBookTypeList() {
        return imageTagDao.getBookTypeList();
    }

    @Override
    public Message saveLesson(int bookId, String bookName, int lessonId, String lessonName) {
        // TODO Auto-generated method stub
        String period = "76";
        String jsonFile = ImageTagUtils.getHomePath() + ImageTagUtils.getJsonFile(bookId, period, bookName);
        File file = new File(jsonFile);
        String oldJson = null;
        if (file.exists()) {
            oldJson = FileUtil.readFileByLines(jsonFile);
        }
        String json = TagClass.analysisLessonHtml(oldJson, bookId, bookName, lessonId, lessonName);
        if (StringUtils.isEmpty(json)) {
            return new Message(false, "Upload fail，try again！");
        }

        String basePath = ImageTagUtils.getHomePath() + ImageTagUtils.getBookPath(bookId, period, bookName, true);
        ImageTagUtils.createJsonFile(json, basePath, bookId + "_" + bookName);
        String formalImgFilePath = ImageTagUtils.getHomePath() + ImageTagUtils.getImageTagFileUploadPath(bookId, period, bookName, 1, true);
        String formalAudioFilePath = ImageTagUtils.getHomePath() + ImageTagUtils.getImageTagFileUploadPath(bookId, period, bookName, 2, true);

        String tempBasePath = ImageTagUtils.getHomePath() + File.separator + TagClass.BASE_PATH.replaceFirst("bookId", String.valueOf(bookId))
                .replaceFirst("bookName", bookName).replaceFirst("lessonId", String.valueOf(lessonId));
        String tempImgFilePath = tempBasePath + File.separator + lessonId + File.separator + "img";
        String tempAudioFilePath = tempBasePath + File.separator + lessonId + File.separator + "sounds";

        //删除多余的图片;
        List<String> allImageFile = FileUtil.getDirAllFile(tempImgFilePath);
        List<String> needDelImage = new ArrayList<String>();
        for (String string : allImageFile) {
            if (!json.contains(string)) {
                needDelImage.add(string);
            }
        }

        if (needDelImage.size() > 0) {
            List<String> delList = new ArrayList<String>();
            for (int i = 0; i < needDelImage.size(); i++) {
                delList.add(tempImgFilePath + File.separator + needDelImage.get(i));
            }
            //删除磁盘文件
            FileUtil.deleteFile(delList.toArray(new String[delList.size()]));
        }

        //删除多余的音频;
        List<String> allAudioFile = FileUtil.getDirAllFile(tempAudioFilePath);
        List<String> needDelAudio = new ArrayList<String>();
        for (String string : allAudioFile) {
            if (!json.contains(string)) {
                needDelAudio.add(string);
            }
        }

        if (needDelAudio.size() > 0) {
            List<String> delList = new ArrayList<String>();
            for (int i = 0; i < needDelAudio.size(); i++) {
                delList.add(tempAudioFilePath + File.separator + needDelAudio.get(i));
            }
            //删除磁盘文件
            FileUtil.deleteFile(delList.toArray(new String[delList.size()]));
        }

        //移动文件
        FileUtil.moveDirAllFile(tempImgFilePath, formalImgFilePath);
        FileUtil.moveDirAllFile(tempAudioFilePath, formalAudioFilePath);

        return new Message(true, "upload successful");
    }

    @Override
    public Message saveBook(int bookId, String bookName) {
        List<Map<String, Object>> lessonList = imageTagDao.getLessonList(String.valueOf(bookId));

        String period = "76";
        String json = TagClass.analysisBookHtml(bookId, bookName, lessonList);
        if (StringUtils.isEmpty(json)) {
            return new Message(false, "Upload fail，try again！");
        }

        String basePath = ImageTagUtils.getHomePath() + ImageTagUtils.getBookPath(bookId, period, bookName, true);
        ImageTagUtils.createJsonFile(json, basePath, bookId + "_" + bookName);
        String formalImgFilePath = ImageTagUtils.getHomePath() + ImageTagUtils.getImageTagFileUploadPath(bookId, period, bookName, 1, true);
        String formalAudioFilePath = ImageTagUtils.getHomePath() + ImageTagUtils.getImageTagFileUploadPath(bookId, period, bookName, 2, true);

        String tempBasePath = ImageTagUtils.getHomePath() + File.separator + TagClass.BASE_PATH.replaceFirst("bookId", String.valueOf(bookId))
                .replaceFirst("bookName", bookName);
        String tempImgFilePath = tempBasePath + File.separator + "img";
        String tempAudioFilePath = tempBasePath + File.separator + "sounds";

        //删除多余的图片;
        List<String> allImageFile = FileUtil.getDirAllFile(tempImgFilePath);
        List<String> needDelImage = new ArrayList<String>();
        for (String string : allImageFile) {
            if (!json.contains(string)) {
                needDelImage.add(string);
            }
        }

        if (needDelImage.size() > 0) {
            List<String> delList = new ArrayList<String>();
            for (int i = 0; i < needDelImage.size(); i++) {
                delList.add(tempImgFilePath + File.separator + needDelImage.get(i));
            }
            //删除磁盘文件
            FileUtil.deleteFile(delList.toArray(new String[delList.size()]));
        }

        //删除多余的音频;
        List<String> allAudioFile = FileUtil.getDirAllFile(tempAudioFilePath);
        List<String> needDelAudio = new ArrayList<String>();
        for (String string : allAudioFile) {
            if (!json.contains(string)) {
                needDelAudio.add(string);
            }
        }

        if (needDelAudio.size() > 0) {
            List<String> delList = new ArrayList<String>();
            for (int i = 0; i < needDelAudio.size(); i++) {
                delList.add(tempAudioFilePath + File.separator + needDelAudio.get(i));
            }
            //删除磁盘文件
            FileUtil.deleteFile(delList.toArray(new String[delList.size()]));
        }

        //移动文件
        FileUtil.moveDirAllFile(tempImgFilePath, formalImgFilePath);
        FileUtil.moveDirAllFile(tempAudioFilePath, formalAudioFilePath);
        return new Message(true, "upload successful");
    }

    @Override
    public void updateZipSize() {
        List<Map<String, Object>> bookList = imageTagDao.getBookList();
        for (Map<String, Object> map : bookList) {
            int id = (Integer) map.get("id");
            ImBook book = imageTagDao.getBook(id);
            String zipFile = ImageTagUtils.getZipFile(String.valueOf(id), "76", book.getBookName());
            File file = new File(zipFile);
            book.setZipSize(file.length());
            imageTagDao.saveBook(book);
        }
    }

}