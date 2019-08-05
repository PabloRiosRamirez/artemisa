package online.grisk.artemisa.integration.activator.impl;

import online.grisk.artemisa.domain.entity.DataIntegration;
import online.grisk.artemisa.domain.entity.Microservice;
import online.grisk.artemisa.domain.service.DataIntegrationService;
import online.grisk.artemisa.integration.activator.BasicRestServiceActivator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Component
public class DataIntegrationServiceActivator extends BasicRestServiceActivator {

    @Autowired
    Microservice microserviceAtenea;

    @Autowired
    DataIntegrationService dataIntegrationService;

    public Map<String, Object> invokeRegisterDataIntegrationExcel(@NotNull Map<String, Object> payload) {
        ResponseEntity<Map<String, Object>> response = dataIntegrationService.registerDataIntegrationExcel(payload);
        this.addServiceResponseToResponseMap(payload, response, microserviceAtenea.getServiceId());
        return payload;
    }

    public DataIntegration invokeUpdateDataIntegrationExcel(long idDataintegration, @NotNull MultipartFile file) {
        return dataIntegrationService.updateDataIntegrationExcel(idDataintegration, file);
    }

    public Map<String, Object> invokeRegisterDataIntegrationBureau(@NotNull Map<String, Object> payload) {
        ResponseEntity<Map<String, Object>> response = dataIntegrationService.registerDataIntegrationBureau(payload);
        this.addServiceResponseToResponseMap(payload, response, microserviceAtenea.getServiceId());
        return payload;
    }

}
