package com.habtech.ETLHabtech.data;

import com.habtech.ETLHabtech.models.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DestinationRepository extends JpaRepository<Destination, Long> {
}
