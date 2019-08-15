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
import online.grisk.artemisa.domain.entity.RiskScore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author Pablo Ríos Ramírez
 * @email pa.riosramirez@gmail.com
 * @web www.pabloriosramirez.com
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "score_range", schema = "public")
public class ScoreRange implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_score_range", nullable = false)
    private Long idScoreRange;

    @Basic(optional = false)
    @NotNull
    @Column(name = "upper_limit", nullable = false)
    private short upperLimit;

    @Basic(optional = false)
    @NotNull
    @Column(name = "lower_limit", nullable = false)
    private short lowerLimit;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "color", nullable = false, length = 10)
    private String color;

    @JsonIgnore
    @JoinColumn(name = "score", referencedColumnName = "id_score", nullable = false)
    @ManyToOne(optional = false)
    private RiskScore score;

    public ScoreRange(Long idScoreRange) {
        this.idScoreRange = idScoreRange;
    }

    public ScoreRange(Long idScoreRange, short upperLimit, short lowerLimit, String color) {
        this.idScoreRange = idScoreRange;
        this.upperLimit = upperLimit;
        this.lowerLimit = lowerLimit;
        this.color = color;
    }
}
