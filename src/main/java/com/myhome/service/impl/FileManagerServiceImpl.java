package com.myhome.service.impl;

import java.util.List;

import com.myhome.bean.HomeAlbum;
import com.myhome.bean.HomePhotos;
import com.myhome.bean.HomeVideoType;
import com.myhome.bean.HomeVideos;
import com.myhome.dao.IFileManagerDao;
import com.myhome.dao.impl.FileManagerDaoImpl;
import com.myhome.service.IFileManagerService;

public class FileManagerServiceImpl implements IFileManagerService
{
	private IFileManagerDao fileDao = new FileManagerDaoImpl();

	@Override
	public HomePhotos insertAPhoto(HomePhotos photo) throws Exception 
	{
		if (photo != null && 
			photo.getPhoto_url() != null && 
			photo.getUpload_date() != null && 
			photo.getCreater() != null &&
			photo.getCreater().getUser_profile_id() != null &&
			photo.getHomeAlbum() != null && 
			photo.getHomeAlbum().getAlbum_id() != null
			) {
			try {
				photo = fileDao.insertAPhoto(photo);
			} catch (Exception e) {
				photo = null;
				throw e;
			} finally {
				fileDao.closeResource();
			}
		}
		// TODO Auto-generated method stub
		return photo;
	}

	@Override
	public List<HomePhotos> getAllHomePhotosFromAblum(HomeAlbum album) throws Exception 
	{
		List<HomePhotos> photoList = null;
		if (album != null && album.getAlbum_id() != null) {
			try {
				photoList = fileDao.getAllHomePhotosFromAblum(album);
			} catch (Exception e) {
				photoList = null;
				throw e;
			} finally {
				fileDao.closeResource();
			}
		}
		return photoList;
	}

	@Override
	public HomeVideos insertAVideo(HomeVideos video) throws Exception
	{
		if (video != null &&
			video.getVideo_url() != null &&
			video.getUpload_date() != null &&
			video.getVideo_total_time() != null &&
			video.getCreater() != null &&
			video.getCreater().getUser_profile_id() != null &&
			video.getVideoType() != null &&
			video.getVideoType().getVideo_type_id() != null
			) {
			try {
				video = fileDao.insertAVideo(video);
			} catch (Exception e) {
				video = null;
				throw e;
			} finally {
				fileDao.closeResource();
			}
		} else {
			video = null;
		}
		return video;
	}

	@Override
	public List<HomeVideos> getAllHomeVideosFromVideoType(HomeVideoType videoType) throws Exception 
	{
		List<HomeVideos> videoList = null;
		if (videoType != null && videoType.getVideo_type_id() != null) {
			try {
				videoList = fileDao.getAllHomeVideosFromVideoType(videoType);
			} catch (Exception e) {
				videoList = null;
				throw e;
			} finally {
				fileDao.closeResource();
			}
		}
		return videoList;
	}

	@Override
	public String updateThumbUrl(Integer video_id, String thumbUrl) throws Exception 
	{
		if (video_id != null && thumbUrl != null) {
			try {
				thumbUrl = fileDao.updateThumbUrl(video_id, thumbUrl);
			} catch (Exception e) {
				thumbUrl = null;
				throw e;
			} finally {
				fileDao.closeResource();
			}
			
		} else {
			thumbUrl = null;
		}
		
		return thumbUrl;
	}
	
}
