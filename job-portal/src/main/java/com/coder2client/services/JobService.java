package com.coder2client.services;


import com.coder2client.dtos.EmployerDto;
import com.coder2client.dtos.JobRequest;
import com.coder2client.dtos.JobResponse;
import com.coder2client.entities.Job;
import com.coder2client.entities.User;
import com.coder2client.enums.ExperienceLevel;
import com.coder2client.enums.JobType;
import com.coder2client.repositories.JobApplicationRepository;
import com.coder2client.repositories.JobRepository;
import com.coder2client.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final JobApplicationRepository jobApplicationRepository;

    public JobResponse createJob(JobRequest request) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User employer = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Job job = new Job();
        job.setJobTitle(request.getJobTitle());
        job.setDescription(request.getDescription());
        job.setLocation(request.getLocation());
        job.setJobType(JobType.valueOf(request.getJobType()));
        job.setExperienceLevel(ExperienceLevel.valueOf(request.getExperienceLevel()));
        job.setSalaryMin(request.getSalaryMin());
        job.setSalaryMax(request.getSalaryMax());
        job.setSalaryCurrency(request.getSalaryCurrency());
        job.setRequirements(request.getRequirements());
        job.setBenefits(request.getBenefits());
        job.setApplicationDeadline(request.getApplicationDeadline());
        job.setEmployer(employer);

        Job savedJob = jobRepository.save(job);
        return mapToJobResponse(savedJob);
    }

    public JobResponse getJobById(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        return mapToJobResponse(job);
    }

    public Page<JobResponse> searchJobs(String location, String jobType, String experienceLevel,
                                        String keyword, Pageable pageable) {
        JobType type = jobType != null ? JobType.valueOf(jobType) : null;
        ExperienceLevel level = experienceLevel != null ? ExperienceLevel.valueOf(experienceLevel) : null;

        Page<Job> jobs = jobRepository.searchJobs(location, type, level, keyword, pageable);
        return jobs.map(this::mapToJobResponse);
    }

    public Page<JobResponse> getJobsByEmployer(Long employerId, Pageable pageable) {
        Page<Job> jobs = jobRepository.findByEmployerId(employerId, pageable);
        return jobs.map(this::mapToJobResponse);
    }

    public List<String> getLocations() {
        return jobRepository.findDistinctLocations();
    }

    public JobResponse updateJob(Long jobId, JobRequest request) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        // Verify the current user is the employer
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!job.getEmployer().getEmail().equals(currentUserEmail)) {
            throw new RuntimeException("Not authorized to update this job");
        }

        job.setJobTitle(request.getJobTitle());
        job.setDescription(request.getDescription());
        job.setLocation(request.getLocation());
        job.setJobType(JobType.valueOf(request.getJobType()));
        job.setExperienceLevel(ExperienceLevel.valueOf(request.getExperienceLevel()));
        job.setSalaryMin(request.getSalaryMin());
        job.setSalaryMax(request.getSalaryMax());
        job.setSalaryCurrency(request.getSalaryCurrency());
        job.setRequirements(request.getRequirements());
        job.setBenefits(request.getBenefits());
        job.setApplicationDeadline(request.getApplicationDeadline());

        Job updatedJob = jobRepository.save(job);
        return mapToJobResponse(updatedJob);
    }

    public void deleteJob(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        // Verify the current user is the employer
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!job.getEmployer().getEmail().equals(currentUserEmail)) {
            throw new RuntimeException("Not authorized to delete this job");
        }

        jobRepository.delete(job);
    }

    private JobResponse mapToJobResponse(Job job) {
        JobResponse response = new JobResponse();
        response.setId(job.getId());
        response.setJobTitle(job.getJobTitle());
        response.setDescription(job.getDescription());
        response.setLocation(job.getLocation());
        response.setJobType(job.getJobType().name());
        response.setExperienceLevel(job.getExperienceLevel().name());
        response.setSalaryMin(job.getSalaryMin());
        response.setSalaryMax(job.getSalaryMax());
        response.setSalaryCurrency(job.getSalaryCurrency());
        response.setRequirements(job.getRequirements());
        response.setBenefits(job.getBenefits());
        response.setStatus(job.getStatus().name());
        response.setCreatedAt(job.getCreatedAt());
        response.setUpdatedAt(job.getUpdatedAt());
        response.setApplicationDeadline(job.getApplicationDeadline());
        response.setApplicationCount(jobApplicationRepository.countByJobId(job.getId()));

        EmployerDto employerDto = new EmployerDto();
        employerDto.setId(job.getEmployer().getId());
        employerDto.setFirstName(job.getEmployer().getFirstName());
        employerDto.setLastName(job.getEmployer().getLastName());
        employerDto.setCompanyName(job.getEmployer().getCompanyName());
        employerDto.setEmail(job.getEmployer().getEmail());
        response.setEmployer(employerDto);

        return response;
    }
}
