package it.unibo.gestione_concessionario.view;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import it.unibo.gestione_concessionario.commons.dto.Cliente;
import it.unibo.gestione_concessionario.controller.Controller;

public class LoginView implements View {

    private JFrame loginFrame;
    private JTextField emailField;
    private JPasswordField passwordField;
    private CustomButton loginButton;
    private CustomButton createAccountButton;
    private Controller controller;
    private boolean isInit = false;

    public LoginView(Controller controller) {
        this.controller = controller;
        initializeFrame();
        initializeStartPanel();
    }

    private void initializeFrame() {
        loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        loginFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (isInit) {
                    try {
                        controller.stop();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(loginFrame, ex.getMessage(),
                                "Impossibile chiudere la connessione", JOptionPane.ERROR_MESSAGE);
                    }
                }
                loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                loginFrame.dispose();
                System.exit(0);
            }
        });
        loginFrame.setSize(800, 200);
        loginFrame.setResizable(false);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setLayout(new FlowLayout());
    }

    private void initializeStartPanel() {
        JPanel panel = new JPanel();

        CustomButton clienteButton = new CustomButton("Cliente");
        CustomButton dipendenteButton = new CustomButton("Dipendente");

        clienteButton.addActionListener(e -> {
            showLoginPanel(false);
            this.controller.initCliente();
            isInit = true;
        });

        dipendenteButton.addActionListener(e -> {
            showLoginPanel(true);
            this.controller.initDipendente();
            isInit = true;
        });

        dipendenteButton.addActionListener(e -> showLoginPanel(true));

        panel.add(clienteButton);
        panel.add(dipendenteButton);

        loginFrame.getContentPane().removeAll();
        loginFrame.add(panel);
    }

    private void showLoginPanel(boolean isEmployee) {
        loginFrame.getContentPane().removeAll();

        JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JLabel emailLabel = new JLabel("Email:");
        JLabel passwordLabel = new JLabel("Password:");

        emailField = new JTextField(25);
        passwordField = new JPasswordField(25);

        loginButton = new CustomButton("Accedi");
        loginButton.addActionListener(isEmployee ? getEmployeeLoginListener() : getClientLoginListener());
        if (!isEmployee) {
            createAccountButton = new CustomButton("Crea Account");
            createAccountButton.addActionListener(e -> showCreateAccountPanel());
        }
        JPanel loginPanel = new JPanel(new GridLayout(2, 1, 20, 20));
        emailPanel.add(emailLabel);
        emailPanel.add(emailField);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        loginPanel.add(emailPanel);
        loginPanel.add(passwordPanel);
        loginPanel.add(loginButton);
        if (!isEmployee) {
            loginPanel.add(createAccountButton);
        }

        loginFrame.add(loginPanel);
        refreshGui();
    }

    private void showCreateAccountPanel() {
        loginFrame.getContentPane().removeAll();
        loginFrame.setSize(700, 450);

        JPanel createAccountPanel = new JPanel(new GridLayout(6, 2, 10, 10));

        JLabel firstNameLabel = new JLabel("Nome:");
        JTextField firstNameField = new JTextField(25);
        JLabel lastNameLabel = new JLabel("Cognome:");
        JTextField lastNameField = new JTextField(25);
        JLabel telefonoLabel = new JLabel("Telefono:");
        JTextField telefonoField = new JTextField(25);
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(25);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(25);

        CustomButton createAccountSubmitButton = new CustomButton("Crea Account");
        createAccountSubmitButton.addActionListener(e -> handleCreateAccount(
                firstNameField.getText(),
                lastNameField.getText(),
                telefonoField.getText(),
                emailField.getText(),
                new String(passwordField.getPassword())));

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
        createAccountPanel.add(new JLabel());
        createAccountPanel.add(createAccountSubmitButton);

        loginFrame.add(createAccountPanel);
        refreshGui();
    }

    private void handleCreateAccount(String firstName, String lastName, String telefono, String email,
            String password) {
        try {
            if (controller.createCliente(new Cliente(firstName, lastName, telefono, email, password))) {
                JOptionPane.showMessageDialog(loginFrame, "Account creato con successo!", "Successo",
                        JOptionPane.INFORMATION_MESSAGE);

                if (controller.checkLoginCliente(email, password)) {
                    this.stop();
                    controller.startCliente();
                } else {
                    showLoginPanel(false);
                }
            } else {
                error("Impossibile creare account", "Errore creazione account");
            }
        } catch (SQLException e) {
            error("Impossibile creare account", "Errore creazione account");
        }
    }

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

    private ActionListener getEmployeeLoginListener() {
        return e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (!controller.checkLoginDipendente(email, password)) {
                error("Impossibile eseguire il Login", "Errore di Login");
            } else {
                this.stop();
                controller.startDipendente();
            }
        };
    }

    @Override
    public void start() {
        loginFrame.setVisible(true);
    }

    public void stop() {
        loginFrame.removeAll();
        loginFrame.setVisible(false);
    }

    public void error(String errore, String tipoDiErrore) {
        JOptionPane.showMessageDialog(null, errore, tipoDiErrore, JOptionPane.ERROR_MESSAGE);
    }

    public void refreshGui() {
        loginFrame.revalidate();
        loginFrame.repaint();
    }

}
