<<<<<<< HEAD
package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class BorrowBook extends JFrame {
    // Database credentials and URL
    private static final String DB_URL = "jdbc:mysql://localhost:3306/LIBRARY";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    // Components for the GUI
    private JButton searchButton, borrowBookButton, calculateButton, borrowButton;
    private JTextField bookIdField, userIdField, takenDateField, returnDateField;
    private JLabel titleLabel, billLabel;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    private double lastCalculatedBill = 0.0; // Store the last calculated bill

    public BorrowBook() {
        super("Borrow Book System");
        initializeUI();
        refreshData(); // Refresh data every time the app is started
    }

    private void initializeUI() {
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setupPanels();
    }

    private void setupPanels() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        JPanel mainPanelContent = createMainPanel();
        JPanel borrowPanelContent = createBorrowPanel();
        mainPanel.add(mainPanelContent, "Main");
        mainPanel.add(borrowPanelContent, "Borrow");
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        titleLabel = new JLabel("Borrow System", SwingConstants.CENTER);
        searchButton = new JButton("Search");
        borrowBookButton = new JButton("Borrow Book");
        buttonPanel.add(searchButton);
        buttonPanel.add(borrowBookButton);
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);

        borrowBookButton.addActionListener(e -> {
            setTakenDateAutomatically();
            cardLayout.show(mainPanel, "Borrow");
        });

        searchButton.addActionListener(e -> {
            Search run = new Search(null);
            run.setVisible(true);
        });

        return panel;
    }

    private JPanel createBorrowPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel fieldsPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        JLabel bookIdLabel = new JLabel("Enter BOOK_ID:");
        bookIdField = new JTextField(10);
        JLabel userIdLabel = new JLabel("Enter USER_ID:");
        userIdField = new JTextField(10);
        JLabel takenDateLabel = new JLabel("Taken Date (yyyy-MM-dd):");
        takenDateField = new JTextField(10);
        takenDateField.setEditable(false);
        JLabel returnDateLabel = new JLabel("Return Date (yyyy-MM-dd):");
        returnDateField = new JTextField(10);
        calculateButton = new JButton("Calculate Bill");
        borrowButton = new JButton("Borrow Book");
        billLabel = new JLabel("Bill: £0.00");
        fieldsPanel.add(bookIdLabel);
        fieldsPanel.add(bookIdField);
        fieldsPanel.add(userIdLabel);
        fieldsPanel.add(userIdField);
        fieldsPanel.add(takenDateLabel);
        fieldsPanel.add(takenDateField);
        fieldsPanel.add(returnDateLabel);
        fieldsPanel.add(returnDateField);
        fieldsPanel.add(calculateButton);
        fieldsPanel.add(borrowButton);
        panel.add(fieldsPanel, BorderLayout.CENTER);
        panel.add(billLabel, BorderLayout.SOUTH);

        calculateButton.addActionListener(e -> calculateBill());
        borrowButton.addActionListener(e -> borrowBook());

        return panel;
    }

    private void setTakenDateAutomatically() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        takenDateField.setText(formatter.format(LocalDate.now()));
    }

    private void calculateBill() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate takenDate = LocalDate.parse(takenDateField.getText(), formatter);
            LocalDate returnDate = LocalDate.parse(returnDateField.getText(), formatter);
            LocalDate currentDate = LocalDate.now();
            long plannedDays = ChronoUnit.DAYS.between(takenDate, returnDate);
            lastCalculatedBill = plannedDays * 1.0;  // Assume £1 per day

            if (returnDate.isBefore(currentDate)) {
                long lateDays = ChronoUnit.DAYS.between(returnDate, currentDate);
                double lateFee = lateDays * 0.10;  // Assume £0.10 per day late fee
                lastCalculatedBill += lateFee;
            }

            billLabel.setText(String.format("Bill: £%.2f", lastCalculatedBill));
        } catch (Exception e) {
            billLabel.setText("Invalid date format!");
            e.printStackTrace();
        }
    }

    private void borrowBook() {
        String bookIdText = bookIdField.getText();
        String userIdText = userIdField.getText();
        String takenDateText = takenDateField.getText();
        String returnDateText = returnDateField.getText();

        if (bookIdText.isEmpty() || userIdText.isEmpty() || takenDateText.isEmpty() || returnDateText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isBookAvailable(bookIdText)) {
            JOptionPane.showMessageDialog(this, "This book is currently taken. Please select another book.", "Book Unavailable", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!validateBookAndUser(bookIdText, userIdText)) return;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO borrow_info (BOOK_ID, USER_ID, Taken_date, Return_date, bill) VALUES (?, ?, ?, ?, ?)")) {

            pstmt.setString(1, bookIdText);
            pstmt.setString(2, userIdText);
            pstmt.setString(3, takenDateText);
            pstmt.setString(4, returnDateText);
            pstmt.setDouble(5, lastCalculatedBill);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Book borrowed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error occurred while borrowing the book: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isBookAvailable(String bookId) {
        String query = "SELECT COUNT(*) FROM borrow_info WHERE BOOK_ID = ? AND Return_date >= CURDATE()";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, bookId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 0;  // Return true if there are no active borrow records
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error checking book availability: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    private void refreshData() {
        updateOverdueBills();  // Update overdue bills on startup
        System.out.println("Data refreshed and bills updated for overdue books.");
    }

    private void updateOverdueBills() {
        String query = "SELECT BOOK_ID, USER_ID, Taken_date, Return_date, bill FROM borrow_info WHERE Return_date < CURDATE()";
        String updateQuery = "UPDATE borrow_info SET bill = ? WHERE BOOK_ID = ? AND USER_ID = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String bookId = rs.getString("BOOK_ID");
                String userId = rs.getString("USER_ID");
                LocalDate takenDate = rs.getDate("Taken_date").toLocalDate();
                LocalDate returnDate = rs.getDate("Return_date").toLocalDate();
                double oldBill = rs.getDouble("bill");

                LocalDate currentDate = LocalDate.now();
                long overdueDays = ChronoUnit.DAYS.between(returnDate, currentDate);
                double newBill = oldBill + (overdueDays * 0.10); // Assuming £0.10 per day late fee

                if (newBill != oldBill) {
                    try (PreparedStatement updatePstmt = conn.prepareStatement(updateQuery)) {
                        updatePstmt.setDouble(1, newBill);
                        updatePstmt.setString(2, bookId);
                        updatePstmt.setString(3, userId);
                        updatePstmt.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating overdue bills: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validateBookAndUser(String bookId, String userId) {
        // Validate book ID and user ID before borrowing
        boolean bookIdValid = isBookIdValid(bookId);
        boolean userIdValid = isUserIdValid(userId);
        if (!bookIdValid) {
            JOptionPane.showMessageDialog(this, "Invalid BOOK_ID. Book not found.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!userIdValid) {
            JOptionPane.showMessageDialog(this, "Invalid USER_ID. User not found.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean isBookIdValid(String bookId) {
        String query = "SELECT COUNT(*) FROM books WHERE BOOK_ID = ?";
        return validateId(query, bookId);
    }

    private boolean isUserIdValid(String userId) {
        String query = "SELECT COUNT(*) FROM users WHERE USER_ID = ?";
        return validateId(query, userId);
    }

    private boolean validateId(String query, String id) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

}
=======
package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class BorrowBook extends JFrame {
    // Database credentials and URL
    private static final String DB_URL = "jdbc:mysql://localhost:3306/LIBRARY";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "246810";

    // Components for the GUI
    private JButton searchButton, borrowBookButton, calculateButton, borrowButton;
    private JTextField bookIdField, userIdField, takenDateField, returnDateField;
    private JLabel titleLabel, billLabel;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    private double lastCalculatedBill = 0.0; // Store the last calculated bill

    public BorrowBook() {
        super("Borrow Book System");
        initializeUI();
        refreshData(); // Refresh data every time the app is started
    }

    private void initializeUI() {
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setupPanels();
    }

    private void setupPanels() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        JPanel mainPanelContent = createMainPanel();
        JPanel borrowPanelContent = createBorrowPanel();
        mainPanel.add(mainPanelContent, "Main");
        mainPanel.add(borrowPanelContent, "Borrow");
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        titleLabel = new JLabel("Borrow System", SwingConstants.CENTER);
        searchButton = new JButton("Search");
        borrowBookButton = new JButton("Borrow Book");
        buttonPanel.add(searchButton);
        buttonPanel.add(borrowBookButton);
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);

        borrowBookButton.addActionListener(e -> {
            setTakenDateAutomatically();
            cardLayout.show(mainPanel, "Borrow");
        });

        searchButton.addActionListener(e -> {
            Search run = new Search(null);
            run.setVisible(true);
        });

        return panel;
    }

    private JPanel createBorrowPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel fieldsPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        JLabel bookIdLabel = new JLabel("Enter BOOK_ID:");
        bookIdField = new JTextField(10);
        JLabel userIdLabel = new JLabel("Enter USER_ID:");
        userIdField = new JTextField(10);
        JLabel takenDateLabel = new JLabel("Taken Date (yyyy-MM-dd):");
        takenDateField = new JTextField(10);
        takenDateField.setEditable(false);
        JLabel returnDateLabel = new JLabel("Return Date (yyyy-MM-dd):");
        returnDateField = new JTextField(10);
        calculateButton = new JButton("Calculate Bill");
        borrowButton = new JButton("Borrow Book");
        billLabel = new JLabel("Bill: £0.00");
        fieldsPanel.add(bookIdLabel);
        fieldsPanel.add(bookIdField);
        fieldsPanel.add(userIdLabel);
        fieldsPanel.add(userIdField);
        fieldsPanel.add(takenDateLabel);
        fieldsPanel.add(takenDateField);
        fieldsPanel.add(returnDateLabel);
        fieldsPanel.add(returnDateField);
        fieldsPanel.add(calculateButton);
        fieldsPanel.add(borrowButton);
        panel.add(fieldsPanel, BorderLayout.CENTER);
        panel.add(billLabel, BorderLayout.SOUTH);

        calculateButton.addActionListener(e -> calculateBill());
        borrowButton.addActionListener(e -> borrowBook());

        return panel;
    }

    private void setTakenDateAutomatically() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        takenDateField.setText(formatter.format(LocalDate.now()));
    }

    private void calculateBill() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate takenDate = LocalDate.parse(takenDateField.getText(), formatter);
            LocalDate returnDate = LocalDate.parse(returnDateField.getText(), formatter);
            LocalDate currentDate = LocalDate.now();
            long plannedDays = ChronoUnit.DAYS.between(takenDate, returnDate);
            lastCalculatedBill = plannedDays * 1.0;  // Assume £1 per day

            if (returnDate.isBefore(currentDate)) {
                long lateDays = ChronoUnit.DAYS.between(returnDate, currentDate);
                double lateFee = lateDays * 0.10;  // Assume £0.10 per day late fee
                lastCalculatedBill += lateFee;
            }

            billLabel.setText(String.format("Bill: £%.2f", lastCalculatedBill));
        } catch (Exception e) {
            billLabel.setText("Invalid date format!");
            e.printStackTrace();
        }
    }

    private void borrowBook() {
        String bookIdText = bookIdField.getText();
        String userIdText = userIdField.getText();
        String takenDateText = takenDateField.getText();
        String returnDateText = returnDateField.getText();

        if (bookIdText.isEmpty() || userIdText.isEmpty() || takenDateText.isEmpty() || returnDateText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isBookAvailable(bookIdText)) {
            JOptionPane.showMessageDialog(this, "This book is currently taken. Please select another book.", "Book Unavailable", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!validateBookAndUser(bookIdText, userIdText)) return;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO borrow_info (BOOK_ID, USER_ID, Taken_date, Return_date, bill) VALUES (?, ?, ?, ?, ?)")) {

            pstmt.setString(1, bookIdText);
            pstmt.setString(2, userIdText);
            pstmt.setString(3, takenDateText);
            pstmt.setString(4, returnDateText);
            pstmt.setDouble(5, lastCalculatedBill);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Book borrowed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error occurred while borrowing the book: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isBookAvailable(String bookId) {
        String query = "SELECT COUNT(*) FROM borrow_info WHERE BOOK_ID = ? AND Return_date >= CURDATE()";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, bookId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 0;  // Return true if there are no active borrow records
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error checking book availability: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    private void refreshData() {
        updateOverdueBills();  // Update overdue bills on startup
        System.out.println("Data refreshed and bills updated for overdue books.");
    }

    private void updateOverdueBills() {
        String query = "SELECT BOOK_ID, USER_ID, Taken_date, Return_date, bill FROM borrow_info WHERE Return_date < CURDATE()";
        String updateQuery = "UPDATE borrow_info SET bill = ? WHERE BOOK_ID = ? AND USER_ID = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String bookId = rs.getString("BOOK_ID");
                String userId = rs.getString("USER_ID");
                LocalDate takenDate = rs.getDate("Taken_date").toLocalDate();
                LocalDate returnDate = rs.getDate("Return_date").toLocalDate();
                double oldBill = rs.getDouble("bill");

                LocalDate currentDate = LocalDate.now();
                long overdueDays = ChronoUnit.DAYS.between(returnDate, currentDate);
                double newBill = oldBill + (overdueDays * 0.10); // Assuming £0.10 per day late fee

                if (newBill != oldBill) {
                    try (PreparedStatement updatePstmt = conn.prepareStatement(updateQuery)) {
                        updatePstmt.setDouble(1, newBill);
                        updatePstmt.setString(2, bookId);
                        updatePstmt.setString(3, userId);
                        updatePstmt.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating overdue bills: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validateBookAndUser(String bookId, String userId) {
        // Validate book ID and user ID before borrowing
        boolean bookIdValid = isBookIdValid(bookId);
        boolean userIdValid = isUserIdValid(userId);
        if (!bookIdValid) {
            JOptionPane.showMessageDialog(this, "Invalid BOOK_ID. Book not found.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!userIdValid) {
            JOptionPane.showMessageDialog(this, "Invalid USER_ID. User not found.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean isBookIdValid(String bookId) {
        String query = "SELECT COUNT(*) FROM books WHERE BOOK_ID = ?";
        return validateId(query, bookId);
    }

    private boolean isUserIdValid(String userId) {
        String query = "SELECT COUNT(*) FROM users WHERE USER_ID = ?";
        return validateId(query, userId);
    }

    private boolean validateId(String query, String id) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

}
>>>>>>> 00c9df7 (Describe what you updated)
