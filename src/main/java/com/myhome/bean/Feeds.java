package com.myhome.bean;

import java.util.Date;
import java.util.List;

public class Feeds 
{
	private Integer feed_id;
	private UserProfile creater;
	private String body;              //动态正文部分
	private String video_url;
	private Date create_date;
	private Boolean is_report = false;     //该动态是否被举报，0：没有   1：有
	private Boolean is_public;     //该动态是否公开，  0：不公开 1：公开
	private String thumb_url;      //视频缩略图的url
	private Integer like_number = 0;
	
	private List<String> homeIdList;
	private List<FeedPhotos> feedPhotoList;
	
	public Integer getFeed_id() {
		return feed_id;
	}
	public void setFeed_id(Integer feed_id) {
		this.feed_id = feed_id;
	}
	public UserProfile getCreater() {
		return creater;
	}
	public void setCreater(UserProfile creater) {
		this.creater = creater;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getVideo_url() {
		return video_url;
	}
	public void setVideo_url(String video_url) {
		this.video_url = video_url;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public Boolean getIs_report() {
		return is_report;
	}
	public void setIs_report(Boolean is_report) {
		this.is_report = is_report;
	}
	public Boolean getIs_public() {
		return is_public;
	}
	public void setIs_public(Boolean is_public) {
		this.is_public = is_public;
	}
	public String getThumb_url() {
		return thumb_url;
	}
	public void setThumb_url(String thumb_url) {
		this.thumb_url = thumb_url;
	}
	public Integer getLike_number() {
		return like_number;
	}
	public void setLike_number(Integer like_number) {
		this.like_number = like_number;
	}
	public List<String> getHomeIdList() {
		return homeIdList;
	}
	public void setHomeIdList(List<String> homeIdList) {
		this.homeIdList = homeIdList;
	}
	public List<FeedPhotos> getFeedPhotoList() {
		return feedPhotoList;
	}
	public void setFeedPhotoList(List<FeedPhotos> feedPhotoList) {
		this.feedPhotoList = feedPhotoList;
	}
	
}
