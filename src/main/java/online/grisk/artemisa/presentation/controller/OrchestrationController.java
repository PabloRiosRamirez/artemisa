package online.grisk.artemisa.presentation.controller;

import online.grisk.artemisa.domain.service.DataIntegrationService;
import online.grisk.artemisa.integration.activator.impl.OrquestrationServiceActivator;
import online.grisk.artemisa.integration.gateway.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
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
        Map<String, Object> payload = orquestrationServiceActivator.invokeExtractExcel(idOrganization, file);
        payload = orquestrationServiceActivator.getConfiguration(payload, idOrganization);
        Message build = MessageBuilder.withPayload(payload).build();
        Map<String, Object> response = gateway.process(build);
        return response;
    }

    @PostMapping("/analysis/{idOrganization}/bureau")
    public Map<String, Object> initAnalysisBureau(@PathVariable("idOrganization") long idOrganization, @NotEmpty @RequestBody Map<String, Object> request) {
        Map<String, Object> payload = orquestrationServiceActivator.invokeExtractBureau(request);
        payload =  orquestrationServiceActivator.getConfiguration(payload, idOrganization);
        Message build = MessageBuilder.withPayload(payload).build();
        Map<String, Object> response = gateway.process(build);
        return response;
    }

    private void verifyParameters(Map<String, Object> payload) {
        Assert.notEmpty(payload, "Payload required");
    }
}
