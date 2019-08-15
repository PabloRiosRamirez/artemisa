package online.grisk.artemisa.presentation.controller;

import online.grisk.artemisa.domain.entity.RiskScore;
import online.grisk.artemisa.domain.service.RiskScoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.Map;

@RestController
@RequestMapping({"/api/artemisa"})
public class ScoreController {
    private static final Logger logger = LoggerFactory.getLogger(ScoreController.class);

    @Autowired
    private RiskScoreService riskScoreService;

    @GetMapping("/score/organization/{idOrganization}")
    public ResponseEntity<?> getDataIntegration(@PathVariable("idOrganization") long idOrganization) {
        RiskScore riskScore = riskScoreService.findByOrganization(idOrganization);
        return new ResponseEntity<RiskScore>(riskScore, HttpStatus.OK);
    }

    @PostMapping("/score")
    public ResponseEntity<?> postDataIntegrationExcel(@NotEmpty @RequestBody Map<String, Object> payload) {
        return riskScoreService.registerScore(payload);
    }
}