package com.skyedu.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.util.HbmDAOUtil;

@Repository
public class WxDao extends HbmDAOUtil{

	public List<Map<String,Object>> getWxOpenIDs(int pageNo){
		String sql = "select t.studentId,b.useropenId,b.studentname from (select d.name,d.code, a.studentId,a.loginDate,a.message,c.name courseName,e.cnName teacherName,f.name gradeName, row_number() over (partition by a.studentId order by a.loginDate desc)rank from  WA_StudentLoginLog a LEFT JOIN Edu_StudentClass b on a.studentId=b.student LEFT JOIN Edu_Course c on b.course=c.id LEFT JOIN Edu_Student d on a.studentId=d.id LEFT JOIN Edu_Teacher e on e.id=c.teacher LEFT JOIN Edu_Grade f on f.code=c.grade left join Edu_Period g on g.id=c.period where c.cate in (1,2,7) and b.status=1 and c.subject='A01' and g.status=2 and d.cityId = 1 and not exists (select 1 from WA_StudentLoginLog where a.studentId = studentId and message like '%1.2.38%'))t LEFT JOIN [172.18.163.168].SkyWX.dbo.Wx_Users b on b.studentcode=t.code where t.rank=1 order by t.studentId";
		Query q = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult((pageNo-1)*100).setMaxResults(100);
		return q.list();
	}
}
