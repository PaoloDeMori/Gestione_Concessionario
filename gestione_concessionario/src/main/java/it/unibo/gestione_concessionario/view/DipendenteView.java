package it.unibo.gestione_concessionario.view;

import javax.swing.*;


import it.unibo.gestione_concessionario.controller.Controller;
import it.unibo.gestione_concessionario.view.panelsDipendente.AddScontoPanel;
import it.unibo.gestione_concessionario.view.panelsDipendente.AllAutoDipendente;
import it.unibo.gestione_concessionario.view.panelsDipendente.AutoScontateDipendente;

import java.awt.*;


public class DipendenteView extends JFrame implements View{

    private CardLayout cardLayout;
    private Controller controller;
    private JPanel cardPanel;
    private AllAutoDipendente autosPanel;
    private AutoScontateDipendente autoScontateDipendente;
    private AddScontoPanel addScontoPanel;


    public DipendenteView(Controller controller) {
        this.controller = controller;
        this.autosPanel=new AllAutoDipendente();
        initialize();
        this.start();
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
        cardLayout = new CardLayout();
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
        CustomButton autoScontateButton = new CustomButton("Sconto Marchio");
        CustomButton autoScontataSingolaButton = new CustomButton("Sconta Un Auto");
        CustomButton leTueVenditeButton = new CustomButton("Le Tue Vendite");
        CustomButton AggiungiDipendente = new CustomButton("Auto Scontate");

        CustomMenu cmSconti= new CustomMenu("Sconti");
        CustomMenu cmAppuntamenti= new CustomMenu("Appuntamenti");

        cmSconti.add(allAutoScontate);
        cmSconti.add(autoScontateButton);
        cmSconti.add(autoScontataSingolaButton);

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
        addPanelSwitchListeners(autosButton, addAutoButton, meetingsButton, createAppuntamentoButton,sellAutoButton,autoScontateButton,autoScontataSingolaButton,AggiungiDipendente,statsButton,allAutoScontate);
    }

    
    private void addPanelSwitchListeners(CustomButton autosButton,CustomButton addAutoButton,CustomButton meetingsButton,CustomButton createAppuntamentoButton,CustomButton sellAutoButton,CustomButton autoScontateButton,CustomButton autoScontataSingolaButton,CustomButton AggiungiDipendente, CustomButton statsButton, CustomButton allAutoScontateButton) {
        autosButton.addActionListener(e -> {
            autosPanel.setAuto(this.controller.allAutoDipendente());
            cardLayout.show(cardPanel, "autoGestite");
        });
        allAutoScontateButton.addActionListener(e -> {
            autoScontateDipendente.filtraAuto();
            cardLayout.show(cardPanel, "allAutoScontate");
        });

        autoScontataSingolaButton.addActionListener(e -> {
            autoScontateDipendente.filtraAuto();
            cardLayout.show(cardPanel, "addSconto");
        });
    }

    private void addPanelsToCardLayout() {
        autoScontateDipendente=new AutoScontateDipendente(controller);
        addScontoPanel=new AddScontoPanel(controller);

        // Aggiungi i pannelli al CardLayout
        cardPanel.add(autosPanel, "autoGestite");
        cardPanel.add(autoScontateDipendente,"allAutoScontate");
        cardPanel.add(addScontoPanel,"addSconto");
        /* 
        cardPanel.add(marchiPanel, "Marchi");
        cardPanel.add(dipendentePanel, "Dipendente");
        cardPanel.add(autoFiltratePanel, "AutoFilter");
        cardPanel.add(appuntamentoSetterPanel, "Appuntamento");
        cardPanel.add(allAuto, "Auto");
        cardPanel.add(garanziaPanel,"Garanzia");
        cardPanel.add(optionalPanel,"Optionals");
        cardPanel.add(autoScontate,"Sconti");*/

        setDipendenteAuto();
    }

    private void setDipendenteAuto(){
        this.autosPanel.setAuto(this.controller.allAutoDipendente());
    }


}
