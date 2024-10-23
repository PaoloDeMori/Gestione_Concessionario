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

    public abstract void stop();

    public List<Auto> visualizzaAutoScontate(Marchio marchio) {
        PreparedStatement ps;
        List<Auto> auto = new ArrayList<>();
        final String vediAuto = "SELECT " +
                "A.Numero_Telaio, " +
                "A.Prezzo, " +
                "A.Immatricolazione, " +
                "A.Targa, " +
                "A.Data, " +
                "M.Descrizione AS Modello, " +
                "MR.Nome AS Marchio, " +
                "COALESCE(O.Percentuale, 1) AS Offerta_Percentuale, " +
                "COALESCE(S.Percentuale, 1) AS Sconto_Percentuale " +
                "FROM " +
                "AUTO A " +
                "JOIN CONFIGURAZIONE C ON A.ID_Configurazione = C.ID_Configurazione " +
                "JOIN MODELLO M ON C.ID_MODELLO = M.ID_MODELLO " +
                "JOIN MARCHIO MR ON M.ID_MARCHIO = MR.ID_MARCHIO " +
                "LEFT JOIN OFFERTA O ON MR.ID_MARCHIO = O.ID_MARCHIO " +
                "AND CURRENT_DATE BETWEEN O.Data_Inizio AND O.Data_Fine " +
                "LEFT JOIN SCONTO S ON A.Numero_Telaio = S.Numero_Telaio " +
                "AND CURRENT_DATE BETWEEN S.Data_Inizio AND S.Data_Fine " +
                "LEFT JOIN VENDITA V ON A.Numero_Telaio = V.Numero_Telaio " + // Aggiungiamo il LEFT JOIN con VENDITA
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
                if (set.getBoolean(3)) {
                    auto.add(
                            new Auto(set.getString(1), prezzoScontato, set.getBoolean(3), Optional.of(set.getString(4)),
                                    Optional.of(set.getDate(5).toLocalDate()), set.getString(6), "", ""));
                } else {
                    auto.add(new Auto(set.getString(1), prezzoScontato, set.getBoolean(3),
                            Optional.of("Non immatricolata"),
                            Optional.empty(), set.getString(6), "", ""));
                }
            }
            for (var a : auto) {
                System.out.println(a.toString());
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
            for (var m : modello) {
                System.out.println(m.toString());
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
            for (var a : auto) {
                System.out.println(a.toString());
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
            for (var a : auto) {
                System.out.println(a.toString());
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
                return set.getInt(1); // Restituisce l'ID del dipendente
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
                e.printStackTrace();
            }
        }
    }

    public String getDipendenteNameById(int Id) {
        PreparedStatement ps = null;
        final String query = "SELECT d.nome,d.cognome FROM DIPENDENTE d WHERE ID_DIPENDENTE = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, Id);
            ResultSet set = ps.executeQuery();
            if (set.next()) {
                String nomeCliente=set.getString(1) + "" + set.getString(2);
                return nomeCliente; // Restituisce l'ID del dipendente
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
                e.printStackTrace();
            }
        }
    }

    
    public String getClienteNameById(int Id) {
        PreparedStatement ps = null;
        final String query = "SELECT cl.nome,cl.cognome FROM CLIENTE cl WHERE ID_CLIENTE = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, Id);
            ResultSet set = ps.executeQuery();
            if (set.next()) {
                String nomeCliente=set.getString(1) + "" + set.getString(2);
                return nomeCliente; // Restituisce l'ID del dipendente
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
                e.printStackTrace();
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
                return set.getInt(1); // Restituisce l'ID del cliente
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
                e.printStackTrace();
            }
        }
    }

        public boolean fissaAppuntamento(Appuntamento appuntamento) {
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
                System.out.println(e.getMessage());
            } catch (SQLException e1) {
                e1.printStackTrace();
                throw new ProblemWithConnectionException(e1);
            }
            return false;
        }
    }

        public List<Optionals> visualizzaOptional(String numero_Telaio) {
        PreparedStatement ps;
        List<Optionals> optional = new ArrayList<>();
        final String vediOptional = "SELECT O.ID_Optional, O.descrizione, O.prezzo " +
                "FROM AUTO A " +
                "JOIN CONFIGURAZIONE C ON A.ID_Configurazione = C.ID_Configurazione " +
                "JOIN MODELLO M ON C.ID_MODELLO = M.ID_MODELLO " +
                "JOIN Supporto S ON M.ID_MODELLO = S.ID_MODELLO " +
                "JOIN OPTIONAL O ON S.ID_Optional = O.ID_Optional " +
                "WHERE A.Numero_Telaio = ?;";
        try {
            ps = connection.prepareStatement(vediOptional);
            ps.setString(1, numero_Telaio);
            ResultSet set = ps.executeQuery();
            while (set.next()) {
                optional.add(new Optionals(set.getInt(1), set.getString(2), set.getDouble(3)));
            }
            for (var o : optional) {
                System.out.println(o.toString());
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
        final String vediOptional = "SELECT * FROM OPTIONAL;";
        try {
            ps = connection.prepareStatement(vediOptional);
            ResultSet set = ps.executeQuery();
            while (set.next()) {
                optional.add(new Optionals(set.getInt(1), set.getString(2), set.getDouble(3)));
            }
            for (var o : optional) {
                System.out.println(o.toString());
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
        final String vediGaranzia = "SELECT ID_Garanzia, scadenza, copertura " +
                "FROM GARANZIA " +
                "WHERE Numero_Telaio = ?;";
        try {
            ps = connection.prepareStatement(vediGaranzia);
            ps.setString(1, numeroTelaio);
            ResultSet set = ps.executeQuery();
            while (set.next()) {
                garanzia = new Garanzia(set.getInt(1), set.getString(2), set.getString(3));
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
