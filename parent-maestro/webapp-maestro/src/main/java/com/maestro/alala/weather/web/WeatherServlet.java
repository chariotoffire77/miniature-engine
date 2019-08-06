package com.maestro.alala.weather.web;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.maestro.alala.weather.quickstart_maestro.WeatherService;
       

public class WeatherServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet( HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String zip  = request.getParameter("zip");
		WeatherService weatherService = new WeatherService();
		PrintWriter out = response.getWriter();
		
		try {
			out.println(weatherService.retrieveForecast( zip ));
		} catch ( Exception e ){
			out.println("Error Retrieving Forecast: "+ e.getMessage());
		}
		out.println("weatherServlet Executed for zip code: "+ zip);
		out.flush();
		out.close();
		
	}
	

}