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
import java.time.LocalTime;

import it.unibo.gestione_concessionario.commons.ConnectionFactory;
import it.unibo.gestione_concessionario.commons.dto.Appuntamento;
import it.unibo.gestione_concessionario.commons.dto.Auto;
import it.unibo.gestione_concessionario.commons.dto.Configurazione;
import it.unibo.gestione_concessionario.commons.dto.Offerta;
import it.unibo.gestione_concessionario.commons.dto.Sconto;

public class ModelDipendente {

    private Connection connection;
    private int iD;

    public ModelDipendente(Connection connection, int iD) {
        this.connection = connection;
        this.iD = iD;
    }

    List<Auto> visualizzaAutoDelDipendente() {
        PreparedStatement ps;
        List<Auto> auto = new ArrayList<>();
        final String vediAuto = "SELECT A.Numero_Telaio, A.prezzo, A.Immatricolazione, A.data, A.targa, M.Descrizione AS Modello, C.Motore, C.alimentazione "
                +
                "FROM AUTO A " +
                "JOIN CONFIGURAZIONE C ON A.ID_Configurazione = C.ID_Configurazione " +
                "JOIN MODELLO M ON C.ID_MODELLO = M.ID_MODELLO " +
                "JOIN MARCHIO MA ON M.ID_MARCHIO = MA.ID_MARCHIO " +
                "JOIN DIPENDENTE D ON D.ID_MARCHIO = MA.ID_MARCHIO " +
                "WHERE D.ID_DIPENDENTE = ?;";
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

    List<Appuntamento> visualizzaAppuntamenti() {
        PreparedStatement ps;
        List<Appuntamento> app = new ArrayList<>();
        final String vediAppuntamenti = "SELECT a.ID_APPUNTAMENTO, a.data, a.ora, a.Tipologia, a.durata, a.Numero_Telaio, "
                +
                "c.Nome AS Cliente_Nome, c.Cognome AS Cliente_Cognome, d.Nome AS Dipendente_Nome, d.Cognome AS Dipendente_Cognome "
                +
                "FROM APPUNTAMENTO a " +
                "JOIN CLIENTE c ON a.ID_CLIENTE = c.ID_CLIENTE " +
                "JOIN DIPENDENTE d ON a.ID_DIPENDENTE = d.ID_DIPENDENTE " +
                "WHERE a.ID_DIPENDENTE = ?;";
        try {
            ps = connection.prepareStatement(vediAppuntamenti);
            ps.setInt(1, iD);
            ResultSet set = ps.executeQuery();
            while (set.next()) {
                LocalDate data = set.getDate(2) != null ? set.getDate(2).toLocalDate() : null;
                LocalTime ora = set.getDate(3) != null ? set.getTime(2).toLocalTime() : null;
                LocalTime durata = set.getDate(5) != null ? set.getTime(2).toLocalTime() : null;
                String nome_cliente = set.getString(7) + " " + set.getString(8);
                String nome_dipendente = set.getString(9) + " " + set.getString(10);
                app.add(new Appuntamento(set.getInt(1), data, ora, set.getString(4), durata,
                        set.getString(6), nome_cliente, nome_dipendente));
            }
            for (var a : app) {
                System.out.println(a.toString());
            }
            return app;
        } catch (SQLException e) {
            throw new ProblemWithConnectionException(e);
        }

    }

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

    public void end() {
        try {
            connection.close();
        } catch (SQLException e) {
        }
    }
    public boolean aggiungiConfigurazione(Configurazione configurazione) {
        PreparedStatement ps;
        final String aggiungiConfigurazione = "INSERT INTO CONFIGURAZIONE (ID_Configurazione, Motore, alimentazione, cc, horse_power, ID_MODELLO) " + 
                                                "VALUES (?, ?, ?, ?, ?, ?);";
        try {
            ps = connection.prepareStatement(aggiungiConfigurazione);
            ps.setInt(1, configurazione.idConfigurazione());
            ps.setString(2, configurazione.modello());
            ps.setString(3, configurazione.motore());
            ps.setString(4, configurazione.alimentazione());
            ps.setInt(5, configurazione.cc());
            ps.setInt(6, configurazione.horsePower());
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
        final String aggiungiAuto = "INSERT INTO AUTO (Numero_Telaio, Immatricolazione, data, targa, ID_Configurazione) " +
                                     "VALUES (?, ?, ?, ?, ?);" ;
        try {
            ps = connection.prepareStatement(aggiungiAuto);
            ps.setString(1, auto.numero_telaio());
            ps.setDouble(2, auto.prezzo());
            ps.setBoolean(3,auto.immatricolazione() );
            ps.setString(4,auto.targa().get());
            ps.setDate(5,Date.valueOf(auto.data().get()));
            ps.setString(6,auto.descrizioneModello());
            ps.setString(7,auto.motore());
            ps.setString(8,auto.alimentazione());
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

    public static void main(String[] args) {
        ModelDipendente model = new ModelDipendente(ConnectionFactory.build("gestione_concessionario_prova",
                "jdbc:mysql://localhost:3306/", "root", "cadmio"), 13);
        model.visualizzaAppuntamenti();
        System.out.println("______________________________________________________________________________");
        model.visualizzaAutoDelDipendente();
        // model.aggiungiSconto(new Sconto(80,LocalDate.now(),LocalDate.of(2025,
        // 12,11),"1HGBH41JXMN109186"));
        model.aggiungiOfferta(new Offerta(1, 50,LocalDate.now() , LocalDate.of(2025, 12,11), 1, 13));
        model.end();
    }
}
