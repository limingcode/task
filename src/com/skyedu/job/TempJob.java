package com.skyedu.job;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TempJob {

	//每月凌晨零点进行排行
	/*@Scheduled(cron = "0 0 0 1 * ?")*/
	public void setRankData(){
		System.out.println("清除任务执行");
		String path = System.getProperty("user.dir") + File.separator + ".." + File.separator + "webapps";
		File file = new File(path+File.separatorChar+"task"+File.separatorChar+"upload"+File.separatorChar+"temp");
		FileUtils.deleteQuietly(file);
	}
}
