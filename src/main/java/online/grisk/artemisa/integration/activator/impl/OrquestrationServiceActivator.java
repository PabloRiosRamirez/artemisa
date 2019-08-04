package online.grisk.artemisa.integration.activator.impl;

import online.grisk.artemisa.domain.service.DataIntegrationService;
import online.grisk.artemisa.domain.service.OrchestrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Component
public class OrquestrationServiceActivator{
    @Autowired
    DataIntegrationService dataIntegrationService;

    @Autowired
    OrchestrationService orchestrationService;

    public Map<String, Object> invokeExtractExcel(long idOrganization, @NotNull MultipartFile file) {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> applicant = new HashMap<>();
        applicant.put("values", orchestrationService.extractVariables(file, idOrganization));
        response.put("applicant", applicant);
        return response;
    }

    public Map<String, Object> invokeExtractBureau(Map payload) {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> applicant = new HashMap<>();
        applicant.put("rut", orchestrationService.extractRut(payload));
        response.put("applicant", applicant);
        return response;
    }

    public Map getConfiguration(Map response, Long idOrganization){
        Map dataintegration = new HashMap();
        dataintegration.put("configuration", dataIntegrationService.findByOrganization(idOrganization));
        response.put("dataintegration", dataintegration);
        return response;
    }

}
