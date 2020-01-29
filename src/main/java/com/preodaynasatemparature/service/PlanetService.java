package com.preodaynasatemparature.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanetService {
	
	@Autowired
	public NasaService nasaService;
	
	public Double temperature(String planet) throws Exception {
		return nasaService.temparatureSol(planet);
	}
}
