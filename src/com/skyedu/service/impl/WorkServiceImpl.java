package com.skyedu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skyedu.dao.impl.ResultCardDAO;
import com.skyedu.dao.impl.WorkDAO;
import com.skyedu.model.Work;
import com.skyedu.model.WorkInfo;
import com.skyedu.service.WorkService;

@Service
public class WorkServiceImpl implements WorkService {

	@Autowired
	private WorkDAO workDAO;
	@Autowired
	private ResultCardDAO resultCardDAO;

	@Override
	public Map<String, Object> getWork(int workId) {
		return workDAO.getWork(workId);
	}

	@Override
	public void saveWork(Work work) {
		workDAO.saveWork(work);
	}

	@Override
	public void delWork(int workId) {
		workDAO.delWork(workId);
	}

	@Override
	public void delWorkInfo(int workInfoId) {
		workDAO.delWorkInfo(workInfoId);
	}

	@Override
	public List<Map<String, Object>> getWorks(int lessonId, int categoryId) {
		return workDAO.getWorks(lessonId, categoryId);
	}

	@Override
	public void saveWorkInfo(WorkInfo workInfo) {
		workDAO.saveWorkInfo(workInfo);
	}

	@Override
	public List<WorkInfo> getWorkInfoList(int workId, boolean flag) {
		return workDAO.getWorkInfoList(workId, flag);
	}

	@Override
	public WorkInfo getWorkInfo(int workId, int questionId) {
		return workDAO.getWorkInfo(workId, questionId);
	}

	@Override
	public void updateWork(Work work) {
		// TODO Auto-generated method stub
		workDAO.updateWork(work);
	}
	
	@Override
	public boolean existResultCardByWork(Work work) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultCardListByWork = resultCardDAO.getResultCardListByWork(work.getiD());
		if (resultCardListByWork==null || resultCardListByWork.size()==0) {
			workDAO.updateWork(work);
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Work getWorkBean(int workId) {
		// TODO Auto-generated method stub
		return workDAO.getWorkBean(workId);
	}

	@Override
	public List<Work> getWorksByQ(int questionId) {
		// TODO Auto-generated method stub
		return workDAO.getWorksByQ(questionId);
	}

	@Override
	public List<Map<String, Object>> workCorrectStatistics(int type, String subject, String period, String grade, String gradeId) {
		List<Map<String, Object>> list = workDAO.workCorrectStatistics(type, subject, period, grade, gradeId);
		if (type == 3) {
			List<Map<String, Object>> newList = new ArrayList<Map<String,Object>>();
			Map<String, Object> tempMap = new HashMap<String, Object>();
			String sortNo = null;
			for (Map<String, Object> map : list) {
				if (StringUtils.isEmpty(sortNo)) {
					sortNo = String.valueOf(map.get("sortNo"));
					if (map.get("category") != null) {
						tempMap.put(String.valueOf(map.get("category")), map);
					}
				} else if (StringUtils.equals(sortNo, String.valueOf(map.get("sortNo")))) {
					if (map.get("category") != null) {
						tempMap.put(String.valueOf(map.get("category")), map);
					}
				} else {
					newList.add(tempMap);
					tempMap = new HashMap<String, Object>();
					sortNo = String.valueOf(map.get("sortNo"));
					if (map.get("category") != null) {
						tempMap.put(String.valueOf(map.get("category")), map);
					}
				}
			}
			newList.add(tempMap);
			return newList;
		}
		return list;
	}
	
	@Override
	public List<Map<String, Object>> studentWorkCorrectStatis(int courseId, String studentId) {
		return workDAO.studentWorkCorrectStatis(courseId, studentId);
	}

}
