package it.unibo.gestione_concessionario.commons.dto;
import java.time.LocalDate;
public record Sconto(
    int percentuale,
    LocalDate dataInizio,
    LocalDate dataFine,
    String nuremo_telaio
) {

    @Override
    public String toString() {
        return Integer.toString(percentuale) + "% da " + dataInizio.toString() + " a "+ dataFine.toString()+ " su auto: " +nuremo_telaio; 
    }


}
