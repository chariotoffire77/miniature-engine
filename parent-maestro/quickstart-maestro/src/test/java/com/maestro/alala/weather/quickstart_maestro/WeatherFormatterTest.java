package com.maestro.alala.weather.quickstart_maestro;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import junit.framework.TestCase;

import com.maestro.alala.weather.model.Weather;

public class WeatherFormatterTest extends TestCase {

	public WeatherFormatterTest(String name) {
		super(name);
	}
	
	public void testFormat() throws Exception {
		InputStream nyData = 
			getClass().getClassLoader().getResourceAsStream("ny-weather.xml");
		Weather weather = new YahooParser().parse( "60202", nyData );
		String formattedResult = new WeatherFormatter().format( weather );
		InputStream expected = 
			getClass().getClassLoader().getResourceAsStream("format-expected.dat");
		//assertEquals( IOUtils.toString( expected ), formattedResult );
	}
}
