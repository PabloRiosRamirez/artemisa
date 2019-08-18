package online.grisk.artemisa.integration.activator.impl;

import online.grisk.artemisa.domain.entity.Dataintegration;
import online.grisk.artemisa.domain.pojo.Microservice;
import online.grisk.artemisa.domain.service.DataintegrationService;
import online.grisk.artemisa.integration.activator.BasicRestServiceActivator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Component
public class DataIntegrationServiceActivator extends BasicRestServiceActivator {

    @Autowired
    Microservice microserviceAtenea;

    @Autowired
    DataintegrationService dataIntegrationService;

    public Map<String, Object> invokeRegisterDataIntegrationExcel(@NotNull Map<String, Object> payload) {
        ResponseEntity<Map<String, Object>> response = dataIntegrationService.registerDataIntegrationExcel(payload);
        this.addServiceResponseToResponseMap(payload, response, microserviceAtenea.getServiceId());
        return payload;
    }

    public Dataintegration invokeUpdateDataIntegrationExcel(long idDataintegration, @NotNull MultipartFile file) {
        return dataIntegrationService.updateDataIntegrationExcel(idDataintegration, file);
    }

    public Map<String, Object> invokeRegisterDataIntegrationBureau(@NotNull Map<String, Object> payload) {
        ResponseEntity<Map<String, Object>> response = dataIntegrationService.registerDataIntegrationBureau(payload);
        this.addServiceResponseToResponseMap(payload, response, microserviceAtenea.getServiceId());
        return payload;
    }

}
