package online.grisk.artemisa.integration.activator.impl;

import com.fasterxml.jackson.databind.JsonNode;
import online.grisk.artemisa.domain.entity.ServiceActivator;
import online.grisk.artemisa.domain.service.BasicRestServiceActivator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.UUID;

@Component
public class HermesServiceActivator extends BasicRestServiceActivator {

    @Autowired
    UUID uuid;

    @Autowired
    ServiceActivator serviceActivatorHermes;

    public Map<String, Object> invoke(@NotNull @Payload Map<String, Object> payload, @NotNull @Headers Map<String, Object> headers) throws Exception {
        HttpEntity<Object> httpEntity = this.buildHttpEntity((Map<String, Object>) payload.get("request"), headers, serviceActivatorHermes);
        ResponseEntity<JsonNode> response = this.executeRequest(serviceActivatorHermes, httpEntity);
        this.addServiceResponseToResponseMap(payload, response, serviceActivatorHermes.getServiceId());
        return payload;
    }
}