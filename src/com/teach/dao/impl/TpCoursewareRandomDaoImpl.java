package com.teach.dao.impl;

import org.springframework.stereotype.Repository;

import com.teach.dao.TpCoursewareRandomDao;
import com.teach.model.TpCoursewareRandom;

@Repository
public class TpCoursewareRandomDaoImpl extends BaseDao implements TpCoursewareRandomDao{

	public TpCoursewareRandom saveCoursewareRandom(TpCoursewareRandom coursewareRandom) {
		return (TpCoursewareRandom) saveOrUpdate(coursewareRandom);
	}

	public TpCoursewareRandom getCoursewareRandom(int id) {
		return (TpCoursewareRandom) find(TpCoursewareRandom.class, id);
	}

	@Override
	public TpCoursewareRandom getCoursewareRandomByLessonId(int lessonId) {
		String hql = "from TpCoursewareRandom c where c.lessonId = "+ lessonId;
		return (TpCoursewareRandom) getObjUseHql(hql);
	}

	@Override
	public void deleteCoursewareRandom(int lessonId) {
		String sql = "delete from TP_CoursewareRandom where lessonId ="+ lessonId;
		updateUseSql(sql);
	}

}
