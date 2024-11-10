package it.unibo.gestione_concessionario.view;

import javax.swing.*;

import it.unibo.gestione_concessionario.commons.dto.Dipendente;
import it.unibo.gestione_concessionario.commons.dto.Garanzia;
import it.unibo.gestione_concessionario.commons.dto.Optionals;
import it.unibo.gestione_concessionario.controller.Controller;
import it.unibo.gestione_concessionario.view.panelscliente.GaranziaPanel;
import it.unibo.gestione_concessionario.view.panelscliente.OptionalPanel;
import it.unibo.gestione_concessionario.view.panelsdipendente.AddAutoDipendente;
import it.unibo.gestione_concessionario.view.panelsdipendente.AddDipendentePanel;
import it.unibo.gestione_concessionario.view.panelsdipendente.AddOffertaPanel;
import it.unibo.gestione_concessionario.view.panelsdipendente.AddScontoPanel;
import it.unibo.gestione_concessionario.view.panelsdipendente.AddVenditaPanel;
import it.unibo.gestione_concessionario.view.panelsdipendente.AllAutoDipendente;
import it.unibo.gestione_concessionario.view.panelsdipendente.AppuntamentiDipendentePanel;
import it.unibo.gestione_concessionario.view.panelsdipendente.AppuntamentiSetterDipendente;
import it.unibo.gestione_concessionario.view.panelsdipendente.AutoScontateDipendente;
import it.unibo.gestione_concessionario.view.panelsdipendente.OfferteDisponibiliPanel;
import it.unibo.gestione_concessionario.view.panelsdipendente.RimuoviDipendentePanel;
import it.unibo.gestione_concessionario.view.panelsdipendente.VenditeDipendente;
import it.unibo.gestione_concessionario.view.panelsdipendente.VisualizzaStatisticheDipendente;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
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
    private AppuntamentiSetterDipendente appuntamentiSetterDipendente;
    private AddVenditaPanel addVenditaPanel;
    private VenditeDipendente venditeDipendente;
    private AddAutoDipendente addAutoDipendente;
    private GaranziaPanel garanziaPanel;
    private OptionalPanel optionalPanel;
    private OfferteDisponibiliPanel offerteDisponibiliPanel;
    private AddDipendentePanel addDipendentePanel;
    private RimuoviDipendentePanel rimuoviDipendentePanel;
    private VisualizzaStatisticheDipendente visualizzaStatisticheDipendente;
    private Dipendente user;

    public DipendenteView(Controller controller) {
        this.controller = controller;
        user = controller.getDipendenteUser();
        this.autosPanel = new AllAutoDipendente();
        this.addOffertaPanel = new AddOffertaPanel(controller);
        this.addAutoDipendente = new AddAutoDipendente(controller);
        this.appuntamentiDipendentePanel = new AppuntamentiDipendentePanel(controller);
        this.appuntamentiSetterDipendente = new AppuntamentiSetterDipendente(controller);
        this.venditeDipendente = new VenditeDipendente(controller);
        this.optionalPanel = new OptionalPanel();
        this.offerteDisponibiliPanel = new OfferteDisponibiliPanel(controller);
        this.addDipendentePanel = new AddDipendentePanel(controller);
        this.rimuoviDipendentePanel = new RimuoviDipendentePanel(controller);
        this.visualizzaStatisticheDipendente = new VisualizzaStatisticheDipendente(controller);
        garanziaPanel = new GaranziaPanel();
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
        setTitle("Gestione Concessionario");
        setMinimumSize(new Dimension(1150,600));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    controller.stop();
                    addVenditaPanel.refresh();
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

        cardLayout = new CustomCardLayout();
        cardPanel = new JPanel(cardLayout);

        add(cardPanel, BorderLayout.CENTER);
        addPanelsToCardLayout();

        cardLayout.show(cardPanel, "autoGestite");
    }

    @Override
    public void start() {
        this.setVisible(true);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.BLACK);
        menuBar.setLayout(new FlowLayout());
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
        CustomButton aggiungiDipendenteButton;
        CustomButton rimuoviDipendenteButton;
        ExitButton esciButton = new ExitButton("Esci");

        aggiungiDipendenteButton = new CustomButton("Aggiungi un dipendente");
        rimuoviDipendenteButton = new CustomButton("Rimuovi un dipendente");
        CustomButton allOfferte = new CustomButton("Offerte Attive");

        CustomMenu cmSconti = new CustomMenu("Sconti");
        CustomMenu cmAppuntamenti = new CustomMenu("Appuntamenti");
        CustomMenu cmAdmin = new CustomMenu("Funzioni Responsabile");
        CustomMenu cmVendite = new CustomMenu("Vendite");

        cmSconti.add(allAutoScontate);
        cmSconti.add(autoConOffertaButton);
        cmSconti.add(autoScontataButton);
        cmSconti.add(allOfferte);

        cmVendite.add(sellAutoButton);
        cmVendite.add(statsButton);
        cmVendite.add(leTueVenditeButton);

        cmAppuntamenti.add(createAppuntamentoButton);
        cmAppuntamenti.add(meetingsButton);

        menuBar.add(autosButton);
        menuBar.add(addAutoButton);
        menuBar.add(cmAppuntamenti);
        menuBar.add(cmSconti);
        menuBar.add(cmVendite);
        if (user.isResponsabile()) {
            cmAdmin.add(aggiungiDipendenteButton);
            cmAdmin.add(rimuoviDipendenteButton);
            menuBar.add(cmAdmin);
        }
        menuBar.add(esciButton);

        esciButton.addActionListener(e -> {
            try {
                addVenditaPanel.refresh();
                controller.stop();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(cardPanel, ex.getMessage(), "Impossibile chiudere la connessione", ABORT);
            }
            System.exit(0);
        });

        addPanelSwitchListeners(autosButton, addAutoButton, meetingsButton, createAppuntamentoButton, sellAutoButton,
                autoConOffertaButton, leTueVenditeButton, autoScontataButton, aggiungiDipendenteButton, statsButton,
                allAutoScontate, allOfferte, rimuoviDipendenteButton);
    }

    private void addPanelSwitchListeners(CustomButton autosButton, CustomButton addAutoButton,
            CustomButton meetingsButton, CustomButton createAppuntamentoButton, CustomButton sellAutoButton,
            CustomButton autoConOffertaButton, CustomButton leTueVenditeButton, CustomButton autoScontataButton,
            CustomButton aggiungiDipendenteButton,
            CustomButton statsButton, CustomButton allautoConOffertaButton, CustomButton allOfferte,
            CustomButton rimuoviDipendenteButton) {
        autosButton.addActionListener(e -> {
            autosPanel.setAuto(this.controller.allAutoDipendente());
            cardLayout.show(cardPanel, "autoGestite");
        });
        allautoConOffertaButton.addActionListener(e -> {
            autoScontateDipendente.filtraAuto();
            cardLayout.show(cardPanel, "allAutoScontate");
        });

        statsButton.addActionListener(e -> {
            visualizzaStatisticheDipendente.refreshMainPanel();
            visualizzaStatisticheDipendente.updateMese();
            cardLayout.show(cardPanel, "stats");
        });

        autoScontataButton.addActionListener(e -> cardLayout.show(cardPanel, "addSconto"));
        autoConOffertaButton.addActionListener(e -> cardLayout.show(cardPanel, "addOfferta"));
        meetingsButton.addActionListener(e -> {
            appuntamentiDipendentePanel.setAppuntamento(this.controller.visualizzaAppuntamenti());
            cardLayout.show(cardPanel, "allAppuntamenti");
        });
        createAppuntamentoButton.addActionListener(e -> {
            appuntamentiSetterDipendente.updateAuto();
            cardLayout.show(cardPanel, "createAppuntamenti");
        });
        sellAutoButton.addActionListener(e -> cardLayout.show(cardPanel, "CreaVendita"));
        leTueVenditeButton.addActionListener(e -> {
            venditeDipendente.filtraVendite();
            cardLayout.show(cardPanel, "LeTueVendite");
        });

        addAutoButton.addActionListener(e -> cardLayout.show(cardPanel, "InserisciAuto"));

        allOfferte.addActionListener(e -> {
            offerteDisponibiliPanel.filtraOfferte();
            cardLayout.show(cardPanel, "TutteOfferte");
        });
        if (user.isResponsabile()) {
            aggiungiDipendenteButton.addActionListener(e -> {
                addDipendentePanel.updateMarchio();
                cardLayout.show(cardPanel, "AggiungiDipendente");
            });
        }
        if (user.isResponsabile()) {
            rimuoviDipendenteButton.addActionListener(e -> {
                cardLayout.show(cardPanel, "RimuoviDipendente");
                rimuoviDipendentePanel.updateDipendente();
            });
        }
    }

    private void addPanelsToCardLayout() {
        autoScontateDipendente = new AutoScontateDipendente(controller);
        addScontoPanel = new AddScontoPanel(controller);
        addVenditaPanel = new AddVenditaPanel(controller);

        cardPanel.add(autosPanel, "autoGestite");
        cardPanel.add(autoScontateDipendente, "allAutoScontate");
        cardPanel.add(addScontoPanel, "addSconto");
        cardPanel.add(addOffertaPanel, "addOfferta");
        cardPanel.add(appuntamentiDipendentePanel, "allAppuntamenti");
        cardPanel.add(appuntamentiSetterDipendente, "createAppuntamenti");
        cardPanel.add(addVenditaPanel, "CreaVendita");
        cardPanel.add(venditeDipendente, "LeTueVendite");
        cardPanel.add(garanziaPanel, "Garanzia");
        cardPanel.add(optionalPanel, "Optionals");
        cardPanel.add(addAutoDipendente, "InserisciAuto");
        cardPanel.add(offerteDisponibiliPanel, "TutteOfferte");
        cardPanel.add(addDipendentePanel, "AggiungiDipendente");
        cardPanel.add(rimuoviDipendentePanel, "RimuoviDipendente");
        cardPanel.add(visualizzaStatisticheDipendente, "stats");

        setDipendenteAuto();
    }

    private void setDipendenteAuto() {

        this.autosPanel.setAuto(this.controller.allAutoDipendente());

        autosPanel.setGaranziaButtonActionListener(e -> {
            JTable table = autosPanel.getTable();
            int selectedRow = table.getSelectedRow();

            if (selectedRow >= 0) {
                String numeroTelaio = (String) table.getValueAt(selectedRow, 1);
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

        autosPanel.setOptionalButtonActionListener(e -> {
            JTable table = autosPanel.getTable();
            int selectedRow = table.getSelectedRow();

            if (selectedRow >= 0) {
                String numeroTelaio = (String) table.getValueAt(selectedRow, 1);
                optionalPanel.removeAll();
                List<Optionals> optional = controller.visualizzaOptionals(numeroTelaio);
                if (!optional.isEmpty()) {
                    optionalPanel.setOptional(optional);
                    cardLayout.show(cardPanel, "Optionals");
                } else {
                    JOptionPane.showMessageDialog(table, "Non ci sono optional", "NO OPTIONAL",
                            JOptionPane.WARNING_MESSAGE);
                }

            }
        });
    }

}
