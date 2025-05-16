<<<<<<< HEAD
package project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Edit extends JFrame {
    private JTable table1;
    private JButton addButton;
    private JButton removeButton;
    private JLabel editLabel;
    private JButton closeButton;
    private JButton refreshButton;

    public Edit() {
        setTitle("Edit Data");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null);
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable(); // Refresh the table after removing data
            }
        });
    }

    private void initComponents() {
        // Create label
        editLabel = new JLabel("Edit Data");
        editLabel.setFont(new Font("Arial", Font.BOLD, 18));

        // Create table model with column names
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Book ID", "Category", "Name", "Author"}, 0);
        table1 = new JTable(model);
        fetchAndDisplayData(model); // Fetch data from database and populate the table

        JScrollPane scrollPane = new JScrollPane(table1);

        // Create buttons
        addButton = new JButton("Add");
        removeButton = new JButton("Remove");
        refreshButton = new JButton("Refresh");
        closeButton = new JButton("Close");

        // Action listener for add button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Add_Books addBook = new Add_Books();
                addBook.setVisible(true);
                refreshTable(); // Refresh the table after adding data
            }
        });

        // Action listener for remove button
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Remove_Books removeBook = new Remove_Books();
                removeBook.setVisible(true);
                refreshTable(); // Refresh the table after removing data
            }
        });

        // Action listener for close button
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the JFrame
            }
        });

        // Create panel for buttons and add components
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);

        // Create panel and add components
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(editLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Add panel to the frame
        add(panel);
    }

    // Method to fetch data from the database and populate the table
    private void fetchAndDisplayData(DefaultTableModel model) {
        // Clear existing rows
        model.setRowCount(0);

        // Database connection details
        String url = "jdbc:mysql://localhost:3306/library";
        String user = "root";
        String password = "root";

        System.out.println("Connecting to database...");
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to database.");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT BOOK_ID, CATEGORY, NAME, AUTHOR FROM books");

            // Iterate through the result set and add rows to the table model
            while (resultSet.next()) {
                String bookId = resultSet.getString("BOOK_ID");
                String category = resultSet.getString("CATEGORY");
                String name = resultSet.getString("NAME");
                String author = resultSet.getString("AUTHOR");
                model.addRow(new Object[]{bookId, category, name, author});
            }

            System.out.println("Data fetched successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        // Print row count of the table model
        System.out.println("Row count: " + model.getRowCount());
    }

    // Method to refresh the table
    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        fetchAndDisplayData(model);
        model.fireTableDataChanged(); // Notify the table that the data has changed
    }
}
=======
package project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Edit extends JFrame {
    private JTable table1;
    private JButton addButton;
    private JButton removeButton;
    private JLabel editLabel;
    private JButton closeButton;
    private JButton refreshButton;

    public Edit() {
        setTitle("Edit Data");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null);
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable(); // Refresh the table after removing data
            }
        });
    }

    private void initComponents() {
        // Create label
        editLabel = new JLabel("Edit Data");
        editLabel.setFont(new Font("Arial", Font.BOLD, 18));

        // Create table model with column names
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Book ID", "Category", "Name", "Author"}, 0);
        table1 = new JTable(model);
        fetchAndDisplayData(model); // Fetch data from database and populate the table

        JScrollPane scrollPane = new JScrollPane(table1);

        // Create buttons
        addButton = new JButton("Add");
        removeButton = new JButton("Remove");
        refreshButton = new JButton("Refresh");
        closeButton = new JButton("Close");

        // Action listener for add button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Add_Books addBook = new Add_Books();
                addBook.setVisible(true);
                refreshTable(); // Refresh the table after adding data
            }
        });

        // Action listener for remove button
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Remove_Books removeBook = new Remove_Books();
                removeBook.setVisible(true);
                refreshTable(); // Refresh the table after removing data
            }
        });

        // Action listener for close button
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the JFrame
            }
        });

        // Create panel for buttons and add components
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);

        // Create panel and add components
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(editLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Add panel to the frame
        add(panel);
    }

    // Method to fetch data from the database and populate the table
    private void fetchAndDisplayData(DefaultTableModel model) {
        // Clear existing rows
        model.setRowCount(0);

        // Database connection details
        String url = "jdbc:mysql://localhost:3306/library";
        String user = "root";
        String password = "246810";

        System.out.println("Connecting to database...");
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to database.");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT BOOK_ID, CATEGORY, NAME, AUTHOR FROM books");

            // Iterate through the result set and add rows to the table model
            while (resultSet.next()) {
                String bookId = resultSet.getString("BOOK_ID");
                String category = resultSet.getString("CATEGORY");
                String name = resultSet.getString("NAME");
                String author = resultSet.getString("AUTHOR");
                model.addRow(new Object[]{bookId, category, name, author});
            }

            System.out.println("Data fetched successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        // Print row count of the table model
        System.out.println("Row count: " + model.getRowCount());
    }

    // Method to refresh the table
    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        fetchAndDisplayData(model);
        model.fireTableDataChanged(); // Notify the table that the data has changed
    }
}
>>>>>>> 00c9df7 (Describe what you updated)
