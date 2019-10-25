package com.util;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skyedu.model.Question;
import com.skyedu.model.Work;
import com.skyedu.model.WorkInfo;
import com.skyedu.service.WorkService;

@Component
public class WorkThread extends Thread {

	private String path;
	private int lessonId;
	private Work work;
	private WorkService workService;

	public WorkThread(String path, int lessonId,Work work, WorkService workService) {
		super();
		this.path = path;
		this.lessonId = lessonId;
		this.workService = workService;
		this.work = work;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		work.setZipState(1);
		workService.updateWork(work);
		zip();
		/*Work work = workService.getWorkBean(workId);
		if (work.getZipDate() == null
				|| work.getModifyDate().getTime() != work.getZipDate()
						.getTime()) {
			try {
				Integer lessonId = work.getLesson();
				String workPath = path + File.separatorChar + "upload"
						+ File.separatorChar + "lesson" + File.separatorChar
						+ lessonId % 15 + File.separatorChar + lessonId % 16
						+ File.separatorChar + lessonId + File.separatorChar
						+ workId;
				String questionPath = path + File.separatorChar + "upload"
						+ File.separatorChar + "question" + File.separatorChar;
				List<WorkInfo> workInfoList = workService.getWorkInfoList(
						workId, true);
				*//*************** 复制文件 ****************//*
				File workDir = new File(workPath);
				if (!workDir.exists()) {
					workDir.mkdirs();
				}
				for (Iterator<WorkInfo> iterator = workInfoList.iterator(); iterator
						.hasNext();) {
					WorkInfo workInfo = (WorkInfo) iterator.next();
					Question question = workInfo.getQuestion();
					int questionId = question.getiD();
					FileToZip.copy(questionPath + questionId % 15
							+ File.separatorChar + questionId % 16
							+ File.separatorChar + questionId, workPath
							+ File.separatorChar + questionId);
				}
				*//*************** 复制文件 ****************//*
				*//*************** 打包work ****************//*
				FileToZip fileToZip = new FileToZip();
				File file = new File(workPath + ".zip");
				if (file.exists()) {
					file.delete();
				}
				fileToZip.zip(file, workPath);
				try {
					FileUtils.deleteDirectory(new File(workPath));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("组卷文件夹删除失败");
				}
				*//*************** 打包work ****************//*
				// 更新打包版本时间
				work.setZipDate(work.getModifyDate());
				work.setSourceSize(file.length());
				workService.updateWork(work);
				
				// 将zip包发送给app服务器
				FileUtil fileUtil = new FileUtil();
				byte[] bytes = fileUtil.getBytes(workPath + ".zip");
				fileUtil.httpPost(bytes, workId ,0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("组卷打包失败");
			}
		}*/
	}
	
	private synchronized void zip(){
			try {
				String workPath = path + File.separatorChar + "upload"
						+ File.separatorChar + "lesson" + File.separatorChar
						+ lessonId % 15 + File.separatorChar + lessonId % 16
						+ File.separatorChar + lessonId + File.separatorChar
						+ work.getiD();
				String questionPath = path + File.separatorChar + "upload"
						+ File.separatorChar + "question" + File.separatorChar;
				List<WorkInfo> workInfoList = workService.getWorkInfoList(
						work.getiD(), true);
				if (workInfoList==null || workInfoList.size()==0) {
					work.setZipState(2);
					workService.updateWork(work);
					return;
				} 
				/*************** 复制文件 ****************/
				File workDir = new File(workPath);
				if (!workDir.exists()) {
					workDir.mkdirs();
				}
				for (Iterator<WorkInfo> iterator = workInfoList.iterator(); iterator
						.hasNext();) {
					WorkInfo workInfo = (WorkInfo) iterator.next();
					Question question = workInfo.getQuestion();
					int questionId = question.getiD();
					FileToZip.copy(questionPath + questionId % 15
							+ File.separatorChar + questionId % 16
							+ File.separatorChar + questionId, workPath
							+ File.separatorChar + questionId);
				}
				/*************** 复制文件 ****************/
				/*************** 打包work ****************/
				FileToZip fileToZip = new FileToZip();
				File file = new File(workPath + ".zip");
				if (file.exists()) {
					file.delete();
				}
				fileToZip.zip(file, workPath);
				try {
					FileUtils.deleteDirectory(new File(workPath));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("组卷文件夹删除失败");
				}
				/*************** 打包work ****************/
				
				// 将zip包发送给app服务器
				FileUtil fileUtil = new FileUtil();
				byte[] bytes = fileUtil.getBytes(workPath + ".zip");
				fileUtil.httpPost(bytes,work.getiD(),lessonId,0);
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("组卷打包失败");
			}
		}

}
