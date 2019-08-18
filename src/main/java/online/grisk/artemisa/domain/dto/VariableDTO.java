package online.grisk.artemisa.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VariableDTO {
    Long idVariable;
    String name;
    String type;
    String coordinate;
    String defaultValue;


}
