package com.skyedu.service;

import java.util.List;
import java.util.Map;

import com.skyedu.model.EduTeacher;
import com.skyedu.model.Message;
import com.skyedu.model.MessageS;
import com.skyedu.sdk.wechat.model.TemplateData;

/**
 * 
 * @author xj.chen
 * @date 2015-11-9
 */
public abstract interface MessageService {

	/**
	 * 消息发送
	 * 
	 * @param type
	 *            消息类型 1积分通知 2故障警告 3待审批通知 4审批结果通知
	 * @param msg
	 *            消息内容
	 * @param toUrl
	 *            点击消息响应的url @@oaId接收者oaid @@userId接收者大富豪id
	 * @param toTeacher
	 *            接收者
	 * @param byTeacher
	 *            发送者
	 * @return
	 */
	public boolean sendMssg(int type, Map<String, TemplateData> msg,
			String toUrl, EduTeacher toTeacher, EduTeacher byTeacher);

	public void saveMessage(Message message);

	public void saveMessageS(MessageS messageS);

	public List<Map<String, Object>> getMessageList(int studentId, int state,int pageNO);

	public List<Map<String, Object>> getMessageList(Map<String, Object> condition);

	public void delMessage(int id);
	
	public void updateMessage(int id);
	
	public int getUnreadCount(int studentId);
	
	public List<Map<String,Object>> getUnpushMessageList();
}
