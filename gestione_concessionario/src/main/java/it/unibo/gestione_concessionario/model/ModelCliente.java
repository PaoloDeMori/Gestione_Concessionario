package it.unibo.gestione_concessionario.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import it.unibo.gestione_concessionario.commons.ConnectionFactory;
import it.unibo.gestione_concessionario.commons.dto.Appuntamento;
import it.unibo.gestione_concessionario.commons.dto.Auto;
import it.unibo.gestione_concessionario.commons.dto.Cliente;
import it.unibo.gestione_concessionario.commons.dto.Dipendente;
import it.unibo.gestione_concessionario.commons.dto.Garanzia;
import it.unibo.gestione_concessionario.commons.dto.Marchio;
import it.unibo.gestione_concessionario.commons.dto.Modello;
import it.unibo.gestione_concessionario.commons.dto.Optionals;
import it.unibo.gestione_concessionario.commons.dto.Tipologia;

public class ModelCliente implements Model {

    private Connection connection;
    private Cliente cliente;

    public ModelCliente() {
    }

    public void init(Connection connection) {
        this.connection = connection;
    }

    public void stop() {
        try {
            connection.close();
        } catch (SQLException e) {

        }
    }

    public boolean creaCliente(Cliente cliente) {
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
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            return false;
        }

    }

    public List<Optionals> visualizzaOptional(Modello modello) {
        PreparedStatement ps;
        List<Optionals> optionals = new ArrayList<>();
        final String vediDipendente = "SELECT O.ID_Optional, O.descrizione, O.prezzo " +
                "FROM OPTIONAL O " +
                "JOIN Supporto S ON O.ID_Optional = S.ID_Optional " +
                "JOIN MODELLO M ON S.ID_MODELLO = M.ID_MODELLO " +
                "WHERE M.ID_MODELLO = ? ;";
        try {
            ps = connection.prepareStatement(vediDipendente);
            ps.setString(1, modello.descrizione());
            ResultSet set = ps.executeQuery();
            while (set.next()) {
                optionals.add(new Optionals(set.getInt(1), set.getString(2), set.getDouble(3)));
            }
            ps.close();
            return optionals;
        } catch (SQLException e) {
            throw new ProblemWithConnectionException(e);

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
            for (var m : marchi) {
                System.out.println(m.toString());
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
        final String vediMarchi = "SELECT ID_TIPOLOGIA, Nome, caratteristiche " +
                "FROM TIPOLOGIA;";
        try {
            ps = connection.prepareStatement(vediMarchi);
            ResultSet set = ps.executeQuery();
            while (set.next()) {
                tipologie.add(new Tipologia(set.getInt(1), set.getString(2), set.getString(3)));
            }
            for (var m : tipologie) {
                System.out.println(m.toString());
            }
            ps.close();
            return tipologie;
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

    public Dipendente visualizzaDipendente(Marchio marchio) {
        PreparedStatement ps;
        Dipendente dipendente = null;
        final String vediDipendente = "SELECT  M.ID_MARCHIO , D.nome, D.cognome, D.telefono, D.e_mail, M.Nome  AS Marchio "
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
                        set.getString(5));
            }
            ps.close();
            return dipendente;
        } catch (SQLException e) {
            throw new ProblemWithConnectionException(e);
        }
    }

    public Dipendente visualizzaDipendente(Modello modello) {
        PreparedStatement ps;
        Dipendente dipendente = null;
        final String vediDipendente = "SELECT * " +
                "FROM DIPENDENTE " +
                "WHERE ID_MARCHIO = ? ";
        try {
            ps = connection.prepareStatement(vediDipendente);
            ps.setInt(1, ID_Marchio(modello));
            ResultSet set = ps.executeQuery();
            while (set.next()) {
                dipendente = new Dipendente(set.getInt(2), set.getString(3),
                        set.getString(4), set.getString(5),
                        set.getString(7));
            }
            ps.close();
            return dipendente;
        } catch (SQLException e) {
            throw new ProblemWithConnectionException(e);
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
                "WHERE " +
                "MR.ID_MARCHIO = ?";
        try {
            ps = connection.prepareStatement(vediAuto);
            ps.setInt(1, marchio.idMarchio());
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

    public int visualizzaIDMarchio(String nomeMarchio) {
        PreparedStatement ps;
        int marchioID = 0;
        final String vediDipendente = "SELECT ID_MARCHIO " +
                "FROM MARCHIO " +
                "WHERE Nome = ?;";
        try {
            ps = connection.prepareStatement(vediDipendente);
            ps.setString(1, nomeMarchio);
            ResultSet set = ps.executeQuery();
            while (set.next()) {
                marchioID = set.getInt(1);
            }
            ps.close();
            return marchioID;
        } catch (SQLException e) {
            throw new ProblemWithConnectionException(e);
        }
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

    public static void main(String[] args) {
        ModelCliente model = new ModelCliente();
        model.init(ConnectionFactory.build("gestione_concessionario_prova",
                "jdbc:mysql://localhost:3306/", "root", "Strong.2024.Password"));
        model.visualizzaMarchi();
        System.out.println("----------------------");
        model.visualizzaModello();
        System.out.println("----------------------");
        // model.visualizzaGaranzia(new Auto("1HGBH41JXMN109186", 20000.00, true,
        // Optional.of("AB123CD"),
        // Optional.of(LocalDate.of(2024, 01, 10)), "", "", ""));
        System.out.println("----------------------");
        model.visualizzaDipendente(new Marchio(2, "Lamborghini"));
    }

}