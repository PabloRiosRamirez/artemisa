package online.grisk.artemisa.presentation.controller;

import online.grisk.artemisa.domain.dto.FileResponseDTO;
import online.grisk.artemisa.domain.entity.DataIntegration;
import online.grisk.artemisa.domain.service.DataIntegrationService;
import online.grisk.artemisa.domain.service.VariableService;
import online.grisk.artemisa.integration.activator.impl.DataIntegrationServiceActivator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;

@RestController
 
public class DataIntegrationController {
    private static final Logger logger = LoggerFactory.getLogger(DataIntegrationController.class);

    @Autowired
    private DataIntegrationService dataIntegrationService;

    @Autowired
    private VariableService variableService;

    @Autowired
    private DataIntegrationServiceActivator dataIntegrationServiceActivator;

    @GetMapping("/dataintegration/organization/{idOrganization}")
    public ResponseEntity<?> getDataIntegration(@PathVariable("idOrganization") long idOrganization) {
        DataIntegration dataIntegration = dataIntegrationService.findByOrganization(idOrganization);
        return new ResponseEntity<DataIntegration>(dataIntegration, HttpStatus.OK);
    }

    @GetMapping("/variables/bureau")
    public ResponseEntity<?> getVariablesBureau(@RequestHeader Map<String, Object> headers) {
        Map response = new HashMap();
        response.put("variables", variableService.findAllByBureau(true));
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/dataintegration/excel")
    public ResponseEntity postDataIntegrationExcel(@NotEmpty @RequestBody Map<String, Object> payload) {
        Map<String, Object> response = dataIntegrationServiceActivator.invokeRegisterDataIntegrationExcel(payload);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PutMapping("/dataintegration/{idDataintegration}/excel")
    public FileResponseDTO putDataIntegration(@PathVariable("idDataintegration") long idDataintegration, @RequestParam("file") MultipartFile file) {
        DataIntegration di = dataIntegrationServiceActivator.invokeUpdateDataIntegrationExcel(idDataintegration, file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/v1/rest/dataintegration/organization/")
                .path(di.getIdDataIntegration() + "/file").toUriString();
        return new FileResponseDTO(di.getAnalyticsFileName(), fileDownloadUri, file.getContentType(), file.getSize());
    }

    @PostMapping("/dataintegration/bureau")
    public ResponseEntity postDataIntegrationBureau(@NotEmpty @RequestBody Map<String, Object> payload) {
        Map<String, Object> response = dataIntegrationServiceActivator.invokeRegisterDataIntegrationBureau(payload);
        return new ResponseEntity(response, HttpStatus.OK);
    }
}