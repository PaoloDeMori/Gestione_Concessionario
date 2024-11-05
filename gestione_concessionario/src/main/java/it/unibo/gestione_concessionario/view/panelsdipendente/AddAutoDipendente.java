package it.unibo.gestione_concessionario.view.panelsdipendente;

import javax.swing.*;
import it.unibo.gestione_concessionario.commons.dto.Auto;
import it.unibo.gestione_concessionario.commons.dto.Configurazione;
import it.unibo.gestione_concessionario.commons.dto.Modello;
import it.unibo.gestione_concessionario.commons.dto.Optionals;
import it.unibo.gestione_concessionario.commons.dto.Personalizzazione;
import it.unibo.gestione_concessionario.controller.Controller;
import it.unibo.gestione_concessionario.view.CustomButton;
import it.unibo.gestione_concessionario.view.View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class AddAutoDipendente extends JPanel {

    private JTextField numeroTelaioField;
    private JTextField prezzoField;
    private JButton creaModelloButton;
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

    private JLabel immatricolazioneLabel = new JLabel("Immatricolazione:");

    private JComboBox<Modello>combomodello;
    private List<Optionals> optionals;

    private Controller controller;

    private CustomButton saveAuto;
    private CustomButton addOptionalButton;
    private JCheckBox configurazioneSetter = new JCheckBox("Nuova configurazione?");
    private JLabel confLabel = new JLabel("Configurazione:");
    private JComboBox<Configurazione> configurazioneList;
    private boolean isPers = false;
    private Configurazione selectedConf;

    private CreaModelloDialog creaModelloDialog;
    private AddOptionalsDialog addOptionalsDialog;
    private String descrizioneModello;
    private JPanel maiPanel;
    Auto auto;

    public AddAutoDipendente(Controller controller) {
        creaModelloDialog = new CreaModelloDialog(controller, this);
        setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel title = new JLabel("Aggiungi Un Auto");
        title.setFont(View.titleFont);
        title.setHorizontalAlignment(JLabel.CENTER);
        titlePanel.add(title);
        this.add(titlePanel, BorderLayout.NORTH);

        this.controller = controller;
        addOptionalsDialog = new AddOptionalsDialog(this, controller);

        this.setMainPanel();

        this.add(maiPanel, BorderLayout.CENTER);
    }

    private void setMainPanel() {
        maiPanel = new JPanel();
        maiPanel.setLayout(new GridLayout(16, 2, 5, 5));

        configurazioneSetter.setSelected(false);
        configurazioneSetter.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                switchConfigurations();
            }

        });

        maiPanel.add(configurazioneSetter);
        creaModelloButton = new CustomButton("Crea Modello");
        maiPanel.add(creaModelloButton);
        creaModelloButton.addActionListener(e -> creaModelloDialog.setVisible(true));

        maiPanel.add(new JLabel("Numero Telaio:"));
        numeroTelaioField = new JTextField("00000000000000000");
        maiPanel.add(numeroTelaioField);

        maiPanel.add(new JLabel("Modello:"));
        combomodello = new JComboBox<>(getModelli().stream().toArray(Modello[]::new));
        combomodello.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                descrizioneModello = ((Modello) combomodello.getSelectedItem()).descrizione();
                updateConfigurazioniList();
            }

        });
        maiPanel.add(combomodello);

        maiPanel.add(new JLabel("Optionals:"));
        addOptionalButton = new CustomButton("Aggiungi optionals");
        maiPanel.add(addOptionalButton);
        addOptionalButton.addActionListener(e -> addOptionalsDialog.start());

        maiPanel.add(new JLabel("Prezzo:"));
        prezzoField = new JTextField("0000");
        maiPanel.add(prezzoField);

        Boolean[] options = { true, false };
        maiPanel.add(immatricolazioneLabel);
        immatricolazioneBox = new JComboBox<>(options);
        immatricolazioneBox.addActionListener(e -> updateAvailableFields());
        maiPanel.add(immatricolazioneBox);

        maiPanel.add(targaLabel);
        targaaField = new JTextField();
        maiPanel.add(targaaField);

        maiPanel.add(confLabel);
        updateConfigurazioniList();
        configurazioneList.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                selectedConf = (Configurazione) configurazioneList.getSelectedItem();
            }

        });
        maiPanel.add(configurazioneList);

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

        saveAuto = new CustomButton("Aggiungi auto");
        maiPanel.add(saveAuto);
        saveAuto.addActionListener(e -> {
            try {
                auto = getAuto();
                Configurazione configurazione = getConfigurazione();

                if (auto != null && configurazione != null) {
                    if (isPers == true) {
                        controller.createAutoEConfig(auto, configurazione);
                    } else {
                        auto.setIdConfigurazione(configurazione.id_Configurazione());
                        controller.createAuto(auto);
                    }
                    List<Personalizzazione> pers = getPersonalizzazioni();
                    if (!pers.isEmpty()) {
                        for (Personalizzazione p : getPersonalizzazioni()) {
                            controller.addPersonalizzazione(p);
                        }
                    }
                    if (JOptionPane.showConfirmDialog(this, "Auto Creata, è presente una garanzia?",
                            "Auto creata con successo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                            null) == JOptionPane.YES_OPTION) {
                        new AddGaranziaDialog(controller, auto.getNumero_telaio()).start();
                    }
                    this.removeAll();
                    setMainPanel();
                    optionals = null;
                    addOptionalsDialog = new AddOptionalsDialog(this, controller);
                    this.add(maiPanel);
                    this.switchConfigurations();
                    this.revalidate();
                    this.repaint();
                } else {
                    JOptionPane.showMessageDialog(this, "Errore nella creazione dell'auto: controlla i dati inseriti.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore nella creazione dell'auto: " + ex.getMessage());
            }
        });

        ccLabel.setVisible(false);
        ccField.setVisible(false);
        hpField.setVisible(false);
        hpLabel.setVisible(false);
        motoreField.setVisible(false);
        motoreLabel.setVisible(false);
        alimentaioneLabel.setVisible(false);
        alimentazioneField.setVisible(false);

    }

    public Auto getAuto() {
        try {
            double prezzo = Double.parseDouble(prezzoField.getText());
            LocalDate data = ((Date) spData.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (descrizioneModello == null) {
                descrizioneModello = ((Modello) combomodello.getSelectedItem()).descrizione();
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

    public List<Personalizzazione> getPersonalizzazioni() {
        if (auto != null) {
            if (optionals != null) {
                List<Optionals> selectedOptionals = optionals;

                List<Personalizzazione> personalizzazioni = new ArrayList<>();
                for (Optionals optional : selectedOptionals) {
                    personalizzazioni.add(new Personalizzazione(optional, auto));
                }
                return personalizzazioni;
            } else {
                return List.of();
            }
        } else {
            throw new IllegalArgumentException("Impossibile creare una personalizzazione senza un'auto.");
        }
    }

    public Configurazione getConfigurazione() {
        if (isPers == true) {
            try {
                int cc = Integer.parseInt(ccField.getText());
                int hp = Integer.parseInt(hpField.getText());
                return new Configurazione(
                        motoreField.getText(),
                        alimentazioneField.getText(),
                        cc,
                        hp,
                        ((Modello) combomodello.getSelectedItem()).idModello());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Errore nei dati inseriti: il campo cc e HP devono essere numeri validi.");
                return null;
            }
        } else {
            return selectedConf;
        }
    }

    private List<Modello> getModelli() {
        return controller.allModelli();
    }

    private void updateConfigurazioniList() {
        try {
            if (configurazioneList == null) {
                configurazioneList = new JComboBox<>(
                        controller.visualizzaConfigurazioni((Modello) combomodello.getSelectedItem()).stream()
                                .toArray(Configurazione[]::new));
            } else {
                configurazioneList.removeAllItems();
                for (Configurazione c : controller.visualizzaConfigurazioni((Modello) combomodello.getSelectedItem())) {
                    configurazioneList.addItem(c);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Impossibile visualizzare configurazioni",
                    JOptionPane.ERROR_MESSAGE);
        }
        configurazioneList.revalidate();
        configurazioneList.repaint();
        this.revalidate();
        this.repaint();
    }

    protected List<Optionals> getOptional() {
        return controller.visualizzaAllOptionals();
    }

    public void addModello() {
        this.removeAll();
        this.setMainPanel();
        this.add(maiPanel);
        combomodello = new JComboBox<>(getModelli().stream().toArray(Modello[]::new));
        this.revalidate();
        this.repaint();
    }

    public void updateAvailableFields() {
        if ((Boolean) immatricolazioneBox.getSelectedItem()) {
            targaaField.setVisible(true);
            targaLabel.setVisible(true);
            dataLabel.setVisible(true);
            spData.setVisible(true);
            immatricolazioneLabel.setVisible(true);
        } else {
            targaaField.setVisible(false);
            targaLabel.setVisible(false);
            dataLabel.setVisible(false);
            spData.setVisible(false);
            immatricolazioneLabel.setVisible(false);
        }
        this.revalidate();
        this.repaint();
    }

    public void setOptionals(List<Optionals> optionals) {
        this.optionals = optionals;
    }

    private void switchConfigurations() {
        if (configurazioneSetter.isSelected()) {
            isPers = true;
            ccLabel.setVisible(true);
            ccField.setVisible(true);
            hpField.setVisible(true);
            hpLabel.setVisible(true);
            motoreField.setVisible(true);
            motoreLabel.setVisible(true);
            alimentaioneLabel.setVisible(true);
            alimentazioneField.setVisible(true);
            confLabel.setVisible(false);
            configurazioneList.setVisible(false);
        } else {
            isPers = false;
            confLabel.setVisible(true);
            configurazioneList.setVisible(true);
            ccLabel.setVisible(false);
            ccField.setVisible(false);
            hpField.setVisible(false);
            hpLabel.setVisible(false);
            motoreField.setVisible(false);
            motoreLabel.setVisible(false);
            alimentaioneLabel.setVisible(false);
            alimentazioneField.setVisible(false);
        }
        this.revalidate();
        this.repaint();
    }
}
