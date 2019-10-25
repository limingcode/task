package com.image.tag.dao;

import java.util.List;
import java.util.Map;

import com.image.tag.model.ImBook;
import com.skyedu.model.book.BookAudioRecordModel;
import com.skyedu.model.book.SendBookJsonRecordModel;
import com.skyedu.model.common.PageModel;
import com.skyedu.model.user.ShareBookModel;

public interface ImageTagDao {
    /**
     * 检查版本
     *
     * @param type
     * @return
     */
    Map<String, Object> checkVersion(int type);

    PageModel getMyWorks(int studentId, PageModel pageModel);

    void shareBook(ShareBookModel shareBookModel);

    /**
     * 保存音频信息
     * @param BookAudioRecordModel
     */
    void saveBookAudioRecordModel(BookAudioRecordModel BookAudioRecordModel);

    Map<String, Object> getShareAudioInfo(int studentId,int graphicalId,int isShare);


    Map<String, Object> getShareBookInfo(int bookId, int studentId);

    /**
     * 获取分享音频信息集合
     * @param studentId
     * @param bookId
     * @param isShare
     * @return
     */
    List<Map<String, Object>> getShareAudioInfoList(Integer studentId,Integer bookId,Integer isShare,Integer tag);

    BookAudioRecordModel getShareAudioInfo(int bookAudioRecordId);

    /**
     * 保存或者修改对象
     * @param object
     * @return
     */
    int  saveOrUpdateObj(Object object);

    public ImBook getBook(int id);

    public SendBookJsonRecordModel getSendBookJsonRecordModel(int id);

    public Map<String, Object> getLesson(int lessonId);

    public Map<String, Object> getStudent(int studentId);

    public ImBook getBook(String gradeCode, String subjectCode, int cateCode, String bookName);

    public int saveBook(ImBook book);
    public int saveSendBookJsonRecordModel(SendBookJsonRecordModel book);

    public void updateBookStatus(int id, byte status);

    public void updateBookStatus(String gradeCode, String subjectCode, int cateCode, String bookName, byte status);

    public List<Map<String, Object>> getSendFailBook();

    public List<Map<String, Object>> getLessonList(String bookId);

    public List<Map<String, Object>> getCateAndStatus(String subject, String grade, String period, String level);

    public List<Map<String, Object>> getBookTypeList();

    public List<Map<String, Object>> getBookList(int studentId);

    @Deprecated
    public List<Map<String, Object>> getBookList(int studentId, int cityId);

    List<Map<String, Object>> getNewBookList(int studentId, int cityId, Integer cateCode,int tag);

    @Deprecated
    public List<Map<String, Object>> getBookList(int studentId, int cityId, int cateCode);

    /**
     * 兼容IOS
     *
     * @param studentId
     * @param cityId
     * @param cateCode
     * @return
     */
    @Deprecated
    public List<Map<String, Object>> getBookListByVisitor(int studentId, int cityId, int cateCode);

    public List<Map<String, Object>> getBookList();

    public List<Map<String, Object>> getBookListById(String ids);


    public List<Map<String, Object>> getAllBookList();

    List<Map<String, Object>> getLessonList(int fid);

    void updateLessonPop(String id, String pop);

    void addLesson(String josn);



}
