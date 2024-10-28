package it.unibo.gestione_concessionario.view.panelsdipendente;


import it.unibo.gestione_concessionario.commons.dto.Vendita;
import it.unibo.gestione_concessionario.controller.Controller;
import it.unibo.gestione_concessionario.view.panelscliente.PersTable;
import it.unibo.gestione_concessionario.view.panelscliente.TablesModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VenditeDipendente extends JPanel {

    private JTable venditeTable;
    private DefaultTableModel tableModel;
    private Controller controller;

    public VenditeDipendente(Controller controller) {
        this.controller = controller;
        this.initialize();
    }

    private void initialize() {
        this.setLayout(new BorderLayout());
        
        tableModel = new TablesModel(new String[]{"Prezzo", "Tipologia", "Data", "Ora", "Dipendente", "Cliente","Numero Telaio"});
        venditeTable = new PersTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(venditeTable);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void filtraVendite() {
        List<Vendita> vendite = controller.allVendite();

        tableModel.setRowCount(0);

        for (Vendita vendita : vendite) {
            tableModel.addRow(new Object[]{
                vendita.contratto().getPrezzo(),
                vendita.contratto().getTipologia(),
                vendita.data(),             
                vendita.ora(),
                controller.getDipendenteNameById(vendita.id_dipendente()),
                controller.getClienteNameById(vendita.codCliente()),
                vendita.nuremo_telaio()
            });
        }
    }
}
