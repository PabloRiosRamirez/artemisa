package online.grisk.artemisa.integration.activator.impl;

import online.grisk.artemisa.domain.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Component
public class OrquestrationServiceActivator {

    @Autowired
    DataIntegrationService dataIntegrationService;

    @Autowired
    RiskScoreService riskScoreService;

    @Autowired
    RiskRatiosService riskRatiosService;
    @Autowired
    BusinessTreeService businessTreeService;

    @Autowired
    OrchestrationService orchestrationService;

    public Map<String, Object> invokeExtractExcel(Map payload, @NotNull MultipartFile file) {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> applicant = new HashMap<>();
        applicant.put("filename", file.getOriginalFilename());
        response.put("applicant", applicant);
        ((Map) payload.get("dataintegration")).put("values", orchestrationService.extractVariables(payload, file));
        response.put("dataintegration", payload.get("dataintegration"));
        response.put("riskScore", payload.get("riskScore"));
        response.put("riskRatios", payload.get("riskRatios"));
        response.put("businessTree", payload.get("businessTree"));
        return response;
    }

    public Map<String, Object> invokeExtractBureau(Map payload) throws Exception {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> applicant = new HashMap<>();
        String rut = orchestrationService.extractRut(payload);
        applicant.put("rut", rut);
        response.put("applicant", applicant);
        response.put("dataintegration", payload.get("dataintegration"));
        return orchestrationService.invokeBureau(response);
    }

    public Map getConfigurations(Map response, Long idOrganization) {
        Map dataintegration = new HashMap();
        dataintegration.put("configuration", dataIntegrationService.findByOrganization(idOrganization));
        response.put("dataintegration", dataintegration);

        Map score = new HashMap();
        score.put("configuration", riskScoreService.findByOrganization(idOrganization));
        response.put("riskScore", score);

        Map ratios = new HashMap();
        ratios.put("configuration", riskRatiosService.findByOrganization(idOrganization));
        response.put("riskRatios", ratios);

        Map businessTree = new HashMap();
        businessTree.put("configuration", businessTreeService.findByOrganization(idOrganization));
        response.put("businessTree", businessTree);

        return response;
    }

}
