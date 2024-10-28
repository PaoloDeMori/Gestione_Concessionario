package it.unibo.gestione_concessionario.view.panelsdipendente;

import it.unibo.gestione_concessionario.commons.dto.Offerta;
import it.unibo.gestione_concessionario.controller.Controller;
import it.unibo.gestione_concessionario.view.View;
import it.unibo.gestione_concessionario.view.panelscliente.PersTable;
import it.unibo.gestione_concessionario.view.panelscliente.TablesModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class OfferteDisponibiliPanel extends JPanel {

    private JTable autoTable;
    private DefaultTableModel tableModel;
    private Controller controller;

    public OfferteDisponibiliPanel(Controller controller) {
        this.controller = controller;
        this.initialize();
    }

    private void initialize() {
        this.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel title = new JLabel("Offerte Attive");
        title.setFont(View.titleFont);
        title.setHorizontalAlignment(JLabel.CENTER);
        titlePanel.add(title);
        this.add(titlePanel,BorderLayout.NORTH);
        
        tableModel = new TablesModel(new String[]{"percentuale", "data inizio", "data fine"});
        autoTable = new PersTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(autoTable);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void filtraOfferte() {
        List<Offerta> offerte = controller.allOfferte();

        tableModel.setRowCount(0);

        for (Offerta off : offerte) {
            tableModel.addRow(new Object[]{
                off.percentuale(),
                off.dataInizio(),
                off.dataFine()
            });
        }
    }
}
