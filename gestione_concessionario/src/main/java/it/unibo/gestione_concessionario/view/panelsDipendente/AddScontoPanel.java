package it.unibo.gestione_concessionario.view.panelsDipendente;

import javax.swing.*;

import it.unibo.gestione_concessionario.commons.dto.Auto;
import it.unibo.gestione_concessionario.commons.dto.Modello;
import it.unibo.gestione_concessionario.commons.dto.Sconto;
import it.unibo.gestione_concessionario.controller.Controller;
import it.unibo.gestione_concessionario.view.CustomButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class AddScontoPanel extends JPanel {

    private JSpinner spData;
    private JSpinner spData2;
    private JSpinner spNumero;
    private JComboBox<Modello> tfmodello;
    private JComboBox<Auto> tfNumeroTelaio;
    private Controller controller;
    CustomButton saveSconto;

    public AddScontoPanel(Controller controller) {
        setLayout(new GridLayout(9, 2, 5, 5)); // 8 righe, 2 colonne con spazi di 5px tra le celle
        this.controller = controller;
        add(new JLabel("Data Inizio:"));
        spData = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spData, "yyyy-MM-dd");
        spData.setEditor(dateEditor);
        add(spData);

        add(new JLabel("Data Fine:"));
        spData2 = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor2 = new JSpinner.DateEditor(spData, "yyyy-MM-dd");
        spData2.setEditor(dateEditor2);
        add(spData2);

        add(new JLabel("Numero (1-100):")); // Etichetta per il JSpinner
        spNumero = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1)); // JSpinner per il numero da 1 a 100
        add(spNumero);

        add(new JLabel("Modello:"));
        tfmodello = new JComboBox<Modello>(getModelli().stream().toArray(Modello[]::new));
        tfmodello.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAuto();
            }

        });
        add(tfmodello);

        add(new JLabel("Numero Telaio:"));
        tfNumeroTelaio = new JComboBox<Auto>(
                getAuto((Modello) tfmodello.getSelectedItem()).stream().toArray(Auto[]::new));
        add(tfNumeroTelaio);

        saveSconto = new CustomButton("Inserisci");
        this.add(saveSconto);

        saveSconto.addActionListener(e -> {
            try {
                Sconto sconto = this.getSconto();
                controller.addSconto(sconto);
                JOptionPane.showMessageDialog(this, "Sconto creato:\n" + sconto);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore nella creazione dello Sconto: " + ex.getMessage());
            }
        });

    }

    // Metodo per ottenere i dati dall'interfaccia
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
                ((Auto) tfNumeroTelaio.getSelectedItem()).getNumero_telaio());
    }

    private List<Modello> getModelli() {
        return controller.allModelli();
    }

    private List<Auto> getAuto(Modello modello) {
        return controller.allAutoFromModelli(modello);
    }

    private void updateAuto() {
        tfNumeroTelaio.removeAllItems();
        Auto[] auto = getAuto((Modello) tfmodello.getSelectedItem()).stream().toArray(Auto[]::new);
        for (Auto auto1 : auto) {
            tfNumeroTelaio.addItem(auto1);
        }
        tfNumeroTelaio.revalidate();
        tfNumeroTelaio.repaint();
    }

}