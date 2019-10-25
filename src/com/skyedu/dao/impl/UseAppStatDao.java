package com.skyedu.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.skyedu.Page;
import com.util.HbmDAOUtil;

@Repository
public class UseAppStatDao extends HbmDAOUtil {

	@SuppressWarnings("unchecked")
	public Page appAndWorkStat(Page page, int type, String className, String studentNum, String studentName, String teacherName,
			String grade, String loginDateStart, String loginDateEnd) {
		String sql = getSql(type, className, studentNum, studentName, teacherName, grade, loginDateStart, loginDateEnd);
		List<Map<String, Object>> list = factory.getCurrentSession().createSQLQuery(sql)
				.setMaxResults(page.getPageSize()).setFirstResult((page.getCurrPage()-1)*page.getPageSize())
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		page.setDataList(list);
		String countQL = getCountQL1(sql);
		Integer count = Integer.valueOf(factory.getCurrentSession().createSQLQuery(countQL).list().get(0).toString());
		int totaPage = (int) Math.ceil((double)count/page.getPageSize());
		page.setTotalPage(totaPage);
		page.setHasPrePage(page.getCurrPage() > 1 ? true : false); //是否有上一页
		page.setHasNextPage(page.getCurrPage() < totaPage ? true : false); //是否有下一页
		return page;
	}
	
	private String getSql(int type, String className, String studentNum, String studentName, String teacherName,
			String grade, String loginDateStart, String loginDateEnd) { //未登录过app
		StringBuffer sql = new StringBuffer();
		if (type == 4) {
			sql.append("select * from (")
				.append("        select a.code,a.name,c.name courseName,e.cnName teacherName,f.name gradeName,row_number() over (partition by a.id order by c.id desc)rank ")
				.append("        from Edu_Student a left join Edu_StudentClass b on a.id=b.student ")
				.append("                LEFT JOIN Edu_Course c on b.course=c.id")
				.append("                LEFT JOIN Edu_Period d on c.period = d.id")
				.append("                LEFT JOIN Edu_Teacher e on e.id=c.teacher")
				.append("                LEFT JOIN Edu_Grade f on f.code=c.grade")
				.append("        where  a.status <> -1 and c.subject='A01' and a.cityId = 1")
				.append("                and not exists (select 1 from WA_StudentLoginLog ab where ab.studentId = a.id) ")
				.append("                and b.status=1 and c.cate in (1,2,7) ")
				.append("                and d.status = 2 and f.id between 1 and 6")
				.append("                and c.name not like '%测试%'");
		} else if (type == 5){
			sql.append("select a.studentId, d.code, d.name, c.name courseName, f.name teacherName, e.name gradeName, count(a.id) num ")
				 .append("  from WA_StudentLoginLog a ")
				 .append("        left join Edu_StudentClass b on a.studentId = b.student ")
				 .append("        left join Edu_Course c on b.course = c.id ")
				 .append("        left join Edu_Student d on d.id = a.studentId ")
				 .append("        left join Edu_Teacher f on f.id = c.teacher ")
				 .append("        left join Edu_Grade e on e.code = c.grade ")
				 .append("        left join Edu_Period g on g.id = c.period ")
				 .append("where b.status = 1 and c.cate in (1,2,7) and c.subject='A01' and g.status=2 and c.name not like '%测试%' and d.cityId = 1 ");
				 
		} else if (type == 6){
			sql.append("select a.studentId, d.code, d.name, c.name courseName, f.name teacherName, e.name gradeName, count(h.id) num ")
				 .append("from (select distinct studentId from WA_StudentLoginLog) a ")
				 .append("        left join Edu_StudentClass b on a.studentId = b.student ")
				 .append("        left join Edu_Course c on b.course = c.id ")
				 .append("        left join Edu_Student d on d.id = a.studentId ")
				 .append("        left join Edu_Teacher f on f.id = c.teacher ")
				 .append("        left join Edu_Grade e on e.code = c.grade ")
				 .append("        left join Edu_Period g on g.id = c.period ")
				 .append("        left join WA_ResultCard h on h.studentId = a.studentId ")
				 .append("where b.status = 1 and c.cate in (1,2,7) and c.subject='A01' and g.status=2 and c.name not like '%测试%' and d.cityId = 1 and h.hasDealed=1 ");
				 
		} else {
			sql.append("select * from (")
			.append("select d.name,d.code, a.studentId,a.loginDate,a.message,c.name courseName,e.cnName teacherName,f.name gradeName, row_number() over (partition by a.studentId order by a.loginDate desc)rank ")
			.append("from  WA_StudentLoginLog a LEFT JOIN Edu_StudentClass b on a.studentId=b.student LEFT JOIN Edu_Course c on b.course=c.id ")
			.append("LEFT JOIN Edu_Student d on a.studentId=d.id ")
			.append("LEFT JOIN Edu_Teacher e on e.id=c.teacher ")
			.append("LEFT JOIN Edu_Grade f on f.code=c.grade ")
			.append("left join Edu_Period g on g.id=c.period ")
			.append("where c.cate in (1,2,7) and b.status=1 and c.subject='A01' and g.status=2 and d.cityId = 1 ");
			if (type == 1) { //登录过app但没提交过作业的学生统计
				sql.append("and (select count(1) from WA_ResultCard aa where aa.studentId=a.studentId )>0 ");
				sql.append("and (select count(1) from WA_ResultCard aa where aa.studentId=a.studentId and aa.hasDealed=1 )=0 ");
			} else if (type == 2) { // 登录过app且提交过作业次数少于4次的学生统计
				sql.append("and (select count(1) from WA_ResultCard aa where aa.studentId=a.studentId )>0 ");
				sql.append("and (select count(1) from WA_ResultCard aa where aa.studentId=a.studentId and aa.hasDealed=1 ) in (1, 2, 3) ");
			} else { //app版本不是最新的
				sql.append("and not exists (select 1 from WA_StudentLoginLog where a.studentId = studentId and message like '%1.2.18%')");
			}
		}
		if (StringUtils.isNotEmpty(className)) {
			sql.append(" and c.name like '%"+ className +"%'");
		}
		if (StringUtils.isNotEmpty(studentNum)) {
			sql.append(" and d.code = '"+ studentNum +"'");
		}
		if (StringUtils.isNotEmpty(studentName)) {
			sql.append(" and d.name like '%"+ studentName +"%'");
		}
		if (StringUtils.isNotEmpty(teacherName)) {
			sql.append(" and e.cnName like '%"+ teacherName +"%'");
		}
		if (StringUtils.isNotEmpty(grade)) {
			sql.append(" and f.code = '"+ grade +"'");
		}
		if (type != 5 && type != 6) {
			sql.append(")t where t.rank=1 ");
			if (type != 4) {
				sql.append(" order by t.loginDate desc");
			}
		} else if (type == 5){
			if (StringUtils.isNotEmpty(loginDateStart)) {
				sql.append(" and a.loginDate >= '"+ loginDateStart +"' ");
			}
			if (StringUtils.isNotEmpty(loginDateEnd)) {
				sql.append(" and a.loginDate <= '"+ loginDateEnd +"' ");
			}
			sql.append("group by a.studentId, d.code, d.name, c.name, f.name, e.name");
		} else {
			if (StringUtils.isNotEmpty(loginDateStart)) {
				sql.append(" and h.modifyDate >= '"+ loginDateStart +"' ");
			}
			if (StringUtils.isNotEmpty(loginDateEnd)) {
				sql.append(" and h.modifyDate <= '"+ loginDateEnd +"' ");
			}
			sql.append(" group by a.studentId, d.code, d.name, c.name, f.name, e.name");
		}
		return sql.toString();
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> exportExcel(int type) {
		String sql = getSql(type, null, null, null, null, null, null, null);
		return factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	public Map<String, Object> getLoginNum(String d1, String d2) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "select count(*) from (" + 
				"select distinct studentId from WA_StudentLoginLog where loginDate >= '"+ d1 +"' and loginDate <= '"+ d2 +"' " + 
				") a";
		map.put("登录人数", Integer.valueOf(factory.getCurrentSession().createSQLQuery(sql).list().get(0).toString()));
		sql = "select count(*) from (" + 
				"select distinct studentId from WA_ResultCard where hasDealed = 1 and modifyDate >= '"+ d1 +"' and modifyDate <= '"+ d2 +"' " + 
				") a";
		map.put("提交作业人数", Integer.valueOf(factory.getCurrentSession().createSQLQuery(sql).list().get(0).toString()));
		return map;
	}

}
