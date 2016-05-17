package com.myhome.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.myhome.bean.UserProfile;
import com.myhome.dao.IUserManagerDao;

public class UserManagerDaoImpl extends BaseDao implements IUserManagerDao
{

	@Override
	public List<UserProfile> getAllUserProfile() throws Exception
	{
		String sql = "select * from user_profile";
		List<UserProfile> list = new ArrayList<UserProfile>();
		openconnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			
			UserProfile userProfile = new UserProfile();
			
			userProfile.setUser_profile_id(rs.getInt("user_profile_id"));
			userProfile.setPhone_number(rs.getString("phone_number"));
			userProfile.setPassword(rs.getString("password"));
			userProfile.setReal_name(rs.getString("real_name"));
			userProfile.setRegister_date(rs.getTimestamp("register_date"));
			userProfile.setIcon_url(rs.getString("icon_url"));
			userProfile.setSex(rs.getString("sex"));
			userProfile.setBirthday(rs.getTimestamp("birthday"));
			userProfile.setSign(rs.getString("sign"));
						
			list.add(userProfile);
		}
		rs.close();
		pstmt.close();
		return list;
	}

	@Override
	public UserProfile userLogin(String phone_number,String password) throws Exception
	{
		UserProfile userProfile = null;
		if (phone_number != null && password != null) {
			String sql = "select * from user_profile where phone_number = ? and password = ?";
			openconnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, phone_number);
			pstmt.setString(2, password);
			
			ResultSet rs = pstmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					userProfile = new UserProfile();
					
					userProfile.setPhone_number(phone_number);
					userProfile.setPassword(password);
					userProfile.setUser_profile_id(rs.getInt("user_profile_id"));
					userProfile.setReal_name(rs.getString("real_name"));
					userProfile.setRegister_date(rs.getTimestamp("register_date"));
					userProfile.setIcon_url(rs.getString("icon_url"));
					userProfile.setSex(rs.getString("sex"));
					userProfile.setBirthday(rs.getTimestamp("birthday"));
					userProfile.setSign(rs.getString("sign"));
				}
				rs.close();
				pstmt.close();
			}
			
		}
		return userProfile;
	}

	@Override
	public boolean isUserExist(String phone_number) throws Exception 
	{
		boolean isUserExist = false;
		if (phone_number != null) {
			String sql = "select count(*) as num from user_profile where  phone_number = ?";
			this.openconnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, phone_number);
			
			ResultSet rs = pstmt.executeQuery();
			if (rs != null) {
				rs.next();
				Integer count = rs.getInt("num");
				if (count > 0) {
					isUserExist = true;
				}
			}
			
			rs.close();
			pstmt.close();
		}
		return isUserExist;
	}

	@Override
	public UserProfile insertUser(UserProfile userProfile) throws Exception 
	{
		//获取主键的最大索引
		Integer maxId = this.getMaxId("user_profile", "user_profile_id");
		
		String sql = "insert into user_profile (user_profile_id,phone_number,password,real_name,"
				+ "register_date,icon_url,sex,birthday,sign) values "
				+ "(" + maxId + ",?,?,?,?,?,?,?,?)";
		this.openconnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, userProfile.getPhone_number());
		pstmt.setString(2, userProfile.getPassword());
		pstmt.setString(3,userProfile.getReal_name());
		pstmt.setTimestamp(4, new java.sql.Timestamp(userProfile.getRegister_date().getTime()));
		pstmt.setString(5,userProfile.getIcon_url());
		pstmt.setString(6,userProfile.getSex());
		if (userProfile.getBirthday() == null){
			pstmt.setDate(7, null);
		} else {
			pstmt.setDate(7,new java.sql.Date(userProfile.getBirthday().getTime()));
		}
		pstmt.setString(8, userProfile.getSign());
		
		int result = pstmt.executeUpdate();
		if (result > 0) {
			userProfile.setUser_profile_id(maxId);
		} else {
			userProfile = null;
		}
		
		pstmt.close();
		
		return userProfile;
	}

	@Override
	public UserProfile updateUser(UserProfile userProfile) throws Exception 
	{
		String sql = "update user_profile set phone_number = ?,password = ?,real_name = ?,icon_url = ?,sex = ?,birthday = ?,sign = ? "
				+ "where user_profile_id = ?";
		this.openconnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, userProfile.getPhone_number());
		pstmt.setString(2, userProfile.getPassword());
		pstmt.setString(3,userProfile.getReal_name());
		pstmt.setString(4, userProfile.getIcon_url());
		pstmt.setString(5, userProfile.getSex());
		pstmt.setTimestamp(6, new java.sql.Timestamp(userProfile.getBirthday().getTime()));
		pstmt.setString(7, userProfile.getSign());
		pstmt.setInt(8, userProfile.getUser_profile_id());
		
		int result = pstmt.executeUpdate();
		if (result <= 0) {
			userProfile = null;
		}
		
		return userProfile;
	}

	@Override
	public String updateUserIconUrl(Integer userProfileId, String iconUrl) throws Exception 
	{
		String sql = "update user_profile set icon_url = ? where user_profile_id = ?";
		this.openconnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, iconUrl);
		pstmt.setInt(2, userProfileId);
		
		int result = pstmt.executeUpdate();
		if (result <= 0) {
			iconUrl = null;
		} 
		pstmt.close();
		return iconUrl;
	}

	@Override
	public UserProfile findUserByPhoneNumber(String phoneNumber) throws Exception 
	{
		String sql = "select * from user_profile where phone_number = ?";
		openconnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, phoneNumber);
		ResultSet rs = pstmt.executeQuery();
		UserProfile userProfile = null;
		while (rs.next()) {
			
			userProfile = new UserProfile();
			
			userProfile.setUser_profile_id(rs.getInt("user_profile_id"));
			userProfile.setPhone_number(rs.getString("phone_number"));
			userProfile.setPassword(rs.getString("password"));
			userProfile.setReal_name(rs.getString("real_name"));
			userProfile.setRegister_date(rs.getTimestamp("register_date"));
			userProfile.setIcon_url(rs.getString("icon_url"));
			userProfile.setSex(rs.getString("sex"));
			userProfile.setBirthday(rs.getTimestamp("birthday"));
			userProfile.setSign(rs.getString("sign"));
						
		}
		rs.close();
		pstmt.close();
		return userProfile;
	}
	
}
