package online.grisk.artemisa.presentation.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import online.grisk.artemisa.domain.dto.FileResponseDTO;
import online.grisk.artemisa.domain.entity.DataIntegration;
import online.grisk.artemisa.domain.service.DataIntegrationService;
import online.grisk.artemisa.integration.gateway.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping({"/api/artemisa"})
@Api(value = "Consumer API Artemisa")
public class MainController {

    @Autowired
    GatewayService gateway;

    @RequestMapping(method = {RequestMethod.POST})
    public ResponseEntity<?> report(@NotEmpty @Payload @RequestBody Map<String, Object> payload, @NotEmpty @Headers @RequestHeader Map<String, Object> headers) {
        this.verifyParameters(payload);
        Map<String, Object> request = new HashMap<>();
        request.put("request", payload);
        Message build = MessageBuilder.withPayload(request).setHeader("action", headers.getOrDefault("action", "").toString()).build();
        Map<String, Object> process = gateway.process(build);
        return new ResponseEntity<>(process, HttpStatus.valueOf(Integer.parseInt(process.getOrDefault("status", "500").toString())));
    }

    private void verifyParameters(Map<String, Object> payload){
        Assert.notEmpty(payload, "Payload required");
    }
}
