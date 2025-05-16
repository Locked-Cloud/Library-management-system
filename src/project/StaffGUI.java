package project;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class StaffGUI extends JFrame {

    private JTextField idField, nameField, contactField, passwordField;
    private JButton addButton, updateButton, deleteButton, clearButton, closeButton;
    private Connection conn;

    public StaffGUI() {
        setTitle("Library Management - Staff Panel");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Connect to database
        connectToDatabase();

        // Create main panel with padding
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Create header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Staff Management");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        // Create form panel
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 15));
        formPanel.setBorder(BorderFactory.createTitledBorder("Staff Information"));

        // Form fields
        JLabel idLabel = createFormLabel("Staff ID:");
        JLabel nameLabel = createFormLabel("Name:");
        JLabel passwordLabel = createFormLabel("Password:");
        JLabel contactLabel = createFormLabel("Contact:");

        idField = createFormField();
        nameField = createFormField();
        passwordField = createFormField();
        contactField = createFormField();

        formPanel.add(idLabel); formPanel.add(idField);
        formPanel.add(nameLabel); formPanel.add(nameField);
        formPanel.add(passwordLabel); formPanel.add(passwordField);
        formPanel.add(contactLabel); formPanel.add(contactField);

        // Create button panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 10, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        addButton = createButton("Add", new Color(34, 139, 34));
        updateButton = createButton("Update", new Color(30, 144, 255));
        deleteButton = createButton("Delete", new Color(220, 20, 60));
        clearButton = createButton("Clear", new Color(128, 128, 128));
        closeButton = createButton("Close", new Color(105, 105, 105));

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(closeButton);

        // Add components to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add main panel to frame
        add(mainPanel);

        // Event handlers
        addButton.addActionListener(e -> addStaff());
        updateButton.addActionListener(e -> updateStaff());
        deleteButton.addActionListener(e -> deleteStaff());
        clearButton.addActionListener(e -> clearFields());
        closeButton.addActionListener(e -> dispose());
    }

    private JLabel createFormLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return label;
    }

    private JTextField createFormField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return field;
    }

    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "246810");
            System.out.println("Database connected successfully.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database connection failed: " + e.getMessage(),
                    "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addStaff() {
        if (!validateFields()) return;

        try {
            String sql = "INSERT INTO Staff (STAFF_ID, NAME, PASSWORD, CONTACT) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, idField.getText());
            stmt.setString(2, nameField.getText());
            stmt.setString(3, passwordField.getText());
            stmt.setInt(4, Integer.parseInt(contactField.getText()));

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Staff added successfully.",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
        } catch (SQLException e) {
            showError("Error adding staff: " + e.getMessage());
        }
    }

    private void updateStaff() {
        if (!validateFields()) return;

        try {
            String sql = "UPDATE Staff SET NAME=?, PASSWORD=?, CONTACT=? WHERE STAFF_ID=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nameField.getText());
            stmt.setString(2, passwordField.getText());
            stmt.setInt(3, Integer.parseInt(contactField.getText()));
            stmt.setString(4, idField.getText());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Staff updated successfully.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Staff ID not found.",
                        "Not Found", JOptionPane.WARNING_MESSAGE);
            }
            clearFields();
        } catch (SQLException e) {
            showError("Error updating staff: " + e.getMessage());
        }
    }

    private void deleteStaff() {
        if (idField.getText().trim().isEmpty()) {
            showError("Please enter Staff ID to delete");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this staff member?",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            String sql = "DELETE FROM Staff WHERE STAFF_ID=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, idField.getText());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Staff deleted successfully.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Staff ID not found.",
                        "Not Found", JOptionPane.WARNING_MESSAGE);
            }
            clearFields();
        } catch (SQLException e) {
            showError("Error deleting staff: " + e.getMessage());
        }
    }

    private boolean validateFields() {
        if (idField.getText().trim().isEmpty() ||
                nameField.getText().trim().isEmpty() ||
                passwordField.getText().trim().isEmpty() ||
                contactField.getText().trim().isEmpty()) {
            showError("All fields are required");
            return false;
        }

        try {
            Integer.parseInt(contactField.getText());
        } catch (NumberFormatException e) {
            showError("Contact must be a valid number");
            return false;
        }

        return true;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message,
                "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        passwordField.setText("");
        contactField.setText("");
        idField.requestFocus();
    }

    // Main method for testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StaffGUI().setVisible(true));
    }
}
