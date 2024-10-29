package it.unibo.gestione_concessionario.view.panelsdipendente;

import javax.swing.*;

import it.unibo.gestione_concessionario.commons.dto.Marchio;
import it.unibo.gestione_concessionario.commons.dto.Modello;
import it.unibo.gestione_concessionario.commons.dto.Tipologia;
import it.unibo.gestione_concessionario.controller.Controller;
import it.unibo.gestione_concessionario.view.CustomButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class CreaModelloDialog extends JDialog {
    private Modello modello;
    private JTextField descrizioneField;
    private JComboBox<Tipologia> tipologiaBox;
    private Marchio marchio;
    private JLabel annoLabel = new JLabel("Anno:");
    private JTextField annoField;
    private boolean isConfirmed = false;
    private Controller controller;
    private Tipologia[] options;
    AddTipologiaDialog addTipologiaDialog;

    public CreaModelloDialog(Controller controller, AddAutoDipendente addVenditaPanel) {
        super();
        this.controller = controller;
        setLayout(new GridLayout(7, 2, 5, 5));

        this.setTitle("Crea Modello");
        add(new JLabel("Descrizione:"));
        descrizioneField = new JTextField();
        add(descrizioneField);
        addTipologiaDialog = new AddTipologiaDialog(controller, this);

        JButton nuovaTipologiaButton = new CustomButton("Nuova Tipologia");
        nuovaTipologiaButton.addActionListener(e -> addTipologiaDialog.start());
        add(new JLabel());
        add(nuovaTipologiaButton);

        add(new JLabel("Tipologia:"));
        options = getTipologie().toArray(new Tipologia[0]);
        tipologiaBox = new JComboBox<>(options);
        add(tipologiaBox);

        add(annoLabel);
        annoField = new JTextField();
        add(annoField);

        add(new JLabel("Marchio:"));
        marchio = controller.getUserMarchio();
        add(new JLabel(marchio.toString()));

        JButton salvaContrattoButton = new CustomButton("Salva Modello");
        salvaContrattoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvaContratto();
                if (isConfirmed) {
                    try {
                        controller.addModello(modello);
                    } catch (SQLException e1) {
                        JOptionPane.showMessageDialog(addVenditaPanel,
                                "Impossibile aggiungere il modello " + e1.getMessage());
                    }
                    addVenditaPanel.addModello();
                    JOptionPane.showMessageDialog(addVenditaPanel,
                            "Modello inserito con successo: " + modello.toString());
                } else {
                    JOptionPane.showMessageDialog(addVenditaPanel, "Modello non valido");
                }
            }
        });
        add(new JLabel());
        add(salvaContrattoButton);

        setSize(400, 300);
    }

    private void salvaContratto() {
        try {
            int anno = Integer.parseInt(annoField.getText());
            String tipologia = (String) (tipologiaBox.getSelectedItem()).toString();
            int id_tipologia = ((Tipologia) (tipologiaBox.getSelectedItem())).idTipologia();

            modello = new Modello(
                    descrizioneField.getText(),
                    anno,
                    tipologia,
                    controller.getUserMarchio().nome(),
                    id_tipologia);
            isConfirmed = true; 
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Errore nei dati inseriti" + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Errore nei dati inseriti " + ex.getMessage());
        }
    }

    public Modello getModello() {
        return modello;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void start() {
        this.setVisible(true);
    }

    List<Tipologia> getTipologie() {

        return controller.getTipologie();
    }

    void updateTipologie() {
        options = getTipologie().toArray(new Tipologia[0]);
        tipologiaBox.removeAllItems();
        for (Tipologia t : options) {
            tipologiaBox.addItem(t);
        }
        tipologiaBox.revalidate();
        tipologiaBox.repaint();
    }
}