package it.unibo.gestione_concessionario.dto;
 import java.util.Optional;
public record Contratto(
    int idContratto,
    double prezzo,
    String tipologia,
    Optional<String> nomeBanca,
    Optional<String> codiceFinanziamento,
    Optional<String> intestatario,
    Optional<String> metodoDiPagamento
) {

}
