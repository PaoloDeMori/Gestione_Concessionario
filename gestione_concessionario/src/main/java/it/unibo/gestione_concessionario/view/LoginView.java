package it.unibo.gestione_concessionario.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

import it.unibo.gestione_concessionario.commons.dto.Cliente;
import it.unibo.gestione_concessionario.controller.Controller;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

public class LoginView implements View {

    private JFrame loginFrame;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JTextField emailField;
    private JPasswordField passwordField;
    private CustomButton loginButton;
    private CustomButton createAccountButton;  // New button for account creation
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

        CustomButton cliente = new CustomButton("Cliente");
        CustomButton dipendente = new CustomButton("Dipendente");

        cliente.addActionListener(e -> { 
            showLoginPanel(false); 
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
        loginPanel = new JPanel(new GridLayout(2,1,20,20));

        emailLabel = new JLabel("Email:");
        passwordLabel = new JLabel("Password:");

        emailField = new JTextField(25);
        passwordField = new JPasswordField(25);

        loginButton = new CustomButton("Accedi");
        loginButton.addActionListener(isEmployee ? getEmployeeLoginListener() : getClientLoginListener());

        createAccountButton = new CustomButton("Crea Account");  // New account creation button
        createAccountButton.addActionListener(e -> showCreateAccountPanel()); // Open create account panel on click

        emailPanel.add(emailLabel);
        emailPanel.add(emailField);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        loginPanel.add(emailPanel);
        loginPanel.add(passwordPanel);
        loginPanel.add(loginButton);
        loginPanel.add(createAccountButton);  // Add the new button to the panel
    
        loginFrame.add(loginPanel);
        refreshGui();
    }

    // Opens Account Creation Panel
    private void showCreateAccountPanel() {
        loginFrame.getContentPane().removeAll();  // Clear the content pane
        loginFrame.setSize(700,450);
        JPanel createAccountPanel = new JPanel(new GridLayout(6, 2, 10, 10));  // Layout for form fields

        // Form fields for account creation
        JLabel firstNameLabel = new JLabel("Nome:");
        JTextField firstNameField = new JTextField(25);
        JLabel lastNameLabel = new JLabel("Cognome:");
        JTextField lastNameField = new JTextField(25);
        JLabel telefonoLabel = new JLabel("telefono:");
        JTextField telefonoField = new JTextField(25);
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(25);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(25);

        CustomButton createAccountSubmitButton = new CustomButton("Crea Account");
        createAccountSubmitButton.addActionListener(e -> {
            // Collect data from form fields
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String telefono = telefonoField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            // Call the controller to create the account
            if(controller.createCliente(new Cliente(firstName, lastName, telefono,email, password)))
            {
                JOptionPane.showMessageDialog(loginFrame, "Account creato con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                if(this.controller.checkLoginCliente(email, password)){
                    this.stop();
                    this.controller.startCliente();
                }
                else{
                    this.showLoginPanel(false);
                }
            }
            else{
                error("Impossibile creare account", "errore creazione account");
            }

            // Return to login screen
            initializeStartPanel();
        });

        // Add components to the panel
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
        createAccountPanel.add(new JLabel());  // Empty cell for layout alignment
        createAccountPanel.add(createAccountSubmitButton);

        loginFrame.add(createAccountPanel);
        refreshGui();  // Refresh to show the new account creation form
    }

    // Client Login Action Listener
    private ActionListener getClientLoginListener() {
        return e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            if (!controller.checkLoginCliente(email, password)) {
                error("Impossibile eseguire il Login","Errore di Login");
            }
        };
    }

    // Employee Login Action Listener
    private ActionListener getEmployeeLoginListener() {
        return e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            if (!controller.checkLoginDipendente(email, password)) {
                error("Impossibile eseguire il Login","Errore di Login");
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
    public void error(String errore,String tipoDiErrore) {
        JOptionPane.showMessageDialog(null, 
                errore, 
                tipoDiErrore, 
                JOptionPane.ERROR_MESSAGE);
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
