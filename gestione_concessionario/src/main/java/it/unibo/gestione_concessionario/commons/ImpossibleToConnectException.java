package it.unibo.gestione_concessionario.commons;

public class ImpossibleToConnectException extends RuntimeException {
    public ImpossibleToConnectException(Exception e) {
        super(e);
    }

}
