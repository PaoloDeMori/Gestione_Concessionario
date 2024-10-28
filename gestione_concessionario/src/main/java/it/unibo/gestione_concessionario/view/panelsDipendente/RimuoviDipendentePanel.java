package it.unibo.gestione_concessionario.view.panelsdipendente;

import javax.swing.*;
import it.unibo.gestione_concessionario.commons.dto.Dipendente;
import it.unibo.gestione_concessionario.controller.Controller;
import it.unibo.gestione_concessionario.view.CustomButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class RimuoviDipendentePanel extends JPanel {

    private JComboBox<Dipendente> dipendenteBox;
    private Dipendente dipendente;

    private Controller controller;
    private CustomButton saveDipendente;

    private JPanel maiPanel;

    public RimuoviDipendentePanel(Controller controller) {
        setLayout(new GridLayout(1, 1));
        this.controller = controller;

        this.setMainPanel();

        this.add(maiPanel);
    }

    private void setMainPanel() {
        maiPanel = new JPanel();
        maiPanel.setLayout(new FlowLayout());


        maiPanel.add(new JLabel("Dipendenti:"));
        dipendenteBox = new JComboBox<>(getDipendente().stream().toArray(Dipendente[]::new));
        dipendente = ((Dipendente) dipendenteBox.getItemAt(1));
        dipendenteBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dipendente = ((Dipendente) dipendenteBox.getSelectedItem());
            }

        });
        maiPanel.add(dipendenteBox);

        saveDipendente = new CustomButton("Rimuovi Dipendente");
        maiPanel.add(saveDipendente);
        saveDipendente.addActionListener(e -> {
            try {
                controller.rimuoviDipendente(dipendente.eMail());
                JOptionPane.showMessageDialog(this, "Dipendente rimosso con successo");
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(this, "Impossibile rimuovere dipendente");
            }
        });

    }

    private List<Dipendente> getDipendente() {
        return controller.visualizzaDipendenti();
    }

    public void addModello() {
        this.removeAll();
        this.setMainPanel();
        this.add(maiPanel);
        dipendenteBox = new JComboBox<>(getDipendente().stream().toArray(Dipendente[]::new));
        this.revalidate();
        this.repaint();
    }

}
