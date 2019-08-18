package com.maestro.alala.weather.quickstart_maestro;

import java.io.InputStream;

import org.apache.log4j.PropertyConfigurator;
import com.maestro.alala.weather.model.Weather;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//Configure log4j
		PropertyConfigurator.configure( Main.class.getClassLoader().getResource("log4j.properties"));
		
		// Read the Zip Code from the Command-line ( if none supplied, use 60202)
		String zipcode = "60202";
		
		try {
			zipcode = args[0];
		} catch (Exception e){
			
		}
		
		new Main (zipcode).start();

	}
	
	private String zipcode;
	
	public Main( String zipcode ){
		this.zipcode = zipcode;
	}
	
	public void start() throws Exception {
		// Retrieve Data
		//InputStream dataIn = new YahooRetriever().retrieve( zip );
		
		
		InputStream nyData = 
				getClass().getClassLoader().getResourceAsStream("ny-weather.xml");
		
		// Parse Data
		//Weather weather = new YahooParser().parse( dataIn );
		
		Weather weather = new YahooParser().parse( /*this.zipcode, */ nyData );
		
		// Format (Print) Data
		System.out.print( new WeatherFormatter().format( weather ));
	}

}
