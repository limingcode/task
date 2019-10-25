package com.skyedu.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skyedu.dao.impl.MistakeDAO;
import com.skyedu.model.Mistake;
import com.skyedu.service.MistakeService;

@Service
public class MistakeServiceImpl implements MistakeService{

	@Autowired
	private MistakeDAO mistakeDAO;
	
	@Override
	public void saveMistake(Mistake mistake) {
		mistakeDAO.saveMistakes(mistake);
	}

	@Override
	public List<Map<String, Object>> getMistakeList(Map<String,Object> condition) {
		List<Map<String, Object>> mistakeList = mistakeDAO.getMistakeList(condition);
		/*for (Iterator<Map<String, Object>> iterator = mistakeList.iterator(); iterator.hasNext();) {
			Map<String, Object> map = (Map<String, Object>) iterator.next();
			int questionId = (Integer) map.get("questionId");
			int lessonId = (Integer) map.get("lessonId");
			int studentId = (Integer) condition.get("studentId");
			List<Map<String, Object>> resultList = questionDAO.getResultList(questionId);
			map.put("resultList", resultList);
			Map<String, Object> answer = resultCardDAO.getAnswer(lessonId, questionId, studentId);
			map.put("answer", answer.get("answer"));
			List<Map<String, Object>> childList = questionDAO.childList(questionId);
			if (childList!=null && childList.size()>0) {
				for (Iterator<Map<String, Object>> iterator2 = childList.iterator(); iterator2
						.hasNext();) {
					Map<String, Object> child = (Map<String, Object>) iterator2
							.next();
					List<Map<String, Object>> cResultList = questionDAO.getResultList((Integer) child.get("id"));
					child.put("resultList", cResultList);
					Map<String, Object> cAnswer = resultCardDAO.getAnswer(lessonId, questionId, studentId);
					child.put("answer", cAnswer.get("answer"));
				}
			}
			map.put("childQuestionList", childList);
		}*/
		return mistakeList;
	}

	@Override
	public void delMistakes(Mistake mistake) {
		mistakeDAO.removeMistakes(mistake);
	}

	@Override
	public void delMistakeByWork(int workId) {
		mistakeDAO.delMistakeByWork(workId);
	}

	@Override
	public void updateMistake(Mistake mistake) {
		// TODO Auto-generated method stub
		mistakeDAO.updateMistake(mistake);
	}

}
