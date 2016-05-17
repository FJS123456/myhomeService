package com.myhome.test;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.myhome.bean.HomeAlbum;
import com.myhome.bean.HomePhotos;
import com.myhome.bean.HomeVideoType;
import com.myhome.bean.HomeVideos;
import com.myhome.bean.UserProfile;
import com.myhome.service.IFileManagerService;
import com.myhome.service.impl.FileManagerServiceImpl;

public class FileManagerTest 
{
	private IFileManagerService fileService = new FileManagerServiceImpl();
	
	@Test
	@Ignore
	public void test_uploadHomePhotos()
	{
		UserProfile userProfile = new UserProfile();
		userProfile.setUser_profile_id(3);
		
		HomeAlbum album = new HomeAlbum();
		album.setAlbum_id(1);
		
		HomePhotos photo = new HomePhotos();
		photo.setCreater(userProfile);
		photo.setHomeAlbum(album);
		photo.setPhoto_url("aaaaa");
		photo.setUpload_date(new Date());
		photo.setHeight(169.32);
		photo.setWidth(332.00);
		
		try {
			photo = fileService.insertAPhoto(photo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (photo != null) {
			System.out.println
			(
					"  photo_id:" + photo.getPhoto_id() + 
					"  photo_url:" + photo.getPhoto_url() +
				    "  upload_date:" + photo.getUpload_date() +
					"  user_profile_id:" + photo.getCreater().getUser_profile_id() +
					"  album_id:" + photo.getHomeAlbum().getAlbum_id() +
					"  width:" + photo.getWidth() +
					"  height:" + photo.getHeight() 
			);
		} else {
			System.out.println("上传相片失败！！");
		}
	}
	
	@Test
	@Ignore
	public void test_getAllPhotosFromAlbum()
	{
		HomeAlbum album = new HomeAlbum();
		album.setAlbum_id(1);
		List<HomePhotos> photoList = null;
		try {
			photoList = fileService.getAllHomePhotosFromAblum(album);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		if (photoList != null) {
			for (int i = 0 ; i < photoList.size() ; ++i) {
				HomePhotos photo = photoList.get(i);
				System.out.println
				(
						"  photo_id:" + photo.getPhoto_id() + 
						"  photo_url:" + photo.getPhoto_url() +
					    "  upload_date:" + photo.getUpload_date() +
						"  user_profile_id:" + photo.getCreater().getUser_profile_id() +
						"  album_id:" + photo.getHomeAlbum().getAlbum_id() +
						"  width:" + photo.getWidth() +
						"  height:" + photo.getHeight() 
				);
			}
		} else {
			System.out.println("获取相片失败！！");
		}
	}
	
	@Test
	@Ignore
	public void test_uploadAHomeVideo()
	{
		HomeVideos video = new HomeVideos();
		video.setVideo_url("aaa");
		video.setUpload_date(new Date());
		video.setThumb_url("thumbddddd");
		video.setPlay_number(0);
		video.setVideo_total_time(342432);
		video.setVideo_desc("水电费水电费都是师傅的说法是");
		
		UserProfile creater = new UserProfile();
		creater.setUser_profile_id(10);
		video.setCreater(creater);
		
		HomeVideoType videoType = new HomeVideoType();
		videoType.setVideo_type_id(1);
		video.setVideoType(videoType);
		
		try {
			video = fileService.insertAVideo(video);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (video != null) {
			System.out.println
			(
					"  video_id:" + video.getVideo_id() + 
					"  video_url:" + video.getVideo_url() +
				    "  upload_date:" + video.getUpload_date() +
					"  user_profile_id:" + video.getCreater().getUser_profile_id() +
					"  video_type_id:" + video.getVideoType().getVideo_type_id() +
					"  thumb_url:" + video.getThumb_url() +
					"  play_number:" + video.getPlay_number() +
					"  video_total_time:" + video.getVideo_total_time() +
					"  video_desc:" + video.getVideo_desc()
			);
		} else {
			System.out.println("上传视频错误！！！！");
		}
	}
	
	@Test
	@Ignore
	public void test_getAllVideosFromVideoType()
	{
		HomeVideoType videoType = new HomeVideoType();
		videoType.setVideo_type_id(1);
		List<HomeVideos> videoList = null;
		try {
			videoList = fileService.getAllHomeVideosFromVideoType(videoType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < videoList.size(); i++) 
		{
			HomeVideos video = videoList.get(i);
			System.out.println
			(
					"  video_id:" + video.getVideo_id() + 
					"  video_url:" + video.getVideo_url() +
				    "  upload_date:" + video.getUpload_date() +
					"  user_profile_id:" + video.getCreater().getUser_profile_id() +
					"  video_type_id:" + video.getVideoType().getVideo_type_id() +
					"  thumb_url:" + video.getThumb_url() +
					"  play_number:" + video.getPlay_number() +
					"  video_total_time:" + video.getVideo_total_time() +
					"  video_desc:" + video.getVideo_desc()
			);
		}
	}
	
	@Test
//	@Ignore
	public void test_updateVideoThumb()
	{
		String thumbUrl = "aafsdfsd";
		try {
			thumbUrl = fileService.updateThumbUrl(1, thumbUrl);
		} catch (Exception e) {
			thumbUrl = null;
			e.printStackTrace();
		}
		if (thumbUrl != null) {
			System.out.println("update sucessful");
		}
	}
}
