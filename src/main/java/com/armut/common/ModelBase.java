package com.armut.common;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"insertDatetime", "updateDatetime", "status"},
        allowGetters = true
)
@Data
public abstract class ModelBase extends Object implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "insertDatetime", nullable = false, updatable = false)
    @CreatedDate
    private Date insertDatetime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateDatetime", nullable = true)
    @LastModifiedDate
    private Date updateDatetime;
    
    //TODO: status ile ilgili bir şey var mi acaba TT
    @Column(name = "status", nullable = false)
    private String status = "1";

}
