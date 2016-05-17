package com.myhome.dao;

import java.util.List;

import com.myhome.bean.HomeAlbum;
import com.myhome.bean.HomePhotos;
import com.myhome.bean.HomeVideoType;
import com.myhome.bean.HomeVideos;

public interface IFileManagerDao extends IBaseDao
{
	//上传相片
	public HomePhotos insertAPhoto(HomePhotos photo) throws Exception;
	//获取家庭相册下的所有图片
	public List<HomePhotos> getAllHomePhotosFromAblum(HomeAlbum album) throws Exception;
	
	//上传视频
	public HomeVideos insertAVideo(HomeVideos video) throws Exception;
	//获取视频库下的所有视频
	public List<HomeVideos> getAllHomeVideosFromVideoType(HomeVideoType videoType) throws Exception;
	//更新视频的缩略图
	public String updateThumbUrl(Integer video_id,String thumbUrl) throws Exception;
}
