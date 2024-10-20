package it.unibo.gestione_concessionario.view.panelsDipendente;

import javax.swing.*;
import it.unibo.gestione_concessionario.commons.dto.Auto;
import it.unibo.gestione_concessionario.commons.dto.Cliente;
import it.unibo.gestione_concessionario.commons.dto.Configurazione;
import it.unibo.gestione_concessionario.commons.dto.Contratto;
import it.unibo.gestione_concessionario.commons.dto.Dipendente;
import it.unibo.gestione_concessionario.commons.dto.Modello;
import it.unibo.gestione_concessionario.commons.dto.Vendita;
import it.unibo.gestione_concessionario.controller.Controller;
import it.unibo.gestione_concessionario.view.CustomButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Calendar;

public class AddAutoDipendente extends JPanel {

    private JTextField numeroTelaioField;
    private JTextField prezzoField;
    private JButton creaContrattoButton;
    private JComboBox<Boolean> immatricolazioneBox;
    private JLabel targaLabel = new JLabel("Targa");
    private JTextField targaaField;
    private JLabel motoreLabel = new JLabel("Motore");
    private JTextField motoreField;
    private JLabel alimentaioneLabel = new JLabel("Alimentazione");
    private JTextField alimentazioneField;
    private JLabel ccLabel = new JLabel("cc");
    private JTextField ccField;
    private JLabel hpLabel = new JLabel("Horse Power");
    private JTextField hpField;

    private JLabel dataLabel = new JLabel("Data immatricolazione");
    private JSpinner spData;

    private JComboBox<Modello> tfmodello;
    private Controller controller;
    private CustomButton saveAuto;
    private ContrattoDialog contrattoDialog;
    private String descrizioneModello;
    private String motore;
    private String alimentazione;
    private JLabel creaContrattoLabel = new JLabel("Contratto da Creare");
    private JPanel maiPanel;

    public AddAutoDipendente(Controller controller) {
        setLayout(new GridLayout(1, 1));
        this.controller = controller;

        this.setMainPanel();

        this.add(maiPanel);
    }

    private void setMainPanel() {
        maiPanel = new JPanel();
        maiPanel.setLayout(new GridLayout(12, 2, 5, 5));

        maiPanel.add(creaContrattoLabel);
        creaContrattoButton = new CustomButton("Crea Contratto");
        maiPanel.add(creaContrattoButton);
        // contrattoDialog = new ContrattoDialog(controller, this);
        creaContrattoButton.addActionListener(e -> contrattoDialog.setVisible(true));

        maiPanel.add(new JLabel("Numero Telaio:"));
        numeroTelaioField = new JTextField("00000000000000000");
        maiPanel.add(numeroTelaioField);

        maiPanel.add(new JLabel("Prezzo:"));
        prezzoField = new JTextField("0000");
        maiPanel.add(prezzoField);

        Boolean[] options = { true, false };
        maiPanel.add(new JLabel("Immatricolazione:"));
        immatricolazioneBox = new JComboBox<Boolean>(options);
        immatricolazioneBox.addActionListener(e -> updateAvailableFields());
        maiPanel.add(immatricolazioneBox);

        maiPanel.add(targaLabel);
        targaaField = new JTextField();
        maiPanel.add(targaaField);

        maiPanel.add(motoreLabel);
        motoreField = new JTextField();
        maiPanel.add(motoreField);

        maiPanel.add(alimentaioneLabel);
        alimentazioneField = new JTextField();
        maiPanel.add(alimentazioneField);

        maiPanel.add(ccLabel);
        ccField = new JTextField();
        maiPanel.add(ccField);

        maiPanel.add(hpLabel);
        hpField = new JTextField();
        maiPanel.add(hpField);

        maiPanel.add(dataLabel);
        spData = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spData, "yyyy-MM-dd");
        spData.setEditor(dateEditor);
        maiPanel.add(spData);

        maiPanel.add(new JLabel("Modello:"));
        tfmodello = new JComboBox<>(getModelli().stream().toArray(Modello[]::new));
        tfmodello.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                descrizioneModello = ((Modello)tfmodello.getSelectedItem()).descrizione();
            }

        });
        maiPanel.add(tfmodello);

        saveAuto = new CustomButton("Aggiungi auto");
        maiPanel.add(saveAuto);
        saveAuto.addActionListener(e -> {
            try {
                Auto auto = getAuto();
                Configurazione configurazione = getConfigurazione();
                
                if (auto != null && configurazione != null) {
                    controller.createAutoEConfig(auto, configurazione);
                    JOptionPane.showMessageDialog(this, "Auto creata:\n" + auto);
                    this.removeAll();
                    setMainPanel();
                    this.add(maiPanel);
                    this.revalidate();
                    this.repaint();
                } else {
                    JOptionPane.showMessageDialog(this, "Errore nella creazione dell'auto: controlla i dati inseriti.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore nella creazione dell'auto: " + ex.getMessage());
            }
        });

    }

    public Auto getAuto() {
        try {
            double prezzo = Double.parseDouble(prezzoField.getText());
            LocalDate data = ((Date) spData.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (descrizioneModello == null) {
                descrizioneModello = ((Modello) tfmodello.getSelectedItem()).descrizione();
            }
            return new Auto(
                    numeroTelaioField.getText(),
                    prezzo,
                    (Boolean) immatricolazioneBox.getSelectedItem(),
                    Optional.of(targaaField.getText()),
                    Optional.of(data),
                    descrizioneModello,
                    motoreField.getText(),
                    alimentazioneField.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Errore nei dati inseriti: il prezzo deve essere un numero valido.");
            return null;
        }
    }

    public Configurazione getConfigurazione() {
        try {
            int cc = Integer.parseInt(ccField.getText());
            int hp = Integer.parseInt(hpField.getText());
            return new Configurazione(
                    motoreField.getText(),
                    alimentazioneField.getText(),
                    cc,
                    hp,
                    ((Modello) tfmodello.getSelectedItem()).idModello());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Errore nei dati inseriti: il campo cc e HP devono essere numeri validi.");
            return null;
        }
    }

    private List<Modello> getModelli() {
        return controller.allModelli();
    }

    public void updateAvailableFields() {
        if ((Boolean) immatricolazioneBox.getSelectedItem()) {
            targaaField.setVisible(true);
            spData.setVisible(true);
        } else {
            targaaField.setVisible(false);
            spData.setVisible(false);
        }
    }
}
