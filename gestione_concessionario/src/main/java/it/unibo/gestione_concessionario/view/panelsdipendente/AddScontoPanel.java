package it.unibo.gestione_concessionario.view.panelsdipendente;

import javax.swing.*;

import it.unibo.gestione_concessionario.commons.dto.Auto;
import it.unibo.gestione_concessionario.commons.dto.Modello;
import it.unibo.gestione_concessionario.commons.dto.Sconto;
import it.unibo.gestione_concessionario.controller.Controller;
import it.unibo.gestione_concessionario.view.CustomButton;
import it.unibo.gestione_concessionario.view.View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class AddScontoPanel extends JPanel {

    private JPanel maiPanel;
    private JSpinner spData;
    private JSpinner spData2;
    private JSpinner spNumero;
    private JComboBox<Modello>comboModello;
    private JComboBox<Auto> comboNumeroTelaio;
    private Controller controller;
    CustomButton saveSconto;

    public AddScontoPanel(Controller controller) {
        setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel title = new JLabel("Aggiungi Uno Sconto");
        title.setFont(View.titleFont);
        title.setHorizontalAlignment(JLabel.CENTER);
        titlePanel.add(title);
        this.add(titlePanel,BorderLayout.NORTH);

        maiPanel = new JPanel(new GridLayout(9, 2, 5, 5));
        this.controller = controller;
        maiPanel.add(new JLabel("Data Inizio:"));
        spData = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spData, "yyyy-MM-dd");
        spData.setEditor(dateEditor);
        maiPanel.add(spData);

        maiPanel.add(new JLabel("Data Fine:"));
        spData2 = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor2 = new JSpinner.DateEditor(spData, "yyyy-MM-dd");
        spData2.setEditor(dateEditor2);
        maiPanel.add(spData2);

        maiPanel.add(new JLabel("Numero (1-100):"));
        spNumero = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1)); 
        maiPanel.add(spNumero);

        maiPanel.add(new JLabel("Modello:"));
        comboModello = new JComboBox<Modello>(getModelli().stream().toArray(Modello[]::new));
        comboModello.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAuto();
            }

        });
        maiPanel.add(comboModello);

        maiPanel.add(new JLabel("Numero Telaio:"));
        comboNumeroTelaio = new JComboBox<Auto>(
                getAuto((Modello) comboModello.getSelectedItem()).stream().toArray(Auto[]::new));
                maiPanel.add(comboNumeroTelaio);

        saveSconto = new CustomButton("Inserisci");
        maiPanel.add(saveSconto);

        saveSconto.addActionListener(e -> {
            try {
                Sconto sconto = this.getSconto();
                controller.addSconto(sconto);
                JOptionPane.showMessageDialog(this, "Sconto creato:\n" + sconto);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore nella creazione dello Sconto: " + ex.getMessage());
            }
        });
        this.add(maiPanel,BorderLayout.CENTER);

    }

    public Sconto getSconto() {
        LocalDate dataInizio = ((java.util.Date) spData.getValue()).toInstant().atZone(java.time.ZoneId.systemDefault())
                .toLocalDate();
        LocalDate dataFine = ((java.util.Date) spData2.getValue()).toInstant().atZone(java.time.ZoneId.systemDefault())
                .toLocalDate();
        int percentuale = ((int) spNumero.getValue());

        return new Sconto(
                percentuale,
                dataInizio,
                dataFine,
                ((Auto) comboNumeroTelaio.getSelectedItem()).getNumero_telaio());
    }

    private List<Modello> getModelli() {
        return controller.allModelli();
    }

    private List<Auto> getAuto(Modello modello) {
        return controller.allAutoNonVenduteFromModelli(modello);
    }

    private void updateAuto() {
        comboNumeroTelaio.removeAllItems();
        Auto[] auto = getAuto((Modello) comboModello.getSelectedItem()).stream().toArray(Auto[]::new);
        for (Auto auto1 : auto) {
            comboNumeroTelaio.addItem(auto1);
        }
        comboNumeroTelaio.revalidate();
        comboNumeroTelaio.repaint();
    }

}