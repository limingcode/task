package com.teach.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.teach.Page;
import com.teach.dao.StatisticsDao;
import com.teach.service.StatisticsService;

@Service
public class StatisticsServiceImpl implements StatisticsService {
	@Resource
	private StatisticsDao statisticsDao;

	@Override
	public Page getLessonUploadStatus(Page page, String subject, String period, String grade, String cate) {
		page = statisticsDao.getLessonUploadStatus(page, subject, period, grade, cate);
		List<Map<String, Object>> list = page.getDataList();
		if(list == null || list.size() == 0) {
			return page;
		}
		int maxLesson = 0;
		Map<String, Object> tempMap = new HashMap<String, Object>();
		List<Map<String, Object>> tempList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> returnList = new ArrayList<Map<String,Object>>();
		int i = 0;
		for (Map<String, Object> map : list) {
			Map<String , Object> statusMap = new HashMap<String, Object>();
			if(tempMap == null ||tempMap.size() == 0) {
				tempMap = map;
				statusMap.put("course", map.get("course"));
				statusMap.put("lesson", map.get("lesson"));
				statusMap.put("memoir", map.get("memoir"));
				tempList.add(statusMap);
				tempMap.remove("course");
				tempMap.remove("lesson");
				tempMap.remove("memoir");
				tempMap.remove("sortNo");
				i++;
				continue;
			}
			if (StringUtils.equals(String.valueOf(map.get("epId")), String.valueOf(tempMap.get("epId"))) &&  //学期
				StringUtils.equals(String.valueOf(map.get("esId")), String.valueOf(tempMap.get("esId"))) && //科目
				StringUtils.equals(String.valueOf(map.get("egId")), String.valueOf(tempMap.get("egId"))) && //年级
				StringUtils.equals(String.valueOf(map.get("ecId")), String.valueOf(tempMap.get("ecId")))) {//层次 
				statusMap.put("course", map.get("course"));
				statusMap.put("lesson", map.get("lesson"));
				statusMap.put("memoir", map.get("memoir"));
				tempList.add(statusMap);
				i++;
			} else {
				tempMap.put("status", tempList);
				returnList.add(tempMap);
				maxLesson = Math.max(maxLesson, i);
				tempList = new ArrayList<Map<String,Object>>();
				tempMap = map;
				statusMap.put("course", map.get("course"));
				statusMap.put("lesson", map.get("lesson"));
				statusMap.put("memoir", map.get("memoir"));
				tempList.add(statusMap);
				tempMap.remove("course");
				tempMap.remove("lesson");
				tempMap.remove("memoir");
				tempMap.remove("sortNo");
				i = 1;
			}
		}
		maxLesson = Math.max(maxLesson, i);
		tempMap.put("status", tempList);
		returnList.add(tempMap);
		page.setDataList(returnList);
		page.setMaxLesson(maxLesson);
		return page;
	}
	
	@Override
	public List<Map<String, Object>> getLessonUploadStatus(String subject, String period){
		List<Map<String, Object>> list = statisticsDao.getLessonUploadStatus(subject, period);
		if(list == null || list.size() == 0) {
			return list;
		}
		Map<String, Object> tempMap = new HashMap<String, Object>();
		List<Map<String, Object>> statusList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> returnList = new ArrayList<Map<String,Object>>();
		for (Map<String, Object> map : list) {
			Map<String , Object> statusMap = new HashMap<String, Object>();
			if (tempMap == null || tempMap.size() == 0) {
				statusMap.put("course", map.get("course"));
				statusMap.put("caseStatus", map.get("caseStatus"));
				statusMap.put("lesson", map.get("lesson"));
				statusMap.put("memoir", map.get("memoir"));
				statusMap.put("egCode", map.get("egCode"));
				statusList.add(statusMap);
				map.remove("course");
				map.remove("caseStatus");
				map.remove("lesson");
				map.remove("memoir");
				map.remove("egCode");
				tempMap = map;
				continue;
			}
			if (StringUtils.equals(String.valueOf(map.get("sortNo")), String.valueOf(tempMap.get("sortNo"))) &&
				StringUtils.equals(String.valueOf(map.get("ecCode")), String.valueOf(tempMap.get("ecCode")))) {
				statusMap.put("course", map.get("course"));
				statusMap.put("caseStatus", map.get("caseStatus"));
				statusMap.put("lesson", map.get("lesson"));
				statusMap.put("memoir", map.get("memoir"));
				statusMap.put("egCode", map.get("egCode"));
				statusList.add(statusMap);
			} else {
				tempMap.put("status", statusList);
				returnList.add(tempMap);
				statusList = new ArrayList<Map<String,Object>>();
				tempMap = map;
				statusMap.put("course", map.get("course"));
				statusMap.put("caseStatus", map.get("caseStatus"));
				statusMap.put("lesson", map.get("lesson"));
				statusMap.put("memoir", map.get("memoir"));
				statusMap.put("egCode", map.get("egCode"));
				statusList.add(statusMap);
				tempMap.remove("course");
				map.remove("caseStatus");
				tempMap.remove("lesson");
				tempMap.remove("memoir");
				tempMap.remove("egCode");
			}
		}
		tempMap.put("status", statusList);
		returnList.add(tempMap);
		return returnList;
	}
	

}
