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
@Table(name = "ratio", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_ratio"})})
public class Ratio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_ratio", nullable = false)
    private Long idRatio;

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

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "post_result", nullable = false, length = 25)
    private String postResult;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "operation", nullable = false, length = 100)
    private String operation;

    @Basic(optional = false)
    @NotNull
    @Column(name = "order_display", nullable = false)
    private short orderDisplay;

    @Basic(optional = false)
    @NotNull
    @Column(name = "enabled", nullable = false)
    private boolean enabled;
    @Basic(optional = false)

    @NotNull
    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @JsonIgnore
    @JoinColumn(name = "riskratio", referencedColumnName = "id_riskratio", nullable = false)
    @ManyToOne(optional = false)
    private RiskRatio riskRatio;

    public Ratio(Long idRatio) {
        this.idRatio = idRatio;
    }

    public Ratio(Long idRatio, String titule, String color, String postResult, String operation, short orderDisplay, boolean enabled, Date createdAt) {
        this.idRatio = idRatio;
        this.titule = titule;
        this.color = color;
        this.postResult = postResult;
        this.operation = operation;
        this.orderDisplay = orderDisplay;
        this.enabled = enabled;
        this.createdAt = createdAt;
    }

    public Ratio(@NotNull @Size(min = 1, max = 100) String titule, @NotNull @Size(min = 1, max = 10) String color, @NotNull @Size(min = 1, max = 25) String postResult, @NotNull @Size(min = 1, max = 100) String operation, @NotNull short orderDisplay, @NotNull boolean enabled, @NotNull Date createdAt, RiskRatio riskRatio) {
        this.titule = titule;
        this.color = color;
        this.postResult = postResult;
        this.operation = operation;
        this.orderDisplay = orderDisplay;
        this.enabled = enabled;
        this.createdAt = createdAt;
        this.riskRatio = riskRatio;
    }
}
