package com.recruiter.repository;

import com.recruiter.model.Application;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Optional<Application> findById(Long id);

    List<Application> findAll();

    Optional<Application> findByJobIdAndUserId(Long jobId, Long userId);

    Optional<Application> findByIdAndUserId(Long Id, Long userId);

    @Modifying
    @Transactional
    @Query("delete from Application application where application.id = :id")
    void deleteById(@Param("id")Long id);

}