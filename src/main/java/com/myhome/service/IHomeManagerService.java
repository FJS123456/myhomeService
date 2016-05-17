package com.myhome.service;

import java.util.List;

import com.myhome.bean.Home;
import com.myhome.bean.HomeAlbum;
import com.myhome.bean.HomeFeedRelation;
import com.myhome.bean.HomeVideoType;
import com.myhome.bean.UserHomeRelation;
import com.myhome.bean.UserProfile;

public interface IHomeManagerService
{
	public Home createHome(Home home) throws Exception;
	public List<Home> getHomeList(Integer user_profile_id) throws Exception;
	public List<UserHomeRelation> getUserHomeRelationList(Integer user_profile_id) throws Exception;
	
	public List<HomeAlbum> getAllAlbumOfAHome(String home_id) throws Exception;
	public HomeAlbum createAlbum(HomeAlbum homeAlbum) throws Exception;
	
	public HomeVideoType createVideoType(HomeVideoType videoType) throws Exception;
	public List<HomeVideoType> getAllVideoTypeOfAhome(String home_id) throws Exception;
	
	public List<UserProfile> addMemberToAHome(Integer userProfileId,String homeId) throws Exception;
}
