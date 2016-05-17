package com.myhome.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.myhome.bean.Feeds;
import com.myhome.bean.Home;
import com.myhome.service.IFeedManagerService;
import com.myhome.service.impl.FeedManagerServiceImpl;

@Path("/feed")
public class FeedResource 
{
	private IFeedManagerService feedService = new FeedManagerServiceImpl();
	
	@POST
	@Path("/createFeed")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public Map<String, Object> createFeed(Feeds feed)
	{
		try {
			feed = feedService.insertAFeed(feed);
		} catch (Exception e) {
			feed = null;
			e.printStackTrace();
		}
		
		int error_code = 1;
		String msg = "创建feed成功";
		if (feed == null) {
			error_code = 0; //失败
			msg = "创建feed失败";
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("feed", feed);
		map.put("error_code", error_code);
		map.put("msg", msg);
		return map;
	}
	
	@POST
	@Path("/getFeedList")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public Map<String, Object> getFeedList(Home home)
	{
		List<Feeds> feedList = new ArrayList<Feeds>();
		Boolean isPublic = home.getHome_id() == null ? true : false;
		try {
			feedList = feedService.getFeedList(home.getHome_id(), isPublic);
		} catch (Exception e) {
			feedList = null;
			e.printStackTrace();
		}
		
		int error_code = 1;
		String msg = "获取feed列表成功";
		if (feedList == null) {
			error_code = 0; //失败
			msg = "获取feed列表失败";
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("feedList", feedList);
		map.put("error_code", error_code);
		map.put("msg", msg);
		return map;
	}
}
