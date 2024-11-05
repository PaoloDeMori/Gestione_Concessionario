package it.unibo.gestione_concessionario.view;

import javax.swing.*;

import it.unibo.gestione_concessionario.commons.dto.Dipendente;
import it.unibo.gestione_concessionario.commons.dto.Garanzia;
import it.unibo.gestione_concessionario.commons.dto.Marchio;
import it.unibo.gestione_concessionario.commons.dto.Optionals;
import it.unibo.gestione_concessionario.controller.Controller;
import it.unibo.gestione_concessionario.view.panelscliente.*;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.Optional;
import java.util.List;

public class ClienteView extends JFrame implements View {

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Controller controller;
    private MarchiPanel marchiPanel;
    private ModelliPanel modelliPanel;
    private JPanel dipendentePanel;
    private AutoFiltrate autoFiltratePanel;
    private AppuntamentoSetter appuntamentoSetterPanel;
    private AllAutoPanel allAuto;
    private GaranziaPanel garanziaPanel;
    private OptionalPanel optionalPanel;
    private AutoScontate autoScontate;
    Optional<Integer> idMarchioSelezionato = Optional.empty();

    public ClienteView(Controller controller) {
        this.controller = controller;
        this.marchiPanel = new MarchiPanel();
        this.allAuto = new AllAutoPanel();
        this.modelliPanel = new ModelliPanel();
        this.dipendentePanel = new JPanel();
        this.autoFiltratePanel = new AutoFiltrate(controller);
        this.autoScontate = new AutoScontate(controller);
        this.appuntamentoSetterPanel = new AppuntamentoSetter(controller);
        this.optionalPanel = new OptionalPanel();
        garanziaPanel = new GaranziaPanel();
        initialize();
    }

    private void initialize() {
        setTitle("Gestione Concessionario");
        setMinimumSize(new Dimension(1150, 600));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    controller.stop();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(cardPanel, ex.getMessage(), "Impossibile chiudere la connessione",
                            ABORT);
                }
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                dispose();
                System.exit(0);
            }
        });

        setLayout(new BorderLayout());

        JMenuBar menuBar = createMenuBar();
        setJMenuBar(menuBar);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        addPanelsToCardLayout();

        add(cardPanel, BorderLayout.CENTER);

        cardLayout.show(cardPanel, "Marchi");
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.BLACK);
        menuBar.setLayout(new FlowLayout());
        addNavigationButtons(menuBar);
        return menuBar;
    }

    private void addNavigationButtons(JMenuBar menuBar) {
        CustomButton marchiButton = new CustomButton("Visualizza Marchi");
        CustomButton modelliButton = new CustomButton("Visualizza Modelli");
        CustomButton autoFiltrateButton = new CustomButton("Auto");
        CustomButton createAppuntamentoButton = new CustomButton("Crea appuntamento");
        CustomButton allAutoButton = new CustomButton("Tutte le auto");
        CustomButton autoScontateButton = new CustomButton("Auto Scontate");
        ExitButton esciButton = new ExitButton("Esci");

        menuBar.add(marchiButton);
        menuBar.add(modelliButton);
        menuBar.add(autoFiltrateButton);
        menuBar.add(createAppuntamentoButton);
        menuBar.add(allAutoButton);
        menuBar.add(autoScontateButton);
        menuBar.add(esciButton);

        esciButton.addActionListener(e -> {
            try {
                controller.stop();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(cardPanel, ex.getMessage(), "Impossibile chiudere la connessione", ABORT);
            }
            System.exit(0);
        });

        addPanelSwitchListeners(marchiButton, modelliButton, autoFiltrateButton, createAppuntamentoButton,
                allAutoButton, autoScontateButton);

    }

    private void addPanelsToCardLayout() {
        JPanel homePanel = new JPanel();
        homePanel.add(new JLabel("Home Content"));

        JPanel profilePanel = new JPanel();
        profilePanel.add(new JLabel("Profile Content"));

        JPanel settingsPanel = new JPanel();
        settingsPanel.add(new JLabel("Settings Content"));

        cardPanel.add(modelliPanel, "Modelli");
        cardPanel.add(marchiPanel, "Marchi");
        cardPanel.add(dipendentePanel, "Dipendente");
        cardPanel.add(autoFiltratePanel, "AutoFilter");
        cardPanel.add(appuntamentoSetterPanel, "Appuntamento");
        cardPanel.add(allAuto, "Auto");
        cardPanel.add(garanziaPanel, "Garanzia");
        cardPanel.add(optionalPanel, "Optionals");
        cardPanel.add(autoScontate, "Sconti");

        setUpMarchiPanel();

        setUpAllAutoPanel();
    }

    private void setUpAllAutoPanel() {

        allAuto.setAuto(controller.allAuto());

        allAuto.setGaranziaButtonActionListener(e -> {
            JTable table = allAuto.getTable();
            int selectedRow = table.getSelectedRow();

            if (selectedRow >= 0) {
                String numeroTelaio = (String) table.getValueAt(selectedRow, 0);
                garanziaPanel.removeAll();
                Optional<Garanzia> garanzia = controller.visualizzaGaranzia(numeroTelaio);
                if (garanzia.isPresent()) {
                    garanziaPanel.setGaranziaPanel(garanzia.get());
                    cardLayout.show(cardPanel, "Garanzia");
                } else {
                    JOptionPane.showMessageDialog(table, "Non c'è garanzia", "NO GARANZIA",
                            JOptionPane.WARNING_MESSAGE);
                }

            }
        });

        allAuto.setOptionalButtonActionListener(e -> {
            JTable table = allAuto.getTable();
            int selectedRow = table.getSelectedRow();

            if (selectedRow >= 0) {
                String numeroTelaio = (String) table.getValueAt(selectedRow, 0);
                optionalPanel.removeAll();
                List<Optionals> optional = controller.visualizzaOptionals(numeroTelaio);
                if (optional.size() > 0) {
                    optionalPanel.setOptional(optional);
                    cardLayout.show(cardPanel, "Optionals");
                } else {
                    JOptionPane.showMessageDialog(table, "Non ci sono optional", "NO OPTIONAL",
                            JOptionPane.WARNING_MESSAGE);
                }

            }
        });
    }

    private void setUpMarchiPanel() {
        marchiPanel.setMarchi(controller.allMarchi());

        marchiPanel.setButtonActionListener(e -> {
            JTable table = marchiPanel.getTable();
            int selectedRow = table.getSelectedRow();

            if (selectedRow >= 0) {
                String nome = (String) table.getValueAt(selectedRow, 0);

                int pos = table.getSelectedRow();
                idMarchioSelezionato = Optional.of(marchiPanel.getgMarchi().get(pos).idMarchio());
                dipendentePanel.removeAll();
                try {
                    if (idMarchioSelezionato.isPresent()) {
                        Dipendente dip = controller
                                .dipendenteFromMarchio(new Marchio(idMarchioSelezionato.get().intValue(), nome));
                        dipendentePanel.add(new SingoloDipendentePanel(dip));
                        cardLayout.show(cardPanel, "Dipendente");
                    } else {
                        throw new IllegalArgumentException("Impossibile risalire all'id marchio");
                    }
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(marchiPanel, ex.getMessage(), "Impossibile visualizzare dipendente",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        marchiPanel.revalidate();
        marchiPanel.repaint();

    }

    private void addPanelSwitchListeners(CustomButton marchiButton, CustomButton modelliButton,
            CustomButton autoFiltrateButton, CustomButton createAppuntamentoButton, CustomButton allAutoButton,
            CustomButton autoScontateButton) {
        marchiButton.addActionListener(e -> {
            marchiPanel.setMarchi(controller.allMarchi());
            cardLayout.show(cardPanel, "Marchi");
        });

        allAutoButton.addActionListener(e -> {
            allAuto.setAuto(controller.allAuto());
            cardLayout.show(cardPanel, "Auto");
        });

        modelliButton.addActionListener(e -> {
            modelliPanel.setModelli(controller.allModelli());
            modelliPanel.revalidate();
            modelliPanel.repaint();
            cardLayout.show(cardPanel, "Modelli");
        });

        autoFiltrateButton.addActionListener(e -> cardLayout.show(cardPanel, "AutoFilter"));

        autoScontateButton.addActionListener(e -> cardLayout.show(cardPanel, "Sconti"));

        createAppuntamentoButton.addActionListener(e -> cardLayout.show(cardPanel, "Appuntamento"));
    }

    @Override
    public void start() {
        setVisible(true);
    }
}
