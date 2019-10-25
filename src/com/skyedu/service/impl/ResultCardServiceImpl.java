package com.skyedu.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.skyedu.dao.impl.MistakeDAO;
import com.skyedu.dao.impl.ResultCardDAO;
import com.skyedu.dao.impl.WorkDAO;
import com.skyedu.model.Mistake;
import com.skyedu.model.ResultCard;
import com.skyedu.model.ResultCardInfo;
import com.skyedu.model.Work;
import com.skyedu.model.WorkInfo;
import com.skyedu.service.ResultCardService;

@Service
public class ResultCardServiceImpl implements ResultCardService{
	
	@Autowired
	private ResultCardDAO resultCardDAO;
	@Autowired
	private WorkDAO workDAO;
	@Autowired
	private MistakeDAO mistakeDAO;

	@Override
	public boolean publishWork(Work work, int studentId, Date openTime) {
		ResultCard resultCard = new ResultCard();
		resultCard.setCreateDate(new Date());
		resultCard.setHasDealed(0);
		resultCard.setModifyDate(new Date());
		resultCard.setOpenTime(openTime);
		resultCard.setScore(0.0);
		resultCard.setStudentId(studentId);
		resultCard.setTotalTime(0);
		resultCard.setWork(work);
		List<ResultCardInfo> resultCardInfoList = new ArrayList<ResultCardInfo>();
//		List<WorkInfo> workInfoList = workDAO.getWorkInfoList(workId, true);
		List<WorkInfo> workInfoList = work.getWorkInfoList();
		if (workInfoList == null || workInfoList.size()==0) {
			return false;
		}
		for (Iterator<WorkInfo> iterator = workInfoList.iterator(); iterator.hasNext();) {
			WorkInfo workInfo = (WorkInfo) iterator.next();
			ResultCardInfo resultCardInfo = new ResultCardInfo();
			resultCardInfo.setCreateDate(new Date());
			resultCardInfo.setModifyDate(new Date());
			resultCardInfo.setResultCard(resultCard);
			resultCardInfo.setWorkInfo(workInfo);
			resultCardInfo.setScore(0.0);
			resultCardInfoList.add(resultCardInfo);
		}
		resultCard.setResultCardInfoList(resultCardInfoList);
		Lock lock = new ReentrantLock();
		lock.lock();
		try {
			// 判断答题卡是否已存在，已存在不重复布置作业
			ResultCard resultCardBean = getResultCard(work.getiD(), studentId);
			if (resultCardBean == null) {
				resultCardDAO.saveResultCard(resultCard);
			} 
		} finally {
			lock.unlock();
		}
		return true;
	}

	@Override
	public ResultCard getResultCard(int workId, int studentId) {
		return resultCardDAO.getResultCard(workId, studentId);
	}

	@Override
	@Transactional
	public void removeWork(int workId, int studentId) {
		ResultCard resultCard = resultCardDAO.getResultCard(workId, studentId);
		if (resultCard!=null) {
			resultCardDAO.delResultCardInfo(resultCard.getiD());
			mistakeDAO.delMistake(resultCard.getiD());
			resultCardDAO.delResultCard(resultCard.getiD());
		}
	}

	@Override
	public void updateResultCard(ResultCard resultCard) {
		resultCardDAO.updateResultCard(resultCard);
	}

	@Override
	public ResultCard getResultCardBean(int resultCardId) {
		return resultCardDAO.getResultCardBean(resultCardId);
	}

	@Override
	public ResultCardInfo getResultCardInfoBean(int resultCardId, int questionId) {
		return resultCardDAO.getResultCardInfoBean(resultCardId, questionId);
	}

	@Override
	public void updateResultCardInfo(ResultCardInfo resultCardInfo) {
		resultCardDAO.updateResultCardInfo(resultCardInfo);
	}

	@Override
	public void saveMistake(Mistake mistake) {
		resultCardDAO.saveMistake(mistake);
	}

	@Override
	public List<Map<String, Object>> getResultCardListByLandC(int categoryId,
			int lessonId) {
		return resultCardDAO.getResultCardListByLandC(categoryId, lessonId);
	}
	
	@Override
	public List<Map<String, Object>> getResultCardListByWork(int workId) {
		return resultCardDAO.getResultCardListByWork(workId);
	}
	
	@Override
	public boolean existResultCardListByWork(int workId) {
		return resultCardDAO.existResultCardListByWork(workId);
	}

	@Override
	public Mistake getMistake(int workInfoId, int studentId) {
		return resultCardDAO.getMistake(workInfoId, studentId);
	}

	@Override
	public List<Map<String, Object>> getResultCardInfoList(int resultCardId) {
		List<Map<String, Object>> resultCardInfoList = resultCardDAO.getResultCardInfoList(resultCardId);
		for (Iterator<Map<String, Object>> iterator = resultCardInfoList.iterator(); iterator
				.hasNext();) {
			Map<String, Object> map = (Map<String, Object>) iterator.next();
			Object answer = map.get("answer");
			if (answer!=null && !"[]".equals((String)answer)) {
				List<Map<String,Object>> answers = (List<Map<String, Object>>) JSONObject.parse((String) answer);
				map.put("answerObj", answers);
			}
			int state = 2;
			List<Map<String, Object>> childResultCardInfoList = resultCardDAO.getChildResultCardInfoList((Integer)map.get("qId"), resultCardId);
			for (Iterator<Map<String, Object>> iterator2 = childResultCardInfoList.iterator(); iterator2
					.hasNext();) {
				Map<String, Object> map2 = (Map<String, Object>) iterator2
						.next();
				Object answer2 = map2.get("answer");
				if (answer2!=null && !"[]".equals((String)answer2)) {
					List<Map<String,Object>> answers2 = (List<Map<String, Object>>) JSONObject.parse((String) answer2);
					map2.put("answerObj", answers2);
				}
				if ((Integer)map2.get("state")!=2) {
					state = 0;
				}
			}
			map.put("state", state);
			map.put("childResultCardInfoList", childResultCardInfoList);
		}
		return resultCardInfoList;
	}

	@Override
	public List<Map<String, Object>> getResultCardListByLandS(int studentId,
			int lessonId) {
		return resultCardDAO.getResultCardListByLandS(studentId, lessonId);
	}

	@Override
	public List<Map<String, Object>> getResultCardListWeek(
			Map<String, Object> condition) {
		return resultCardDAO.getResultCardListWeek(condition);
	}

	@Override
	public List<Map<String, Object>> getClassScoreAndCorrect(
			Map<String, Object> condition) {
		return resultCardDAO.getClassScoreAndCorrect(condition);
	}

	@Override
	public List<Map<String, Object>> getLessonResultCard(int studentId,
			int lessonId) {
		return resultCardDAO.getLessonResultCard(studentId, lessonId);
	}

	@Override
	public List<ResultCard> getResultCardList(int lessonId, int studentId) {
		return resultCardDAO.getResultCardList(lessonId, studentId);
	}
	
	@Override
	public List<ResultCard> getResultCardList(String period, String grade,
			String subject, String cate,int studentId) {
		return resultCardDAO.getResultCardList(period, grade, subject, cate, studentId);
	}

	@Override
	public void delResultCardInfoByWork(int workId) {
		resultCardDAO.delResultCardInfoByWork(workId);
	}

	@Override
	public void delResultCardByWork(int workId) {
		resultCardDAO.delResultCardByWork(workId);
	}
}
