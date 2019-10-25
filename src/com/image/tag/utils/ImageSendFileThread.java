package com.image.tag.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.image.tag.dao.ImageTagDao;
import com.image.tag.model.ImBook;

public class ImageSendFileThread extends Thread {
	
	private int bookId;
	
	private String period;
	
	private String zipFilePath;
	
	private String bookPath;

	public ImageSendFileThread(int bookId, String period, String zipFilePath, String bookPath) {
		super();
		this.bookId = bookId;
		this.period = period;
		this.zipFilePath = zipFilePath;
		this.bookPath = bookPath;
	}



	@Override
	public void run() {
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		ImageTagDao imageTagDao = context.getBean(ImageTagDao.class);
		ImBook book= imageTagDao.getBook(bookId);
		File file = new File(zipFilePath);
		long zipSize = FileUtil.zip(file, bookPath);
		book.setSendNum(0);   //主动发送时次数设为0
		book.setStatus(ImageTagSendFileUtil.FILE_SEND_STATUS_SENDING);
		book.setZipSize(zipSize);
		book.setSendTime(new Date());
		book.setUpdateTime(new Date());
		String fileMd5Code = FileUtil.fileMd5Code(file);
		book.setMd5Code(fileMd5Code);
		int id = imageTagDao.saveBook(book);
		
		int code = ImageTagSendFileUtil.httpPost(bookId, period, FileUtil.getBytes(file.getPath()),ImageTagSendFileUtil.FILE_SEND_ADDR);
		if(code != 200) {
			imageTagDao.updateBookStatus(id, ImageTagSendFileUtil.FILE_SEND_STATUS_UNSEND);
		}
	}
	
}
