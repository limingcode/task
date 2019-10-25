package com.skyedu.job;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.skyedu.model.MessageS;
import com.skyedu.service.CourseService;
import com.skyedu.service.MessageService;
import com.util.JpushClientUtil;

/**
 * 信息定时发布
 * @author admin
 *
 */
@Component
public class MessageJob {

	@Autowired
	private MessageService messageService;
	@Autowired
	private CourseService courseService;
	
	/**
	 * 每隔5分钟执行一次
	 */
	@Scheduled(cron = "0 0/5 * * * ?")
	public void publishMessage(){
		List<Map<String, Object>> messageList = messageService.getUnpushMessageList();
		for (Iterator<Map<String, Object>> iterator = messageList.iterator(); iterator.hasNext();) {
			Map<String, Object> map = (Map<String, Object>) iterator.next();
			
			//循环遍历学生推送信息
			List<Map<String, Object>> studentList = courseService.getStudentList((Integer) map.get("course"),true);
			for (Iterator<Map<String, Object>> iterator2 = studentList.iterator(); iterator2
					.hasNext();) {
				Map<String, Object> student = (Map<String, Object>) iterator2
						.next();
				MessageS messageS = new MessageS();
				messageS.setMessage((Integer) map.get("id"));
				messageS.setStudent((Integer) student.get("id"));
				messageS.setState(0);
				messageService.saveMessageS(messageS);
				//向app推送消息
				JpushClientUtil.sendToAliasIos(student.get("id").toString(), (String) map.get("title"), (String) map.get("title"), (String) map.get("message"), "");
			}
		}
	}
}
