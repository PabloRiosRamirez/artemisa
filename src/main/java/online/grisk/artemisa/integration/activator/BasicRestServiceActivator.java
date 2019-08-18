package online.grisk.artemisa.integration.activator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import online.grisk.artemisa.domain.pojo.Microservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

public class BasicRestServiceActivator {


    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private RestTemplate restTemplate;

    protected HttpEntity<Object> buildHttpEntity(Map<String, Object> payload, Map<String, Object> headers, Microservice microservice) {
        HttpHeaders httpHeaders = createHttpHeaders(headers, microservice);
        return new HttpEntity<>(payload, httpHeaders);
    }

    protected HttpHeaders createHttpHeaders(Map<String, Object> mapHeaders, Microservice microservice) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        mapHeaders.forEach((k, v) -> {
            if (v instanceof String) {
                httpHeaders.add(k.toLowerCase(), v.toString());
            }
        });
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
    protected ResponseEntity<Map<String, Object>> consumerRestServiceActivator(@NotBlank String path, @NotNull HttpMethod method, @NotNull @Payload Map<String, Object> payload, @NotNull @Headers Map<String, Object> headers, @NotNull Microservice microserviceArtemisa) throws Exception {
        HttpEntity<Object> httpEntity = this.buildHttpEntity(payload, headers, microserviceArtemisa);
        return this.executeRequest(path, method, microserviceArtemisa, httpEntity);
    }
    protected ResponseEntity<Map<String, Object>> executeRequest(String path, HttpMethod method, Microservice microservice, HttpEntity<Object> httpEntity) throws Exception {
        ResponseEntity response;
        try {
            response = this.restTemplate.exchange("http://" + microservice.getServiceId() + path, method, httpEntity, Map.class);
        } catch (RestClientResponseException e) {
            throw new Exception(this.buildErrorMessage(microservice.getServiceId(), e));
        } catch (IllegalStateException e) {
            throw new IllegalStateException("No instances available for " + microservice.getServiceId());
        } catch (Exception e) {
            throw new Exception();
        }
        return response;
    }
}
