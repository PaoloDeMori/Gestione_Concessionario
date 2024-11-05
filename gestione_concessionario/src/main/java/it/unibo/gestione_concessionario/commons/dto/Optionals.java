package it.unibo.gestione_concessionario.commons.dto;

public record Optionals(
    int idOptional,
    String descrizione,
    double prezzo
) {

    @Override
    public String toString() {
        return descrizione + " "+ Double.toString(prezzo);
    }

}
