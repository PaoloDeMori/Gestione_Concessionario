package it.unibo.gestione_concessionario.model;

import java.sql.Connection;

public interface Model {

    void init(Connection connection);

    void stop();

}
