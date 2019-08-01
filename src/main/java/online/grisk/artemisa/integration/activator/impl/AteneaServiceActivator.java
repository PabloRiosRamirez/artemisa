package online.grisk.artemisa.integration.activator.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import online.grisk.artemisa.domain.dto.DataIntegrationDTO;
import online.grisk.artemisa.domain.dto.VariableBureauDTO;
import online.grisk.artemisa.domain.entity.DataIntegration;
import online.grisk.artemisa.domain.entity.ServiceActivator;
import online.grisk.artemisa.domain.entity.Variable;
import online.grisk.artemisa.domain.service.DataIntegrationService;
import online.grisk.artemisa.domain.service.TypeVariableService;
import online.grisk.artemisa.domain.service.VariableService;
import online.grisk.artemisa.integration.activator.BasicRestServiceActivator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.*;

@Component
public class AteneaServiceActivator extends BasicRestServiceActivator {

    @Autowired
    UUID uuid;

    @Autowired
    ServiceActivator serviceActivatorAtenea;

    @Autowired
    DataIntegrationService dataIntegrationService;

    @Autowired
    VariableService variableService;

    @Autowired
    TypeVariableService typeVariableService;

    @Autowired
    ObjectMapper objectMapper;


    public Map<String, Object> invokeRegisterDataIntegrationExcel(@NotNull @Payload Map<String, Object> payload, @NotNull @Headers Map<String, Object> headers) throws Exception {
        ResponseEntity<JsonNode> response = this.executeRegisterDataIntegrationExcel((Map<String, Object>) payload.get("request"));
        this.addServiceResponseToResponseMap(payload, response, serviceActivatorAtenea.getServiceId());
        return payload;
    }

    private ResponseEntity<JsonNode> executeRegisterDataIntegrationExcel(Map<String, Object> request) {
        DataIntegrationDTO dataIntegrationDTO = objectMapper.convertValue(request, DataIntegrationDTO.class);
        dataIntegrationService.deletedByOrganization(dataIntegrationDTO.getOrganization());
        Collection<Variable> variableCollection = new ArrayList<>();
        for (VariableBureauDTO variable : dataIntegrationDTO.getVariables()) {
            variableCollection.add(new Variable(variable.getName(), variable.getName(), variable.getCoordenate(), variable.getValueDefault(), typeVariableService.findOne(Long.valueOf(variable.getType()))));
        }
        Collection<Variable> variables = variableService.saveAll(variableCollection);
        DataIntegration dataIntegration = dataIntegrationService.save(new DataIntegration(dataIntegrationDTO.getOrganization(), new Date(), true, false, variables));
        return new ResponseEntity(objectMapper.convertValue(dataIntegration, JsonNode.class), HttpStatus.CREATED);
    }
}
