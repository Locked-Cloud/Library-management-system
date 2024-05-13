package project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Create extends JDialog {
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JLabel nameLabel;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JButton saveButton;
    private JButton closeButton;
    private JPasswordField passwordField1;
    private JTextField textField2;
    private JTextField textField1;
    private JLabel addUsersLabel;

    public Create() {
        initComponents(); // Initialize components

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void initComponents() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Set dialog default close operation

        nameField = new JTextField(20);
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);
        nameLabel = new JLabel("Name:");
        emailLabel = new JLabel("Email:");
        passwordLabel = new JLabel("Password:");
        saveButton = new JButton("Save");
        closeButton = new JButton("Close");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertUserIntoDatabase(); // Call method to insert user information into the database

            }
        });

        JPanel panel = new JPanel();
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(saveButton);
        panel.add(closeButton);
        add(panel); // Add panel directly to the JDialog

        pack(); // Pack the dialog to fit the components
        setLocationRelativeTo(null); // Center the dialog on the screen
    }

    // Method to insert user information into the database
    public void insertUserIntoDatabase() {
        // Get user input from text fields
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        // Check if any of the fields are empty
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method if any field is empty
        }

        // Trim input data to fit within the maximum length allowed for the 'CONTACT' column
        name = name.substring(0, Math.min(name.length(), 50)); // Assuming maximum length is 50 characters
        email = email.substring(0, Math.min(email.length(), 100)); // Assuming maximum length is 100 characters

        // Database connection details
        String url = "jdbc:mysql://localhost:3306/library"; // Update URL according to your database
        String username = "root"; // Update username
        String passwordDb = "root"; // Update password

        // SQL query to insert user information into the database
        String sql = "INSERT INTO users (USER_ID, NAME, PASSWORD, CONTACT) VALUES (?, ?, ?, ?)";

        try {
            // Establish database connection
            Connection connection = DriverManager.getConnection(url, username, passwordDb);

            // Prepare the SQL statement
            PreparedStatement statement = connection.prepareStatement(sql);

            // Set parameters for the statement
            statement.setString(1, name); // Set name in the USER_ID column
            statement.setString(2, name); // Set name in the NAME column
            statement.setString(3, password); // Set password in the PASSWORD column
            statement.setString(4, email); // Set email in the CONTACT column

            // Execute the SQL statement
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("User inserted successfully.");
                dispose();
                // Open the help window or perform other actions after successful user creation
            }

            // Close resources
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            System.err.println("Error inserting user into database: " + ex.getMessage());
            if (ex.getMessage().contains("Data too long")) {
                JOptionPane.showMessageDialog(null, "Input data too long. Please enter shorter values.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
