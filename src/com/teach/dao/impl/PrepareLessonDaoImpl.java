package com.teach.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.teach.dao.PrepareLessonDao;

@Repository
public class PrepareLessonDaoImpl extends BaseDao implements PrepareLessonDao {

	@Override
	public List<Map<String, Object>> getLessonStatus(String period, String grade, String subject, String cate) {
		String ep = "select p.state,p.year,p.pName name,	stuff((select ','+cast(epe.id AS varchar(10)) from (select *,left(name,2) pName from Edu_Period ep ) epe where epe.year=p.year and epe.pName=p.pName for xml path('')),1,1,'') id from (select *,left(name,2) pName from Edu_Period ep where ep.status!=-1) p group by p.state,pName,p.year ";
		
		StringBuffer sqlWhere = new StringBuffer();
		if(StringUtils.isNotEmpty(subject)) { //科目 
			sqlWhere.append(" and es.code = '"+ subject +"' ");
		}
		if(StringUtils.isNotEmpty(period)) { //学期
			sqlWhere.append(" and wl.period = '"+ period +"' ");
		}
		if(StringUtils.isNotEmpty(grade)) { //年级 
			sqlWhere.append(" and eg.code = '"+ grade +"' ");
		}
		if(StringUtils.isNotEmpty(cate)) { //层次
			sqlWhere.append(" and ec.code = '"+ cate +"' ");
		}
		
		String sql = "select wl.id, wl.sortNo, (case when tc.id is not null and tc.pptType != 0 then 1 else 0 end) course, (case when tf5.id is null and tc.lessonVideoUrl is null then 0 else 1 end) lessonVideo, (case when tf6.id is null and tc.memoirVideoUrl is null then 0 else 1 end) memoirVideo  " + 
				"from WA_Lesson wl inner join WA_Hierarchy wh on wh.id = wl.hierarchy " + 
				"        inner join Edu_Grade eg on wh.grade = eg.code " + 
				"        inner join ("+ ep +") ep on ep.id = wl.period " +  
				"        inner join Edu_Subject es on es.code = wh.subject " + 
				"        inner join Edu_Cate ec on ec.code = wh.cate " + 
				"        left join TP_Courseware tc on wl.id = tc.lessonId" + 
				"        left join TP_FileUpload tf5 on wl.id = tf5.lessonId and tf5.fileType = 5 " + 
				"        left join TP_FileUpload tf6 on wl.id = tf6.lessonId and tf6.fileType = 6 " + 
				"where 1=1 " + sqlWhere +
				"order by wl.hierarchy, wl.sortNo ";
		return getListUseSql(sql);
	}

}
