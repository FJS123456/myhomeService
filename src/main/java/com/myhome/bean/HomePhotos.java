package com.myhome.bean;

import java.util.Date;

public class HomePhotos
{
	private Integer photo_id;
	private String photo_url;
	private Date upload_date;
	private UserProfile creater;
	private HomeAlbum homeAlbum;
	private Double width;
	private Double height;
	
	public Integer getPhoto_id() {
		return photo_id;
	}
	public void setPhoto_id(Integer photo_id) {
		this.photo_id = photo_id;
	}
	public String getPhoto_url() {
		return photo_url;
	}
	public void setPhoto_url(String photo_url) {
		this.photo_url = photo_url;
	}
	public Date getUpload_date() {
		return upload_date;
	}
	public void setUpload_date(Date upload_date) {
		this.upload_date = upload_date;
	}
	public UserProfile getCreater() {
		return creater;
	}
	public void setCreater(UserProfile creater) {
		this.creater = creater;
	}
	public HomeAlbum getHomeAlbum() {
		return homeAlbum;
	}
	public void setHomeAlbum(HomeAlbum homeAlbum) {
		this.homeAlbum = homeAlbum;
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
