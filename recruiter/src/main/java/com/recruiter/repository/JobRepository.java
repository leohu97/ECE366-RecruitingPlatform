package com.recruiter.repository;


import com.recruiter.model.Job;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByTitle(String title);

    Optional<Job> findById(Long id);

    List<Job> findByCompanyId(Long id);

    List<Job> findAll();

    Optional<Job> findByIdAndCompanyId(Long jobId, Long companyId);

    Optional<Job> findByCompanyIdAndTitle(Long companyId, String title);



    @Modifying
    @Transactional
    @Query("delete from Job job where job.id = :id")
    void deleteById(@Param("id")Long id);


}