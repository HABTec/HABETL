package com.habtech.ETLHabtech.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "connection")
public class Connection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="name")
    private String name;

    @Column(name="last_sync")
    private Instant lastSync;

    @Column(name="is_enabled")
    private Boolean isEnabled;

    @Column(name="created_at")
    @CreationTimestamp
    private Instant createdAt;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="source_id",referencedColumnName = "id", nullable = false)
    private Source source;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "destination_id", referencedColumnName = "id",nullable = false)
    private Destination destination;

    public Connection(String name, Source source, Destination destination) {
        this.name = name;
        this.source = source;
        this.destination = destination;
        this.isEnabled= true;
    }

    public Connection(String name, Boolean isEnabled, Source source, Destination destination) {
        this.name = name;
        this.isEnabled = isEnabled;
        this.source = source;
        this.destination = destination;
    }

    public Connection(String name, Instant lastSync, Boolean isEnabled, Source source, Destination destination) {
        this.name = name;
        this.lastSync = lastSync;
        this.isEnabled = isEnabled;
        this.source = source;
        this.destination = destination;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public Connection() {
    }

    public Instant getLastSync() {
        return lastSync;
    }

    public void setLastSync(Instant lastSync) {
        this.lastSync = lastSync;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }
}
