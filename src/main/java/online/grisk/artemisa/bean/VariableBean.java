package online.grisk.artemisa.bean;

import lombok.Data;
import online.grisk.artemisa.domain.entity.Variable;

@Data
public class VariableBean extends Variable {

    private Object value;
    private String type;

}
