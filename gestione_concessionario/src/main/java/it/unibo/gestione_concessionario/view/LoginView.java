package it.unibo.gestione_concessionario.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import it.unibo.gestione_concessionario.controller.Controller;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

public class LoginView implements View {

    private JFrame loginFrame;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private Controller controller;
    private JPanel loginPanel;

    public LoginView(Controller controller) {
        this.controller = controller;
        initializeFrame();
        initializeStartPanel();
    }

    // Frame Initialization
    private void initializeFrame() {
        loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(800, 200);
        loginFrame.setResizable(false);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setLayout(new FlowLayout());
    }

    // Initial Panel with Client/Employee buttons
    private void initializeStartPanel() {
        JPanel panel = new JPanel();

        JButton cliente = new JButton("Cliente");
        JButton dipendente = new JButton("Dipendente");

        cliente.addActionListener(e -> {showLoginPanel(false);
            this.controller.initCliente();
        });
        dipendente.addActionListener(e -> showLoginPanel(true));

        panel.add(cliente);
        panel.add(dipendente);

        loginFrame.getContentPane().removeAll(); // Properly clear the content pane
        loginFrame.add(panel);
        loginFrame.setVisible(true);
    }

    // Shows Login Panel based on the user type
    private void showLoginPanel(boolean isEmployee) {
        loginFrame.getContentPane().removeAll(); // Properly clear the content pane
        
        JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        loginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        emailLabel = new JLabel("Email:");
        passwordLabel = new JLabel("Password:");

        emailField = new JTextField(25);
        passwordField = new JPasswordField(25);

        loginButton = new JButton("Accedi");
        loginButton.addActionListener(isEmployee ? getEmployeeLoginListener() : getClientLoginListener());

        emailPanel.add(emailLabel);
        emailPanel.add(emailField);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        loginPanel.add(emailPanel);
        loginPanel.add(passwordPanel);
        loginPanel.add(loginButton);

        loginFrame.add(loginPanel);
        refreshGui();
    }

    // Client Login Action Listener
    private ActionListener getClientLoginListener() {
        return e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            if (!controller.checkLoginCliente(email, password)) {
                error();
            }
        };
    }

    // Employee Login Action Listener
    private ActionListener getEmployeeLoginListener() {
        return e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            if (!controller.checkLoginDipendente(email, password)) {
                error();
            }
        };
    }

    @Override
    public void start() {
        // Optionally implement the start logic
        System.out.println("Application started");
    }

    @Override
    public void stop() {
        // Optionally implement the stop logic
        System.out.println("Application stopped");
    }

    @Override
    public void error() {
        JOptionPane.showMessageDialog(null, 
                "Impossibile fare login", 
                "Errore di Login", 
                JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void refreshGui() {
        loginFrame.revalidate();
        loginFrame.repaint();
    }

    public static void main(String[] args) {
        // For testing purposes
        LoginView lv = new LoginView(new Controller());
        lv.start();
    }
}
