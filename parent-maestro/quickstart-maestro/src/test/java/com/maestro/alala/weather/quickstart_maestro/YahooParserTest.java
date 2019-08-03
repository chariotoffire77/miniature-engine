package com.maestro.alala.weather.quickstart_maestro;

import java.io.InputStream;

import junit.framework.TestCase;

import com.maestro.alala.weather.model.Weather;

public class YahooParserTest extends TestCase {

	public YahooParserTest(String name) {
		super(name);
	}
	
	public void testParser() throws Exception {
		InputStream nyData = 
			getClass().getClassLoader().getResourceAsStream("ny-weather.xml");
		Weather weather = new YahooParser().parse( "60202", nyData );
		//assertEquals( "New York", weather.getCity() );
		//assertEquals( "NY", weather.getRegion() );
		//assertEquals( "US", weather.getCountry() );
		//assertEquals( "39", weather.getTemp() );
		
		//assertEquals( "Fair", weather.getCondition() );
		
		//assertEquals( "39", weather.getChill() );
		//assertEquals( "67", weather.getHumidity() );
	}
}
