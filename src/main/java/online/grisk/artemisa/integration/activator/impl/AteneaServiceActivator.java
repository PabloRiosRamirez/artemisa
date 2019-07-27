package online.grisk.artemisa.integration.activator.impl;

import com.fasterxml.jackson.databind.JsonNode;
import online.grisk.artemisa.domain.entity.ServiceActivator;
import online.grisk.artemisa.integration.activator.BasicRestServiceActivator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class AteneaServiceActivator extends BasicRestServiceActivator {

    @Autowired
    UUID uuid;

    @Autowired
    ServiceActivator serviceActivatorAtenea;

    public Map<String, Object> invoke(@NotNull @Payload Map<String, Object> payload, @NotNull @Headers Map<String, Object> headers) throws Exception {
        Map configuration = new HashMap();
        payload.put("configuration", configuration);
        HttpEntity<Object> httpEntity = this.buildHttpEntity((Map<String, Object>) payload.get("request"), headers, serviceActivatorAtenea);
        ResponseEntity<JsonNode> response = this.executeRequest(serviceActivatorAtenea, httpEntity);
        this.addServiceResponseToResponseMap(payload, response, serviceActivatorAtenea.getServiceId());
        return payload;
    }
}
