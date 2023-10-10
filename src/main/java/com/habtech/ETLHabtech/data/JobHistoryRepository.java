package com.habtech.ETLHabtech.data;

import com.habtech.ETLHabtech.models.JobHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobHistoryRepository extends JpaRepository<JobHistory, Long> {
}
