package com.armut.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.armut.model.ActivityLogEntity;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLogEntity, Long> {

}
