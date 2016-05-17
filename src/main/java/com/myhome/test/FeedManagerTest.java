package com.myhome.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.myhome.bean.FeedPhotos;
import com.myhome.bean.Feeds;
import com.myhome.bean.Home;
import com.myhome.bean.UserProfile;
import com.myhome.service.IFeedManagerService;
import com.myhome.service.impl.FeedManagerServiceImpl;

public class FeedManagerTest 
{
	private IFeedManagerService feedService = new FeedManagerServiceImpl();
	@Test
	@Ignore
	public void test_insertAFeed()
	{
		Feeds feed = new Feeds();
		feed.setBody("是的发送到发送到是否啥地方时发生的方式发生地方");
		feed.setCreate_date(new Date());
		feed.setIs_public(false);
		feed.setVideo_url("dfsfsdsdfsdfds");
		feed.setThumb_url("sssssssssss");
		
		UserProfile creater = new UserProfile();
		creater.setUser_profile_id(3);
		feed.setCreater(creater);
		
		List<String> homeIdList = new ArrayList<String>();
		homeIdList.add("34074013");
		homeIdList.add("35037179");
		homeIdList.add("48961952");
		feed.setHomeIdList(homeIdList);
		
		try {
			feed = feedService.insertAFeed(feed);
		} catch (Exception e) {
			feed = null;
			e.printStackTrace();
		}
		
		if (feed != null) {
			System.out.println
			(
					"  feed_id:" + feed.getFeed_id() + 
					"  user_profile_id:" + feed.getCreater().getUser_profile_id() +
				    "  body:" + feed.getBody() +
					"  video_url:" + feed.getVideo_url() +
					"  create_date:" + feed.getCreate_date() +
					"  is_report:" + feed.getIs_report() +
					"  is_public:" + feed.getIs_public() + 
					"  thumb_url:" + feed.getThumb_url() + 
					"  like_number:" + feed.getLike_number()
			);
			
			List<String> newHomeIdList = feed.getHomeIdList();
			for (String homeId : newHomeIdList) {
				System.out.println(homeId);
			}
			
		} else {
			System.out.println("插入动态失败！！！");
		}
	}
	
	@Test
	@Ignore
	public void test_insertAFeedPhoto()
	{
		FeedPhotos feedPhoto = new FeedPhotos();
		feedPhoto.setPhoto_url("dsfsdfsdfdsf");
		feedPhoto.setWidth(100.0);
		feedPhoto.setHeight(120.0);
		
		Feeds feed = new Feeds();
		feed.setFeed_id(1);
		feedPhoto.setFeed(feed);
		
		try {
			feedPhoto = feedService.insertAFeedPhoto(feedPhoto);
		} catch (Exception e) {
			feedPhoto = null;
			e.printStackTrace();
		}
		
		if (feedPhoto != null) {
			System.out.println("插入feedPhoto成功");
		} else {
			System.out.println("fail!!!");
		}
	}
	
	@Test
	@Ignore
	public void test_updateFeedVideoThumbUrl()
	{
		String thumbUrl = "dfsfdsfsdf";
		try {
			thumbUrl = feedService.updateFeedVideoThumb(1, thumbUrl);
		} catch (Exception e) {
			thumbUrl = null;
			e.printStackTrace();
		}
		if (thumbUrl != null) {
			System.out.println("更新thumbUrl成功");
		} else {
			System.out.println("fail!!!");
		}
	}
	
	@Test
	@Ignore
	public void test_updateFeedVideoUrl()
	{
		String videoUrl = "ddddddddd";
		try {
			videoUrl = feedService.updateFeedVideo(1, videoUrl);
		} catch (Exception e) {
			videoUrl = null;
			e.printStackTrace();
		}
		if (videoUrl != null) {
			System.out.println("更新videoUrl成功");
		} else {
			System.out.println("fail!!!");
		}
	}
	
	@Test
//	@Ignore
	public void test_getFeedListOfAHome()
	{
		List<Feeds> feedList = null;
		try {
			feedList = feedService.getFeedList(null,true);
		} catch (Exception e) {
			feedList = null;
			e.printStackTrace();
		}
		
		if (feedList != null) {
			for (Feeds feed : feedList) {
				System.out.println
				(
						"  feed_id:" + feed.getFeed_id() + 
						"  user_profile_id:" + feed.getCreater().getUser_profile_id() +
					    "  body:" + feed.getBody() +
						"  video_url:" + feed.getVideo_url() +
						"  create_date:" + feed.getCreate_date() +
						"  is_report:" + feed.getIs_report() +
						"  is_public:" + feed.getIs_public() + 
						"  thumb_url:" + feed.getThumb_url() + 
						"  like_number:" + feed.getLike_number() + 
						"  user_profile_id:" + feed.getCreater().getUser_profile_id() + 
						"  phone_number:" + feed.getCreater().getPhone_number() + 
						"  real_name:" + feed.getCreater().getReal_name() + 
						"  icon_url:" + feed.getCreater().getIcon_url()
				);
				
				List<FeedPhotos> photoList = feed.getFeedPhotoList();
				for (FeedPhotos feedPhoto : photoList) {
					System.out.println
					(
							"  feed_photo_id:" + feedPhoto.getFeed_photo_id() + 
							"  photo_url:" + feedPhoto.getPhoto_url() +
						    "  feed_id:" + feedPhoto.getFeed().getFeed_id() +
							"  width:" + feedPhoto.getWidth() +
							"  height:" + feedPhoto.getHeight()
					);
				}
				
			}
		} else {
			System.out.println("获取家庭动态失败！！！");
		}
	}
}
