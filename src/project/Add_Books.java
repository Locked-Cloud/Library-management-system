<<<<<<< HEAD
package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Add_Books extends JFrame {
    private JTextField bookIdField;
    private JTextField categoryField;
    private JTextField nameField;
    private JTextField authorField;
    private JButton closeButton;
    private JButton uploadButton;
    private JLabel uploadFilesLabel;
    private JButton ADDButton;
    private JButton removeButton;
    private JTextField textField5;
    private JTextField textField4;
    private JTextField textField3;
    private JTextField textField2;
    private JTextField textField1;
    private JButton addButton;

    public Add_Books() {
        setTitle("Add Books");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        setNextBookId();
        pack();
        setLocationRelativeTo(null);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void initComponents() {
        JLabel bookIdLabel = new JLabel("Book ID:");
        JLabel categoryLabel = new JLabel("Category:");
        JLabel nameLabel = new JLabel("Name:");
        JLabel authorLabel = new JLabel("Author:");

        bookIdField = new JTextField(20);
        categoryField = new JTextField(20);
        nameField = new JTextField(20);
        authorField = new JTextField(20);

        addButton = new JButton("Add");
        closeButton = new JButton("Close");

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 5);
        panel.add(bookIdLabel, c);
        c.gridy++;
        panel.add(categoryLabel, c);
        c.gridy++;
        panel.add(nameLabel, c);
        c.gridy++;
        panel.add(authorLabel, c);

        c.gridx = 1;
        c.gridy = 0;
        panel.add(bookIdField, c);
        c.gridy++;
        panel.add(categoryField, c);
        c.gridy++;
        panel.add(nameField, c);
        c.gridy++;
        panel.add(authorField, c);

        c.gridy++;
        c.anchor = GridBagConstraints.CENTER;
        panel.add(addButton, c);
        c.gridy++;
        panel.add(closeButton, c);

        add(panel);
    }

    private void setNextBookId() {
        int nextId = fetchNextBookId();
        bookIdField.setText(String.valueOf(nextId));
        bookIdField.setEditable(false);  // Optionally make it non-editable
    }

    private int fetchNextBookId() {
        int nextId = 1;  // Default starting ID
        String url = "jdbc:mysql://localhost:3306/library";
        String user = "root";
        String password = "root";
        String query = "SELECT MAX(BOOK_ID) AS max_id FROM books";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                nextId = resultSet.getInt("max_id") + 1;  // Increment to get the next ID
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return nextId;
    }
    private void clearTextFields() {
        bookIdField.setText("");
        categoryField.setText("");
        nameField.setText("");
        authorField.setText("");
        // Add any other text fields you want to clear here
    }


    private void addBook() {
        String bookID = bookIdField.getText();
        String category = categoryField.getText();
        String name = nameField.getText();
        String author = authorField.getText();

        if (bookID.isEmpty() || category.isEmpty() || name.isEmpty() || author.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        new UploadPDFApp(Integer.parseInt(bookID), name, category, author).setVisible(true);
        clearTextFields();
        dispose();
    }
=======
package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Add_Books extends JFrame {
    private JTextField bookIdField;
    private JTextField categoryField;
    private JTextField nameField;
    private JTextField authorField;
    private JButton closeButton;
    private JButton uploadButton;
    private JLabel uploadFilesLabel;
    private JButton ADDButton;
    private JButton removeButton;
    private JTextField textField5;
    private JTextField textField4;
    private JTextField textField3;
    private JTextField textField2;
    private JTextField textField1;
    private JButton addButton;

    public Add_Books() {
        setTitle("Add Books");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        setNextBookId();
        pack();
        setLocationRelativeTo(null);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void initComponents() {
        JLabel bookIdLabel = new JLabel("Book ID:");
        JLabel categoryLabel = new JLabel("Category:");
        JLabel nameLabel = new JLabel("Name:");
        JLabel authorLabel = new JLabel("Author:");

        bookIdField = new JTextField(20);
        categoryField = new JTextField(20);
        nameField = new JTextField(20);
        authorField = new JTextField(20);

        addButton = new JButton("Add");
        closeButton = new JButton("Close");

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 5);
        panel.add(bookIdLabel, c);
        c.gridy++;
        panel.add(categoryLabel, c);
        c.gridy++;
        panel.add(nameLabel, c);
        c.gridy++;
        panel.add(authorLabel, c);

        c.gridx = 1;
        c.gridy = 0;
        panel.add(bookIdField, c);
        c.gridy++;
        panel.add(categoryField, c);
        c.gridy++;
        panel.add(nameField, c);
        c.gridy++;
        panel.add(authorField, c);

        c.gridy++;
        c.anchor = GridBagConstraints.CENTER;
        panel.add(addButton, c);
        c.gridy++;
        panel.add(closeButton, c);

        add(panel);
    }

    private void setNextBookId() {
        int nextId = fetchNextBookId();
        bookIdField.setText(String.valueOf(nextId));
        bookIdField.setEditable(false);  // Optionally make it non-editable
    }

    private int fetchNextBookId() {
        int nextId = 1;  // Default starting ID
        String url = "jdbc:mysql://localhost:3306/library";
        String user = "root";
        String password = "246810";
        String query = "SELECT MAX(BOOK_ID) AS max_id FROM books";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                nextId = resultSet.getInt("max_id") + 1;  // Increment to get the next ID
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return nextId;
    }
    private void clearTextFields() {
        bookIdField.setText("");
        categoryField.setText("");
        nameField.setText("");
        authorField.setText("");
        // Add any other text fields you want to clear here
    }


    private void addBook() {
        String bookID = bookIdField.getText();
        String category = categoryField.getText();
        String name = nameField.getText();
        String author = authorField.getText();

        if (bookID.isEmpty() || category.isEmpty() || name.isEmpty() || author.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        new UploadPDFApp(Integer.parseInt(bookID), name, category, author).setVisible(true);
        clearTextFields();
        dispose();
    }
>>>>>>> 00c9df7 (Describe what you updated)
}