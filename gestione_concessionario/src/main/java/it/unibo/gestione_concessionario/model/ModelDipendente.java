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
import java.time.ZoneId;

import it.unibo.gestione_concessionario.commons.ConnectionFactory;
import it.unibo.gestione_concessionario.commons.dto.Appuntamento;
import it.unibo.gestione_concessionario.commons.dto.Auto;

public class ModelDipendente {

    private Connection connection;
    private int iD;

    public ModelDipendente(Connection connection, int iD) {
        this.connection = connection;
        this.iD = iD;
    }

   /*  List<Auto> visualizzaAuto() {
        PreparedStatement ps;
        List<Auto> auto = new ArrayList<>();
        final String vediAuto = "SELECT A.Numero_Telaio, A.Immatricolazione, A.data, A.targa, M.Descrizione AS Modello, C.Motore, C.alimentazione "
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
                auto.add(new Auto(set.getString(1), set.getInt(2), Optional.of(set.getString(3)),
                        Optional.of(set.getString(4)), Optional.empty(), set.getString(5), set.getString(6),
                        set.getString(7)));
            }
            for (var a : auto) {
                System.out.println(a.toString());
            }
            return auto;
        } catch (SQLException e) {
            throw new ProblemWithConnectionException(e);
        }
    }*/

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
                LocalDate data = Optional.ofNullable(set.getDate(2))
                        .map(date -> date.toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate())
                        .orElse(null);
                LocalTime ora = Optional.ofNullable(set.getDate(3) != null ? set.getTime(2).toLocalTime() : null).get();
                LocalTime durata = Optional.ofNullable(set.getDate(5) != null ? set.getTime(2).toLocalTime() : null)
                        .get();
                String nome_cliente = set.getString(7) + " " + set.getString(8);
                String nome_dipendente = set.getString(9) + " " + set.getString(10);
                app.add(new Appuntamento(set.getInt(1), data, ora, set.getString(4), Optional.of(durata),
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

   /*  public boolean aggiungiSconto(int Percentual, LocalDate inizio, LocalDate fine, String numero_telaio,int ID_DIPENDENTE){
        PreparedStatement ps;
        final String aggiungiScon = "INSERT INTO SCONTO (Percentuale, data_inizio, data_fine, Numero_Telaio, ID_DIPENDENTE) " + 
                                     "VALUES (?,?,?,?,?);";
        try {
        ps = connection.prepareStatement(aggiungiScon);
        ps.setInt(1, Percentual);
        ps.setDate(2, inizio != null ? Date.valueOf(inizio) : null);
        ps.setDate(3, fine != null ? Date.valueOf(fine) : null);
        ps.setString(4, numero_telaio);
        ps.setInt(5, ID_DIPENDENTE);

        }
    }*/

    public static void main(String[] args) {
        ModelDipendente model = new ModelDipendente(ConnectionFactory.build("gestione_concessionario_prova",
                "jdbc:mysql://localhost:3306/", "root", "cadmio"), 13);
        //model.visualizzaAuto();
    }
}
