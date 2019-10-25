package com.teach.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.teach.Page;
import com.teach.dao.TpFeedbackDao;

/**
 * 课后反馈dao
 * @author skyedu_beyond
 * @version 1.0
 */
@Repository
public class TpFeedbackDaoImpl extends BaseDao implements TpFeedbackDao {

	/**
	 * 得到反馈列表
	 */
	@Override
	public List<Map<String, Object>> getFeedbackList(int lessonId) {
		String sql = "select id, content, comment, createDate, (select name from Edu_Teacher et where et.oaId = tf.oaId) as name,"
				+ "(select es.name from Edu_Subject es, Edu_Course ed where es.code = ed.subject and ed.id = tf.courceId) as subject"
				+ " from TP_Feedback tf where tf.lessonId = '"+ lessonId +"'";
		return getListUseSql(sql);
	}

	/**
	 * 得到反馈分页列表
	 */
	@Override
	public Page getPage(Page page, String lessonId, String createTime, String subject, String period, String grade, String cate,
			String classTime, String name, String state) {
		StringBuffer sqlWhere = new StringBuffer();
		if(StringUtils.isNotEmpty(lessonId)) { //课次id
			sqlWhere.append(" and tf.lessonId = '"+ lessonId +"'");
		}
		if(StringUtils.isNotEmpty(createTime)) { //评论时间
			sqlWhere.append(" and convert(nvarchar(50), tf.createDate,112) = '"+ createTime.replaceAll("-", "") +"'");
		}
		if(StringUtils.isNotEmpty(subject)) { //科目 
			sqlWhere.append(" and es.code = '"+ subject +"'");
		}
		if(StringUtils.isNotEmpty(period)) { //学期
			sqlWhere.append(" and wl.period = '"+ period +"'");
		}
		if(StringUtils.isNotEmpty(grade)) { //年级 
			sqlWhere.append(" and eg.code = '"+ grade +"'");
		}
		if(StringUtils.isNotEmpty(classTime)) {  //课次
			sqlWhere.append(" and wl.id = '"+ classTime +"'");
		}
		if(StringUtils.isNotEmpty(cate)) { //层次
			sqlWhere.append(" and ec.code = '"+ cate +"'");
		}
		if(StringUtils.isNotEmpty(state)) { //状态（赞、不赞）
			sqlWhere.append(" and tf.comment = '"+ state +"'");
		}
		if(StringUtils.isNotEmpty(name)) { //姓名
			sqlWhere.append(" and et.name like '%"+ name +"%'");
		}
		String sql ="select tf.id, tf.content, tf.comment, convert(nvarchar(50), tf.createDate,111) createDate, es.name as subject," + 
					"        (ep.year + ep.name) as perName, eg.name as graName, wl.sortNo, ec.name as catName,  et.name as name " + 
					"   from TP_Feedback tf inner join WA_Lesson wl on tf.lessonId = wl.id " + 
					"	inner join WA_Hierarchy wh on wh.id = wl.hierarchy  " + 
					"   inner join Edu_Subject es on es.code = wh.subject " + 
					"   inner join Edu_Period ep on charindex(','+CONVERT(varchar(10),ep.id)+',', ','+wl.period+',')>0 " + 
					"   inner join Edu_Grade eg on wh.grade = eg.code " + 
					"   inner join Edu_Cate ec on ec.code = wh.cate " +
					"   inner join Edu_Teacher et on et.oaId = tf.oaId "+
					"   where 1=1 ";
		
		List<Map<String, Object>> data = getPageListUseSql(sql + sqlWhere.toString(), page);
		page.setDataList(data);
		sql = 	"select count(*) from TP_Feedback tf inner join WA_Lesson wl on tf.lessonId = wl.id " + 
				"	inner join WA_Hierarchy wh on wh.id = wl.hierarchy  " + 
				"   inner join Edu_Subject es on es.code = wh.subject " + 
				"   inner join Edu_Period ep on charindex(','+CONVERT(varchar(10),ep.id)+',', ','+wl.period+',')>0 " + 
				"   inner join Edu_Grade eg on wh.grade = eg.code " + 
				"   inner join Edu_Cate ec on ec.code = wh.cate " +
				"   inner join Edu_Teacher et on et.oaId = tf.oaId "+
				"   where 1=1";
		int totalNum = getCount(sql + sqlWhere.toString());
		int totaPage = (int) Math.ceil((double)totalNum/page.getPageSize());
		page.setTotalPage(totaPage);
		page.setHasPrePage(page.getCurrPage() > 1 ? true : false); //是否有上一页
		page.setHasNextPage(page.getCurrPage() < totaPage ? true : false); //是否有下一页
		return page;
	}

	@Override
	public Map<String, Object> getLessonInfo(int lessonId) {
		String sql = "select wl.id, es.code as subject, wl.period as perName, eg.code as graName, wl.sortNo, ec.code as catName " + 
					"   from WA_Lesson wl inner join WA_Hierarchy wh on wh.id = wl.hierarchy " + 
					"  inner join Edu_Subject es on es.code = wh.subject    " + 
					"  inner join Edu_Grade eg on wh.grade = eg.code    " + 
					"  inner join Edu_Cate ec on ec.code = wh.cate    " + 
					"  where wl.id = '"+ lessonId +"'";
		return getMapUseSql(sql);
	}

	@Override
	public void deleteFeedbackByLessonId(int lessonId) {
		String sql = "delete from TP_Feedback where lessonId = " + lessonId;
		updateUseSql(sql);;
	}

	@Override
	public void deleteAttendanceRecord(int lessonId) {
		String sql = "delete from TP_AttendanceRecord where lessonId = " + lessonId;
		updateUseSql(sql);;
	}

}
