package com.myhome.bean;

public class FeedPhotos
{
	private Integer feed_photo_id;
	private String photo_url;
	private Feeds feed;
	private Double width;
	private Double height;
	
	public Integer getFeed_photo_id() {
		return feed_photo_id;
	}
	public void setFeed_photo_id(Integer feed_photo_id) {
		this.feed_photo_id = feed_photo_id;
	}
	public String getPhoto_url() {
		return photo_url;
	}
	public void setPhoto_url(String photo_url) {
		this.photo_url = photo_url;
	}
	public Feeds getFeed() {
		return feed;
	}
	public void setFeed(Feeds feed) {
		this.feed = feed;
	}
	public Double getWidth() {
		return width;
	}
	public void setWidth(Double width) {
		this.width = width;
	}
	public Double getHeight() {
		return height;
	}
	public void setHeight(Double height) {
		this.height = height;
	}
	
}
