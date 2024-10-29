package it.unibo.gestione_concessionario.view.panelscliente;

import it.unibo.gestione_concessionario.commons.dto.Auto;
import it.unibo.gestione_concessionario.commons.dto.Marchio;
import it.unibo.gestione_concessionario.controller.Controller;
import it.unibo.gestione_concessionario.view.CustomButton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AutoScontate extends JPanel {

    private JComboBox<Marchio> marchioComboBox;
    private JButton filtraButton;
    private JTable autoTable;
    private DefaultTableModel tableModel;
    private Controller controller;

    public AutoScontate(Controller controller) {
        this.controller = controller;
        this.initialize();
    }

    private void initialize() {
        this.setLayout(new BorderLayout());

        JPanel filtroPanel = new JPanel(new GridLayout(3,2));

        marchioComboBox = new JComboBox<>(getMarchi().stream().toArray(Marchio[]::new));

        filtroPanel.add(new JLabel("Seleziona Marchio:"));
        filtroPanel.add(marchioComboBox);

        filtraButton = new CustomButton("Filtra");
        filtroPanel.add(filtraButton);

        this.add(filtroPanel, BorderLayout.NORTH);

        tableModel = new TablesModel(new String[]{"Numero Telaio", "Modello", "Prezzo Scontato", "Data Immatricolazione", "Targa"});
        autoTable = new PersTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(autoTable);
        this.add(scrollPane, BorderLayout.CENTER);

        filtraButton.addActionListener(e->filtraAuto());
    

    }
    private List<Marchio> getMarchi() {
        return controller.allMarchi();
    }

    private void filtraAuto() {
        Marchio marchioSelezionato = (Marchio) marchioComboBox.getSelectedItem();

        List<Auto> autoFiltrate = controller.visualizzaAutoScontate(marchioSelezionato);

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
