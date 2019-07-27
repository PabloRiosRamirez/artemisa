/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.grisk.artemisa.domain.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author pablo
 */
@Getter
@Setter
@NoArgsConstructor
@Table(name = "score",schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"configuration", "id_score"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Score.findAll", query = "SELECT s FROM Score s")
    , @NamedQuery(name = "Score.findByIdScore", query = "SELECT s FROM Score s WHERE s.idScore = :idScore")
    , @NamedQuery(name = "Score.findByTitule", query = "SELECT s FROM Score s WHERE s.titule = :titule")
    , @NamedQuery(name = "Score.findByVariable", query = "SELECT s FROM Score s WHERE s.variable = :variable")
    , @NamedQuery(name = "Score.findByEnabled", query = "SELECT s FROM Score s WHERE s.enabled = :enabled")
    , @NamedQuery(name = "Score.findByCreatedAt", query = "SELECT s FROM Score s WHERE s.createdAt = :createdAt")})
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

    public Score() {
    }

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

    public Long getIdScore() {
        return idScore;
    }

    public void setIdScore(Long idScore) {
        this.idScore = idScore;
    }

    public String getTitule() {
        return titule;
    }

    public void setTitule(String titule) {
        this.titule = titule;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    @XmlTransient
    public Collection<ScoreRange> getScoreRangeCollection() {
        return scoreRangeCollection;
    }

    public void setScoreRangeCollection(Collection<ScoreRange> scoreRangeCollection) {
        this.scoreRangeCollection = scoreRangeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idScore != null ? idScore.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Score)) {
            return false;
        }
        Score other = (Score) object;
        if ((this.idScore == null && other.idScore != null) || (this.idScore != null && !this.idScore.equals(other.idScore))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "online.grisk.artemisa.domain.entity.Score[ idScore=" + idScore + " ]";
    }
    
}
