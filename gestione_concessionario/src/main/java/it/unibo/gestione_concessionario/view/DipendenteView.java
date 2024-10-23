package it.unibo.gestione_concessionario.view;

import javax.swing.*;

import it.unibo.gestione_concessionario.commons.dto.Garanzia;
import it.unibo.gestione_concessionario.commons.dto.Optionals;
import it.unibo.gestione_concessionario.controller.Controller;
import it.unibo.gestione_concessionario.view.panelsCliente.GaranziaPanel;
import it.unibo.gestione_concessionario.view.panelsCliente.OptionalPanel;
import it.unibo.gestione_concessionario.view.panelsDipendente.AddAutoDipendente;
import it.unibo.gestione_concessionario.view.panelsDipendente.AddOffertaPanel;
import it.unibo.gestione_concessionario.view.panelsDipendente.AddScontoPanel;
import it.unibo.gestione_concessionario.view.panelsDipendente.AddVenditaPanel;
import it.unibo.gestione_concessionario.view.panelsDipendente.AllAutoDipendente;
import it.unibo.gestione_concessionario.view.panelsDipendente.AppuntamentiDipendentePanel;
import it.unibo.gestione_concessionario.view.panelsDipendente.AppuntamentiSetterDipendente;
import it.unibo.gestione_concessionario.view.panelsDipendente.AutoScontateDipendente;
import it.unibo.gestione_concessionario.view.panelsDipendente.OfferteDisponibiliPanel;
import it.unibo.gestione_concessionario.view.panelsDipendente.VenditeDipendente;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;

public class DipendenteView extends JFrame implements View {

    private CustomCardLayout cardLayout;
    private Controller controller;
    private JPanel cardPanel;
    private AllAutoDipendente autosPanel;
    private AutoScontateDipendente autoScontateDipendente;
    private AddScontoPanel addScontoPanel;
    private AddOffertaPanel addOffertaPanel;
    private AppuntamentiDipendentePanel appuntamentiDipendentePanel;
    private AppuntamentiSetterDipendente AppuntamentiSetterDipendente;
    private AddVenditaPanel addVenditaPanel;
    private VenditeDipendente venditeDipendente;
    private AddAutoDipendente addAutoDipendente;
    private GaranziaPanel garanziaPanel;
    private OptionalPanel optionalPanel;
    private OfferteDisponibiliPanel offerteDisponibiliPanel;

    public DipendenteView(Controller controller) {
        this.controller = controller;
        this.autosPanel = new AllAutoDipendente();
        this.addOffertaPanel = new AddOffertaPanel(controller);
        this.addAutoDipendente = new AddAutoDipendente(controller);
        this.appuntamentiDipendentePanel = new AppuntamentiDipendentePanel(controller);
        this.AppuntamentiSetterDipendente=new AppuntamentiSetterDipendente(controller);
        this.venditeDipendente = new VenditeDipendente(controller);
        this.optionalPanel=new OptionalPanel();
        this.offerteDisponibiliPanel=new OfferteDisponibiliPanel(controller);
        garanziaPanel=new GaranziaPanel();
        initialize();
        this.start();
    }

    private class CustomCardLayout extends CardLayout {
        @Override
        public void show(Container parent, String name) {
            super.show(parent, name);
            addVenditaPanel.refresh();
        }
    }
        

    private void initialize() {
        // Impostazioni iniziali della finestra
        setTitle("Menu and CardLayout Example");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Creazione della barra menu
        JMenuBar menuBar = createMenuBar();
        setJMenuBar(menuBar);

        // Creazione del CardLayout per la gestione dei pannelli
        cardLayout = new CustomCardLayout();
        cardPanel = new JPanel(cardLayout);

        // Aggiungi pannelli specifici al CardLayout
        // addPanelsToCardLayout();

        // Aggiungi il cardPanel al centro della finestra
        add(cardPanel, BorderLayout.CENTER);
        addPanelsToCardLayout();

        // Visualizzazione iniziale su "Marchi"
        cardLayout.show(cardPanel, "autoGestite");
    }

    @Override
    public void start() {
        this.setVisible(true);
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stop'");
    }

    @Override
    public void error(String errore, String tipoDiErrore) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'error'");
    }

    @Override
    public void refreshGui() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'refreshGui'");
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.BLACK);

        // Aggiungi pulsanti per la navigazione dei pannelli
        addNavigationButtons(menuBar);
        return menuBar;
    }

    private void addNavigationButtons(JMenuBar menuBar) {
        CustomButton autosButton = new CustomButton("Auto Gestite");
        CustomButton addAutoButton = new CustomButton("Inserisci Auto");
        CustomButton meetingsButton = new CustomButton("I Tuoi Appuntamenti");
        CustomButton createAppuntamentoButton = new CustomButton("Crea appuntamento");
        CustomButton sellAutoButton = new CustomButton("Vendi Un Auto");
        CustomButton statsButton = new CustomButton("Statistiche");
        CustomButton allAutoScontate = new CustomButton("Sconti Disponibili");
        CustomButton autoConOffertaButton = new CustomButton("Offerta Marchio");
        CustomButton autoScontataButton = new CustomButton("Sconta Un Auto");
        CustomButton leTueVenditeButton = new CustomButton("Le Tue Vendite");
        CustomButton AggiungiDipendente = new CustomButton("Auto Scontate");
        CustomButton allOfferte = new CustomButton("Offerte Attive");


        CustomMenu cmSconti = new CustomMenu("Sconti");
        CustomMenu cmAppuntamenti = new CustomMenu("Appuntamenti");

        cmSconti.add(allAutoScontate);
        cmSconti.add(autoConOffertaButton);
        cmSconti.add(autoScontataButton);
        cmSconti.add(allOfferte);


        cmAppuntamenti.add(createAppuntamentoButton);
        cmAppuntamenti.add(meetingsButton);

        menuBar.add(autosButton);
        menuBar.add(addAutoButton);
        menuBar.add(cmAppuntamenti);
        menuBar.add(sellAutoButton);
        menuBar.add(cmSconti);
        menuBar.add(leTueVenditeButton);
        menuBar.add(AggiungiDipendente);

        // Aggiungi gli ActionListener per gestire i cambi di pannello
        addPanelSwitchListeners(autosButton, addAutoButton, meetingsButton, createAppuntamentoButton, sellAutoButton,
                autoConOffertaButton,leTueVenditeButton, autoScontataButton, AggiungiDipendente, statsButton, allAutoScontate, allOfferte);
    }

    private void addPanelSwitchListeners(CustomButton autosButton, CustomButton addAutoButton,
            CustomButton meetingsButton, CustomButton createAppuntamentoButton, CustomButton sellAutoButton,
            CustomButton autoConOffertaButton,CustomButton leTueVenditeButton, CustomButton autoScontataButton, CustomButton AggiungiDipendente,
            CustomButton statsButton, CustomButton allautoConOffertaButton, CustomButton allOfferte) {
        autosButton.addActionListener(e -> {
            autosPanel.setAuto(this.controller.allAutoDipendente());
            cardLayout.show(cardPanel, "autoGestite");
        });
        allautoConOffertaButton.addActionListener(e -> {
            autoScontateDipendente.filtraAuto();
            cardLayout.show(cardPanel, "allAutoScontate");
        });

        autoScontataButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "addSconto");
        });
        autoConOffertaButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "addOfferta");
        });
        meetingsButton.addActionListener(e -> {
            appuntamentiDipendentePanel.setAppuntamento(this.controller.visualizzaAppuntamenti());
            cardLayout.show(cardPanel, "allAppuntamenti");
        });
        createAppuntamentoButton.addActionListener(e -> {
            appuntamentiDipendentePanel.setAppuntamento(this.controller.visualizzaAppuntamenti());
            cardLayout.show(cardPanel, "createAppuntamenti");
        });
        sellAutoButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "CreaVendita");
        });
        leTueVenditeButton.addActionListener(e -> {
            venditeDipendente.filtraVendite();
            cardLayout.show(cardPanel, "LeTueVendite");
        });

        addAutoButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "InserisciAuto");
        });
        allOfferte.addActionListener(e -> {
            offerteDisponibiliPanel.filtraOfferte();
            cardLayout.show(cardPanel, "TutteOfferte");
        });
    }

    private void addPanelsToCardLayout() {
        autoScontateDipendente = new AutoScontateDipendente(controller);
        addScontoPanel = new AddScontoPanel(controller);
        addVenditaPanel = new AddVenditaPanel(controller);

        // Aggiungi i pannelli al CardLayout
        cardPanel.add(autosPanel, "autoGestite");
        cardPanel.add(autoScontateDipendente, "allAutoScontate");
        cardPanel.add(addScontoPanel, "addSconto");
        cardPanel.add(addOffertaPanel, "addOfferta");
        cardPanel.add(appuntamentiDipendentePanel,"allAppuntamenti");
        cardPanel.add(AppuntamentiSetterDipendente,"createAppuntamenti");
        cardPanel.add(addVenditaPanel,"CreaVendita");
        cardPanel.add(venditeDipendente,"LeTueVendite");
        cardPanel.add(garanziaPanel,"Garanzia");
        cardPanel.add(optionalPanel,"Optionals");
        cardPanel.add(addAutoDipendente,"InserisciAuto");
        cardPanel.add(offerteDisponibiliPanel,"TutteOfferte");


        /*
         * cardPanel.add(marchiPanel, "Marchi");
         * cardPanel.add(dipendentePanel, "Dipendente");
         * cardPanel.add(autoFiltratePanel, "AutoFilter");
         * cardPanel.add(appuntamentoSetterPanel, "Appuntamento");
         * cardPanel.add(allAuto, "Auto");
         * 
         * cardPanel.add(autoScontate,"Sconti");
         */

        setDipendenteAuto();
    }

    private void setDipendenteAuto() {

        this.autosPanel.setAuto(this.controller.allAutoDipendente());

        autosPanel.setGaranziaButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable table = autosPanel.getTable();
                int selectedRow = table.getSelectedRow();

                if (selectedRow >= 0) {
                    String numeroTelaio = (String) table.getValueAt(selectedRow, 1);
                    garanziaPanel.removeAll();
                    Optional<Garanzia> garanzia = controller.visualizzaGaranzia(numeroTelaio);
                    if(garanzia.isPresent()){
                        garanziaPanel.setGaranziaPanel(garanzia.get());
                        cardLayout.show(cardPanel, "Garanzia");
                    }
                    else{
                        JOptionPane.showMessageDialog(table, "Non c'è garanzia", "NO GARANZIA", JOptionPane.WARNING_MESSAGE);
                    }

                }
            }
        });


        autosPanel.setOptionalButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable table = autosPanel.getTable();
                int selectedRow = table.getSelectedRow();

                if (selectedRow >= 0) {
                    String numeroTelaio = (String) table.getValueAt(selectedRow, 1);
                    optionalPanel.removeAll();
                    List<Optionals> optional = controller.visualizzaOptionals(numeroTelaio);
                    if(optional.size()>0){
                        optionalPanel.setOptional(optional);
                        cardLayout.show(cardPanel, "Optionals");
                    }
                    else{
                        JOptionPane.showMessageDialog(table, "Non ci sono optional", "NO OPTIONAL", JOptionPane.WARNING_MESSAGE);
                    }

                }
            }
        });
    }


}
