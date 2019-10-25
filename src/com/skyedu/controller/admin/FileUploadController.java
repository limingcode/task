package com.skyedu.controller.admin;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skyedu.model.AppZip;
import com.skyedu.model.Work;
import com.skyedu.service.AppZipService;
import com.skyedu.service.WorkService;
import com.util.CommonUtil;
import com.util.FileToZip;

@Controller
@RequestMapping("/upload")
public class FileUploadController {

	@Autowired
	private WorkService workService;
	@Autowired
	private AppZipService appZipService;

	@RequestMapping("/temp")
	@ResponseBody
	public void saveImg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String path = request.getSession().getServletContext().getRealPath("/");
		String fileName = "";// 文件名称
		String shortPath = "/upload/temp/";
		path = path + shortPath;
		File file = new File(path);
		if (!file.exists())
			file.mkdirs();

		/** 上传文件处理内容 **/
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload sfu = new ServletFileUpload(factory);
		sfu.setHeaderEncoding("UTF-8"); // 处理中文问题
		sfu.setSizeMax(50 * 1024 * 1024); // 限制文件大小
		try {
			List<FileItem> fileItems = sfu.parseRequest(request); // 解码请求
			for (Iterator<FileItem> iterator = fileItems.iterator(); iterator.hasNext();) {
				FileItem fi = (FileItem) iterator.next();
				if (StringUtils.isEmpty(fi.getName())) {
					continue;
				}
				fileName = UUID.randomUUID().toString().replace("-", "")
						+ fi.getName().substring(fi.getName().lastIndexOf("."), fi.getName().length());
				fi.write(new File(path, fileName));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/**********************/

		// 获取图片url地址
		String imgUrl = request.getContextPath() + "/" + shortPath + fileName;
		response.setContentType("text/text;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(imgUrl); // 返回url地址
		out.flush();
		out.close();
	}

	/**
	 * 测试用数据传输接口
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/file")
	@ResponseBody
	public byte[] getFile(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		String lessonId = request.getParameter("lessonId");
		String path = request.getSession().getServletContext().getRealPath("/");
		String lessonPath = path + File.separatorChar + "upload" + File.separatorChar + "lesson" + File.separatorChar
				+ lessonId;
		String zipPath = path + File.separatorChar + "upload" + File.separatorChar + "lesson" + File.separatorChar
				+ lessonId + ".zip";
		FileToZip fileToZip = new FileToZip();
		File file = new File(zipPath);
		if (!file.exists()) {
			fileToZip.zip(file, lessonPath);
		}

		byte[] by = new byte[(int) file.length()];
		InputStream is = null;
		ByteArrayOutputStream bytestream = null;
		try {
			is = new FileInputStream(file);
			bytestream = new ByteArrayOutputStream();
			byte[] bb = new byte[2048];
			int ch;
			ch = is.read(bb);
			while (ch != -1) {
				bytestream.write(bb, 0, ch);
				ch = is.read(bb);
			}
			by = bytestream.toByteArray();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				bytestream.close();
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return by;
	}

	@RequestMapping("/fileReceive")
	public void fileReceive(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("接收文件+++++++++++++++++++");
		// 获取项目路径
		String basePath = CommonUtil.APPFILEWEB;
		String path = request.getSession().getServletContext().getRealPath("/");
		String workId = request.getHeader("workId");
		String state = request.getHeader("state");
		String lessonIdd = request.getHeader("lessonId");
		Integer lessonId = Integer.parseInt(lessonIdd);
		try {
			String lessonPath = path + "/../upload/lesson/" + lessonId % 15 + "/" + lessonId % 16 + "/" + lessonId;
			String workPath = lessonPath + "/" + workId;
			File file = new File(workPath + ".zip");
			System.out.println(state);
			if (state.equals("0")) {
				File lessonDir = new File(lessonPath);
				if (!lessonDir.exists()) {
					lessonDir.mkdirs();
				}
				ServletInputStream in = request.getInputStream();
				FileOutputStream fos = new FileOutputStream(file);
				byte[] b = new byte[1024];
				int n = 0;
				while ((n = in.read(b)) != -1) {
					fos.write(b, 0, n);
				}
				fos.close();
				in.close();
				// 更新打包版本时间
				Work work = workService.getWorkBean(Integer.valueOf(workId));
				work.setZipDate(work.getModifyDate());
				work.setSourceSize(file.length());
				work.setZipState(2);
				work.setDownUrl(basePath + "/upload/lesson/" + lessonId % 15 + "/" + lessonId % 16 + "/" + lessonId + "/"
						+ workId + ".zip");
				workService.updateWork(work);
				System.out.println("打包成功：" + workId);
			} else if (state.equals("1")) {
				System.out.println("删除zip：" + workId);
				if (file.exists()) {
					file.delete();
				}
			}
		} catch (Exception e) {
			AppZip appZip = appZipService.getAppZip(Integer.valueOf(workId));
			String message = e.getMessage();
			if (appZip == null) {
				appZip = new AppZip();
				appZip.setCount(0);
				appZip.setCreateDate(new Date());
				appZip.setMessage(message);
				appZip.setModifyDate(new Date());
				appZip.setState(Integer.valueOf(state));
				appZip.setWorkId(Integer.valueOf(workId));
				appZip.setLessonId(lessonId);
				appZipService.saveAppZip(appZip);
			} else {
				appZip.setCount(appZip.getCount() + 1);
				appZip.setMessage(message);
				appZip.setModifyDate(new Date());
				appZipService.updateAppZip(appZip);
			}
			System.out.println("接收失败+++++++++++++++++" + e.getMessage());
			e.printStackTrace();
		}

	}
}
