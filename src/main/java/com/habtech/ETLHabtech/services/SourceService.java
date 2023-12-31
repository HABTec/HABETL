package com.habtech.ETLHabtech.services;

import com.habtech.ETLHabtech.data.SourceRepository;
import com.habtech.ETLHabtech.models.Source;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SourceService {
    private final SourceRepository sourceRepository;

    public SourceService(SourceRepository sourceRepository) {
        this.sourceRepository = sourceRepository;
    }

    public List<Source> getAllSources(){
        return sourceRepository.findAll();
    }

    public void createSource(Source source) {
        sourceRepository.save(source);
    }

    public Source getById(long sourceId) {
        return sourceRepository.getReferenceById(sourceId);
    }
}
