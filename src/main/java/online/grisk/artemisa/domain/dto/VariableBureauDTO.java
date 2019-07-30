package online.grisk.artemisa.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VariableBureauDTO {
    String name;
    String type;
    String coordenate;
    String valueDefault;


}
