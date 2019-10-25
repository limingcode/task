/*package com.skyedu.controller.task;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

*//**
 * 微信公众号Controller
 * @author Jinmingming jin_ming_ming@qq.com
 * @date 2015-10-13
 *//*
@Controller("taskWXController")
@RequestMapping("/wx")
public class WXController extends BaseController {
	
	private static final String QRURL = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=";

	@Resource 
	private WeChatService weChatService;

	@Resource 
	private WeChatMessageService weChatMessageService;
	
	@Resource 
	private EmployeeService employeeService;
	
	*//**
	 * 接口配置信息
	 * @param request
	 * @param response
	 * @param model
	 *//*
	@RequestMapping(value = "/receiveMessage", method = { RequestMethod.GET,
			RequestMethod.POST})
	@ResponseBody
	public void receiveMessage(HttpServletRequest request,HttpServletResponse response, ModelMap model) {
		// 验证服务器地址的有效性
		if (request.getMethod().toLowerCase().equals("get")) {
			try {
				if (weChatService.checkSignature(request.getParameter("signature"), request.getParameter("timestamp"), request.getParameter("nonce"))) {
					response.getWriter().write(request.getParameter("echostr"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				// 处理接收消息
				ServletInputStream in = request.getInputStream();
				// 将POST流转换为XStream对象
				XStream xs = new XStream(new DomDriver());
				// 将指定节点下的xml节点数据映射为对象
				xs.alias("xml", InputMessage.class);
				// 将流转换为字符串
				StringBuilder xmlMsg = new StringBuilder();
				byte[] b = new byte[4096];
				for (int n; (n = in.read(b)) != -1;) {
					xmlMsg.append(new String(b, 0, n, "UTF-8"));
				}
				// 将xml内容转换为InputMessage对象
				InputMessage inputMsg = (InputMessage) xs.fromXML(xmlMsg.toString());
				// 取得消息类型
				String msgType = inputMsg.getMsgType();
				// 取得事件类型
				String event = inputMsg.getEvent();
				// 根据消息类型获取对应的消息内容
				String toUserName = inputMsg.getToUserName();
				String fromUserName = inputMsg.getFromUserName();
				if (msgType.equals(MsgType.Event.toString())) {// 事件推送
					if (event.equals(Event.Subscribe.toString()) || event.equals(Event.Scan.toString())) {// 订阅与已订阅
						String eventKey = inputMsg.getEventKey();
						String ticket = inputMsg.getTicket();
						if (eventKey != null && ticket != null) {// 扫描带参数二维码事件
							// 得到事件KEY值
							Integer id = eventKey.contains("qrscene_") ? Integer.parseInt(eventKey.replace("qrscene_", "")) : Integer.parseInt(eventKey);
							Employee employee = employeeService.findByOpenid(fromUserName);
							Employee employee1 = employeeService.findById(id);
							if (employee1 != null) {
								if (employee == null) {
									// 进行绑定
									employeeService.updateOpenid(id, fromUserName);
									employee = employeeService.findByOpenid(fromUserName);
								} 
								String content = SpringUtils.getMessage("wechat.message.userBind",
										new Object[] { employee.getName(), employee.getId(), id });
								// 发送用户基本信息
								response.getWriter().write(weChatMessageService.sendTextMsg(fromUserName, toUserName, content));
							} else {
								// 扫描信息不存在
								response.getWriter().write(SpringUtils.getMessage("wechat.message.userNotExist", null));
							}
							
						} else {//关注事件
						}
					}
				}else if(msgType.equals(MsgType.Text.toString())){
					String mess = inputMsg.getContent();
					
					if(!com.skyedu.util.StringUtil.isEmpty(mess)&&mess.trim().equals("我爱你")){
						//已经绑定了手机号码的用户
						String ret = "秋续寒奖金游戏规则\n";
						ret += "1、本次秋续寒奖金以班级为单位发放。\n";
						ret += "2、秋季在读班级人数定6人-20人15个档次，其中班级人数超过20人的统一归纳20人档次。\n";
						ret += "3、本次数据读取日期：寒假每期的前3次课结束。\n";
						ret += "4、本次方案数据指标分三个板块，分别为英语1-4年级，英语毕业班6和9年级，英语其它年级及非英语学科。";
						response.getWriter().write(weChatMessageService.sendTextMsg(fromUserName, toUserName, ret));
					}else if(!com.skyedu.util.StringUtil.isEmpty(mess)&&mess.trim().equals("运营")){
						List<Item> articles = new ArrayList<Item>();
						String picUrl = "https://mmbiz.qlogo.cn/mmbiz_jpg/uEkeVGASlRib5GSrYCM8oLkEfFXw8hhra4rdaXplUQ2XyKaGgKebfsPCVsTbuCCEZBGO0H5hiac5HAUdictSp8iaxg/0?wx_fmt=jpeg";
						String description = "蓝天校历-运营";
						String title = "蓝天校历-运营";
						String url = "http://mp.weixin.qq.com/s?__biz=MzI5MTA3Mjc0NQ==&mid=505525862&idx=1&sn=e02f3a3045476df43a4f0b7aa88f5577&chksm=77c329e040b4a0f66f8d298ed5b2322cbc765bb09d8c375b9a9c4aef57c81b9e5f70f4d6f79e&mpshare=1&scene=1&srcid=1115nbdQfegdeZ8F6VxgFxxm#wechat_redirect";
						articles.add(new Item(title, description, picUrl, url));
						//Logger.getLogger(this.getClass()).error(weChatMessageService.sendImgMsg(toUserName, fromUserName, articles));
						response.getWriter().write(weChatMessageService.sendImgMsg(fromUserName, toUserName, articles));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	*//**
	 * 获得二维码
	 * @param id 用户身份
	 * @param model
	 * @return
	 *//*
	@RequestMapping(value = "/getQrUrl", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getQrUrl(Integer id, ModelMap model) {
		Map<String, Object> map = new HashMap<String, Object>();
		Employee employee = employeeService.findById(id);
		// 验证二维码信息是否有效
		if (employee.getQrcodeTicket() != null && (new Date().before(employee.getQrcodeTicketExpire()))) {
			map.put("message", Message.success("", null));
			map.put("qrUrl", QRURL + employee.getQrcodeTicket());
			return map;
		}
		try {
			JSONObject json = weChatService.createTicket("QR_SCENE", id.toString(), null);
			if (json.containsKey("errcode")) {
				map.put("message", Message.error(json.getString("errmsg"), null));
			} else {
				String ticket = json.getString("ticket");
				String expire_seconds = json.getString("expire_seconds");
//				String url = json.getString("url");
				// 更新二维码信息
				employeeService.updateQrcodeTicket(id, ticket, DateUtils.addSeconds(new Date(), Integer.parseInt(expire_seconds)));
				map.put("message", Message.success("", null));
				map.put("qrUrl", QRURL + ticket);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
}
*/