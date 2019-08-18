package com.maestro.alala.weather.quickstart_maestro;
// org.sonatype.mavenbook.weather;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import org.apache.log4j.Logger;


//Copyright 2019 Oath Inc. Licensed under the terms of the zLib license see https://opensource.org/licenses/Zlib for terms.
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import java.util.List;
import java.util.Properties;
import java.util.ArrayList;
import java.util.Date;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Random;
import java.util.Collections;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.Charset;
import java.net.URI;


public class YahooRetriever {

	private static Logger log = Logger.getLogger(YahooRetriever.class);
	


	public InputStream retrieve(String location, String woeid, String latAndLong) throws Exception {
		log.info( "Retrieving Weather Data" );
		// String url = "http://weather.yahooapis.com/forecastrss?p=" + zipcode;
		//String url = "https://weather-ydn-yql.media.yahoo.com/forecastrss?p=" + zipcode;
		//URLConnection conn = new URL(url).openConnection();
		String formatOption="xml";
		//String location="sunnyvale,ca";
		//String woeid=null;
		//String latAndLong=null;
		
		String response=formulateSignature(formatOption, location, woeid, latAndLong );
		
		InputStream connResponseAsStream = new ByteArrayInputStream(response.getBytes(Charset.forName("UTF-8")));
		//return conn.getInputStream();
		return connResponseAsStream;
	}
	
	private final String formulateSignature(String formatOption, String location, String woeid, String latAndLong )
			throws Exception {

		
		StringBuffer appIdBuf = new StringBuffer();
		StringBuffer consumerKeyBuf = new StringBuffer();
		StringBuffer consumerSecretBuf = new StringBuffer();
		
		getYahooWeatherCredentials( appIdBuf, consumerKeyBuf, consumerSecretBuf);
		
		String appId = appIdBuf.toString();
		String consumerKey = consumerKeyBuf.toString();
		String consumerSecret = consumerSecretBuf.toString();
        
        final String url = "https://weather-ydn-yql.media.yahoo.com/forecastrss";

        long timestamp = new Date().getTime() / 1000;
        byte[] nonce = new byte[32];
        Random rand = new Random();
        rand.nextBytes(nonce);
        String oauthNonce = new String(nonce).replaceAll("\\W", "");
        
        List<String> parameters = new ArrayList<>();
        parameters.add("oauth_consumer_key=" + consumerKey);
        parameters.add("oauth_nonce=" + oauthNonce);
        parameters.add("oauth_signature_method=HMAC-SHA1");
        parameters.add("oauth_timestamp=" + timestamp);
        parameters.add("oauth_version=1.0");
        
        //
        //parameters.add("format=json");
        // Add variables parameters
        StringBuffer useInUrlHeader = new StringBuffer();    // fill in....
        addParameters( parameters, formatOption, location, woeid, latAndLong, useInUrlHeader);
        //parameters.add(formatText);
        Collections.sort(parameters);
        
        StringBuffer parametersList = new StringBuffer();
        for (int i = 0; i < parameters.size(); i++) {
            parametersList.append(((i > 0) ? "&" : "") + parameters.get(i));
        }

        String signatureString = "GET&" +
            URLEncoder.encode(url, "UTF-8") + "&" +
            URLEncoder.encode(parametersList.toString(), "UTF-8");

        String signature = null;
        try {
            SecretKeySpec signingKey = new SecretKeySpec((consumerSecret + "&").getBytes(), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            byte[] rawHMAC = mac.doFinal(signatureString.getBytes());
            Encoder encoder = Base64.getEncoder();
            signature = encoder.encodeToString(rawHMAC);
        } catch (Exception e) {
            System.err.println("Unable to append signature");
            System.exit(0);
        }

        String authorizationLine = "OAuth " +
            "oauth_consumer_key=\"" + consumerKey + "\", " +
            "oauth_nonce=\"" + oauthNonce + "\", " +
            "oauth_timestamp=\"" + timestamp + "\", " +
            "oauth_signature_method=\"HMAC-SHA1\", " +
            "oauth_signature=\"" + signature + "\", " +
            "oauth_version=\"1.0\"";

        HttpClient client = HttpClient.newHttpClient();
        
        /*
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url + "?location=sunnyvale,ca&format=xml"))
            .header("Authorization", authorizationLine)
            .header("X-Yahoo-App-Id", appId)
            .header("Content-Type", "application/xml")
            .build();
            
        */
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + useInUrlHeader.toString()))
                .header("Authorization", authorizationLine)
                .header("X-Yahoo-App-Id", appId)
                .header("Content-Type", "application/xml")
                .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        System.out.println(response.body());
		return response.body();
	}
	
	
	private void getYahooWeatherCredentials( StringBuffer appId, StringBuffer consumerKey, StringBuffer consumerSecret) throws IOException {
		
        // This file must not be uploaded to GIT bucket........ 
		InputStream credStream = getClass()
        		 .getClassLoader().getResourceAsStream("yahoo-weather-api-credentials.properties");
         
         Properties credProperties = new Properties();
         credProperties.load(credStream);
         
         String appId1 = credProperties.getProperty("appId");
         String consumerKey1 = credProperties.getProperty("consumerKey");
         String consumerSecret1 = credProperties.getProperty("consumerSecret");
         
         appId.append(appId1);
         consumerKey.append(consumerKey1);
         consumerSecret.append(consumerSecret1);
		
	}

	private boolean addParameters(List<String> parameters, String formatOption, String location, String woeid,
			String latAndLong, StringBuffer useInUrlHeader) {
		if ( parameters == null ) {
			log.error("empty parameter list....");
			return false;
		}
		
		if ( formatOption.equalsIgnoreCase("xml")  || formatOption.equalsIgnoreCase("json")) {
			log.debug("formatOption="+formatOption);
			parameters.add("format="+formatOption);
		}else {
			log.debug("Unknown format.....");
			return false;
		}
		
		if ( location != null) {
			try {
				parameters.add("location="+URLEncoder.encode(location, "UTF-8"));
				
				//"?location=sunnyvale,ca&format=xml"
				useInUrlHeader.append("?location="+location+"&format="+formatOption);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}else if( woeid != null) {
			//parameters.add();
			return true;
		}else if( latAndLong != null) {
			//parameters.add();
			return true;
		}
		
		// TODO Auto-generated method stub
		log.debug("No valid location, woeid, or latitude and Longitude found....");
		return true;
		
	}

	private String retrieveByLocation() {
		return "";
	}
	
	private String retrieveByLatitude() {
		return "";
	}

}



