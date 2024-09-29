package it.unibo.gestione_concessionario.model;

import java.sql.Connection;

import it.unibo.gestione_concessionario.commons.dto.Auto;
import it.unibo.gestione_concessionario.commons.dto.Marchio;
import it.unibo.gestione_concessionario.commons.dto.Modello;

import java.util.List;
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

}
