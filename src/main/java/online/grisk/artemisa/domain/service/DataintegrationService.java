package online.grisk.artemisa.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import online.grisk.artemisa.domain.dto.DataintegrationDTO;
import online.grisk.artemisa.domain.dto.VariableDTO;
import online.grisk.artemisa.domain.entity.Dataintegration;
import online.grisk.artemisa.domain.entity.Variable;
import online.grisk.artemisa.domain.exception.FileStorageException;
import online.grisk.artemisa.domain.exception.MyFileNotFoundException;
import online.grisk.artemisa.persistence.repository.BusinessTreeRepository;
import online.grisk.artemisa.persistence.repository.DataIntegrationRepository;
import online.grisk.artemisa.persistence.repository.RiskRatioRepository;
import online.grisk.artemisa.persistence.repository.RiskScoreRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

@Service
public class DataintegrationService {

    @Autowired
    VariableService variableService;

    @Autowired
    TypeVariableService typeVariableService;

    @Autowired
    private RiskScoreRepository riskScoreRepository;
    @Autowired
    private RiskRatioRepository riskRatioRepository;
    @Autowired
    private BusinessTreeRepository businessTreeRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private DataIntegrationRepository dataIntegrationRepository;

    @Transactional
    public ResponseEntity<Map<String, Object>> registerDataIntegrationExcel(Map<String, Object> request) {
        DataintegrationDTO dataIntegrationDTO = objectMapper.convertValue(request, DataintegrationDTO.class);
        Collection<Dataintegration> dataintegrationCollection = new ArrayList<>();
        Dataintegration dataIntegrationsByOrganization = dataIntegrationRepository.findDataIntegrationsByOrganization(dataIntegrationDTO.getOrganization());
        if (dataIntegrationsByOrganization != null && !dataIntegrationsByOrganization.isBureau()) {
            dataintegrationCollection.add(dataIntegrationsByOrganization);
            variableService.deletedByDataintegration(dataintegrationCollection);
        }
        this.deletedByOrganization(dataIntegrationDTO.getOrganization());
        riskScoreRepository.deleteAllByOrganization(dataIntegrationDTO.getOrganization());
        riskRatioRepository.deleteAllByOrganization(dataIntegrationDTO.getOrganization());
        businessTreeRepository.deleteAllByOrganization(dataIntegrationDTO.getOrganization());
        Collection<Variable> variableCollection = new ArrayList<>();
        for (VariableDTO variable : dataIntegrationDTO.getVariables()) {
            variableCollection.add(new Variable(variable.getName(), variable.getName().replaceAll(" ", "").trim().toUpperCase(), variable.getCoordinate(), variable.getDefaultValue(), typeVariableService.findByCode(variable.getType()), false));
        }
        Collection<Variable> variables = variableService.saveAll(variableCollection);
        Dataintegration dataIntegration = this.save(new Dataintegration(dataIntegrationDTO.getOrganization(), new Date(), false, variables));
        return new ResponseEntity(objectMapper.convertValue(dataIntegration, Map.class), HttpStatus.CREATED);
    }

    @Transactional
    public Dataintegration updateDataIntegrationExcel(long id_dataintegration, MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            Dataintegration di = dataIntegrationRepository.getOne(id_dataintegration);
            di.setAnalyticsFileName(fileName);
            di.setAnalyticsFile(file.getBytes());
            return dataIntegrationRepository.save(di);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Transactional
    public ResponseEntity<Map<String, Object>> registerDataIntegrationBureau(Map<String, Object> request) {
        DataintegrationDTO dataIntegrationDTO = objectMapper.convertValue(request, DataintegrationDTO.class);
        Collection<Dataintegration> dataintegrationCollection = new ArrayList<>();
        Dataintegration dataIntegrationsByOrganization = dataIntegrationRepository.findDataIntegrationsByOrganization(dataIntegrationDTO.getOrganization());
        if (dataIntegrationsByOrganization != null && !dataIntegrationsByOrganization.isBureau()) {
            dataintegrationCollection.add(dataIntegrationsByOrganization);
            variableService.deletedByDataintegration(dataintegrationCollection);
        }
        this.deletedByOrganization(dataIntegrationDTO.getOrganization());
        riskScoreRepository.deleteAllByOrganization(dataIntegrationDTO.getOrganization());
        riskRatioRepository.deleteAllByOrganization(dataIntegrationDTO.getOrganization());
        businessTreeRepository.deleteAllByOrganization(dataIntegrationDTO.getOrganization());
        Collection<Variable> variableCollection = new ArrayList<>();
        for (VariableDTO variable : dataIntegrationDTO.getVariables()) {
            variableCollection.add(variableService.findOne(variable.getIdVariable()));
        }
        Dataintegration dataIntegration = this.save(new Dataintegration(dataIntegrationDTO.getOrganization(), new Date(), true, variableCollection));
        return new ResponseEntity((Map<String, Object>) objectMapper.convertValue(dataIntegration, Map.class), HttpStatus.CREATED);
    }

    @Transactional
    public void deletedByOrganization(Long organization) {
        dataIntegrationRepository.deleteAllByOrganization(organization);
        businessTreeRepository.deleteAllByOrganization(organization);
        riskRatioRepository.deleteAllByOrganization(organization);
        riskScoreRepository.deleteAllByOrganization(organization);
    }

    @Transactional
    public Dataintegration save(Dataintegration dataIntegration) {
        return dataIntegrationRepository.save(dataIntegration);
    }


    @Transactional
    public Dataintegration findOne(long idDataintegration) {
        return dataIntegrationRepository.findById(idDataintegration)
                .orElseThrow(() -> new MyFileNotFoundException("DataIntegration not found with id " + idDataintegration));
    }

    @Transactional
    public Dataintegration findByOrganization(long idOrganization) {
        Dataintegration dataIntegrationsByOrganization = dataIntegrationRepository.findDataIntegrationsByOrganization(idOrganization);
        Collection<Dataintegration> dataintegrationCollection = new ArrayList<>();
        dataintegrationCollection.add(dataIntegrationsByOrganization);
        dataIntegrationsByOrganization.setVariableCollection(variableService.findAllByDataintegrationOrderByName(dataintegrationCollection));
        return dataIntegrationsByOrganization;
    }

}
