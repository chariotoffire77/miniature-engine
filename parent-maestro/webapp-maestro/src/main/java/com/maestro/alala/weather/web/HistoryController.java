package com.maestro.alala.weather.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.maestro.alala.weather.model.Location;
import com.maestro.alala.weather.model.Weather;
import com.maestro.alala.weather.persist.LocationDAO;
import com.maestro.alala.weather.persist.WeatherDAO;

public class HistoryController implements Controller {

	private LocationDAO locationDAO;
	private WeatherDAO weatherDAO;

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String city = request.getParameter("city");
		Location location = locationDAO.findByCity(city);
		List<Weather> weathers = weatherDAO.recentForLocation( location );
		
		Map<String,Object> model = new HashMap<String,Object>();
		model.put( "location", location );
		model.put( "weathers", weathers );
		
		return new ModelAndView("history", model);
	}

	public WeatherDAO getWeatherDAO() {
		return weatherDAO;
	}

	public void setWeatherDAO(WeatherDAO weatherDAO) {
		this.weatherDAO = weatherDAO;
	}

	public LocationDAO getLocationDAO() {
		return locationDAO;
	}

	public void setLocationDAO(LocationDAO locationDAO) {
		this.locationDAO = locationDAO;
	}
}