package com.habtech.ETLHabtech.services;

import com.habtech.ETLHabtech.data.DestinationRepository;
import com.habtech.ETLHabtech.models.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public class DestinationService {

    private DestinationRepository destinationRepository;

    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    public List<Destination> getAllDestinations(){
        return destinationRepository.findAll();
    }
}
