package com.teach.service.impl;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.teach.FileUploadMessage;
import com.teach.Page;
import com.teach.dao.PersonFileDao;
import com.teach.dao.TpServersDao;
import com.teach.model.TpPersonFile;
import com.teach.model.TpPersonFileSendRecord;
import com.teach.service.PersonFileService;
import com.teach.util.CommonUtil;
import com.teach.util.FileUploadUtil;
import com.teach.util.PersonFileThread;
import com.teach.util.PersonFileUtil;
import com.teach.util.SendFileUtil;
import com.util.FileToZip;

@Service
public class PersonFileServiceImpl implements PersonFileService {
	
	@Resource
	private PersonFileDao personFileDao;
	@Resource
	private TpServersDao serversDao;
	
	@Override
	public boolean checkIsTeacher(int oaId) {
		return personFileDao.checkIsTeacher(oaId);
	}

	@Override
	public Page getPersonFileList(int oaId, String search, Page page) {
		return personFileDao.getPersonFileList(oaId, search, page);
	}

	@Override
	public FileUploadMessage getIsSupportFileType(String fileName) {
		if (".zip.png.mp4.jpg.gif.mp3.ogg.ppt".contains(fileName.substring(fileName.lastIndexOf(".")))) {
			return new FileUploadMessage(true, null);
		}
		return new FileUploadMessage(false, "该文件格式不支持上传!");
	}

	@Override
	public void savePersonFile(int oaId, TpPersonFile personFile) {
		int type = 2;
		if (personFileDao.checkIsTeacher(oaId)) {
			if (PersonFileUtil.isZipType(personFile.getFileName())) {
				String homePath = PersonFileUtil.getHomePath();
				String personDirPath = PersonFileUtil.getPersonDirPath(oaId);
				String unZipPath = homePath + personDirPath + "unZip" + File.separator;
				CommonUtil.unZip(unZipPath, new File(homePath + personFile.getFilePath()));
				List<String> list = CommonUtil.getFileNameByRegularExpression(unZipPath + "data", "thmb[0-9]{1,3}.*");
				if(list != null && list.size() > 0) { //上传的zip包文件目录正确
					type = 1;
					String indexHtml = unZipPath + "/index.html";
					String index = CommonUtil.readFileByLines(indexHtml);
					FileToZip.copy(FileUploadUtil.getUploadCoursewareFileDir(StringUtils.contains(index, "version")), unZipPath);
					FileToZip fileToZip = new FileToZip();
					File file = new File(homePath + personDirPath, personFile.getFileName());
					fileToZip.zip(file, unZipPath);
				}
				FileUploadUtil.deleteDirectory(unZipPath);//删除上传的zip和解压出来的文件夹
			}
		}
		personFile.setFileType(type);
		
		int id = personFileDao.save(personFile);
		
		if (type == 1) { //课件文件发送到校区服务器
			PersonFileThread thread = new PersonFileThread(id, oaId, personFile.getFileName(), PersonFileUtil.getHomePath() + personFile.getFilePath());
			thread.start();
		}
	}

	@Override
	public FileUploadMessage reNameFile(int id, String name) {
		TpPersonFile personFile = personFileDao.getPersonFile(id);
		String fileName = personFile.getFileName();
		if (!name.equals(fileName.substring(0, fileName.lastIndexOf(".")))) {
			String suffixName = fileName.substring(fileName.lastIndexOf("."));
			String personDirPath = PersonFileUtil.getHomePath() + PersonFileUtil.getPersonDirPath(personFile.getOaId());
			File newfile = new File(personDirPath, name + suffixName); 
            if(newfile.exists()){
                return new FileUploadMessage(false, "文件："+ name + suffixName + "已存在，不能重命名！");//重命名文件不存在
            }
            File oldfile = new File(personDirPath, fileName); 
            if (!oldfile.renameTo(newfile)) {
            	return new FileUploadMessage(false, "重命名失败！");
            }
            personFile.setFileName(name + suffixName);
            String filePath = personFile.getFilePath();
            personFile.setFilePath(filePath.substring(0, filePath.lastIndexOf(File.separator)+1) + name + suffixName);
            personFileDao.save(personFile);
		}
		 FileUploadMessage message = new FileUploadMessage(true, "重命名成功！");
		 message.setUrl(personFile.getFilePath());
		 return message;
	}
	
	@Override
	public void deleteFile(int id) {
		TpPersonFile personFile = personFileDao.getPersonFile(id);
		String fileName = personFile.getFileName();
		File file = new File(PersonFileUtil.getHomePath() + PersonFileUtil.getPersonDirPath(personFile.getOaId()), fileName);
		FileUtils.deleteQuietly(file);
		if (personFile.getFileType() != 1) {  //普通文件，直接删除
			personFileDao.delete(id);
		} else { //课件文件
			personFile.setStatus(-1);
			personFileDao.save(personFile);
		}
		
	}
	
	/**
	 * 获取得到老师的所有班级
	 */
	@Override
	public List<Map<String, Object>> getAllCourse(int oaId) {
		return personFileDao.getAllCourse(oaId);
	}
	
	/**
	 * 获取该班次所有在读的学生
	 */
	@Override
	public List<Map<String, Object>> getStudentList(int courseId) {
		return personFileDao.getStudentList(courseId);
	}

	/**
	 * 单个发送文件(teach自动调用接口)
	 * @param id
	 */
	@Override
	public void sendFileToServer(int id) {
		TpPersonFileSendRecord record = personFileDao.getPersonFileSendRecord(id);
		int fid = record.getFid();
		TpPersonFile personFile = personFileDao.getPersonFile(fid);
		record.setCount(record.getCount()+1);
		record.setStatus(PersonFileUtil.FILE_SEND_STATUS_SENDING); 
		record.setSendTime(new Date());  //更新最新一次发送时间
		personFileDao.save(record);
		int code = PersonFileUtil.httpPost(record.getServerAddr(), SendFileUtil.getBytes(PersonFileUtil.getHomePath() + personFile.getFilePath()), 
				personFile.getFileName(), personFile.getOaId(), record.getId());
		if (code != 200) {
			record.setStatus(PersonFileUtil.FILE_SEND_STATUS_UNSEND); 
			personFileDao.save(record);
		}
	}
	
	/**
	 * 单个发送文件(teach手动调用接口)
	 * @param id
	 */
	@Override
	public void sendFile(int id, String serverIp) {
		TpPersonFile personFile = personFileDao.getPersonFile(id);
		TpPersonFileSendRecord record = personFileDao.getPersonFileSendRecord(id, serverIp);
		if (record == null) {
			Map<String, Object> server = serversDao.getServerByServerIp(serverIp);
			record = new TpPersonFileSendRecord();
			record.setFid(id);
			record.setServerId(Integer.valueOf(String.valueOf(server.get("id"))));
			record.setServerAddr((String) server.get("serverAddr"));
		}
		record.setCount(0);
		record.setStatus(PersonFileUtil.FILE_SEND_STATUS_SENDING); 
		record.setSendTime(new Date());  //更新最新一次发送时间
		record = personFileDao.save(record);
		int code = PersonFileUtil.httpPost(record.getServerAddr(), SendFileUtil.getBytes(PersonFileUtil.getHomePath() + personFile.getFilePath()), 
				personFile.getFileName(), personFile.getOaId(), record.getId());
		if (code != 200) {
			record.setStatus(PersonFileUtil.FILE_SEND_STATUS_UNSEND); 
			personFileDao.save(record);
		}
		
	}
}
