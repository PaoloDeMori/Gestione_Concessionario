package it.unibo.gestione_concessionario.view.panelscliente;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PersTable extends JTable{

    public PersTable(DefaultTableModel tm){
        super(tm);
        this.getTableHeader().setReorderingAllowed(false);
        this.getTableHeader().setResizingAllowed(false);
    }

    public PersTable(){
        super();
        this.getTableHeader().setReorderingAllowed(false);
        this.getTableHeader().setResizingAllowed(false);
    }
    
}
