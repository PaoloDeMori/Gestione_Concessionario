package it.unibo.gestione_concessionario.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.time.LocalDate;

import it.unibo.gestione_concessionario.commons.ConnectionFactory;
import it.unibo.gestione_concessionario.commons.dto.Auto;
import it.unibo.gestione_concessionario.commons.dto.Configurazione;
import it.unibo.gestione_concessionario.commons.dto.Contratto;
import it.unibo.gestione_concessionario.commons.dto.Dipendente;
import it.unibo.gestione_concessionario.commons.dto.Marchio;
import it.unibo.gestione_concessionario.commons.dto.Modello;
import it.unibo.gestione_concessionario.commons.dto.Offerta;
import it.unibo.gestione_concessionario.commons.dto.Sconto;
import it.unibo.gestione_concessionario.commons.dto.Vendita;

public class ModelDipendente extends Model {

    private Dipendente dipendente;
    private int iD;

    public ModelDipendente() {
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

    public boolean checkLoginDipendente(String email, String password) {
        PreparedStatement ps = null;
        final String login = "SELECT d.ID_MARCHIO, d.nome, d.cognome, d.telefono, d.responsabile, d.e_mail " +
                "FROM DIPENDENTE d " +
                "WHERE d.e_mail = ? " +
                "AND d.password = ?;";
        try {
            ps = connection.prepareStatement(login);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet set = ps.executeQuery();
            if (set.next()) {
                iD = set.getInt(1);
                dipendente=new Dipendente(set.getInt(1),set.getString(2),set.getString(3),set.getString(4),set.getBoolean(5),set.getString(6));
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new ProblemWithConnectionException(e);
        }

    }

    public List<Auto> visualizzaAutoDelDipendente() {
        PreparedStatement ps;
        List<Auto> auto = new ArrayList<>();
        final String vediAuto ="SELECT A.Numero_Telaio, A.prezzo, A.Immatricolazione, A.data, A.targa, M.Descrizione AS Modello, C.Motore, C.alimentazione "+
        "FROM AUTO A "+
        "JOIN CONFIGURAZIONE C ON A.ID_Configurazione = C.ID_Configurazione "+  
        "JOIN MODELLO M ON C.ID_MODELLO = M.ID_MODELLO "+  
        "JOIN MARCHIO MA ON M.ID_MARCHIO = MA.ID_MARCHIO "+
        "JOIN DIPENDENTE D ON D.ID_MARCHIO = MA.ID_MARCHIO "+ 
        "WHERE D.ID_DIPENDENTE = ? "+
        "AND A.Numero_Telaio NOT IN (SELECT Numero_Telaio FROM VENDITA);";
        try {
            ps = connection.prepareStatement(vediAuto);
            ps.setInt(1, iD);
            ResultSet set = ps.executeQuery();
            while (set.next()) {
                LocalDate data = set.getDate(4).toLocalDate();
                auto.add(new Auto(set.getString(1), set.getDouble(2), set.getBoolean(3), Optional.of(set.getString(5)),
                        Optional.of(data), set.getString(6), set.getString(7), set.getString(8)));
            }
            for (var a : auto) {
                System.out.println(a.toString());
            }
            return auto;
        } catch (SQLException e) {
            throw new ProblemWithConnectionException(e);
        }
    }

    /*
     * List<Appuntamento> visualizzaAppuntamenti() {
     * PreparedStatement ps;
     * List<Appuntamento> app = new ArrayList<>();
     * final String vediAppuntamenti =
     * "SELECT a.ID_APPUNTAMENTO, a.data, a.ora, a.Tipologia, a.durata, a.Numero_Telaio, "
     * +
     * "c.Nome AS Cliente_Nome, c.Cognome AS Cliente_Cognome, d.Nome AS Dipendente_Nome, d.Cognome AS Dipendente_Cognome "
     * +
     * "FROM APPUNTAMENTO a " +
     * "JOIN CLIENTE c ON a.ID_CLIENTE = c.ID_CLIENTE " +
     * "JOIN DIPENDENTE d ON a.ID_DIPENDENTE = d.ID_DIPENDENTE " +
     * "WHERE a.ID_DIPENDENTE = ?;";
     * try {
     * ps = connection.prepareStatement(vediAppuntamenti);
     * ps.setInt(1, iD);
     * ResultSet set = ps.executeQuery();
     * while (set.next()) {
     * LocalDate data = set.getDate(2) != null ? set.getDate(2).toLocalDate() :
     * null;
     * LocalTime ora = set.getDate(3) != null ? set.getTime(2).toLocalTime() : null;
     * LocalTime durata = set.getDate(5) != null ? set.getTime(2).toLocalTime() :
     * null;
     * String nome_cliente = set.getString(7) + " " + set.getString(8);
     * String nome_dipendente = set.getString(9) + " " + set.getString(10);
     * app.add(new Appuntamento(data, ora, set.getString(4), durata,
     * set.getString(6), nome_cliente, nome_dipendente));
     * }
     * for (var a : app) {
     * System.out.println(a.toString());
     * }
     * return app;
     * } catch (SQLException e) {
     * throw new ProblemWithConnectionException(e);
     * }
     * 
     * }
     */

    public boolean aggiungiSconto(Sconto sconto) {
        PreparedStatement ps;
        final String aggiungiScon = "INSERT INTO SCONTO (Percentuale, data_inizio, data_fine, Numero_Telaio, ID_DIPENDENTE) "
                +
                "VALUES (?,?,?,?,?);";
        try {
            ps = connection.prepareStatement(aggiungiScon);
            ps.setInt(1, sconto.percentuale());
            ps.setDate(2, sconto.dataInizio() != null ? Date.valueOf(sconto.dataInizio()) : null);
            ps.setDate(3, sconto.dataFine() != null ? Date.valueOf(sconto.dataFine()) : null);
            ps.setString(4, sconto.nuremo_telaio());
            ps.setInt(5, iD);
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

    public boolean aggiungiOfferta(Offerta offerta) {
        PreparedStatement ps;
        final String aggiungiOff = "INSERT INTO OFFERTA (Percentuale, data_inizio, data_fine, ID_MARCHIO, ID_DIPENDENTE) "
                +
                "VALUES (?,?,?,?,?);";
        try {
            ps = connection.prepareStatement(aggiungiOff);
            ps.setInt(1, offerta.percentuale());
            ps.setDate(2, offerta.dataInizio() != null ? Date.valueOf(offerta.dataInizio()) : null);
            ps.setDate(3, offerta.dataFine() != null ? Date.valueOf(offerta.dataFine()) : null);
            ps.setInt(4, offerta.ID_marchio());
            ps.setInt(5, iD);
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

    public boolean aggiungiModello(Modello modello) {
        PreparedStatement ps;
        final String aggiungiMod = "INSERT INTO MODELLO (Descrizione, Anno, ID_TIPOLOGIA, ID_MARCHIO) " +
                "VALUES (?, ?, ?, ?, ?);";
        try {
            ps = connection.prepareStatement(aggiungiMod);
            ps.setString(1, modello.descrizione());
            ps.setInt(2, modello.anno());
            ps.setString(3, modello.tipologia());
            ps.setString(4, modello.marchio());
            ps.setInt(5, iD);
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

    /*
     * public boolean fissaTestDrive(Appuntamento appuntamento) {
     * PreparedStatement ps;
     * final String fissaTestDrive =
     * "INSERT INTO APPUNTAMENTO (data, ora, Tipologia, durata, Numero_Telaio, ID_CLIENTE, ID_DIPENDENTE) "
     * +
     * "VALUES (?,Test-Drive,?,?,?,?,?);";
     * try {
     * ps = connection.prepareStatement(fissaTestDrive);
     * ps.setDate(1, Date.valueOf(appuntamento.data()));
     * ps.setTime(2, java.sql.Time.valueOf(appuntamento.ora()));
     * ps.setTime(3, java.sql.Time.valueOf(appuntamento.durata()));
     * ps.setString(4, appuntamento.numero_telaio());
     * ps.setString(5, appuntamento.nome_dipendente());
     * ps.setString(6, appuntamento.nome_cliente());
     * ps.execute();
     * ps.close();
     * return true;
     * }
     * catch (SQLException e) {
     * try {
     * connection.rollback();
     * } catch (SQLException e1) {
     * e1.printStackTrace();
     * }
     * return false;
     * }
     * }
     */

    /*
     * public boolean fissaConsulenza(Appuntamento appuntamento) {
     * PreparedStatement ps;
     * final String fissaTestDrive =
     * "INSERT INTO APPUNTAMENTO (data, ora, Tipologia, durata, Numero_Telaio, ID_CLIENTE, ID_DIPENDENTE) "
     * +
     * "VALUES (?,Consulenza,?,?,?,?,?);";
     * try {
     * ps = connection.prepareStatement(fissaTestDrive);
     * ps.setDate(1, Date.valueOf(appuntamento.data()));
     * ps.setTime(2, java.sql.Time.valueOf(appuntamento.ora()));
     * ps.setTime(3, java.sql.Time.valueOf(appuntamento.durata()));
     * ps.setString(4, appuntamento.numero_telaio());
     * ps.setString(5, appuntamento.nome_dipendente());
     * ps.setString(6, appuntamento.nome_cliente());
     * ps.execute();
     * ps.close();
     * return true;
     * }
     * catch (SQLException e) {
     * try {
     * connection.rollback();
     * } catch (SQLException e1) {
     * e1.printStackTrace();
     * }
     * return false;
     * }
     * }
     */

    public boolean agigungiDipendente(Dipendente dipendente) {
        PreparedStatement ps;
        final String aggiungiDip = "INSERT INTO DIPENDENTE (ID_MARCHIO, nome, cognome, telefono, e_mail) " +
                "VALUES (?, ?, ?, ?, ?, ?); ";
        try {
            ps = connection.prepareStatement(aggiungiDip);
            ps.setInt(1, dipendente.idmarchio());
            ps.setString(2, dipendente.nome());
            ps.setString(3, dipendente.cognome());
            ps.setString(4, dipendente.telefono());
            ps.setString(5, dipendente.eMail());
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

    public void end() {
        try {
            connection.close();
        } catch (SQLException e) {
        }
    }

    public boolean aggiungiConfigurazione(Configurazione configurazione) {
        PreparedStatement ps;
        final String aggiungiConfigurazione = "INSERT INTO CONFIGURAZIONE (ID_Configurazione, Motore, alimentazione, cc, horse_power, ID_MODELLO) "
                +
                "VALUES (?, ?, ?, ?, ?, ?);";
        try {
            ps = connection.prepareStatement(aggiungiConfigurazione);
            ps.setInt(1, configurazione.idConfigurazione());
            ps.setString(2, configurazione.motore());
            ps.setString(3, configurazione.alimentazione());
            ps.setInt(4, configurazione.cc());
            ps.setInt(5, configurazione.horsePower());
            ps.setInt(6, configurazione.id_modello());
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

    public boolean aggiungiAuto(Auto auto) {
        PreparedStatement ps;
        final String aggiungiAuto = "INSERT INTO AUTO (Numero_Telaio, Immatricolazione, data, targa, ID_Configurazione) "
                +
                "VALUES (?, ?, ?, ?, ?);";
        try {
            ps = connection.prepareStatement(aggiungiAuto);
            ps.setString(1, auto.getNumero_telaio());
            ps.setDouble(2, auto.getPrezzo());
            ps.setBoolean(3, auto.getImmatricolazione());
            ps.setString(4, auto.getTarga().get());
            ps.setDate(5, Date.valueOf(auto.getData().get()));
            ps.setString(6, auto.getDescrizioneModello());
            ps.setString(7, auto.getMotore());
            ps.setString(8, auto.getAlimentazione());
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

    public boolean inserisciVendita(Vendita vendita) {
        PreparedStatement ps;
        final String inserisciVendita = "INSERT INTO VENDITA (Numero_Telaio, ID_Contratto, data, ora, tipologia ,ID_DIPENDENTE, ID_CLIENTE) "
                +
                "VALUES (?,?,?,?,?,?);";
        try {
            ps = connection.prepareStatement(inserisciVendita);
            ps.setInt(1, vendita.idVendita());
            ps.setInt(2, vendita.idContratto());
            ps.setDate(3, Date.valueOf(vendita.data()));
            ps.setTime(4, java.sql.Time.valueOf(vendita.ora()));
            ps.setString(5, vendita.tipologia().get());
            ps.setInt(6, vendita.id_dipendente());
            ps.setInt(7, vendita.codCliente());
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

    public boolean aggiungiContratto(Contratto contratto) {
        PreparedStatement ps;
        final String aggiungiContratto = "INSERT INTO CONTRATTO (ID_CONTRATTO,prezzo, Tipologia, Nome_banca, codice_finanziamento, Intestatario, metodo_di_pagamento) "
                +
                "VALUES (?,?,?,?,?,?);";
        try {
            ps = connection.prepareStatement(aggiungiContratto);
            ps.setInt(1, contratto.idContratto());
            ps.setDouble(2, contratto.prezzo());
            ps.setString(3, contratto.tipologia());
            ps.setString(4, contratto.nomeBanca().get());
            ps.setString(5, contratto.codiceFinanziamento().get());
            ps.setString(6, contratto.intestatario().get());
            ps.setString(7, contratto.metodoDiPagamento().get());
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

    public Dipendente getDipendenteUser(){
        return this.dipendente;
    }

    public boolean isUserResponabile(){
        return this.dipendente.isResponsabile();
    }

    public Optional<Marchio> getMyMarchio() {
    PreparedStatement ps = null;
    final String queryMarchio = "SELECT ID_MARCHIO, Nome FROM MARCHIO WHERE ID_MARCHIO = ?;";
    try {
        ps = connection.prepareStatement(queryMarchio);
        ps.setInt(1, iD);
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()) {
            int id = resultSet.getInt("ID_MARCHIO");
            String nome = resultSet.getString("Nome");
            return Optional.of(new Marchio(id, nome));
        } else {
            return Optional.empty();
        }
    } catch (SQLException e) {
        throw new ProblemWithConnectionException(e);
    } finally {
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

    public static void main(String[] args) {
        ModelDipendente model = new ModelDipendente();
        model.init(ConnectionFactory.build("gestione_concessionario_prova",
                "jdbc:mysql://localhost:3306/", "root", "cadmio"));
        // model.visualizzaAppuntamenti();
        System.out.println("______________________________________________________________________________");
        model.visualizzaAutoDelDipendente();
        // model.aggiungiSconto(new Sconto(80,LocalDate.now(),LocalDate.of(2025,
        // 12,11),"1HGBH41JXMN109186"));
        model.aggiungiOfferta(new Offerta(1, 50, LocalDate.now(), LocalDate.of(2025, 12, 11), 1, 13));
        model.end();
    }
}
