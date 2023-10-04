package com.habtech.ETLHabtech.data;

import com.habtech.ETLHabtech.models.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceRepository extends JpaRepository<Source,Long> {
}
