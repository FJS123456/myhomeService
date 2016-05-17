package com.myhome.dao;


import java.util.List;

import com.myhome.bean.FeedPhotos;
import com.myhome.bean.Feeds;

public interface IFeedManagerDao extends IBaseDao  
{
	public Feeds insertAFeed(Feeds feed) throws Exception;
	public FeedPhotos insertAFeedPhoto(FeedPhotos feedPhoto) throws Exception;
	//更新feed视频的缩略图
	public String updateFeedVideoThumb(Integer feedId, String thumbUrl) throws Exception;
	//更新feed视频
	public String updateFeedVideo(Integer feedId,String videoUrl) throws Exception;
	
	public List<Feeds> getFeedList(String homeId,Boolean isPublic) throws Exception;
}
