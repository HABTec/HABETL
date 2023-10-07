package com.habtech.ETLHabtech.models;

import com.habtech.ETLHabtech.services.ConnectionService;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "table_stream")
public class Stream {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "target_name")
    private String targetName;

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

    private String resourceURI;

    @Column(name = "result_path")
    private String resultPath;

    @Column(name = "result_object",length = Integer.MAX_VALUE)
    private String resultObject;

    public String getResultObject() {
        return resultObject;
    }

    public void setResultObject(String resultObject) {
        this.resultObject = resultObject;
    }

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "connection_id",referencedColumnName = "id")
    private Connection connection;


    @OneToMany(mappedBy = "stream",fetch = FetchType.EAGER,orphanRemoval = true,cascade = CascadeType.REMOVE)
    private List<TableColumn> tableColumns;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public String getResultPath() {
        return resultPath;
    }

    public void setResultPath(String resultPath) {
        this.resultPath = resultPath;
    }

    public List<TableColumn> getTableColumns() {
        return tableColumns;
    }

    public void setTableColumns(List<TableColumn> tableColumns) {
        this.tableColumns = tableColumns;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Stream() {
    }

    public Stream(long id, String name, String targetName, String resourceURI, String resultPath, Connection connection, List<TableColumn> tableColumns) {
        this.id = id;
        this.name = name;
        this.targetName = targetName;
        this.resourceURI = resourceURI;
        this.resultPath = resultPath;
        this.connection = connection;
        this.tableColumns = tableColumns;
    }

    public String getURL() {
        return connection.getSource().getHost()+resourceURI;
    }

    @Override
    public String toString() {
        return "Stream{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", targetName='" + targetName + '\'' +
                ", resourceURI='" + resourceURI + '\'' +
                ", resultPath='" + resultPath + '\'' +
                ", resultObject='" + resultObject + '\'' +
                ", connection=" + connection +
                ", tableColumns=" + tableColumns +
                '}';
    }
}
