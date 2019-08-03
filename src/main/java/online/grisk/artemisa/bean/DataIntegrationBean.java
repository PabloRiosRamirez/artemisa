package online.grisk.artemisa.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import online.grisk.artemisa.domain.entity.DataIntegration;
import online.grisk.artemisa.domain.entity.Variable;

import java.util.Collection;
import java.util.List;

@Data
public class DataIntegrationBean extends DataIntegration {

    private List<VariableBean> variables;

    @Override
    @JsonIgnore
    public Collection<Variable> getVariableCollection() {
        // TODO Auto-generated method stub
        return super.getVariableCollection();
    }

}
