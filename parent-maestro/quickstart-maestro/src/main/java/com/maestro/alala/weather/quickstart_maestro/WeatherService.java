package com.maestro.alala.weather.quickstart_maestro;

import java.io.InputStream;

import com.maestro.alala.weather.model.Weather;

public class WeatherService {
	
	private YahooRetriever yahooRetriever;
	private YahooParser yahooParser;
	
	public WeatherService(){}
	
	public Weather retrieveForecast(String location, String woeid, String latAndLong) throws Exception {
		
		if ( location == null && woeid == null && latAndLong == null ) {
			throw new Exception ("Provide either a value for location, woeid, or latitude/longitude.");
		}
		
		// Retrieve Data
		InputStream dataIn = new YahooRetriever().retrieve(location, woeid, latAndLong);
		
		// Parse Data
		Weather weather = new YahooParser().parse(dataIn);
		
		// Format (Print) Data
		// return new WeatherFormatter().format( weather );
		
		return weather;
		          
	}
	
	public YahooParser getYahooParser(){
		return yahooParser;
	}
	
	public YahooRetriever getYahooRetriever(){
		return yahooRetriever;
	}
	
	
	public void  setYahooParser( YahooParser yahooParser){
		this.yahooParser = yahooParser;
	}
	
	public void  setYahooRetriever( YahooRetriever yahooRetriever){
		this.yahooRetriever = yahooRetriever;
	}
	

}
