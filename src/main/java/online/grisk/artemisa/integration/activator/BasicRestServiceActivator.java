package online.grisk.artemisa.integration.activator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import online.grisk.artemisa.domain.entity.Microservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class BasicRestServiceActivator {


    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private RestTemplate restTemplate;

    protected HttpEntity<Object> buildHttpEntity(Map<String, Object> payload, HttpHeaders headers, Microservice microservice) {
        HttpHeaders httpHeaders = createHttpHeaders(headers, microservice);
        return new HttpEntity<>(payload, httpHeaders);
    }

    private HttpHeaders createHttpHeaders(HttpHeaders headers, Microservice microservice) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if (headers.get("action") != null) {
            httpHeaders.add("action", headers.get("action").get(0).toString());
        }
        httpHeaders.setBasicAuth(microservice.getServiceUsername(), microservice.getServicePassword());
        return httpHeaders;
    }

    protected ResponseEntity<Map<String, Object>> executeRequest(Microservice microservice, HttpEntity<Object> httpEntity) throws Exception {
        ResponseEntity<Map<String, Object>> response;
        try {
        	ParameterizedTypeReference<Map<String, Object>> responseType =  new ParameterizedTypeReference<Map<String, Object>>() {};
        	String uri = microservice.getUri();
            response = this.restTemplate.exchange(uri, HttpMethod.POST, httpEntity, responseType);
        } catch (RestClientResponseException e) {
            throw new Exception(this.buildErrorMessage(microservice.getServiceId(), e));
        } catch (IllegalStateException e) {
            throw new IllegalStateException("No instances available for " + microservice.getServiceId());
        } catch (Exception e) {
            throw new Exception();
        }
        return response;
    }


    private String buildErrorMessage(String nameServiceActivator, RestClientResponseException e) throws Exception {
        JsonNode jsonNode = this.objectMapper.readTree(e.getResponseBodyAsString());
        return jsonNode.get("message") != null ? String.format("An error ocurred executing %s service activator: %S (STATUS: %d)", nameServiceActivator, jsonNode.get("message").asText(), e.getRawStatusCode()) : String.format("An error ocurred executing %s service activator: %S (STATUS: %d)", nameServiceActivator, e.getMessage(), e.getRawStatusCode());
    }

    protected void addServiceResponseToResponseMap(Map<String, Object> payload, ResponseEntity<Map<String, Object>> response, String serviceId) {
        payload.put(serviceId.toLowerCase() + "_" + "response", response.getBody());
        payload.put("current_response", response.getBody());
        payload.put("status", response.getStatusCodeValue());
    }
}
