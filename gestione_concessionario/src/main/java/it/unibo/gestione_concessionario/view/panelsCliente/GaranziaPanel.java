package it.unibo.gestione_concessionario.view.panelscliente;

 import javax.swing.*;
import java.awt.*;
import it.unibo.gestione_concessionario.commons.dto.Garanzia;

public class GaranziaPanel extends JPanel {

    public GaranziaPanel() {
        setLayout(new GridLayout(3, 2, 10, 10)); 

    }

    public void setGaranziaPanel(Garanzia garanzia){
        JLabel idGaranziaTextLabel = new JLabel("ID Garanzia:");
        JLabel scadenzaTextLabel = new JLabel("Scadenza:");
        JLabel coperturaTextLabel = new JLabel("Copertura:");


        JLabel idGaranziaLabel = new JLabel(String.valueOf(garanzia.idGaranzia()));
        JLabel scadenzaLabel = new JLabel(garanzia.scadenza().toString());
        JLabel coperturaLabel = new JLabel(garanzia.copertura());

        add(idGaranziaTextLabel);
        add(idGaranziaLabel);

        add(scadenzaTextLabel);
        add(scadenzaLabel);

        add(coperturaTextLabel);
        add(coperturaLabel);
    }

}
