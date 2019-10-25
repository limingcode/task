package com.skyedu.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.skyedu.dao.impl.CourseDAO;
import com.skyedu.dao.impl.LessonDAO;
import com.skyedu.dao.impl.ResultCardDAO;
import com.skyedu.model.LessonBean;
import com.skyedu.model.ResultCard;
import com.skyedu.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService{

	@Autowired
	private CourseDAO courseDao;
	@Autowired
	private LessonDAO lessonDao;
	@Autowired
	private ResultCardDAO resultCardDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> getHierarchyListNew(int studentId) {
		// TODO Auto-generated method stub
		Map<String,Map<String, Object>> lessonBeanMap = new HashMap<String, Map<String, Object>>();
		List<ResultCard> resultCardList = resultCardDao.getResultCardList(studentId);
		for (Iterator<ResultCard> iterator = resultCardList.iterator(); iterator.hasNext();) {
			ResultCard resultCard = (ResultCard) iterator.next();
			LessonBean lesson = resultCard.getWork().getLesson();
			Map<String, Object> lessonMap = lessonBeanMap.get(lesson.getiD().toString());
			if (lessonMap!=null) {
				List<ResultCard> object = (List<ResultCard>) lessonMap.get("resultCardList");
				object.add(resultCard);
			} else {
				lessonMap = new HashMap<String, Object>();
				List<ResultCard> resultCardList1 = new ArrayList<ResultCard>();
				resultCardList1.add(resultCard);
				lessonMap.put("resultCardList", resultCardList1);
				lessonBeanMap.put(lesson.getiD().toString(), lessonMap);
			}
		}
		
		List<Map<String, Object>> courseList = courseDao.getCourseList(studentId);
		for (Iterator<Map<String, Object>> iterator = courseList.iterator(); iterator.hasNext();) {
			Map<String,Object> course = (Map<String,Object>) iterator.next();
			String grade = (String) course.get("grade");
			String subject = (String) course.get("subject");
			String cate = (String) course.get("cate");
			Integer period = (Integer) course.get("period");
			List<Map<String,Object>> lessonList = lessonDao.getLessonList(period.toString(), grade, subject, cate);
			for (Iterator<Map<String, Object>> iterator2 = lessonList.iterator(); iterator2
					.hasNext();) {
				Map<String, Object> map = (Map<String, Object>) iterator2
						.next();
				Integer lessonId = (Integer) map.get("iD");
				map.put("modifyDate", new Date(0));
				Map<String,Object> lessonMap = lessonBeanMap.get(lessonId.toString());
				int unDealed = 0;
				Double starRCorrect = 0.00;
				List<ResultCard> resultCardList1 = new ArrayList<ResultCard>();
				if (lessonMap!=null) {
					resultCardList1 = (List<ResultCard>) lessonMap.get("resultCardList");
					for (Iterator<ResultCard> iterator3 = resultCardList1.iterator(); iterator3.hasNext();) {
						Date lessonModifyDate = (Date) map.get("modifyDate");
						ResultCard resultCard = iterator3.next();
						Integer hasDealed = resultCard.getHasDealed();
						Date resultCardModifyDate = resultCard.getCreateDate();
						if (resultCardModifyDate.getTime() > lessonModifyDate.getTime()) {
							map.put("modifyDate", resultCardModifyDate);
						}
						if (0 == hasDealed) {
							unDealed++;
						}
						String correct = resultCard.getCorrect() == null ? "0" : resultCard.getCorrect();
						if (!StringUtils.isEmpty(correct)) {
							Double RCorrect = Double.parseDouble(correct);
							starRCorrect = starRCorrect + RCorrect;
						}
					} 
				}
				int starCount = 0;
				double starCorrect = starRCorrect/resultCardList1.size();
				if (starCorrect<=0.50) {
					
				}else if(starCorrect<=0.70){
					starCount = 1;
				}else if(starCorrect<=0.90){
					starCount = 2;
				}else if(starCorrect>0.90){
					starCount = 3;
				}
				Date modifyDate = (Date) map.get("modifyDate");
				long time = modifyDate.getTime()+resultCardList1.size();
				map.put("modifyDate", new Date(time));
				map.put("unDealed", unDealed);
				map.put("count", resultCardList1.size());
				map.put("starCount", starCount);
			}
			course.put("lessonList", lessonList);
		}
		return courseList;
	}

	private Map<String, Object> forLessonMap( LessonBean lessonBean) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("iD", lessonBean.getiD());
		map.put("modifyDate", new Date(0));
		map.put("sortNo", lessonBean.getSortNo());
		map.put("openTime", lessonBean.getOpenTime());
		map.put("hierarchy", lessonBean.getHierarchy().getiD());
		map.put("createDate", lessonBean.getCreateDate());
		map.put("brief", lessonBean.getBrief());
		return map;
	}
	
	@Override
	public List<Map<String,Object>> getHierarchyList(int studentId) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> courseList = courseDao.getCourseList(studentId);
		//List<Map<String, Object>> hierarchyList = baseService.myHierarchys(studentId);
		for (Iterator<Map<String, Object>> iterator = courseList.iterator(); iterator.hasNext();) {
			Map<String,Object> course = (Map<String,Object>) iterator.next();
			String grade = (String) course.get("grade");
			String subject = (String) course.get("subject");
			String cate = (String) course.get("cate");
			Integer period = (Integer) course.get("period");
			List<Map<String,Object>> lessonList = lessonDao.getLessonList(period.toString(), grade, subject, cate);
			for (Iterator<Map<String, Object>> iterator2 = lessonList.iterator(); iterator2
					.hasNext();) {
				Map<String, Object> map = (Map<String, Object>) iterator2
						.next();
				int lessonId = (Integer) map.get("iD");
				map.put("modifyDate", new Date(0));
				List<Map<String,Object>> resultCardList = resultCardDao.getResultCards(lessonId, studentId);
				int unDealed = 0;
				Double starRCorrect = 0.00;
				for (Iterator<Map<String,Object>> iterator3 = resultCardList.iterator(); iterator3
						.hasNext();) {
					Date lessonModifyDate = (Date) map.get("modifyDate");
					Map<String,Object> resultCard = (Map<String,Object>) iterator3.next();
					Integer hasDealed = (Integer) resultCard.get("hasDealed");
					Date resultCardModifyDate = (Date) resultCard.get("createDate");
					if (resultCardModifyDate.getTime()>lessonModifyDate.getTime()) {
						map.put("modifyDate", resultCardModifyDate);
					}
					if (0==hasDealed) {
						unDealed ++;
					}
					String correct = resultCard.get("correct")==null?"0":((Double)resultCard.get("correct")).toString();
					if (!StringUtils.isEmpty(correct)) {
						Double RCorrect = Double.parseDouble(correct);
						starRCorrect = starRCorrect + RCorrect;
					}
				}
				int starCount = 0;
				double starCorrect = starRCorrect/resultCardList.size();
				if (starCorrect<=0.50) {
					
				}else if(starCorrect<=0.70){
					starCount = 1;
				}else if(starCorrect<=0.90){
					starCount = 2;
				}else if(starCorrect>0.90){
					starCount = 3;
				}
				Date modifyDate = (Date) map.get("modifyDate");
				long time = modifyDate.getTime()+resultCardList.size();
				map.put("modifyDate", new Date(time));
				map.put("unDealed", unDealed);
				map.put("count", resultCardList.size());
				map.put("starCount", starCount);
			}
			course.put("lessonList", lessonList);
		}
		return courseList;
	}

	@Override
	public List<Map<String, Object>> getStudentList(int courseId,boolean flag) {
		// TODO Auto-generated method stub
		return courseDao.getStudentList(courseId,flag);
	}

	@Override
	public List<Map<String, Object>> searchStudent(String studentName) {
		// TODO Auto-generated method stub
		return courseDao.searchStudent(studentName);
	}

	@Override
	public List<Map<String, Object>> getCourseInfoList(int studentId) {
		// TODO Auto-generated method stub
		return courseDao.getCourseInfoList(studentId);
	}

	@Override
	public List<Map<String, Object>> getCourseList() {
		// TODO Auto-generated method stub
		return courseDao.getCourseList();
	}

	@Override
	public void setCourseTime(String courseTime, int courseId) {
		// TODO Auto-generated method stub
		courseDao.setCourseTime(courseTime, courseId);
	}

}
