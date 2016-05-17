package com.myhome.bean;

import java.util.Date;
import java.util.List;

public class Home
{
	private String home_id;
	private String home_name;
	private String annoucement;     		//家庭公告
	private UserProfile creater;  		//创建者
	private Date create_date;
	private Integer score;				//积分，用以评估家庭的活跃度
	private String sign;
	
	private List<UserProfile> memberList = null;
	
	public String getHome_name() {
		return home_name;
	}
	public void setHome_name(String home_name) {
		this.home_name = home_name;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getHome_id() {
		return home_id;
	}
	public void setHome_id(String home_id) {
		this.home_id = home_id;
	}
	public String getAnnoucement() {
		return annoucement;
	}
	public void setAnnoucement(String annoucement) {
		this.annoucement = annoucement;
	}
	
	public UserProfile getCreater() {
		return creater;
	}
	public void setCreater(UserProfile creater) {
		this.creater = creater;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public List<UserProfile> getMemberList() {
		return memberList;
	}
	public void setMemberList(List<UserProfile> memberList) {
		this.memberList = memberList;
	}
	
	
	
}
