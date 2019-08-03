package online.grisk.artemisa.bean;

import java.util.Collection;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import online.grisk.artemisa.domain.entity.DataIntegration;
import online.grisk.artemisa.domain.entity.TypeVariable;
import online.grisk.artemisa.domain.entity.Variable;

@Data
public class VariableBean extends Variable {

	private Object value;
	private String type;

}
