package com.armut.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.Valid;


import com.armut.common.ModelBase;

import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class UserEntity extends ModelBase {

	@Column(length = 50, nullable = false)
	@Valid
	private String userName;
	@Column(nullable = false)
	
	private String password;
	
	//private String token ;
	
	@Column(name = "UUID", nullable=true)
	private String token;

}