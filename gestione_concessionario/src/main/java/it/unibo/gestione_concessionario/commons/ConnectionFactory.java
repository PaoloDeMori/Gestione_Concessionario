package it.unibo.gestione_concessionario.commons;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public final class  ConnectionFactory {

    private ConnectionFactory(){}

    public static final Connection build(String db, String dbLocation , String name, String Password){
        String dbAddress = dbLocation+db;

        try{
            Connection connection= DriverManager.getConnection(dbAddress,name,Password);
            if(connection==null){
                throw new SQLException();
            }
            else{
                return connection;
            }
        }
        catch(SQLException e){
            throw new ImpossibleToConnectException(e);
        }
    }
    
}
