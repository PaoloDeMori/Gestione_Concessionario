package it.unibo.gestione_concessionario.commons.dto;

public record Marchio(
    int idMarchio,
    String nome
){

    @Override
    public String toString() {
        return this.nome();
    }}
