package it.unibo.gestione_concessionario.view.panelscliente;

import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.gestione_concessionario.commons.dto.Dipendente;

import java.awt.GridLayout;

public class SingoloDipendentePanel extends JPanel {
    
    public SingoloDipendentePanel(Dipendente dipendente) {
        setLayout(new GridLayout(5, 2));
        
        JLabel nomeLabel = new JLabel("Nome:");
        JLabel cognomeLabel = new JLabel("Cognome:");
        JLabel telefonoLabel = new JLabel("Telefono:");
        JLabel emailLabel = new JLabel("Email:");
        
        add(nomeLabel);
        add(new JLabel(dipendente.nome()));
        add(cognomeLabel);
        add(new JLabel(dipendente.cognome()));
        add(telefonoLabel);
        add(new JLabel(dipendente.telefono()));
        add(emailLabel);
        add(new JLabel(dipendente.eMail()));

    }
}
