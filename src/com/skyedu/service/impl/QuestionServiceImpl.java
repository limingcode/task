package com.skyedu.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.skyedu.dao.impl.QuestionDAO;
import com.skyedu.dao.impl.WorkDAO;
import com.skyedu.exception.QuestionException;
import com.skyedu.model.Hierarchy;
import com.skyedu.model.Lesson;
import com.skyedu.model.Question;
import com.skyedu.model.Result;
import com.skyedu.model.ResultCardInfo;
import com.skyedu.model.Work;
import com.skyedu.service.HierarchyService;
import com.skyedu.service.LessonService;
import com.skyedu.service.QuestionService;
import com.skyedu.service.WorkService;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionDAO questionDAO;
	@Autowired
	private WorkDAO workDAO;
	@Autowired
	private WorkService workService;
	@Autowired
	private HierarchyService hierarchyService;
	@Autowired
	private LessonService lessonService;

	@Override
	public Question getQuestion(int id) {
		// TODO Auto-generated method stub
		Question question = questionDAO.getQuestion(id);
		return question;
	}

	@Override
	public void saveQuestion(Question question) {
		// TODO Auto-generated method stub
		questionDAO.save(question);
	}

	@Override
	public List<Map<String, Object>> quesList(Map<String, Object> condition) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> questionList = questionDAO
				.questionList(condition);
		return questionList;
	}

	@Override
	public List<Map<String, Object>> childList(int parentId) {
		// TODO Auto-generated method stub
		return questionDAO.childList(parentId);
	}

	@Override
	public List<Map<String, Object>> getQuestionByWork(int workId,String brief) {
		// TODO Auto-generated method stub
		return questionDAO.getQuestionByWork(workId,brief);
	}

	@Override
	public void saveQuestionHtml(int questionId, String qItemCnt) {
		// TODO Auto-generated method stub
		questionDAO.saveQuestionHtml(questionId, qItemCnt);
	}

	@Override
	public void updateQuestionHtml(int questionId, String qItemCnt) {
		// TODO Auto-generated method stub
		questionDAO.updateQuestionHtml(questionId, qItemCnt);
	}

	@Override
	public Map<String, Object> getQuestionHtml(int questionId) {
		// TODO Auto-generated method stub
		return questionDAO.getQuestionHtml(questionId);
	}

	@Override
	public void updateQuestion(Question question) {
		// TODO Auto-generated method stub
		questionDAO.update(question);
	}

	@Override
	public void deleteResults(int questionId) {
		// TODO Auto-generated method stub
		questionDAO.deleteResults(questionId);
	}

	@Override
	public void countScore(int parentId) {
		// TODO Auto-generated method stub
		Question question = questionDAO.getQuestion(parentId);
		List<Map<String, Object>> childList = questionDAO.childList(parentId);
		Double score = (double) 0;
		int time = 0;
		for (Iterator<Map<String, Object>> iterator = childList.iterator(); iterator.hasNext();) {
			Map<String, Object> childQuestion = (Map<String, Object>) iterator.next();
			Object childScore = childQuestion.get("score");
			if (childScore!=null) {
				score = score + (Double)childScore;
			}
			Object childTime = childQuestion.get("standardTime");
			if (childTime!=null) {
				time = time + (Integer)childTime;
			}
		}
		question.setScore(score);
		question.setStandardTime(time);
		questionDAO.updateQuestion(question);
	}

	@Override
	public boolean delQuestion(int questionId) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> workInfoByQ = workDAO.getWorkInfoByQ(questionId);
		if (workInfoByQ==null ||workInfoByQ.size()==0) {
			List<Map<String, Object>> childList = questionDAO.childList(questionId);
			if (childList!=null && childList.size()>0) {
				for (Iterator<Map<String, Object>> iterator = childList.iterator(); iterator.hasNext();) {
					Map<String, Object> map = (Map<String, Object>) iterator.next();
					questionDAO.deleteQuestionHtml((Integer) map.get("id"));
					questionDAO.delete(Question.class, (Integer) map.get("id"));
				}
			}
			questionDAO.deleteQuestionHtml(questionId);
			questionDAO.delete(Question.class, questionId);
			return true;
		}else{
			return false;
		}
	}
	
	public Map<String,Object> saveQuestions(List<Map<String, Object>> qItemList,int oaId,Hierarchy hierarchy,String appPath,String quesPath, String quesOriPath){
		Map<String,Object> data = new HashMap<String, Object>();
		Question parent = null;
		int count = 0;
		try {
			for (Iterator<Map<String, Object>> iterator = qItemList.iterator(); iterator
					.hasNext();) {

				// 存储临时文件夹的文件路径
				//List<String> files = new ArrayList<String>();
				List<Map<String,Object>> fileMaps = new ArrayList<Map<String,Object>>();

				Map<String, Object> item = (Map<String, Object>) iterator
						.next();
				
				String qItemJson = (String) item.get("qItemJson");
				qItemJson = qItemJson.replace("&lt;", "<").replace("&gt;", ">").replace("&nbsp;", " ").replace("&amp;", "&");
				String qItemCnt = (String) item.get("qItemCnt");
				qItemCnt = qItemCnt.replace("&lt;", "<").replace("&gt;", ">");
				
				Map<String, Object> jsonObject = (Map<String, Object>) JSONObject
						.parse(qItemJson);
				if (jsonObject==null) {
					continue;
				}
				Question question = new Question();
				List<Result> resultList = new ArrayList<Result>();
				//oaId
				question.setOaId(oaId);
				question.setEditor(oaId);
				question.setCreateDate(new Date());
				question.setModifyDate(new Date());
				question.setHierarchy(hierarchy);
				question.setIsVoice(0);

				Object standTime = jsonObject
						.get("standTime");
				if (standTime==null) {
					continue;
				}
				question.setStandardTime(Integer.valueOf((String) standTime));
				
				Object score = jsonObject
						.get("score");
				if (score!=null) {
					if (score instanceof BigDecimal) {
						question.setScore(((BigDecimal) score).doubleValue());
					}else{
						question.setScore((Integer) score);
					}
				}
				Integer questionType = Integer.valueOf((String) jsonObject
						.get("questionType"));
				question.setQuestionType(questionType);
				
				Integer answerLength = (Integer) jsonObject
						.get("answerLength");
				question.setAnswerLength(answerLength);

				Object analyzation = jsonObject.get("analysis");
				if (analyzation != null) {
					question.setAnalyzation((String) analyzation);
				}
				Object brief = jsonObject.get("questionTitle");
				if (brief != null) {
					question.setBrief((String) brief);
				}

				List<Map<String, Object>> pList = (List<Map<String, Object>>) jsonObject
						.get("contents");
				for (Iterator<Map<String, Object>> iterator2 = pList
						.iterator(); iterator2.hasNext();) {
					Map<String, Object> pModule = (Map<String, Object>) iterator2
							.next();
					List<Map<String, Object>> imgs = (List<Map<String, Object>>) pModule
							.get("imgs");
					for (Iterator<Map<String, Object>> iterator3 = imgs
							.iterator(); iterator3.hasNext();) {
						Map<String, Object> img = (Map<String, Object>) iterator3
								.next();
						//app数据处理
						String url = (String) img.get("url");
						url = url.replace("/", "\\");
						//files.add(url);
						String imgName = url.substring(url
								.lastIndexOf("\\") + 1);
						img.put("name", "small"+imgName);
						//后台图片处理
						Map<String, Object> fileMap = new HashMap<String, Object>();
						fileMap.put("name", "small"+imgName);
						fileMap.put("oriName", imgName);
						fileMap.put("url", url);
						fileMap.put("width", img.get("width"));
						fileMap.put("height", img.get("height"));
						fileMap.put("type", 0);//0 图片， 1 音频
						fileMaps.add(fileMap);
					}
				}
				String title = JSONObject.toJSONString(pList);
				question.setTitle(title);

				List<Map<String, Object>> audios = (List<Map<String, Object>>) jsonObject
						.get("audio");
				for (Iterator<Map<String, Object>> iterator2 = audios
						.iterator(); iterator2.hasNext();) {
					Map<String, Object> audio = (Map<String, Object>) iterator2
							.next();
					question.setIsVoice(1);
					String url = (String) audio.get("url");
					url = url.replace("/", "\\");
					//files.add(url);
					String audioName = url.substring(url.lastIndexOf("\\") + 1);
					audio.put("name", audioName);
					//后台图片处理
					Map<String, Object> fileMap = new HashMap<String, Object>();
					fileMap.put("name", audioName);
					fileMap.put("oriName", audioName);
					fileMap.put("url", url);
					fileMap.put("type", 1);//0 图片， 1 音频
					fileMaps.add(fileMap);
				}

				String voice = JSONObject.toJSONString(audios);
				question.setVoice(voice);

				if (questionType == 1 || questionType == 2 || questionType == 6) {
					List<Map<String, Object>> results = (List<Map<String, Object>>) jsonObject
							.get("result");
					for (Iterator<Map<String, Object>> iterator2 = results
							.iterator(); iterator2.hasNext();) {
						Result result = new Result();
						Map<String, Object> res = (Map<String, Object>) iterator2
								.next();
						result.setAnswer(res.get("answer").toString());
						result.setContent(res.get("content").toString());
						result.setScore(0);
						result.setCreateDate(new Date());
						result.setHead(res.get("sortNo").toString());
						result.setModifyDate(new Date());
						result.setQuestion(question);
						resultList.add(result);
					}
				}else if(questionType == 3 || questionType == 4){
					
					Pattern pattern = Pattern.compile("<@T id='([0-9]*?)' score='([0-9\\.]*?)'>(.*?)<@/T>");
					Matcher matcher = pattern.matcher(qItemJson);
					while(matcher.find()){
						Result result = new Result();
						result.setAnswer(matcher.group(3).trim().replaceAll(" +", " "));
						result.setContent(matcher.group(3).trim().replaceAll(" +", " "));
						result.setScore(Float.valueOf(matcher.group(2)));
						result.setCreateDate(new Date());
						result.setHead(matcher.group(1));
						result.setModifyDate(new Date());
						result.setQuestion(question);
						resultList.add(result);
					}
				}
				
				question.setResultList(resultList);
				if (count == 0) {
					parent = question;
					count++;
				} else {
					question.setParentQuestion(parent);
					count++;
				}

				saveQuestion(question);
				int questionId = question.getiD();
				qItemCnt = qItemCnt.replace("upload\\temp\\", "upload\\questionOri\\"+ questionId % 15
						+ File.separatorChar + questionId % 16
						+ File.separatorChar + questionId + File.separatorChar).replace("upload/temp/", "upload\\questionOri\\"+ questionId % 15
								+ File.separatorChar + questionId % 16
								+ File.separatorChar + questionId + File.separatorChar);
				saveQuestionHtml(questionId, qItemCnt);

				
				/***************** 将临时文件夹数据转移到question文件夹 ***********************/
				File questionDir = new File(quesPath + questionId % 15
						+ File.separatorChar + questionId % 16
						+ File.separatorChar + questionId);
				File questionOriDir = new File(quesOriPath + questionId % 15
						+ File.separatorChar + questionId % 16
						+ File.separatorChar + questionId);
				if (!questionDir.exists()) {
					questionDir.mkdirs();
				}
				if (!questionOriDir.exists()) {
					questionOriDir.mkdirs();
				}
				for (Map<String, Object> map : fileMaps) {
					//文件临时路径
					String filePath = (String) map.get("url");
					filePath = filePath.replace("task", "");
					File file = new File(appPath + filePath);
					//文件名
					String fileName = (String) map.get("name");
					String oriName = (String) map.get("oriName");
					//将文件保存到原始文件夹
					FileUtils.copyFileToDirectory(file, questionOriDir);
					if ((Integer)map.get("type")==1) {
						//音频文件
						FileUtils.copyFileToDirectory(new File(questionOriDir.getAbsolutePath()+File.separatorChar+fileName), questionDir);
					} else {
						//图片文件
						int width = (Integer) map.get("width");
						int height = (Integer) map.get("height");
						changeSize(questionOriDir.getAbsolutePath()+File.separatorChar+oriName, questionDir.getAbsolutePath()+File.separatorChar+fileName, width, height);		
					}
				}
				
				try {
					//文件转移成功后，将临时文件删除
					for (Map<String, Object> map : fileMaps) {
						//文件临时路径
						String filePath = (String) map.get("url");
						if (filePath.contains("temp")) {
							filePath = filePath.replace("task", "");
							File file = new File(appPath + filePath);
							file.delete();
						}
					}
				} catch (Exception e) {
					System.out.println("临时数据删除失败");
				}
				/***************** 将临时文件夹数据转移到question文件夹 ***********************/
			}
			if (count>1) {
				countScore(parent.getiD());
			}
			data.put("code", 100);
			data.put("message", "修改成功");
			return data;
		} catch (FileNotFoundException e) {
			throw new QuestionException(101, "文件未找到："+e.getMessage());
		} catch (Exception e) {
			throw new QuestionException(101, "录入异常："+e.getMessage());
		}
	}
	
	public Map<String,Object> saveQuestionsEx(HttpServletRequest request, String appPath, String quesPath,
			String quesOriPath, String quesOriTempPath, List<Map<String, Object>> parse, String grade, String subject,
			String cate, String parentId) {
		Map<String, Object> data = new HashMap<String, Object>();
		if (StringUtils.isEmpty(parentId)) {
			data.put("code", 101);
			data.put("message", "参数错误");
			return data;
		}
		List<Work> worksByQ = workService.getWorksByQ(Integer.valueOf(parentId));
		if (worksByQ!=null&&worksByQ.size()>0) {
			Work work = worksByQ.get(0);
			Map<String, Object> lessonInfo = lessonService.getLessonInfo(work.getLesson().getiD());
			data.put("code", 101);
			data.put("message", "题目被"+lessonInfo.get("year")+lessonInfo.get("periodName")+lessonInfo.get("subjectName")+lessonInfo.get("gradeName")+lessonInfo.get("cateName")+"第"+lessonInfo.get("sortNo")+"课引用，不允许修改");
			return data;
		}
		String qItemIdList = request.getParameter("qItemIdList");
		if (!StringUtils.isEmpty(qItemIdList) && !"[null]".equals(qItemIdList)) {
			List<String> ids = (List<String>) JSONObject.parse(qItemIdList);
			for (Iterator<String> iterator = ids.iterator(); iterator.hasNext();) {
				String delId = (String) iterator.next();
				boolean delflag = delQuestion(Integer.valueOf(delId));
				if (!delflag) {
					data.put("code", 101);
					data.put("message", "题目被引用，不允许修改");
					return data;
				}
			}
		}
		
		Hierarchy hierarchy = hierarchyService.getHierarchy(grade, subject,
				cate);
		Question parent = getQuestion(Integer.valueOf(parentId));
		try {
			for (Iterator<Map<String, Object>> iterator = parse.iterator(); iterator
					.hasNext();) {
				
				// 存储临时文件夹的文件路径
				//List<String> files = new ArrayList<String>();
				List<Map<String,Object>> fileMaps = new ArrayList<Map<String,Object>>();

				Map<String, Object> item = (Map<String, Object>) iterator
						.next();
				
				String qItemJson = (String) item.get("qItemJson");
				qItemJson = qItemJson.replace("&lt;", "<").replace("&gt;", ">").replace("&nbsp;", " ").replace("&amp;", "&");
				String qItemCnt = (String) item.get("qItemCnt");
				if (StringUtils.isEmpty(qItemJson)) {
					continue;
				}
				qItemCnt = qItemCnt.replace("&lt;", "<").replace("&gt;", ">");
				
				Map<String, Object> jsonObject = (Map<String, Object>) JSONObject
						.parse(qItemJson);
				Question question = new Question();
				List<Result> resultList = new ArrayList<Result>();
				Object qId = jsonObject.get("questionId");
				
				//判断是修改还是新增
				boolean flag = false;
				if (qId==null || StringUtils.isEmpty((String)qId)) {
					question = new Question();
					//从session获取
					question.setOaId((Integer) request.getSession().getAttribute("oaId"));
					question.setCreateDate(new Date());
					question.setIsVoice(0);
					flag = true;
				}else{
					question = getQuestion(Integer.valueOf((String)qId));
					//需要先删除题目的答案，要不会重复
					deleteResults(Integer.valueOf((String)qId));
					
				}
				question.setEditor((Integer) request.getSession().getAttribute("oaId"));
				question.setHierarchy(hierarchy);
				question.setModifyDate(new Date());
				question.setStandardTime(Integer.valueOf((String) jsonObject
						.get("standTime")));
				
				Object score = jsonObject
						.get("score");
				if (score!=null) {
					if (score instanceof BigDecimal) {
						question.setScore(((BigDecimal) score).doubleValue());
					}else{
						question.setScore((Integer) score);
					}
				}
				Integer questionType = Integer.valueOf((String) jsonObject
						.get("questionType"));
				question.setQuestionType(questionType);
				
				Integer answerLength = (Integer) jsonObject
						.get("answerLength");
				question.setAnswerLength(answerLength);

				Object analyzation = jsonObject.get("analysis");
				if (analyzation != null) {
					question.setAnalyzation((String) analyzation);
				}
				Object brief = jsonObject.get("questionTitle");
				if (brief != null) {
					question.setBrief((String) brief);
				}

				List<Map<String, Object>> pList = (List<Map<String, Object>>) jsonObject
						.get("contents");
				for (Iterator<Map<String, Object>> iterator2 = pList
						.iterator(); iterator2.hasNext();) {
					Map<String, Object> pModule = (Map<String, Object>) iterator2
							.next();
					List<Map<String, Object>> imgs = (List<Map<String, Object>>) pModule
							.get("imgs");
					for (Iterator<Map<String, Object>> iterator3 = imgs
							.iterator(); iterator3.hasNext();) {
						Map<String, Object> img = (Map<String, Object>) iterator3
								.next();
						String url = (String) img.get("url");
						url = url.replace("/", "\\");
						/*if (url.contains("temp")) {
							files.add(url);
						}*/
						String imgName = url.substring(url
								.lastIndexOf("\\") + 1);
						img.put("name", "small"+imgName);
						//后台图片处理
						Map<String, Object> fileMap = new HashMap<String, Object>();
						fileMap.put("name", "small"+imgName);
						fileMap.put("oriName", imgName);
						fileMap.put("url", url);
						fileMap.put("width", img.get("width"));
						fileMap.put("height", img.get("height"));
						fileMap.put("type", 0);//0 图片， 1 音频
						fileMaps.add(fileMap);
					}
				}
				String title = JSONObject.toJSONString(pList);
				question.setTitle(title);

				List<Map<String, Object>> audios = (List<Map<String, Object>>) jsonObject
						.get("audio");
				for (Iterator<Map<String, Object>> iterator2 = audios
						.iterator(); iterator2.hasNext();) {
					Map<String, Object> audio = (Map<String, Object>) iterator2
							.next();
					question.setIsVoice(1);
					String url = (String) audio.get("url");
					/*if (url.contains("temp")) {
						files.add(url);
					}*/
					url = url.replace("/", "\\");
					String audioName = url.substring(url.lastIndexOf("\\") + 1);
					audio.put("name", audioName);
					//后台图片处理
					Map<String, Object> fileMap = new HashMap<String, Object>();
					fileMap.put("name", audioName);
					fileMap.put("oriName", audioName);
					fileMap.put("url", url);
					fileMap.put("type", 1);//0 图片， 1 音频
					fileMaps.add(fileMap);
				}

				String voice = JSONObject.toJSONString(audios);
				question.setVoice(voice);

				if (questionType == 1 || questionType == 2 || questionType == 6) {
					List<Map<String, Object>> results = (List<Map<String, Object>>) jsonObject
							.get("result");
					for (Iterator<Map<String, Object>> iterator2 = results
							.iterator(); iterator2.hasNext();) {
						Result result = new Result();
						Map<String, Object> res = (Map<String, Object>) iterator2
								.next();
						result.setAnswer(res.get("answer").toString());
						result.setContent(res.get("content").toString());
						result.setScore(0);
						result.setCreateDate(new Date());
						result.setHead(res.get("sortNo").toString());
						result.setModifyDate(new Date());
						result.setQuestion(question);
						resultList.add(result);
					}
				}else if(questionType == 3 || questionType == 4){
					
					Pattern pattern = Pattern.compile("<@T id='([0-9]*?)' score='([0-9\\.]*?)'>(.*?)<@/T>");
					Matcher matcher = pattern.matcher(qItemJson);
					while(matcher.find()){
						Result result = new Result();
						result.setAnswer(matcher.group(3).trim().replaceAll(" +", " "));
						result.setContent(matcher.group(3).trim().replaceAll(" +", " "));
						result.setScore(Float.valueOf(matcher.group(2)));
						result.setCreateDate(new Date());
						result.setHead(matcher.group(1));
						result.setModifyDate(new Date());
						result.setQuestion(question);
						resultList.add(result);
					}
				}
				
				question.setResultList(resultList);
				if (qId .equals(parentId)) {
					question.setParentQuestion(null);
				} else {
					question.setParentQuestion(parent);
				}
				
				
				
				if (flag) {
					saveQuestion(question);
				}else{
					question.setiD(Integer.valueOf((String)qId));
					updateQuestion(question);
				}
				int questionId = question.getiD();
				qItemCnt = qItemCnt.replace("upload\\question\\", "upload\\questionOri\\").replace("upload/question/","upload/questionOri/").replace("upload\\temp\\", "upload\\questionOri\\"+ questionId % 15
						+ File.separatorChar + questionId % 16
						+ File.separatorChar + questionId + File.separatorChar).replace("upload/temp/", "upload\\questionOri\\"+ questionId % 15
								+ File.separatorChar + questionId % 16
								+ File.separatorChar + questionId + File.separatorChar);
				if (flag) {
					saveQuestionHtml(questionId, qItemCnt);
				} else {
					updateQuestionHtml(questionId, qItemCnt);
				}

				/***************** 将临时文件夹数据转移到question文件夹 ***********************/
				File questionDir = new File(quesPath + questionId % 15
						+ File.separatorChar + questionId % 16
						+ File.separatorChar + questionId);
				File questionOriDir = new File(quesOriPath + questionId % 15
						+ File.separatorChar + questionId % 16
						+ File.separatorChar + questionId);
				File questionOriTempDir = new File(quesOriTempPath + questionId % 15
						+ File.separatorChar + questionId % 16
						+ File.separatorChar + questionId);
				if (!questionDir.exists()) {
					questionDir.mkdirs();
				}
				if (!questionOriDir.exists()) {
					questionOriDir.mkdirs();
				}
				if (!questionOriTempDir.exists()) {
					questionOriDir.mkdirs();
				}
				//将文件保存到原始文件夹的备用文件夹
				for (Map<String, Object> map : fileMaps) {
					//文件临时路径
					String filePath = (String) map.get("url");
					filePath = filePath.replace("task", "");
					File file = new File(appPath + filePath);
					//文件名
					FileUtils.copyFileToDirectory(file, questionOriTempDir);
				}
				
				//删除原始文件夹，可以清除冗余数据
				FileUtils.deleteQuietly(questionOriDir);
				//删除题库文件夹，可以清除冗余数据
				FileUtils.deleteQuietly(questionDir);
				
				//重新创建原始文件夹
				if (!questionOriDir.exists()) {
					questionOriDir.mkdirs();
				}
				//重新创建题库文件夹
				if (!questionDir.exists()) {
					questionDir.mkdirs();
				}

				//将备用文件夹的数据转移到原始文件夹
				for (Map<String, Object> map : fileMaps) {
					//String oriName = (String) map.get("oriName");
					FileUtils.copyFileToDirectory(new File(questionOriTempDir.getAbsolutePath()+File.separatorChar+map.get("oriName")), questionOriDir);
				}
				
				//将数据转化放入题库文件夹
				for (Map<String, Object> map : fileMaps){
					String fileName = (String) map.get("name");
					String oriName = (String) map.get("oriName");
					if ((Integer)map.get("type")==1) {
						//音频文件
						FileUtils.copyFileToDirectory(new File(questionOriDir.getAbsolutePath()+File.separatorChar+oriName), questionDir);
					} else {
						//图片文件
						Object object = map.get("width");
						Object object2 = map.get("height");
						int width = 0;
						int height = 0;
						if (object instanceof BigDecimal) {
							width=((BigDecimal) object).intValue();
						}else{
							width=(Integer) object;
						}
						if (object2 instanceof BigDecimal) {
							height=((BigDecimal) object2).intValue();
						}else{
							height=(Integer) object2;
						}
						changeSize(questionOriDir.getAbsolutePath()+File.separatorChar+oriName, questionDir.getAbsolutePath()+File.separatorChar+fileName, width, height);		
					}
				}
				
				//删除备用文件夹，可以清除冗余数据
				FileUtils.deleteQuietly(questionOriTempDir);
				
				try {
					//文件转移成功后，将临时文件删除
					for (Map<String, Object> map : fileMaps) {
						//文件临时路径
						String filePath = (String) map.get("url");
						filePath = filePath.replace("task", "");
						if (filePath.contains("temp")) {
							File file = new File(appPath + filePath);
							file.delete();
						}
					}
				} catch (Exception e) {
					System.out.println("临时数据删除失败");
				}
				/***************** 将临时文件夹数据转移到question文件夹 ***********************/
			}
			if (parent.getQuestionType()==0) {
				countScore(parent.getiD());
			}
			data.put("code", 100);
			data.put("message", "修改成功");
			return data;
		} catch (FileNotFoundException e) {
			throw new QuestionException(101, "文件未找到："+e.getMessage());
		} catch (Exception e) {
			throw new QuestionException(101, "录入异常："+e.getMessage());
		}
	}
	
	/**
     * 不按照比例，指定大小进行缩放
     * 
     * @throws IOException
     */
    private void changeSize(String oldPath,String newPath,int width,int height) throws IOException {
        /**
         * keepAspectRatio(false) 默认是按照比例缩放的
         */
        Thumbnails.of(oldPath).size(width*2, height*2).keepAspectRatio(false).toFile(newPath);
    }
}
	

