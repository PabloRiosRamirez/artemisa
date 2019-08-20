package online.grisk.artemisa.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import online.grisk.artemisa.domain.dto.BusinessTreeDTO;
import online.grisk.artemisa.domain.dto.BusinessTreeNodeDTO;
import online.grisk.artemisa.domain.dto.FrontBusinessTreeDTO;
import online.grisk.artemisa.domain.dto.FrontBusinessTreeNodeDTO;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
	public BusinessTree save(FrontBusinessTreeDTO frontBusinessTreeDTO) {
		BusinessTree businessTree = new BusinessTree();
		businessTree.setCreatedAt(new Date());
		businessTree.setOrganization(frontBusinessTreeDTO.getOrganization());
		businessTree = businessTreeRepository.save(businessTree);
		List<BusinessTreeNode> listaNodosGuardados = new ArrayList<BusinessTreeNode>();
		for (FrontBusinessTreeNodeDTO nodeDto : frontBusinessTreeDTO.getNodes()) {
			BusinessTreeNode businessTreeNode = new BusinessTreeNode();
		    businessTreeNode.setExpression(nodeDto.getExpression());
		    businessTreeNode.setOutput(nodeDto.isOutput());
		    businessTreeNode.setLabelOutput(nodeDto.getLabel());
		    businessTreeNode.setColor(nodeDto.getColor());
			businessTreeNode.setChildrenNegation(null);
			businessTreeNode.setChildrenAffirmation(null);
			businessTreeNode.setBusinessTree(businessTree);
			businessTreeNode.setMain(nodeDto.isMain());
			BusinessTreeNode bt = nodeTreeRepository.save(businessTreeNode);
			listaNodosGuardados.add(bt);
		}
		ArrayList<FrontBusinessTreeNodeDTO> listaArrayNodesFront = (ArrayList<FrontBusinessTreeNodeDTO>) frontBusinessTreeDTO.getNodes();
		for (int i = 0; i < listaArrayNodesFront.size(); i++) {
			if(!listaArrayNodesFront.get(i).isOutput()) {
				listaNodosGuardados.get(i).setChildrenAffirmation(listaNodosGuardados.get(getIdChilden(listaArrayNodesFront, listaArrayNodesFront.get(i).getChildrenAffirmation())).getIdBusinessTreeNode());
				listaNodosGuardados.get(i).setChildrenNegation(listaNodosGuardados.get(getIdChilden(listaArrayNodesFront, listaArrayNodesFront.get(i).getChildrenNegation())).getIdBusinessTreeNode());
				nodeTreeRepository.save(listaNodosGuardados.get(i));
			}
		}
		return businessTree;

	}
	
	private int getIdChilden(List<FrontBusinessTreeNodeDTO> listaNodes, String id) {
		for (int i = 0; i < listaNodes.size(); i++) {
			if(listaNodes.get(i).getId().equalsIgnoreCase(id)) {
				return i;
			}
		}
		return -1;
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
