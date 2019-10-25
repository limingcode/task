package com.teach.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.teach.Page;
import com.teach.dao.StatisticsDao;

@Repository
public class StatisticsDaoImpl extends BaseDao implements StatisticsDao {

	/**
	 * 课件上传状态查询
	 */
	@Override
	public Page getLessonUploadStatus(Page page, String subject, String period, String grade, String cate) {
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
		
		String idSql = "select ep.id epId, es.id esId, eg.id egId, ec.id ecId " + 
						"from WA_Lesson wl inner join WA_Hierarchy wh on wh.id = wl.hierarchy " + 
						"        inner join Edu_Grade eg on wh.grade = eg.code " + 
						"        inner join ("+ ep +") ep on ep.id = wl.period " + 
						"        inner join Edu_Subject es on es.code = wh.subject " + 
						"        inner join Edu_Cate ec on ec.code = wh.cate " + 
						"where 1=1 " + sqlWhere +
						"group by ep.id, es.id, eg.id, ec.id";
		
		List<Map<String, Object>> idList = getPageListUseSql(idSql, page);
		if(idList == null || idList.size() == 0) {
			return page;
		}
		
		//查询总数
		String totalNumSql = "select count(*) from( " +
						    "	select count(*) countNum " + 
							"	from WA_Lesson wl inner join WA_Hierarchy wh on wh.id = wl.hierarchy " + 
							"        inner join Edu_Grade eg on wh.grade = eg.code " + 
							"        inner join ("+ ep +") ep on ep.id = wl.period " +  
							"        inner join Edu_Subject es on es.code = wh.subject " + 
							"        inner join Edu_Cate ec on ec.code = wh.cate " + 
							"	where 1=1 " + sqlWhere +
							"	group by ep.id, es.id, eg.id, ec.id" +
							") t";
		int totalNum = getCount(totalNumSql);
		int totaPage = (int) Math.ceil((double)totalNum/page.getPageSize());
		page.setTotalPage(totaPage);
		page.setHasPrePage(page.getCurrPage() > 1 ? true : false); //是否有上一页
		page.setHasNextPage(page.getCurrPage() < totaPage ? true : false); //是否有下一页
		
		String idWhere = "";
		for (Map<String, Object> map : idList) {
			idWhere  += " or (ep.id = '"+ map.get("epId") +"' " 
					 + " and eg.id = "+ map.get("egId") +" "
					 + " and es.id = "+ map.get("esId") +" "
					 + " and ec.id = "+ map.get("ecId") +" )";
		}
		String sql = "select wl.id, wl.sortNo, eg.id egId, ep.id epId, es.id esId, ec.id ecId, es.code  esCode, es.name  esName, (ep.year + ep.name)  perName, eg.code  egCode, eg.name  egName, ec.code  ecCode, ec.name  ecName, " + 
					"        (case when tc.id is null then 0 else 1 end) course, (case when tf5.id is null then 0 else 1 end) lesson, (case when tf6.id is null then 0 else 1 end) memoir  " + 
					"from WA_Lesson wl inner join WA_Hierarchy wh on wh.id = wl.hierarchy " + 
					"        inner join Edu_Grade eg on wh.grade = eg.code " + 
					"        inner join ("+ ep +") ep on ep.id = wl.period " + 
					"        inner join Edu_Subject es on es.code = wh.subject " + 
					"        inner join Edu_Cate ec on ec.code = wh.cate " + 
					"        left join TP_Courseware tc on wl.id = tc.lessonId " + 
					"        left join TP_FileUpload tf5 on wl.id = tf5.lessonId and tf5.fileType = 5 " + 
					"        left join TP_FileUpload tf6 on wl.id = tf6.lessonId and tf6.fileType = 6 " + 
					"where 1=1 and " + idWhere.replaceFirst("or", "") +
					"order by ep.id asc, es.id asc, eg.id asc, ec.id asc, wl.sortNo asc";
		List<Map<String, Object>> list = getListUseSql(sql);
		page.setDataList(list);
		return page;
	}
	
	/**
	 * 课件上传状态查询V2
	 */
	@Override
	public List<Map<String, Object>> getLessonUploadStatus(String subject, String period){
		String ep = "select p.state,p.year,p.pName name,	stuff((select ','+cast(epe.id AS varchar(10)) from (select *,left(name,2) pName from Edu_Period ep ) epe where epe.year=p.year and epe.pName=p.pName for xml path('')),1,1,'') id from (select *,left(name,2) pName from Edu_Period ep where ep.status!=-1) p group by p.state,pName,p.year ";
		String sql = "select wl.sortNo, eg.code egCode, eg.name egName, ec.code ecCode, ec.name  ecName," + 
					"      (case when tc.id is null then 0 else 1 end) course," + 
					"      (case when tc.teachCaseName is null then 0 else 1 end) caseStatus," + 
					"      (case when tf5.id is null then 0 else 1 end) lesson," + 
					"      (case when tf6.id is null then 0 else 1 end) memoir " + 
					"from WA_Lesson wl inner join WA_Hierarchy wh on wh.id = wl.hierarchy " + 
					"        inner join Edu_Grade eg on wh.grade = eg.code" + 
					"        inner join ("+ ep +") ep on ep.id = wl.period" + 
					"         inner join Edu_Subject es on es.code = wh.subject" + 
					"         inner join Edu_Cate ec on ec.code = wh.cate and ec.code in (1, 2, 3, 7)" + 
					"         left join TP_Courseware tc on wl.id = tc.lessonId" + 
					"         left join TP_FileUpload tf5 on wl.id = tf5.lessonId and tf5.fileType = 5" + 
					"         left join TP_FileUpload tf6 on wl.id = tf6.lessonId and tf6.fileType = 6 " + 
					"where es.code = '"+ subject +"' and ep.id = '"+ period +"' " + 
					"order by  wl.sortNo, ec.id, eg.id asc";
		return getListUseSql(sql);
	}

}
