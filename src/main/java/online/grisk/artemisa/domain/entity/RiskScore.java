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
@Table(name = "riskscore", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_riskscore"})})
public class RiskScore implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_riskscore", nullable = false)
    private Long idRiskScore;

    @Basic(optional = false)
    @NotNull
    @Column(name = "organization", nullable = false)
    private long organization;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "variable", nullable = false, length = 100)
    private String variable;

    @Basic(optional = false)
    @NotNull
    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "riskScore")
    private Collection<RiskScoreRange> riskScoreRangeCollection;

    public RiskScore(Long idRiskScore) {
        this.idRiskScore = idRiskScore;
    }

    public RiskScore(Long idRiskScore, String variable, Date createdAt) {
        this.idRiskScore = idRiskScore;
        this.variable = variable;
        this.createdAt = createdAt;
    }
}
