package it.unibo.gestione_concessionario.view.panelsdipendente;

import javax.swing.*;
import it.unibo.gestione_concessionario.commons.dto.Dipendente;
import it.unibo.gestione_concessionario.commons.dto.Marchio;
import it.unibo.gestione_concessionario.controller.Controller;
import it.unibo.gestione_concessionario.view.CustomButton;
import it.unibo.gestione_concessionario.view.View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class AddDipendentePanel extends JPanel {

    private JTextField nomeField;
    private JTextField cognomeField;
    private JComboBox<Boolean> isResponsabileBox;
    private JComboBox<Marchio> marchioBox;
    private JTextField eMailField;
    private JTextField telefonoField;
    private JTextField passwordField;
    private Boolean isResponsabile=false;
    private Marchio marchio;

    private Controller controller;
    private CustomButton saveDipendente;

    private JPanel maiPanel;

    public AddDipendentePanel(Controller controller) {
        setLayout(new BorderLayout());
        this.controller = controller;
        
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel title = new JLabel("Aggiungi Un Dipendente");
        title.setFont(View.titleFont);
        title.setHorizontalAlignment(JLabel.CENTER);
        titlePanel.add(title);
        this.add(titlePanel,BorderLayout.NORTH);

        this.setMainPanel();

        this.add(maiPanel,BorderLayout.CENTER);
    }

    private void setMainPanel() {



        maiPanel = new JPanel();
        maiPanel.setLayout(new GridLayout(14, 2, 5, 5));

        maiPanel.add(new JLabel("Nome :"));
        nomeField = new JTextField("Nome Dipendente");
        maiPanel.add(nomeField);

        maiPanel.add(new JLabel("Cognome :"));
        cognomeField = new JTextField("Cognome Dipendente");
        maiPanel.add(cognomeField);

        maiPanel.add(new JLabel("Email :"));
        eMailField = new JTextField("Email Dipendente");
        maiPanel.add(eMailField);

        maiPanel.add(new JLabel("Telefono :"));
        telefonoField = new JTextField("telefono Dipendente");
        maiPanel.add(telefonoField);

        maiPanel.add(new JLabel("Password :"));
        passwordField = new JTextField("Password Dipendente");
        maiPanel.add(passwordField);

        maiPanel.add(new JLabel("Marchi senza dipendenti:"));
        marchioBox = new JComboBox<>(getMarchio().stream().toArray(Marchio[]::new));
        marchio = ((Marchio) marchioBox.getSelectedItem());
        marchioBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                marchio = ((Marchio) marchioBox.getSelectedItem());
            }

        });
        maiPanel.add(marchioBox);


        Boolean[] options = { false, true };
        maiPanel.add(new JLabel("Responsabile?"));
        isResponsabileBox = new JComboBox<>(options);
        isResponsabileBox.addActionListener(e -> isResponsabile=(Boolean)isResponsabileBox.getSelectedItem());
        maiPanel.add(isResponsabileBox);

        saveDipendente = new CustomButton("Aggiungi Dipendente");
        maiPanel.add(saveDipendente);
        saveDipendente.addActionListener(e -> {
            try {
                controller.aggiungiDipendente(new Dipendente(marchio.idMarchio(), nomeField.getText(), cognomeField.getText(), telefonoField.getText(), isResponsabile, eMailField.getText()),passwordField.getText());
                JOptionPane.showMessageDialog(this, "Dipendente creato con successo");
                updateMarchio();
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(this, "Impossibile creare dipendente");
            }
        });

    }

    private List<Marchio> getMarchio() {
        return controller.visualizzaMarchiSenzaDipendente();
    }

    public void updateMarchio() {
        marchioBox.removeAllItems();
        List<Marchio> marchi = getMarchio();
        for (Marchio m : marchi) {
            marchioBox.addItem(m);
        }
        if (!marchi.isEmpty()) {
            marchio = marchi.get(0);
        }
        marchioBox.revalidate();
        marchioBox.repaint();
        this.revalidate();
        this.repaint();
    }

}
