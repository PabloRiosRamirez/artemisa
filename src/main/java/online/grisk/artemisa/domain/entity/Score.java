/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.grisk.artemisa.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * @author pablo
 */
@Getter
@Setter
@NoArgsConstructor
@Table(name = "score", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"configuration", "id_score"})})
public class Score implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_score", nullable = false)
    private Long idScore;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "titule", nullable = false, length = 100)
    private String titule;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "variable", nullable = false, length = 100)
    private String variable;
    @Basic(optional = false)
    @NotNull
    @Column(name = "enabled", nullable = false)
    private boolean enabled;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JoinColumn(name = "configuration", referencedColumnName = "id_configuration", nullable = false)
    @ManyToOne(optional = false)
    private Configuration configuration;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "score")
    private Collection<ScoreRange> scoreRangeCollection;

    public Score(Long idScore) {
        this.idScore = idScore;
    }

    public Score(Long idScore, String titule, String variable, boolean enabled, Date createdAt) {
        this.idScore = idScore;
        this.titule = titule;
        this.variable = variable;
        this.enabled = enabled;
        this.createdAt = createdAt;
    }
}
