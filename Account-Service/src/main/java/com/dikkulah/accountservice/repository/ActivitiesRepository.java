package com.dikkulah.accountservice.repository;

import com.dikkulah.accountservice.model.Activity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ActivitiesRepository extends JpaRepository<Activity, UUID> {
    List<Activity> findActivitiesByAccount_IdOrderByTimeDesc(UUID uuid,Pageable pageable);
}
