package com.coder2client.repositories;

import com.coder2client.entities.Job;
import com.coder2client.enums.ExperienceLevel;
import com.coder2client.enums.JobStatus;
import com.coder2client.enums.JobType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    Page<Job> findByStatus(JobStatus status, Pageable pageable);

    Page<Job> findByEmployerId(Long employerId, Pageable pageable);

    List<Job> findByEmployerIdAndStatus(Long employerId, JobStatus status);

    @Query("SELECT j FROM Job j WHERE j.status = 'ACTIVE' AND " +
            "(:location IS NULL OR LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%'))) AND " +
            "(:jobType IS NULL OR j.jobType = :jobType) AND " +
            "(:experienceLevel IS NULL OR j.experienceLevel = :experienceLevel) AND " +
            "(:keyword IS NULL OR LOWER(j.jobTitle) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(j.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Job> searchJobs(@Param("location") String location,
                         @Param("jobType") JobType jobType,
                         @Param("experienceLevel") ExperienceLevel experienceLevel,
                         @Param("keyword") String keyword,
                         Pageable pageable);

    @Query("SELECT DISTINCT j.location FROM Job j WHERE j.status = 'ACTIVE'")
    List<String> findDistinctLocations();

    @Query("SELECT COUNT(j) FROM Job j WHERE j.employer.id = :employerId")
    long countByEmployerId(@Param("employerId") Long employerId);
}
