package com.myhome.dao;

import java.util.List;

import com.myhome.bean.UserProfile;

public interface IUserManagerDao extends IBaseDao
{
	public List<UserProfile> getAllUserProfile() throws Exception;
	public UserProfile userLogin(String phone_number,String password) throws Exception;
	public boolean isUserExist(String phone_number) throws Exception;
	public UserProfile insertUser(UserProfile userProfile) throws Exception;
	public UserProfile updateUser(UserProfile userProfile) throws Exception;
	//更新用户头像的url
	public String updateUserIconUrl(Integer userProfileId,String iconUrl) throws Exception;
	//根据手机号查找用户
	public UserProfile findUserByPhoneNumber(String phoneNumber) throws Exception;
}
