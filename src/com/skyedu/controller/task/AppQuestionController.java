package com.skyedu.controller.task;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.skyedu.model.Mistake;
import com.skyedu.model.Question;
import com.skyedu.model.Result;
import com.skyedu.model.ResultCard;
import com.skyedu.model.ResultCardInfo;
import com.skyedu.model.WorkInfo;
import com.skyedu.service.LessonService;
import com.skyedu.service.MistakeService;
import com.skyedu.service.QuestionService;
import com.skyedu.service.ResultCardService;
import com.skyedu.service.WorkService;
import com.util.FileThread;

@Controller
@RequestMapping("/appQuestion")
public class AppQuestionController {

	@Autowired
	private QuestionService questionService;
	@Autowired
	private ResultCardService resultCardService;
	@Autowired
	private MistakeService mistakeService;
	@Autowired
	private LessonService lessonService;
	@Autowired
	private WorkService workService;
	
	@RequestMapping(value="/setResultList")
	@ResponseBody
	@Transactional
	public Map<String,Object> setResultList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			Map<String,Object> data = new HashMap<String, Object>();
			String resultCardId = request.getParameter("resultCardId");
			String totalTime = request.getParameter("totalTime");
			String answers = request.getParameter("answerList");
			String userStudentId = request.getParameter("userStudentId");
			data.put("resultCardId", resultCardId);
			data.put("totalTime", totalTime);
			
			if (StringUtils.isEmpty(resultCardId) ||StringUtils.isEmpty(totalTime) ||StringUtils.isEmpty(answers) ||StringUtils.isEmpty(userStudentId)) {
				map.put("code", 101);
				map.put("message", "参数异常");
				return map;
			}
			
			//答题卡得分计数
			Double score = 0.0;
			String correct = "";
			int rightCount = 0;
			List<Map<String,Object>> answerList = (List<Map<String, Object>>) JSONObject.parse(answers);
			List<Map<String,Object>> mistakeList = new ArrayList<Map<String,Object>>();
			//遍历学生答题的答案
			for (Iterator<Map<String, Object>> iterator = answerList.iterator(); iterator.hasNext();) {
				Map<String, Object> questionAnswer = (Map<String, Object>) iterator.next();
				int questionId = (Integer) questionAnswer.get("questionId");
				int questionType = (Integer) questionAnswer.get("questionType");
				int useTime = (Integer) questionAnswer.get("time");
				//小题得分计数
				double answerScore = (double) 0;
				
				//学生一道题的答案，填空和多选都有多个答案
				Object object = questionAnswer.get("answer");
				Question question = questionService.getQuestion(questionId);
				//防止app提交失效的数据
				if (question == null || question.getQuestionType()==0) {
					continue;
				}
				ResultCardInfo resultCardInfoBean = new ResultCardInfo();
				if (object==null || ((List<Map<String, Object>>) object).size()==0) {
					resultCardInfoBean = resultCardService.getResultCardInfoBean(Integer.valueOf(resultCardId), questionId);
					resultCardInfoBean.setAnswer("");
					resultCardInfoBean.setModifyDate(new Date());
					resultCardInfoBean.setUseTime(useTime);
					resultCardInfoBean.setScore(0.0);
					resultCardInfoBean.setState(0);
					resultCardService.updateResultCardInfo(resultCardInfoBean);
				}else {
					List<Map<String, Object>> studentAnswers = (List<Map<String, Object>>) object;
					List<Result> resultList = question.getResultList();
					
					//填空或者连线
					if (questionType == 3 || questionType == 4) {
						
						//待修改
						if (question.getiD()==15238 ) {
							List<Map<String, Object>> newStudentAnswer = new ArrayList<Map<String, Object>>();
							r1:for (Map<String, Object> studentAnswer : studentAnswers) {
								if ((Integer)studentAnswer.get("index")==2) {
									studentAnswer.put("index", 1);
									newStudentAnswer.clear();
									newStudentAnswer.add(studentAnswer);
									studentAnswers = newStudentAnswer;
									break r1;
								} else if((Integer)studentAnswer.get("index")==1){
									newStudentAnswer.add(studentAnswer);
								}
							}
						}
						
						for (int i = 0; i < studentAnswers.size(); i++) {
							//题目选项对象
							Map<String, Object> studentAnswer = studentAnswers.get(i);
							int index = (Integer) studentAnswer.get("index");
							Result result = resultList.get(index - 1);
							//填空题可能多个答案，用||切分
							String[] trueAnswer = result.getAnswer().split("\\|\\|");
							List<String> tAnswers = new ArrayList<String>();
							for (int j = 0; j < trueAnswer.length; j++) {
								String tAnswer = trueAnswer[j];
								tAnswers.add(tAnswer.replaceAll(" ", ""));
							}
							if (tAnswers.contains(((String) studentAnswer.get("answer")).replaceAll(" ", "")) || result
									.getAnswer().equals(((String) studentAnswer.get("answer")).replaceAll(" ", ""))) {
								answerScore = answerScore + result.getScore();
								studentAnswer.put("right", 1);
							}
						}
					}
					boolean state = true;
					//选择题
					if (questionType == 1 || questionType == 2 || questionType == 6) {
						for (int i = 0; i < resultList.size(); i++) {
							Result result = resultList.get(i);
							String head = result.getHead();
							Integer trueAnswer = Integer.valueOf(result.getAnswer());
							//正确选项
							if (trueAnswer == 1) {
								for (Iterator<Map<String, Object>> iterator2 = studentAnswers.iterator(); iterator2
										.hasNext();) {
									Map<String, Object> studentAnswer = (Map<String, Object>) iterator2.next();
									//选项标头，学生答案中有正确选项就跳出，一直未选中说明少选
									if (head.equals(studentAnswer.get("answer"))) {
										state = true;
										break;
									} else {
										state = false;
									}
								}
							} else {
								//错误选项
								for (Iterator<Map<String, Object>> iterator2 = studentAnswers.iterator(); iterator2
										.hasNext();) {
									Map<String, Object> studentAnswer = (Map<String, Object>) iterator2.next();
									//选项标头，学生答案中有错误选项就跳出
									if (head.equals(studentAnswer.get("answer"))) {
										state = false;
										break;
									}
								}
							}

							if (!state) {
								break;
							}

						}
					}
					if (questionType == 5) {
						//需要从answer中获取学生的音频url，去第三方下载
						String path = request.getSession().getServletContext().getRealPath("/");
						// 按学生id存储,存入个人作品文件夹
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
						String time = sdf.format(new Date());
						String shortPath = "/upload/studentVoice/"+time.substring(0, 4)+"/"+time.substring(4, 6)+"/"+time.substring(6);
						for (int i = 0; i < studentAnswers.size(); i++) {
							//题目选项对象
							Map<String, Object> studentAnswer = studentAnswers.get(i);
							String uuid = UUID.randomUUID().toString().replace("-", "");
							String oggurl = (String) studentAnswer.get("answer");
							if (!StringUtils.isEmpty(oggurl) && !oggurl.contains("upload")) {

								FileThread fileThread = new FileThread(path + shortPath, "http://" + oggurl,
										uuid + ".mp3");
								fileThread.run();
							}
							studentAnswer.put("answer", shortPath + File.separatorChar + uuid + ".mp3");
						}
						BigDecimal big = new BigDecimal(questionAnswer.get("score").toString());
						answerScore = big.doubleValue();
					}
					double questionScore = question.getScore();
					resultCardInfoBean = resultCardService.getResultCardInfoBean(Integer.valueOf(resultCardId),
							questionId);
					if (resultCardInfoBean == null) {
						map.put("code", 101);
						map.put("message", "答题卡已失效");
						return map;
					}
					resultCardInfoBean.setAnswer(studentAnswers.toString());
					resultCardInfoBean.setModifyDate(new Date());
					resultCardInfoBean.setUseTime(useTime);
					if (questionType == 3 || questionType == 4) {
						resultCardInfoBean.setScore(answerScore);
						if (questionScore == answerScore) {
							resultCardInfoBean.setState(2);
						} else if (answerScore != 0) {
							resultCardInfoBean.setState(1);
						} else {
							resultCardInfoBean.setState(0);
						}
					} else if (questionType == 5) {
						resultCardInfoBean.setScore(answerScore);
						double answerCorrect = answerScore / questionScore;
						if (answerCorrect > 0.8) {
							resultCardInfoBean.setState(2);
						} else {
							resultCardInfoBean.setState(1);
						}
					} else if (questionType == 1 || questionType == 2 || questionType == 6) {
						if (state) {
							resultCardInfoBean.setScore(question.getScore());
							resultCardInfoBean.setState(2);
						} else {
							resultCardInfoBean.setScore((float) 0);
							resultCardInfoBean.setState(0);
						}
					}
					if (resultCardInfoBean.getState() == 2) {
						rightCount++;
					}
					questionAnswer.put("score", resultCardInfoBean.getScore());
					questionAnswer.put("state", resultCardInfoBean.getState());
					resultCardService.updateResultCardInfo(resultCardInfoBean);
				}
				//加入错题集
				int mQuestion = 0;
				ResultCardInfo mResultCardInfo = null;
				if (question.getParentQuestion()!=null) {
					mQuestion = question.getParentQuestion().getiD();
					ResultCardInfo pResultCardInfoBean = resultCardService.getResultCardInfoBean(Integer.parseInt(resultCardId), mQuestion);
					pResultCardInfoBean.setModifyDate(new Date());
					resultCardService.updateResultCardInfo(pResultCardInfoBean);
					mResultCardInfo = pResultCardInfoBean;
				}else{
					mQuestion = questionId;
					mResultCardInfo = resultCardInfoBean;
				}
				if (resultCardInfoBean.getState()<2) {
					ResultCard resultCardBean = resultCardService.getResultCardBean(Integer.parseInt(resultCardId));
					WorkInfo workInfo = workService.getWorkInfo(resultCardBean.getWork().getiD(), mQuestion);
					Mistake mistake = resultCardService.getMistake(workInfo.getiD(), Integer.valueOf(userStudentId));
					Map<String,Object> misMap = new HashMap<String, Object>();
					misMap.put("resultCardInfoId", mResultCardInfo.getiD());
					if (mistake==null) {
						mistake = new Mistake();
						mistake.setCreateDate(new Date());
						mistake.setResultCard(Integer.valueOf(resultCardId));
						mistake.setStudentId(Integer.valueOf(userStudentId));
						mistake.setWorkInfo(workInfo);
						mistakeService.saveMistake(mistake);
					}else{
						mistake.setCreateDate(new Date());
						mistakeService.updateMistake(mistake);
					}
					misMap.put("mistakeId", mistake.getiD());
					mistakeList.add(misMap);
				}
				
				score = score + resultCardInfoBean.getScore();
			}
			
			correct = getNum(rightCount, answerList.size());
			//correct = (double) (rightCount/count);
			ResultCard resultCard = resultCardService.getResultCardBean(Integer.valueOf(resultCardId));
			Date now = new Date();
			resultCard.setHasDealed(1);
			resultCard.setModifyDate(now);
			resultCard.setScore(score);
			resultCard.setTotalTime(Integer.valueOf(totalTime));
			resultCard.setCorrect(correct);
			resultCardService.updateResultCard(resultCard);
			Double starCorrect = Double.parseDouble(correct);
			int starCount = 0;
			if (starCorrect<=0.50) {
				
			}else if(starCorrect<=0.70){
				starCount = 1;
			}else if(starCorrect<=0.90){
				starCount = 2;
			}else if(starCorrect>0.90){
				starCount = 3;
			}
			/*=====================课次修改时间========================*/
			/*int workId = resultCard.getWorkId();
			Map<String, Object> work = workService.getWork(workId);
			Date lessonModifyDate = new Date(0);
			List<ResultCard> resultCardList = resultCardService.getResultCardList((Integer)work.get("lesson"), Integer.valueOf(userStudentId));
			for (Iterator<ResultCard> iterator3 = resultCardList.iterator(); iterator3
					.hasNext();) {
				ResultCard resultCardm = (ResultCard) iterator3.next();
				Date resultCardModifyDate = resultCardm.getCreateDate();
				if (resultCardModifyDate.getTime()>lessonModifyDate.getTime()) {
					lessonModifyDate = resultCardModifyDate;
				}
			}
			long time = lessonModifyDate.getTime()+resultCardList.size();*/
			/*=====================课次修改时间========================*/
			data.put("lessonModifyDate",now /*new Date(time)*/);
			data.put("rightCount", rightCount);
			data.put("score", score);
			data.put("modifyDate", now);
			data.put("correct", correct);
			data.put("mistakeList", mistakeList);
			data.put("answerList", answerList);
			data.put("starCount", starCount);
			data.put("count", answerList.size());
			map.put("data", data);
			map.put("code", 100);
			map.put("message", "提交成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("code", 101);
			map.put("message", "提交失败");
		}
		
		return map;
	}
	
	/**
	 * 生成百分数
	 * @param i
	 * @param j
	 * @return
	 */
	public String getNum(Integer i,Integer j){
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);
		if (j==0) {
			return "0.00";
		}
		return df.format(i.doubleValue() / j) ;
		 
	}
	
	@RequestMapping("/getMistakeList")
	@ResponseBody
	public Map<String,Object> getMistakeList(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> data = new HashMap<String, Object>();
		Map<String,Object> condition = new HashMap<String, Object>();
		String userStudentId = request.getParameter("userStudentId");
		String courseId = request.getParameter("courseId");
		String pageNo = request.getParameter("pageNo");
		if (StringUtils.isEmpty(userStudentId)||StringUtils.isEmpty(courseId)) {
			data.put("code", 101);
			data.put("message", "参数异常");
			return data;
		}
		condition.put("studentId", Integer.valueOf(userStudentId));
		condition.put("courseId", Integer.valueOf(courseId));
		if (StringUtils.isEmpty(pageNo)) {
			condition.put("pageNo", 1);
		}else{
			condition.put("pageNo", Integer.valueOf(pageNo));
		}
		List<Map<String, Object>> mistakeList = mistakeService.getMistakeList(condition);
		data.put("code", 100);
		data.put("data", mistakeList);
		return data;
	}
	
	@RequestMapping("/removeMistake")
	@ResponseBody
	public Map<String,Object> removeMistake(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> data = new HashMap<String, Object>();
		String mistakeId = request.getParameter("mistakeId");
		if (StringUtils.isEmpty(mistakeId)) {
			data.put("code", 101);
			data.put("message", "参数异常");
			return data;
		}
		Mistake mistake = new Mistake();
		mistake.setiD(Integer.valueOf(mistakeId));
		mistakeService.delMistakes(mistake);
		data.put("code", 100);
		data.put("message", "删除成功");
		return data;
	}
	
	@RequestMapping("/preview")
	@ResponseBody
	public void preview(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		Map<String,Object> data = new HashMap<String, Object>();
		String parameter = request.getParameter("questionId");
		if (StringUtils.isEmpty(parameter)) {
			data.put("code", 101);
			data.put("message", "参数异常");
			response.getOutputStream().write(JSONObject.toJSONString(data).getBytes("UTF-8"));
			return;
		}
		int questionId = Integer.valueOf(parameter);
		Map<String, Object> preview = lessonService.preview(questionId);
		data.put("code", 100);
		data.put("data", preview);
		response.getOutputStream().write(JSONObject.toJSONString(data).getBytes("UTF-8"));
	}
	
	@RequestMapping(value="/checkQuestion")
	@ResponseBody
	public Map<String,Object> checkQuestion(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			Map<String,Object> data = new HashMap<String, Object>();
			String answers = request.getParameter("answerList");
			String userStudentId = request.getParameter("userStudentId");
			
			if (StringUtils.isEmpty(answers) ||StringUtils.isEmpty(userStudentId)) {
				map.put("code", 101);
				map.put("message", "参数异常");
				return map;
			}
			
			//答题卡得分计数
			Double score = 0.0;
			String correct = "";
			int rightCount = 0;
			List<Map<String,Object>> answerList = (List<Map<String, Object>>) JSONObject.parse(answers);
			
			//遍历学生答题的答案
			for (Iterator<Map<String, Object>> iterator = answerList.iterator(); iterator.hasNext();) {
				Map<String, Object> questionAnswer = (Map<String, Object>) iterator.next();
				int questionId = (Integer) questionAnswer.get("questionId");
				int questionType = (Integer) questionAnswer.get("questionType");
				//小题得分计数
				double answerScore = (double) 0;
				
				//学生一道题的答案，填空和多选都有多个答案
				List<Map<String, Object>> studentAnswers = (List<Map<String, Object>>) questionAnswer.get("answer");
				if (studentAnswers==null || studentAnswers.size()==0) {
					continue;
				}
				Question question = questionService.getQuestion(questionId);
				//防止app提交失效的数据
				if (question == null) {
					continue;
				}
				List<Result> resultList = question.getResultList();
				//填空或者连线
				if (questionType==3||questionType==4) {
					for(int i=0;i<studentAnswers.size();i++) {
						//题目选项对象
						Map<String,Object> studentAnswer = studentAnswers.get(i);
						int index = (Integer)studentAnswer.get("index");
						//学生的一个答案
						Result result = resultList.get(index-1);
						//填空题可能多个答案，用||切分
						String[] trueAnswer = result.getAnswer().split("\\|\\|");
						List<String> tAnswers = new ArrayList<String>();
						for (int j = 0; j < trueAnswer.length; j++) {
							String tAnswer = trueAnswer[j];
							tAnswers.add(tAnswer.replaceAll(" ", ""));
						}
						if (tAnswers.contains(((String) studentAnswer.get("answer")).replaceAll(" ", "")) || result
								.getAnswer().equals(((String) studentAnswer.get("answer")).replaceAll(" ", ""))) {
							answerScore = answerScore + result.getScore();
							studentAnswer.put("right", 1);
						}
					}
				}
				
				boolean state = true;
				//选择题
				if(questionType==1||questionType==2||questionType==6){
					for (int i=0;i<resultList.size();i++) {
						Result result = resultList.get(i);
						String head = result.getHead();
						Integer trueAnswer = Integer.valueOf(result.getAnswer());
						//正确选项
						if (trueAnswer==1) {
							for (Iterator<Map<String, Object>> iterator2 = studentAnswers.iterator(); iterator2
									.hasNext();) {
								Map<String,Object> studentAnswer = (Map<String,Object>) iterator2.next();
								//选项标头，学生答案中有正确选项就跳出，一直未选中说明少选
								if (head.equals(studentAnswer.get("answer"))) {
									state = true;
									break;
								}else{
									state = false;
								}
							}
						}else{
							//错误选项
							for (Iterator<Map<String, Object>> iterator2 = studentAnswers.iterator(); iterator2
									.hasNext();) {
								Map<String,Object> studentAnswer = (Map<String,Object>) iterator2.next();
								//选项标头，学生答案中有错误选项就跳出
								if (head.equals(studentAnswer.get("answer"))) {
									state = false;
									break;
								}
							}
						}
						
						if (!state) {
							break;
						}
						
					}
				}
				
				
				double questionScore = question.getScore();
				Map<String,Object> resultCardInfo = new HashMap<String, Object>();
				if (questionType==3||questionType==4) {
					resultCardInfo.put("score", answerScore);
					if (questionScore == answerScore) {
						resultCardInfo.put("state", 2);
					} else if(answerScore != 0){
						resultCardInfo.put("state", 1);
					} else{
						resultCardInfo.put("state", 0);
					}
				}else if(questionType==5){
					resultCardInfo.put("score", answerScore);
					if (answerScore>80) {
						resultCardInfo.put("state", 2);
					}else{
						resultCardInfo.put("state", 1);
					}
				}else if(questionType==1||questionType==2||questionType==6){
					if (state) {
						resultCardInfo.put("score", question.getScore());
						resultCardInfo.put("state", 2);
					}else{
						resultCardInfo.put("score", (double)0);
						resultCardInfo.put("state", 0);
					}
				}
				
				if ((Integer)resultCardInfo.get("state")==2) {
					rightCount++;
				}
				
				questionAnswer.put("score", resultCardInfo.get("score"));
				questionAnswer.put("state", resultCardInfo.get("state"));
				
				score = score + (Double)resultCardInfo.get("score");
			}
			
			correct = getNum(rightCount, answerList.size());
			data.put("rightCount", rightCount);
			data.put("score", score);
			data.put("correct", correct);
			data.put("answerList", answerList);
			data.put("count", answerList.size());
			map.put("data", data);
			map.put("code", 100);
			map.put("message", "提交成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("code", 101);
			map.put("message", "提交失败");
		}
		return map;
	}

}
