package it.unibo.gestione_concessionario.controller;

import it.unibo.gestione_concessionario.commons.ConnectionFactory;
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

    public void initCliente() {
        this.model = new ModelCliente();
        connection = ConnectionFactory.build("gestione_concessionario_prova",
                "jdbc:mysql://localhost:3306/", "root", "cadmio");
        this.model.init(connection);
    }

    public void initDipendente() {
        this.model = new ModelDipendente();
        connection = ConnectionFactory.build("gestione_concessionario_prova",
                "jdbc:mysql://localhost:3306/", "root", "cadmio");
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
            throw new ProblemWithConnectionException("Operazione non supportata per questo modello.");
        }
    }

    public boolean checkLoginCliente(String email, String password) {
        if (model instanceof ModelCliente && ((ModelCliente) model).checkLoginCliente(email, password)) {
            this.view = new ClienteView(this);
            return true;
        } else {
            throw new ProblemWithConnectionException("Operazione non supportata per questo modello.");
        }
    }

    public boolean createCliente(Cliente cliente) {
        if (model instanceof ModelCliente) {
            return ((ModelCliente) model).creaCliente(cliente);
        } else {
            throw new ProblemWithConnectionException("Operazione non supportata per questo modello.");
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
            throw new ProblemWithConnectionException("Operazione non supportata per questo modello.");
        }
    }

    public List<Modello> allModelli() {
        return model.visualizzaModello();
    }

    public Dipendente dipendenteFromMarchio(Marchio marchio) {
        if (model instanceof ModelCliente) {
            return ((ModelCliente) model).visualizzaDipendente(marchio);
        } else {
            throw new ProblemWithConnectionException("Operazione non supportata per questo modello.");
        }
    }

    public Dipendente dipendeteFromModello(Modello modello) {
        if (model instanceof ModelCliente) {
            return ((ModelCliente) model).visualizzaDipendente(modello);
        } else {
            throw new ProblemWithConnectionException("Operazione non supportata per questo modello.");
        }
    }

    public int idFromNameMarchio(String name) {
        if (model instanceof ModelCliente) {
            return ((ModelCliente) model).visualizzaIDMarchio(name);
        } else {
            throw new ProblemWithConnectionException("Operazione non supportata per questo modello.");
        }
    }

    public List<Auto> visualizzaAutoxMarchioxTipologia(Marchio marchio, Tipologia tipologia) {
        if (model instanceof ModelCliente) {
            return ((ModelCliente) model).visualizzaAutoxMarchioxTipologia(marchio, tipologia);
        } else {
            throw new ProblemWithConnectionException("Operazione non supportata per questo modello.");
        }
    }

    public List<Tipologia> allTipologie() {
        if (model instanceof ModelCliente) {
            return ((ModelCliente) model).visualizzTipologie();
        } else {
            throw new ProblemWithConnectionException("Operazione non supportata per questo modello.");
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
            throw new ProblemWithConnectionException("Operazione non supportata per questo modello.");
        }
    }

    public boolean addAppuntamento(Appuntamento appuntamento) {
            return model.fissaAppuntamento(appuntamento);
    }


    public Cliente getClienteUser() {
        if (model instanceof ModelCliente) {
            return ((ModelCliente) model).getCliente();
        } else {
            throw new ProblemWithConnectionException("Operazione non supportata per questo modello.");
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
            throw new ProblemWithConnectionException("Operazione non supportata per questo modello.");
        }
    }

    public Optional<Garanzia> visualizzaGaranzia(String numeroTelaio) {
            return model.visualizzaGaranzia(numeroTelaio);
    }

    public List<Optionals> visualizzaOptionals(String numeroTelaio) {
            return model.visualizzaOptional(numeroTelaio);
    }

    public List<Auto> visualizzaAutoScontate(Marchio marchio) {
        return model.visualizzaAutoScontate(marchio);
    }

    // Dipendente

    public List<Auto> allAutoDipendente() {
        if (model instanceof ModelDipendente) {
            return ((ModelDipendente) model).visualizzaAutoDelDipendente();
        } else {
            throw new ProblemWithConnectionException("Operazione non supportata per questo modello.");
        }
    }

    
    public int addContratto(Contratto contratto) {
        if (model instanceof ModelDipendente) {
            return ((ModelDipendente) model).aggiungiContratto(contratto);
        } else {
            throw new ProblemWithConnectionException("Operazione non supportata per questo modello.");
        }
    }

    public boolean eliminaContratto(Contratto contratto) {
        if (model instanceof ModelDipendente) {
            return ((ModelDipendente) model).eliminaContratto(contratto);
        } else {
            throw new ProblemWithConnectionException("Operazione non supportata per questo modello.");
        }
    }


    public Dipendente getDipendenteUser() {
        if (model instanceof ModelDipendente) {
            return ((ModelDipendente) model).getDipendenteUser();
        } else {
            throw new ProblemWithConnectionException("Operazione non supportata per questo modello.");
        }
    }

    public Marchio getUserMarchio() {
        if (model instanceof ModelDipendente) {
            return ((ModelDipendente) model).getMyMarchio()
                    .orElseThrow(() -> new ProblemWithConnectionException("Marchio non trovato."));
        } else {
            throw new ProblemWithConnectionException("Operazione non supportata per questo modello.");
        }
    }

    public void addSconto(Sconto sconto) {
        if (model instanceof ModelDipendente) {
            ((ModelDipendente) model).aggiungiSconto(sconto);
        } else {
            throw new ProblemWithConnectionException("Operazione non supportata per questo modello.");
        }
    }

    public void addVendita(Vendita vendita) {
        if (model instanceof ModelDipendente) {
            ((ModelDipendente) model).inserisciVendita(vendita);
        } else {
            throw new ProblemWithConnectionException("Operazione non supportata per questo modello.");
        }
    }

    public void addOfferta(Offerta offerta){
        if(model instanceof ModelDipendente){
            ((ModelDipendente) model).aggiungiOfferta(offerta);
        }
        else {
            throw new ProblemWithConnectionException("Operazione non supportata per questo modello.");
        }
    }

    public void createAutoEConfig(Auto auto,Configurazione config) throws SQLException{
        if(model instanceof ModelDipendente){
            try {
                auto.setIdConfigurazione(((ModelDipendente) model).aggiungiConfigurazione(config));
                ((ModelDipendente) model).aggiungiAuto(auto);
            } catch (SQLException e) {
                throw new SQLException("errore nella creazione dell'auto");

            }
        }
        else {
            throw new ProblemWithConnectionException("Operazione non supportata per questo modello.");
        }
    }

    public String getClienteNameById(int id){
        return this.model.getClienteNameById(id);
    }

    public String getDipendenteNameById(int iD){
        return this.model.getDipendenteNameById(iD);
    }

    public List<Appuntamento> visualizzaAppuntamenti(){
        if(model instanceof ModelDipendente){
          return ((ModelDipendente) model).visualizzaAppuntamenti();
        }
        else {
            throw new ProblemWithConnectionException("Operazione non supportata per questo modello.");
        }

    }

    public List<Cliente> allClienti(){
        if(model instanceof ModelDipendente){
            return ((ModelDipendente) model).allClienti();
          }
          else {
              throw new ProblemWithConnectionException("Operazione non supportata per questo modello.");
          }
    }

    public List<Vendita> allVendite(){
        if(model instanceof ModelDipendente){
            return ((ModelDipendente) model).visualizzaVendite();
          }
          else {
              throw new ProblemWithConnectionException("Operazione non supportata per questo modello.");
          }
    }
}