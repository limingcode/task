package com.skyedu.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.skyedu.model.WaTeacherLoginInfo;
import com.util.CommonUtil;
import com.util.HbmDAOUtil;

/**   
* 类说明
* @author  lisujing
* @version V1.0   
* @Date    2019年7月10日 下午2:31:01  
*/
@Repository
@SuppressWarnings("unchecked")
public class LoginLogDao extends HbmDAOUtil{
	
	public void add(WaTeacherLoginInfo waTeacherLoginInfo) {
		this.save(waTeacherLoginInfo);
	}
	
    public List<Map<String,Object>> getLoginLogList(Map<String,Object> condition) {
    	int pageNo=(Integer)condition.get("pageNo");
        String courseListSql = getLoginLogListSql(condition);
        String sqlCount = "select count(*) count from ("+getLoginLogListSql(condition)+") temp";
        Query q = factory.getCurrentSession().createSQLQuery(courseListSql)
				.setFirstResult((pageNo - 1) * CommonUtil.QUESTIONSIZE)
				.setMaxResults(CommonUtil.QUESTIONSIZE)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Query q1 = factory.getCurrentSession().createSQLQuery(sqlCount).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		int size = (Integer) ((Map<String,Object>)q1.uniqueResult()).get("count");
		int totalPage = size / CommonUtil.QUESTIONSIZE + 1;
		if (size % CommonUtil.QUESTIONSIZE == 0) {
			totalPage = totalPage - 1;
		}
		if (pageNo > totalPage) {
			pageNo = totalPage;
			condition.put("pageNo", pageNo);
		}
		condition.put("totalPage", totalPage);
        return q.list();
    }

    /*private String getLoginLogListSql(Map<String,Object> condition){
        StringBuilder sb= new StringBuilder();
		sb.append(" select distinct teacher.name teacherName,teacher.id,");
		sb.append(" stuff((select ','+sub.name");
		sb.append(" from Edu_Course course");
		sb.append(" left join Edu_Subject sub on sub.code=course.subject");
		sb.append(" left join Edu_Period pe on pe.id=course.period");
		sb.append(" where teacher.id=course.teacher and course.status=1 and pe.state=1 group by sub.name,sub.id order by sub.id for xml PATH('')),1,1,'') subjectName,");
		sb.append(" stuff((select ','+gra.name ");
		sb.append(" from Edu_Course course");
		sb.append(" left join Edu_Grade gra on gra.code=course.grade");
		sb.append(" left join Edu_Period pe on pe.id=course.period");
		sb.append(" where teacher.id=course.teacher and course.status=1 and pe.state=1 group by gra.name,gra.id order by gra.id FOR xml PATH('')),1,1,'') gradeName,");
		sb.append(" convert(varchar(100),info.createDate,23) loginDate,");
		sb.append(" stuff((select distinct '@'+LEFT(convert(varchar(100),tInfo.createDate,108),5)");
		sb.append(" from WA_TeacherLoginInfo tInfo");
		sb.append(" where tInfo.oaId=info.oaId and convert(varchar(100),tInfo.createDate,23)=convert(varchar(100),info.createDate,23)");
		sb.append(" FOR xml PATH('')),1,1,'') times");
		sb.append(" from Edu_Teacher teacher");
		sb.append(" left join WA_TeacherLoginInfo info on teacher.oaId=info.oaId");
		sb.append(" left join Edu_Course c on c.teacher=teacher.id");
		sb.append(" left join Edu_Subject s on s.code=c.subject");
		sb.append(" left join Edu_Grade g on g.code=c.grade");
		sb.append(" where teacher.status<>5");
		if(condition.get("teacherName")!=null&&!StringUtils.isEmpty(condition.get("teacherName").toString())) {
			sb.append(" and teacher.name like '%"+condition.get("teacherName").toString()+"%'");
		}
		if(condition.get("subjectId")!=null) {
			sb.append(" and s.id="+(Integer)condition.get("subjectId"));
		}
		if(condition.get("gradeId")!=null) {
			sb.append(" and g.id="+(Integer)condition.get("gradeId"));
		}
		if(condition.get("loginDate")!=null&&!StringUtils.isEmpty(condition.get("loginDate").toString())) {
			sb.append(" and convert(varchar(100),info.createDate,23)='"+condition.get("loginDate").toString()+"'");
		}
        return sb.toString();
    }*/
    private String getLoginLogListSql(Map<String,Object> condition){
        StringBuilder sb= new StringBuilder();
        sb.append(" select distinct temp.teacherName,temp.subjectName,temp.gradeName,temp.loginDate,");
        sb.append(" stuff((select distinct '@'+LEFT(convert(varchar(100),tInfo.createDate,108),5)");
		sb.append(" from TP_TeacherRecord tInfo");
		sb.append(" where tInfo.oaId=temp.oaId and convert(varchar(100),tInfo.createDate,23)=temp.loginDate");
		sb.append(" FOR xml PATH('')),1,1,'') times");
		sb.append(" from (");
		sb.append(" select distinct teacher.name teacherName,teacher.id,info.oaId,");
		sb.append(" stuff((select ','+sub.name");
		sb.append(" from Edu_Course course");
		sb.append(" left join Edu_Subject sub on sub.code=course.subject");
		sb.append(" left join Edu_Period pe on pe.id=course.period");
		sb.append(" where teacher.id=course.teacher and course.status=1 and pe.state=1 group by sub.name,sub.id order by sub.id for xml PATH('')),1,1,'') subjectName,");
		sb.append(" stuff((select ','+gra.name ");
		sb.append(" from Edu_Course course");
		sb.append(" left join Edu_Grade gra on gra.code=course.grade");
		sb.append(" left join Edu_Period pe on pe.id=course.period");
		sb.append(" where teacher.id=course.teacher and course.status=1 and pe.state=1 group by gra.name,gra.id order by gra.id FOR xml PATH('')),1,1,'') gradeName,");
		sb.append(" convert(varchar(100),info.createDate,23) loginDate,");
		sb.append(" LEFT(convert(varchar(100),info.createDate,108),5) times");
		sb.append(" from Edu_Teacher teacher");
		sb.append(" left join TP_TeacherRecord info on teacher.oaId=info.oaId");
		sb.append(" left join Edu_Course c on c.teacher=teacher.id");
		sb.append(" left join Edu_Subject s on s.code=c.subject");
		sb.append(" left join Edu_Grade g on g.code=c.grade");
		sb.append(" where teacher.status<>5 and c.status=1");
		if(condition.get("teacherName")!=null&&!StringUtils.isEmpty(condition.get("teacherName").toString())) {
			sb.append(" and teacher.name like '%"+condition.get("teacherName").toString()+"%'");
		}
		if(condition.get("subjectId")!=null) {
			sb.append(" and s.id="+(Integer)condition.get("subjectId"));
		}
		if(condition.get("gradeId")!=null) {
			sb.append(" and g.id="+(Integer)condition.get("gradeId"));
		}
		if(condition.get("loginDate")!=null&&!StringUtils.isEmpty(condition.get("loginDate").toString())) {
			sb.append(" and convert(varchar(100),info.createDate,23)='"+condition.get("loginDate").toString()+"'");
		}
		sb.append(" ) temp");
        return sb.toString();
    }
}
