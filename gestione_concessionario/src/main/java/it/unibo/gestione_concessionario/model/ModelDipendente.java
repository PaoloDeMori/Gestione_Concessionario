package it.unibo.gestione_concessionario.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalTime;

import it.unibo.gestione_concessionario.commons.dto.Appuntamento;
import it.unibo.gestione_concessionario.commons.dto.Auto;
import it.unibo.gestione_concessionario.commons.dto.Cliente;
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
                dipendente = new Dipendente(set.getInt(1), set.getString(2), set.getString(3), set.getString(4),
                        set.getBoolean(5), set.getString(6));
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
        final String vediAuto = "SELECT A.Numero_Telaio, A.prezzo, A.Immatricolazione, A.data, A.targa, M.Descrizione AS Modello, C.Motore, C.alimentazione "
                +
                "FROM AUTO A " +
                "JOIN CONFIGURAZIONE C ON A.ID_Configurazione = C.ID_Configurazione " +
                "JOIN MODELLO M ON C.ID_MODELLO = M.ID_MODELLO " +
                "JOIN MARCHIO MA ON M.ID_MARCHIO = MA.ID_MARCHIO " +
                "JOIN DIPENDENTE D ON D.ID_MARCHIO = MA.ID_MARCHIO " +
                "WHERE D.ID_DIPENDENTE = ? " +
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

    public List<Appuntamento> visualizzaAppuntamenti() {
        PreparedStatement ps;
        List<Appuntamento> app = new ArrayList<>();
        final String vediAppuntamenti = "SELECT a.data, a.ora, a.durata, a.Numero_Telaio, a.Tipologia, "
                +
                "c.ID_CLIENTE, d.ID_DIPENDENTE "
                +
                "FROM APPUNTAMENTO a "
                +
                "JOIN CLIENTE c ON a.ID_CLIENTE = c.ID_CLIENTE "
                +
                "JOIN DIPENDENTE d ON a.ID_DIPENDENTE = d.ID_DIPENDENTE "
                +
                "WHERE a.ID_DIPENDENTE = ? " +
                "AND CONCAT(a.data, ' ', a.ora) > CURRENT_TIMESTAMP;";
        try {
            ps = connection.prepareStatement(vediAppuntamenti);
            ps.setInt(1, iD);
            ResultSet set = ps.executeQuery();
            while (set.next()) {
                LocalDate data = set.getDate(1) != null ? set.getDate(1).toLocalDate() : null;
                LocalTime ora = set.getDate(2) != null ? set.getTime(2).toLocalTime() : null;
                LocalTime durata = set.getDate(3) != null ? set.getTime(3).toLocalTime() : null;
                int iD_Cliente = set.getInt(6);
                int iD_Dipendente = set.getInt(7);
                app.add(new Appuntamento(data, ora, set.getString(5), durata, set.getString(4),
                        iD_Dipendente, iD_Cliente));
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

    public int aggiungiConfigurazione(Configurazione configurazione) throws SQLException {
        PreparedStatement ps;
        final String aggiungiConfigurazione = "INSERT INTO CONFIGURAZIONE (Motore, alimentazione, cc, horse_power, ID_MODELLO) "
                +
                "VALUES (?, ?, ?, ?, ?);";
        try {
            ps = connection.prepareStatement(aggiungiConfigurazione,PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, configurazione.motore());
            ps.setString(2, configurazione.alimentazione());
            ps.setInt(3, configurazione.cc());
            ps.setInt(4, configurazione.horsePower());
            ps.setInt(5, configurazione.id_modello());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            int generatedId = -1;
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
            ps.close();
            connection.commit();
            return generatedId;
        } catch (SQLException e) {
            try {
                connection.rollback();
                throw new SQLException("Impossibile creare configurazione");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return -1;
    }

    public boolean aggiungiAuto(Auto auto) {
        PreparedStatement ps;
        final String aggiungiAuto = "INSERT INTO AUTO (Numero_Telaio,prezzo, Immatricolazione, data, targa, ID_Configurazione) "
                +
                "VALUES (?, ?, ?, ?, ?, ?);";
        try {
            ps = connection.prepareStatement(aggiungiAuto);
            ps.setString(1, auto.getNumero_telaio());
            ps.setDouble(2, auto.getPrezzo());
            ps.setBoolean(3, auto.getImmatricolazione());
            ps.setDate(4, Date.valueOf(auto.getData().get()));
            ps.setString(5, auto.getTarga().get());
            ps.setInt(6, auto.getIdConfigurazione());
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
        final String inserisciVendita = "INSERT INTO VENDITA (Numero_Telaio, ID_Contratto, data, ora,ID_DIPENDENTE, ID_CLIENTE) "
                +
                "VALUES (?,?,?,?,?,?);";
        try {
            ps = connection.prepareStatement(inserisciVendita);
            // TODO inserire numero_telaio
            ps.setString(1, vendita.nuremo_telaio());
            ps.setInt(2, vendita.contratto().getIdContratto());
            ps.setDate(3, Date.valueOf(vendita.data()));
            ps.setTime(4, java.sql.Time.valueOf(vendita.ora()));
            ps.setInt(5, vendita.id_dipendente());
            ps.setInt(6, vendita.codCliente());
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

    public int aggiungiContratto(Contratto contratto) {
        PreparedStatement ps;
        final String aggiungiContratto = "INSERT INTO CONTRATTO (prezzo, Tipologia, Nome_banca, codice_finanziamento, Intestatario, metodo_di_pagamento) "
                +
                "VALUES (?,?,?,?,?,?);";
        try {
            // Prepara lo statement specificando che vuoi le chiavi generate
            ps = connection.prepareStatement(aggiungiContratto, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, contratto.getPrezzo());
            ps.setString(2, contratto.getTipologia());
    
            if (contratto.getTipologia().equals("Finanziamento")) {
                ps.setString(3, contratto.getNomeBanca().get());
                ps.setString(4, contratto.getCodiceFinanziamento().get());
                ps.setString(5, contratto.getIntestatario().get());
                ps.setNull(6, Types.VARCHAR);
            } else if(contratto.getTipologia().equals("Unica Rata")){
                ps.setNull(3, Types.VARCHAR);
                ps.setNull(4, Types.VARCHAR);
                ps.setNull(5, Types.VARCHAR);
                ps.setString(6, contratto.getMetodoDiPagamento().get());
            } else {
                throw new SQLException("Tipologia non valida");
            }
    
            // Esegui l'update
            ps.executeUpdate();
    
            // Recupera le chiavi generate
            ResultSet rs = ps.getGeneratedKeys();
            int generatedId = -1;
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
    
            // Chiudi lo statement e fai il commit
            ps.close();
            connection.commit();
    
            // Ritorna l'ID generato
            return generatedId;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            return -1;
        }
    }
    

    public boolean eliminaContratto(Contratto contratto) {
        PreparedStatement ps;
        final String eliminaContratto = "DELETE FROM CONTRATTO WHERE id_contratto = ?;";
    
        try {
            ps = connection.prepareStatement(eliminaContratto);
            ps.setInt(1, contratto.getIdContratto());
    
            int rowsAffected = ps.executeUpdate();
            ps.close();
            connection.commit();
                return rowsAffected > 0;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            return false;
        }
    }

    

    @Override
    public List<Modello> visualizzaModello() {
        PreparedStatement ps;
        List<Modello> modello = new ArrayList<>();
        final String vediModello = "SELECT m.ID_MODELLO, m.Descrizione, m.Anno,T.nome, ma.nome " +
                "FROM MODELLO m " +
                "JOIN TIPOLOGIA T ON T.ID_TIPOLOGIA=m.ID_TIPOLOGIA " +
                "JOIN MARCHIO ma ON ma.ID_MARCHIO=m.ID_MARCHIO " +
                "WHERE m.ID_MARCHIO=?;";
        try {
            ps = connection.prepareStatement(vediModello);
            ps.setInt(1, dipendente.idmarchio());
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

    public List<Vendita> visualizzaVendite() {
        PreparedStatement ps;
        List<Vendita> vendite = new ArrayList<>();
        final String vediModello = "SELECT v.Numero_Telaio, v.data,v.ora, v.ID_CLIENTE,v.ID_DIPENDENTE, c.ID_Contratto,c.prezzo, c.Tipologia " +
                "FROM VENDITA v " +
                "JOIN CONTRATTO c ON c.ID_Contratto=v.ID_Contratto " +
                "WHERE v.ID_DIPENDENTE=?;";
        try {
            ps = connection.prepareStatement(vediModello);
            ps.setInt(1, dipendente.idmarchio());
            ResultSet set = ps.executeQuery();
            while (set.next()) {
                Contratto contratto = new Contratto(set.getInt(6),set.getDouble(7),set.getString(8));
                vendite.add(new Vendita(set.getString(1), contratto, set.getDate(2).toLocalDate(), set.getTime(3).toLocalTime(),
                        set.getInt(5),set.getInt(4)));
            }
            for (var v : vendite) {
                System.out.println(v.toString());
            }
            ps.close();
            return vendite;
        } catch (SQLException e) {
            throw new ProblemWithConnectionException(e);
        }
    }


    public Dipendente getDipendenteUser() {
        return this.dipendente;
    }

    public boolean isUserResponabile() {
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

    public List<Cliente> allClienti() {
        PreparedStatement ps = null;
        List<Cliente> clienti = new ArrayList<Cliente>();
        final String queryMarchio = "SELECT nome, cognome, telefono,e_mail,password FROM CLIENTE ;";
        try {
            ps = connection.prepareStatement(queryMarchio);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String nome = resultSet.getString("nome");
                String cognome = resultSet.getString("cognome");
                String telefono = resultSet.getString("telefono");
                String e_mail = resultSet.getString("e_mail");
                String password = resultSet.getString("password");
                clienti.add(new Cliente(nome, cognome, telefono, e_mail, password));
            }
            return clienti;
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
}
