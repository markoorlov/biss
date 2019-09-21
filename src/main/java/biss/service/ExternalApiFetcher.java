package biss.service;

import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import biss.external.response.Ip;

@Service
public class ExternalApiFetcher {

	private static final String URL = "https://ipapi.co/";

	private final RestTemplate restTemplate = new RestTemplate();
	private HttpEntity<String> entity = new HttpEntity<String>("parameters", getHeaders());

	public ExternalApiFetcher() {}

	public Ip getIp(String ip) {
		
		String ipUrl = URL + ip + "/json";
		
		ResponseEntity<Ip> response = restTemplate.exchange(ipUrl, HttpMethod.GET, entity,
				new ParameterizedTypeReference<Ip>() {
				});

		if (response.getStatusCode() != HttpStatus.OK) {
			LoggerFactory.getLogger(ExternalApiFetcher.class).error("There is a problem with external API.");
		}
		return response.getBody();
	}

	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		return headers;
	}
}
