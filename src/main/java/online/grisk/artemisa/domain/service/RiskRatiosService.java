package online.grisk.artemisa.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import online.grisk.artemisa.domain.dto.RiskRatioDTO;
import online.grisk.artemisa.domain.dto.RiskRatioRatioDTO;
import online.grisk.artemisa.domain.entity.RiskRatio;
import online.grisk.artemisa.domain.entity.RiskRatioRatio;
import online.grisk.artemisa.domain.exception.MyFileNotFoundException;
import online.grisk.artemisa.persistence.repository.RiskRatioRatioRepository;
import online.grisk.artemisa.persistence.repository.RiskRatioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

@Service
public class RiskRatiosService {

	@Autowired
	VariableService variableService;

	@Autowired
	TypeVariableService typeVariableService;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	private RiskRatioRepository riskRatioRepository;

	@Autowired
	private RiskRatioRatioRepository riskRatioRatioRepository;

	@Transactional
	public ResponseEntity<Map<String, Object>> registerScore(Map<String, Object> request) {
		/*
		 * DataIntegrationDTO dataIntegrationDTO = objectMapper.convertValue(request,
		 * DataIntegrationDTO.class); Collection<DataIntegration>
		 * dataIntegrationCollection = new ArrayList<>(); DataIntegration
		 * dataIntegrationsByOrganization =
		 * dataIntegrationRepository.findDataIntegrationsByOrganization(
		 * dataIntegrationDTO.getOrganization()); if (dataIntegrationsByOrganization !=
		 * null && !dataIntegrationsByOrganization.isBureau()) {
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
		riskRatioRepository.deleteAllByOrganization(organization);
	}

	@Transactional
	public RiskRatio save(RiskRatioDTO riskRatioDto) {
		RiskRatio riskRatio = new RiskRatio();
		riskRatio.setCreatedAt(new Date());
		riskRatio.setOrganization(riskRatioDto.getOrganization());
		riskRatio = riskRatioRepository.save(riskRatio);
		Short count = 0;
		for (RiskRatioRatioDTO riskRatioRatioDto : riskRatioDto.getRatios()) {
			RiskRatioRatio riskRatioRatio = new RiskRatioRatio();
			riskRatioRatio.setTitule(riskRatioRatioDto.getTitule());
			riskRatioRatio.setColor(riskRatioRatioDto.getColor());
			riskRatioRatio.setPostResult(riskRatioRatioDto.getPostResult());
			riskRatioRatio.setExpression(riskRatioRatioDto.getExpression());
			riskRatioRatio.setOrderDisplay(count);
			riskRatioRatio.setCreatedAt(new Date());
			riskRatioRatio.setRiskRatio(riskRatio);
			riskRatioRatioRepository.save(riskRatioRatio);
			count++;
		}
		return riskRatio;
	}

	@Transactional
	public RiskRatio findOne(long idRatio) {
		return riskRatioRepository.findById(idRatio)
				.orElseThrow(() -> new MyFileNotFoundException("RiskRatio not found with id " + idRatio));
	}

	@Transactional
	public RiskRatio findByOrganization(long idOrganization) {
		return riskRatioRepository.findRiskRatioByOrganization(idOrganization);
	}

}
