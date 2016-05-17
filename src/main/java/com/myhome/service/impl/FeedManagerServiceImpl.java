package com.myhome.service.impl;

import java.util.List;

import com.myhome.bean.FeedPhotos;
import com.myhome.bean.Feeds;
import com.myhome.bean.Home;
import com.myhome.dao.IFeedManagerDao;
import com.myhome.dao.impl.FeedManagerDaoImpl;
import com.myhome.service.IFeedManagerService;

public class FeedManagerServiceImpl implements IFeedManagerService
{
	private IFeedManagerDao feedDao = new FeedManagerDaoImpl();

	@Override
	public Feeds insertAFeed(Feeds feed) throws Exception 
	{
		if (feed != null &&
			feed.getCreate_date() != null && 
			feed.getIs_public() != null && 
			feed.getCreater() != null &&
			feed.getCreater().getUser_profile_id() != null &&
			feed.getHomeIdList().size() > 0
				) {
			try {
				feed = feedDao.insertAFeed(feed);
			} catch (Exception e) {
				feed = null;
				throw e;
			} finally {
				feedDao.closeResource();
			}
		} else {
			feed = null;
		}
		return feed;
	}

	@Override
	public FeedPhotos insertAFeedPhoto(FeedPhotos feedPhoto) throws Exception 
	{
		if (feedPhoto != null &&
		    feedPhoto.getPhoto_url() != null &&
		    feedPhoto.getWidth() != null &&
		    feedPhoto.getHeight() != null && 
		    feedPhoto.getFeed() != null &&
		    feedPhoto.getFeed().getFeed_id() != null) {
			try {
				feedPhoto = feedDao.insertAFeedPhoto(feedPhoto);
			} catch (Exception e) {
				feedPhoto = null;
				throw e;
			}
		} else {
			feedPhoto = null;
		}
		return feedPhoto;
	}

	@Override
	public String updateFeedVideoThumb(Integer feedId, String thumbUrl) throws Exception 
	{
		if (feedId != null && thumbUrl != null) {
			try {
				thumbUrl = feedDao.updateFeedVideoThumb(feedId, thumbUrl);
			} catch (Exception e) {
				thumbUrl = null;
				throw e;
			}
		} else {
			thumbUrl = null;
		}
		return thumbUrl;
	}

	@Override
	public String updateFeedVideo(Integer feedId, String videoUrl) throws Exception 
	{
		if (feedId != null && videoUrl != null) {
			try {
				videoUrl = feedDao.updateFeedVideo(feedId, videoUrl);
			} catch (Exception e) {
				videoUrl = null;
				throw e;
			}
		} else {
			videoUrl = null;
		}
		return videoUrl;
	}

	@Override
	public List<Feeds> getFeedList(String homeId,Boolean isPublic) throws Exception 
	{
		List<Feeds> feedList = null;
		if ((homeId != null && isPublic == false) || (homeId == null && isPublic == true)) {
			try {
				feedList = feedDao.getFeedList(homeId, isPublic);
			} catch (Exception e) {
				feedList = null;
				throw e;
			} finally {
				feedDao.closeResource();
			}
		}
		
		return feedList;
	}
}
