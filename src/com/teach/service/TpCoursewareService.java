package com.teach.service;

import com.teach.model.TpCourseware;

public interface TpCoursewareService {

	TpCourseware saveCourseware(TpCourseware courseware, String[] background);

	TpCourseware getCourseware(int id);

	TpCourseware getCoursewareByLessonId(int lessonId);

	void updateUploadFile(int lessonId, String content, String[] background);
	
	void saveCopyPpt(int currLessonId, int targetLessonId);


    TpCourseware saveUploadCourseware(TpCourseware courseware, boolean isUpdate);

	void deleteUploadCoursewareFile(String id, int lessonId);
	
	void deleteCourseware(int lessonId);

	void uploadVideoSrc(int lessonId, byte type, String videoSrc);
	
	void updateLessonName(int id, String name);
}
