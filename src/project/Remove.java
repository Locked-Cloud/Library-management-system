<<<<<<< HEAD
package project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Remove extends JFrame {
    private JLabel removeLabel;
    private JTextField textField1;
    private JButton removeButton;
    private JLabel user_idLabel;
    private JButton closeButton;

    public Remove() {
        setTitle("Remove User");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        removeLabel = new JLabel("Enter User ID:");
        textField1 = new JTextField(10);
        removeButton = new JButton("Remove");
        user_idLabel = new JLabel("User ID:");
        closeButton = new JButton("Close");

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = textField1.getText().trim();
                if (!userId.isEmpty()) {
                    if (removeUser(userId)) {
                        JOptionPane.showMessageDialog(Remove.this, "User with ID " + userId + " removed successfully.");
                    } else {
                        JOptionPane.showMessageDialog(Remove.this, "Failed to remove user with ID " + userId + ".", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(Remove.this, "Please enter a user ID to remove.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        panel.add(user_idLabel);
        panel.add(textField1);
        panel.add(removeButton);
        panel.add(closeButton);
        add(panel);

        setVisible(true);
    }

    private boolean removeUser(String userId) {
        // JDBC connection parameters
        String url = "jdbc:mysql://localhost:3306/library";
        String user = "root";
        String password = "root";

        // SQL query to delete the user based on user ID
        String sql = "DELETE FROM users WHERE user_id = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Set the user ID parameter
            statement.setString(1, userId);

            // Execute the deletion query
            int rowsAffected = statement.executeUpdate();

            // Check if any rows were affected (user was successfully deleted)
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false; // Return false indicating removal failure
        }
    }


}
=======
package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Remove extends JDialog {
    private JTextField idField;
    private JButton removeButton;
    private JButton closeButton;
    private JTextField textField1;
    private JLabel user_idLabel;
    private JLabel removeLabel;
    private JComboBox<String> typeComboBox;

    public Remove() {
        setTitle("Remove User/Staff");
        setSize(600, 400);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Remove User/Staff");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 15));
        formPanel.setBorder(BorderFactory.createTitledBorder("Remove Record"));

        JLabel typeLabel = new JLabel("Type:");
        typeComboBox = new JComboBox<>(new String[]{"User", "Staff"});
        typeComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField();
        idField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        idField.addActionListener(e -> removeRecord());

        formPanel.add(typeLabel);
        formPanel.add(typeComboBox);
        formPanel.add(idLabel);
        formPanel.add(idField);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        removeButton = new JButton("Remove");
        closeButton = new JButton("Close");

        styleButton(removeButton, new Color(220, 20, 60));
        styleButton(closeButton, new Color(128, 128, 128));

        removeButton.addActionListener(e -> removeRecord());
        closeButton.addActionListener(e -> dispose());

        buttonPanel.add(removeButton);
        buttonPanel.add(closeButton);

        // Add to Main Panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
    }

    private void removeRecord() {
        String id = idField.getText().trim();
        String type = (String) typeComboBox.getSelectedItem();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an ID.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Integer.parseInt(id); // Optional: Check if it's a number
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID must be numeric.",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to permanently remove this " + type.toLowerCase() + " (ID: " + id + ")?",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        String tableName = type.equalsIgnoreCase("User") ? "users" : "staff";
        String idColumn = type.equalsIgnoreCase("User") ? "user_id" : "staff_id";

        String url = "jdbc:mysql://localhost:3306/library";
        String dbUser = "root";
        String dbPassword = "246810";

        String sql = "DELETE FROM " + tableName + " WHERE " + idColumn + " = ?";

        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this,
                        type + " with ID " + id + " removed successfully.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                idField.setText("");
            } else {
                JOptionPane.showMessageDialog(this,
                        type + " with ID " + id + " not found.",
                        "Not Found", JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Database error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Remove().setVisible(true));
    }
}
>>>>>>> 00c9df7 (Describe what you updated)
