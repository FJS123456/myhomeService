package com.myhome.service.impl;

import java.util.List;
import java.util.Random;

import com.myhome.bean.Home;
import com.myhome.bean.HomeAlbum;
import com.myhome.bean.HomeVideoType;
import com.myhome.bean.UserHomeRelation;
import com.myhome.bean.UserProfile;
import com.myhome.dao.IHomeManagerDao;
import com.myhome.dao.impl.HomeManagerDaoImpl;
import com.myhome.service.IHomeManagerService;

public class HomeManagerServiceImpl implements IHomeManagerService
{
	private IHomeManagerDao homeDao = new HomeManagerDaoImpl();
	
	@Override
	public Home createHome(Home home) throws Exception 
	{
		if (    home != null && 
			    home.getHome_name() != null && 
			    home.getCreate_date() != null &&
			    home.getCreater() != null &&
			    home.getCreater().getUser_profile_id() != null) {
				try {
					for (int i = 0 ; i < 3 ; ++i) {
						String homeId = this.getRandomHomeId();
						boolean isExist = homeDao.isHomeExist(homeId);
						if (!isExist) {
							home.setHome_id(homeId);
							home = homeDao.insertHome(home);
							break;
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
					home = null;
					throw e;
				} finally {
					homeDao.closeResource();
				}
			} else {
				home = null;
			}
			return home;
	}
	
	private String getRandomHomeId()
	{
		String randomStr = "";
		int count = 8;
		Random random = new Random();
		//最后一位为1-9；
		randomStr += random.nextInt(9) + 1;
		for (int i = 1; i < count ; ++i) {
			int tempNumber = random.nextInt(10);
			randomStr += tempNumber;
		}
		return randomStr;
	}

	@Override
	public List<Home> getHomeList(Integer user_profile_id) throws Exception 
	{
		List<Home> homeList = null;
		try {
			homeList = homeDao.getHomeList(user_profile_id);
		} catch (Exception e) {
			throw e;
		} finally {
			homeDao.closeResource();
		}
		
		return homeList;
	}
	
	@Override
	public List<UserHomeRelation> getUserHomeRelationList(Integer user_profile_id) throws Exception 
	{
		List<UserHomeRelation> userHomeList = null;
		try {
			userHomeList = homeDao.getUserHomeRelationList(user_profile_id);
		} catch (Exception e) {
			throw e;
		} finally {
			homeDao.closeResource();
		}
		
		return userHomeList;
	}

	@Override
	public List<HomeAlbum> getAllAlbumOfAHome(String home_id) throws Exception
	{
		List<HomeAlbum> albumList = null;
		try {
			albumList = homeDao.getAllAlbumOfAHome(home_id);
		} catch (Exception e) {
			throw e;
		} finally {
			homeDao.closeResource();
		}
		return albumList;
	}

	@Override
	public HomeAlbum createAlbum(HomeAlbum homeAlbum) throws Exception 
	{
		if (homeAlbum != null && 
			homeAlbum.getAlbum_name() != null && 
			homeAlbum.getHome() != null &&
			homeAlbum.getHome().getHome_id() != null &&
			homeAlbum.getCreate_date() != null) {
			
			try {
				homeAlbum = homeDao.createAlbum(homeAlbum);
			} catch (Exception e) {
				throw e;
			} finally {
				homeDao.closeResource();
			}
			
		} else {
			homeAlbum = null;
		}
		
		return homeAlbum;
	}

	@Override
	public HomeVideoType createVideoType(HomeVideoType videoType) throws Exception 
	{
		if (videoType != null && 
			videoType.getType_name() != null && 
			videoType.getHome() != null &&
			videoType.getHome().getHome_id() != null &&
			videoType.getCreate_date() != null) {
				try {
					videoType = homeDao.createVideoType(videoType);
				} catch (Exception e) {
					throw e;
				} finally {
					homeDao.closeResource();
				}
			} else {
				videoType = null;
			}
		return videoType;
	}

	@Override
	public List<HomeVideoType> getAllVideoTypeOfAhome(String home_id) throws Exception 
	{
		List<HomeVideoType> videoTypeList = null;
		try {
			videoTypeList = homeDao.getAllVideoTypeOfAhome(home_id);
		} catch (Exception e) {
			throw e;
		} finally {
			homeDao.closeResource();
		}
		return videoTypeList;
	}

	@Override
	public List<UserProfile> addMemberToAHome(Integer userProfileId,String homeId) throws Exception 
	{
		List<UserProfile> memberList = null;
		if (userProfileId != null && homeId != null) {
			try {
				memberList = homeDao.addMemberToAHome(userProfileId,homeId);
			} catch (Exception e) {
				memberList = null;
				throw e;
			} finally {
				homeDao.closeResource();
			}
		}
		return memberList;
	}
}
