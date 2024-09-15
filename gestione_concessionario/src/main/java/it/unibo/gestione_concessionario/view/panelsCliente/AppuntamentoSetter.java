package it.unibo.gestione_concessionario.view.panelsCliente;



import javax.swing.*;

import it.unibo.gestione_concessionario.commons.dto.Appuntamento;
import it.unibo.gestione_concessionario.commons.dto.Auto;
import it.unibo.gestione_concessionario.commons.dto.Dipendente;
import it.unibo.gestione_concessionario.commons.dto.Modello;
import it.unibo.gestione_concessionario.controller.Controller;
import it.unibo.gestione_concessionario.view.CustomButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AppuntamentoSetter extends JPanel {

    private JSpinner spData;
    private JSpinner spOra;
    private JComboBox<Modello> tfmodello;
    private JSpinner spDurata;
    private JComboBox<Auto> tfNumeroTelaio;
    private JLabel tfNomeDipendente;
    private JLabel tfNomeCliente;
    private Controller controller;
    private Dipendente dipendente;
    CustomButton saveAppuntamentoTestDrive;
    CustomButton saveAppuntamentoConsulenza;



    public AppuntamentoSetter(Controller controller) {
        setLayout(new GridLayout(9, 2, 5, 5)); // 8 righe, 2 colonne con spazi di 5px tra le celle
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
        tfmodello = new JComboBox<Modello>(getModelli().stream().toArray(Modello[]::new));
        tfmodello.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                updateAuto();
                updateDipendente();
            }
            
        });
        add(tfmodello);

        add(new JLabel("Durata:"));
        spDurata = new JSpinner(new SpinnerDateModel());
        timeEditor = new JSpinner.DateEditor(spDurata, "HH:mm");
        spDurata.setEditor(timeEditor);
        add(spDurata);

        add(new JLabel("Numero Telaio:"));
        tfNumeroTelaio = new JComboBox<Auto>(getAuto((Modello)tfmodello.getSelectedItem()).stream().toArray(Auto[]::new));
        add(tfNumeroTelaio);

        add(new JLabel("Nome Dipendente:"));
        tfNomeDipendente = new JLabel();
        updateDipendente();
        add(tfNomeDipendente);

        add(new JLabel("Nome Cliente:"));
        tfNomeCliente = new JLabel(controller.getClienteUser().nome()+" "+controller.getClienteUser().cognome());
        add(tfNomeCliente);

        saveAppuntamentoTestDrive=new CustomButton("Inserisci Test-Drive");
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

        saveAppuntamentoConsulenza=new CustomButton("Inserisci Consulenza");
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

    // Metodo per ottenere i dati dall'interfaccia
    public Appuntamento getAppuntamento(boolean isTestDrive) {
        String tipologia;
        if(isTestDrive){
            tipologia="Test-Drive";
        }
        else{
            tipologia="Consulenza";
        }
        updateAuto();
        updateDipendente();
        LocalDate data = ((java.util.Date) spData.getValue()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        LocalTime ora = ((java.util.Date) spOra.getValue()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalTime();
        LocalTime durata =LocalTime.of(0, 0, 0);
        String email = dipendente.eMail();
        
        return new Appuntamento(
            data,
            ora,
            tipologia,
            durata,
            ((Auto)tfNumeroTelaio.getSelectedItem()).numero_telaio(),
            this.controller.id_DipendenteByEmail(email),
            this.controller.id_ClienteByEmail(this.controller.getClienteUser().eMail())
        );
    }

    private List<Modello> getModelli() {
        return controller.allModelli();
    }
    private List<Auto> getAuto(Modello modello){
        return controller.allAutoFromModelli(modello);
    }
    private void updateAuto(){
        tfNumeroTelaio.removeAllItems();
        Auto[] auto = getAuto((Modello)tfmodello.getSelectedItem()).stream().toArray(Auto[]::new);
        for(Auto auto1 : auto){
        tfNumeroTelaio.addItem(auto1);
        }
        tfNumeroTelaio.revalidate();
        tfNumeroTelaio.repaint();
    }
    private void updateDipendente(){
        this.dipendente=this.controller.dipendeteFromModello((Modello)tfmodello.getSelectedItem());
        this.tfNomeDipendente.setText(dipendente.toString());
    }
  
}