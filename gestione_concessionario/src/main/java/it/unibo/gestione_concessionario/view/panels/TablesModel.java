package it.unibo.gestione_concessionario.view.panels;

import javax.swing.table.DefaultTableModel;


public class TablesModel extends DefaultTableModel {
    public TablesModel(Object[][] data, String[] columnNames){
        super(data,columnNames);
    }
    @Override
            public boolean isCellEditable(int row, int column) {
                // Rende tutte le celle non modificabili
                return false;
            }
 
    
}
