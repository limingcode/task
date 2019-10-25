package com.skyedu.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.util.HbmDAOUtil;

@Repository
public class CourseDAO extends HbmDAOUtil {

	/** 获取学生班级列表 */
	public List<Map<String, Object>> getCourseList(int studentId) {
		String sql = "select ec.grade,ec.subject,ec.cate,ec.period,max(ec.id)iD,(select ect.name from Edu_Cate ect where ect.code = ec.cate) cateName,(select es.name from Edu_Subject es where es.code = ec.subject) subjectName,(select eg.name from Edu_Grade eg where eg.code = ec.grade) gradeName,(select left(ep.name,2) from Edu_Period ep where ep.id = ec.period) periodName,(select ep.year from Edu_Period ep where ep.id = ec.period) periodYear from Edu_Course ec where ec.id in (select course from Edu_StudentClass esc where esc.student="
				+ studentId
				+ "  and esc.status=1 ) and ec.cate is not NULL and ec.subject='A01' GROUP BY ec.grade,ec.subject,ec.cate,ec.period ORDER BY periodYear desc";
		Query q = factory.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}
	
	/** 获取学生班级列表 */
	public List<Map<String, Object>> getLessonList(int studentId) {
		String sql = "select ec.grade,ec.subject,ec.cate,ec.period,max(ec.id)iD,(select ect.name from Edu_Cate ect where ect.code = ec.cate) cateName,(select es.name from Edu_Subject es where es.code = ec.subject) subjectName,(select eg.name from Edu_Grade eg where eg.code = ec.grade) gradeName,(select left(ep.name,2) from Edu_Period ep where ep.id = ec.period) periodName,(select ep.year from Edu_Period ep where ep.id = ec.period) periodYear from Edu_Course ec where ec.id in (select course from Edu_StudentClass esc where esc.student="
				+ studentId
				+ "  and esc.status=1 ) and ec.cate is not NULL and ec.subject='A01' GROUP BY ec.grade,ec.subject,ec.cate,ec.period ORDER BY periodYear desc";
		Query q = factory.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}

	/** 获取班级学生列表 */
	public List<Map<String, Object>> getStudentList(int courseId,boolean flag) {
		String sql = "select es.* from Edu_Student es LEFT JOIN Edu_StudentClass esc on es.id=esc.student where esc.course="+courseId+ (flag?" and esc.status=1":"")+ " order by es.id desc";
		Query q = factory.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}
	
	/** 获取学生报的班级列表 */
	public List<Map<String,Object>> searchStudent(String studentName){
		String sql = "select es.id studentId,es.name studentName,ec.id courseId,ec.name courseName,(select et.name from edu_teacher et where et.id=ec.teacher) teacherName,(select ect.name from Edu_Cate ect where ect.code = ec.cate) cateName,(select es.name from Edu_Subject es where es.code = ec.subject) subjectName,(select eg.name from Edu_Grade eg where eg.code = ec.grade) gradeName,(select ep.name from Edu_Period ep where ep.id = ec.period) periodName,(select ep.year from Edu_Period ep where ep.id = ec.period) periodYear from Edu_StudentClass esc LEFT JOIN Edu_Student es on es.id=esc.student LEFT JOIN Edu_Course ec on esc.course=ec.id where esc.status=1 and es.name like '%"+studentName+"%' order by es.id desc,ec.id desc";
		Query q = factory.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}
	
	/** 获取学生班级详细信息列表 */
	public List<Map<String, Object>> getCourseInfoList(int studentId) {

		String sql="select es.id                                 studentId,\n" +
                    "       es.name                               studentName,\n" +
                    "       et.name                               teacherName,\n" +
                    "       'http://www.actionsky.net/' + et.photo photo,\n" +
                    "       ed.name                               deptName,\n" +
                    "       ec.beginDate,\n" +
                    "       ec.endDate,\n" +
                    "       ec.id                                 courseId,\n" +
                    "       ec.name                               courseName,\n" +
                    "       ect.name                              cateName,\n" +
                    "       esu.name                              subjectName,\n" +
                    "       eg.name                               gradeName,\n" +
                    "       ep.name                               periodName,\n" +
                    "       ep.year                               periodYear,\n" +
                    "       ec.courseTime as                      courseTime\n" +
                    "from Edu_StudentClass esc\n" +
                    "       LEFT JOIN Edu_Student es on es.id = esc.student\n" +
                    "       LEFT JOIN Edu_Course ec on esc.course = ec.id\n" +
                    "       LEFT JOIN Edu_Teacher et on et.id = ec.teacher\n" +
                    "       LEFT JOIN Edu_Depa ed on ed.code = ec.depa\n" +
                    "       LEFT JOIN Edu_Cate ect on ect.code = ec.cate\n" +
                    "       LEFT JOIN Edu_Grade eg on eg.code = ec.grade\n" +
                    "       LEFT JOIN Edu_Subject esu on esu.code = ec.subject\n" +
                    "       LEFT JOIN Edu_Period ep on ep.id = ec.period where\n" +
                    "     esc.status = 1 and esc.student = "+studentId+" order by\n" +
                    "     es.id desc,\n" +
                    "     ec.id desc";

		Query q = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}
    public List<Map<String, Object>> getWjCourseInfoList(int studentId) {
        String sql="select es.studentId   studentId,\n" +
                "       es.studentname studentName,\n" +
                "       et.teacherName teacherName,\n" +
                "       null           photo,\n" +
                "       null           deptName,\n" +
                "       ec.beginDate,\n" +
                "       ec.endDate,\n" +
                "       ec.CourseId    courseId,\n" +
                "       ec.CourseName  courseName,\n" +
                "       null           cateName,\n" +
                "       null           subjectName,\n" +
                "       eg.GradeName   gradeName,\n" +
                "       ep.periodname  periodName,\n" +
                "       ep.periodyear  periodYear,\n" +
                "       null as        courseTime\n" +
                "from Wj_Edu_StudentClass esc\n" +
                "       LEFT JOIN wj_Edu_Student es on es.studentId = esc.StudentId\n" +
                "       LEFT JOIN wj_Edu_Course ec on esc.CourseIdTo = ec.CourseId\n" +
                "       LEFT JOIN [192.168.218.15].SkyWJ.dbo.Edu_Teacher et on et.teacherId = ec.TeacherId\n" +
                "       LEFT JOIN wj_Edu_Grade eg on eg.GradeCode = ec.GradeCode\n" +
                "       LEFT JOIN [192.168.218.15].SkyWJ.dbo.Edu_Period ep on ep.periodid = ec.PeriodId\n" +
                "where esc.status = 1\n" +
                "  and esc.StudentId = "+studentId+"\n" +
                "order by es.studentId desc,\n" +
                "         ec.CourseId desc";

        Query q = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return q.list();
    }
	
	/** 获取所有没有更新上课时间的课程，定时任务查找并进行更新 */
	public List<Map<String, Object>> getCourseList(){
		String sql = "select * from Edu_Course es where es.status=1 and es.courseTimeFlag is null and es.endDate>'2017-07-01'";
		Query q = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}
	
	/** 更新课程的上课时间 */
	public void setCourseTime(String courseTime,int courseId){
		String sql = "update Edu_Course set courseTime='"+courseTime+"',courseTimeFlag=1 where id="+courseId;
		factory.getCurrentSession().createSQLQuery(sql).executeUpdate();
	}
}
