/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.grisk.artemisa.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;
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
@Entity
@Table(name = "configuration",schema = "public")
public class Configuration implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_configuration", nullable = false)
    private Long idConfiguration;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "update_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "organization", nullable = false, length = 100)
    private String organization;
    @Basic(optional = false)
    @NotNull
    @Column(name = "full", nullable = false)
    private boolean full;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "configuration")
    private Score score;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "configuration")
    private Tree tree;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "configuration")
    private Collection<Ratio> ratioCollection;

    public Configuration(Long idConfiguration) {
        this.idConfiguration = idConfiguration;
    }

    public Configuration(Long idConfiguration, Date updateAt, String organization, boolean full) {
        this.idConfiguration = idConfiguration;
        this.updateAt = updateAt;
        this.organization = organization;
        this.full = full;
    }

}
