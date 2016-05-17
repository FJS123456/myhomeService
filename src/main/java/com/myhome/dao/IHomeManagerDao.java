package com.myhome.dao;

import java.util.List;

import com.myhome.bean.Home;
import com.myhome.bean.HomeAlbum;
import com.myhome.bean.HomeFeedRelation;
import com.myhome.bean.HomeVideoType;
import com.myhome.bean.UserHomeRelation;
import com.myhome.bean.UserProfile;

public interface IHomeManagerDao extends IBaseDao
{
	public Home insertHome(Home home) throws Exception;
	public boolean isHomeExist(String home_id) throws Exception; 
	public List<Home> getHomeList(Integer user_profile_id) throws Exception;
	public List<UserHomeRelation> getUserHomeRelationList(Integer user_profile_id) throws Exception;
	//获取一个家庭空间下的所有相册
	public List<HomeAlbum> getAllAlbumOfAHome(String home_id) throws Exception;
	//创建相册
	public HomeAlbum createAlbum(HomeAlbum homeAlbum) throws Exception;
	
	//创建视频库
	public HomeVideoType createVideoType(HomeVideoType videoType) throws Exception;
	//获取一个家庭空间下的所有视频库
	public List<HomeVideoType> getAllVideoTypeOfAhome(String home_id) throws Exception;
	
	//添加家庭成员,返回所有家庭成员
	public List<UserProfile> addMemberToAHome(Integer userProfileId,String homeId) throws Exception;
	
	
	
}
