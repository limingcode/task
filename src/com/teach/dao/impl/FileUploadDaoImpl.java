package com.teach.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.teach.dao.FileUploadDao;
import com.teach.model.TpFileUpload;

@Repository
public class FileUploadDaoImpl extends BaseDao implements FileUploadDao{

	public TpFileUpload saveUploadInfo(TpFileUpload fileupload) {
		return (TpFileUpload) saveObj(fileupload);
	}

	/**
	 * 查询出课件中上传的图片、音频、动画和视频
	 */
	@Override
	public List<Serializable> getFileListByLessonId(int lessonId) {
		String hql = "from TpFileUpload tf where tf.lessonId = '"+ lessonId +"' and tf.fileType in ('1','2','3','4')";
		return getListUseHql(hql);
	}

	/**
	 * 删除多个文件上传记录
	 */
	@Override
	public void deleteFile(Integer[] ids) {
		delete(TpFileUpload.class, ids);
	}

	/**
	 * 更新说课、实录视频地址
	 */
	@Override
	public void updateCoursewareVideoUrl(String sql) {
		updateUseSql(sql);
	}

	/**
	 * 通过lessonId和视频类型得到说课、实录视频
	 */
	@Override
	public TpFileUpload getVideoFile(int lessonId, Byte fileType) {
		String hql = "from TpFileUpload tf where tf.lessonId = '"+ lessonId +"' and tf.fileType = '"+ fileType +"'";
		return (TpFileUpload) getObjUseHql(hql);
	}
	
	@Override
	public List<Serializable> getThemeFileList(int lessonId){
		String hql = "from TpFileUpload tf where tf.lessonId = '"+ lessonId +"' and tf.fileType = 7";
		return getListUseHql(hql);
	}

	/**
	 * 得到课件相关的所有上传文件，包括说课视频，实录视频
	 */
	@Override
	public List<Map<String, Object>> getAllFileList(int lessonId) {
		String sql = "select * from Tp_FileUpload tf where tf.lessonId = '"+ lessonId +"' ";
		return getListUseSql(sql);
	}

	@Override
	public void saveUploadInfoList(String tableName, List<Map<String, Object>> list) {
		for (Map<String, Object> map : list) {
			saveMap(tableName, map);
		}
	}

	@Override
	public void deleteFileByLessonId(int lessonId) {
		String sql = "delete from Tp_FileUpload where lessonId = "+ lessonId ;
		updateUseSql(sql);
	}

}
