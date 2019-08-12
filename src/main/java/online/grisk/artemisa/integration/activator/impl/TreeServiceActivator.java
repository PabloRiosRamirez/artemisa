package online.grisk.artemisa.integration.activator.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import online.grisk.artemisa.domain.entity.Microservice;
import online.grisk.artemisa.integration.activator.BasicRestServiceActivator;

import java.util.HashMap;
import java.util.Map;

@Component
public class TreeServiceActivator extends BasicRestServiceActivator {
	
	@Autowired
	Microservice microserviceZeus;

    public Map<String, Object> invoke(@Payload Map<String, Object> payload, @Headers Map<String, Object> header) throws Exception {
		ResponseEntity<Map<String, Object>> responseEntity = consumerRestServiceActivator("/api/zeus/businessTree", HttpMethod.POST, payload, new HashMap<>(), microserviceZeus);
		return responseEntity.getBody();
    }
}
