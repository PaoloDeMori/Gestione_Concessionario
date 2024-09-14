package it.unibo.gestione_concessionario.view;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTable;

import it.unibo.gestione_concessionario.commons.dto.Auto;
import it.unibo.gestione_concessionario.commons.dto.Marchio;
import it.unibo.gestione_concessionario.commons.dto.Modello;
import it.unibo.gestione_concessionario.controller.Controller;
import it.unibo.gestione_concessionario.view.panels.MarchiPanel;
import it.unibo.gestione_concessionario.view.panels.ModelliPanel;
import it.unibo.gestione_concessionario.view.panels.OptionalsPanels;
import it.unibo.gestione_concessionario.view.panels.SingoloDipendentePanel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

public class ClienteView extends JFrame implements View {

    private CardLayout cardLayout;
    private JPanel cardPanel;
    Controller controller;
    private MarchiPanel marchiPanel;
    private ModelliPanel modelliPanel = new ModelliPanel();
    private JPanel dipendente = new JPanel();
    private OptionalsPanels optionalFromAutos = new OptionalsPanels();

    public ClienteView(Controller controller) {
        this.controller = controller;
        marchiPanel = new MarchiPanel();
        this.initialize();

    }

    private void initialize() {
        this.setTitle("Menu and CardLayout Example");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // Crea il menu bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.BLACK);

        // Crea il menu principale "File"
        JMenu menu = new JMenu("Menu");
        menu.setForeground(new Color(255, 140, 0));

        // Aggiungi le voci del menu
        CustomButton homeItem = new CustomButton("Home");
        CustomButton profileItem = new CustomButton("Profile");
        CustomButton settingsItem = new CustomButton("Settings");

        // Aggiungi le voci al menu
        menu.add(homeItem);
        menu.add(profileItem);
        menu.add(settingsItem);

        CustomButton marchiButton = new CustomButton("Visualizza Marchi");
        menuBar.add(marchiButton);

        CustomButton modelliButton = new CustomButton("Visualizza Modelli");
        menuBar.add(modelliButton);

        // Aggiungi il menu al menu bar
        menuBar.add(menu);

        // Aggiungi il menu bar alla finestra
        this.setJMenuBar(menuBar);

        // Crea il CardLayout per gestire i pannelli
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Pannelli specifici per Home, Profile e Settings
        JPanel homePanel = new JPanel();
        homePanel.add(new javax.swing.JLabel("Home Content"));

        JPanel profilePanel = new JPanel();
        profilePanel.add(new javax.swing.JLabel("Profile Content"));

        JPanel settingsPanel = new JPanel();
        settingsPanel.add(new javax.swing.JLabel("Settings Content"));

        // Imposta inizialmente i dati del MarchiPanel
        marchiPanel.setButtonActionListener(new ActionListener() {
            JTable tabella = marchiPanel.getTable();
            int id;
            String nome;
            @Override
            public void actionPerformed(ActionEvent e) {
                dipendente.removeAll();
               int rigaSelezionata = tabella.getSelectedRow();
               if(rigaSelezionata>=0){
               nome = (String)tabella.getValueAt(rigaSelezionata, 0);
               id = controller.idFromNameMarchio(nome);
               dipendente.add(new SingoloDipendentePanel(controller.dipendenteFromMarchio(new Marchio(id, nome))));
               cardLayout.show(cardPanel, "Dipendente");
               }
            }
            
        });
        marchiPanel.setMarchi(controller.allMarchi());
        marchiPanel.revalidate();
        marchiPanel.repaint();

        // Aggiungi i pannelli al CardLayout
        cardPanel.add(modelliPanel, "Modelli");
        cardPanel.add(marchiPanel, "Marchi");
        cardPanel.add(dipendente, "Dipendente");
        cardPanel.add(optionalFromAutos, "Optional");

        // Aggiungi il cardPanel al centro della finestra
        this.add(cardPanel, BorderLayout.CENTER);

        // Imposta la visualizzazione iniziale su "Home"
        cardLayout.show(cardPanel, "Marchi");
        
        // Aggiungi gli ActionListener per gestire i cambi di pannello
        marchiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update MarchiPanel data
                marchiPanel.setMarchi(controller.allMarchi());
                // Switch to the 'Marchi' panel
                cardLayout.show(cardPanel, "Marchi");
            }
        });

        // Aggiungi gli ActionListener per gestire i cambi di pannello
        modelliButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update MarchiPanel data
                modelliPanel.setModelli(controller.allModelli());
                modelliPanel.revalidate();
                modelliPanel.repaint();
                // Switch to the 'Marchi' panel
                cardLayout.show(cardPanel, "Modelli");
            }
        });

        homeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Home");
            }
        });

        profileItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Profile");
            }
        });

        settingsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Settings");
            }
        });
    }

    @Override
    public void start() {
        this.setVisible(true);
    }

    @Override
    public void stop() {
        throw new UnsupportedOperationException("Unimplemented method 'stop'");
    }

    @Override
    public void error(String errore, String tipoDiErrore) {
        throw new UnsupportedOperationException("Unimplemented method 'error'");
    }

    @Override
    public void refreshGui() {
        this.revalidate();
        this.repaint();
    }

    public static void main(String[] args) {
        Controller c = new Controller();
        c.initCliente();
        ClienteView cv = new ClienteView(c);
        cv.start();
    }
}