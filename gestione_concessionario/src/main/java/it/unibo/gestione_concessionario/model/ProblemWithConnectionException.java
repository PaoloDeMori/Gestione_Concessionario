package it.unibo.gestione_concessionario.model;

public class ProblemWithConnectionException extends RuntimeException{

    public ProblemWithConnectionException(Exception e){
        super(e);
    }
    
}