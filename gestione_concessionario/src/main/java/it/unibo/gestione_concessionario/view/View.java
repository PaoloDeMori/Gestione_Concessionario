package it.unibo.gestione_concessionario.view;

public interface View {
    void start();
    void stop();
    void error(String errore,String tipoDiErrore);
    void refreshGui();
    
}
