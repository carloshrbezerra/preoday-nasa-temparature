package com.preodaynasatemparature;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class NasaServiceConfig {

	private String url;
	private String api;
	private String apiKey;
	private static final int TIMEOUT = 1000;

	@Bean(name = "restTemplateNasaService")
	public RestTemplate prepareRestTemplateForCapacitaForService() {

		RequestConfig.Builder requestBuilder = RequestConfig.custom();
		requestBuilder = requestBuilder.setConnectTimeout(TIMEOUT);

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		httpClientBuilder.setDefaultRequestConfig(requestBuilder.build());
		CloseableHttpClient httpClient = httpClientBuilder.build();

		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

		return new RestTemplate(requestFactory);
	}

	@Value("${nasa.url}")
	public void setUrl(String url) {
		this.url = url;
	}

	@Value("${nasa.api}")
	public void setApi(String api) {
		this.api = api;
	}
	

	public String getApiKey() {
		return apiKey;
	}

	@Value("${nasa.apikey}")
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getUrl() {
		return url;
	}

	public String getApi() {
		return api;
	}
}
