package com.skyedu.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.skyedu.dao.impl.BaseDAO;
import com.skyedu.dao.impl.LessonDAO;
import com.skyedu.dao.impl.QuestionDAO;
import com.skyedu.dao.impl.ResultCardDAO;
import com.skyedu.dao.impl.WorkDAO;
import com.skyedu.model.Lesson;
import com.skyedu.model.LessonBean;
import com.skyedu.model.Question;
import com.skyedu.model.Result;
import com.skyedu.model.ResultCard;
import com.skyedu.model.ResultCardInfo;
import com.skyedu.model.Work;
import com.skyedu.model.WorkInfo;
import com.skyedu.service.LessonService;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

@Service
public class LessonServiceImpl implements LessonService {

	@Autowired
	private WorkDAO workDAO;
	@Autowired
	private ResultCardDAO resultCardDAO;
	@Autowired
	private LessonDAO lessonDAO;
	@Autowired
	private QuestionDAO questionDAO;
	@Autowired
	private BaseDAO baseDAO;

	@Override
	public Lesson getLessonWorks(int lessonId) {
		// TODO Auto-generated method stub
		Lesson lesson = new Lesson();
		List<Work> waWorkList = workDAO.getWorkList(lessonId, 0);
		lesson.setWorkList(waWorkList);
		return lesson;
	}

	@Override
	public List<Map<String,Object>> getLessonResultCardList(int lessonId, int studentId) {
		// TODO Auto-generated method stub
		List<ResultCard> resultCardList = resultCardDAO.getResultCardList(
				lessonId, studentId);

		List<Map<String, Object>> resultCardList1 = new ArrayList<Map<String,Object>>();
		
		for (Iterator<ResultCard> iterator = resultCardList.iterator(); iterator
				.hasNext();) {
			// 计数，标准题个数
			Integer count = 0;
			ResultCard resultCard = iterator.next();
			
			Map<String, Object> resutlCard1 = forMapResutlCard(resultCard);
			resultCardList1.add(resutlCard1);

			List<ResultCardInfo> resultCardInfoList = resultCard.getResultCardInfoList();
			List<Map<String,Object>> resultCardInfoList1 = new ArrayList<Map<String,Object>>();
			// 循环遍历填充数据
			for (Iterator<ResultCardInfo> iterator2 = resultCardInfoList
					.iterator(); iterator2.hasNext();) {
				ResultCardInfo resultCardInfo = iterator2.next();
				if (resultCardInfo.getWorkInfo().getQuestion().getQuestionType()!=0) {
					count++;
				}
				if (resultCardInfo.getWorkInfo().getQuestion().getParentQuestion()==null) {
					resultCardInfoList1.add(forMapResultCardInfo(null, resultCardInfo,resultCardInfoList));							
				} 

			}
			resutlCard1.put("resultCardInfoList", resultCardInfoList1);
			resutlCard1.put("count",count);
		}
		return resultCardList1;
	}

	private Map<String,Object> forMapResultCardInfo(Map<String,Object> parentQuestion,ResultCardInfo resultCardInfo,List<ResultCardInfo> resultCardInfos) {
		Map<String,Object> resultCardInfo1 = new HashMap<String,Object>();
		resultCardInfo1.put("createDate", resultCardInfo.getCreateDate());
		resultCardInfo1.put("iD", resultCardInfo.getiD());
		resultCardInfo1.put("modifyDate", resultCardInfo.getModifyDate());
		resultCardInfo1.put("score", resultCardInfo.getScore());
		resultCardInfo1.put("state", resultCardInfo.getState());
		resultCardInfo1.put("answer", StringUtils.isEmpty(resultCardInfo.getAnswer())?null:JSONObject.parse(resultCardInfo.getAnswer()));
		WorkInfo workInfo = resultCardInfo.getWorkInfo();
		resultCardInfo1.put("workInfo", forMapWorkInfo(parentQuestion,workInfo,resultCardInfos,resultCardInfo));
		return resultCardInfo1;
	}

	private Map<String,Object> forMapWorkInfo(Map<String,Object> parentQuestion,WorkInfo workInfo,List<ResultCardInfo> resultCardInfos,ResultCardInfo resultCardInfo) {
		Map<String,Object> workInfo1 = new HashMap<String,Object>();
		workInfo1.put("createDate", workInfo.getCreateDate());
		workInfo1.put("iD", workInfo.getiD());
		workInfo1.put("modifyDate", workInfo.getModifyDate());
		workInfo1.put("sortNo", workInfo.getSortNo());
		Question question = workInfo.getQuestion();
		Map<String, Object> question1 = forMapQuestion(parentQuestion,question,resultCardInfos,resultCardInfo);
		workInfo1.put("question", question1);
		return workInfo1;
	}

	private Map<String, Object> forMapQuestion(Map<String,Object> parentQuestion,Question question,List<ResultCardInfo> resultCardInfos,ResultCardInfo resultCardIn) {
		Map<String,Object> question1 = new HashMap<String, Object>();
		question1.put("analyzation", question.getAnalyzation());
		question1.put("answerLength", question.getAnswerLength());
		question1.put("brief", question.getBrief());
		question1.put("createDate", question.getCreateDate());
//		question1.put("hierarchy", question.getHierarchy().getiD());
		question1.put("iD", question.getiD());
		question1.put("isVoice", question.getIsVoice());
		question1.put("modifyDate", question.getModifyDate());
		question1.put("questionType", question.getQuestionType());
		List<Result> resultList = question.getResultList();
		List<Map<String, Object>> resultList1 = forMapResult(resultList);
		question1.put("resultList", resultList1);
		question1.put("score", question.getScore());
		question1.put("standardTime", question.getStandardTime());
		question1.put("title", question.getTitle());
		question1.put("voice", question.getVoice());
		if (question.getQuestionType() == 5) {
			question1.put("isListen", 3);
		}else if (question.getIsVoice() == 1) {
			question1.put("isListen", 2);
		}else{
			question1.put("isListen", 1);
		}
		
		if (question.getParentQuestion()!=null && question.getParentQuestion().getQuestionType()!=5) {
			if ( question.getQuestionType() == 5) {
				parentQuestion.put("isListen", 3);
			}else if (question.getIsVoice() == 1) {
				parentQuestion.put("isListen", 2);
			}
		}
		question1.put("answer", StringUtils.isEmpty(resultCardIn.getAnswer())?null:JSONObject.parse(resultCardIn.getAnswer()));
		List<Map<String,Object>> childResultCardInfoList = new ArrayList<Map<String,Object>>();
		for (ResultCardInfo info : resultCardInfos) {
			Question parentQuestion1 = info.getWorkInfo().getQuestion().getParentQuestion();
			if (parentQuestion1!=null && parentQuestion1.getiD()==question.getiD()) {
				childResultCardInfoList.add(forMapResultCardInfo(question1,info, resultCardInfos));
			}
		}
		question1.put("childResultCardInfoList", childResultCardInfoList);
		return question1;
	}

	private List<Map<String, Object>> forMapResult(List<Result> resultList) {
		List<Map<String,Object>> resultList1 = new ArrayList<Map<String,Object>>();
		for (Iterator<Result> iterator = resultList.iterator(); iterator.hasNext();) {
			Result result = (Result) iterator.next();
			Map<String,Object> result1 = new HashMap<String, Object>();
			result1.put("answer", result.getAnswer());
			result1.put("content", result.getContent());
			result1.put("createDate", result.getCreateDate());
			result1.put("head", result.getHead());
			result1.put("iD", result.getiD());
			result1.put("modifyDate", result.getModifyDate());
			result1.put("score", result.getScore());
			resultList1.add(result1);
		}
		return resultList1;
	}

	private Map<String,Object> forMapResutlCard(ResultCard resultCard) {
		Map<String,Object> resultCard1 = new HashMap<String, Object>();
		resultCard1.put("correct", resultCard.getCorrect());
		resultCard1.put("createDate", resultCard.getCreateDate());
		resultCard1.put("hasDealed", resultCard.getHasDealed());
		resultCard1.put("iD", resultCard.getiD());
		resultCard1.put("modifyDate", resultCard.getModifyDate());
		resultCard1.put("openTime", resultCard.getOpenTime());
		resultCard1.put("score", resultCard.getScore());
		resultCard1.put("studentId", resultCard.getStudentId());
		resultCard1.put("totalTime", resultCard.getTotalTime());
		Work work = resultCard.getWork();
		resultCard1.put("work", forMapWork(work));
		return resultCard1;
	}

	private Map<String, Object> forMapWork(Work work) {
		Map<String,Object> work1 = new HashMap<String, Object>();
		work1.put("category", work.getCategory().getiD());
		work1.put("categoryName", work.getCategory().getCategoryName());
		work1.put("createDate", work.getCreateDate());
		work1.put("downUrl", work.getDownUrl());
		work1.put("iD", work.getiD());
		work1.put("lesson", work.getLesson().getiD());
		work1.put("modifyDate", work.getModifyDate());
		work1.put("score", work.getScore());
		work1.put("sourceSize", work.getSourceSize());
		work1.put("standardTime", work.getStandardTime());
		work1.put("zipDate", work.getZipDate());
		work1.put("zipState", work.getZipState());
		return work1;
	}


	@Override
	public List<Map<String, Object>> getLessonResultCards(int lessonId,
														  int studentId) {
				List<Map<String, Object>> resultCardList = resultCardDAO
						.getResultCards(lessonId, studentId);
				for (Iterator<Map<String, Object>> iterator = resultCardList.iterator(); iterator
						.hasNext();) {
					// 计数，标准题个数
					int count = 0;
					Map<String, Object> resultCard = iterator.next();
					int resultCardId = (Integer) resultCard.get("id");
					resultCard.put("iD", resultCardId);
					int workId = (Integer) resultCard.get("work");
					Map<String, Object> work = workDAO.getWork(workId);
					work.put("iD", workId);
					resultCard.put("work", work);
					List<Map<String, Object>> resultCardInfoList = resultCardDAO
							.getResultCardInfos(resultCardId);
					resultCard.put("resultCardInfoList", resultCardInfoList);
					// 循环遍历填充数据
					for (Iterator<Map<String, Object>> iterator2 = resultCardInfoList
							.iterator(); iterator2.hasNext();) {
						Map<String, Object> resultCardInfo = iterator2.next();
						resultCardInfo.put("iD", resultCardInfo.get("id"));
						int workInfoId = (Integer) resultCardInfo.get("workInfo");
						Map<String, Object> workInfo = workDAO.getWorkInfo(workInfoId);
						workInfo.put("iD", workInfoId);
						resultCardInfo.put("workInfo", workInfo);
						int questionId = (Integer) workInfo.get("question");
						Map<String, Object> question = questionDAO
								.getQuesMap(questionId);
						question.put("iD", questionId);
						workInfo.put("question", question);
						// question.put("answer", resultCardInfo.get("answer"));
						List<Map<String, Object>> resultList = questionDAO
								.getResultList(questionId);
						question.put("resultList", resultList);
						if ((Integer) question.get("questionType") != 0) {
							count++;
						}
						if ((Integer) question.get("questionType") == 5) {
							question.put("isListen", 3);
						}else if ((Integer) question.get("isVoice") == 1) {
							question.put("isListen", 2);
						}else{
							question.put("isListen", 1);
						}
						// 添加子题的resultCardInfo
						List<Map<String, Object>> childResultCardInfoList = new ArrayList<Map<String, Object>>();
						List<Map<String, Object>> childList = questionDAO
								.childList(questionId);
						if (childList != null) {
							for (Iterator<Map<String, Object>> iterator3 = childList
									.iterator(); iterator3.hasNext();) {
								Map<String, Object> childResultCardInfo = new HashMap<String, Object>();
								Map<String, Object> childQuestion = (Map<String, Object>) iterator3
										.next();
								ResultCardInfo resultCardInfoBean = resultCardDAO
										.getResultCardInfoBean(resultCardId,
												(Integer) childQuestion.get("id"));
								childResultCardInfo.put("iD",resultCardInfoBean.getiD());
								String answer = resultCardInfoBean.getAnswer();
								childResultCardInfo.put("answer",JSONObject.parse(answer));
								childResultCardInfo.put("createDate",resultCardInfoBean.getCreateDate());
								childResultCardInfo.put("modifyDate",resultCardInfoBean.getModifyDate());
								childResultCardInfo.put("resultCard",resultCardInfoBean.getResultCard().getiD());
								childResultCardInfo.put("score",resultCardInfoBean.getScore());
								childResultCardInfo.put("state",resultCardInfoBean.getState());
								childResultCardInfo.put("useTime",resultCardInfoBean.getUseTime());
								WorkInfo workInfoBean = resultCardInfoBean.getWorkInfo();
								Map<String, Object> childWork = new HashMap<String, Object>();
								childWork.put("appSortNo", workInfoBean.getAppSortNo());
								childWork.put("createDate",
										workInfoBean.getCreateDate());
								childWork.put("iD", workInfoBean.getiD());
								childWork.put("modifyDate",
										workInfoBean.getModifyDate());
								childWork.put("sortNo", workInfoBean.getSortNo());
								childWork.put("work", workInfoBean.getWork());
								childResultCardInfo.put("workInfo", childWork);

								childQuestion.put("iD", childQuestion.get("id"));
								List<Map<String, Object>> resultList1 = questionDAO
										.getResultList((Integer) childQuestion
												.get("id"));
								childQuestion.put("resultList", resultList1);
								childQuestion.put("answer", JSONObject.parse(answer));
								if ((Integer) childQuestion.get("questionType") != 0) {
									count++;
								}
								
								if ((Integer)question.get("isListen")<3) {
									if ((Integer) childQuestion.get("questionType") == 5) {
										question.put("isListen", 3);
									}else if ((Integer) childQuestion.get("isVoice") == 1) {
										question.put("isListen", 2);
									}
								}

								childWork.put("question", childQuestion);
								childResultCardInfoList.add(childResultCardInfo);
							}
						}
						question.put("childResultCardInfoList", childResultCardInfoList);
						resultCard.put("count", count);
					}
				}
				return resultCardList;
	}

	@Override
	public Map<String, Object> preview(int questionId) {
		// 计数，标准题个数
		int count = 1;

		Map<String, Object> resultCard = new HashMap<String, Object>();
		resultCard.put("iD", count);

		Map<String, Object> work = new HashMap<String, Object>();
		work.put("iD", count);
		resultCard.put("work", work);
		List<Map<String, Object>> resultCardInfoList = new ArrayList<Map<String, Object>>();
		resultCard.put("resultCardInfoList", resultCardInfoList);
		// 循环遍历填充数据
		count++;
		Map<String, Object> resultCardInfo = new HashMap<String, Object>();
		resultCardInfoList.add(resultCardInfo);
		resultCardInfo.put("iD", count);
		Map<String, Object> workInfo = new HashMap<String, Object>();

		workInfo.put("iD", count);
		resultCardInfo.put("workInfo", workInfo);

		Map<String, Object> question = questionDAO.getQuesMap(questionId);

		question.put("iD", questionId);
		workInfo.put("question", question);
		// question.put("answer", resultCardInfo.get("answer"));
		List<Map<String, Object>> resultList = questionDAO
				.getResultList(questionId);
		question.put("resultList", resultList);
		
		if ((Integer) question.get("questionType") == 5) {
			question.put("isListen", 3);
		}else if ((Integer) question.get("isVoice") == 1) {
			question.put("isListen", 2);
		}else{
			question.put("isListen", 1);
		}

		// 添加子题的resultCardInfo
		List<Map<String, Object>> childResultCardInfoList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> childList = questionDAO.childList(questionId);
		if (childList != null) {
			for (Iterator<Map<String, Object>> iterator3 = childList.iterator(); iterator3
					.hasNext();) {
				count++;
				Map<String, Object> childResultCardInfo = new HashMap<String, Object>();
				Map<String, Object> childQuestion = (Map<String, Object>) iterator3
						.next();
				childResultCardInfo.put("iD", count);

				Map<String, Object> childWork = new HashMap<String, Object>();
				childWork.put("iD", count);
				childResultCardInfo.put("workInfo", childWork);

				childQuestion.put("iD", childQuestion.get("id"));
				List<Map<String, Object>> resultList1 = questionDAO
						.getResultList((Integer) childQuestion.get("id"));
				childQuestion.put("resultList", resultList1);
				if ((Integer)question.get("isListen")<3) {
					if ((Integer) childQuestion.get("questionType") == 5) {
						question.put("isListen", 3);
					}else if ((Integer) childQuestion.get("isVoice") == 1) {
						question.put("isListen", 2);
					}
				}

				childWork.put("question", childQuestion);
				childResultCardInfoList.add(childResultCardInfo);
			}
		}
		question.put("childResultCardInfoList", childResultCardInfoList);
		return resultCard;
	}

	@Override
	public void commitLesson(Map<String, Object> lesson) {
		// TODO Auto-generated method stub
		lessonDAO.commitLesson(lesson);
	}

	@Override
	public List<Map<String, Object>> getLessonList(String period, String grade,
			String subject, String cate) {
		// TODO Auto-generated method stub
		return lessonDAO.getLessonList(period, grade, subject, cate);
	}

	@Override
	public void deleteMaxLesson(int lessonId) {
		lessonDAO.deleteMaxLesson(lessonId);
	}

	@Override
	public Map<String, Object> getLessonInfo(int lessonId) {
		return lessonDAO.getLessonInfo(lessonId);
	}
	
	@Override
	public LessonBean getLessonBean(int lessonId) {
		return lessonDAO.getLessonBean(lessonId);
	}

	@Override
	public void updateLesson(int lessonId) {
		lessonDAO.updateLesson(lessonId);
	}

	@Override
	public void updateOpenTime(int lessonId, String openTime) {
		lessonDAO.updateOpenTime(lessonId, openTime);
	}
	
	@Override
	public void updateBrief(int lessonId, String brief) {
		lessonDAO.updateBrief(lessonId, brief);
	}

	@Override
	public List<Map<String, Object>> getLessonListByCourse(int courseId) {
		return lessonDAO.getLessonListByCourse(courseId);
	}

	@Override
	public Map<String, Object> getMaxLesson(int hierarchyId, String periodId) {
		return lessonDAO.getMaxLesson(hierarchyId, periodId);
	}

}
