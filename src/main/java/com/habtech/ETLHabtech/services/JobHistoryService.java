package com.habtech.ETLHabtech.services;

import com.habtech.ETLHabtech.data.JobHistoryRepository;
import com.habtech.ETLHabtech.models.JobHistory;
import org.springframework.stereotype.Service;

@Service
public class JobHistoryService {
    private final JobHistoryRepository jobHistoryRepository;

    public JobHistoryService(JobHistoryRepository jobHistoryRepository) {
        this.jobHistoryRepository = jobHistoryRepository;
    }

    public void save(JobHistory jobHistory){
        jobHistoryRepository.save(jobHistory);
    }
}
