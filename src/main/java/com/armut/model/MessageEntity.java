package com.armut.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.armut.common.ModelBase;

import lombok.Data;

@Entity
@Table(name = "messages")
@Data
public class MessageEntity extends ModelBase {
	@Column(nullable = false)
	private Long senderId;
	@Column(nullable = false)
	private Long receiverId;

	private String content;
}
