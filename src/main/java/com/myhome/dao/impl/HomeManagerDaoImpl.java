package com.myhome.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.myhome.bean.FeedPhotos;
import com.myhome.bean.Feeds;
import com.myhome.bean.Home;
import com.myhome.bean.HomeAlbum;
import com.myhome.bean.HomeFeedRelation;
import com.myhome.bean.HomeVideoType;
import com.myhome.bean.UserHomeRelation;
import com.myhome.bean.UserProfile;
import com.myhome.dao.IHomeManagerDao;

public class HomeManagerDaoImpl extends BaseDao implements IHomeManagerDao
{

	@Override
	public Home insertHome(Home home) throws Exception 
	{
		Integer relationMaxId = this.getMaxId("user_home_relation", "relation_id");
		
		String homeSql = "insert into home (home_id,announcement,create_user_id,create_date,"
				+ "score,sign,home_name) values "
				+ "(?,?,?,?,?,?,?)";
		String relationSql = "insert into user_home_relation (user_profile_id,home_id,is_admin,relation_id) values "
				+ "(?,?,?,?)";
		
		this.openconnection();
		//开启事物
	    this.beginTransaction();
	    
		PreparedStatement homePs = conn.prepareStatement(homeSql);
		homePs.setString(1, home.getHome_id());
		homePs.setString(2, home.getAnnoucement());
		homePs.setInt(3, home.getCreater().getUser_profile_id());
		homePs.setTimestamp(4, new java.sql.Timestamp(home.getCreate_date().getTime()));
		homePs.setInt(5,home.getScore());
		homePs.setString(6,home.getSign());
		homePs.setString(7, home.getHome_name());
		
		PreparedStatement relationPs = conn.prepareStatement(relationSql);
		relationPs.setInt(1, home.getCreater().getUser_profile_id());
		relationPs.setString(2, home.getHome_id());
		relationPs.setInt(3, 1); //创建者为管理员
		relationPs.setInt(4, relationMaxId);
		
		try {
			homePs.executeUpdate();
			relationPs.executeUpdate();
			this.commit();
		} catch (Exception e) {
			//回滚事物
			this.rollback();
			home = null;
			throw e;
		} finally {
			//关闭事物
			conn.setAutoCommit(true);
		}
		homePs.close();
		relationPs.close();
				
		return home;
	}

	@Override
	public boolean isHomeExist(String home_id) throws Exception 
	{
		boolean isHomeExist = false;
		if (home_id != null) {
			String sql = "select count(*) as num from home where home_id = ?";
			this.openconnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, home_id);
			
			ResultSet rs = pstmt.executeQuery();
			if (rs != null) {
				rs.next();
				Integer count = rs.getInt("num");
				if (count > 0) {
					isHomeExist = true;
				}
			}
			
			rs.close();
			pstmt.close();
		}
		return isHomeExist;
	}

	@Override
	public List<Home> getHomeList(Integer user_profile_id) throws Exception 
	{
		List<Home> homeList = this.getHomeListBaseData(user_profile_id);
		homeList = this.getHomeMembersData(homeList);
		
		return homeList;
	}
	//获取一个用户所有家庭列表的基本数据
	public List<Home> getHomeListBaseData(Integer user_profile_id) throws Exception
	{
		List<Home> homeList = null;
		if (user_profile_id != null) {
			String sql = "select * from home_user_view where user_profile_id = ?";
			this.openconnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user_profile_id);
			ResultSet rs = pstmt.executeQuery();
			if (rs != null) {
				homeList = new ArrayList<Home>();
				while (rs.next()) {
					Home home = new Home();
					
					home.setHome_id(rs.getString("home_id"));
					home.setAnnoucement(rs.getString("announcement"));
					home.setScore(rs.getInt("score"));
					home.setSign(rs.getString("sign"));
					home.setHome_name(rs.getString("home_name"));
					home.setCreate_date(rs.getTimestamp("create_date"));
					
					UserProfile userProfile = new UserProfile();
					userProfile.setUser_profile_id(rs.getInt("create_user_id"));
					
					home.setCreater(userProfile);
					
					homeList.add(home);
				}
			}
			
			rs.close();
			pstmt.close();	
		}
		return homeList;
	}
	
	//获取家庭成员的部分数据
	public List<Home> getHomeMembersData(List<Home> homeList) throws Exception
	{
		for (Home home : homeList) {
			if (home.getHome_id() == null) continue;
			List<UserProfile> memberList = this.getMemberListOfAHome(home.getHome_id());
			home.setMemberList(memberList);
		}
		return homeList;
	}
	//获取一个家庭下的所有 成员信息
	public List<UserProfile> getMemberListOfAHome(String homeId) throws Exception
	{
		List<UserProfile> memberList = null;
		String sql = "select * from home_members_detail_view where home_id = ?";
		PreparedStatement ps = this.conn.prepareStatement(sql);
		ps.setString(1,homeId);
		
		ResultSet rs = ps.executeQuery();
		if (rs != null) {
			memberList = new ArrayList<UserProfile>();
			while (rs.next()) {
				UserProfile member = new UserProfile();
				member.setUser_profile_id(rs.getInt("user_profile_id"));
				member.setReal_name(rs.getString("real_name"));
				member.setIcon_url(rs.getString("icon_url"));
				member.setPhone_number(rs.getString("phone_number"));
				
				memberList.add(member);
			}
			rs.close();
			ps.close();
		}
		return memberList;
	}
	
	@Override
	public List<UserHomeRelation> getUserHomeRelationList(Integer user_profile_id) throws Exception
	{
		List<UserHomeRelation> userHomeList = null;
		if (user_profile_id != null) {
			String sql = "select * from user_home_relation where user_profile_id = ?";
			this.openconnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user_profile_id);
			ResultSet rs = pstmt.executeQuery();
			if (rs != null) {
				userHomeList = new ArrayList<UserHomeRelation>();
				while (rs.next()) {
					
					UserHomeRelation userHome = new UserHomeRelation();
					userHome.setIs_admin(rs.getBoolean("is_admin"));
					userHome.setRelation_id(rs.getInt("relation_id"));
					
					Home home = new Home();
					home.setHome_id(rs.getString("home_id"));
					userHome.setHome(home);
					
					UserProfile userProfile = new UserProfile();
					userProfile.setUser_profile_id(rs.getInt("user_profile_id"));
					userHome.setUserProfile(userProfile);
					
					userHomeList.add(userHome);
				}
			}
			
			rs.close();
			pstmt.close();	
		}
		return userHomeList;
	}
	
	public List<HomeAlbum> getAllAlbumOfAHome(String home_id) throws Exception
	{
		List<HomeAlbum> albumList = null;
		if (home_id != null) {
			String sql = "select * from home_album where home_id = ? order by create_date desc";
			this.openconnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, home_id);
			ResultSet rs = pstmt.executeQuery();
			if (rs != null) {
				albumList = new ArrayList<HomeAlbum>();
				while (rs.next()) {
					HomeAlbum homeAlbum = new HomeAlbum();
					
					homeAlbum.setAlbum_id(rs.getInt("album_id"));
					homeAlbum.setAlbum_name(rs.getString("album_name"));
					homeAlbum.setCreate_date(rs.getTimestamp("create_date"));
					
					Home home = new Home();
					home.setHome_id(rs.getString("home_id"));
					
					homeAlbum.setHome(home);
					
					albumList.add(homeAlbum);
				}
				rs.close();
				pstmt.close();
			}
		}
		return albumList;
	}

	@Override
	public HomeAlbum createAlbum(HomeAlbum homeAlbum) throws Exception 
	{
		//获取主键的最大索引
		Integer maxId = this.getMaxId("home_album", "album_id");
		
		String sql = "insert into home_album (album_id,album_name,create_date,home_id) values " +
		"(?,?,?,?)";
		openconnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, maxId);
		pstmt.setString(2, homeAlbum.getAlbum_name());
		pstmt.setTimestamp(3, new java.sql.Timestamp(homeAlbum.getCreate_date().getTime()));
		pstmt.setString(4, homeAlbum.getHome().getHome_id());
		
		int result = pstmt.executeUpdate();
		if (result != 0) {
			homeAlbum.setAlbum_id(maxId);
		} else {
			homeAlbum = null;
		}
		
		pstmt.close();
		
		return homeAlbum;
	}

	@Override
	public HomeVideoType createVideoType(HomeVideoType videoType) throws Exception
	{
		Integer maxId = this.getMaxId("home_video_type", "video_type_id");
		
		String sql = "insert into home_video_type (video_type_id,type_name,create_date,home_id) values " +
		"(?,?,?,?)";
		openconnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, maxId);
		pstmt.setString(2, videoType.getType_name());
		pstmt.setTimestamp(3, new java.sql.Timestamp(videoType.getCreate_date().getTime()));
		pstmt.setString(4, videoType.getHome().getHome_id());
		
		int result = pstmt.executeUpdate();
		if (result != 0) {
			videoType.setVideo_type_id(maxId);;
		} else {
			videoType = null;
		}
		
		pstmt.close();
		
		return videoType;
	}

	@Override
	public List<HomeVideoType> getAllVideoTypeOfAhome(String home_id) throws Exception 
	{
		List<HomeVideoType> vidoeTypeList = null;
		if (home_id != null) {
			String sql = "select * from home_video_type where home_id = ? order by create_date desc";
			this.openconnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, home_id);
			ResultSet rs = pstmt.executeQuery();
			if (rs != null) {
				vidoeTypeList = new ArrayList<HomeVideoType>();
				while (rs.next()) {
					HomeVideoType videoType = new HomeVideoType();
					
					videoType.setVideo_type_id(rs.getInt("video_type_id"));
					videoType.setType_name(rs.getString("type_name"));
					videoType.setCreate_date(rs.getTimestamp("create_date"));
					
					Home home = new Home();
					home.setHome_id(rs.getString("home_id"));
					
					videoType.setHome(home);
					
					vidoeTypeList.add(videoType);
				}
				rs.close();
				pstmt.close();
			}
		}
		return vidoeTypeList;
	}

	@Override
	public List<UserProfile> addMemberToAHome(Integer userProfileId,String homeId) throws Exception
	{
		Integer maxId = this.getMaxId("user_home_relation", "relation_id");
		
		String relationSql = "insert into user_home_relation (user_profile_id,home_id,is_admin,relation_id) values "
				+ "(?,?,?,?)";
		this.openconnection();
		PreparedStatement pstmt = conn.prepareStatement(relationSql);
		pstmt.setInt(1, userProfileId);
		pstmt.setString(2, homeId);
		pstmt.setBoolean(3, false);
		pstmt.setInt(4, maxId);
		
		int result = pstmt.executeUpdate();
		
		List<UserProfile> memberList = null;
		if (result > 0) {
			memberList = this.getMemberListOfAHome(homeId);
		}
		
		return memberList;
	}

}
