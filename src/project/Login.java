package project;

import com.formdev.flatlaf.FlatDarkLaf; // Import FlatDarkLaf

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends JDialog {
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton loginButton;
    private JPanel loginPanel;
    private JButton signupButton;
    private JLabel loginLabel;

    public Login(JFrame parent) {
        super(parent);
        setTitle("Login");
        // Set FlatDarkLaf Look and Feel
        FlatDarkLaf.setup(); // Or other themes like FlatLightLaf, FlatIntelliJLaf, FlatDarculaLaf

        // Initialize Components
        loginPanel = new JPanel();
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        textField1 = new JTextField(20);
        passwordField1 = new JPasswordField(20);
        loginButton = createModernButton("Login");
        signupButton = createModernButton("Sign Up");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Sign-Up Button Action
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Signup signup = new Signup();
                signup.setVisible(true);
            }
        });

        // Login Button Action
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = textField1.getText();
                String password = String.valueOf(passwordField1.getPassword());

                if (authenticateAdmin(userId, password)) {
                    dispose();
                    Dashboardadmin();
                } else if (authenticateUser(userId, password)) {
                    dispose();
                    Dashboarduser();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Create GUI Layout
        loginPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        loginPanel.add(new JLabel("User ID:"), gbc);
        gbc.gridx = 1;
        loginPanel.add(textField1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        loginPanel.add(passwordField1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        loginPanel.add(loginButton, gbc);
        gbc.gridy = 3;
        loginPanel.add(signupButton, gbc);

        // Set Content Pane and Modern Style
        setContentPane(loginPanel);
        setSize(600, 400);
        setMinimumSize(new Dimension(600, 400));
        setModal(true);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    // Create Modern Styled Button
    private JButton createModernButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(0, 120, 215));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private boolean authenticateAdmin(String userId, String password) {
        if (userId.startsWith("A")) {
            final String DB_URL = "jdbc:mysql://localhost:3306/LIBRARY";
            final String USERNAME = "root";
            final String PASSWORD = "root";
            try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
                String sql = "SELECT * FROM ADMIN WHERE USER_ID = ? AND PASSWORD = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, userId);
                pstmt.setString(2, password);
                ResultSet resultSet = pstmt.executeQuery();

                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(rootPane, "Welcome " + userId + "!", "Welcome", JOptionPane.INFORMATION_MESSAGE);
                    return true;
                } else {
                    System.out.println("No matching user found for the provided credentials.");
                }
            } catch (SQLException e) {
                System.err.println("SQL Exception: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean authenticateUser(String userId, String password) {
        final String DB_URL = "jdbc:mysql://localhost:3306/LIBRARY";
        final String USERNAME = "root";
        final String PASSWORD = "root";
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            String sql = "SELECT * FROM USERS WHERE USER_ID = ? AND PASSWORD = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            pstmt.setString(2, password);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                JOptionPane.showMessageDialog(rootPane, "Welcome " + userId + "!", "Welcome", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                System.out.println("No matching user found for the provided credentials.");
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    private void Dashboardadmin() {
        Dashboardadmin dashboard1 = new Dashboardadmin();
        dashboard1.setVisible(true);
    }

    private void Dashboarduser() {
        Dashboarduser dashboard = new Dashboarduser();
        dashboard.setVisible(true);
    }
}
