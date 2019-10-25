package com.image.tag.service;

import java.util.List;
import java.util.Map;

import com.image.tag.Message;
import com.image.tag.model.ImBook;
import com.skyedu.model.book.SendBookJsonRecordModel;

public interface ImageTagService {
	
	public ImBook getBook(int id);

	 SendBookJsonRecordModel getSendBookJsonRecordModel(int id);

    void getBookJson(int id);
    String getJsonFlie(Map<String, Object> map);

	public Map<String, Object> getLesson(int lessonId);
	
	public boolean isUpdataImageTag(String bookId, String period, String bookName);

	public List<String> getImageList(String json, int bookId, String period, String bookName, boolean updataImageTag);
	
	public void saveImageTag(int bookId, String period, String bookName, String jsonString) throws Exception;

	public void sendBook(String bookId, String period, String bookName, String filePath);
	
	public Map<String, Object> getStudentClass(int userStudentId);
	
	public Map<String, Object> getStudentClass(int studentId, int cityId);

	public void updateBookStatus(String bookId, String period, String bookName, byte fileSendStatusSended);

	public List<Map<String, Object>> getSendFailBook();

	public List<Map<String, Object>> getAllBook();

	public List<Map<String, Object>> getLessonList(String bookId);

	public List<Map<String, Object>> getCateAndStatus(String subject, String grade, String period, String level);

	public List<Map<String, Object>> getBookTypeList();

	public Message saveLesson(int bookId, String bookName, int lessonId, String lessonName);

	public Message saveBook(int bookId, String bookName);

	public void updateZipSize();

}
