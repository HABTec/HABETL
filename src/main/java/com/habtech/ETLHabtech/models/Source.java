package com.habtech.ETLHabtech.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name="source")
public class Source {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="name",nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "source_type",nullable = false)
    private SourceType sourceType;
    private String username;
    private String password;
    private String host;

    @Column(name = "db")
    private String database;

    @Column(name = "source_table")
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

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public SourceType getSourceType() {
        return sourceType;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getHost() {
        return host;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public Source() {
    }


    public Source(long id, String name, SourceType sourceType, String username, String password, String host, String database) {
        this.id = id;
        this.name = name;
        this.sourceType = sourceType;
        this.username = username;
        this.password = password;
        this.host = host;
        this.database = database;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSourceType(SourceType sourceType) {
        this.sourceType = sourceType;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
