<<<<<<< HEAD
package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class Signup extends JDialog {
    private JTextField nameField;
    private JTextField phoneField;
    private JPasswordField passwordField;
    private JLabel nameLabel;
    private JLabel phoneLabel;
    private JLabel passwordLabel;
    private JButton saveButton;
    private JButton closeButton;
    private JPasswordField passwordField1;
    private JTextField textField2;
    private JTextField textField1;
    private JLabel emailLabel;
    private JLabel signupLabel;
    private JPanel Signup;
    private JPanel signupPanel;
    private Login loginDialog; // Optional reference to a Login dialog

    public Signup(Login existingLoginDialog) {
        this.loginDialog = existingLoginDialog; // Store the existing login dialog reference
        setTitle("Sign Up");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        setSize(450, 300);
        setMinimumSize(new Dimension(450, 300));
        setLocationRelativeTo(null); // Center the dialog on the screen
        setVisible(true);
    }

    public Signup() {
        this(null); // If no Login dialog is passed, handle accordingly in the constructor
    }

    private void initComponents() {
        signupPanel = new JPanel(new BorderLayout(10, 10));
        signupPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel signupTitleLabel = new JLabel("Sign Up", SwingConstants.CENTER);
        signupTitleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        signupTitleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        nameField = new JTextField(20);
        phoneField = new JTextField(20);
        passwordField = new JPasswordField(20);

        nameLabel = new JLabel("Name:");
        phoneLabel = new JLabel("Phone Number:");
        passwordLabel = new JLabel("Password:");

        saveButton = new JButton("Save");
        closeButton = new JButton("Close");

        closeButton.addActionListener(this::closeAction);
        saveButton.addActionListener(this::saveAction);

        setupLayout(signupTitleLabel);
    }

    private void setupLayout(JLabel signupTitleLabel) {
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        fieldsPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        fieldsPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        fieldsPanel.add(phoneLabel, gbc);
        gbc.gridx = 1;
        fieldsPanel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        fieldsPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        fieldsPanel.add(passwordField, gbc);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonsPanel.add(saveButton);
        buttonsPanel.add(closeButton);

        signupPanel.add(signupTitleLabel, BorderLayout.NORTH);
        signupPanel.add(fieldsPanel, BorderLayout.CENTER);
        signupPanel.add(buttonsPanel, BorderLayout.SOUTH);

        add(signupPanel);
    }

    private void closeAction(ActionEvent e) {
        dispose();
        reactivateLoginDialog();
    }

    private void saveAction(ActionEvent e) {
        if (validateInput()) {
            if (insertUserIntoDatabase()) {
                dispose();
                reactivateLoginDialog();
            }
        }
    }

    private boolean validateInput() {
        String phone = phoneField.getText().trim();
        String name = nameField.getText().trim();
        if (name.isEmpty() || phone.isEmpty() || passwordField.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Check if phone number is numeric
        if (!phone.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid phone number.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Check if the name already exists in the database
        if (userExists(name)) {
            JOptionPane.showMessageDialog(this, "This username already exists. Please choose another.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private boolean userExists(String name) {
        String url = "jdbc:mysql://localhost:3306/library";
        String username = "root";
        String password = "root";
        String query = "SELECT COUNT(*) FROM users WHERE USER_ID = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            return false;
        }
    }

    private boolean insertUserIntoDatabase() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String password = new String(passwordField.getPassword());

        String url = "jdbc:mysql://localhost:3306/library";
        String username = "root";
        String passwordDb = "root";
        String sql = "INSERT INTO users (USER_ID, NAME, PASSWORD, CONTACT) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, passwordDb);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, name);
            statement.setString(3, password);
            statement.setString(4, phone);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException ex) {
            System.err.println("Error inserting user into database: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Error inserting user into database.", "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void reactivateLoginDialog() {
        if (loginDialog == null) {
            loginDialog = new Login(null);
            loginDialog.setVisible(true);
        } else {
            loginDialog.setVisible(true);
        }
    }
=======
package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class Signup extends JDialog {
    private JTextField nameField;
    private JTextField phoneField;
    private JPasswordField passwordField;
    private JLabel nameLabel;
    private JLabel phoneLabel;
    private JLabel passwordLabel;
    private JButton saveButton;
    private JButton closeButton;
    private JPasswordField passwordField1;
    private JTextField textField2;
    private JTextField textField1;
    private JLabel emailLabel;
    private JLabel signupLabel;
    private JPanel Signup;
    private JPanel signupPanel;
    private Login loginDialog; // Optional reference to a Login dialog

    public Signup(Login existingLoginDialog) {
        this.loginDialog = existingLoginDialog; // Store the existing login dialog reference
        setTitle("Sign Up");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        setSize(450, 300);
        setMinimumSize(new Dimension(450, 300));
        setLocationRelativeTo(null); // Center the dialog on the screen
        setVisible(true);
    }

    public Signup() {
        this(null); // If no Login dialog is passed, handle accordingly in the constructor
    }

    private void initComponents() {
        signupPanel = new JPanel(new BorderLayout(10, 10));
        signupPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel signupTitleLabel = new JLabel("Sign Up", SwingConstants.CENTER);
        signupTitleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        signupTitleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        nameField = new JTextField(20);
        phoneField = new JTextField(20);
        passwordField = new JPasswordField(20);

        nameLabel = new JLabel("Name:");
        phoneLabel = new JLabel("Phone Number:");
        passwordLabel = new JLabel("Password:");

        saveButton = new JButton("Save");
        closeButton = new JButton("Close");

        closeButton.addActionListener(this::closeAction);
        saveButton.addActionListener(this::saveAction);

        setupLayout(signupTitleLabel);
    }

    private void setupLayout(JLabel signupTitleLabel) {
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        fieldsPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        fieldsPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        fieldsPanel.add(phoneLabel, gbc);
        gbc.gridx = 1;
        fieldsPanel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        fieldsPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        fieldsPanel.add(passwordField, gbc);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonsPanel.add(saveButton);
        buttonsPanel.add(closeButton);

        signupPanel.add(signupTitleLabel, BorderLayout.NORTH);
        signupPanel.add(fieldsPanel, BorderLayout.CENTER);
        signupPanel.add(buttonsPanel, BorderLayout.SOUTH);

        add(signupPanel);
    }

    private void closeAction(ActionEvent e) {
        dispose();
        reactivateLoginDialog();
    }

    private void saveAction(ActionEvent e) {
        if (validateInput()) {
            if (insertUserIntoDatabase()) {
                dispose();
                reactivateLoginDialog();
            }
        }
    }

    private boolean validateInput() {
        String phone = phoneField.getText().trim();
        String name = nameField.getText().trim();
        if (name.isEmpty() || phone.isEmpty() || passwordField.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Check if phone number is numeric
        if (!phone.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid phone number.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Check if the name already exists in the database
        if (userExists(name)) {
            JOptionPane.showMessageDialog(this, "This username already exists. Please choose another.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private boolean userExists(String name) {
        String url = "jdbc:mysql://localhost:3306/library";
        String username = "root";
        String password = "246810";
        String query = "SELECT COUNT(*) FROM users WHERE USER_ID = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
            return false;
        }
    }

    private boolean insertUserIntoDatabase() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String password = new String(passwordField.getPassword());

        String url = "jdbc:mysql://localhost:3306/library";
        String username = "root";
        String passwordDb = "246810";
        String sql = "INSERT INTO users (USER_ID, NAME, PASSWORD, CONTACT) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, passwordDb);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, name);
            statement.setString(3, password);
            statement.setString(4, phone);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException ex) {
            System.err.println("Error inserting user into database: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Error inserting user into database.", "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void reactivateLoginDialog() {
        if (loginDialog == null) {
            loginDialog = new Login(null);
            loginDialog.setVisible(true);
        } else {
            loginDialog.setVisible(true);
        }
    }
>>>>>>> 00c9df7 (Describe what you updated)
}