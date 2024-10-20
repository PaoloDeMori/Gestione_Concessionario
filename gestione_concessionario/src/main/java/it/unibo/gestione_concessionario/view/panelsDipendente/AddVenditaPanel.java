package it.unibo.gestione_concessionario.view.panelsDipendente;

import javax.swing.*;
import it.unibo.gestione_concessionario.commons.dto.Auto;
import it.unibo.gestione_concessionario.commons.dto.Cliente;
import it.unibo.gestione_concessionario.commons.dto.Contratto;
import it.unibo.gestione_concessionario.commons.dto.Dipendente;
import it.unibo.gestione_concessionario.commons.dto.Modello;
import it.unibo.gestione_concessionario.commons.dto.Vendita;
import it.unibo.gestione_concessionario.controller.Controller;
import it.unibo.gestione_concessionario.view.CustomButton;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

public class AddVenditaPanel extends JPanel {

    private JSpinner spData;
    private JSpinner spOra;
    private Dipendente dipendente;
    private JButton creaContrattoButton;
    private Contratto contratto;


    private JComboBox<Modello> tfmodello;
    private JComboBox<Auto> tfNumeroTelaio;
    private JLabel tfNomeDipendente;
    private JComboBox<Cliente> tfNomeCliente;
    private Controller controller;
    private CustomButton saveVendita;
    private CustomButton refreshButton;
    private ContrattoDialog contrattoDialog;
    private String numeroTelaio;
    private JLabel creaContrattoLabel = new JLabel("Contratto da Creare");
    private JPanel maiPanel;

    public AddVenditaPanel(Controller controller) {
        setLayout(new GridLayout(1, 1));
        this.controller = controller;

        this.setMainPanel();


        this.add(maiPanel);
    }

    private void setMainPanel() {
        maiPanel = new JPanel();
        maiPanel.setLayout(new GridLayout(9, 2, 5, 5));

        maiPanel.add(creaContrattoLabel);
        creaContrattoButton = new CustomButton("Crea Contratto");
        maiPanel.add(creaContrattoButton);
        contrattoDialog = new ContrattoDialog(controller, this);
        creaContrattoButton.addActionListener(e -> contrattoDialog.setVisible(true));

        maiPanel.add(new JLabel("Data"));
        spData = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spData, "yyyy-MM-dd");
        spData.setEditor(dateEditor);
        maiPanel.add(spData);

        maiPanel.add(new JLabel("Ora:"));
        spOra = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(spOra, "HH:mm");
        spOra.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY));
        spOra.setEditor(timeEditor);
        maiPanel.add(spOra);

        maiPanel.add(new JLabel("Modello:"));
        tfmodello = new JComboBox<>(getModelli().stream().toArray(Modello[]::new));
        tfmodello.addActionListener(e -> updateAuto());
        maiPanel.add(tfmodello);

        maiPanel.add(new JLabel("Numero Telaio:"));
        tfNumeroTelaio = new JComboBox<>(getAuto((Modello) tfmodello.getSelectedItem()).stream().toArray(Auto[]::new));
        tfNumeroTelaio.addActionListener(e -> {
            Auto selectedAuto = (Auto) tfNumeroTelaio.getSelectedItem();
            if (selectedAuto != null) {
                numeroTelaio = selectedAuto.getNumero_telaio();
                System.out.println("Numero telaio selezionato: " + selectedAuto.getNumero_telaio());
            } else {
                System.out.println("Nessun auto selezionata");
            }
        });
        maiPanel.add(tfNumeroTelaio);

        maiPanel.add(new JLabel("Nome Dipendente:"));
        tfNomeDipendente = new JLabel();
        updateDipendente();
        maiPanel.add(tfNomeDipendente);

        maiPanel.add(new JLabel("Nome Cliente:"));
        tfNomeCliente = new JComboBox<>(controller.allClienti().stream().toArray(Cliente[]::new));
        maiPanel.add(tfNomeCliente);

        saveVendita = new CustomButton("Aggiungi vendita");
        maiPanel.add(saveVendita);
        saveVendita.addActionListener(e -> {
            try {
                if (contratto == null) {
                    throw new IllegalStateException("Devi prima inserire un contratto");
                }
                Vendita vendita = getVendita();
                controller.addVendita(vendita);
                contratto=null;
                JOptionPane.showMessageDialog(this, "Vendita creata:\n" + vendita);
                this.removeAll();
                setMainPanel();
                this.add(maiPanel);
                this.revalidate();
                this.repaint();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore nella creazione della Vendita: " + ex.getMessage());
            }
        });

        refreshButton = new CustomButton("Refresh");
        maiPanel.add(refreshButton);
        refreshButton.addActionListener(e -> {
                refresh();
        });

    }

    protected void addContratto(Contratto contratto) {
        if (contratto != null) {
            creaContrattoButton.setEnabled(false);
            creaContrattoButton.setFocusPainted(true);
            creaContrattoButton.setFocusable(false);
            creaContrattoLabel.setText("Contratto inserito con successo!");
        }
        this.contratto = contratto;
    }

    public Vendita getVendita() {
        LocalDate data = ((java.util.Date) spData.getValue()).toInstant().atZone(java.time.ZoneId.systemDefault())
                .toLocalDate();
        LocalTime ora = ((java.util.Date) spOra.getValue()).toInstant().atZone(java.time.ZoneId.systemDefault())
                .toLocalTime();
        String email = dipendente.eMail();
        Cliente cliente = (Cliente) tfNomeCliente.getSelectedItem();
        if (cliente == null) {
            throw new IllegalStateException("Cliente non selezionato");
        }
        if(numeroTelaio==null){
        numeroTelaio = ((Auto) tfNumeroTelaio.getSelectedItem()).getNumero_telaio();
        }
        return new Vendita(
                numeroTelaio,
                contratto,
                data,
                ora,
                controller.id_DipendenteByEmail(email),
                controller.id_ClienteByEmail(cliente.eMail()));
    }

    private List<Modello> getModelli() {
        return controller.allModelli();
    }

    private List<Auto> getAuto(Modello modello) {
        return controller.allAutoNonVenduteFromModelli(modello);
    }

    private void updateAuto() {
        Modello selectedModello = (Modello) tfmodello.getSelectedItem();
        if (selectedModello != null) {
            tfNumeroTelaio.removeAllItems();
            List<Auto> autoList = getAuto(selectedModello);
            for (Auto auto : autoList) {
                tfNumeroTelaio.addItem(auto);
            }
            tfNumeroTelaio.revalidate();
            tfNumeroTelaio.repaint();
        }
    }

    private void updateDipendente() {
        this.dipendente = this.controller.getDipendenteUser();
        this.tfNomeDipendente.setText(dipendente.toString());
    }

    public void refresh(){
        if(contratto!=null){
            controller.eliminaContratto(contratto);
            JOptionPane.showMessageDialog(this, "Contratto eliminato ");
        }
        contratto=null;
        this.removeAll();
        setMainPanel();
        this.add(maiPanel);
        this.revalidate();
        this.repaint();
    }

    public Contratto getContratto() {
        return contratto;
    }
}
