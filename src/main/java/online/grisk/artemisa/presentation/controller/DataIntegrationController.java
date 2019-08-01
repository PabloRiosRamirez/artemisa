package online.grisk.artemisa.presentation.controller;

import online.grisk.artemisa.domain.dto.FileResponseDTO;
import online.grisk.artemisa.domain.entity.DataIntegration;
import online.grisk.artemisa.domain.service.DataIntegrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;

@RestController
@RequestMapping({"/api/artemisa"})
public class DataIntegrationController {
    private static final Logger logger = LoggerFactory.getLogger(DataIntegrationController.class);

    @Autowired
    private DataIntegrationService dataIntegrationService;

    @PutMapping("/data-integration/{id_dataintegration}")
    public FileResponseDTO postDataIntegration(@PathVariable("id_dataintegration") long id_dataintegration, @RequestParam("file") MultipartFile file) {
        DataIntegration di = dataIntegrationService.uploadFile(id_dataintegration, file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/v1/rest/data-integration/organization/")
                .path(di.getIdDataIntegration() + "").toUriString();
        return new FileResponseDTO(di.getAnalyticsFileName(), fileDownloadUri, file.getContentType(), file.getSize());
    }

    @GetMapping("/data-integration/organization/{id_organization}")
    public ResponseEntity getDataIntegration(@PathVariable("id_organization") long id_organization) {
        DataIntegration dataIntegration = dataIntegrationService.findByOrganization(id_organization);
        if (dataIntegration != null) {
            return new ResponseEntity(dataIntegration, HttpStatus.OK);
        } else {
            return new ResponseEntity(new HashMap<>(), HttpStatus.OK);
        }
    }
}