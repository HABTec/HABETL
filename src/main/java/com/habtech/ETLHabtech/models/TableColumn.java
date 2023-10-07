package com.habtech.ETLHabtech.models;

import jakarta.persistence.*;

@Entity
@Table(name = "table_column")
public class TableColumn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "target_name", nullable = false)
    private String targetName;

    @Column(name = "is_disabled")
    private Boolean isDisabled = false;

    @Column(name = "data_type")
    private String dataType;

    @Column(name = "path")
    private String path;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "stream_id", referencedColumnName = "id")
    private Stream stream;

    public Stream getStream() {
        return stream;
    }

    public void setStream(Stream stream) {
        this.stream = stream;
    }

    public TableColumn() {
    }

    public TableColumn(long id, String name, String targetName, Boolean isDisabled, String dataType, String path, Stream stream) {
        this.id = id;
        this.name = name;
        this.targetName = targetName;
        this.isDisabled = isDisabled;
        this.dataType = dataType;
        this.path = path;
        this.stream = stream;
    }

    public TableColumn(String name, String targetName, Boolean isDisabled, String dataType, String path, Stream stream) {
        this.name = name;
        this.targetName = targetName;
        this.isDisabled = isDisabled;
        this.dataType = dataType;
        this.path = path;
        this.stream = stream;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public Boolean getDisabled() {
        return isDisabled;
    }

    public void setDisabled(Boolean disabled) {
        isDisabled = disabled;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "TableColumn{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", targetName='" + targetName + '\'' +
                ", isDisabled=" + isDisabled +
                ", dataType='" + dataType + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
