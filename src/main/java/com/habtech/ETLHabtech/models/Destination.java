package com.habtech.ETLHabtech.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "destination")
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "destination_type",nullable = false)
    private DestinationType destinationType;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "host")
    private String host;
    @Column(name = "db")
    private String database;

    @Column(name = "destination_table")
    private String table;

    @Column(name="created_at")
    @CreationTimestamp
    private Instant createdAt;

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Destination() {
    }

    public Destination(long id, String name, DestinationType destinationType, String username, String password, String host, String database, String table) {
        this.id = id;
        this.name = name;
        this.destinationType = destinationType;
        this.username = username;
        this.password = password;
        this.host = host;
        this.database = database;
        this.table = table;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public DestinationType getDestinationType() {
        return destinationType;
    }

    public void setDestinationType(DestinationType destinationType) {
        this.destinationType = destinationType;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public Destination(long id, String name, DestinationType destinationType, String username, String password, String host, String database) {
        this.id = id;
        this.name = name;
        this.destinationType = destinationType;
        this.username = username;
        this.password = password;
        this.host = host;
        this.database = database;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
