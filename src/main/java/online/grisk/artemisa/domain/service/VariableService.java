package online.grisk.artemisa.domain.service;

import online.grisk.artemisa.domain.entity.Variable;
import online.grisk.artemisa.domain.exception.MyFileNotFoundException;
import online.grisk.artemisa.persistence.repository.VariableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class VariableService {

    @Autowired
    private VariableRepository variableRepository;

    @Transactional
    public Variable save(Variable variable) {
        return variableRepository.save(variable);
    }

    @Transactional
    public Collection<Variable> saveAll(Iterable<Variable> variables) {
        return variableRepository.saveAll(variables);
    }

    @Transactional
    public Variable findOne(long id_Variable) {
        return variableRepository.findById(id_Variable)
                .orElseThrow(() -> new MyFileNotFoundException("Variable not found with id " + id_Variable));
    }
}
