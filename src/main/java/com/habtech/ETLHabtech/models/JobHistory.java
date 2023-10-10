package com.habtech.ETLHabtech.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
public class JobHistory {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;

    @Column(name = "run_at")
    @CreationTimestamp
    private Instant run_at;

    private boolean isSuccessfullyCompleted;

    private String log;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "connection_id", referencedColumnName = "id")
    private Connection connection;


    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public JobHistory(Instant run_at, boolean isSuccessfullyCompleted, String log, Connection connection) {
        this.run_at = run_at;
        this.isSuccessfullyCompleted = isSuccessfullyCompleted;
        this.log = log;
        this.connection = connection;
    }

    public JobHistory(long id, Instant run_at, boolean isSuccessfullyCompleted, String log, Connection connection) {
        this.id = id;
        this.run_at = run_at;
        this.isSuccessfullyCompleted = isSuccessfullyCompleted;
        this.log = log;
        this.connection = connection;
    }

    public JobHistory() {
    }

    public JobHistory(Instant run_at, boolean isSuccessfullyCompleted, String log) {
        this.run_at = run_at;
        this.isSuccessfullyCompleted = isSuccessfullyCompleted;
        this.log = log;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Instant getRun_at() {
        return run_at;
    }

    public void setRun_at(Instant run_at) {
        this.run_at = run_at;
    }

    public boolean isSuccessfullyCompleted() {
        return isSuccessfullyCompleted;
    }

    public void setSuccessfullyCompleted(boolean successfullyCompleted) {
        this.isSuccessfullyCompleted = successfullyCompleted;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
}
