package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Dashboardstaff extends JFrame {
    private JTextField nameField, contactField, memberIdField;
    private JButton registerButton, updateButton;

    public Dashboardstaff() {
        setTitle("Staff Dashboard - Library System");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Library Staff Panel", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        formPanel.add(new JLabel("Member ID:"));
        memberIdField = new JTextField();
        formPanel.add(memberIdField);

        formPanel.add(new JLabel("Full Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Contact Info:"));
        contactField = new JTextField();
        formPanel.add(contactField);

        registerButton = new JButton("Register New Member");
        updateButton = new JButton("Update Member Info");

        formPanel.add(registerButton);
        formPanel.add(updateButton);

        add(formPanel, BorderLayout.CENTER);

        // Action Listeners
        registerButton.addActionListener(e -> registerMember());
        updateButton.addActionListener(e -> updateMember());
    }

    private void registerMember() {
        String id = memberIdField.getText().trim();
        String name = nameField.getText().trim();
        String contact = contactField.getText().trim();

        if (id.isEmpty() || name.isEmpty() || contact.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields");
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LIBRARY", "root", "246810")) {
            String sql = "INSERT INTO USERS (USER_ID, FULL_NAME, CONTACT_INFO) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            stmt.setString(2, name);
            stmt.setString(3, contact);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Member registered successfully.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void updateMember() {
        String id = memberIdField.getText().trim();
        String name = nameField.getText().trim();
        String contact = contactField.getText().trim();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter Member ID to update.");
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LIBRARY", "root", "246810")) {
            String sql = "UPDATE USERS SET FULL_NAME = ?, CONTACT_INFO = ? WHERE USER_ID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, contact);
            stmt.setString(3, id);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Member info updated.");
            } else {
                JOptionPane.showMessageDialog(this, "Member ID not found.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
