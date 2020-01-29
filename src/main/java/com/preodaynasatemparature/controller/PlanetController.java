package com.preodaynasatemparature.controller;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.preodaynasatemparature.domain.Temperature;
import com.preodaynasatemparature.service.PlanetService;

@RestController
@RequestMapping("/nasa")
public class PlanetController {

	@Autowired
	public PlanetService planetService;

	@GetMapping("/temperature")
	public Temperature listarMotivo(@PathParam("sol") String planet) {
		try {
			return new Temperature(planetService.temperature(planet));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
