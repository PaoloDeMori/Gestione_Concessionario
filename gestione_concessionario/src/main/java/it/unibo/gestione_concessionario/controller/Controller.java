package it.unibo.gestione_concessionario.controller;

import it.unibo.gestione_concessionario.model.ProblemWithConnectionException;
import it.unibo.gestione_concessionario.commons.dto.Appuntamento;
import it.unibo.gestione_concessionario.commons.dto.Auto;
import it.unibo.gestione_concessionario.commons.dto.Cliente;
import it.unibo.gestione_concessionario.commons.dto.Configurazione;
import it.unibo.gestione_concessionario.commons.dto.Contratto;
import it.unibo.gestione_concessionario.commons.dto.Dipendente;
import it.unibo.gestione_concessionario.commons.dto.Garanzia;
import it.unibo.gestione_concessionario.commons.dto.Marchio;
import it.unibo.gestione_concessionario.commons.dto.Modello;
import it.unibo.gestione_concessionario.commons.dto.Offerta;
import it.unibo.gestione_concessionario.commons.dto.Optionals;
import it.unibo.gestione_concessionario.commons.dto.Personalizzazione;
import it.unibo.gestione_concessionario.commons.dto.Sconto;
import it.unibo.gestione_concessionario.commons.dto.Tipologia;
import it.unibo.gestione_concessionario.commons.dto.Vendita;
import it.unibo.gestione_concessionario.model.Model;
import it.unibo.gestione_concessionario.model.ModelCliente;
import it.unibo.gestione_concessionario.model.ModelDipendente;
import it.unibo.gestione_concessionario.view.ClienteView;
import it.unibo.gestione_concessionario.view.DipendenteView;
import it.unibo.gestione_concessionario.view.View;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class Controller {
    View view;
    Model model;
    Connection connection;
    String standardErrorMessage = "Operazione non supportata per questo modello.";

    public Controller(Connection connection) {
        this.connection = connection;
    }

    public void initCliente() {
        this.model = new ModelCliente();
        this.model.init(connection);
    }

    public void initDipendente() {
        this.model = new ModelDipendente();
        this.model.init(connection);
    }

    public boolean checkLoginDipendente(String email, String password) {
        if (model instanceof ModelDipendente) {
            if (!((ModelDipendente) model).checkLoginDipendente(email, password)) {
                return false;
            } else {
                this.view = new DipendenteView(this);
                return true;
            }
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public boolean checkLoginCliente(String email, String password) {
        if (model instanceof ModelCliente) {
            if (!((ModelCliente) model).checkLoginCliente(email, password)) {
                return false;
            } else {
                this.view = new ClienteView(this);
                return true;
            }
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public boolean createCliente(Cliente cliente) throws SQLException {
        if (model instanceof ModelCliente) {
            return ((ModelCliente) model).creaCliente(cliente);
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public void startCliente() {
        if (view != null) {
            this.view.start();
        } else {
            throw new ProblemWithConnectionException("Nessuna vista cliente inizializzata.");
        }
    }

    public void startDipendente() {
        if (view != null) {
            this.view.start();
        } else {
            throw new ProblemWithConnectionException("Nessuna vista dipendente inizializzata.");
        }
    }

    public List<Marchio> allMarchi() {
        if (model instanceof ModelCliente) {
            return ((ModelCliente) model).visualizzaMarchi();
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public List<Modello> allModelli() {
        return model.visualizzaModello();
    }

    public List<Offerta> allOfferte() {
        if (model instanceof ModelDipendente) {
            return ((ModelDipendente) model).visualizzaOfferte();
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public Dipendente dipendenteFromMarchio(Marchio marchio) throws IllegalArgumentException{
        if (model instanceof ModelCliente) {
            return ((ModelCliente) model).visualizzaDipendente(marchio);
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public Dipendente dipendeteFromModello(Modello modello) {
        if (model instanceof ModelCliente) {
            return ((ModelCliente) model).visualizzaDipendente(modello);
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public List<Auto> visualizzaAutoxMarchioxTipologia(Marchio marchio, Tipologia tipologia) {
        if (model instanceof ModelCliente) {
            return ((ModelCliente) model).visualizzaAutoxMarchioxTipologia(marchio, tipologia);
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public List<Tipologia> allTipologie() {
        if (model instanceof ModelCliente) {
            return ((ModelCliente) model).visualizzTipologie();
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public List<Auto> allAutoFromModelli(Modello modello) {
        return model.visualizzaAutoxModello(modello);
    }

    public List<Auto> allAutoNonVenduteFromModelli(Modello modello) {
        return model.visualizzaAutoxModelloNonVendute(modello);
    }

    public int idMarchioFromNomeModello(Modello modello) {
        if (model instanceof ModelCliente) {
            return ((ModelCliente) model).ID_Marchio(modello);
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public boolean addAppuntamento(Appuntamento appuntamento) throws SQLException {
        return model.fissaAppuntamento(appuntamento);
    }

    public Cliente getClienteUser() {
        if (model instanceof ModelCliente) {
            return ((ModelCliente) model).getCliente();
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public int id_ClienteByEmail(String email) {
        return model.getClienteIDByEmail(email);
    }

    public int id_DipendenteByEmail(String email) {
        return model.getDipendenteIDByEmail(email);
    }

    public List<Auto> allAuto() {
        if (model instanceof ModelCliente) {
            return ((ModelCliente) model).visualizzaTutteLeAutoConDescrizioneModello();
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public Optional<Garanzia> visualizzaGaranzia(String numeroTelaio) {
        return model.visualizzaGaranzia(numeroTelaio);
    }

    public List<Optionals> visualizzaOptionals(String numeroTelaio) {
        return model.visualizzaOptional(numeroTelaio);
    }

    public List<Optionals> visualizzaAllOptionals() {
        return model.visualizzaAllOptional();
    }

    public List<Auto> visualizzaAutoScontate(Marchio marchio) {
        return model.visualizzaAutoScontate(marchio);
    }

    public List<Auto> allAutoDipendente() {
        if (model instanceof ModelDipendente) {
            return ((ModelDipendente) model).visualizzaAutoDelDipendente();
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public int addContratto(Contratto contratto) throws SQLException {
        if (model instanceof ModelDipendente) {
            return ((ModelDipendente) model).aggiungiContratto(contratto);
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public List<Configurazione> visualizzaConfigurazioni(Modello modello)  throws SQLException{
        if (model instanceof ModelDipendente) {
            return ((ModelDipendente) model).visualizzaConfigurazioni(modello);
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public boolean eliminaContratto(Contratto contratto) throws SQLException {
        if (model instanceof ModelDipendente) {
            return ((ModelDipendente) model).eliminaContratto(contratto);
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public Dipendente getDipendenteUser() {
        if (model instanceof ModelDipendente) {
            return ((ModelDipendente) model).getDipendenteUser();
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public Marchio getUserMarchio() {
        if (model instanceof ModelDipendente) {
            return ((ModelDipendente) model).getMyMarchio()
                    .orElseThrow(() -> new ProblemWithConnectionException("Marchio non trovato."));
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public List<Tipologia> getTipologie() {
        if (model instanceof ModelDipendente) {
            return ((ModelDipendente) model).visualizzaTipologia();
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public void addSconto(Sconto sconto) throws SQLException {
        if (model instanceof ModelDipendente) {
            ((ModelDipendente) model).aggiungiSconto(sconto);
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public void addSconto(Modello modello) throws SQLException {
        if (model instanceof ModelDipendente) {
            ((ModelDipendente) model).aggiungiModello(modello);
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public void addVendita(Vendita vendita) throws SQLException {
        if (model instanceof ModelDipendente) {
            ((ModelDipendente) model).inserisciVendita(vendita);
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public void addOfferta(Offerta offerta) throws SQLException {
        if (model instanceof ModelDipendente) {
            ((ModelDipendente) model).aggiungiOfferta(offerta);
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public void addModello(Modello modello) throws SQLException {
        if (model instanceof ModelDipendente) {
            ((ModelDipendente) model).aggiungiModello(modello);
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public void addPersonalizzazione(Personalizzazione personalizzazione) throws SQLException {
        if (model instanceof ModelDipendente) {
            ((ModelDipendente) model).aggiungiPersonalizzazione(personalizzazione);
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public void createAutoEConfig(Auto auto, Configurazione config) throws SQLException {
        if (model instanceof ModelDipendente) {
            try {
                auto.setIdConfigurazione(((ModelDipendente) model).aggiungiConfigurazione(config));
                ((ModelDipendente) model).aggiungiAuto(auto);
            } catch (SQLException e) {
                throw new SQLException("errore nella creazione dell'auto");

            }
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public void createAuto(Auto auto) throws SQLException {
        if (model instanceof ModelDipendente) {
            try {
                ((ModelDipendente) model).aggiungiAuto(auto);
            } catch (SQLException e) {
                throw new SQLException("errore nella creazione dell'auto");

            }
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public String getClienteNameById(int id) {
        return this.model.getClienteNameById(id);
    }

    public String getDipendenteNameById(int iD) {
        return this.model.getDipendenteNameById(iD);
    }

    public List<Appuntamento> visualizzaAppuntamenti() {
        if (model instanceof ModelDipendente) {
            return ((ModelDipendente) model).visualizzaAppuntamenti();
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }

    }

    public List<Cliente> allClienti() {
        if (model instanceof ModelDipendente) {
            return ((ModelDipendente) model).allClienti();
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public List<Vendita> allVendite() {
        if (model instanceof ModelDipendente) {
            return ((ModelDipendente) model).visualizzaVendite();
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public void addGaranzia(Garanzia garanzia) throws SQLException {
        if (model instanceof ModelDipendente) {
            ((ModelDipendente) model).aggiungiGaranzia(garanzia);
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public void addTipologia(Tipologia tipologia) throws SQLException {
        if (model instanceof ModelDipendente) {
            ((ModelDipendente) model).aggiungiTipologia(tipologia);
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public void aggiungiDipendente(Dipendente dipendente, String password) throws SQLException {
        if (model instanceof ModelDipendente) {
            ((ModelDipendente) model).aggiungiDipendente(dipendente, password);
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public List<Marchio> visualizzaMarchiSenzaDipendente() {
        if (model instanceof ModelDipendente) {
            return ((ModelDipendente) model).visualizzaMarchiSenzaDipendente();
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public List<Dipendente> visualizzaDipendenti() {
        if (model instanceof ModelDipendente) {
            return ((ModelDipendente) model).visualizzaDipendenti();
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public int visualizzaTotaleVendite() {
        if (model instanceof ModelDipendente) {
            return ((ModelDipendente) model).visualizzaTotaleVendite();
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public int visualizzaMedia() {
        if (model instanceof ModelDipendente) {
            return ((ModelDipendente) model).visualizzaMediaVendite();
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public int visualizzaGuadagniAlMese(int mese) {
        if (model instanceof ModelDipendente) {
            return ((ModelDipendente) model).visualizzaVenditeMese(mese);
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public void rimuoviDipendente(String email) throws SQLException {
        if (model instanceof ModelDipendente) {
            if (!((ModelDipendente) model).rimuoviDipendente(email)) {
                throw new SQLException("Errore nella rimozione");
            }
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public String nomeModelloPiuVenduto() {
        if (model instanceof ModelDipendente) {
            return ((ModelDipendente) model).nomeModelloPiuVenduto();
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public String nomeModelloMenoVenduto() {
        if (model instanceof ModelDipendente) {
            return ((ModelDipendente) model).nomeModelloMenoVenduto();
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public int numeroAcquistiConFinanziamento() {
        if (model instanceof ModelDipendente) {
            return ((ModelDipendente) model).numeroAcquistiConFinanziamento();
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public int numeroAcquistiConUnicaRata() {
        if (model instanceof ModelDipendente) {
            return ((ModelDipendente) model).numeroAcquistiConUnicaRata();
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public int numeroTotaleAutoVendute() {
        if (model instanceof ModelDipendente) {
            return ((ModelDipendente) model).numeroTotaleAutoVendute();
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public int percentualeAutoVenduteConSconto() {
        if (model instanceof ModelDipendente) {
            return ((ModelDipendente) model).percentualeAutoVenduteConSconto();
        } else {
            throw new ProblemWithConnectionException(standardErrorMessage);
        }
    }

    public void stop() throws SQLException {
        connection.close();
    }

}