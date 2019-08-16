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

import online.grisk.artemisa.domain.dto.RiskScoreDTO;
import online.grisk.artemisa.domain.entity.RiskScore;
import online.grisk.artemisa.domain.service.RiskScoreService;

@RestController
@RequestMapping({ "/api/artemisa/score" })
public class RiskScoreController {

	@Autowired
	private RiskScoreService riskScoreService;

	@PostMapping(consumes = { "application/json" })
	public ResponseEntity<?> save(@RequestBody RiskScoreDTO riskScoreDto) {
		try {
			riskScoreService.deletedByOrganization(riskScoreDto.getOrganization());
			RiskScore riskScore = riskScoreService.save(riskScoreDto);
			return new ResponseEntity<Object>(riskScore, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Internal Server Error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/organization/{organizationId}")
	public ResponseEntity<?> findByOrganizationId(@PathVariable(name = "organizationId", required = true) long id) {
		try {
			RiskScore riskScore = riskScoreService.findByOrganization(id);
			return riskScore != null ? new ResponseEntity<RiskScore>(riskScore, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<String>("Internal Server Error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
