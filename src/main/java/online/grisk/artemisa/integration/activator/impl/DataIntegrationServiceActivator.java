package online.grisk.artemisa.integration.activator.impl;

import online.grisk.artemisa.domain.entity.ServiceActivator;
import online.grisk.artemisa.domain.service.DataIntegrationService;
import online.grisk.artemisa.integration.activator.BasicRestServiceActivator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class DataIntegrationServiceActivator extends BasicRestServiceActivator {

    @Autowired
    ServiceActivator serviceActivatorAtenea;

    @Autowired
    DataIntegrationService dataIntegrationService;

    public Map<String, Object> invokeRegisterDataIntegrationExcel(@NotNull Map<String, Object> payload) {
        ResponseEntity<Map<String, Object>> response = dataIntegrationService.registerDataIntegrationExcel(payload);
        this.addServiceResponseToResponseMap(payload, response, serviceActivatorAtenea.getServiceId());
        return payload;
    }

    public Map<String, Object> invokeRegisterDataIntegrationBureau(@NotNull Map<String, Object> payload) {
        ResponseEntity<Map<String, Object>> response = dataIntegrationService.registerDataIntegrationBureau((Map<String, Object>) payload.get("request"));
        this.addServiceResponseToResponseMap(payload, response, serviceActivatorAtenea.getServiceId());
        return payload;
    }

    public Map<String, Object> invokeReportDataIntegration(@Payload Map<String, Object> payload, @Headers Map<String, Object> header) {
        Map<String, Object> attribute = new HashMap<>();
        for (int i = 0; i < new Random().nextInt(35) + 20; i++) {
            attribute.put("variable_" + i, new Random().nextInt(35000) + 1 + "");
        }
        payload.put("dataIntegration", attribute);
        return payload;
    }

}
