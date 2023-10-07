package com.habtech.ETLHabtech.services;

import com.habtech.ETLHabtech.data.TableColumnRepository;
import com.habtech.ETLHabtech.models.TableColumn;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TableColumnService {
    private final TableColumnRepository tableColumnRepository;

    public TableColumnService(TableColumnRepository tableColumnRepository) {
        this.tableColumnRepository = tableColumnRepository;
    }

    public void update(TableColumn tableColumn) {
        tableColumnRepository.save(tableColumn);
    }

    public void save(TableColumn column) {
        tableColumnRepository.save(column);
    }

    public void saveAll(ArrayList<TableColumn> tableColumns) {
        tableColumnRepository.saveAll(tableColumns);
    }
}
