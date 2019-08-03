package online.grisk.artemisa.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataIntegrationDTO {

    Long organization;
    Collection<VariableBureauDTO> variables;

    public Map<String, Object> toMap() {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("organization", organization);
        objectMap.put("variables", variables);
        return objectMap;
    }
}
