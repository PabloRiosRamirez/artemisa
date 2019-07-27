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
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 * @author pablo
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "data_integration", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_data_integration", "organization"})})
public class DataIntegration implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_data_integration", nullable = false)
    private Long idDataIntegration;
    @Basic(optional = false)
    @NotNull
    @Column(name = "organization", nullable = false)
    private long organization;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "enabled", nullable = false)
    private boolean enabled;
    @Basic(optional = false)
    @NotNull
    @Column(name = "bureau", nullable = false)
    private boolean bureau;
    @Lob
    @Column(name = "analytics_file")
    private byte[] analyticsFile;
    @Column(name = "analytics_filename")
    private String analyticsFileName;
    @Column(name = "analytics_filetype")
    private String analyticsFileType;
    /*@JoinTable(name = "data_integration_has_variable", joinColumns = {
        @JoinColumn(name = "id_data_integration", referencedColumnName = "id_data_integration", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "id_variable", referencedColumnName = "id_variable", nullable = false)})
    @ManyToMany
    private Collection<Variable> variableCollection;*/

	@JoinTable(name = "data_integration_has_variable", joinColumns = {
			@JoinColumn(name = "id_data_integration", referencedColumnName = "id_data_integration", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "id_variable", referencedColumnName = "id_variable", nullable = false) })
	@ManyToMany
	private Collection<Variable> variableCollection;

	public DataIntegration(Long idDataIntegration) {
		this.idDataIntegration = idDataIntegration;
	}

	public DataIntegration(Long idDataIntegration, long organization, Date createdAt, boolean enabled, boolean bureau) {
		this.idDataIntegration = idDataIntegration;
		this.organization = organization;
		this.createdAt = createdAt;
		this.enabled = enabled;
		this.bureau = bureau;
	}

	public DataIntegration(@NotNull long organization, @NotNull Date createdAt, @NotNull boolean enabled,
			@NotNull boolean bureau, byte[] analyticsFile, String analyticsFileName, String analyticsFileType,
			Collection<Variable> variableCollection) {
		super();
		this.organization = organization;
		this.createdAt = createdAt;
		this.enabled = enabled;
		this.bureau = bureau;
		this.analyticsFile = analyticsFile;
		this.analyticsFileName = analyticsFileName;
		this.analyticsFileType = analyticsFileType;
		this.variableCollection = variableCollection;
	}
}