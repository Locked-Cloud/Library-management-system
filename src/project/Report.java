package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Report extends JDialog {
    private JLabel reportAnIssueLabel;
    private JTextField bookIdField;
    private JTextField userIdField;
    private JTextArea descriptionField;
    private JButton sendButton;
    private JButton closeButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextArea textArea1;
    private JPanel panel;

    public Report(JFrame parentFrame) {
        super(parentFrame, "Report an Issue", true);
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Initialize components
        reportAnIssueLabel = new JLabel("Report an Issue");
        bookIdField = new JTextField(20);
        userIdField = new JTextField(20);
        descriptionField = new JTextArea(5, 20);
        sendButton = new JButton("Send");
        closeButton = new JButton("Close");

        // Set up the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(reportAnIssueLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Book ID:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(bookIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("User ID:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(userIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Description:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(new JScrollPane(descriptionField), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(sendButton, gbc);

        gbc.gridy = 5;
        panel.add(closeButton, gbc);

        // Action listeners
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bookId = bookIdField.getText().trim();
                String userId = userIdField.getText().trim();
                String description = descriptionField.getText().trim();

                if (!bookId.isEmpty() && !userId.isEmpty() && !description.isEmpty()) {
                    if (bookExists(bookId) && userExists(userId)) {
                        if (saveToDatabase(bookId, userId, description)) {
                            JOptionPane.showMessageDialog(null, "Report successfully submitted.", "Success", JOptionPane.INFORMATION_MESSAGE);
                            clearFields();
                        } else {
                            JOptionPane.showMessageDialog(null, "Error submitting the report.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        if (!bookExists(bookId)) {
                            JOptionPane.showMessageDialog(null, "Book ID does not exist.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                        }
                        if (!userExists(userId)) {
                            JOptionPane.showMessageDialog(null, "User ID does not exist.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setContentPane(panel);
        pack();
        setLocationRelativeTo(parentFrame);
    }

    private boolean saveToDatabase(String bookId, String userId, String description) {
        String url = "jdbc:mysql://localhost:3306/library";
        String dbUser = "root";
        String password = "root";
        String sql = "INSERT INTO reports (book_id, user_id, description) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, dbUser, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, bookId);
            statement.setString(2, userId);
            statement.setString(3, description);
            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private boolean bookExists(String bookId) {
        String url = "jdbc:mysql://localhost:3306/library";
        String dbUser = "root";
        String password = "root";
        String sql = "SELECT * FROM books WHERE book_id = ?";

        try (Connection connection = DriverManager.getConnection(url, dbUser, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, bookId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private boolean userExists(String userId) {
        String url = "jdbc:mysql://localhost:3306/library";
        String dbUser = "root";
        String password = "root";
        String sql = "SELECT * FROM users WHERE user_id = ?";

        try (Connection connection = DriverManager.getConnection(url, dbUser, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void clearFields() {
        bookIdField.setText("");
        userIdField.setText("");
        descriptionField.setText("");
    }

    /*public static void main(String[] args) {
        JFrame parentFrame = new JFrame();
        parentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Report reportDialog = new Report(parentFrame);
        reportDialog.setVisible(true);
    }*/
}
