/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.grisk.artemisa.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Pablo Ríos Ramírez
 * @email pa.riosramirez@gmail.com
 * @web www.pabloriosramirez.com
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "variable",schema = "public")
public class Variable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_variable", nullable = false)
    private Long idVariable;

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

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "coordinate", nullable = false, length = 50)
    private String coordinate;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "default_value", nullable = false, length = 100)
    private String defaultValue;

    @JsonBackReference
    @ManyToMany(mappedBy = "variableCollection")
    private Collection<DataIntegration> dataIntegrationCollection;

    @JoinColumn(name = "type_variable", referencedColumnName = "id_type_variable", nullable = false)
    @ManyToOne(optional = false)
    private TypeVariable typeVariable;

    public Variable(Long idVariable) {
        this.idVariable = idVariable;
    }

    public Variable(String name, String code, String coordinate, String defaultValue) {
        this.idVariable = idVariable;
        this.name = name;
        this.code = code;
        this.coordinate = coordinate;
        this.defaultValue = defaultValue;
    }

    public Variable(@NotNull @Size(min = 1, max = 100) String name, @NotNull @Size(min = 1, max = 50) String code, @NotNull @Size(min = 1, max = 50) String coordinate, @NotNull @Size(min = 1, max = 100) String defaultValue, TypeVariable typeVariable) {
        this.name = name;
        this.code = code;
        this.coordinate = coordinate;
        this.defaultValue = defaultValue;
        this.typeVariable = typeVariable;
    }
}
