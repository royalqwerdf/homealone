package com.elice.homealone.global.jobstatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobId;

    @Setter
    private String status;
    @Setter
    private int progress;
    @Setter
    private Date startTime;
    @Setter
    private Date endTime;

    @Builder
    JobStatus(
        String jobId,
        String status,
        Date startTimee) {
        this.jobId = jobId;
        this.status = status;
        this.startTime = startTime;
    }

}
