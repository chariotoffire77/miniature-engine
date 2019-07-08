package com.maestro.alala.quickstart_maestro.weather;

import java.io.InputStream;

public class WeatherService {
	
	public WeatherService(){}
	
	public String retrieveForecast(String zipcode ) throws Exception {
		
		// Retrieve Data
		InputStream dataIn = new YahooRetriever().retrieve(zipcode);
		
		// Parse Data
		Weather weather = new YahooParser().parse(dataIn);
		
		// Format (Print) Data
		return new WeatherFormatter().format( weather );
		          
	}

}
