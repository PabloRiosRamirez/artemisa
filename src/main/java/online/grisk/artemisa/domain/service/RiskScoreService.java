package online.grisk.artemisa.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import online.grisk.artemisa.domain.dto.RiskScoreDTO;
import online.grisk.artemisa.domain.entity.RiskScore;
import online.grisk.artemisa.domain.entity.ScoreRange;
import online.grisk.artemisa.domain.exception.MyFileNotFoundException;
import online.grisk.artemisa.persistence.repository.RiskScoreRepository;
import online.grisk.artemisa.persistence.repository.ScoreRangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class RiskScoreService {

    @Autowired
    VariableService variableService;

    @Autowired
    TypeVariableService typeVariableService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private RiskScoreRepository riskScoreRepository;

    @Autowired
    private ScoreRangeRepository scoreRangeRepository;


    @Transactional
    public ResponseEntity<Map<String, Object>> registerScore(Map<String, Object> request) {

        RiskScoreDTO riskScoreDTO = objectMapper.convertValue(request, RiskScoreDTO.class);
        /*RiskScore riskScore = riskScoreRepository.findScoreByOrganization(riskScoreDTO.getOrganization());
        if (dataIntegrationsByOrganization != null && !dataIntegrationsByOrganization.isBureau()) {
            dataIntegrationCollection.add(dataIntegrationsByOrganization);
            variableService.deletedByDataintegration(dataIntegrationCollection);
        }
        this.deletedByOrganization(dataIntegrationDTO.getOrganization());
        Collection<Variable> variableCollection = new ArrayList<>();
        for (VariableBureauDTO variable : dataIntegrationDTO.getVariables()) {
            variableCollection.add(new Variable(variable.getName(), variable.getName(), variable.getCoordenate(), variable.getValueDefault(), typeVariableService.findByCode(variable.getType()), false));
        }
        Collection<Variable> variables = variableService.saveAll(variableCollection);
        DataIntegration dataIntegration = this.save(new DataIntegration(dataIntegrationDTO.getOrganization(), new Date(), true, false, variables));
        return new ResponseEntity(objectMapper.convertValue(dataIntegration, Map.class), HttpStatus.CREATED);
        */
        return new ResponseEntity(request, HttpStatus.OK);
    }

    @Transactional
    public void deletedByOrganization(Long organization) {
        riskScoreRepository.deleteAllByOrganization(organization);
    }

    @Transactional
    public RiskScore save(RiskScore dataIntegration) {
        return riskScoreRepository.save(dataIntegration);
    }


    @Transactional
    public RiskScore findOne(long idScore) {
        return riskScoreRepository.findById(idScore)
                .orElseThrow(() -> new MyFileNotFoundException("RiskScore not found with id " + idScore));
    }

    @Transactional
    public RiskScore findByOrganization(long idOrganization) {
        RiskScore scoreByOrganization = riskScoreRepository.findScoreByOrganization(idOrganization);
        scoreByOrganization.setScoreRangeCollection(scoreRangeRepository.findAllByScoreOrderByLowerLimit(scoreByOrganization));
        return scoreByOrganization;
    }

}
