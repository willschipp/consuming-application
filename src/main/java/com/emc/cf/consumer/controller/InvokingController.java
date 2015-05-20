package com.emc.cf.consumer.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/invoke")
public class InvokingController implements EnvironmentAware {
	
	private static final Log logger = LogFactory.getLog(InvokingController.class);

	@Value("${service.endpoint}")
	private String endpoint;
	
	private String apiKey;
	
	private RestTemplate restTemplate;
	
	@RequestMapping(method=RequestMethod.GET)
	public String invoke() {
		//use rest to invoke the other service
		restTemplate = new RestTemplate();
		//build the request
		HttpHeaders headers = new HttpHeaders();
		headers.set("api-key", apiKey);
		HttpEntity<?> request = new HttpEntity<>(headers);
		//execute
		ResponseEntity<String> response = restTemplate.exchange(endpoint, HttpMethod.GET, request, String.class);
		//return
		return response.getBody();
		
	}

	@Override
	public void setEnvironment(Environment environment) {
		logger.info(environment);
		
		this.apiKey = environment.getProperty("vcap.application");
	}
	
}
