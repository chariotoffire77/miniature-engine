package com.maestro.alala.weather.quickstart_maestro;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;

/**
 * Hello world!
 *
 */
public class AppTwitter 
{
    public static void main( String[] args )
    {
    	RestClient client = new RestClient();
    	
    	// create the resource instance to interact with 
    	Resource resource = client.resource("http://search.twitter.com/search.json");
    	
    	//https://dev.twitter.com/docs/api/1.1/overview
    	//Resource resource = client.resource("dev.twitter.com/docs/api/1.1/overview.json");
    	
    	//Resource resource = client.resource("dev.twitter.com/overview.json");
    	// issue the request
    	ClientResponse response = 
    			resource.queryParam("q", new Object[]{"maven"}).get();
    	
    	System.out.println(response.getEntity(String.class));
        System.out.println( "Hello World!" );
    }
}
