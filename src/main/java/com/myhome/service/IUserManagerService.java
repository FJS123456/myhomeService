package com.myhome.service;

import java.util.List;

import com.myhome.bean.UserProfile;

public interface IUserManagerService
{
	public List<UserProfile> getAllUserProfile() throws Exception;
	public UserProfile userLogin(String phone_number,String password) throws Exception;
	public UserProfile userRegister(UserProfile userProfile) throws Exception;
	public UserProfile updateUser(UserProfile userProfile) throws Exception;
	public String updateUserIconUrl(Integer userProfileId,String iconUrl) throws Exception;
	public UserProfile findUserByPhoneNumber(String phoneNumber) throws Exception;
}
