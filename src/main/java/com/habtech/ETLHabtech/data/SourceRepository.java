package com.habtech.ETLHabtech.data;

import com.habtech.ETLHabtech.models.Source;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SourceRepository extends JpaRepository<Source,Long> {
}
