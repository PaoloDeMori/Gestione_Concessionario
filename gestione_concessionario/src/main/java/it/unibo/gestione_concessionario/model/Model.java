package it.unibo.gestione_concessionario.model;

import java.sql.Connection;

import it.unibo.gestione_concessionario.commons.dto.Appuntamento;
import it.unibo.gestione_concessionario.commons.dto.Auto;
import it.unibo.gestione_concessionario.commons.dto.Garanzia;
import it.unibo.gestione_concessionario.commons.dto.Marchio;
import it.unibo.gestione_concessionario.commons.dto.Modello;
import it.unibo.gestione_concessionario.commons.dto.Optionals;

import java.util.List;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.util.Optional;
import java.sql.SQLException;

public abstract class Model {

    protected Connection connection;

    public abstract void init(Connection connection);

    public abstract void stop() throws SQLException;

    public List<Auto> visualizzaAutoScontate(Marchio marchio) {
        PreparedStatement ps;
        List<Auto> auto = new ArrayList<>();
        final String vediAuto = "SELECT " +
                "A.Numero_Telaio, " +
                "A.Prezzo, " +
                "A.Immatricolazione, A.data, A.targa, " +
                "M.Descrizione AS Modello, " +
                "MR.Nome AS Marchio, " +
                "COALESCE(O.Percentuale, 0) AS Offerta_Percentuale, " +
                "COALESCE(S.Percentuale, 0) AS Sconto_Percentuale " +
                "FROM " +
                "AUTO A " +
                "JOIN CONFIGURAZIONE C ON A.ID_Configurazione = C.ID_Configurazione " +
                "JOIN MODELLO M ON C.ID_MODELLO = M.ID_MODELLO " +
                "JOIN MARCHIO MR ON M.ID_MARCHIO = MR.ID_MARCHIO " +
                "LEFT JOIN OFFERTA O ON MR.ID_MARCHIO = O.ID_MARCHIO " +
                "AND CURRENT_DATE BETWEEN O.Data_Inizio AND O.Data_Fine " +
                "LEFT JOIN SCONTO S ON A.Numero_Telaio = S.Numero_Telaio " +
                "AND CURRENT_DATE BETWEEN S.Data_Inizio AND S.Data_Fine " +
                "LEFT JOIN VENDITA V ON A.Numero_Telaio = V.Numero_Telaio " +
                "WHERE " +
                "MR.ID_MARCHIO = ? " +
                "AND V.Numero_Telaio IS NULL";
        try {
            ps = connection.prepareStatement(vediAuto);
            ps.setInt(1, marchio.idMarchio());
            ResultSet set = ps.executeQuery();
            while (set.next()) {
                double prezzoOriginale = set.getDouble(2);
                int percentualeSconto = set.getInt(9);
                double importoSconto = (prezzoOriginale * percentualeSconto) / 100;
                double prezzoScontato = prezzoOriginale - importoSconto;

                int percentualeOfferta = set.getInt(8);
                double importoOfferta = (prezzoOriginale * percentualeOfferta) / 100;
                double prezzoOfferta = prezzoOriginale - importoOfferta;

                if (percentualeSconto > 0) {
                    auto.add(
                            new Auto(set.getString(1), prezzoScontato, set.getBoolean(3), set.getString(6),Optional.of(set.getString(5)),Optional.of(set.getDate(4).toLocalDate())));
                }
                if (percentualeOfferta > 0) {
                    auto.add(
                        new Auto(set.getString(1), prezzoOfferta, set.getBoolean(3), set.getString(6),Optional.of(set.getString(5)),Optional.of(set.getDate(4).toLocalDate())));
                }
            }
            ps.close();
            return auto;
        } catch (SQLException e) {
            throw new ProblemWithConnectionException(e);
        }
    }

    public List<Modello> visualizzaModello() {
        PreparedStatement ps;
        List<Modello> modello = new ArrayList<>();
        final String vediModello = "SELECT m.ID_MODELLO, m.Descrizione, m.Anno,T.nome, ma.nome " +
                "FROM MODELLO m " +
                "JOIN TIPOLOGIA T ON T.ID_TIPOLOGIA=m.ID_TIPOLOGIA " +
                "JOIN MARCHIO ma ON ma.ID_MARCHIO=m.ID_MARCHIO;";
        try {
            ps = connection.prepareStatement(vediModello);
            ResultSet set = ps.executeQuery();
            while (set.next()) {
                modello.add(new Modello(set.getInt(1), set.getString(2), set.getInt(3), set.getString(4),
                        set.getString(5)));
            }
            ps.close();
            return modello;
        } catch (SQLException e) {
            throw new ProblemWithConnectionException(e);
        }
    }

    public List<Auto> visualizzaAutoxModello(Modello modello) {
        PreparedStatement ps;
        List<Auto> auto = new ArrayList<>();
        final String vediAuto = "SELECT A.Numero_Telaio, A.prezzo , A.Immatricolazione, A.targa, A.data, M.Descrizione AS Modello, T.nome AS Tipologia "
                +
                "FROM AUTO A " +
                "JOIN CONFIGURAZIONE C ON A.ID_Configurazione = C.ID_Configurazione " +
                "JOIN MODELLO M ON C.ID_MODELLO = M.ID_MODELLO " +
                "JOIN TIPOLOGIA T ON M.ID_TIPOLOGIA = T.ID_TIPOLOGIA " +
                "WHERE M.Descrizione = ?";
        try {
            ps = connection.prepareStatement(vediAuto);
            ps.setString(1, modello.descrizione());
            ResultSet set = ps.executeQuery();
            while (set.next()) {
                auto.add(new Auto(set.getString(1), set.getDouble(2), set.getBoolean(3), Optional.of(set.getString(4)),
                        Optional.of(set.getDate(5).toLocalDate()), set.getString(6), "", ""));
            }
            ps.close();
            return auto;
        } catch (SQLException e) {
            throw new ProblemWithConnectionException(e);
        }
    }

    public List<Auto> visualizzaAutoxModelloNonVendute(Modello modello) {
        PreparedStatement ps;
        List<Auto> auto = new ArrayList<>();
        final String vediAuto = "SELECT A.Numero_Telaio, A.prezzo , A.Immatricolazione, A.targa, A.data, M.Descrizione AS Modello, T.nome AS Tipologia "
                +
                "FROM AUTO A " +
                "JOIN CONFIGURAZIONE C ON A.ID_Configurazione = C.ID_Configurazione " +
                "JOIN MODELLO M ON C.ID_MODELLO = M.ID_MODELLO " +
                "JOIN TIPOLOGIA T ON M.ID_TIPOLOGIA = T.ID_TIPOLOGIA " +
                "WHERE M.Descrizione = ? " +
                "AND A.Numero_Telaio NOT IN (SELECT Numero_Telaio FROM VENDITA);";
        try {
            ps = connection.prepareStatement(vediAuto);
            ps.setString(1, modello.descrizione());
            ResultSet set = ps.executeQuery();
            while (set.next()) {
                auto.add(new Auto(set.getString(1), set.getDouble(2), set.getBoolean(3), Optional.of(set.getString(4)),
                        Optional.of(set.getDate(5).toLocalDate()), set.getString(6), "", ""));
            }
            ps.close();
            return auto;
        } catch (SQLException e) {
            throw new ProblemWithConnectionException(e);
        }
    }

    public int getDipendenteIDByEmail(String email) {
        PreparedStatement ps = null;
        final String query = "SELECT ID_DIPENDENTE FROM DIPENDENTE WHERE e_mail = ?;";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            ResultSet set = ps.executeQuery();
            if (set.next()) {
                return set.getInt(1);
            } else {
                throw new SQLException("Nessun dipendente trovato con l'email fornita.");
            }
        } catch (SQLException e) {
            throw new ProblemWithConnectionException(e);
        } finally {
            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException e) {
                throw new ProblemWithConnectionException(e);
            }
        }
    }

    public String getDipendenteNameById(int iD) {
        PreparedStatement ps = null;
        final String query = "SELECT d.nome,d.cognome FROM DIPENDENTE d WHERE ID_DIPENDENTE = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, iD);
            ResultSet set = ps.executeQuery();
            if (set.next()) {
                return set.getString(1) + "" + set.getString(2);
            } else {
                throw new SQLException("Nessun cliente trovato con l'id fornito.");
            }
        } catch (SQLException e) {
            throw new ProblemWithConnectionException(e);
        } finally {
            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException e) {
                throw new ProblemWithConnectionException(e);
            }
        }
    }

    public String getClienteNameById(int iD) {
        PreparedStatement ps = null;
        final String query = "SELECT cl.nome,cl.cognome FROM CLIENTE cl WHERE ID_CLIENTE = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, iD);
            ResultSet set = ps.executeQuery();
            if (set.next()) {
                return set.getString(1) + "" + set.getString(2);
            } else {
                throw new SQLException("Nessun cliente trovato con l'id fornito.");
            }
        } catch (SQLException e) {
            throw new ProblemWithConnectionException(e);
        } finally {
            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException e) {
                throw new ProblemWithConnectionException(e);
            }
        }
    }

    public int getClienteIDByEmail(String email) {
        PreparedStatement ps = null;
        final String query = "SELECT ID_CLIENTE FROM CLIENTE WHERE e_mail = ?;";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            ResultSet set = ps.executeQuery();
            if (set.next()) {
                return set.getInt(1);
            } else {
                throw new SQLException("Nessun cliente trovato con l'email fornita.");
            }
        } catch (SQLException e) {
            throw new ProblemWithConnectionException(e);
        } finally {
            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException e) {
                throw new ProblemWithConnectionException(e);
            }
        }
    }

    public boolean fissaAppuntamento(Appuntamento appuntamento) throws SQLException {
        PreparedStatement ps = null;
        final String fissaAppuntamento = "INSERT INTO APPUNTAMENTO (data, ora, Tipologia, durata, Numero_Telaio, ID_DIPENDENTE, ID_CLIENTE) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(fissaAppuntamento);
            ps.setDate(1, Date.valueOf(appuntamento.data()));
            ps.setTime(2, java.sql.Time.valueOf(appuntamento.ora()));
            ps.setString(3, appuntamento.tipologia());
            ps.setTime(4, java.sql.Time.valueOf(appuntamento.durata()));
            ps.setString(5, appuntamento.numero_telaio());
            ps.setInt(6, appuntamento.id_dipendente());
            ps.setInt(7, appuntamento.id_cliente());

            ps.executeUpdate();
            ps.close();
            connection.commit();
            return true;
        } catch (SQLException e) {
            try {
                connection.rollback();
                throw e;
            } catch (SQLException e1) {
                throw new SQLException(e1);
            }
        }
    }

    public List<Optionals> visualizzaOptional(String numeroTelaio) {
        PreparedStatement ps;
        List<Optionals> optional = new ArrayList<>();
        final String vediOptional = "SELECT O.ID_Optional, O.descrizione, O.prezzo " +
                "FROM AUTO A " +
                "JOIN Personalizzazione P ON A.Numero_Telaio = P.Numero_Telaio " +
                "JOIN OPTIONAL O ON P.ID_Optional = O.ID_Optional " +
                "WHERE A.Numero_Telaio = ?;";
        try {
            ps = connection.prepareStatement(vediOptional);
            ps.setString(1, numeroTelaio);
            ResultSet set = ps.executeQuery();
            while (set.next()) {
                optional.add(new Optionals(set.getInt(1), set.getString(2), set.getDouble(3)));
            }
            ps.close();
            return optional;
        } catch (SQLException e) {
            throw new ProblemWithConnectionException(e);
        }

    }

    public List<Optionals> visualizzaAllOptional() {
        PreparedStatement ps;
        List<Optionals> optional = new ArrayList<>();
        final String vediOptional = "SELECT ID_Optional, descrizione, prezzo FROM OPTIONAL;";
        try {
            ps = connection.prepareStatement(vediOptional);
            ResultSet set = ps.executeQuery();
            while (set.next()) {
                optional.add(new Optionals(set.getInt(1), set.getString(2), set.getDouble(3)));
            }
            ps.close();
            return optional;
        } catch (SQLException e) {
            throw new ProblemWithConnectionException(e);
        }

    }

    public Optional<Garanzia> visualizzaGaranzia(String numeroTelaio) {
        PreparedStatement ps;
        Garanzia garanzia = null;
        final String vediGaranzia = "SELECT ID_Garanzia, scadenza, copertura, Numero_Telaio " +
                "FROM GARANZIA " +
                "WHERE Numero_Telaio = ?;";
        try {
            ps = connection.prepareStatement(vediGaranzia);
            ps.setString(1, numeroTelaio);
            ResultSet set = ps.executeQuery();
            while (set.next()) {
                garanzia = new Garanzia(set.getInt(1), set.getDate(2).toLocalDate(), set.getString(3),
                        set.getString(4));
            }
            ps.close();
            if (garanzia != null) {
                return Optional.of(garanzia);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new ProblemWithConnectionException(e);
        }
    }

}
