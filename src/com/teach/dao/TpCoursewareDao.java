package com.teach.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.teach.model.TpCourseware;

public interface TpCoursewareDao {
    List<Map<String,Object>> test();
	TpCourseware saveCourseware(TpCourseware courseware);

	TpCourseware getCourseware(int id);

	TpCourseware getCoursewareByLessonId(int lessonId);
	
	void saveCourseware(String tableName, Map<String, Object> map);

	Map<String, Object> getCoursewareMapByLessonId(int lessonId);

	void deleteCourseware(int id);

	void deleteCoursewareBylessonId(int lessonId);
	
	void updateLessonName(int id, String name);
	
}
