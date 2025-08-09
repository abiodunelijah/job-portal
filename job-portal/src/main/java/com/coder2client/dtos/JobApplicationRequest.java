package com.coder2client.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationRequest {

    @NotNull(message = "Job ID is required")
    private Long jobId;

    private String coverLetter;
    private String resumeUrl;
}