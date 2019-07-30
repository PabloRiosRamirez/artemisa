package online.grisk.artemisa.presentation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import online.grisk.artemisa.domain.entity.DataIntegration;
import online.grisk.artemisa.domain.service.DataIntegrationService;
import online.grisk.artemisa.domain.dto.FileResponseDTO;

@RestController
public class DataIntegrationController {

	private static final Logger logger = LoggerFactory.getLogger(DataIntegrationController.class);

	@Autowired
	private DataIntegrationService dataIntegrationService;

	@PostMapping("/v1/rest/data-integration/{id_dataintegration}")
	public FileResponseDTO uploadFile(@PathVariable("id_dataintegration") long id_dataintegration, @RequestParam("file") MultipartFile file) {
		DataIntegration di = dataIntegrationService.uploadFile(id_dataintegration, file);
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/v1/rest/data-integration/organization/")
				.path(di.getIdDataIntegration() + "").toUriString();
		return new FileResponseDTO(di.getAnalyticsFileName(), fileDownloadUri, file.getContentType(), file.getSize());
	}

	@GetMapping("/v1/rest/data-integration/{id_organization}")
	public ResponseEntity<Resource> downloadFile(@PathVariable long id_organization) {
		// Load file from database
		DataIntegration di = dataIntegrationService.findOne(id_organization);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + di.getAnalyticsFileName() + "\"")
				.body(new ByteArrayResource(di.getAnalyticsFile()));
	}

}