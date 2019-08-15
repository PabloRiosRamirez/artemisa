package online.grisk.artemisa.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import online.grisk.artemisa.domain.entity.RiskRatio;
import online.grisk.artemisa.domain.service.RiskRatiosService;

@RestController
@RequestMapping({ "/api/artemisa/riskRatios" })
public class RiskRatioController {
	
	@Autowired
	private RiskRatiosService riskRatiosServices;

	@PostMapping
	public ResponseEntity<?> save(@RequestBody RiskRatio riskRatio) {
		try {
			return new ResponseEntity<Object>(riskRatiosServices.save(riskRatio),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Internal Server Error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/organization/{organizationId}")
	public ResponseEntity<?> findByOrganizationId(@PathVariable(name = "organizationId", required = true) long id) {
		try {
			return new ResponseEntity<Object>(riskRatiosServices.findByOrganization(id),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Internal Server Error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(name = "id", required = true) long id) {
		try {
			//borrar padre e hijo (risk ratio y ratio)
			riskRatiosServices.deletedByOrganization(id);
			return new ResponseEntity<Object>("Business Tree deleted.",HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Internal Server Error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
