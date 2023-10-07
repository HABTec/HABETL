package com.habtech.ETLHabtech.data;

import com.habtech.ETLHabtech.models.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StreamRepository extends JpaRepository<Stream,Long> {
}
