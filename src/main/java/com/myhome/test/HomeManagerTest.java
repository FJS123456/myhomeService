package com.myhome.test;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.myhome.bean.Home;
import com.myhome.bean.HomeAlbum;
import com.myhome.bean.HomeVideoType;
import com.myhome.bean.UserHomeRelation;
import com.myhome.bean.UserProfile;
import com.myhome.service.IHomeManagerService;
import com.myhome.service.impl.HomeManagerServiceImpl;

public class HomeManagerTest 
{
	private IHomeManagerService homeService = new HomeManagerServiceImpl();
	
	@Test
	@Ignore
	public void test_createHome()
	{
		UserProfile userProfile = new UserProfile();
		userProfile.setUser_profile_id(3);
		
		Home home = new Home();
		home.setHome_name("testtesttest");
		home.setCreate_date(new Date());
		home.setScore(0); //默认积分为0
		home.setSign("水电费水电费事实上事实上水水水水");
		home.setCreater(userProfile);
		
		try {
			home = homeService.createHome(home);
		} catch (Exception e) {
			home = null;
			e.printStackTrace();
		}
		
		if (home != null) {
			System.out.println
			(
					"  home_id:" + home.getHome_id() + 
					"  announcement:" + home.getAnnoucement() +
				    "  create_user_id:" + home.getCreater().getUser_profile_id() +
					"  create_date:" + home.getCreate_date() +
					"  score:" + home.getScore() +
					"  sign:" + home.getSign() +
					"  home_name:" + home.getHome_name() 
			);
		} else {
			System.out.println("创建房间失败!!!");
		}
	}
	@Test
	@Ignore
	public void test_getHomeList()
	{
		List<Home> homeList = null;
		try {
			homeList = homeService.getHomeList(3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (homeList != null) {
			for (int i = 0 ; i < homeList.size() ; ++i) {
				Home home = homeList.get(i);
				System.out.println
				(
						"  home_id:" + home.getHome_id() + 
						"  announcement:" + home.getAnnoucement() +
					    "  create_user_id:" + home.getCreater().getUser_profile_id() +
						"  create_date:" + home.getCreate_date() +
						"  score:" + home.getScore() +
						"  sign:" + home.getSign() +
						"  home_name:" + home.getHome_name()
				);
				System.out.println("***********************");
				List<UserProfile> memberList = home.getMemberList();
				for (UserProfile member : memberList) {
					System.out.println
					(
						" user_profile_id:" + member.getUser_profile_id() + 
						" real_name:" + member.getReal_name() + 
						" icon_url:" + member.getIcon_url()
				    );
				}
			}
		}
	}
	
	@Test
	@Ignore
	public void test_getUserHomeRelationList()
	{
		List<UserHomeRelation> userHomeList = null;
		try {
			userHomeList = homeService.getUserHomeRelationList(10);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (userHomeList != null) {
			for (int i = 0 ; i < userHomeList.size() ; ++i) {
				UserHomeRelation userHome = userHomeList.get(i);
				System.out.println
				(
						"  user_profile_id:" + userHome.getUserProfile().getUser_profile_id() + 
						"  home_id:" + userHome.getHome().getHome_id() +
					    "  is_adming:" + userHome.getIs_admin() +
					    "  relation_id:" + userHome.getRelation_id()
				);
			}
		}
	}
	
	@Test
	@Ignore
	public void test_getAllAlbumOfAHome() 
	{
		List<HomeAlbum> albumList = null;
		try {
			albumList = homeService.getAllAlbumOfAHome("12792838");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (albumList != null) {
			for (int i = 0; i < albumList.size(); i++) {
				HomeAlbum album = albumList.get(i);
				System.out.println
				(
						"  album_id:" + album.getAlbum_id() + 
						"  album_name:" + album.getAlbum_name() +
						"  create_date:" + album.getCreate_date() +
					    "  home_id:" + album.getHome().getHome_id()
				);
			}
		}
	}
	
	@Test
	@Ignore
	public void test_createAlbum() 
	{
		HomeAlbum album = new HomeAlbum();
		album.setAlbum_name("父母");
		album.setCreate_date(new Date());
		
		Home home = new Home();
		home.setHome_id("12792838");
		
		album.setHome(home);
		
		try {
			album = homeService.createAlbum(album);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (album != null) {
			System.out.println("创建相册成功");
		} else {
			System.out.println("创建相册失败");
		}
		
	}
	
	@Test
	@Ignore
	public void test_getAllVideoTypeOfAHome() 
	{
		List<HomeVideoType> videoTypeList = null;
		try {
			videoTypeList = homeService.getAllVideoTypeOfAhome("12792838");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (videoTypeList != null) {
			for (int i = 0; i < videoTypeList.size(); i++) {
				HomeVideoType videoType = videoTypeList.get(i);
				System.out.println
				(
						"  video_type_id:" + videoType.getVideo_type_id() + 
						"  type_name:" + videoType.getType_name() +
						"  create_date:" + videoType.getCreate_date() +
					    "  home_id:" + videoType.getHome().getHome_id()
				);
			}
		}
	}
	
	@Test
	@Ignore
	public void test_createHomeVideoType() 
	{
		HomeVideoType videoType = new HomeVideoType();
		videoType.setType_name("童年");
		videoType.setCreate_date(new Date());
		
		Home home = new Home();
		home.setHome_id("12792838");
		
		videoType.setHome(home);
		
		try {
			videoType = homeService.createVideoType(videoType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (videoType != null) {
			System.out.println("创建视频库成功");
		} else {
			System.out.println("创建视频库失败");
		}
		
	}
	
	@Test
//	@Ignore
	public void test_addMemberToHome() 
	{
		List<UserProfile> memberList = null;
		try {
			memberList = homeService.addMemberToAHome(5,"12792838");
		} catch (Exception e) {
			memberList = null;
			e.printStackTrace();
		}
		
		if (memberList != null) {
			for (UserProfile userProfile : memberList) {
				System.out.println
				(
					" user_profile_id:" + userProfile.getUser_profile_id() + 
					" real_name:" + userProfile.getReal_name() + 
					" icon_url:" + userProfile.getIcon_url()
			    );
			}
		} else {
			System.out.println("添加家庭成员失败");
		}
		
	}
	
}
