package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Remove_Books extends JFrame {
    private JTextField textField1;
    private JButton removeButton;
    private JButton closeButton;
    private JLabel BOOK_IDLabel;

    public Remove_Books() {
        setTitle("Remove Books");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        // Create label and text field
        JLabel bookIdLabel = new JLabel("Book ID:");
        textField1 = new JTextField(20);

        // Create buttons
        removeButton = new JButton("Remove");
        closeButton = new JButton("Close");

        // Action listener for remove button
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeBook();
            }
        });

        // Action listener for close button
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the JFrame
            }
        });

        // Create panel and add components
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.add(bookIdLabel);
        panel.add(textField1);
        panel.add(removeButton);
        panel.add(closeButton);

        // Add panel to the frame
        add(panel);
    }

    private void removeBook() {
        // Get book ID from text field
        String bookID = textField1.getText();

        // Database connection details
        String url = "jdbc:mysql://localhost:3306/library";
        String user = "root";
        String password = "root";

        // SQL query to remove a book
        String query = "DELETE FROM books WHERE BOOK_ID = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Set book ID parameter in the prepared statement
            statement.setString(1, bookID);

            // Execute the query
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Book removed successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "No book found with ID: " + bookID, "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Clear text field after removing the book
            textField1.setText("");

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}

