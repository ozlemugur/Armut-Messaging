package com.armut.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.armut.model.MessageEntity;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long>{

	List<MessageEntity> findByReceiverId(long receiverId);
	List<MessageEntity> findBySenderId(long senderId);
	
}
