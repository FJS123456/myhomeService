package com.myhome.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.myhome.bean.HomeAlbum;
import com.myhome.bean.HomePhotos;
import com.myhome.bean.HomeVideoType;
import com.myhome.bean.HomeVideos;
import com.myhome.bean.UserProfile;
import com.myhome.dao.IFileManagerDao;

public class FileManagerDaoImpl extends BaseDao implements IFileManagerDao
{

	@Override
	public HomePhotos insertAPhoto(HomePhotos photo) throws Exception 
	{
		Integer maxId = this.getMaxId("home_photos", "photo_id");
		
		String sql = "insert into home_photos (photo_id,photo_url,upload_date,user_profile_id,"
				+ "album_id,width,height) values "
				+ "(" + maxId + ",?,?,?,?,?,?)";
		this.openconnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, photo.getPhoto_url());
		pstmt.setTimestamp(2, new java.sql.Timestamp(photo.getUpload_date().getTime()));
		pstmt.setInt(3,photo.getCreater().getUser_profile_id());
		pstmt.setInt(4,photo.getHomeAlbum().getAlbum_id());
		pstmt.setDouble(5, photo.getWidth());
		pstmt.setDouble(6, photo.getHeight());
		
		int result = pstmt.executeUpdate();
		if (result > 0) {
			photo.setPhoto_id(maxId);
		} else {
			photo = null;
		}
		
		pstmt.close();
		
		return photo;
	}

	@Override
	public List<HomePhotos> getAllHomePhotosFromAblum(HomeAlbum album) throws Exception 
	{
		List<HomePhotos> photoList = null;
		if (album != null) {
			String sql = "select * from home_photos where album_id = ? order by upload_date desc";
			this.openconnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, album.getAlbum_id());
			ResultSet rs = pstmt.executeQuery();
			if (rs != null) {
				photoList = new ArrayList<HomePhotos>();
				while (rs.next()) {
					HomePhotos photo = new HomePhotos();
				
					photo.setPhoto_id(rs.getInt("photo_id"));
					photo.setPhoto_url(rs.getString("photo_url"));
					photo.setUpload_date(rs.getTimestamp("upload_date"));
					photo.setWidth(rs.getDouble("width"));
					photo.setHeight(rs.getDouble("height"));
					
					UserProfile user = new UserProfile();
					user.setUser_profile_id(rs.getInt("user_profile_id"));
					photo.setCreater(user);
					
					photo.setHomeAlbum(album);
					
					photoList.add(photo);
				}
				rs.close();
				pstmt.close();
			}
		}
		return photoList;
	}

	@Override
	public HomeVideos insertAVideo(HomeVideos video) throws Exception 
	{
		Integer maxId = this.getMaxId("home_videos", "video_id");
		
		String sql = "insert into home_videos (video_id,video_url,upload_date,user_profile_id,"
				+ "video_type_id,thumb_url,play_number,video_total_time,video_desc) values "
				+ "(" + maxId + ",?,?,?,?,?,?,?,?)";
		this.openconnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, video.getVideo_url());
		pstmt.setTimestamp(2, new java.sql.Timestamp(video.getUpload_date().getTime()));
		pstmt.setInt(3,video.getCreater().getUser_profile_id());
		pstmt.setInt(4,video.getVideoType().getVideo_type_id());
		pstmt.setString(5, video.getThumb_url());
		pstmt.setInt(6, video.getPlay_number());
		pstmt.setInt(7, video.getVideo_total_time());
		pstmt.setString(8, video.getVideo_desc());
		
		int result = pstmt.executeUpdate();
		if (result > 0) {
			video.setVideo_id(maxId);;
		} else {
			video = null;
		}
		
		pstmt.close();
		
		return video;
	}

	@Override
	public List<HomeVideos> getAllHomeVideosFromVideoType(HomeVideoType videoType) throws Exception 
	{
		List<HomeVideos> videoList = null;
		if (videoType != null) {
			String sql = "select * from home_videos where video_type_id = ? order by upload_date desc";
			this.openconnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, videoType.getVideo_type_id());
			ResultSet rs = pstmt.executeQuery();
			if (rs != null) {
				videoList = new ArrayList<HomeVideos>();
				while (rs.next()) {
					HomeVideos video = new HomeVideos();
					video.setVideo_id(rs.getInt("video_id"));
					video.setVideo_url(rs.getString("video_url"));
					video.setUpload_date(rs.getTimestamp("upload_date"));
					video.setThumb_url(rs.getString("thumb_url"));
					video.setPlay_number(rs.getInt("play_number"));
					video.setVideo_total_time(rs.getInt("video_total_time"));
					video.setVideo_desc(rs.getString("video_desc"));
					
					UserProfile user = new UserProfile();
					user.setUser_profile_id(rs.getInt("user_profile_id"));
					video.setCreater(user);
					
					video.setVideoType(videoType);
					
					videoList.add(video);
				}
				rs.close();
				pstmt.close();
			}
		}
		return videoList;
	}

	@Override
	public String updateThumbUrl(Integer video_id, String thumbUrl) throws Exception 
	{
		String sql = "update home_videos set thumb_url = ? where video_id = ?";
		this.openconnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, thumbUrl);
		pstmt.setInt(2, video_id);
		
		int result = pstmt.executeUpdate();
		if (result <= 0) {
			thumbUrl = null;
		} 
		pstmt.close();
		return thumbUrl;
	}

}
