package com.maestro.alala.weather.web;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.maestro.alala.weather.model.Weather;
import com.maestro.alala.weather.persist.WeatherDAO;
import com.maestro.alala.weather.quickstart_maestro.WeatherService;


public class WeatherController implements Controller {

	private WeatherService weatherService;
	private WeatherDAO weatherDAO;

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// String zip = request.getParameter("zip");
		// Weather weather = weatherService.retrieveForecast(zip);
		
		String location = request.getParameter("location");
		String woeid = request.getParameter("woeid");
		String latAndLong = request.getParameter("latAndLong");
				
		//Weather weather = weatherService.retrieveForecast(zip);
		
		Weather weather = weatherService.retrieveForecast(location, woeid, latAndLong);
		weatherDAO.save(weather);
		return new ModelAndView("weather", "weather", weather);
	}

	public WeatherService getWeatherService() {
		return weatherService;
	}

	public void setWeatherService(WeatherService weatherService) {
		this.weatherService = weatherService;
	}

	public WeatherDAO getWeatherDAO() {
		return weatherDAO;
	}

	public void setWeatherDAO(WeatherDAO weatherDAO) {
		this.weatherDAO = weatherDAO;
	}
}