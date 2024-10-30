package it.unibo.gestione_concessionario.commons;

public class ImpossibleToConnectException extends RuntimeException {
    public ImpossibleToConnectException(String string) {
        super(string);
    }
    public ImpossibleToConnectException(Exception e) {
        super(e.getMessage());
    }

}
