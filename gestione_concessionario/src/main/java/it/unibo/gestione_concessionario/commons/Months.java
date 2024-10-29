package it.unibo.gestione_concessionario.commons;

public enum Months {
    GENNAIO(1),
    FEBBRAIO(2),
    MARZO(3),
    APRILE(4),
    MAGGIO(5),
    GIUGNO(6),
    LUGLIO(7),
    AGOSTO(8),
    SETTEMBRE(9),
    OTTOBRE(10),
    NOVEMBRE(11),
    DICEMBRE(12);

    private final int numeroMese;

    Months(int numeroMese) {
        this.numeroMese = numeroMese;
    }

    public int getNumeroMese() {
        return numeroMese;
    }
}
