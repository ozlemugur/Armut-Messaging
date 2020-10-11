package com.armut.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


import com.armut.common.ModelBase;

import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class UserEntity extends ModelBase {

	@Column(length = 50, nullable = false)
	private String userName;
	@Column(length = 50, nullable = false)
	private String password;
	@Column(name = "token", nullable = true)
	private String token;

}