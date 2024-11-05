package it.unibo.gestione_concessionario.commons.dto;
import java.time.LocalDate;
import java.time.LocalTime;
public record Vendita(
    String nuremo_telaio,
    Contratto contratto,
    LocalDate data,
    LocalTime ora,
    int id_dipendente,
    int  codCliente
    
) {

    @Override
    public String toString() {
        return nuremo_telaio +" "+ contratto.toString()+ " il " + data.toString() + " " + ora.toString(); 
    }

}
