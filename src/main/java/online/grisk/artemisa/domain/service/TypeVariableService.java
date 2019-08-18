package online.grisk.artemisa.domain.service;

import online.grisk.artemisa.domain.entity.TypeVariable;
import online.grisk.artemisa.domain.exception.MyFileNotFoundException;
import online.grisk.artemisa.persistence.repository.TypeVariableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TypeVariableService {

    @Autowired
    private TypeVariableRepository typeVariableRepository;

    @Transactional
    public TypeVariable findOne(long id_type_variable) {
        return typeVariableRepository.findById(id_type_variable)
                .orElseThrow(() -> new MyFileNotFoundException("Type Variable not found with id " + id_type_variable));
    }

    @Transactional
    public TypeVariable findByCode(String code) {
        return typeVariableRepository.findByCode(code);
    }
}
