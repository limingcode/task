package com.image.tag.utils;

import com.image.tag.dao.ImageTagDao;
import com.image.tag.model.ImBook;
import com.skyedu.model.book.SendBookJsonRecordModel;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.util.Date;

public class ImageSendJsonFileThread extends Thread {

	private int bookId;

	private String period;

	private String jsonFilePath;


	public ImageSendJsonFileThread(int bookId, String period, String jsonFilePath) {
		super();
		this.bookId = bookId;
		this.period = period;
		this.jsonFilePath = jsonFilePath;
	}



	@Override
	public void run() {
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		ImageTagDao imageTagDao = context.getBean(ImageTagDao.class);
        File file = new File(jsonFilePath);
        String fileMd5Code = FileUtil.fileMd5Code(file);
        final SendBookJsonRecordModel sendBookJsonRecordModel = new SendBookJsonRecordModel();
        sendBookJsonRecordModel.setBookId(bookId);
        sendBookJsonRecordModel.setCreateTime(new Date());
        sendBookJsonRecordModel.setJsonCode(fileMd5Code);
        final int i = imageTagDao.saveSendBookJsonRecordModel(sendBookJsonRecordModel);
        ImBook book= imageTagDao.getBook(bookId);
		book.setStatus(ImageTagSendFileUtil.FILE_SEND_STATUS_SENDING);
		book.setMd5JsonCode(fileMd5Code);
	    imageTagDao.saveBook(book);
		int code = ImageTagSendFileUtil.httpPost(i,bookId, period,FileUtil.getBytes(file.getPath()),ImageTagSendFileUtil.FILE_SEND_ADDR_JSON,book.getBookName());
		if(code != 200) {
			imageTagDao.updateBookStatus(bookId, ImageTagSendFileUtil.FILE_SEND_STATUS_UNSEND);
		}
	}
	
}
