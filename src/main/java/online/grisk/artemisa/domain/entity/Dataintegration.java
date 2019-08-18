/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.grisk.artemisa.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * @author Pablo Ríos Ramírez
 * @email pa.riosramirez@gmail.com
 * @web www.pabloriosramirez.com
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "dataintegration", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_dataintegration", "organization"})})
public class Dataintegration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_dataintegration", nullable = false)
    private Long idDataintegration;

    @Basic(optional = false)
    @NotNull
    @Column(name = "organization", nullable = false)
    private long organization;

    @Basic(optional = false)
    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Basic(optional = false)
    @NotNull
    @Column(name = "bureau", nullable = false)
    private boolean bureau;


    @JsonIgnore
    @Lob
    @Column(name = "analytics_file")
    private byte[] analyticsFile;

    @Column(name = "analytics_filename")
    private String analyticsFileName;

    @JsonManagedReference
    @JoinTable(name = "dataintegration_has_variable",
            joinColumns = {
                    @JoinColumn(name = "id_dataintegration", referencedColumnName = "id_dataintegration", nullable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "id_variable", referencedColumnName = "id_variable", nullable = false)})
    @ManyToMany
    private Collection<Variable> variableCollection;

    public Dataintegration(Long idDataintegration) {
        this.idDataintegration = idDataintegration;
    }

    public Dataintegration(Long idDataintegration, long organization, Date createdAt, boolean bureau) {
        this.idDataintegration = idDataintegration;
        this.organization = organization;
        this.createdAt = createdAt;
        this.bureau = bureau;
    }

    public Dataintegration(long organization, Date createdAt,boolean bureau) {
        this.organization = organization;
        this.createdAt = createdAt;
        this.bureau = bureau;
    }

    public Dataintegration(long organization, Date createdAt, boolean bureau, Collection<Variable> variableCollection) {
        this.organization = organization;
        this.createdAt = createdAt;
        this.bureau = bureau;
        this.variableCollection = variableCollection;
    }

    public Dataintegration(@NotNull long organization, @NotNull Date createdAt, @NotNull boolean enabled,
                           @NotNull boolean bureau, byte[] analyticsFile, String analyticsFileName) {
        this.organization = organization;
        this.createdAt = createdAt;
        this.bureau = bureau;
        this.analyticsFile = analyticsFile;
        this.analyticsFileName = analyticsFileName;
    }
}
