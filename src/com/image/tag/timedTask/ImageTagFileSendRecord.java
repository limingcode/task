package com.image.tag.timedTask;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.image.tag.service.ImageTagService;
import com.image.tag.utils.ImageTagUtils;

@Component
public class ImageTagFileSendRecord {
	
	@Resource
	private ImageTagService imageTagService;

	/**
	 * 每天执行一次
	 * @throws Exception 
	 */
//	@Scheduled(cron = "0 0/5 * * * ?")
	public void deleteFileRecord() {
		List<Map<String, Object>> list = imageTagService.getSendFailBook();
		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				String bookId = String.valueOf(map.get("id"));
				String period = "76";
				String bookName = String.valueOf(map.get("bookName"));
				String filePath = ImageTagUtils.getZipFile(bookId, period, bookName);
				imageTagService.sendBook(bookId, period, bookName, filePath);
			}
		}
	}
	
}
