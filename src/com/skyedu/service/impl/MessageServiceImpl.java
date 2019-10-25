package com.skyedu.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.skyedu.dao.impl.MessageDAO;
import com.skyedu.model.EduTeacher;
import com.skyedu.model.Message;
import com.skyedu.model.MessageS;
import com.skyedu.sdk.wechat.model.Template;
import com.skyedu.sdk.wechat.model.TemplateData;
import com.skyedu.service.MessageService;
import com.skyedu.service.WeChatMessageService;
import com.util.StringUtil;

/**
 * 消息
 * 
 * @author Jinmingming jin_ming_ming@qq.com
 * @date 2015-11-9
 */
@Service
public class MessageServiceImpl implements MessageService {

	@Value("${setting.siteUrl}")
	private String siteUrl;

	@Value("${template1.id}")
	private String template1Id;

	@Value("${template2.id}")
	private String template2Id;
	private String quesUrl = "http://sog.skyedu99.com/index/authorization.jhtml?redirectUrl=http://sog.skyedu99.com/wx/applyScore/qInfo.jhtml?id=";

	@Value("${template3.id}")
	private String template3Id;
	@Value("${template4.id}")
	private String template4Id;
	@Value("${template5.id}")
	private String template5Id;
	@Value("${template6.id}")
	private String template6Id;
	@Value("${template7.id}")
	private String template7Id;
	@Value("${template8.id}")
	private String template8Id;

	@Resource
	private WeChatMessageService weChatMessageService;
	@Autowired
	private MessageDAO messageDAO;

	private String getType(int type) {
		String ret = "未知";
		switch (type) {
		case 0:
			ret = "OA系统";
			break;
		case 1:
			ret = "学员收费管理系统";
			break;
		case 2:
			ret = "蓝天教育官网";
			break;
		case 3:
			ret = "大富豪积分管理(SOG)";
			break;
		case 4:
			ret = "掌上蓝天";
			break;
		case 5:
			ret = "蓝天学助手(CALT)";
			break;
		case 6:
			ret = "教室课件管理系统";
			break;
		case 7:
			ret = "校区电脑硬件及网络";
			break;
		case 8:
			ret = "SR测试管理";
			break;
		case 9:
			ret = "竞争力官网";
			break;
		case 10:
			ret = "竞争力学员系统";
			break;
		case 11:
			ret = "其他";
			break;
		}
		return ret;
	};

	public boolean sendMssg(int type, Map<String, TemplateData> msg,
			String toUrl, EduTeacher toTeacher, EduTeacher byTeacher) {
		String sendModle = "";
		switch (type) {
		case 1:
			sendModle = template1Id;
			break;
		case 2:
			sendModle = template2Id;
			break;
		case 3:
			sendModle = template3Id;
			break;
		case 4:
			sendModle = template4Id;
			break;
		case 5:
			sendModle = template5Id;
			break;
		case 6:
			sendModle = template6Id;
			break;
		case 7:
			sendModle = template7Id;
			break;
		case 8:
			sendModle = template8Id;
			break;
		}
		if (sendModle == null || sendModle == "") {
			return false;
		}
		if (!StringUtil.isEmpty(toUrl))// 装载参数
			toUrl = toUrl.replaceAll("@@_oaId", toTeacher.getOaId() + "")
					.replaceAll("@@_userId", toTeacher.getId() + "")
					.replaceAll("@@@", "%26").replaceAll("@@", "&");
		Template template = new Template();
		template.setTemplate_id(sendModle);
		template.setUrl(toUrl);
		template.setData(msg);
		template.setTouser(toTeacher.getWxOpenId());
		return weChatMessageService.sendTemplate(template);
	}

	@Override
	public void saveMessage(Message message) {
		// TODO Auto-generated method stub
		messageDAO.saveMessage(message);
	}

	@Override
	public void saveMessageS(MessageS messageS) {
		// TODO Auto-generated method stub
		messageDAO.saveMessageS(messageS);
	}

	@Override
	public List<Map<String, Object>> getMessageList(int studentId, int state,int pageNo) {
		// TODO Auto-generated method stub
		return messageDAO.getMessageList(studentId, state, pageNo);
	}

	@Override
	public List<Map<String, Object>> getMessageList(Map<String, Object> condition) {
		// TODO Auto-generated method stub
		return messageDAO.getMessageList(condition);
	}

	@Override
	public void delMessage(int id) {
		// TODO Auto-generated method stub
		messageDAO.delMessage(id);
	}

	@Override
	public void updateMessage(int id) {
		// TODO Auto-generated method stub
		messageDAO.updateMessage(id);
	}

	@Override
	public int getUnreadCount(int studentId) {
		// TODO Auto-generated method stub
		return messageDAO.getUnreadCount(studentId);
	}

	@Override
	public List<Map<String, Object>> getUnpushMessageList() {
		// TODO Auto-generated method stub
		return messageDAO.getUnpushMessageList();
	}

}
