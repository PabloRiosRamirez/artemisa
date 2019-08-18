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

/**
 * @author Pablo Ríos Ramírez
 * @email pa.riosramirez@gmail.com
 * @web www.pabloriosramirez.com
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "businesstree_node", schema = "public")
public class BusinessTreeNode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_businesstree_node", nullable = false)
    private Long idBusinessTreeNode;

    @Size(max = 200)
    @Column(name = "expression", length = 200)
    private String expression;

    @Basic(optional = false)
    @NotNull
    @Column(name = "output", nullable = false)
    private boolean output;

    @Size(max = 100)
    @Column(name = "label_output", length = 100)
    private String labelOutput;

    @Size(max = 10)
    @Column(name = "color", length = 10)
    private String color;

    @Column(name = "children_negation")
    private Long childrenNegation;

    @Column(name = "children_affirmation")
    private Long childrenAffirmation;

    @JsonIgnore
    @JoinColumn(name = "businesstree", referencedColumnName = "id_businesstree", nullable = false)
    @ManyToOne(optional = false)
    private BusinessTree businessTree;

    public BusinessTreeNode(Long idBusinessTreeNode) {
        this.idBusinessTreeNode = idBusinessTreeNode;
    }

    public BusinessTreeNode(Long idBusinessTreeNode, boolean output) {
        this.idBusinessTreeNode = idBusinessTreeNode;
        this.output = output;
    }
}
