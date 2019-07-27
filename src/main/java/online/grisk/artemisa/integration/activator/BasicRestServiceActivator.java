package online.grisk.artemisa.integration.activator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import online.grisk.artemisa.domain.entity.ServiceActivator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class BasicRestServiceActivator {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    protected HttpEntity<Object> buildHttpEntity(Map<String, Object> payload, Map<String, Object> headers, ServiceActivator serviceActivator) {
        HttpHeaders httpHeaders = createHttpHeaders(headers, serviceActivator);
        return new HttpEntity<>(payload, httpHeaders);
    }

    private HttpHeaders createHttpHeaders(Map<String, Object> mapHeaders, ServiceActivator serviceActivator) {
        HttpHeaders httpHeaders = new HttpHeaders();
        mapHeaders.forEach((k, v) -> {
            if (v instanceof String) {
                httpHeaders.add(k.toLowerCase(), v.toString());
                httpHeaders.setBasicAuth(serviceActivator.getServiceUsername(), serviceActivator.getServicePassword());
            }
        });
        return httpHeaders;
    }

    protected ResponseEntity<JsonNode> executeRequest(ServiceActivator serviceActivator, HttpEntity<Object> httpEntity) throws Exception {
        ResponseEntity response;
        try {
            response = this.restTemplate.exchange(serviceActivator.getUri(), HttpMethod.POST, httpEntity, JsonNode.class);
        } catch (RestClientResponseException e) {
            throw new Exception(this.buildErrorMessage(serviceActivator.getServiceId(), e));
        } catch (IllegalStateException e) {
            throw new IllegalStateException("No instances available for " + serviceActivator.getServiceId());
        } catch (Exception e) {
            throw new Exception();
        }
        return response;
    }


    private String buildErrorMessage(String nameServiceActivator, RestClientResponseException e) throws Exception {
        JsonNode jsonNode = this.objectMapper.readTree(e.getResponseBodyAsString());
        return jsonNode.get("message") != null ? String.format("An error ocurred executing %s service activator: %S (STATUS: %d)", nameServiceActivator, jsonNode.get("message").asText(), e.getRawStatusCode()) : String.format("An error ocurred executing %s service activator: %S (STATUS: %d)", nameServiceActivator, e.getMessage(), e.getRawStatusCode());
    }

    protected void addServiceResponseToResponseMap(Map<String, Object> payload, ResponseEntity<JsonNode> response, String serviceId) {
        payload.put(serviceId.toLowerCase() + "_" + "response", response.getBody());
        payload.put("current_response", response.getBody());
        payload.put("status", response.getStatusCodeValue());
    }
}
