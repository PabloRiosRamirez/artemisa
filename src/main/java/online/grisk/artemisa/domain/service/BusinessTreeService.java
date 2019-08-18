package online.grisk.artemisa.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import online.grisk.artemisa.domain.dto.BusinessTreeDTO;
import online.grisk.artemisa.domain.dto.BusinessTreeNodeDTO;
import online.grisk.artemisa.domain.entity.BusinessTree;
import online.grisk.artemisa.domain.entity.BusinessTreeNode;
import online.grisk.artemisa.domain.exception.MyFileNotFoundException;
import online.grisk.artemisa.persistence.repository.BusinessTreeRepository;
import online.grisk.artemisa.persistence.repository.NodeTreeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

@Service
public class BusinessTreeService {

	@Autowired
	VariableService variableService;

	@Autowired
	TypeVariableService typeVariableService;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	private BusinessTreeRepository businessTreeRepository;

	@Autowired
	private NodeTreeRepository nodeTreeRepository;

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
		businessTreeRepository.deleteAllByOrganization(organization);
	}

	@Transactional
	public BusinessTree save(BusinessTreeDTO businessTreedTO) {
		BusinessTree businessTree = new BusinessTree();
		businessTree.setCreatedAt(new Date());
		businessTree.setOrganization(businessTreedTO.getOrganization());
		businessTree = businessTreeRepository.save(businessTree);
		for (BusinessTreeNodeDTO nodeDto : businessTreedTO.getNodes()) {
			BusinessTreeNode businessTreeNode = new BusinessTreeNode();
		    businessTreeNode.setExpression(nodeDto.getExpression());
		    businessTreeNode.setOutput(nodeDto.isOutput());
		    businessTreeNode.setLabelOutput(nodeDto.getLabelOutput());
		    businessTreeNode.setColor(nodeDto.getColor());
			businessTreeNode.setChildrenNegation(nodeDto.getChildrenNegation());
			businessTreeNode.setChildrenAffirmation(nodeDto.getChildrenAffirmation());
			businessTreeNode.setBusinessTree(businessTree);
			nodeTreeRepository.save(businessTreeNode);
		}
		return businessTree;
	}

	@Transactional
	public BusinessTree findOne(long idTree) {
		return businessTreeRepository.findById(idTree)
				.orElseThrow(() -> new MyFileNotFoundException("BusinessTree not found with id " + idTree));
	}

	@Transactional
	public BusinessTree findByOrganization(long idOrganization) {
		return businessTreeRepository.findBusinessTreeByOrganization(idOrganization);
	}

}
