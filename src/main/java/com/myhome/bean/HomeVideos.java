package com.myhome.bean;

import java.util.Date;

public class HomeVideos 
{
	private Integer video_id;
	private String video_url;
	private Date upload_date;
	private UserProfile creater;
	private HomeVideoType videoType;
	private String thumb_url;       //缩略图
	private Integer play_number;
	private Integer video_total_time;
	private String video_desc;
	
	public Integer getVideo_id() {
		return video_id;
	}
	public void setVideo_id(Integer video_id) {
		this.video_id = video_id;
	}
	public String getVideo_url() {
		return video_url;
	}
	public void setVideo_url(String video_url) {
		this.video_url = video_url;
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
	public HomeVideoType getVideoType() {
		return videoType;
	}
	public void setVideoType(HomeVideoType videoType) {
		this.videoType = videoType;
	}
	public String getThumb_url() {
		return thumb_url;
	}
	public void setThumb_url(String thumb_url) {
		this.thumb_url = thumb_url;
	}
	public Integer getPlay_number() {
		return play_number;
	}
	public void setPlay_number(Integer play_number) {
		this.play_number = play_number;
	}
	public Integer getVideo_total_time() {
		return video_total_time;
	}
	public void setVideo_total_time(Integer video_total_time) {
		this.video_total_time = video_total_time;
	}
	public String getVideo_desc() {
		return video_desc;
	}
	public void setVideo_desc(String video_desc) {
		this.video_desc = video_desc;
	}
	
}
