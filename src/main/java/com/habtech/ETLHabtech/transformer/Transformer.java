package com.habtech.ETLHabtech.transformer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.habtech.ETLHabtech.models.*;
import com.habtech.ETLHabtech.models.Connection;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.sql.*;
import java.time.Instant;
import java.util.Iterator;

public class Transformer {
    Connection connection;

    public Transformer(){}

    public Transformer(Connection connection) {
        this.connection = connection;
    }

    public void transform() throws Exception {
        java.sql.Connection con = DatabaseConnector.getConnection(connection.getDestination());
        createTables(con);
        syncAll(con);
    }

    
    private void syncAll(java.sql.Connection con) throws Exception {
        for (Stream stream :
                connection.getStreams()) {
            sync(stream, con);
        }
    }
    private void sync(Stream stream, java.sql.Connection con) throws Exception {
        Source source = connection.getSource();
        PreparedStatement statement;

        //extract stream

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(source.getUsername(), source.getPassword());
        HttpEntity<HttpHeaders> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(stream.getURL(), HttpMethod.GET, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(response.getBody());
                JsonNode object = root.at(stream.getResultPath());

                if (object.isArray()) {
                    for (JsonNode obj :
                            object) {
                        //create insert statement
                        String persist_data = "INSERT INTO "+stream.getTargetName()+" ( ";
                        String persist_data_values = "created_at ) values ( ";
                        for (TableColumn column :
                                stream.getTableColumns()) {
                            if (!column.getDisabled()) {
                                persist_data+=column.getName()+", ";
                                persist_data_values+="?, ";
                            }
                        }

                        persist_data+=persist_data_values+" ? )";

                        statement = con.prepareStatement(persist_data);
                        int paramIndex = 1;
                        for (TableColumn column :
                                stream.getTableColumns()) {

                            if (!column.getDisabled()) {
                                String value;
                                if(column.getPath().equals("/")) {
                                    value = obj.get(column.getName()) != null ? obj.get(column.getName()).asText() : null;
                                }else {
                                    value = obj.at(column.getPath()) != null ? obj.at(column.getPath()).asText() : null;
                                }
                                statement.setString(paramIndex,value);
                                paramIndex++;
                            }
                        }
                        statement.setTimestamp(paramIndex, Timestamp.from(Instant.now()));
                        statement.executeUpdate();

                    }
                }else{
                    throw new Exception("Expecting array object given!");
                }

        }else{
            throw new Exception("Error: Status code not ok "+response.getStatusCode()+" \nFailed to load from stream using "+stream.getURL());
        }
        
    }


    public void createTables(java.sql.Connection con) throws SQLException {
        Statement statement = con.createStatement();
        for (Stream s:  connection.getStreams()) {
            if(doesTableExist(con,s.getTargetName())){
                truncateTable(statement,s);
            }else {
                createTable(statement,s);
            }
        }
    }

    private void truncateTable(Statement statement, Stream s) throws SQLException {
        statement.executeUpdate("TRUNCATE "+s.getTargetName());
    }

    private void createTable(Statement statement,Stream stream) throws SQLException {
        statement.executeUpdate(getSchemaSQL(stream));
    }

    private String getSchemaSQL(Stream stream) {
        String statement = "CREATE TABLE "+stream.getTargetName()+" (";

        for (TableColumn column :
                stream.getTableColumns()) {
                statement += "  "+column.getName()+" "+ column.getDataType()+", ";
        }
        statement+="created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP )";
        return statement;
    }

    private boolean doesTableExist(java.sql.Connection con,String table) throws SQLException {
        DatabaseMetaData dbmd = con.getMetaData();
        ResultSet result = dbmd.getTables(null,null,table,null);
        if(result.next())
            return true;
        return false;
    }

}
