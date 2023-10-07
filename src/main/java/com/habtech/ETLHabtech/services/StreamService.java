package com.habtech.ETLHabtech.services;

import com.habtech.ETLHabtech.data.StreamRepository;
import com.habtech.ETLHabtech.models.Stream;
import org.springframework.stereotype.Service;

@Service
public class StreamService {
    private final StreamRepository streamRepository;

    public StreamService(StreamRepository streamRepository) {
        this.streamRepository = streamRepository;
    }

    public void save(Stream stream) {
        streamRepository.save(stream);
    }

    public void delete(Long id) {
        streamRepository.deleteById(id);
    }

    public Stream getById(long id) {
        return streamRepository.getReferenceById(id);
    }
}
