package com.elice.homealone.global.jobstatus;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobStatusRepository extends JpaRepository<JobStatus, Long> {


    JobStatus findByJobId(String jobId);
}
