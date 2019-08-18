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
@Table(name = "businesstree", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_businesstree", "organization"})})
public class BusinessTree implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_businesstree", nullable = false)
    private Long idBusinessTree;

    @Basic(optional = false)
    @NotNull
    @Column(name = "organization", nullable = false)
    private long organization;

    @Basic(optional = false)
    @NotNull
    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "businessTree")
    private Collection<BusinessTreeNode> businessTreeNodeCollection;

    public BusinessTree(Long idBusinessTree) {
        this.idBusinessTree = idBusinessTree;
    }

    public BusinessTree(Long idBusinessTree, String titule, boolean enabled, Date createdAt) {
        this.idBusinessTree = idBusinessTree;
        this.createdAt = createdAt;
    }
}
