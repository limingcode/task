package com.teach.dao;

import com.teach.model.TpCoursewareRandom;

public interface TpCoursewareRandomDao {

	TpCoursewareRandom saveCoursewareRandom(TpCoursewareRandom coursewareRandom);

	TpCoursewareRandom getCoursewareRandom(int id);

	TpCoursewareRandom getCoursewareRandomByLessonId(int lessonId);
	
	void deleteCoursewareRandom(int lessonId);
	
}
