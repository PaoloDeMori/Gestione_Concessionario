package it.unibo.gestione_concessionario.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface Model {

    void init(Connection connection);

    void stop();

}
