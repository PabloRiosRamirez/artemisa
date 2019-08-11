package online.grisk.artemisa.integration.activator.impl;

import online.grisk.artemisa.domain.entity.Microservice;
import online.grisk.artemisa.integration.activator.BasicRestServiceActivator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.UUID;

@Component
public class EmailServiceActivator extends BasicRestServiceActivator {

    @Autowired
    UUID uuid;

    @Autowired
    Microservice microserviceHermes;

    public Map<String, Object> invokeSendEmail(@NotNull @Payload Map<String, Object> payload, @NotNull @Headers Map<String, Object> headers) throws Exception {
        HttpEntity<Object> httpEntity = this.buildHttpEntity((Map<String, Object>) payload, headers, microserviceHermes);
        ResponseEntity<Map<String, Object>> response = this.executeRequest(microserviceHermes, httpEntity);
        this.addServiceResponseToResponseMap(payload, response, microserviceHermes.getServiceId());
        return payload;
    }
}
