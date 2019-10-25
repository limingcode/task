package com.skyedu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skyedu.dao.impl.WxDao;
import com.skyedu.service.WxServiceTest;
import com.util.HttpClientUtils;

@Service
public class WxServiceTestImpl implements WxServiceTest{

	@Autowired
	private WxDao wxDao;
	
	@Override
	public void getWxOpenIDs() throws Exception {
		Map<String,String> params = new HashMap<String, String>();
		params.put("moq", "101");
		params.put("msg", "\\n为给SEES用户带来更好的使用体验，我们已在2018年6月22日 10:00进行了服务器升级。 \\n升级后SEES(1.2.18)及更早的版本将停止使用。\\n为了您的权益，请尽快更新至最新版本。\\n注：无需卸载重新安装，直接更新即可。");
		params.put("name", "SEES");
		params.put("url", "http://teaching.skyedu99.com/i/index.html");
		for (int i = 28; ; i++) {
			List<Map<String, Object>> wxOpenIDs = wxDao.getWxOpenIDs(i);
			if (wxOpenIDs==null || wxOpenIDs.size()==0) {
				break;
			}
			for (Map<String, Object> map : wxOpenIDs) {
				if (map.get("useropenId")==null) {
					continue;
				}
				params.put("wxopenid", (String)map.get("useropenId"));
				System.out.println(map.get("studentname")+">>"+map.get("useropenId")+HttpClientUtils.post("http://wx.xuedou.com/sms/wx", params));
				Thread.sleep(500);
			}
		}
	}

	public static void main(String[] args) {
//		String url = "http://wx.xuedou.com/sms/wx?moq=101&wxopenid=oGkbgjhO51gn3JVBOF1Ag7ar3I18&msg=公告:SEES新版本(1.2.0)已发布。为更好的解决老版本遗留问题，建议您先卸载老版本的APP，然后通过App Store重新安装最新版本（直接更新可能导致遗留问题存在，请谅解）。&name=蓝天教育sees";
//		System.out.println(HttpClientUtils.get(url));
		Map<String,String> params = new HashMap<String, String>();
		params.put("moq", "101");
		params.put("wxopenid", "oGkbgjhO51gn3JVBOF1Ag7ar3I18");
		params.put("msg", "为给SEES用户带来更好的使用体验，我们已在2018年6月22日 10:00进行了服务器升级。 \\n升级后SEES(1.2.18)及更早的版本将停止使用。\\n为了您的权益，请尽快更新至最新版本。\\n注：无需卸载重新安装，直接更新即可。");
		params.put("name", "SEES");
		System.out.println(HttpClientUtils.post("http://wx.xuedou.com/sms/wx", params));
	}
}
