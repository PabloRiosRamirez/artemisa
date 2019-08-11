/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.grisk.artemisa.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
@Table(name = "risk_ratio", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_riskratio", "organization"})})
public class RiskRatio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_riskratio", nullable = false)
    private Long idRiskRatio;

    @Basic(optional = false)
    @NotNull
    @Column(name = "organization", nullable = false)
    private long organization;

    @Size(min = 1, max = 100)
    @Column(name = "titule", length = 100)
    private String titule;

    @Basic(optional = false)
    @NotNull
    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;


    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "riskRatio")
    private Collection<Ratio> ratiosCollection;

    public RiskRatio(@NotNull long organization, @Size(min = 1, max = 100) String titule, @NotNull Date createdAt, Collection<Ratio> ratiosCollection) {
        this.organization = organization;
        this.titule = titule;
        this.createdAt = createdAt;
        this.ratiosCollection = ratiosCollection;
    }
}
