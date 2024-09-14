package it.unibo.gestione_concessionario.commons.dto;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
public record Vendita(
    int idVendita,
    String nuremo_telaio,
    int idContratto,
    LocalDate data,
    LocalTime ora,
    Optional<String> tipologia,
    int id_dipendente,
    int  codCliente
    
) {

}
