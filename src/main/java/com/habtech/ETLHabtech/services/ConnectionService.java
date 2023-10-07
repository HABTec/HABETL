package com.habtech.ETLHabtech.services;

import com.habtech.ETLHabtech.data.ConnectionRepository;
import com.habtech.ETLHabtech.models.Connection;
import com.habtech.ETLHabtech.models.Destination;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectionService {
    final private ConnectionRepository connectionRepository;

    public ConnectionService(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }

    public List<Connection> getAllConnection(){
        return connectionRepository.findAll();
    }

    public Connection createConnection(Connection connection){
        return connectionRepository.save(connection);
    }

    public Connection getById(Long id) {
        return connectionRepository.getReferenceById(id);
    }
}
