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
