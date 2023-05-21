package com.example.echo.webservice;

import java.net.URI;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class SwapiClient {

	RestTemplate restTemplate = new RestTemplate();
	
	static final String ROOT_URI = "https://swapi.dev/api/planets/";

	public String getPlanetName(String id) {	
		try {
			if (Integer.parseInt(id) > 60 || Integer.parseInt(id) < 1) {
				return "not found";
			} 
		} catch (Exception e) {
			return "wrong input format";
		}	

		URI uri = UriComponentsBuilder.fromUriString(ROOT_URI)
			.path("/{id}")
			.buildAndExpand(id)
			.toUri();
		
		Swapi swapi = restTemplate.getForObject(uri, Swapi.class);

		if (swapi == null) {
			return "not found";
		} else {
			return swapi.getName();
		}
	}

}
