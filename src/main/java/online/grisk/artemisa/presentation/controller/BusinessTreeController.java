package online.grisk.artemisa.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import online.grisk.artemisa.domain.dto.BusinessTreeDTO;
import online.grisk.artemisa.domain.entity.BusinessTree;
import online.grisk.artemisa.domain.service.BusinessTreeService;

@RestController
@RequestMapping({ "/api/artemisa/tree" })
public class BusinessTreeController {

	@Autowired
	private BusinessTreeService businessTreeService;

	@PostMapping
	public ResponseEntity<?> save(@RequestBody BusinessTreeDTO businessTreeDto) {
		try {
			businessTreeService.deletedByOrganization(businessTreeDto.getOrganization());
			return new ResponseEntity<Object>(businessTreeService.save(businessTreeDto), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Internal Server Error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/organization/{organizationId}")
	public ResponseEntity<?> findByOrganizationId(@PathVariable(name = "organizationId", required = true) long id) {
		try {
			BusinessTree businessTree = businessTreeService.findByOrganization(id);
			return new ResponseEntity<BusinessTree>(businessTree, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Internal Server Error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
