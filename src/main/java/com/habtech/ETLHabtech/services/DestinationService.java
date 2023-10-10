package com.habtech.ETLHabtech.services;

import com.habtech.ETLHabtech.data.DestinationRepository;
import com.habtech.ETLHabtech.models.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinationService {

    final private DestinationRepository destinationRepository;

    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }
    public List<Destination> getAllDestinations(){
        return destinationRepository.findAll();
    }

    public Destination createDestination(Destination destination){
        return destinationRepository.save(destination);
    }

    public Destination getById(long destinationId) {
        return destinationRepository.getReferenceById(destinationId);
    }
}
