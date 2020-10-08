package com.armut.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.armut.model.BlockedListEntity;

@Repository
public interface BlockedListRepository extends JpaRepository<BlockedListEntity, Long> {

	BlockedListEntity findByUserIdAndBlockedId(Long userId, Long blockedId);

}