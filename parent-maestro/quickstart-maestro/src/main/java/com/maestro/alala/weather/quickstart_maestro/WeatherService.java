package com.maestro.alala.weather.quickstart_maestro;

import java.io.InputStream;

import com.maestro.alala.weather.model.Weather;

public class WeatherService {
	
	private YahooRetriever yahooRetriever;
	private YahooParser yahooParser;
	
	public WeatherService(){}
	
	public Weather retrieveForecast(String zipcode ) throws Exception {
		
		// Retrieve Data
		InputStream dataIn = new YahooRetriever().retrieve(zipcode);
		
		// Parse Data
		Weather weather = new YahooParser().parse(zipcode, dataIn);
		
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
