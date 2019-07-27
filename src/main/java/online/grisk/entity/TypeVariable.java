/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.grisk.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author pablo
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "type_variable",schema = "public")
public class TypeVariable implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_type_variable", nullable = false)
    private Long idTypeVariable;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "code", nullable = false, length = 50)
    private String code;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typeVariable")
    private Collection<Variable> variableCollection;

    public TypeVariable(Long idTypeVariable) {
        this.idTypeVariable = idTypeVariable;
    }

    public TypeVariable(Long idTypeVariable, String name, String code) {
        this.idTypeVariable = idTypeVariable;
        this.name = name;
        this.code = code;
    }
}
