package com.coder2client.repositories;


import com.coder2client.entities.JobApplication;
import com.coder2client.enums.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    Page<JobApplication> findByApplicantId(Long applicantId, Pageable pageable);

    Page<JobApplication> findByJobId(Long jobId, Pageable pageable);

    List<JobApplication> findByJobIdAndStatus(Long jobId, ApplicationStatus status);

    @Query("SELECT ja FROM JobApplication ja WHERE ja.job.employer.id = :employerId")
    Page<JobApplication> findByEmployerId(@Param("employerId") Long employerId, Pageable pageable);

    boolean existsByJobIdAndApplicantId(Long jobId, Long applicantId);

    @Query("SELECT COUNT(ja) FROM JobApplication ja WHERE ja.job.id = :jobId")
    long countByJobId(@Param("jobId") Long jobId);

    @Query("SELECT COUNT(ja) FROM JobApplication ja WHERE ja.applicant.id = :applicantId")
    long countByApplicantId(@Param("applicantId") Long applicantId);
}