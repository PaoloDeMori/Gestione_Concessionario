package it.unibo.gestione_concessionario.commons;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public final class  ConnectionFactory {

    private ConnectionFactory(){}

    public static final Connection build(String db, String dbLocation , String name, String password){
        String dbAddress = dbLocation+db;

        try{
            Connection connection= DriverManager.getConnection(dbAddress,name,password);
            if(connection==null){
                throw new SQLException();
            }
            else{
                connection.setAutoCommit(false);
                return connection;
            }
        }
        catch(SQLException e){
            throw new ImpossibleToConnectException(e);
        }
    }
    
}
