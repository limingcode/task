package com.teach.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.teach.dao.TpCoursewareDao;
import com.teach.model.TpCourseware;

@Repository
public class TpCoursewareDaoImpl extends BaseDao implements TpCoursewareDao{

	public TpCourseware saveCourseware(TpCourseware courseware) {
		return (TpCourseware) saveOrUpdate(courseware);
	}

	public TpCourseware getCourseware(int id) {
		return (TpCourseware) find(TpCourseware.class, id);
	}

	@Override
	public TpCourseware getCoursewareByLessonId(int lessonId) {
		String hql = "from TpCourseware c where c.lessonId = '"+ lessonId +"'";
		return (TpCourseware) getObjUseHql(hql);
	}

	@Override
	public void saveCourseware(String tableName, Map<String, Object> map) {
		saveMap(tableName, map);
	}

	@Override
	public Map<String, Object> getCoursewareMapByLessonId(int lessonId) {
		String sql = "select * from tp_courseware where lessonId = '"+ lessonId +"'";
		return getMapUseSql(sql);
	}
    @Override
    public List<Map<String,Object>> test() {
        String sql = "select lessonId from TP_Courseware where pptType=2 order by creatTime desc";
        Query query = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        return query.list();
    }

	@Override
	public void deleteCourseware(int id) {
		delete(TpCourseware.class, id);
	}

	@Override
	public void deleteCoursewareBylessonId(int lessonId) {
		String sql = "delete from tp_courseware where lessonId = "+ lessonId;
		updateUseSql(sql);
	}

	@Override
	public void updateLessonName(int id, String name) {
		String sql = "update WA_Lesson set brief = '"+ name.replaceAll("'", "''") +"' where id = "+ id;
		updateUseSql(sql);
	}
}
