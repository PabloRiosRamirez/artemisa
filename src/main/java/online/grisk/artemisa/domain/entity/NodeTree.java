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
@Table(name = "node_tree", schema = "public")
public class NodeTree implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_node_tree", nullable = false)
    private Long idNodeTree;

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

    @Column(name = "children_afirmation")
    private Long childrenAfirmation;

    @JsonIgnore
    @JoinColumn(name = "tree", referencedColumnName = "id_tree", nullable = false)
    @ManyToOne(optional = false)
    private BusinessTree tree;

    public NodeTree(Long idNodeTree) {
        this.idNodeTree = idNodeTree;
    }

    public NodeTree(Long idNodeTree, boolean output) {
        this.idNodeTree = idNodeTree;
        this.output = output;
    }

}
