package com.skyedu.job;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.skyedu.model.Message;
import com.skyedu.model.MessageS;
import com.skyedu.service.BaseService;
import com.skyedu.service.MessageService;
import com.skyedu.service.RankService;
import com.util.JpushClientUtil;

@Component
public class RankJob {

	@Autowired
	private BaseService baseService;
	@Autowired
	private RankService rankService;
	@Autowired
	private MessageService messageService;
	
	//每周日晚上十点进行排行
	@Scheduled(cron = "0 0 22 ? * SUN")
	@Transactional
	public void setRankData(){
		System.out.println("排行任务执行");
		//List<Map<String, Object>> depaList = baseService.depaList();
		List<Map<String, Object>> gradeList = baseService.gradeList();
		List<Map<String, Object>> cateList = baseService.cateList();
		List<Map<String, Object>> subjectList = baseService.subjectList();
		
		//赠与称号和发送消息
		for (Iterator<Map<String, Object>> iterator = subjectList.iterator(); iterator.hasNext();) {
			Map<String, Object> subject = (Map<String, Object>) iterator.next();
			for (Iterator<Map<String, Object>> iterator2 = gradeList.iterator(); iterator2
					.hasNext();) {
				Map<String, Object> grade = (Map<String, Object>) iterator2
						.next();
				for (Iterator<Map<String, Object>> iterator3 = cateList.iterator(); iterator3
						.hasNext();) {
					Map<String, Object> cate = (Map<String, Object>) iterator3
							.next();
					//全部校区进行排行
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("grade", grade.get("code"));
					map.put("cate", cate.get("code"));
					map.put("subject", subject.get("code"));
					int rankCount = rankService.getRankCount(map);
					if (rankCount==0) {
						continue;
					}
					long rank1 = ((Double)Math.ceil(rankCount*0.02)).longValue();
					long rank2 = ((Double)Math.ceil(rankCount*0.05)).longValue();
					long rank3 = ((Double)Math.ceil(rankCount*0.10)).longValue();
					//排名在5%到10%的人
					map.put("start", rank2);
					map.put("end", rank3);
					map.put("designtion", "崭露头角");
					rankService.setDesigntion(map);
					//排名在2%到5%的人
					map.put("start", rank1);
					map.put("end", rank2);
					map.put("designtion", "出类拔萃");
					rankService.setDesigntion(map);
					//排名在2%以内的人
					map.put("start", 0l);
					map.put("end", rank1);
					map.put("designtion", "一鸣惊人");
					rankService.setDesigntion(map);
					
				}
				//获取本期有称号的学生
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("grade", grade.get("code"));
				condition.put("subject", subject.get("code"));
				condition.put("week", 0);
				List<Map<String, Object>> studentList = rankService.getStudentList(condition);
				for (Iterator<Map<String, Object>> iterator3 = studentList.iterator(); iterator3
						.hasNext();) {
					Map<String, Object> map = (Map<String, Object>) iterator3
							.next();
					Integer studentId = (Integer) map.get("studentId");
					String subjectName = (String) map.get("subjectName");
					String gradeName = (String) map.get("gradeName");
					String cateName = (String) map.get("cateName");
					String designation = (String) map.get("designation");
					String message = "恭喜你在本周排名"+subjectName+gradeName+cateName+"层次中获得"+designation+"的称号";
					Message mess = new Message();
					mess.setCreateDate(new Date());
					mess.setMessage(message);
					mess.setSender(0);
					mess.setTitle(message);
					String uuid = UUID.randomUUID().toString().replace("-", "");
					mess.setUuid(uuid);
					mess.setOpenTime(new Date());
					mess.setStatus(1);
					messageService.saveMessage(mess);
					MessageS messageS = new MessageS();
					messageS.setMessage(mess.getiD());
					messageS.setStudent(studentId);
					messageS.setState(0);
					messageService.saveMessageS(messageS);
					//向app推送消息
					try {
						JpushClientUtil.sendToAliasIos(studentId.toString(), message, message, message, "");
					} catch (Exception e) {
					}
				}
			}
		}
		
	}
}
