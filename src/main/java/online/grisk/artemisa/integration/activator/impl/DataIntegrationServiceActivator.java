package online.grisk.artemisa.integration.activator.impl;

import online.grisk.artemisa.domain.entity.ServiceActivator;
import online.grisk.artemisa.domain.service.DataIntegrationService;
import online.grisk.artemisa.integration.activator.BasicRestServiceActivator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataIntegrationServiceActivator extends BasicRestServiceActivator {

    @Autowired
    ServiceActivator serviceActivatorAtenea;

    @Autowired
    DataIntegrationService dataIntegrationService;

    public Object invokeRegisterDataIntegrationExcel(@NotNull Map<String, Object> payload) {
        ResponseEntity<Map<String, Object>> response = dataIntegrationService.registerDataIntegrationExcel((Map<String, Object>) payload.get("request"));
        this.addServiceResponseToResponseMap(payload, response, serviceActivatorAtenea.getServiceId());
        return payload;
    }
    public Object invokeRegisterDataIntegrationBureau() {
        HashMap<String, Object> payload = new HashMap<>();
        ResponseEntity<Map<String, Object>> response = dataIntegrationService.registerDataIntegrationExcel((Map<String, Object>) payload.get("request"));
        this.addServiceResponseToResponseMap(payload, response, serviceActivatorAtenea.getServiceId());
        return payload;
    }


}
