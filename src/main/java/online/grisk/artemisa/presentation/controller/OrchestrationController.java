package online.grisk.artemisa.presentation.controller;

import online.grisk.artemisa.domain.service.DataIntegrationService;
import online.grisk.artemisa.integration.activator.impl.OrquestrationServiceActivator;
import online.grisk.artemisa.integration.gateway.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping({"/api/artemisa"})
public class OrchestrationController {

    @Autowired
    GatewayService gateway;

    @Autowired
    OrquestrationServiceActivator orquestrationServiceActivator;

    @Autowired
    DataIntegrationService dataIntegrationService;

    @PostMapping("/analysis/{idOrganization}/excel")
    public Map<String, Object> initAnalysisExcel(@PathVariable("idOrganization") long idOrganization, @RequestParam("file") MultipartFile file) {
        Map<String, Object> payload = orquestrationServiceActivator.getConfigurations(new HashMap(), idOrganization);
        payload = orquestrationServiceActivator.invokeExtractExcel(payload, file);
        Message build = MessageBuilder.withPayload(payload).build();
        Map<String, Object> response = gateway.process(build);
        return response;
//        return payload;
    }

    @PostMapping("/analysis/{idOrganization}/bureau")
    public Map<String, Object> initAnalysisBureau(@PathVariable("idOrganization") long idOrganization, @NotEmpty @RequestBody Map<String, Object> request) throws Exception {
        Map<String, Object> payload = orquestrationServiceActivator.getConfigurations(request, idOrganization);
        payload =  orquestrationServiceActivator.invokeExtractBureau(payload);
        Message build = MessageBuilder.withPayload(payload).build();
        Map<String, Object> response = gateway.process(build);
        return response;
//        return payload;
    }

    private void verifyParameters(Map<String, Object> payload) {
        Assert.notEmpty(payload, "Payload required");
    }
}
