<<<<<<< HEAD
package project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Show_All extends JDialog {
    private JPanel panel1;
    private JLabel showBooksLabel;
    private JTable table1;
    private JButton closeButton;

    public Show_All() {
        JFrame frame = new JFrame("Show All Books");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(panel1);

        // Create table model with 4 columns
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("BOOK_ID");
        model.addColumn("CATEGORY");
        model.addColumn("NAME");
        model.addColumn("AUTHOR");

        // JDBC connection parameters
        String url = "jdbc:mysql://localhost:3306/library";
        String user = "root";
        String password = "root";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT BOOK_ID, CATEGORY, NAME, AUTHOR FROM books")) {

            // Iterate through the result set and add rows to the table model
            while (resultSet.next()) {
                String bookId = resultSet.getString("BOOK_ID");
                String category = resultSet.getString("CATEGORY");
                String name = resultSet.getString("NAME");
                String author = resultSet.getString("AUTHOR");
                model.addRow(new Object[]{bookId, category, name, author});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        // Set the table model to the JTable
        table1.setModel(model);

        frame.pack();
        frame.setVisible(true);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.getWindowAncestor(panel1).dispose();
            }
        });

    }
}
=======
package project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Show_All extends JDialog {
    private JPanel panel1;
    private JLabel showBooksLabel;
    private JTable table1;
    private JButton closeButton;

    public Show_All() {
        JFrame frame = new JFrame("Show All Books");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(panel1);

        // Create table model with 4 columns
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("BOOK_ID");
        model.addColumn("CATEGORY");
        model.addColumn("NAME");
        model.addColumn("AUTHOR");

        // JDBC connection parameters
        String url = "jdbc:mysql://localhost:3306/library";
        String user = "root";
        String password = "246810";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT BOOK_ID, CATEGORY, NAME, AUTHOR FROM books")) {

            // Iterate through the result set and add rows to the table model
            while (resultSet.next()) {
                String bookId = resultSet.getString("BOOK_ID");
                String category = resultSet.getString("CATEGORY");
                String name = resultSet.getString("NAME");
                String author = resultSet.getString("AUTHOR");
                model.addRow(new Object[]{bookId, category, name, author});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        // Set the table model to the JTable
        table1.setModel(model);

        frame.pack();
        frame.setVisible(true);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.getWindowAncestor(panel1).dispose();
            }
        });

    }
}
>>>>>>> 00c9df7 (Describe what you updated)
