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

import online.grisk.artemisa.domain.dto.RiskRatioDTO;
import online.grisk.artemisa.domain.entity.RiskRatio;
import online.grisk.artemisa.domain.service.RiskRatiosService;

@RestController
@RequestMapping({ "/api/artemisa/ratios" })
public class RiskRatioController {

	@Autowired
	private RiskRatiosService riskRatiosServices;

	@PostMapping(consumes = { "application/json" })
	public ResponseEntity<?> save(@RequestBody RiskRatioDTO riskRatioDto) {
		try {
			riskRatiosServices.deletedByOrganization(riskRatioDto.getOrganization());
			RiskRatio riskRatio = riskRatiosServices.save(riskRatioDto);
			return new ResponseEntity<Object>(riskRatio, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Internal Server Error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/organization/{organizationId}")
	public ResponseEntity<?> findByOrganizationId(@PathVariable(name = "organizationId", required = true) long id) {
		try {
			RiskRatio riskRatio = riskRatiosServices.findByOrganization(id);
			return riskRatio != null ? new ResponseEntity<Object>(riskRatio, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<String>("Internal Server Error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
