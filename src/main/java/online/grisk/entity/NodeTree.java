/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.grisk.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Pablo Ríos Ramírez
 * @email pa.riosramirez@gmail.com
 * @web www.pabloriosramirez.com
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "node_tree",schema = "public")
public class NodeTree implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_node_tree", nullable = false)
    private Long idNodeTree;
    @Size(max = 100)
    @Column(name = "variable", length = 100)
    private String variable;
    @Size(max = 50)
    @Column(name = "comparator", length = 50)
    private String comparator;
    @Size(max = 50)
    @Column(name = "value_comparator", length = 50)
    private String valueComparator;
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
    @Column(name = "parent")
    private BigInteger parent;
    @JoinColumn(name = "tree", referencedColumnName = "id_tree", nullable = false)
    @ManyToOne(optional = false)
    private Tree tree;

    public NodeTree(Long idNodeTree) {
        this.idNodeTree = idNodeTree;
    }

    public NodeTree(Long idNodeTree, boolean output) {
        this.idNodeTree = idNodeTree;
        this.output = output;
    }

}
