package it.unibo.gestione_concessionario.view.panelscliente;

import it.unibo.gestione_concessionario.commons.dto.Auto;
import it.unibo.gestione_concessionario.commons.dto.Marchio;
import it.unibo.gestione_concessionario.commons.dto.Tipologia;
import it.unibo.gestione_concessionario.controller.Controller;
import it.unibo.gestione_concessionario.view.CustomButton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AutoFiltrate extends JPanel {

    private JComboBox<Marchio> marchioComboBox;
    private JComboBox<Tipologia> tipologiaComboBox;
    private JButton filtraButton;
    private JTable autoTable;
    private DefaultTableModel tableModel;
    private Controller controller;

    public AutoFiltrate(Controller controller) {
        this.controller = controller;
        this.initialize();
    }

    private void initialize() {
        this.setLayout(new BorderLayout());

        JPanel filtroPanel = new JPanel(new GridLayout(3,2));

        marchioComboBox = new JComboBox<>(getMarchi().stream().toArray(Marchio[]::new));

        tipologiaComboBox = new JComboBox<>(getTipologie().stream().toArray(Tipologia[]::new));

        filtroPanel.add(new JLabel("Seleziona Marchio:"));
        filtroPanel.add(marchioComboBox);

        filtroPanel.add(new JLabel("Seleziona Tipologia:"));
        filtroPanel.add(tipologiaComboBox);

        filtraButton = new CustomButton("Filtra");
        filtroPanel.add(filtraButton);

        this.add(filtroPanel, BorderLayout.NORTH);

        tableModel = new TablesModel(new String[]{"Numero Telaio", "Modello", "Prezzo1", "Data Immatricolazione", "Targa"});
        autoTable = new PersTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(autoTable);
        this.add(scrollPane, BorderLayout.CENTER);

        filtraButton.addActionListener(e-> filtraAuto());
    }

    private List<Marchio> getMarchi() {
        return controller.allMarchi();
    }

    private List<Tipologia> getTipologie() {
        return controller.allTipologie();
    }

    private void filtraAuto() {
        Marchio marchioSelezionato = (Marchio) marchioComboBox.getSelectedItem();
        Tipologia tipologiaSelezionata = (Tipologia) tipologiaComboBox.getSelectedItem();

        List<Auto> autoFiltrate = controller.visualizzaAutoxMarchioxTipologia(marchioSelezionato, tipologiaSelezionata);

        tableModel.setRowCount(0);

        for (Auto auto : autoFiltrate) {
            tableModel.addRow(new Object[]{
                auto.getNumero_telaio(),
                auto.getDescrizioneModello(),  
                auto.getPrezzo(),             
                auto.getData().isPresent() ? auto.getData().get() : "",  
                auto.getTarga().isPresent() ? auto.getTarga().get() : "" 
            });
        }
    }
}
