package online.grisk.artemisa.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import online.grisk.artemisa.domain.dto.RiskScoreDTO;
import online.grisk.artemisa.domain.dto.RiskScoreRangeDTO;
import online.grisk.artemisa.domain.entity.RiskScore;
import online.grisk.artemisa.domain.entity.RiskScoreRange;
import online.grisk.artemisa.domain.exception.MyFileNotFoundException;
import online.grisk.artemisa.persistence.repository.RiskScoreRangeRepository;
import online.grisk.artemisa.persistence.repository.RiskScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

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
	private RiskScoreRangeRepository riskScoreRangeRepository;

	@Transactional
	public ResponseEntity<Map<String, Object>> registerScore(Map<String, Object> request) {

		RiskScoreDTO riskScoreDTO = objectMapper.convertValue(request, RiskScoreDTO.class);
		/*
		 * RiskScore riskScore =
		 * riskScoreRepository.findScoreByOrganization(riskScoreDTO.getOrganization());
		 * if (dataIntegrationsByOrganization != null &&
		 * !dataIntegrationsByOrganization.isBureau()) {
		 * dataIntegrationCollection.add(dataIntegrationsByOrganization);
		 * variableService.deletedByDataintegration(dataIntegrationCollection); }
		 * this.deletedByOrganization(dataIntegrationDTO.getOrganization());
		 * Collection<Variable> variableCollection = new ArrayList<>(); for
		 * (VariableBureauDTO variable : dataIntegrationDTO.getVariables()) {
		 * variableCollection.add(new Variable(variable.getName(), variable.getName(),
		 * variable.getCoordenate(), variable.getValueDefault(),
		 * typeVariableService.findByCode(variable.getType()), false)); }
		 * Collection<Variable> variables = variableService.saveAll(variableCollection);
		 * DataIntegration dataIntegration = this.save(new
		 * DataIntegration(dataIntegrationDTO.getOrganization(), new Date(), true,
		 * false, variables)); return new
		 * ResponseEntity(objectMapper.convertValue(dataIntegration, Map.class),
		 * HttpStatus.CREATED);
		 */
		return new ResponseEntity(request, HttpStatus.OK);
	}

	@Transactional
	public void deletedByOrganization(Long organization) {
		riskScoreRepository.deleteAllByOrganization(organization);
	}

	@Transactional
	public RiskScore save(RiskScoreDTO riskScoreDto) {
		RiskScore riskScore = new RiskScore();
		riskScore.setCreatedAt(new Date());
		riskScore.setOrganization(riskScoreDto.getOrganization());
		riskScore.setVariable(riskScoreDto.getVariable());
		riskScore.setTitule(riskScoreDto.getTitule());
		riskScore = riskScoreRepository.save(riskScore);
		for (RiskScoreRangeDTO rangeDto : riskScoreDto.getRanges()) {
			RiskScoreRange riskScoreRange = new RiskScoreRange();
			riskScoreRange.setColor(rangeDto.getColor());
			riskScoreRange.setUpperLimit(rangeDto.getUpperLimit());
			riskScoreRange.setLowerLimit(rangeDto.getLowerLimit());
			riskScoreRange.setRiskScore(riskScore);
			riskScoreRangeRepository.save(riskScoreRange);
		}
		return riskScore;
	}

	@Transactional
	public RiskScore findOne(long idScore) {
		return riskScoreRepository.findById(idScore)
				.orElseThrow(() -> new MyFileNotFoundException("RiskScore not found with id " + idScore));
	}

	@Transactional
	public RiskScore findByOrganization(long idOrganization) {
		RiskScore scoreByOrganization = riskScoreRepository.findRiskScoreByOrganization(idOrganization);
		if(scoreByOrganization != null) {
			scoreByOrganization.setRiskScoreRangeCollection(riskScoreRangeRepository.findAllByRiskScoreOrderByLowerLimit(scoreByOrganization));
		}
		return scoreByOrganization;
	}

}
