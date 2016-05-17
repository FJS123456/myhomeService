package com.myhome.bean;

public class UserPhotos 
{
	private Integer photo_id;
	private String photo_url;
	private UserProfile creater;
	
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
	public UserProfile getCreater() {
		return creater;
	}
	public void setCreater(UserProfile creater) {
		this.creater = creater;
	}
	
}
