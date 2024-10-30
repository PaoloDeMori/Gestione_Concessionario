package it.unibo.gestione_concessionario.view.panelsdipendente;



import javax.swing.*;

import it.unibo.gestione_concessionario.commons.dto.Appuntamento;
import it.unibo.gestione_concessionario.commons.dto.Auto;
import it.unibo.gestione_concessionario.commons.dto.Cliente;
import it.unibo.gestione_concessionario.commons.dto.Dipendente;
import it.unibo.gestione_concessionario.commons.dto.Modello;
import it.unibo.gestione_concessionario.controller.Controller;
import it.unibo.gestione_concessionario.view.CustomButton;
import it.unibo.gestione_concessionario.view.View;
import java.util.Calendar;
import java.util.Date;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AppuntamentiSetterDipendente extends JPanel {

    private JPanel maiPanel;
    private JSpinner spData;
    private JSpinner spOra;
    private JComboBox<Modello> tfmodello;
    private JSpinner spDurata;
    private JComboBox<Auto> tfNumeroTelaio;
    private JLabel tfNomeDipendente;
    private JComboBox<Cliente> tfNomeCliente;
    private Controller controller;
    private Dipendente dipendente;
    CustomButton saveAppuntamentoTestDrive;
    CustomButton saveAppuntamentoConsulenza;



    public AppuntamentiSetterDipendente(Controller controller) {
        setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel title = new JLabel("Crea Un Appuntamento");
        title.setFont(View.titleFont);
        title.setHorizontalAlignment(JLabel.CENTER);
        titlePanel.add(title);
        this.add(titlePanel,BorderLayout.NORTH);

        maiPanel=new JPanel(new GridLayout(9, 2, 5, 5));
        this.controller = controller;

        maiPanel.add(new JLabel("Data:"));
        spData = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spData, "yyyy-MM-dd");
        spData.setEditor(dateEditor);
        maiPanel.add(spData);

        maiPanel.add(new JLabel("Ora:"));
        spOra = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(spOra, "HH:mm");
        spOra.setEditor(timeEditor);
        maiPanel.add(spOra);

        maiPanel.add(new JLabel("Modello:"));
        tfmodello = new JComboBox<>(getModelli().stream().toArray(Modello[]::new));
        tfmodello.addActionListener(e-> {
                updateAuto();
                updateDipendente();
            }
            
        );
        maiPanel.add(tfmodello);

        maiPanel.add(new JLabel("Durata:"));
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

        maiPanel.add(spDurata);

        maiPanel.add(new JLabel("Numero Telaio:"));
        tfNumeroTelaio = new JComboBox<>(getAuto((Modello)tfmodello.getSelectedItem()).stream().toArray(Auto[]::new));
        maiPanel.add(tfNumeroTelaio);

        maiPanel.add(new JLabel("Nome Dipendente:"));
        tfNomeDipendente = new JLabel();
        updateDipendente();
        maiPanel.add(tfNomeDipendente);

        maiPanel.add(new JLabel("Nome Cliente:"));
        tfNomeCliente = new JComboBox<>(controller.allClienti().stream().toArray(Cliente[]::new));
        maiPanel.add(tfNomeCliente);

        saveAppuntamentoTestDrive=new CustomButton("Inserisci Test-Drive");
        maiPanel.add(saveAppuntamentoTestDrive);

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
        maiPanel.add(saveAppuntamentoConsulenza);
        this.add(maiPanel,BorderLayout.CENTER);

    }

    public Appuntamento getAppuntamento(boolean isTestDrive) {
        String tipologia;
        if(isTestDrive){
            tipologia="Test-Drive";
        }
        else{
            tipologia="Consulenza";
        }
        updateDipendente();
        LocalDate data = ((java.util.Date) spData.getValue()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        LocalTime ora = ((java.util.Date) spOra.getValue()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalTime();
        LocalTime durata =((java.util.Date) spDurata.getValue()).toInstant().atZone(java.time.ZoneId.systemDefault())
        .toLocalTime();
        String email = dipendente.eMail();
        
        return new Appuntamento(
            data,
            ora,
            tipologia,
            durata,
            ((Auto)tfNumeroTelaio.getSelectedItem()).getNumero_telaio(),
            this.controller.id_DipendenteByEmail(email),
            this.controller.id_ClienteByEmail( ((Cliente)tfNomeCliente.getSelectedItem()).eMail())
        );
    }

    private List<Modello> getModelli() {
        return controller.allModelli();
    }
    private List<Auto> getAuto(Modello modello){
        return  controller.allAutoNonVenduteFromModelli(modello);
    }
    
    public void updateAuto(){
        tfNumeroTelaio.removeAllItems();
        Auto[] auto = getAuto((Modello)tfmodello.getSelectedItem()).stream().toArray(Auto[]::new);
        for(Auto auto1 : auto){
        tfNumeroTelaio.addItem(auto1);
        }
        tfNumeroTelaio.revalidate();
        tfNumeroTelaio.repaint();
    }
    private void updateDipendente(){
        this.dipendente=this.controller.getDipendenteUser();
        this.tfNomeDipendente.setText(dipendente.toString());
    }
  
}