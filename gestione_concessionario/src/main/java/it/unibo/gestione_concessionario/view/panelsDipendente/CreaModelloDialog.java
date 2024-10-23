package it.unibo.gestione_concessionario.view.panelsDipendente;

import javax.swing.*;

import it.unibo.gestione_concessionario.commons.dto.Marchio;
import it.unibo.gestione_concessionario.commons.dto.Modello;
import it.unibo.gestione_concessionario.commons.dto.Tipologia;
import it.unibo.gestione_concessionario.controller.Controller;
import it.unibo.gestione_concessionario.view.CustomButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreaModelloDialog extends JDialog {
    private Modello modello; 
    private JTextField descrizioneField;
    private JComboBox<Tipologia> tipologiaBox;
    private Marchio marchio;
    private JLabel annoLabel = new JLabel("Anno:");
    private JTextField annoField;
    private boolean isConfirmed = false;
    private Controller controller;

    public CreaModelloDialog(Controller controller, AddAutoDipendente addVenditaPanel) {
        super();
        this.controller = controller;
        setLayout(new GridLayout(7, 2, 5, 5)); // Layout per il dialog

        this.setTitle("Crea Modello");
        add(new JLabel("Descrizione:"));
        descrizioneField = new JTextField();
        add(descrizioneField);

        add(new JLabel("Tipologia:"));
        Tipologia[] options = controller.getTipologie().toArray(new Tipologia[0]);
        tipologiaBox = new JComboBox<Tipologia>(options);
        add(tipologiaBox);

        add(annoLabel);
        annoField = new JTextField();
        add(annoField);

        add(new JLabel("Marchio:"));
        marchio=controller.getUserMarchio();
        add(new JLabel(marchio.toString()));

        // Pulsante per confermare il contratto
        JButton salvaContrattoButton = new CustomButton("Salva Modello");
        salvaContrattoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvaContratto();
                if (isConfirmed) {
                    controller.addModello(modello);
                    addVenditaPanel.addModello();
                    JOptionPane.showMessageDialog(addVenditaPanel,
                            "Modello inserito con successo: " + modello.toString());
                } else {
                    JOptionPane.showMessageDialog(addVenditaPanel, "Modello non valido");
                }
            }
        });
        add(new JLabel()); // Spazio vuoto per il layout
        add(salvaContrattoButton);

        // Imposta la dimensione del dialog
        setSize(400, 300);
    }

    // Metodo per salvare il contratto
    private void salvaContratto() {
        try {
            // Validazione del prezzo
            int anno = Integer.parseInt(annoField.getText());
            String tipologia = (String)(tipologiaBox.getSelectedItem()).toString();
            int id_tipologia =((Tipologia)(tipologiaBox.getSelectedItem())).idTipologia();

            // Creazione del contratto
            modello = new Modello(
                    descrizioneField.getText(),
                    anno,
                    tipologia,
                    controller.getUserMarchio().nome(),
                    id_tipologia);
            isConfirmed = true; // Imposta che il contratto è stato confermato
            dispose(); // Chiudi il dialog
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Errore nei dati inseriti"+ ex.getMessage());
        }
        catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Errore nei dati inseriti " + ex.getMessage());
        }
    }

    // Metodo per ottenere il contratto creato
    public Modello getModello() {
        return modello;
    }

    // Metodo per verificare se il contratto è stato confermato
    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void start() {
        this.setVisible(true);
    }
}