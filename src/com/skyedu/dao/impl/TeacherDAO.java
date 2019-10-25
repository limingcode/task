package com.skyedu.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.skyedu.model.EduTeacher;
import com.util.HbmDAOUtil;
import com.util.StringUtil;

@Repository
@SuppressWarnings("unchecked")
public class TeacherDAO extends HbmDAOUtil {
	
	/**
	 * 获取员工列表
	 * @param name名字或者是电话号码
	 * @param sex性别
	 * @param dept部门
	 * @param job职位
	 * @param position岗位
	 * @param education学历
	 * @param skill技能
	 * @param certificate证书
	 * @param squad小组
	 * @param largerAllotIs 查询权重比此大的人
	 * @return
	 *//*
	public List<Map<String, Object>> userList(Integer page, Integer maxResults,String name, Integer sex, Integer dept, Integer job,
			Integer position, Integer education, Integer skill, Integer certificate, Integer squad, Integer largerAllotIs) {
		Query query = factory.getCurrentSession().createSQLQuery(userQl(name, sex, dept, job, position, education, skill, certificate, squad, largerAllotIs));
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if(maxResults!=null&&maxResults>0){
			query.setFirstResult((page-1)*maxResults);
			query.setMaxResults(maxResults);
		}
		return query.list();
	}

	public int userCount(String name, Integer sex, Integer dept, Integer job,
			Integer position, Integer education, Integer skill, Integer certificate, Integer squad, Integer largerAllotIs) {
		String sql = getCountQL1(userQl(name, sex, dept, job, position, education, skill, certificate, squad, largerAllotIs));
		Query q = factory.getCurrentSession().createSQLQuery(sql);
		return Integer.valueOf(q.uniqueResult().toString());
	}
	
	private String userQl(String name, Integer sex, Integer dept, Integer job,
			Integer position, Integer education, Integer skill, Integer certificate, Integer squad, Integer largerAllotIs){
		StringBuffer sql = new StringBuffer("select em.*,dId,dName,jId,jName,pId,pName,eId,eName,sId,sName,cId,cName,sqId,sqName,jtId,jtName,acc.*,rank.orderAll,rank.orderBfb,rank.orderName");
		sql.append(" ,(select sum(score) from SOG_Score where type=1 and employee=em.id) setScore");
		sql.append(" from SOG_Employee em left join SOG_Dept d on em.dept=d.did");
		sql.append(" left join SOG_Job j on em.job=j.jid left join SOG_Position p on em.position=p.pid");
		sql.append(" left join SOG_Education e on em.education=e.eid left join SOG_Skill s on em.skill=s.sid");
		sql.append(" left join SOG_Certificate c on em.certificate=c.cid left join SOG_Squad sq on em.squad=sq.sqid");
		sql.append(" left join SOG_JobTitle jt on em.jobTitle=jt.jtId");
		sql.append(" left join SOG_AccountUser acc on em.id=acc.employee");
		sql.append(" left join (select * from rank_all()) rank on em.id=rank.id");
		sql.append(" where status=1");
		if(!StringUtil.isEmpty(name))//姓名或者是电话
			sql.append(" and (em.name like'%").append(name).append("%' or em.tel like'%").append(name).append("%')");
		if(sex!=null&&sex!=0)
			sql.append(" and em.sex="+sex);
		if(dept!=null&&dept!=0)
			sql.append(" and em.dept="+dept);
		if(job!=null&&job!=0)
			sql.append(" and em.job="+job);
		if(position!=null&&position!=0)
			sql.append(" and em.position="+position);
		if(education!=null&&education!=0)
			sql.append(" and em.education="+education);
		if(skill!=null&&skill!=0)
			sql.append(" and em.skill="+skill);
		if(certificate!=null&&certificate!=0)
			sql.append(" and em.certificate="+certificate);
		if(squad!=null&&squad!=0)
			sql.append(" and em.squad="+squad);
		if(largerAllotIs!=null)
			sql.append(" and em.allot_is>"+largerAllotIs);
		sql.append(" order by em.name asc");
		return sql.toString();
	}
	
	public void upd(Integer allot, Integer id, Integer dept, Integer job,
			Integer position, Integer education, Integer squad, Integer jobTitle, Integer userType){
		String sql = "update SOG_Employee set dept="+dept+",job="+job+",position="+position+",education="+education+",squad="+squad+",allot_is="+allot+",jobTitle="+jobTitle+",userType="+userType+" where id="+id;
		factory.getCurrentSession().createSQLQuery(sql).executeUpdate();
	}
	public void updCertificateUser(int employee, String certificate){
		factory.getCurrentSession().createSQLQuery("delete from SOG_CertificateUser where employee="+employee).executeUpdate();
		if(!StringUtil.isEmpty(certificate)){
			factory.getCurrentSession().createSQLQuery("insert into SOG_CertificateUser select "+employee+",string from dbo.fn_StrSplit('"+certificate+"',',')").executeUpdate();
		}
	}
	public void updSkillUser(int employee, String skill){
		factory.getCurrentSession().createSQLQuery("delete from SOG_SkillUser where employee="+employee).executeUpdate();
		if(!StringUtil.isEmpty(skill)){
			factory.getCurrentSession().createSQLQuery("insert into SOG_SkillUser select "+employee+",string from dbo.fn_StrSplit('"+skill+"',',')").executeUpdate();
		}
	}
	
	public List<Map<String, Object>> skillUserList(int employee){
		Query query = factory.getCurrentSession().createSQLQuery("select b.* from SOG_SkillUser a left join SOG_Skill b on a.skill=b.sId where a.employee="+employee);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
	

	public List<Map<String, Object>> certificateUserList(int employee){
		Query query = factory.getCurrentSession().createSQLQuery("select b.* from SOG_CertificateUser a left join SOG_Certificate b on a.certificate=b.cId where a.employee="+employee);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
	
	*//**
	 * 查询
	 * @param id
	 * @return
	 *//*
	public EduTeacher findById(Integer id) {
		Criteria criteria = factory.getCurrentSession().createCriteria(EduTeacher.class);
		criteria.add(Restrictions.eq("id", id));
		return (EduTeacher) criteria.uniqueResult();
	}
	
	*//**
	 * 查询
	 * @param oa_id
	 * @return
	 *//*
	public EduTeacher findByOaId(Integer id) {
		Criteria criteria = factory.getCurrentSession().createCriteria(EduTeacher.class);
		criteria.add(Restrictions.eq("oaId", id));
		return (EduTeacher) criteria.uniqueResult();
	}
	
	*//**
	 * 修改二维码信息
	 * @param id
	 * @param qrcode_ticket
	 * @param qrcode_ticket_expire
	 * @return
	 *//*
	public void updateQrcodeTicket(Integer id, String qrcodeTicket, Date qrcodeTicketExpire) {
		Query q = factory.getCurrentSession().createSQLQuery("update SOG_Employee set qrcode_ticket = :ticket,qrcode_ticket_expire = :expire where id = :id");
		q.setParameter("ticket", qrcodeTicket);
		q.setParameter("expire", qrcodeTicketExpire);
		q.setParameter("id", id);
		q.executeUpdate();
	}
	
	*//**
	 * 修改openid
	 * @param id
	 * @param openid
	 * @return
	 *//*
	public void updateOpenid(Integer id, String openid) {
		Query q = factory.getCurrentSession().createSQLQuery("update SOG_Employee set wx_openid = :openid,wx_create_time = :time where id = :id");
		q.setParameter("openid", openid);
		q.setParameter("time", new Date());
		q.setParameter("id", id);
		q.executeUpdate();
	}
	
	*//**
	 * 查询
	 * @param openid
	 * @return
	 *//*
	public EduTeacher findByOpenid(String openid) {
		Criteria criteria = factory.getCurrentSession().createCriteria(EduTeacher.class);
		criteria.add(Restrictions.eq("wxOpenid", openid));
		return (EduTeacher) criteria.uniqueResult();
	}
	
	*//**
	 * 查询
	 * @param openid
	 * @return
	 *//*
	public EduTeacher findByOaid(Integer oaId) {
		Criteria criteria = factory.getCurrentSession().createCriteria(EduTeacher.class);
		criteria.add(Restrictions.eq("oaId", oaId));
		return (EduTeacher) criteria.uniqueResult();
	}

	*//**
	 * 修改用户头像
	 * @param id
	 * @param portrait
	 * @return
	 *//*
	public void updatePortrait(Integer id, String portrait) {
		Query q = factory.getCurrentSession().createSQLQuery("update SOG_Employee set portrait = :portrait where id = :id");
		q.setParameter("portrait", portrait);
		q.setParameter("id", id);
		q.executeUpdate();
	}
	
	*//**
	 * 修改用户封面
	 * @param id
	 * @param portrait
	 * @return
	 *//*
	public void updateCoverImage(Integer id, String coverImage) {
		Query q = factory.getCurrentSession().createSQLQuery("update SOG_Employee set cover_image = :converImage where id = :id");
		q.setParameter("converImage", coverImage);
		q.setParameter("id", id);
		q.executeUpdate();
	}*/
}
