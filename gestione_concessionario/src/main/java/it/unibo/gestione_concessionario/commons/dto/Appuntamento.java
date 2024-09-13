package it.unibo.gestione_concessionario.commons.dto;

import java.time.LocalDate;
import java.time.LocalTime;
public record Appuntamento(
    int idAppuntamento,
    LocalDate data,
    LocalTime ora,
    String tipologia,
    LocalTime durata,
    String numero_telaio,
    String  nome_dipendente,
    String  nome_cliente
) {

}
