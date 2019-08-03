package online.grisk.artemisa.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import online.grisk.artemisa.domain.dto.DataIntegrationDTO;
import online.grisk.artemisa.domain.dto.VariableBureauDTO;
import online.grisk.artemisa.domain.entity.DataIntegration;
import online.grisk.artemisa.domain.entity.Variable;
import online.grisk.artemisa.domain.exception.FileStorageException;
import online.grisk.artemisa.domain.exception.MyFileNotFoundException;
import online.grisk.artemisa.persistence.repository.DataIntegrationRepository;
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
public class DataIntegrationService {

    @Autowired
    VariableService variableService;

    @Autowired
    TypeVariableService typeVariableService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private DataIntegrationRepository dataIntegrationRepository;

    @Transactional
    public ResponseEntity<Map<String, Object>> registerDataIntegrationExcel(Map<String, Object> request) {
        DataIntegrationDTO dataIntegrationDTO = objectMapper.convertValue(request, DataIntegrationDTO.class);
        Collection<DataIntegration> dataIntegrationCollection  = new ArrayList<>();
        dataIntegrationCollection.add(dataIntegrationRepository.findDataIntegrationsByOrganization(dataIntegrationDTO.getOrganization()));
        variableService.deletedByDataintegration(dataIntegrationCollection);
        this.deletedByOrganization(dataIntegrationDTO.getOrganization());
        Collection<Variable> variableCollection = new ArrayList<>();
        for (VariableBureauDTO variable : dataIntegrationDTO.getVariables()) {
            variableCollection.add(new Variable(variable.getName(), variable.getName(), variable.getCoordenate(), variable.getValueDefault(), typeVariableService.findByCode(variable.getType()), false));
        }
        Collection<Variable> variables = variableService.saveAll(variableCollection);
        DataIntegration dataIntegration = this.save(new DataIntegration(dataIntegrationDTO.getOrganization(), new Date(), true, false, variables));
        return new ResponseEntity(objectMapper.convertValue(dataIntegration, Map.class), HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<Map<String, Object>> registerDataIntegrationBureau(Map<String, Object> request) {
        DataIntegrationDTO dataIntegrationDTO = objectMapper.convertValue(request, DataIntegrationDTO.class);
        this.deletedByOrganization(dataIntegrationDTO.getOrganization());
        Collection<Variable> variableCollection = new ArrayList<>();
        for (VariableBureauDTO variable : dataIntegrationDTO.getVariables()) {
            variableCollection.add(variableService.findOne(variable.getIdVariable()));
        }
        DataIntegration dataIntegration = this.save(new DataIntegration(dataIntegrationDTO.getOrganization(), new Date(), true, true, variableCollection));
        return new ResponseEntity((Map<String, Object>) objectMapper.convertValue(dataIntegration, Map.class), HttpStatus.CREATED);
    }

    @Transactional
    public void deletedByOrganization(Long organization) {
        dataIntegrationRepository.deleteAllByOrganization(organization);
    }

    @Transactional
    public DataIntegration save(DataIntegration dataIntegration) {
        return dataIntegrationRepository.save(dataIntegration);
    }

    @Transactional
    public DataIntegration uploadFile(long id_dataintegration, MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            DataIntegration di = dataIntegrationRepository.getOne(id_dataintegration);

            di.setAnalyticsFileName(fileName);
            di.setAnalyticsFile(file.getBytes());

            return dataIntegrationRepository.save(di);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Transactional
    public DataIntegration findOne(long idDataintegration) {
        return dataIntegrationRepository.findById(idDataintegration)
                .orElseThrow(() -> new MyFileNotFoundException("DataIntegration not found with id " + idDataintegration));
    }

    @Transactional
    public DataIntegration findByOrganization(long idOrganization) {
        return dataIntegrationRepository.findDataIntegrationsByOrganization(idOrganization);
    }

}
