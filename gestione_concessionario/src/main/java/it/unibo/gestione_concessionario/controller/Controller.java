package it.unibo.gestione_concessionario.controller;


import it.unibo.gestione_concessionario.commons.ConnectionFactory;
import it.unibo.gestione_concessionario.commons.dto.Appuntamento;
import it.unibo.gestione_concessionario.commons.dto.Auto;
import it.unibo.gestione_concessionario.commons.dto.Cliente;
import it.unibo.gestione_concessionario.commons.dto.Dipendente;
import it.unibo.gestione_concessionario.commons.dto.Marchio;
import it.unibo.gestione_concessionario.commons.dto.Modello;
import it.unibo.gestione_concessionario.commons.dto.Tipologia;
import it.unibo.gestione_concessionario.model.ModelCliente;
import it.unibo.gestione_concessionario.model.ModelDipendente;
import it.unibo.gestione_concessionario.view.ClienteView;
import it.unibo.gestione_concessionario.view.DipendenteView;
import it.unibo.gestione_concessionario.view.View;

import java.util.List;

public class Controller {
    View view;
    ModelDipendente modelDipendente;
    ModelCliente modelCliente;

    public void initCliente(){
        this.modelCliente=new ModelCliente();
        this.modelCliente.init(ConnectionFactory.build("gestione_concessionario_prova",
                "jdbc:mysql://localhost:3306/", "root", "cadmio"));
    }

    public void initDipendente(){
        this.modelDipendente=new ModelDipendente();
        this.modelDipendente.init(ConnectionFactory.build("gestione_concessionario_prova",
                "jdbc:mysql://localhost:3306/", "root", "cadmio"));
    }

    public boolean checkLoginDipendente(String email, String password){
        if(modelDipendente.checkLoginDipendente(email, password)){
            this.view = new DipendenteView();
            return true;
        }
        else{
            return false;
        }
    }

    public boolean checkLoginCliente(String email, String password){
        if(this.modelCliente.checkLoginCliente(email, password)){
            this.view = new ClienteView(this);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean createCliente(Cliente cliente){
        return this.modelCliente.creaCliente(cliente);
    }


    public void startCliente(){
        this.view.start();
    }

    public void startDipendenteView(){

    }

    public List<Marchio> allMarchi(){
        return this.modelCliente.visualizzaMarchi();
    }

    public List<Modello> allModelli(){
        return this.modelCliente.visualizzaModello();

    }

    public Dipendente dipendenteFromMarchio(Marchio marchio){
     return   this.modelCliente.visualizzaDipendente(marchio);
    }
    public Dipendente dipendeteFromModello(Modello modello){
        return this.modelCliente.visualizzaDipendente(modello);
    }

    public int idFromNameMarchio(String name){
        return modelCliente.visualizzaIDMarchio(name);
    }

    public List<Auto> visualizzaAutoxMarchioxTipologia(Marchio marchio, Tipologia tipologia){
        return modelCliente.visualizzaAutoxMarchioxTipologia(marchio, tipologia);
    }

    public List<Tipologia> allTipologie(){
        return this.modelCliente.visualizzTipologie();
    }

    public List<Auto> allAutoFromModelli(Modello modello){
       return this.modelCliente.visualizzaAutoxModello(modello);
    }

    public int idMarchioFromNomeModello(Modello modello){
        return this.modelCliente.ID_Marchio(modello);
    }

    public boolean addAppuntamento(Appuntamento appuntamento){
        return this.modelCliente.fissaAppuntamento(appuntamento);
    }
    public Cliente getClienteUser(){
        return this.modelCliente.getCliente();
    }
    public int id_ClienteByEmail(String email){
        return this.modelCliente.getClienteIDByEmail(email);
    }
    public int id_DipendenteByEmail(String email){
        return this.modelCliente.getDipendenteIDByEmail(email);
    }

}
