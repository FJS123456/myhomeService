package com.myhome.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.myhome.bean.Home;
import com.myhome.bean.HomeAlbum;
import com.myhome.bean.HomeVideoType;
import com.myhome.bean.UserHomeRelation;
import com.myhome.bean.UserProfile;
import com.myhome.service.IHomeManagerService;
import com.myhome.service.IUserManagerService;
import com.myhome.service.impl.HomeManagerServiceImpl;
import com.myhome.service.impl.UserManagerServiceImpl;

@Path("/home")
public class HomeResource 
{
	private IHomeManagerService homeService = new HomeManagerServiceImpl();
	private IUserManagerService userService = new UserManagerServiceImpl();
	
	@POST
	@Path("/createHome")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public Map<String, Object> createHome(Home home)
	{
		try {
			home = homeService.createHome(home);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int error_code = 1;
		String msg = "创建家庭成功";
		if (home == null) {
			error_code = 0; //失败
			msg = "创建家庭失败";
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("home", home);
		map.put("error_code", error_code);
		map.put("msg", msg);
		return map;
	}
	
	@POST
	@Path("/getHomeList")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public Map<String, Object> getHomeList(UserProfile userProfile) 
	{
		List<Home> homeList = null;
		List<UserHomeRelation> userHomeRelationList = null;
		try {
			homeList = homeService.getHomeList(userProfile.getUser_profile_id());
			userHomeRelationList = homeService.getUserHomeRelationList(userProfile.getUser_profile_id());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int error_code = 1;
		String msg = "获取家庭列表成功";
		if (homeList == null) {
			error_code = 0; //失败
			msg = "获取家庭列表失败";
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("homeList", homeList);
		map.put("userHomeRelationList", userHomeRelationList);
		map.put("error_code", error_code);
		map.put("msg", msg);
		return map;
	}
	
	@POST
	@Path("/getAlbumList")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public Map<String, Object> getAlbumList(Home home)
	{
		List<HomeAlbum> albumList = null;
		try {
			 albumList = homeService.getAllAlbumOfAHome(home.getHome_id());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int error_code = 1;
		String msg = "获取相册列表成功";
		if (albumList == null) {
			error_code = 0; //失败
			msg = "获取相册列表失败";
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("albumList", albumList);
		map.put("error_code", error_code);
		map.put("msg", msg);
		return map;
	}
	
	@POST
	@Path("/createHomeAlbum")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public Map<String, Object> createHomeAlbum(HomeAlbum homeAlbum)
	{
		try {
			 homeAlbum = homeService.createAlbum(homeAlbum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int error_code = 1;
		String msg = "创建相册成功";
		if (homeAlbum == null) {
			error_code = 0; //失败
			msg = "创建相册失败";
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("homeAlbum", homeAlbum);
		map.put("error_code", error_code);
		map.put("msg", msg);
		return map;
	}
	
	@POST
	@Path("/getVideoTypeList")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public Map<String, Object> getVideoTypeList(Home home)
	{
		List<HomeVideoType> videoTypeList = null;
		try {
			videoTypeList = homeService.getAllVideoTypeOfAhome(home.getHome_id());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int error_code = 1;
		String msg = "获取视频库列表成功";
		if (videoTypeList == null) {
			error_code = 0; //失败
			msg = "获取视频库列表失败";
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("videoTypeList", videoTypeList);
		map.put("error_code", error_code);
		map.put("msg", msg);
		return map;
	}
	
	@POST
	@Path("/createHomeVideoType")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public Map<String, Object> createHomeVideoType(HomeVideoType videoType)
	{
		try {
			videoType = homeService.createVideoType(videoType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int error_code = 1;
		String msg = "创建视频库成功";
		if (videoType == null) {
			error_code = 0; //失败
			msg = "创建视频库失败";
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("videoType", videoType);
		map.put("error_code", error_code);
		map.put("msg", msg);
		return map;
	}
	
	@POST
	@Path("/addHomeMember")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public Map<String, Object> addHomeMember(Map<String,Object> info)
	{
		String homeId = info.get("homeId").toString();
		String phoneNumber = info.get("phoneNumber").toString();
		UserProfile member = null;
		try {
			member = userService.findUserByPhoneNumber(phoneNumber);
		} catch (Exception e) {
			member = null;
			e.printStackTrace();
		}
		//该手机用户存在
		List<UserProfile> memberList = null;
		if (member != null) {
			try {
				memberList = homeService.addMemberToAHome(member.getUser_profile_id(),homeId);
			} catch (Exception e) {
				memberList = null;
				e.printStackTrace();
			}
		}
		
		int error_code = 1;
		String msg = "添加家庭成员成功";
		if (memberList == null) {
			error_code = 0; //失败
			msg = "添加家庭成员失败";
			if (member == null) {
				msg = "该用户不存在";
			}
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberList", memberList);
		map.put("error_code", error_code);
		map.put("msg", msg);
		return map;
	}
	
}
