package com.myhome.service.impl;

import java.util.List;

import com.myhome.bean.UserProfile;
import com.myhome.dao.IUserManagerDao;
import com.myhome.dao.impl.UserManagerDaoImpl;
import com.myhome.service.IUserManagerService;

public class UserManagerServiceImpl implements IUserManagerService
{
	private IUserManagerDao userDao = new UserManagerDaoImpl();
	
	@Override
	public List<UserProfile> getAllUserProfile() throws Exception
	{
		List<UserProfile> userList = null;
		try {
			userList = userDao.getAllUserProfile();
		} catch (Exception e) {
			throw e;
		}finally{
			userDao.closeResource();
		}
		
		return userList;
	}
	@Override
	public UserProfile userLogin(String phone_number,String password) throws Exception
	{
		UserProfile user = null;
		try {
		    user = userDao.userLogin(phone_number,password);
		} catch (Exception e) {
			throw e;
		}finally {
			userDao.closeResource();
		}
		return user;
	}
	@Override
	public UserProfile userRegister(UserProfile userProfile) throws Exception 
	{
		if (userProfile != null && 
		    userProfile.getPhone_number() != null && 
		    userProfile.getPassword() != null && 
		    userProfile.getRegister_date() != null) {
			try {
				boolean isExist = userDao.isUserExist(userProfile.getPhone_number());
				if (!isExist) {
					userProfile = userDao.insertUser(userProfile);
				} else {
					userProfile = null;
				}
			} catch (Exception e) {
				// TODO: handle exception
				throw e;
			} finally {
				userDao.closeResource();
			}
		} else {
			userProfile = null;
		}
		return userProfile;
	}
	@Override
	public UserProfile updateUser(UserProfile userProfile) throws Exception
	{
		if (userProfile != null) {
			try {
				userProfile = userDao.updateUser(userProfile);
			} catch (Exception e) {
				userProfile = null;
				throw e;
			} finally {
				userDao.closeResource();
			}
		}
		
		return userProfile;
	}
	@Override
	public String updateUserIconUrl(Integer userProfileId, String iconUrl) throws Exception 
	{
		if (userProfileId != null && iconUrl != null) {
			try {
				iconUrl = userDao.updateUserIconUrl(userProfileId, iconUrl);
			} catch (Exception e) {
				iconUrl = null;
				throw e;
			} finally {
				userDao.closeResource();
			}
			
		} else {
			iconUrl = null;
		}
		
		return iconUrl;
	}
	@Override
	public UserProfile findUserByPhoneNumber(String phoneNumber) throws Exception 
	{
		UserProfile userProfile = null;
		if (phoneNumber != null) {
			try {
				userProfile = userDao.findUserByPhoneNumber(phoneNumber);
			} catch (Exception e) {
				userProfile = null;
				e.printStackTrace();
			} finally {
				userDao.closeResource();
			}
		}
		return userProfile;
	}
	
}
