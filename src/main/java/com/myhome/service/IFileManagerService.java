package com.myhome.service;

import java.util.List;

import com.myhome.bean.HomeAlbum;
import com.myhome.bean.HomePhotos;
import com.myhome.bean.HomeVideoType;
import com.myhome.bean.HomeVideos;

public interface IFileManagerService 
{
	public HomePhotos insertAPhoto(HomePhotos photo) throws Exception;
	public List<HomePhotos> getAllHomePhotosFromAblum(HomeAlbum album) throws Exception;
	public HomeVideos insertAVideo(HomeVideos video) throws Exception;
	public List<HomeVideos> getAllHomeVideosFromVideoType(HomeVideoType videoType) throws Exception;
	public String updateThumbUrl(Integer video_id,String thumbUrl) throws Exception;
}
