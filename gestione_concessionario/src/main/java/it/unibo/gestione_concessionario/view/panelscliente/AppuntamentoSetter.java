package it.unibo.gestione_concessionario.view.panelscliente;

import javax.swing.*;

import it.unibo.gestione_concessionario.commons.dto.Appuntamento;
import it.unibo.gestione_concessionario.commons.dto.Auto;
import it.unibo.gestione_concessionario.commons.dto.Dipendente;
import it.unibo.gestione_concessionario.commons.dto.Modello;
import it.unibo.gestione_concessionario.controller.Controller;
import it.unibo.gestione_concessionario.view.CustomButton;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AppuntamentoSetter extends JPanel {

    private JSpinner spData;
    private JSpinner spOra;
    private JComboBox<Modello> combomodello;
    private JSpinner spDurata;
    private JComboBox<Auto> comboNumeroTelaio;
    private JLabel tfNomeDipendente;
    private JLabel tfNomeCliente;
    private Controller controller;
    private Dipendente dipendente;
    CustomButton saveAppuntamentoTestDrive;
    CustomButton saveAppuntamentoConsulenza;

    public AppuntamentoSetter(Controller controller) {
        setLayout(new GridLayout(9, 2, 5, 5));
        this.controller = controller;

        add(new JLabel("Data:"));
        spData = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spData, "yyyy-MM-dd");
        spData.setEditor(dateEditor);
        add(spData);

        add(new JLabel("Ora:"));
        spOra = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(spOra, "HH:mm");
        spOra.setEditor(timeEditor);
        add(spOra);

        add(new JLabel("Modello:"));
        combomodello = new JComboBox<>(getModelli().stream().toArray(Modello[]::new));
        combomodello.addActionListener(e -> {
            updateAuto();
            updateDipendente();
        });
        add(combomodello);

        add(new JLabel("Durata:"));
        spDurata = new JSpinner(new SpinnerDateModel());
        timeEditor = new JSpinner.DateEditor(spDurata, "HH:mm");
        spDurata.setEditor(timeEditor);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date zeroTime = calendar.getTime();
        spDurata.setValue(zeroTime);

        add(spDurata);

        add(new JLabel("Numero Telaio:"));
        comboNumeroTelaio = new JComboBox<>(
                getAuto((Modello) combomodello.getSelectedItem()).stream().toArray(Auto[]::new));
        add(comboNumeroTelaio);

        add(new JLabel("Nome Dipendente:"));
        tfNomeDipendente = new JLabel();
        updateDipendente();
        add(tfNomeDipendente);

        add(new JLabel("Nome Cliente:"));
        tfNomeCliente = new JLabel(controller.getClienteUser().nome() + " " + controller.getClienteUser().cognome());
        add(tfNomeCliente);

        saveAppuntamentoTestDrive = new CustomButton("Inserisci Test-Drive");
        this.add(saveAppuntamentoTestDrive);

        saveAppuntamentoTestDrive.addActionListener(e -> {
            try {
                Appuntamento appuntamento = this.getAppuntamento(true);
                controller.addAppuntamento(appuntamento);
                JOptionPane.showMessageDialog(this, "Appuntamento creato:\n" + appuntamento);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore nella creazione dell'appuntamento: " + ex.getMessage());
            }
        });

        saveAppuntamentoConsulenza = new CustomButton("Inserisci Consulenza");
        saveAppuntamentoConsulenza.addActionListener(e -> {
            try {
                Appuntamento appuntamento = this.getAppuntamento(false);
                controller.addAppuntamento(appuntamento);
                JOptionPane.showMessageDialog(this, "Appuntamento creato:\n" + appuntamento);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore nella creazione dell'appuntamento: " + ex.getMessage());
            }
        });
        this.add(saveAppuntamentoConsulenza);

    }

    public Appuntamento getAppuntamento(boolean isTestDrive) {
        String tipologia;
        if (isTestDrive) {
            tipologia = "Test-Drive";
        } else {
            tipologia = "Consulenza";
        }
        updateDipendente();
        LocalDate data = ((java.util.Date) spData.getValue()).toInstant().atZone(java.time.ZoneId.systemDefault())
                .toLocalDate();
        LocalTime ora = ((java.util.Date) spOra.getValue()).toInstant().atZone(java.time.ZoneId.systemDefault())
                .toLocalTime();
        LocalTime durata = ((java.util.Date) spDurata.getValue()).toInstant().atZone(java.time.ZoneId.systemDefault())
                .toLocalTime();
        String numeroTelaio = ((Auto) comboNumeroTelaio.getSelectedItem()).getNumero_telaio();

        String email = dipendente.eMail();
        updateAuto();

        return new Appuntamento(
                data,
                ora,
                tipologia,
                durata,
                numeroTelaio,
                this.controller.id_DipendenteByEmail(email),
                this.controller.id_ClienteByEmail(this.controller.getClienteUser().eMail()));
    }

    private List<Modello> getModelli() {
        return controller.allModelli();
    }

    private List<Auto> getAuto(Modello modello) {
        return controller.allAutoFromModelli(modello);
    }

    private void updateAuto() {
        comboNumeroTelaio.removeAllItems();
        Auto[] auto = getAuto((Modello) combomodello.getSelectedItem()).stream().toArray(Auto[]::new);
        for (Auto auto1 : auto) {
            comboNumeroTelaio.addItem(auto1);
        }

        if (auto.length > 0) {
            comboNumeroTelaio.setSelectedItem(auto[0]);
        }

        comboNumeroTelaio.revalidate();
        comboNumeroTelaio.repaint();
    }

    private void updateDipendente() {
        this.dipendente = this.controller.dipendeteFromModello((Modello) combomodello.getSelectedItem());
        this.tfNomeDipendente.setText(dipendente.toString());
    }

}