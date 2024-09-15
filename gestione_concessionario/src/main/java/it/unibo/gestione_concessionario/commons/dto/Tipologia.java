package it.unibo.gestione_concessionario.commons.dto;

public record Tipologia(
        int idTipologia,
        String nome,
        String caratteristiche) {

        @Override
        public String toString() {
                return nome;
        }

}
