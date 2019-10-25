package com.teach.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.teach.Page;
import com.teach.dao.PersonFileDao;
import com.teach.model.TpPersonFile;
import com.teach.model.TpPersonFileSendRecord;
import com.util.CommonUtil;

@Repository
public class PersonFileDaoImpl extends BaseDao implements PersonFileDao {

	/**
	 * 得到个人文件记录
	 */
	@Override
	public TpPersonFile getPersonFile(int id) {
		return (TpPersonFile) find(TpPersonFile.class, id);
	}
	
	/**
	 * 得到个人文件发送记录
	 */
	@Override
	public TpPersonFileSendRecord getPersonFileSendRecord(int id) {
		return (TpPersonFileSendRecord) find(TpPersonFileSendRecord.class, id);
	}
	
	/**
	 * 得到文件发送记录（仅限当前服务器ip）
	 */
	@Override
	public TpPersonFileSendRecord getPersonFileSendRecord(int fid, String serverIp) {
		String hql = "select id, fId, serverId, serverAddr, status, sendTime, count from Tp_PersonFileSendRecord pfs where pfs.fid = "+ fid +" "
				+ "and pfs.serverId in (select id from tp_servers where serverIp = '"+ serverIp +"')";
		return (TpPersonFileSendRecord) getObjUseSql(hql);
	}
	
	/**
	 * 根据oaId判断是否是老师
	 */
	@Override
	public boolean checkIsTeacher(int oaId) {
		String sql = "select count(id) from EDU_TEACHER where oaId = " + oaId;
		return getCount(sql) > 0 ? true : false;
	}

	/**
	 * 得到个人文件分页列表
	 */
	@Override
	public Page getPersonFileList(int oaId, String search, Page page) {
		String sqlWhere = " where status <> -1 and oaId = "+ oaId;
		if (StringUtils.isNotEmpty(search)) {
			sqlWhere += " and fileName like '%"+ search +"%'";
		}
		
		String sql = "select id, oaId, fileType, fileName, fileSize, filePath, CONVERT(varchar(100), uploadTime, 23) uploadTime "
				+ "from TP_PERSONFILE "+ sqlWhere +" order by id desc";
		List<Map<String, Object>> list = getPageListUseSql(sql, page);
		sql = "select count(*) from TP_PERSONFILE"+ sqlWhere;
		page.setDataList(list);
		int totalNum = getCount(sql);
		int totaPage = (int) Math.ceil((double)totalNum/page.getPageSize());
		page.setTotalPage(totaPage);
		page.setHasPrePage(page.getCurrPage() > 1 ? true : false); //是否有上一页
		page.setHasNextPage(page.getCurrPage() < totaPage ? true : false); //是否有下一页
		return page;
	}

	@Override
	public int save(TpPersonFile personFile) {
		personFile = (TpPersonFile) saveOrUpdate(personFile);
		return personFile.getId();
	}
	
	@Override
	public TpPersonFileSendRecord save(TpPersonFileSendRecord record) {
		record = (TpPersonFileSendRecord) saveOrUpdate(record);
		return record;
	}

	@Override
	public void delete(int id) {
		delete(TpPersonFile.class, id);
	}
	
	@Override
	public List<Map<String, Object>> getAllCourse(int oaId) {
		String sql = "select ec.id,ec.name,ec.period,ec.grade,ec.subject,ec.cate,ep.termName from Edu_Course ec left join edu_period ep on ec.period=ep.id "
				+ "where ec.teacher = (select id from edu_teacher where oaId = "+ oaId +") and ec.status = 1 and DATEDIFF(day, ec.beginDate, getDate()) < 30";
		return getListUseSql(sql);
	}
	
	@Override
	public List<Map<String, Object>> getStudentList(int courseId) {
		String sql = "select id, name, (case when img is not null then '"+CommonUtil.APPCLIENTURL+"/AppTask'+img else '' end) img from edu_student where id in (select student from edu_studentClass where course = "+ courseId +" and status = 1)";
		return getListUseSql(sql);
	}
	
	@Override
	public void deleteRecord() {
		String sql = "delete tp_personFile where status = -1 and id not in (select distinct fid from tp_personFileSendRecord)";
		updateUseSql(sql);
	}

}
