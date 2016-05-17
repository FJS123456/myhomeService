package com.myhome.service;

import java.util.List;

import com.myhome.bean.FeedPhotos;
import com.myhome.bean.Feeds;

public interface IFeedManagerService
{
	public Feeds insertAFeed(Feeds feed) throws Exception;
	public FeedPhotos insertAFeedPhoto(FeedPhotos feedPhoto) throws Exception;
	public String updateFeedVideoThumb(Integer feedId, String thumbUrl) throws Exception;
	public String updateFeedVideo(Integer feedId,String videoUrl) throws Exception;
	//获取一个动态列表 1.home_id 为null 发现模块的feed    2.home_id 非null 家庭下的feed
	public List<Feeds> getFeedList(String homeId,Boolean isPublic) throws Exception; 
}
