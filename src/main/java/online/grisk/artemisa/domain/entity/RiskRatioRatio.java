/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.grisk.artemisa.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
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
@Table(name = "riskratio_ratio", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_riskratio_ratio"})})
public class RiskRatioRatio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_riskratio_ratio", nullable = false)
    private Long idRiskRatioRatio;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "titule", nullable = false, length = 100)
    private String titule;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "color", nullable = false, length = 10)
    private String color;

    @Column(name = "post_result", length = 50)
    private String postResult;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "expression", nullable = false, length = 100)
    private String expression;

    @Basic(optional = false)
    @NotNull
    @Column(name = "order_display", nullable = false)
    private short orderDisplay;

    @NotNull
    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @JsonIgnore
    @JoinColumn(name = "riskratio", referencedColumnName = "id_riskratio", nullable = false)
    @ManyToOne(optional = false)
    private RiskRatio riskRatio;

    public RiskRatioRatio(Long idRiskRatioRatio) {
        this.idRiskRatioRatio = idRiskRatioRatio;
    }

    public RiskRatioRatio(Long idRiskRatioRatio, String titule, String color, String postResult, String expression, short orderDisplay, boolean enabled, Date createdAt) {
        this.idRiskRatioRatio = idRiskRatioRatio;
        this.titule = titule;
        this.color = color;
        this.postResult = postResult;
        this.expression = expression;
        this.orderDisplay = orderDisplay;
        this.createdAt = createdAt;
    }

    public RiskRatioRatio(@NotNull @Size(min = 1, max = 100) String titule, @NotNull @Size(min = 1, max = 10) String color, @NotNull @Size(min = 1, max = 25) String postResult, @NotNull @Size(min = 1, max = 100) String operation, @NotNull short orderDisplay, @NotNull boolean enabled, @NotNull Date createdAt, RiskRatio riskRatio) {
        this.titule = titule;
        this.color = color;
        this.postResult = postResult;
        this.orderDisplay = orderDisplay;
        this.createdAt = createdAt;
        this.riskRatio = riskRatio;
    }
}
