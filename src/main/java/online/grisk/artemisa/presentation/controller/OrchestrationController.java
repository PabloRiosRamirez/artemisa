package online.grisk.artemisa.presentation.controller;

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

import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping({"/api/artemisa"})
public class OrchestrationController {

    @Autowired
    GatewayService gateway;

    @RequestMapping(method = {RequestMethod.POST})
    public ResponseEntity<?> orquestrationAnalysis(@NotEmpty @Payload @RequestBody Map<String, Object> payload, @NotEmpty @Headers @RequestHeader Map<String, Object> headers) {
        this.verifyParameters(payload);
        Map<String, Object> request = new HashMap<>();
        request.put("request", payload);
        Message build = MessageBuilder.withPayload(request).setHeader("action", headers.getOrDefault("action", "").toString()).build();
        Map<String, Object> process = gateway.process(build);
        return new ResponseEntity<>(process, HttpStatus.valueOf(Integer.parseInt(process.getOrDefault("status", "500").toString())));
    }

    private void verifyParameters(Map<String, Object> payload) {
        Assert.notEmpty(payload, "Payload required");
    }
}
