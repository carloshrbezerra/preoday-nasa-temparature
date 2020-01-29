package com.preodaynasatemparature.service;

import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.preodaynasatemparature.NasaServiceConfig;

@Service
public class NasaService {

	@Autowired
	RestTemplate restTemplateNasaService;

	@Autowired
	NasaServiceConfig nasaServiceConfig;

	private static final String API_KEY_PARAM = "/?api_key=";
	private static final String ATMOSFERIC_TEMPERATURE = "AT";
	private static final String CHAVE_TEMPERATURA = "ct";

	private Integer CHAVE_MAIS_ATUAL = 0;
	private Integer TOTAL_DISPONIVEL = 0;
	private Double TOTAL_TEMPERATURA = 0d;

	
	public Double temparatureSol(String planet) throws Exception {

		String param = planet != null ? "&feedtype=json&ver=1.0&key=SOL" : "&feedtype=json&ver=1.0";

		URI uri = URI.create(nasaServiceConfig.getUrl() + nasaServiceConfig.getApi() + API_KEY_PARAM
				+ nasaServiceConfig.getApiKey() + param);

		ResponseEntity<Object> response = null;
		try {
			response = restTemplateNasaService.getForEntity(uri, Object.class);
			return retornarTemperatura(planet, response.getBody());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private Double retornarTemperatura(String planet, Object response) {
		// Caso passe parâmetor deo planet seja SOL pega a temperatura da previsão mais
		// atual
		if (planet != null && planet.equalsIgnoreCase("SOL")) {

			ObjectMapper oMapper = new ObjectMapper();

			Map<String, Object> mapTop = oMapper.convertValue(response, Map.class);

			mapTop.entrySet().forEach(entry -> {
				if (isNumeric(entry.getKey())) {
					Integer chave = Integer.parseInt(entry.getKey());
					if (chave > CHAVE_MAIS_ATUAL) {
						CHAVE_MAIS_ATUAL = chave;
					}
				}
			});

			Map<String, Object> mapAT = oMapper.convertValue(mapTop.get(CHAVE_MAIS_ATUAL.toString()), Map.class);
			Map<String, Integer> mapTemperatura = oMapper.convertValue(mapAT.get(ATMOSFERIC_TEMPERATURE), Map.class);

			return Double.parseDouble(mapTemperatura.get(CHAVE_TEMPERATURA).toString());
		}
		// Caso contrário faz a media de todas as temperatura
		else {
			ObjectMapper oMapper = new ObjectMapper();
			Map<String, Object> mapTop = oMapper.convertValue(response, Map.class);

			mapTop.entrySet().forEach(entry -> {
				if (isNumeric(entry.getKey())) {
					TOTAL_DISPONIVEL++;
					Map<String, Object> mapAT = oMapper.convertValue(mapTop.get(entry.getKey()), Map.class);
					Map<String, Integer> mapTemperatura = oMapper.convertValue(mapAT.get(ATMOSFERIC_TEMPERATURE),
							Map.class);
					TOTAL_TEMPERATURA += mapTemperatura.get(CHAVE_TEMPERATURA);
				}
			});
			return TOTAL_TEMPERATURA / TOTAL_DISPONIVEL;
		}
	}

	public static boolean isNumeric(final String str) {
		if (str == null || str.length() == 0) {
			return false;
		}
		return str.chars().allMatch(Character::isDigit);
	}
}
