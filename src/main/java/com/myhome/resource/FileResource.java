package com.myhome.resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.myhome.bean.FeedPhotos;
import com.myhome.bean.Feeds;
import com.myhome.bean.HomeAlbum;
import com.myhome.bean.HomePhotos;
import com.myhome.bean.HomeVideoType;
import com.myhome.bean.HomeVideos;
import com.myhome.bean.UserProfile;
import com.myhome.service.IFeedManagerService;
import com.myhome.service.IFileManagerService;
import com.myhome.service.IUserManagerService;
import com.myhome.service.impl.FeedManagerServiceImpl;
import com.myhome.service.impl.FileManagerServiceImpl;
import com.myhome.service.impl.UserManagerServiceImpl;

@Path("/file")
public class FileResource
{
	private static final Logger LOG = LogManager.getLogger(FileResource.class);
	private IFileManagerService fileService = new FileManagerServiceImpl();
	private IFeedManagerService feedService = new FeedManagerServiceImpl();
	private IUserManagerService userService = new UserManagerServiceImpl();
	
	/**
     * 上传家庭相片
     */
    @Context
	ServletContext servletContext;
    @POST
    @Path("uploadHomePhoto")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> uploadHomePhoto(
			@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition 
			contentDispositionHeader,@FormDataParam("userProfileId") String userProfileId,
			@FormDataParam("albumId") String albumId,
			@FormDataParam("albumName") String albumName,
			@FormDataParam("homeId") String homeId,
			@FormDataParam("width") String width,
			@FormDataParam("height") String height
			) 
					throws IOException 
    {
    	    UUID uuid = UUID.randomUUID();  
        String fileName = uuid.toString() + ".jpg";
//		String fileName = contentDispositionHeader.getFileName();
		//resource为自己创建的目录，
        String url = "/resource/" + homeId + "/album/" + albumName + "/" + fileName;
		
        //将图片写入到本地
        writeFile(fileInputStream, url);
		
		UserProfile userProfile = new UserProfile();
		userProfile.setUser_profile_id(new Integer(userProfileId));
		
		HomeAlbum album = new HomeAlbum();
		album.setAlbum_id(new Integer(albumId));
		
		HomePhotos photo = new HomePhotos();
		photo.setCreater(userProfile);
		photo.setHomeAlbum(album);
		photo.setPhoto_url(url);
		photo.setUpload_date(new Date());
		photo.setWidth(new Double(width));
		photo.setHeight(new Double(height));
		
		try {
			photo = fileService.insertAPhoto(photo);
		} catch (Exception e) {
			photo = null;
			e.printStackTrace();
		}
		
		int error_code = 1;
		String msg = "上传相片成功";
		if (photo == null) {
			error_code = 0; //失败
			msg = "上传相片失败";
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("photo", photo);
		map.put("error_code", error_code);
		map.put("msg", msg);
		return map;

    }
    
    @POST
	@Path("/getHomePhotoList")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public Map<String, Object> getHomePhotoList(HomeAlbum album)
	{
		List<HomePhotos> photoList = null;
		try {
			 photoList = fileService.getAllHomePhotosFromAblum(album);
		} catch (Exception e) {
			photoList = null;
			e.printStackTrace();
		}
		
		int error_code = 1;
		String msg = "获取家庭相片成功";
		if (photoList == null) {
			error_code = 0; //失败
			msg = "获取家庭相片失败";
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("photoList", photoList);
		map.put("error_code", error_code);
		map.put("msg", msg);
		return map;
	}
    
    @POST
    @Path("/uploadHomeVideo")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> uploadHomeVideo(
			@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition 
			contentDispositionHeader,@FormDataParam("userProfileId") String userProfileId,
			@FormDataParam("videoTypeId") String videoTypeId,
			@FormDataParam("videoTypeName") String videoTypeName,
			@FormDataParam("homeId") String homeId,
			@FormDataParam("videoTotalTime") String videoTotalTime,
			@FormDataParam("videoDesc") String videoDesc
			) 
					throws IOException 
    {
    		UUID uuid = UUID.randomUUID();  
        String fileName = uuid.toString() + ".MOV";
		//resource为自己创建的目录，
        String url = "/resource/" + homeId + "/videoType/" + videoTypeName + "/" + fileName;
        //将图片写入到本地
        writeFile(fileInputStream, url);
        
        HomeVideos video = new HomeVideos();
        video.setVideo_url(url);
        video.setUpload_date(new Date());
        video.setPlay_number(0);
        video.setVideo_total_time(new Integer(videoTotalTime));
        video.setVideo_desc(videoDesc);
        
        UserProfile creater = new UserProfile();
        creater.setUser_profile_id(new Integer(userProfileId));
        video.setCreater(creater);
        
        HomeVideoType videoType = new HomeVideoType();
        videoType.setVideo_type_id(new Integer(videoTypeId));
        video.setVideoType(videoType);
        
        try {
			video = fileService.insertAVideo(video);
		} catch (Exception e) {
			video = null;
			e.printStackTrace();
		}
		
		int error_code = 1;
		String msg = "上传视频成功";
		if (video == null) {
			error_code = 0; //失败
			msg = "上传视频失败";
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("video", video);
		map.put("error_code", error_code);
		map.put("msg", msg);
		
		return map;
    }
    
    //上传视频缩略图
    @POST
    @Path("/uploadVideoThumb")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> uploadVideoThumb(
			@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition contentDispositionHeader,
			@FormDataParam("videoId") String videoId,
			@FormDataParam("videoTypeName") String videoTypeName,
			@FormDataParam("homeId") String homeId
			) 
					throws IOException 
    {
    		UUID uuid = UUID.randomUUID();  
        String fileName = uuid.toString() + ".jpg";
		//resource为自己创建的目录，
        String url = "/resource/" + homeId + "/videoType/" + videoTypeName + "/" + fileName;
        //将图片写入到本地
        writeFile(fileInputStream, url);
        
        try {
        		url = fileService.updateThumbUrl(new Integer(videoId), url);
		} catch (Exception e) {
			url = null;
			e.printStackTrace();
		}
        
    		int error_code = 1;
		String msg = "上传视频缩略图成功";
		if (url == null) {
			error_code = 0; //失败
			msg = "上传视频缩略图失败";
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("thumbUrl", url);
		map.put("error_code", error_code);
		map.put("msg", msg);
		
		return map;
    }
    
    public void writeFile(InputStream fileInputStream,String url) throws IOException
    {
    		String rootPath = this.servletContext.getRealPath(url);
		File file = new File(rootPath); 
		File parent = file.getParentFile(); 
		//判断目录是否存在，不在创建 
		if(!parent.exists()){ 
			parent.mkdirs(); 
		} 
		file.createNewFile(); 
		
		OutputStream outpuStream = new FileOutputStream(file);
		int read = 0;
		byte[] bytes = new byte[1024];
 
		while ((read = fileInputStream.read(bytes)) != -1) {
			outpuStream.write(bytes, 0, read);
		}

		outpuStream.flush();
		outpuStream.close();

		fileInputStream.close();
    }
    
    @POST
	@Path("/getHomeVideoList")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public Map<String, Object> getHomeVideoList(HomeVideoType videoType)
	{
		List<HomeVideos> videoList = null;
		try {
			 videoList = fileService.getAllHomeVideosFromVideoType(videoType);
		} catch (Exception e) {
			videoList = null;
			e.printStackTrace();
		}
		
		int error_code = 1;
		String msg = "获取家庭视频成功";
		if (videoList == null) {
			error_code = 0; //失败
			msg = "获取家庭视频失败";
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("videoList", videoList);
		map.put("error_code", error_code);
		map.put("msg", msg);
		return map;
	}
    
   /*******************************feed相关**************************************/
    
  //上传feed图片
    @POST
    @Path("/uploadFeedPhoto")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> uploadFeedPhoto(
			@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition contentDispositionHeader,
			@FormDataParam("feedId") String feedId,
			@FormDataParam("width") String width,
			@FormDataParam("height") String height
			) 
					throws IOException 
    {
    		UUID uuid = UUID.randomUUID();  
        String fileName = uuid.toString() + ".jpg";
		//resource为自己创建的目录，
        String url = "/resource/" + feedId + "/feedPhoto/" + fileName;
        //将图片写入到本地
        LOG.info("写入文件前");
        writeFile(fileInputStream, url);
        
        FeedPhotos feedPhoto = new FeedPhotos();
        feedPhoto.setPhoto_url(url);
        feedPhoto.setWidth(new Double(width));
        feedPhoto.setHeight(new Double(height));
        
        Feeds feed = new Feeds();
        feed.setFeed_id(new Integer(feedId));
        feedPhoto.setFeed(feed);
        
        try {
        		feedPhoto = feedService.insertAFeedPhoto(feedPhoto);
        		LOG.info("写入数据库");
		} catch (Exception e) {
			feedPhoto = null;
			e.printStackTrace();
		}
        
    		int error_code = 1;
		String msg = "上传feed图片成功";
		if (feedPhoto == null) {
			error_code = 0; //失败
			msg = "上传feed图片失败";
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("feedPhoto", feedPhoto);
		map.put("error_code", error_code);
		map.put("msg", msg);
		
		return map;
    }
    
    //上传feed视频缩略图
    @POST
    @Path("/uploadFeedVideoThumb")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> uploadFeedVideoThumb(
			@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition contentDispositionHeader,
			@FormDataParam("feedId") String feedId
			) 
					throws IOException 
    {
    		UUID uuid = UUID.randomUUID();  
        String fileName = uuid.toString() + ".jpg";
		//resource为自己创建的目录，
        String url = "/resource/" + feedId + "/feedVideo/" + fileName;
        //将图片写入到本地
        writeFile(fileInputStream, url);
        
        try {
        		url = feedService.updateFeedVideoThumb(new Integer(feedId), url);
		} catch (Exception e) {
			url = null;
			e.printStackTrace();
		}
        
    		int error_code = 1;
		String msg = "上传feed视频缩略图成功";
		if (url == null) {
			error_code = 0; //失败
			msg = "上传feed视频缩略图失败";
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("thumbUrl", url);
		map.put("error_code", error_code);
		map.put("msg", msg);
		
		return map;
    }
    
    //上传feed视频
    @POST
    @Path("/uploadFeedVideo")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> uploadFeedVideo(
			@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition contentDispositionHeader,
			@FormDataParam("feedId") String feedId
			) 
					throws IOException 
    {
    		UUID uuid = UUID.randomUUID();  
        String fileName = uuid.toString() + ".MOV";
		//resource为自己创建的目录，
        String videoUrl = "/resource/" + feedId + "/feedVideo/" + fileName;
        //将图片写入到本地
        writeFile(fileInputStream, videoUrl);
        
        try {
        	videoUrl = feedService.updateFeedVideo(new Integer(feedId), videoUrl);
		} catch (Exception e) {
			videoUrl = null;
			e.printStackTrace();
		}
        
    		int error_code = 1;
		String msg = "上传feed视频成功";
		if (videoUrl == null) {
			error_code = 0; //失败
			msg = "上传feed视频失败";
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("feedVideoUrl", videoUrl);
		map.put("error_code", error_code);
		map.put("msg", msg);
		
		return map;
    }
    
    /******************************************用户相关*************************************/
  //上传用户头像
    @POST
    @Path("/uploadUserIcon")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> uploadUserIcon(
			@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition contentDispositionHeader,
			@FormDataParam("userProfileId") String userProfileId,
			@FormDataParam("iconUrl") String iconUrl
			) 
					throws IOException 
    {
    		if (iconUrl != null) {
    			if (iconUrl.equals("") == false) {
    				String rootPath = this.servletContext.getRealPath(iconUrl);
    				File file = new File(rootPath);
    				if (file.exists()) {
    					file.delete();
    				}
    			}
    		}
    		UUID uuid = UUID.randomUUID();  
        String fileName = uuid.toString() + ".jpg";
		//resource为自己创建的目录，
        String url = "/resource/user/" + userProfileId + "/avatar/" + fileName;
        //将图片写入到本地
        writeFile(fileInputStream, url);
        
        try {
        		url = userService.updateUserIconUrl(new Integer(userProfileId), url);
		} catch (Exception e) {
			url = null;
			e.printStackTrace();
		}
        
    		int error_code = 1;
		String msg = "上传用户头像成功";
		if (url == null) {
			error_code = 0; //失败
			msg = "上传用户头像失败";
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("iconUrl", url);
		map.put("error_code", error_code);
		map.put("msg", msg);
		
		return map;
    }

}
