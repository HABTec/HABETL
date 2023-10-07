package com.habtech.ETLHabtech.data;

import com.habtech.ETLHabtech.models.TableColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableColumnRepository extends JpaRepository<TableColumn,Long> {
}
