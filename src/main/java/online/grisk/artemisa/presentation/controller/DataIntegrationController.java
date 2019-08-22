package online.grisk.artemisa.presentation.controller;

import online.grisk.artemisa.domain.dto.FileResponseDTO;
import online.grisk.artemisa.domain.entity.Dataintegration;
import online.grisk.artemisa.domain.service.DataintegrationService;
import online.grisk.artemisa.domain.service.VariableService;
import online.grisk.artemisa.integration.activator.impl.DataIntegrationServiceActivator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping({"/api/artemisa"})
public class DataIntegrationController {
    @Autowired
    private DataintegrationService dataIntegrationService;

    @Autowired
    private VariableService variableService;

    @Autowired
    private DataIntegrationServiceActivator dataIntegrationServiceActivator;

    @GetMapping("/dataintegration/organization/{idOrganization}")
    public ResponseEntity<?> getDataIntegration(@PathVariable("idOrganization") long idOrganization) {
        Dataintegration dataIntegration = dataIntegrationService.findByOrganization(idOrganization);
        return new ResponseEntity<Dataintegration>(dataIntegration, HttpStatus.OK);
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
        Dataintegration di = dataIntegrationServiceActivator.invokeUpdateDataIntegrationExcel(idDataintegration, file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/artemisa/dataintegration/")
                .path(di.getIdDataintegration() + "/downloadFile").toUriString();
        return new FileResponseDTO(di.getAnalyticsFileName(), fileDownloadUri, file.getContentType(), file.getSize());
    }

    @PostMapping("/dataintegration/bureau")
    public ResponseEntity postDataIntegrationBureau(@NotEmpty @RequestBody Map<String, Object> payload) {
        Map<String, Object> response = dataIntegrationServiceActivator.invokeRegisterDataIntegrationBureau(payload);
        return new ResponseEntity(response, HttpStatus.OK);
    }
    
	@GetMapping("/dataintegration/downloadFile/{id}")
	public ResponseEntity<Resource> downloadFile(@PathVariable long id) {
		// Load file from database
		Dataintegration di = dataIntegrationService.findOne(id);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + di.getAnalyticsFileName() + "\"")
				.body(new ByteArrayResource(di.getAnalyticsFile()));
	}
}