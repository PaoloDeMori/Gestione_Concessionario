package it.unibo.gestione_concessionario.view;

import javax.swing.*;
import it.unibo.gestione_concessionario.commons.dto.Cliente;
import it.unibo.gestione_concessionario.controller.Controller;

import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView implements View {

    private JFrame loginFrame;
    private JTextField emailField;
    private JPasswordField passwordField;
    private CustomButton loginButton;
    private CustomButton createAccountButton;
    private Controller controller;

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

    // Panel with Client/Employee selection buttons
    private void initializeStartPanel() {
        JPanel panel = new JPanel();

        CustomButton clienteButton = new CustomButton("Cliente");
        CustomButton dipendenteButton = new CustomButton("Dipendente");

        clienteButton.addActionListener(e -> {
            showLoginPanel(false);
            this.controller.initCliente();
        });

        dipendenteButton.addActionListener(e -> showLoginPanel(true));

        panel.add(clienteButton);
        panel.add(dipendenteButton);

        loginFrame.getContentPane().removeAll();  // Clear the content pane before switching
        loginFrame.add(panel);
        loginFrame.setVisible(true);
    }

    // Show Login Panel based on user type (Client or Employee)
    private void showLoginPanel(boolean isEmployee) {
        loginFrame.getContentPane().removeAll();  // Clear the content pane

        // Create email and password panels
        JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JLabel emailLabel = new JLabel("Email:");
        JLabel passwordLabel = new JLabel("Password:");

        emailField = new JTextField(25);
        passwordField = new JPasswordField(25);

        // Login and account creation buttons
        loginButton = new CustomButton("Accedi");
        loginButton.addActionListener(isEmployee ? getEmployeeLoginListener() : getClientLoginListener());

        createAccountButton = new CustomButton("Crea Account");
        createAccountButton.addActionListener(e -> showCreateAccountPanel());

        // Assemble login panel
        JPanel loginPanel = new JPanel(new GridLayout(2, 1, 20, 20));
        emailPanel.add(emailLabel);
        emailPanel.add(emailField);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        
        loginPanel.add(emailPanel);
        loginPanel.add(passwordPanel);
        loginPanel.add(loginButton);
        loginPanel.add(createAccountButton);

        loginFrame.add(loginPanel);
        refreshGui();
    }

    // Show Create Account Panel
    private void showCreateAccountPanel() {
        loginFrame.getContentPane().removeAll();  // Clear the content pane
        loginFrame.setSize(700, 450);

        JPanel createAccountPanel = new JPanel(new GridLayout(6, 2, 10, 10));

        // Form fields for account creation
        JLabel firstNameLabel = new JLabel("Nome:");
        JTextField firstNameField = new JTextField(25);
        JLabel lastNameLabel = new JLabel("Cognome:");
        JTextField lastNameField = new JTextField(25);
        JLabel telefonoLabel = new JLabel("Telefono:");
        JTextField telefonoField = new JTextField(25);
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(25);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(25);

        // Submit button for account creation
        CustomButton createAccountSubmitButton = new CustomButton("Crea Account");
        createAccountSubmitButton.addActionListener(e -> handleCreateAccount(
            firstNameField.getText(),
            lastNameField.getText(),
            telefonoField.getText(),
            emailField.getText(),
            new String(passwordField.getPassword())
        ));

        // Add form components to the panel
        createAccountPanel.add(firstNameLabel);
        createAccountPanel.add(firstNameField);
        createAccountPanel.add(lastNameLabel);
        createAccountPanel.add(lastNameField);
        createAccountPanel.add(telefonoLabel);
        createAccountPanel.add(telefonoField);
        createAccountPanel.add(emailLabel);
        createAccountPanel.add(emailField);
        createAccountPanel.add(passwordLabel);
        createAccountPanel.add(passwordField);
        createAccountPanel.add(new JLabel());  // Placeholder for layout alignment
        createAccountPanel.add(createAccountSubmitButton);

        loginFrame.add(createAccountPanel);
        refreshGui();
    }

    // Handle Account Creation
    private void handleCreateAccount(String firstName, String lastName, String telefono, String email, String password) {
        // Create Cliente and check if the account creation is successful
        if (controller.createCliente(new Cliente(firstName, lastName, telefono, email, password))) {
            JOptionPane.showMessageDialog(loginFrame, "Account creato con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);

            // Automatically login after account creation
            if (controller.checkLoginCliente(email, password)) {
                this.stop();
                controller.startCliente();
            } else {
                showLoginPanel(false);
            }
        } else {
            error("Impossibile creare account", "Errore creazione account");
        }
    }

    // Client Login Action Listener
    private ActionListener getClientLoginListener() {
        return e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (!controller.checkLoginCliente(email, password)) {
                error("Impossibile eseguire il Login", "Errore di Login");
            } else {
                this.stop();
                controller.startCliente();
            }
        };
    }

    // Employee Login Action Listener
    private ActionListener getEmployeeLoginListener() {
        return e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (!controller.checkLoginDipendente(email, password)) {
                error("Impossibile eseguire il Login", "Errore di Login");
            }
        };
    }

    @Override
    public void start() {
        System.out.println("Application started");
    }

    @Override
    public void stop() {
        loginFrame.removeAll();
        loginFrame.setVisible(false);
    }

    @Override
    public void error(String errore, String tipoDiErrore) {
        JOptionPane.showMessageDialog(null, errore, tipoDiErrore, JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void refreshGui() {
        loginFrame.revalidate();
        loginFrame.repaint();
    }

    public static void main(String[] args) {
        LoginView lv = new LoginView(new Controller());
        lv.start();
    }
}
