package com.skyedu.controller.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skyedu.service.RankService;
import com.skyedu.service.ResultCardService;
import com.skyedu.service.UserService;
import com.util.DateUtil;

@Controller
@RequestMapping("/appRank")
public class AppRankController {

	@Autowired
	private RankService rankService;
	@Autowired
	private ResultCardService resultCardService;
	
	@RequestMapping("/getRankData")
	@ResponseBody
	public Map<String,Object> getRankData(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		String studentId = request.getParameter("userStudentId");
		String grade = request.getParameter("grade");
		String subject = request.getParameter("subject");
		String cate = request.getParameter("cate");
		String week = request.getParameter("week");
		if (StringUtils.isEmpty(studentId)) {
			map.put("message", "参数错误");
			map.put("code", 101);
			return map;
		}
		if (StringUtils.isEmpty(week)) {
			week="0";
		}
		Map<String,Object> condition = new HashMap<String, Object>();
		condition.put("studentId", Integer.parseInt(studentId));
		condition.put("grade", grade);
		condition.put("subject", subject);
		condition.put("cate", cate);
		condition.put("week", Integer.parseInt(week));
		Map<String, Object> student = rankService.getStudent(condition);
		if (student!=null) {
			condition.put("week", Integer.parseInt(week)-1);
			List<Map<String, Object>> resultCardList = resultCardService.getResultCardListWeek(condition);
			student.put("categoryList", resultCardList);
			student.put("iD", student.get("id"));
			Object object = student.get("sex");
			if(object==null || StringUtils.isEmpty((String)object)){
				student.put("sex", 2);
			}else if("男".equals((String)object)){
				student.put("sex", 0);
			}else if("女".equals((String)object)){
				student.put("sex", 1);
			}else{
				student.put("sex", 2);
			}
			
			
			Date createDate = (Date) student.get("createDate");
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyyMMdd", Locale.getDefault());
		    Calendar cal = Calendar.getInstance();  
		    cal.setTime(createDate); 
		    // 获取星期日结束时间戳  
	    	cal.add(Calendar.DATE, 7);
	    	cal.set(Calendar. DAY_OF_WEEK, Calendar.SUNDAY );  
		    String time = simpleDateFormat.format(cal.getTime()) + " 23:59:59.999";
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS");
		    Date validTime = sdf.parse(time);
		    student.put("validTime", validTime);
		    
			Map<String, Object> studentOld = rankService.getStudent(condition);
			if (studentOld!=null) {
				student.put("oldRank", studentOld.get("rank"));
			}else{
				student.put("oldRank", null);
			}
		}
		map.put("data", student);
		map.put("code", 100);
		return map;
	}
	
	@RequestMapping("/getRankList")
	@ResponseBody
	public Map<String,Object> getRankList(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> map = new HashMap<String, Object>();
		String grade = request.getParameter("grade");
		String subject = request.getParameter("subject");
		String cate = request.getParameter("cate");
		String pageNo = request.getParameter("pageNo");
		String week = request.getParameter("week");
		Map<String,Object> condition = new HashMap<String, Object>();
		if (StringUtils.isEmpty(week)) {
			week="0";
		}
		if (StringUtils.isEmpty(pageNo)) {
			pageNo = "1";
		}
		condition.put("grade", grade);
		condition.put("subject", subject);
		condition.put("cate", cate);
		condition.put("pageNo", Integer.parseInt(pageNo));
		condition.put("week", Integer.parseInt(week));
		List<Map<String, Object>> rankStudentList = rankService.getRankStudentList(condition);
		for (Map<String, Object> map2 : rankStudentList) {
			Object object = map2.get("sex");
			if(object==null || StringUtils.isEmpty((String)object)){
				map2.put("sex", 2);
			}else if("男".equals((String)object)){
				map2.put("sex", 0);
			}else if("女".equals((String)object)){
				map2.put("sex", 1);
			}else{
				map2.put("sex", 2);
			}
		}
		map.put("code", 100);
		map.put("data", rankStudentList);
		return map;
	}
}
