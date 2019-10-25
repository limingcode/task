package com.skyedu.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.skyedu.model.EduCate;
import com.skyedu.model.EduDepa;
import com.skyedu.model.EduGrade;
import com.skyedu.model.EduPeriod;
import com.skyedu.model.EduSubject;
import com.skyedu.model.EduTeacher;
import com.util.DateUtil;
import com.util.HbmDAOUtil;

@Repository
@SuppressWarnings("unchecked")
public class BaseDAO extends HbmDAOUtil {

	/** 学期 */
	public List<Map<String, Object>> periodList() {

		return factory
				.getCurrentSession()
				.createSQLQuery(
						"select top 5 * from (select p.state,p.year,p.pName name,stuff((select ','+cast(epe.id AS varchar(10)) from (select *,left(name,2) pName from Edu_Period ep ) epe where epe.year=p.year and epe.pName=p.pName for xml path('')),1,1,'') id from (select *,left(name,2) pName from Edu_Period ep where ep.status!=-1) p group by p.state,pName,p.year )t order by t.state,t.year desc,t.id DESC")
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	/** 学期 */
	public List<EduPeriod> periodBeans() {
		return this.list(EduPeriod.class.getName());
	}

	/** 校区 */
	public List<Map<String, Object>> depaList() {
		return factory.getCurrentSession()
				.createSQLQuery("select * from Edu_Depa")
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	/** 校区 */
	public List<EduDepa> depaBeans() {
		return this.list(EduDepa.class.getName());
	}

	/** 年级 */
	public List<Map<String, Object>> gradeList() {
		return factory
				.getCurrentSession()
				.createSQLQuery(
						"select * from Edu_Grade eg where eg.code not in ('A13','B01') and id<13 order by id")
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	/** 年级 */
	public List<EduDepa> gradeBeans() {
		return this.list(EduGrade.class.getName());
	}

	/** 科目 */
	public List<Map<String, Object>> subjectList() {
		return factory
				.getCurrentSession()
				.createSQLQuery(
						"select * from Edu_Subject es where es.code not in ('A03','A09')")
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	/** 科目 */
	public List<EduDepa> subjectBeans() {
		return this.list(EduSubject.class.getName());
	}

	/** 层次 */
	public List<Map<String, Object>> cateList() {
	    String  sql ="select *\n" +
                "from Edu_Cate\n" +
                "where id in (53,\n" +
                "             1253,\n" +
                "             1283,\n" +
                "             52,\n" +
                "             1390,\n" +
                "             1393,\n" +
                "             1394,\n" +
                "             1401,\n" +
                "             51,\n" +
                "             1261,\n" +
                "             1268)\n" ;
		return factory
				.getCurrentSession()
				.createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	/** 课程 */
	public List<Map<String, Object>> courseList(Map<String, Object> map) {
		Object object = map.get("dept");
		Object object2 = map.get("cate");
		Object object3 = map.get("subject");
		Object object4 = map.get("grade");
		Object object5 = map.get("period");
		Object object6 = map.get("oaId");
		String sql = "select ec.*,et.name teacherName from Edu_Course ec left join Edu_Teacher et on et.id=ec.teacher where ec.status!=-1 ";
		if (object != null && !"0".equals(object.toString())) {
			sql = sql + " and ec.depa='"+object+"'";
		}
		if (object2 != null) {
			sql = sql + " and ec.cate='"+object2+"'";
		}
		if (object3 != null) {
			sql = sql + " and ec.subject='"+object3+"'";
		}
		if (object4 != null) {
			sql = sql + " and ec.grade='"+object4+"'";
		}
		if (object5 != null) {
			sql = sql + " and ec.period in ("+object5+")";
		}
		/*if (object6 != null) {
			sql = sql + " and et.oaId ="+object6;
		}*/
		return factory.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	/** 课程 */
	public Map<String, Object> getCourse(int courseId) {
		String sql = "select * from Edu_Course where id=" + courseId;
		Query q = factory.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return (Map<String, Object>) q.uniqueResult();
	}

	/** 层次 */
	public List<EduDepa> cateBeans() {
		return this.list(EduCate.class.getName());
	}

	/** 老师 */
	public Map<String, Object> teacherInfo(Integer oaId) {
		List<Map<String, Object>> list = factory
				.getCurrentSession()
				.createSQLQuery(
						"select * from Edu_Teacher where oaid="
								+ oaId)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		if (list != null && list.size() > 0)
			return list.get(0);
		return null;
	}
	
	/** 老师 */
	public Map<String, Object> getTeacher(String teacherName, String tel) {
		List<Map<String, Object>> list = factory
				.getCurrentSession()
				.createSQLQuery(
						"select * from Edu_Teacher where (name='"+teacherName+"' or englishName='"+teacherName+"' or cnName='"+teacherName+"') and tel='"+tel+"'")
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		if (list != null && list.size() > 0)
			return list.get(0);
		return null;
	}

	/** 老师 */
	public EduTeacher teacherBean(Integer oaId) {
		Criteria criteria = factory.getCurrentSession().createCriteria(
				EduTeacher.class);
		criteria.add(Restrictions.eq("oaId", oaId));
		criteria.add(Restrictions.ne("status", 5));
		List<EduTeacher> list = criteria.list();
		if (null != list && list.size() > 0)
			return list.get(0);
		return null;
	}

	/** 我的班级 */
	public List<Map<String, Object>> courseList(Integer student) {
		String sql = "select co.* from Edu_StudentClass cls left join edu_Course co on cls.course=co.id where 1=1";
		if (student != null)
			sql += "and cls.student=" + student;
		sql += " and cls.status in (1,3)";
		return factory.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	/** 我的在读层次 */
	public List<Map<String, Object>> myHierarchys(int student) {
		StringBuffer sql = new StringBuffer(
				"select h.id,g.name gradeName,sub.name subjectName,cate.name cateName,");
		sql.append(" h.cate cateCode,h.grade gradeCode,h.subject subjectCode");
		sql.append(" from Edu_StudentClass cls left join Edu_Course co on cls.course=co.id");
		sql.append(" left join WA_Hierarchy h on co.grade=h.grade and h.cate=co.cate and h.subject=co.subject");
		sql.append(" left join Edu_Cate cate on h.cate=cate.code");
		sql.append(" left join Edu_Subject sub on h.subject=sub.code");
		sql.append(" left join Edu_Grade g on h.grade=g.code");
		sql.append(" where cls.student=" + student + " and cls.status =1");
		sql.append(" ");
		sql.append(" group by g.name,sub.name,cate.name,h.id,h.cate,h.grade,h.subject");
		return factory.getCurrentSession().createSQLQuery(sql.toString())
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
}
