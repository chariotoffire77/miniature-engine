package com.maestro.alala.quickstart_maestro.weather;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

public class YahooRetriever {
	
	private static Logger log = Logger.getLogger(YahooRetriever.class);
	
	public InputStream retrieve2( /*int*/ String zipcode ) throws Exception {
		log.info( "Retrieving Weather Data");
		String url = "http://weather.yahooapis.com/forecastrss?p="+zipcode;
		URLConnection conn = new URL(url).openConnection();
		return conn.getInputStream();
	}
	
	public InputStream retrieve( /*int*/ String zipcode ) throws Exception {
		log.info( "Retrieving Weather Data");
		
		//the weather site is no longer functional; hence this replacement
		
		InputStream nyData = getClass().getClassLoader().getResourceAsStream("ny-weather.xml");
		
		return nyData;
	}
	

}
