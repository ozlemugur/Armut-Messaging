package com.armut.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.armut.common.ModelBase;

import lombok.Data;

@Entity
@Table(name = "blocked_lists")
@Data
public class BlockedListEntity extends ModelBase {
	@Column( nullable=false)
	private long blockedId;
	@Column( nullable=false)
	private long userId;

}
