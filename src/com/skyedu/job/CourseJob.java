package com.skyedu.job;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.skyedu.service.CourseService;
import com.util.CommonUtil;
import com.util.HttpClientUtils;

@Component
public class CourseJob {
	
	@Autowired
	private CourseService courseService;

	/**
	 * 每隔5分钟执行一次
	 * @throws Exception 
	 */
	/*@Scheduled(cron = "0 0 0/2 * * ?")
	public void pushAppZip() throws Exception {
		List<Map<String, Object>> courseList = courseService.getCourseList();
		for (Iterator<Map<String, Object>> iterator = courseList.iterator(); iterator.hasNext();) {
			Map<String, Object> map = (Map<String, Object>) iterator.next();
			Map<String,String> params = new HashMap<String, String>();
			params.put("courseId", ((Integer)map.get("id")).toString());
			String post = HttpClientUtils.post(CommonUtil.COURSETIMEURL, params);
			Map<String,Object> parse = (Map<String, Object>) JSONObject.parse(post);
			Object object = parse.get("rows");
			String courseTime = "";
			if (object!=null && !StringUtils.isEmpty(object.toString())) {
				List<Map<String,Object>> object1 = (List<Map<String, Object>>) object;
				courseTime = (String) object1.get(0).get("CourseTime");
			}
			courseService.setCourseTime(courseTime, (Integer)map.get("id"));
		}	
	}*/
}
