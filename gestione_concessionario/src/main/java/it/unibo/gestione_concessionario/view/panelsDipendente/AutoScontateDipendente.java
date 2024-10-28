package it.unibo.gestione_concessionario.view.panelsdipendente;

import it.unibo.gestione_concessionario.commons.dto.Auto;
import it.unibo.gestione_concessionario.commons.dto.Marchio;
import it.unibo.gestione_concessionario.controller.Controller;
import it.unibo.gestione_concessionario.view.View;
import it.unibo.gestione_concessionario.view.panelscliente.PersTable;
import it.unibo.gestione_concessionario.view.panelscliente.TablesModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AutoScontateDipendente extends JPanel {

    private JTable autoTable;
    private DefaultTableModel tableModel;
    private Controller controller;

    public AutoScontateDipendente(Controller controller) {
        this.controller = controller;
        this.initialize();
    }

    private void initialize() {
        this.setLayout(new BorderLayout());
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel title = new JLabel("Tutte le auto con sconti o offerte");
        title.setFont(View.titleFont);
        title.setHorizontalAlignment(JLabel.CENTER);
        titlePanel.add(title);
        this.add(titlePanel,BorderLayout.NORTH);
        
        tableModel = new TablesModel(new String[]{"Numero Telaio", "Modello", "Prezzo Scontato"});
        autoTable = new PersTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(autoTable);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void filtraAuto() {
        Marchio marchioSelezionato = this.controller.getUserMarchio();

        List<Auto> autoFiltrate = controller.visualizzaAutoScontate(marchioSelezionato);

        tableModel.setRowCount(0);

        for (Auto auto : autoFiltrate) {
            tableModel.addRow(new Object[]{
                auto.getNumero_telaio(),
                auto.getDescrizioneModello(),  
                auto.getPrezzo()
            });
        }
    }
}
