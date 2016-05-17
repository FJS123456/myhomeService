package com.myhome.api;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class ApplicationAPI extends ResourceConfig
{
	public ApplicationAPI()
	{
		//资源类所在的包路径  
	    packages("com.myhome.resource");
	    
	    register(MultiPartFeature.class);
	}
}
