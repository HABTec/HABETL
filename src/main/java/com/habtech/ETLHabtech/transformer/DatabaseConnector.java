package com.habtech.ETLHabtech.transformer;

import com.habtech.ETLHabtech.models.Destination;
import com.habtech.ETLHabtech.models.DestinationType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static Connection con;

    public static Connection getConnection(DestinationType type, String host,int port, String username, String password, String database) throws Exception {
        return getConnection(type, host, port, username, password, database,false);
    }
    public static Connection getConnection(DestinationType type, String host,int port, String username, String password, String database,boolean reconnect) throws Exception {

        if(con!=null && !reconnect ){
            return con;
        }else{
            String connectionURI = getConnectionURI(type,host,port,database);
            con = DriverManager.getConnection(connectionURI,username,password);
        }
        return con;
    }

    private static String getConnectionURI(DestinationType type, String host,int port, String database) throws Exception {
        switch (type){
            case POSTGRES -> {
                return "jdbc:postgresql://"+host+":"+port+"/"+database;
            }
        }
        throw new Exception("Error: Unsupported destination type!");
    }

    public static Connection getConnection(Destination destination) throws Exception {
        return getConnection(destination,false);
    }

    private static Connection getConnection(Destination destination, boolean reconnect) throws Exception {
        return getConnection(destination.getDestinationType(),destination.getHost(),destination.getPort(),destination.getUsername(), destination.getPassword(), destination.getDatabase(),reconnect);
    }
}
