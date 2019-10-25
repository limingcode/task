package com.skyedu.controller.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.skyedu.service.WxServiceTest;

@Controller
@RequestMapping("/wxtask")
public class WxTestController {

	@Autowired
	private WxServiceTest wxServiceTest;
	
	@RequestMapping("/send")
	public void send() throws Exception {
		wxServiceTest.getWxOpenIDs();
	}
}
