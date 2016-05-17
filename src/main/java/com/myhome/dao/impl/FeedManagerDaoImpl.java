package com.myhome.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.myhome.bean.FeedPhotos;
import com.myhome.bean.Feeds;
import com.myhome.bean.Home;
import com.myhome.bean.UserProfile;
import com.myhome.dao.IFeedManagerDao;

public class FeedManagerDaoImpl extends BaseDao implements IFeedManagerDao
{

	@Override
	public Feeds insertAFeed(Feeds feed) throws Exception 
	{
		Integer maxFeedId = this.getMaxId("feeds", "feed_id");
		feed.setFeed_id(maxFeedId);
		
		//开启事物
	    this.beginTransaction();
		this.openconnection();
		
		String feedSql = "insert into feeds (feed_id,user_profile_id,body,video_url,create_date,is_report,is_public,thumb_url,like_number) "
				+ "values (?,?,?,?,?,?,?,?,?)";
		PreparedStatement feedPs = conn.prepareStatement(feedSql);
		feedPs.setInt(1,maxFeedId);
		feedPs.setInt(2, feed.getCreater().getUser_profile_id());
		feedPs.setString(3, feed.getBody());
		feedPs.setString(4, feed.getVideo_url());
		feedPs.setTimestamp(5, new java.sql.Timestamp(feed.getCreate_date().getTime()));
		feedPs.setBoolean(6, feed.getIs_report());
		feedPs.setBoolean(7, feed.getIs_public());
		feedPs.setString(8,feed.getThumb_url());
		feedPs.setInt(9, feed.getLike_number());
		
		String homeFeedRelationSql = "insert into home_feed_relation (home_id,feed_id) values " 
				+ "(?,?)";
		PreparedStatement relationPs = conn.prepareStatement(homeFeedRelationSql);
		for (int i = 0 ; i < feed.getHomeIdList().size() ; ++i) {
			String homeId = feed.getHomeIdList().get(i);
			if (homeId != null) {
				relationPs.setString(1, homeId);
				relationPs.setInt(2, maxFeedId);
				relationPs.addBatch();
			}
		}
		
		try {
			feedPs.executeUpdate();
			relationPs.executeBatch();
			this.commit();
		} catch (Exception e) {
			//回滚事物
			this.rollback();
			feed = null;
			throw e;
		} finally {
			//关闭事物
			conn.setAutoCommit(true);
		}
				
		return feed;
	}

	@Override
	public FeedPhotos insertAFeedPhoto(FeedPhotos feedPhoto) throws Exception 
	{
		Integer maxId = this.getMaxId("feed_photos", "feed_photo_id");
		String sql = "insert into feed_photos (feed_photo_id,photo_url,feed_id,width,height) values " +
					"(?,?,?,?,?)";
		this.openconnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, maxId);
		pstmt.setString(2, feedPhoto.getPhoto_url());
		pstmt.setInt(3, feedPhoto.getFeed().getFeed_id());
		pstmt.setDouble(4, feedPhoto.getWidth());
		pstmt.setDouble(5, feedPhoto.getHeight());
		
		int result = pstmt.executeUpdate();
		if (result > 0) {
			feedPhoto.setFeed_photo_id(maxId);
		} else {
			feedPhoto = null;
		}
		
		pstmt.close();
		
		return feedPhoto;
	}

	@Override
	public String updateFeedVideoThumb(Integer feedId, String thumbUrl) throws Exception 
	{
		String sql = "update feeds set thumb_url = ? where feed_id = ?";
		this.openconnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, thumbUrl);
		pstmt.setInt(2, feedId);
		
		int result = pstmt.executeUpdate();
		if (result <= 0) {
			thumbUrl = null;
		} 
		pstmt.close();
		return thumbUrl;
	}

	@Override
	public String updateFeedVideo(Integer feedId, String videoUrl) throws Exception
	{
		String sql = "update feeds set video_url = ? where feed_id = ?";
		this.openconnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, videoUrl);
		pstmt.setInt(2, feedId);
		
		int result = pstmt.executeUpdate();
		if (result <= 0) {
			videoUrl = null;
		} 
		pstmt.close();
		
		return videoUrl;
	}

	@Override
	public List<Feeds> getFeedList(String homeId, Boolean isPublic) throws Exception 
	{
		List<Feeds> feedList = null;
		
		this.openconnection();
		
		feedList = this.baseDataOfFeeds(homeId, isPublic);  //获取动态的基本数据
		feedList = this.getPhotosOfFeed(feedList);  //获取动态图片数据
		
		return feedList;
	}
	
	public List<Feeds> baseDataOfFeeds(String homeId,Boolean isPublic) throws Exception
	{
		List<Feeds> feedList = null;
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		if (homeId != null) {              //家庭空间模块
			String sql = "select * from feed_home_view where home_id = ?";
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setString(1, homeId);
		} else if (isPublic = true) {       //发现模块
			String sql = "select * from feed_user_view where is_public = ?";
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setBoolean(1, isPublic);
		}
		
		rs = pstmt.executeQuery();
		
		if (rs != null) {
			feedList = new ArrayList<Feeds>();
			while (rs.next()) {
				Feeds feed = new Feeds();
				
				feed.setFeed_id(rs.getInt("feed_id"));
				feed.setBody(rs.getString("body"));
				feed.setVideo_url(rs.getString("video_url"));
				feed.setCreate_date(rs.getTimestamp("create_date"));
				feed.setIs_public(rs.getBoolean("is_public"));
				feed.setThumb_url(rs.getString("thumb_url"));
				feed.setLike_number(rs.getInt("like_number"));
				
				UserProfile userProfile = new UserProfile();
				userProfile.setUser_profile_id(rs.getInt("user_profile_id"));
				userProfile.setPhone_number(rs.getString("phone_number"));
				userProfile.setReal_name(rs.getString("real_name"));
				userProfile.setIcon_url(rs.getString("icon_url"));
				feed.setCreater(userProfile);
				
				feedList.add(feed);
			}
			rs.close();
			pstmt.close();
		}
		
		return feedList;
	}
	
	public List<Feeds> getPhotosOfFeed(List<Feeds> sourceFeedList) throws Exception
	{
		List<Feeds> feedList = sourceFeedList;
		
		for (Feeds feeds : feedList) {
			
			if (feeds.getFeed_id() == null) continue;
			
			List<FeedPhotos> photoList = null;
			String sql = "select * from feed_photos where feed_id = ?";
			PreparedStatement ps = this.conn.prepareStatement(sql);
			ps.setInt(1, feeds.getFeed_id());
			
			ResultSet rs = ps.executeQuery();
			if (rs != null) {
				photoList = new ArrayList<FeedPhotos>();
				while (rs.next()) {
					FeedPhotos feedPhoto = new FeedPhotos();
					feedPhoto.setFeed_photo_id(rs.getInt("feed_photo_id"));
					feedPhoto.setPhoto_url(rs.getString("photo_url"));
					feedPhoto.setWidth(rs.getDouble("width"));
					feedPhoto.setHeight(rs.getDouble("height"));
					
					Feeds tempFeed = new Feeds();
					tempFeed.setFeed_id(feeds.getFeed_id());
					
					feedPhoto.setFeed(tempFeed);
					
					photoList.add(feedPhoto);
				}
				rs.close();
				ps.close();
				
				feeds.setFeedPhotoList(photoList);
			}
		}
		
		return feedList;
	}


}
