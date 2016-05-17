package com.myhome.test;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.myhome.bean.UserProfile;
import com.myhome.service.IUserManagerService;
import com.myhome.service.impl.UserManagerServiceImpl;

public class UserManagerTest 
{
	private IUserManagerService userService = new UserManagerServiceImpl();
	
	@Test
	@Ignore
	public void test_getAllUserProfile() throws Exception 
	{
		List<UserProfile> userList = userService.getAllUserProfile();
		for (int i = 0; i < userList.size(); ++i) {
			UserProfile userProfile = userList.get(i);
			System.out.println
			( 
					"user_profile_id:" + userProfile.getUser_profile_id() + 
					"  phone_number:" + userProfile.getPhone_number() +
				    "  password:" + userProfile.getPassword() +
					"  real_name:" + userProfile.getReal_name() +
					"  register_date:" + userProfile.getRegister_date() +
					"  icon_url:" + userProfile.getIcon_url() +
					"  sex:" + userProfile.getSex() +
					"  birthday:" + userProfile.getBirthday() +
					"  sign:" + userProfile.getSign() 
			);
		}
	}
	
	@Test
	@Ignore
	public void test_userLogin() throws Exception
	{
		UserProfile userProfile = new UserProfile();
		userProfile.setPhone_number("1353523432");
		userProfile.setPassword("sddsfdf");
		userProfile = userService.userLogin(userProfile.getPhone_number(),
				userProfile.getPassword());
		if (userProfile != null) {
			System.out.println
			( 
					"user_profile_id:" + userProfile.getUser_profile_id() + 
					"  phone_number:" + userProfile.getPhone_number() +
				    "  password:" + userProfile.getPassword() +
					"  real_name:" + userProfile.getReal_name() +
					"  register_date:" + userProfile.getRegister_date() +
					"  icon_url:" + userProfile.getIcon_url() +
					"  sex:" + userProfile.getSex() +
					"  birthday:" + userProfile.getBirthday() +
					"  sign:" + userProfile.getSign() 
			);
		} else {
			System.out.println("fail");
		}
	}
	
	@Test
	@Ignore
	public void test_registerUser()
	{
		Date date = new Date();
		UserProfile userProfile = new UserProfile();
		userProfile.setPhone_number("13576353776");
		userProfile.setPassword("fujisheng555");
		userProfile.setRegister_date(date);
		try {
			userProfile = userService.userRegister(userProfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (userProfile != null) {
			System.out.println
			( 
					"user_profile_id:" + userProfile.getUser_profile_id() + 
					"  phone_number:" + userProfile.getPhone_number() +
				    "  password:" + userProfile.getPassword() +
					"  real_name:" + userProfile.getReal_name() +
					"  register_date:" + userProfile.getRegister_date() +
					"  icon_url:" + userProfile.getIcon_url() +
					"  sex:" + userProfile.getSex() +
					"  birthday:" + userProfile.getBirthday() +
					"  sign:" + userProfile.getSign() 
			);
		} else {
			System.out.println("fail");
		}
	}
	
	@Test
//	@Ignore
	public void test_updateUser()
	{
		UserProfile userProfile = new UserProfile();
		userProfile.setUser_profile_id(3);
		userProfile.setPhone_number("13576353788");
		userProfile.setPassword("dddd");
		userProfile.setReal_name("发送答复都是");
		userProfile.setIcon_url("ICONSDFSD");
		userProfile.setSex("m");
		userProfile.setBirthday(new Date());
		userProfile.setSign("水电费水电费速度速度");
		
		try {
			userProfile = userService.updateUser(userProfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (userProfile != null) {
			System.out.println
			( 
					"user_profile_id:" + userProfile.getUser_profile_id() + 
					"  phone_number:" + userProfile.getPhone_number() +
				    "  password:" + userProfile.getPassword() +
					"  real_name:" + userProfile.getReal_name() +
					"  register_date:" + userProfile.getRegister_date() +
					"  icon_url:" + userProfile.getIcon_url() +
					"  sex:" + userProfile.getSex() +
					"  birthday:" + userProfile.getBirthday() +
					"  sign:" + userProfile.getSign() 
			);
		} else {
			System.out.println("fail");
		}
	}
}
