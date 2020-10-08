package com.armut.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.armut.common.ModelBase;

import lombok.Data;

@Entity
@Table(name = "activity_logs")
@Data
public class ActivityLogEntity extends ModelBase {

	@Column( length=50, nullable=true)
	private long userId;
	@Transient
	@Column(length=50, nullable=false)
	private String activityInput;
	private String activityName;
	private int activityStatus = 1;

}
