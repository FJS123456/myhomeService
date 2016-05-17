package com.myhome.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.myhome.bean.UserProfile;
import com.myhome.service.IUserManagerService;
import com.myhome.service.impl.UserManagerServiceImpl;

@Path("user")
public class UserResource 
{
	private IUserManagerService userService = new UserManagerServiceImpl();
	
	@GET
	@Path("/getUserList")
	@Produces({MediaType.APPLICATION_JSON})
	public Map<String,Object> getAllUserProfile()
	{
		List<UserProfile> userList = null;
		try {
			userList = userService.getAllUserProfile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("list", userList);
		map.put("count", 20);
		map.put("index", 0);
		return map;
	}
	
	@POST
	@Path("/userLogin")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public Map<String,Object> userLogin(UserProfile userProfile)
	{
		try {
			userProfile = userService.userLogin(userProfile.getPhone_number(),
					userProfile.getPassword());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int error_code = 1;
		String msg = "请求成功";
		if (userProfile == null) {
			error_code = 0; //失败
			msg = "用户名或密码错误";
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userProfile", userProfile);
		map.put("error_code", error_code);
		map.put("msg", msg);
		return map;
	}
	
	@POST
	@Path("/userRegister")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public Map<String,Object> userRegister(UserProfile userProfile)
	{
		try {
			userProfile = userService.userRegister(userProfile);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		int error_code = 1;
		String msg = "请求成功";
		if (userProfile == null) {
			error_code = 0; //失败
			msg = "该用户已注册";
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("error_code", error_code);
		map.put("msg", msg);
		map.put("userProfile", userProfile);
		return map;
	}
	
	@POST
	@Path("/userUpdate")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public Map<String,Object> userUpdate(UserProfile userProfile)
	{
		try {
			userProfile = userService.updateUser(userProfile);
		} catch (Exception e) {
			userProfile = null;
			e.printStackTrace();
		}
		
		int error_code = 1;
		String msg = "更新用户信息成功成功";
		if (userProfile == null) {
			error_code = 0; //失败
			msg = "更新用户信息失败";
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("error_code", error_code);
		map.put("msg", msg);
		map.put("userProfile", userProfile);
		return map;
	}
	
}
