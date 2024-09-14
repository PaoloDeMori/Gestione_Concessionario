package it.unibo.gestione_concessionario.controller;


import it.unibo.gestione_concessionario.commons.ConnectionFactory;
import it.unibo.gestione_concessionario.commons.dto.Cliente;
import it.unibo.gestione_concessionario.model.ModelCliente;
import it.unibo.gestione_concessionario.model.ModelDipendente;
import it.unibo.gestione_concessionario.view.ClienteView;
import it.unibo.gestione_concessionario.view.DipendenteView;
import it.unibo.gestione_concessionario.view.View;

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
            this.view = new ClienteView();
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

    }

    public void startDipendenteView(){

    }


}
