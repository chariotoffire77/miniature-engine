package com.maestro.alala.quickstart_maestro.weather;

import java.io.InputStream;

import org.apache.log4j.PropertyConfigurator;

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
	
	private String zip;
	
	public Main( String zip ){
		this.zip = zip;
	}
	
	public void start() throws Exception {
		// Retrieve Data
		//InputStream dataIn = new YahooRetriever().retrieve( zip );
		
		
		InputStream nyData = 
				getClass().getClassLoader().getResourceAsStream("ny-weather.xml");
		
		// Parse Data
		//Weather weather = new YahooParser().parse( dataIn );
		
		Weather weather = new YahooParser().parse( nyData );
		
		// Format (Print) Data
		System.out.print( new WeatherFormatter().format( weather ));
	}

}
