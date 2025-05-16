<<<<<<< HEAD
package project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class history_books extends JDialog {
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JTable borrowInfoTable;
    private JButton closeButton, refreshButton;

    public history_books(JFrame parent) {
        super(parent, "Borrow Information History", true);

        // Set up main panel layout
        mainPanel = new JPanel(new BorderLayout());

        // Title label
        titleLabel = new JLabel("Borrow Information", SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Table for borrow_info data
        borrowInfoTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(borrowInfoTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        closeButton = new JButton("Close");
        refreshButton = new JButton("Refresh");
        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Set up the dialog
        setContentPane(mainPanel);
        setSize(600, 400);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Table model for the borrow_info data
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("BORROW_ID");
        model.addColumn("BOOK_ID");
        model.addColumn("USER_ID");
        model.addColumn("Taken_date");
        model.addColumn("Return_date");
        model.addColumn("Bill");
        borrowInfoTable.setModel(model);
        refreshTableData();  // Load data initially

        // Handle the close button action
        closeButton.addActionListener(e -> dispose());

        // Handle the refresh button action
        refreshButton.addActionListener(e -> updateOverdueBills());
    }

    private void refreshTableData() {
        DefaultTableModel model = (DefaultTableModel) borrowInfoTable.getModel();
        model.setRowCount(0); // Clear existing data

        String url = "jdbc:mysql://localhost:3306/LIBRARY";
        String user = "root";
        String password = "root";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT BORROW_ID, BOOK_ID, USER_ID, Taken_date, Return_date, bill FROM borrow_info")) {

            while (resultSet.next()) {
                model.addRow(new Object[]{
                        resultSet.getInt("BORROW_ID"),
                        resultSet.getInt("BOOK_ID"),
                        resultSet.getString("USER_ID"),
                        resultSet.getDate("Taken_date"),
                        resultSet.getDate("Return_date"),
                        resultSet.getDouble("bill")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateOverdueBills() {
        String query = "SELECT BOOK_ID, USER_ID, Taken_date, Return_date, bill FROM borrow_info WHERE Return_date < CURDATE()";
        String updateQuery = "UPDATE borrow_info SET bill = ? WHERE BOOK_ID = ? AND USER_ID = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LIBRARY", "root", "root");
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                LocalDate returnDate = rs.getDate("Return_date").toLocalDate();
                double oldBill = rs.getDouble("bill");

                LocalDate currentDate = LocalDate.now();
                long overdueDays = ChronoUnit.DAYS.between(returnDate, currentDate);
                double newBill = oldBill + (overdueDays * 0.10); // Assuming £0.10 per day late fee

                if (newBill != oldBill) {
                    try (PreparedStatement updatePstmt = conn.prepareStatement(updateQuery)) {
                        updatePstmt.setDouble(1, newBill);
                        updatePstmt.setString(2, rs.getString("BOOK_ID"));
                        updatePstmt.setString(3, rs.getString("USER_ID"));
                        updatePstmt.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating overdue bills: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        refreshTableData();  // Refresh table data after updating bills
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
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class history_books extends JDialog {
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JTable borrowInfoTable;
    private JButton closeButton, refreshButton;

    public history_books(JFrame parent) {
        super(parent, "Borrow Information History", true);

        // Set up main panel layout
        mainPanel = new JPanel(new BorderLayout());

        // Title label
        titleLabel = new JLabel("Borrow Information", SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Table for borrow_info data
        borrowInfoTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(borrowInfoTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        closeButton = new JButton("Close");
        refreshButton = new JButton("Refresh");
        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Set up the dialog
        setContentPane(mainPanel);
        setSize(600, 400);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Table model for the borrow_info data
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("BORROW_ID");
        model.addColumn("BOOK_ID");
        model.addColumn("USER_ID");
        model.addColumn("Taken_date");
        model.addColumn("Return_date");
        model.addColumn("Bill");
        borrowInfoTable.setModel(model);
        refreshTableData();  // Load data initially

        // Handle the close button action
        closeButton.addActionListener(e -> dispose());

        // Handle the refresh button action
        refreshButton.addActionListener(e -> updateOverdueBills());
    }

    private void refreshTableData() {
        DefaultTableModel model = (DefaultTableModel) borrowInfoTable.getModel();
        model.setRowCount(0); // Clear existing data

        String url = "jdbc:mysql://localhost:3306/LIBRARY";
        String user = "root";
        String password = "246810";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT BORROW_ID, BOOK_ID, USER_ID, Taken_date, Return_date, bill FROM borrow_info")) {

            while (resultSet.next()) {
                model.addRow(new Object[]{
                        resultSet.getInt("BORROW_ID"),
                        resultSet.getInt("BOOK_ID"),
                        resultSet.getString("USER_ID"),
                        resultSet.getDate("Taken_date"),
                        resultSet.getDate("Return_date"),
                        resultSet.getDouble("bill")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateOverdueBills() {
        String query = "SELECT BOOK_ID, USER_ID, Taken_date, Return_date, bill FROM borrow_info WHERE Return_date < CURDATE()";
        String updateQuery = "UPDATE borrow_info SET bill = ? WHERE BOOK_ID = ? AND USER_ID = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LIBRARY", "root", "246810");
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                LocalDate returnDate = rs.getDate("Return_date").toLocalDate();
                double oldBill = rs.getDouble("bill");

                LocalDate currentDate = LocalDate.now();
                long overdueDays = ChronoUnit.DAYS.between(returnDate, currentDate);
                double newBill = oldBill + (overdueDays * 0.10); // Assuming £0.10 per day late fee

                if (newBill != oldBill) {
                    try (PreparedStatement updatePstmt = conn.prepareStatement(updateQuery)) {
                        updatePstmt.setDouble(1, newBill);
                        updatePstmt.setString(2, rs.getString("BOOK_ID"));
                        updatePstmt.setString(3, rs.getString("USER_ID"));
                        updatePstmt.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating overdue bills: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        refreshTableData();  // Refresh table data after updating bills
    }

}
>>>>>>> 00c9df7 (Describe what you updated)
