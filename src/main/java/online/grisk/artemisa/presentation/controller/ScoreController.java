package online.grisk.artemisa.presentation.controller;

import online.grisk.artemisa.domain.dto.FileResponseDTO;
import online.grisk.artemisa.domain.entity.DataIntegration;
import online.grisk.artemisa.domain.entity.RiskScore;
import online.grisk.artemisa.domain.service.DataIntegrationService;
import online.grisk.artemisa.domain.service.RiskScoreService;
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
@RequestMapping({"/api/artemisa"})
public class ScoreController {
    private static final Logger logger = LoggerFactory.getLogger(ScoreController.class);

    @Autowired
    private RiskScoreService riskScoreService;

    @GetMapping("/score/organization/{idOrganization}")
    public ResponseEntity getDataIntegration(@PathVariable("idOrganization") long idOrganization) {
        RiskScore riskScore = riskScoreService.findByOrganization(idOrganization);
        return new ResponseEntity(riskScore, HttpStatus.OK);
    }

    @PostMapping("/score")
    public ResponseEntity postDataIntegrationExcel(@NotEmpty @RequestBody Map<String, Object> payload) {
        return riskScoreService.registerScore(payload);
    }
}