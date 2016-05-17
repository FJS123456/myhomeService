package com.myhome.bean;

import java.util.Date;

public class HomeVideoType
{
	private Integer video_type_id;
	private String type_name;
	private Date create_date;
	private Home home;
	
	public Integer getVideo_type_id() {
		return video_type_id;
	}
	public void setVideo_type_id(Integer video_type_id) {
		this.video_type_id = video_type_id;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public Home getHome() {
		return home;
	}
	public void setHome(Home home) {
		this.home = home;
	}
	
}
