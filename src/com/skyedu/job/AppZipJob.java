package com.skyedu.job;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.skyedu.model.AppZip;
import com.skyedu.service.AppZipService;
import com.skyedu.service.WorkService;
import com.util.DatabaseContextHolder;
import com.util.FileUtil;

@Component
public class AppZipJob {
	
	@Autowired
	private AppZipService appZipService;

	/**
	 * 每隔5分钟执行一次
	 * @throws Exception 
	 */
	//@Scheduled(cron = "0 */5 * * * ?")
	public void pushAppZip() throws Exception {
		List<Map<String,Object>> appZipList = appZipService.getAppZipList();
		for (Iterator<Map<String,Object>> iterator = appZipList.iterator(); iterator.hasNext();) {
			Map<String,Object> appZip = (Map<String,Object>) iterator.next();
			Integer state = (Integer) appZip.get("state");
			Integer workId = (Integer) appZip.get("workId");
			int lessonId = (Integer) appZip.get("lessonId");
			FileUtil fileUtil = new FileUtil();
			//0为发送，1删除
			if (state==0) {
				File directory = new File("");// 参数为空
				String courseFile = directory.getCanonicalPath();
				System.out.println(courseFile);
				String workPath = courseFile + File.separatorChar + "upload"
						+ File.separatorChar + "lesson" + File.separatorChar
						+ lessonId % 15 + File.separatorChar + lessonId % 16
						+ File.separatorChar + lessonId + File.separatorChar
						+ workId;
				// 将zip包发送给app服务器
				byte[] bytes = fileUtil.getBytes(workPath + ".zip");
				byte[] httpPost = fileUtil.httpPost(bytes, workId, lessonId,state);
				if (httpPost!=null) {
					appZipService.delAppZip((Integer) appZip.get("id"));
				}
			} else {
				byte[] by = new byte[1];
				byte[] httpPost = fileUtil.httpPost(by, workId,lessonId ,state);
				if (httpPost!=null) {
					appZipService.delAppZip((Integer) appZip.get("id"));
				}
			}
			
		}
	}
}
