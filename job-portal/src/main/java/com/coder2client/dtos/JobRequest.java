package com.coder2client.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobRequest {

    @NotBlank(message = "Job title is required")
    private String jobTitle;

    @NotBlank(message = "Job description is required")
    private String description;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Job type is required")
    private String jobType; // FULL_TIME, PART_TIME, CONTRACT, INTERNSHIP, FREELANCE

    @NotNull(message = "Experience level is required")
    private String experienceLevel; // ENTRY, JUNIOR, MID, SENIOR, EXECUTIVE

    private BigDecimal salaryMin;
    private BigDecimal salaryMax;
    private String salaryCurrency = "USD";
    private String requirements;
    private String benefits;
    private LocalDateTime applicationDeadline;
}