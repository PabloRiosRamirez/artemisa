package online.grisk.artemisa.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import online.grisk.artemisa.domain.entity.BusinessTree;
import online.grisk.artemisa.domain.entity.RiskScore;
import online.grisk.artemisa.domain.exception.MyFileNotFoundException;
import online.grisk.artemisa.persistence.repository.BusinessTreeRepository;
import online.grisk.artemisa.persistence.repository.NodeTreeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private NodeTreeRepository NodeTreeRepository;

    @Transactional
    public ResponseEntity<Map<String, Object>> registerScore(Map<String, Object> request) {
        /*
        DataIntegrationDTO dataIntegrationDTO = objectMapper.convertValue(request, DataIntegrationDTO.class);
        Collection<DataIntegration> dataIntegrationCollection = new ArrayList<>();
        DataIntegration dataIntegrationsByOrganization = dataIntegrationRepository.findDataIntegrationsByOrganization(dataIntegrationDTO.getOrganization());
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
    	BusinessTree bt = businessTreeRepository.findBusinessTreeByOrganization(organization);
    	if(bt != null) {
	    	NodeTreeRepository.deleteByIdBusinessTree(bt.getIdTree());
	        businessTreeRepository.deleteAllByOrganization(organization);
    	}
    }

    @Transactional
    public BusinessTree save(BusinessTree businessTree) {
        return businessTreeRepository.save(businessTree);
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
