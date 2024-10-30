package it.unibo.gestione_concessionario.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import it.unibo.gestione_concessionario.commons.dto.Auto;
import it.unibo.gestione_concessionario.commons.dto.Cliente;
import it.unibo.gestione_concessionario.commons.dto.Dipendente;
import it.unibo.gestione_concessionario.commons.dto.Marchio;
import it.unibo.gestione_concessionario.commons.dto.Modello;
import it.unibo.gestione_concessionario.commons.dto.Tipologia;

public class ModelCliente extends Model {

    private Cliente cliente;

    public void init(Connection connection) {
        this.connection = connection;
    }

    public void stop() throws SQLException {
        connection.close();
    }

    public boolean creaCliente(Cliente cliente) throws SQLException {
        PreparedStatement ps = null;
        final String creaCliente = "INSERT INTO CLIENTE (nome, cognome, telefono, e_mail, password) VALUES ( ?, ?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(creaCliente);
            ps.setString(1, cliente.nome());
            ps.setString(2, cliente.cognome());
            ps.setString(3, cliente.telefono());
            ps.setString(4, cliente.eMail());
            ps.setString(5, cliente.password());

            ps.executeUpdate();
            ps.close();
            connection.commit();
            return true;
        } catch (SQLException e) {
            try {
                connection.rollback();
                throw e;
            } catch (SQLException e1) {
                throw e;
            }
        }

    }

    public List<Marchio> visualizzaMarchi() {
        PreparedStatement ps;
        List<Marchio> marchi = new ArrayList<>();
        final String vediMarchi = "SELECT ID_MARCHIO, Nome " +
                "FROM MARCHIO;";
        try {
            ps = connection.prepareStatement(vediMarchi);
            ResultSet set = ps.executeQuery();
            while (set.next()) {
                marchi.add(new Marchio(set.getInt(1), set.getString(2)));
            }
            ps.close();
            return marchi;
        } catch (SQLException e) {
            throw new ProblemWithConnectionException(e);
        }
    }

    public List<Tipologia> visualizzTipologie() {
        PreparedStatement ps;
        List<Tipologia> tipologie = new ArrayList<>();
        final String vediMarchi = "SELECT Nome, caratteristiche " +
                "FROM TIPOLOGIA;";
        try {
            ps = connection.prepareStatement(vediMarchi);
            ResultSet set = ps.executeQuery();
            while (set.next()) {
                tipologie.add(new Tipologia(set.getString(1), set.getString(2)));
            }
            ps.close();
            return tipologie;
        } catch (SQLException e) {
            throw new ProblemWithConnectionException(e);
        }

    }

    public List<Auto> visualizzaAutoxMarchioxTipologia(Marchio marchio, Tipologia tipologia) {
        PreparedStatement ps;
        List<Auto> auto = new ArrayList<>();
        final String vediAuto = "SELECT A.Numero_Telaio, A.prezzo , A.Immatricolazione, A.targa, A.data, M.Descrizione AS Modello, T.nome AS Tipologia "
                +
                "FROM AUTO A " +
                "JOIN CONFIGURAZIONE C ON A.ID_Configurazione = C.ID_Configurazione " +
                "JOIN MODELLO M ON C.ID_MODELLO = M.ID_MODELLO " +
                "JOIN TIPOLOGIA T ON M.ID_TIPOLOGIA = T.ID_TIPOLOGIA " +
                "WHERE M.ID_MARCHIO = ? " +
                "AND T.nome = ?;";

        try {
            ps = connection.prepareStatement(vediAuto);
            ps.setInt(1, marchio.idMarchio());
            ps.setString(2, tipologia.nome());
            ResultSet set = ps.executeQuery();
            while (set.next()) {
                if (set.getBoolean(3)) {
                    auto.add(new Auto(set.getString(1), set.getDouble(2), set.getBoolean(3),
                            Optional.of(set.getString(4)),
                            Optional.of(set.getDate(5).toLocalDate()), set.getString(6), "", ""));
                } else {
                    auto.add(new Auto(set.getString(1), set.getDouble(2), set.getBoolean(3),
                            Optional.of("non immatricolata"),
                            Optional.empty(), set.getString(6), "", ""));
                }
            }
            ps.close();
            return auto;
        } catch (SQLException e) {
            throw new ProblemWithConnectionException(e);
        }
    }

    public Dipendente visualizzaDipendente(Marchio marchio) throws IllegalArgumentException {
        PreparedStatement ps;
        Dipendente dipendente = null;
        final String vediDipendente = "SELECT  M.ID_MARCHIO , D.nome, D.cognome, D.telefono,D.responsabile, D.e_mail, M.Nome  AS Marchio "
                +
                "FROM DIPENDENTE D " +
                "JOIN MARCHIO M ON D.ID_MARCHIO = M.ID_MARCHIO " +
                "WHERE M.ID_MARCHIO = ?;";
        try {
            ps = connection.prepareStatement(vediDipendente);
            ps.setInt(1, marchio.idMarchio());
            ResultSet set = ps.executeQuery();
            while (set.next()) {
                dipendente = new Dipendente(set.getInt(1), set.getString(2), set.getString(3), set.getString(4),
                        set.getBoolean(5),
                        set.getString(6));
            }
            ps.close();
            if (dipendente != null) {
                return dipendente;
            } else {
                throw new IllegalArgumentException("Nessun dipendente associato a questo marchio");
            }
        } catch (SQLException e) {
            throw new ProblemWithConnectionException(e);
        }
    }

    public Dipendente visualizzaDipendente(Modello modello) {
        PreparedStatement ps;
        Dipendente dipendente = null;
        final String vediDipendente = "SELECT ID_MARCHIO, nome, cognome, telefono, responsabile, e_mail " +
                "FROM DIPENDENTE " +
                "WHERE ID_MARCHIO = ? ";
        try {
            ps = connection.prepareStatement(vediDipendente);

            int ID_MARCHIO_MODELLO = ID_Marchio(modello);
            ps.setInt(1, ID_MARCHIO_MODELLO);

            ResultSet set = ps.executeQuery();
            if (set.next()) {
                dipendente = new Dipendente(
                        set.getInt(1),
                        set.getString(2),
                        set.getString(3),
                        set.getString(4),
                        set.getBoolean(5),
                        set.getString(6));
            }
            ps.close();
            return dipendente;
        } catch (SQLException e) {
            throw new ProblemWithConnectionException(e);
        }
    }

    public int ID_Marchio(Modello modello) {
        PreparedStatement ps;
        final String ID_MARCHIOfromNomeModello = "SELECT m.ID_MARCHIO " +
                "FROM MODELLO mo " +
                "JOIN MARCHIO m ON mo.ID_MARCHIO = m.ID_MARCHIO " +
                "WHERE mo.Descrizione = ?;";
        try {
            ps = connection.prepareStatement(ID_MARCHIOfromNomeModello);
            ps.setString(1, modello.descrizione());
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                return res.getInt(1);
            } else
                throw new SQLException();
        } catch (SQLException e) {
            throw new ProblemWithConnectionException(e);
        }
    }

    public boolean checkLoginCliente(String email, String password) {
        PreparedStatement ps = null;
        final String login = "SELECT * " +
                "FROM CLIENTE c " +
                "WHERE c.e_mail = ? " +
                "AND c.password = ?;";
        try {
            ps = connection.prepareStatement(login);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet set = ps.executeQuery();
            if (set.next()) {
                cliente = new Cliente(set.getString(2), set.getString(3), set.getString(4), set.getString(5),
                        set.getString(6));
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new ProblemWithConnectionException(e);
        }

    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public List<Auto> visualizzaTutteLeAutoConDescrizioneModello() {
        PreparedStatement ps = null;
        List<Auto> autoList = new ArrayList<>();
        final String query = "SELECT A.Numero_Telaio, A.Prezzo, A.Immatricolazione, A.data, A.targa, M.Descrizione AS DescrizioneModello "
                +
                "FROM AUTO A " +
                "JOIN CONFIGURAZIONE C ON A.ID_Configurazione = C.ID_Configurazione " +
                "JOIN MODELLO M ON C.ID_MODELLO = M.ID_MODELLO;";

        try {
            ps = connection.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Auto auto = new Auto(
                        resultSet.getString("Numero_Telaio"),
                        resultSet.getDouble("Prezzo"),
                        resultSet.getBoolean("Immatricolazione"),
                        Optional.ofNullable(resultSet.getString("targa")),
                        Optional.ofNullable(
                                resultSet.getDate("data") != null ? resultSet.getDate("data").toLocalDate() : null),
                        resultSet.getString("DescrizioneModello"),
                        "",
                        "");
                autoList.add(auto);
            }

            ps.close();
        } catch (SQLException e) {
            throw new ProblemWithConnectionException(e);
        }

        return autoList;
    }
}