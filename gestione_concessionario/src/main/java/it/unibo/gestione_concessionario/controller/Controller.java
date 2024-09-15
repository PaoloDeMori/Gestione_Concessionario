package it.unibo.gestione_concessionario.controller;

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
import it.unibo.gestione_concessionario.model.ModelCliente;
import it.unibo.gestione_concessionario.model.ModelDipendente;
import it.unibo.gestione_concessionario.view.ClienteView;
import it.unibo.gestione_concessionario.view.DipendenteView;
import it.unibo.gestione_concessionario.view.View;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class Controller {
    View view;
    ModelDipendente modelDipendente;
    ModelCliente modelCliente;
    Connection connection;

    public void initCliente() {
        this.modelCliente = new ModelCliente();
        connection = ConnectionFactory.build("gestione_concessionario_prova",
                "jdbc:mysql://localhost:3306/", "root", "cadmio");
        this.modelCliente.init(connection);
    }

    public void initDipendente() {
        this.modelDipendente = new ModelDipendente();
        connection = ConnectionFactory.build("gestione_concessionario_prova",
            "jdbc:mysql://localhost:3306/", "root", "cadmio");
        this.modelDipendente.init(connection);
    }

    public boolean checkLoginDipendente(String email, String password) {
        if (modelDipendente.checkLoginDipendente(email, password)) {
            this.view = new DipendenteView();
            return true;
        } else {
            return false;
        }
    }

    public boolean checkLoginCliente(String email, String password) {
        if (this.modelCliente.checkLoginCliente(email, password)) {
            this.view = new ClienteView(this);
            return true;
        } else {
            return false;
        }
    }

    public boolean createCliente(Cliente cliente) {
        return this.modelCliente.creaCliente(cliente);
    }

    public void startCliente() {
        this.view.start();
    }

    public void startDipendenteView() {

    }

    public List<Marchio> allMarchi() {
        return this.modelCliente.visualizzaMarchi();
    }

    public List<Modello> allModelli() {
        return this.modelCliente.visualizzaModello();

    }

    public Dipendente dipendenteFromMarchio(Marchio marchio) {
        return this.modelCliente.visualizzaDipendente(marchio);
    }

    public Dipendente dipendeteFromModello(Modello modello) {
        return this.modelCliente.visualizzaDipendente(modello);
    }

    public int idFromNameMarchio(String name) {
        return modelCliente.visualizzaIDMarchio(name);
    }

    public List<Auto> visualizzaAutoxMarchioxTipologia(Marchio marchio, Tipologia tipologia) {
        return modelCliente.visualizzaAutoxMarchioxTipologia(marchio, tipologia);
    }

    public List<Tipologia> allTipologie() {
        return this.modelCliente.visualizzTipologie();
    }

    public List<Auto> allAutoFromModelli(Modello modello) {
        return this.modelCliente.visualizzaAutoxModello(modello);
    }

    public int idMarchioFromNomeModello(Modello modello) {
        return this.modelCliente.ID_Marchio(modello);
    }

    public boolean addAppuntamento(Appuntamento appuntamento) {
        return this.modelCliente.fissaAppuntamento(appuntamento);
    }

    public Cliente getClienteUser() {
        return this.modelCliente.getCliente();
    }

    public int id_ClienteByEmail(String email) {
        return this.modelCliente.getClienteIDByEmail(email);
    }

    public int id_DipendenteByEmail(String email) {
        return this.modelCliente.getDipendenteIDByEmail(email);
    }

    public List<Auto> allAuto() {
        return this.modelCliente.visualizzaTutteLeAutoConDescrizioneModello();
    }

    public Optional<Garanzia> visualizzaGaranzia(String numeroTelaio) {
        return modelCliente.visualizzaGaranzia(numeroTelaio);
    }

    public List<Optionals> visualizzaOptionals(String numeroTelaio) {
        return modelCliente.visualizzaOptional(numeroTelaio);
    }

    public List<Auto> visualizzaAutoScontate(Marchio marchio) {
        return this.modelCliente.visualizzaAutoScontate(marchio);
    }

}
