package com.util;

import java.io.IOException;



public class FileThread extends Thread{

	private String path;
	private String url;
	private String fileName;

	public FileThread(String path, String url, String fileName) {
		super();
		this.path = path;
		this.url = url;
		this.fileName = fileName;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		try {
			FileToZip.downLoadFromUrl(url, fileName, path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
