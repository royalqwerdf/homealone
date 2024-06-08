package com.elice.homealone.global.jobstatus;

import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobStatusService {
    private JobStatusRepository jobStatusRepository;

    public JobStatus createJobStatus(String jobId) {
        JobStatus jobStatus = JobStatus.builder()
            .jobId(jobId)
            .status("IN_PROGRESS")
            .startTimee(new Date())
            .build();
        return jobStatusRepository.save(jobStatus);
    }

    public void updateJobStatusProgress(String jobId, int progress) {
        JobStatus jobStatus = jobStatusRepository.findByJobId(jobId);
        if (jobStatus != null) {
            jobStatus.setProgress(progress);
            jobStatusRepository.save(jobStatus);
        }
    }

    public void markJobAsCompleted(String jobId) {
        JobStatus jobStatus = jobStatusRepository.findByJobId(jobId);
        if (jobStatus != null) {
            jobStatus.setStatus("COMPLETED");
            jobStatus.setEndTime(new Date());
            jobStatusRepository.save(jobStatus);
        }
    }

    public JobStatus getJobStatus(String jobId) {
        return jobStatusRepository.findByJobId(jobId);
    }
}
