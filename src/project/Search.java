<<<<<<< HEAD
package project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Search extends JDialog {
    private JPanel panel1;
    private JTable table1;
    private JButton closeButton;
    private JButton findButton;
    private JTextField textField1;
    private JLabel enterBookNameLabel;
    private JTextField searchField;
    private JButton searchButton;

    public Search(JFrame parentFrame) {
        super(parentFrame, "Search Books", true); // Set the dialog to be modal

        panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS)); // Arrange components vertically

        // Create a table model with 4 columns
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("BOOK_ID");
        model.addColumn("CATEGORY");
        model.addColumn("NAME");
        model.addColumn("AUTHOR");

        table1 = new JTable(model); // Initialize table1 with the created model

        JScrollPane scrollPane = new JScrollPane(table1); // Add table to scroll pane
        panel1.add(scrollPane); // Add scroll pane to panel

        // Add a label and search field for book name
        enterBookNameLabel = new JLabel("Enter book name:"); // Initialize label with text
        searchField = new JTextField(20); // Initialize searchField with 20 columns

        JPanel searchPanel = new JPanel(); // A separate panel for the search field and button
        searchPanel.add(enterBookNameLabel); // Add the label to the search panel
        searchPanel.add(searchField); // Add searchField to the search panel

        searchButton = new JButton("Search"); // Initialize searchButton with text "Search"
        searchPanel.add(searchButton); // Add searchButton to the search panel

        panel1.add(searchPanel); // Add the search panel to the main panel

        closeButton = new JButton("Close"); // Initialize closeButton with text "Close"
        panel1.add(closeButton); // Add closeButton to the main panel

        // Action listener for searchButton
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchField.getText().trim();
                if (!searchText.isEmpty()) {
                    searchInDatabase(searchText, model);
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a book name to search.", "Search Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Action listener for closeButton
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the dialog
            }
        });

        setContentPane(panel1); // Set panel1 as content pane of the dialog
        pack(); // Pack the dialog
        setLocationRelativeTo(parentFrame); // Center the dialog relative to the parent frame
    }

    private void searchInDatabase(String searchText, DefaultTableModel model) {
        // JDBC connection parameters
        String url = "jdbc:mysql://localhost:3306/library";
        String user = "root";
        String password = "root";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement("SELECT BOOK_ID, CATEGORY, NAME, AUTHOR FROM books WHERE NAME LIKE ?")) {

            statement.setString(1, "%" + searchText + "%");
            ResultSet resultSet = statement.executeQuery();

            // Clear existing rows
            model.setRowCount(0);

            // Iterate through the result set and add rows to the table model
            while (resultSet.next()) {
                String bookId = resultSet.getString("BOOK_ID");
                String category = resultSet.getString("CATEGORY");
                String name = resultSet.getString("NAME");
                String author = resultSet.getString("AUTHOR");
                model.addRow(new Object[]{bookId, category, name, author});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
=======
package project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Search extends JDialog {
    private JPanel panel1;
    private JTable table1;
    private JButton closeButton;
    private JButton findButton;
    private JTextField textField1;
    private JLabel enterBookNameLabel;
    private JTextField searchField;
    private JButton searchButton;

    public Search(JFrame parentFrame) {
        super(parentFrame, "Search Books", true); // Set the dialog to be modal

        panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS)); // Arrange components vertically

        // Create a table model with 4 columns
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("BOOK_ID");
        model.addColumn("CATEGORY");
        model.addColumn("NAME");
        model.addColumn("AUTHOR");

        table1 = new JTable(model); // Initialize table1 with the created model

        JScrollPane scrollPane = new JScrollPane(table1); // Add table to scroll pane
        panel1.add(scrollPane); // Add scroll pane to panel

        // Add a label and search field for book name
        enterBookNameLabel = new JLabel("Enter book name:"); // Initialize label with text
        searchField = new JTextField(20); // Initialize searchField with 20 columns

        JPanel searchPanel = new JPanel(); // A separate panel for the search field and button
        searchPanel.add(enterBookNameLabel); // Add the label to the search panel
        searchPanel.add(searchField); // Add searchField to the search panel

        searchButton = new JButton("Search"); // Initialize searchButton with text "Search"
        searchPanel.add(searchButton); // Add searchButton to the search panel

        panel1.add(searchPanel); // Add the search panel to the main panel

        closeButton = new JButton("Close"); // Initialize closeButton with text "Close"
        panel1.add(closeButton); // Add closeButton to the main panel

        // Action listener for searchButton
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchField.getText().trim();
                if (!searchText.isEmpty()) {
                    searchInDatabase(searchText, model);
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a book name to search.", "Search Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Action listener for closeButton
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the dialog
            }
        });

        setContentPane(panel1); // Set panel1 as content pane of the dialog
        pack(); // Pack the dialog
        setLocationRelativeTo(parentFrame); // Center the dialog relative to the parent frame
    }

    private void searchInDatabase(String searchText, DefaultTableModel model) {
        // JDBC connection parameters
        String url = "jdbc:mysql://localhost:3306/library";
        String user = "root";
        String password = "246810";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement("SELECT BOOK_ID, CATEGORY, NAME, AUTHOR FROM books WHERE NAME LIKE ?")) {

            statement.setString(1, "%" + searchText + "%");
            ResultSet resultSet = statement.executeQuery();

            // Clear existing rows
            model.setRowCount(0);

            // Iterate through the result set and add rows to the table model
            while (resultSet.next()) {
                String bookId = resultSet.getString("BOOK_ID");
                String category = resultSet.getString("CATEGORY");
                String name = resultSet.getString("NAME");
                String author = resultSet.getString("AUTHOR");
                model.addRow(new Object[]{bookId, category, name, author});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
>>>>>>> 00c9df7 (Describe what you updated)
