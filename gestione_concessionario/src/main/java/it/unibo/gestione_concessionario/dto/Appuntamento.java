package it.unibo.gestione_concessionario.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
public record Appuntamento(
    int idAppuntamento,
    LocalDate data,
    LocalTime ora,
    String tipologia,
    Optional<Integer> durata,
    Auto nuremo_telaio,
    Dipendente dipendente,
    Cliente cliente
) {

}
