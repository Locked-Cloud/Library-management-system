<<<<<<< HEAD
package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Return extends JFrame {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/library";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    private JTextField bookIdField;
    private JTextField userIdField;
    private JLabel userIdLabel;
    private JLabel bookIdLabel;
    private JButton returnButton;
    private JButton closeButton;
    private JLabel BOOK_IDLabel;
    private JLabel USER_IDLabel;
    private JTextField textField1;
    private JTextField textField2;
    private JButton payButton;
    private JPanel mainPanel;

    public Return() {
        super("Return Book");
        createUI();
        setActionListeners();
    }

    private void createUI() {
        userIdLabel = new JLabel("USER_ID:");
        bookIdLabel = new JLabel("BOOK_ID:");
        bookIdField = new JTextField(10);
        userIdField = new JTextField(10);
        returnButton = new JButton("Return Book");
        closeButton = new JButton("Close");
        payButton = new JButton("Pay Bill");

        mainPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        mainPanel.add(bookIdLabel);
        mainPanel.add(bookIdField);
        mainPanel.add(userIdLabel);
        mainPanel.add(userIdField);
        mainPanel.add(returnButton);
        mainPanel.add(payButton);
        mainPanel.add(closeButton);

        add(mainPanel, BorderLayout.CENTER);
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void setActionListeners() {
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnBook();
            }
        });

        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                payBill();
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void returnBook() {
        String bookIdText = bookIdField.getText();
        String userIdText = userIdField.getText();

        if (bookIdText.isEmpty() || userIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in both USER_ID and BOOK_ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int bookId;
        try {
            bookId = Integer.parseInt(bookIdText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid BOOK_ID format.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isBookBorrowed(bookId, userIdText)) {
            JOptionPane.showMessageDialog(this, "This book is not currently borrowed by this user.", "Book Not Borrowed", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!isBillCleared(bookId, userIdText)) {
            JOptionPane.showMessageDialog(this, "Outstanding bill must be cleared before returning the book.", "Bill Outstanding", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM borrow_info WHERE BOOK_ID = ? AND USER_ID = ?")) {

            pstmt.setInt(1, bookId);
            pstmt.setString(2, userIdText);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Book returned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error returning the book. Please try again.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error returning the book: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isBookBorrowed(int bookId, String userId) {
        String query = "SELECT COUNT(*) FROM borrow_info WHERE BOOK_ID = ? AND USER_ID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, bookId);
            pstmt.setString(2, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isBillCleared(int bookId, String userId) {
        String query = "SELECT bill FROM borrow_info WHERE BOOK_ID = ? AND USER_ID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, bookId);
            pstmt.setString(2, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("bill") == 0.0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void payBill() {
        String userIdText = userIdField.getText();
        if (userIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter USER_ID to pay the bill.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("UPDATE borrow_info SET bill = 0 WHERE USER_ID = ?")) {

            pstmt.setString(1, userIdText);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Bill paid successfully! All dues cleared.", "Payment Successful", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No outstanding bills found or user does not exist.", "Payment Unsuccessful", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error while updating the bill: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
=======
package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Return extends JFrame {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/library";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "246810";

    private JTextField bookIdField;
    private JTextField userIdField;
    private JLabel userIdLabel;
    private JLabel bookIdLabel;
    private JButton returnButton;
    private JButton closeButton;
    private JLabel BOOK_IDLabel;
    private JLabel USER_IDLabel;
    private JTextField textField1;
    private JTextField textField2;
    private JButton payButton;
    private JPanel mainPanel;

    public Return() {
        super("Return Book");
        createUI();
        setActionListeners();
    }

    private void createUI() {
        userIdLabel = new JLabel("USER_ID:");
        bookIdLabel = new JLabel("BOOK_ID:");
        bookIdField = new JTextField(10);
        userIdField = new JTextField(10);
        returnButton = new JButton("Return Book");
        closeButton = new JButton("Close");
        payButton = new JButton("Pay Bill");

        mainPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        mainPanel.add(bookIdLabel);
        mainPanel.add(bookIdField);
        mainPanel.add(userIdLabel);
        mainPanel.add(userIdField);
        mainPanel.add(returnButton);
        mainPanel.add(payButton);
        mainPanel.add(closeButton);

        add(mainPanel, BorderLayout.CENTER);
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void setActionListeners() {
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnBook();
            }
        });

        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                payBill();
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void returnBook() {
        String bookIdText = bookIdField.getText();
        String userIdText = userIdField.getText();

        if (bookIdText.isEmpty() || userIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in both USER_ID and BOOK_ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int bookId;
        try {
            bookId = Integer.parseInt(bookIdText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid BOOK_ID format.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isBookBorrowed(bookId, userIdText)) {
            JOptionPane.showMessageDialog(this, "This book is not currently borrowed by this user.", "Book Not Borrowed", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!isBillCleared(bookId, userIdText)) {
            JOptionPane.showMessageDialog(this, "Outstanding bill must be cleared before returning the book.", "Bill Outstanding", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM borrow_info WHERE BOOK_ID = ? AND USER_ID = ?")) {

            pstmt.setInt(1, bookId);
            pstmt.setString(2, userIdText);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Book returned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error returning the book. Please try again.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error returning the book: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isBookBorrowed(int bookId, String userId) {
        String query = "SELECT COUNT(*) FROM borrow_info WHERE BOOK_ID = ? AND USER_ID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, bookId);
            pstmt.setString(2, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isBillCleared(int bookId, String userId) {
        String query = "SELECT bill FROM borrow_info WHERE BOOK_ID = ? AND USER_ID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, bookId);
            pstmt.setString(2, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("bill") == 0.0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void payBill() {
        String userIdText = userIdField.getText();
        if (userIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter USER_ID to pay the bill.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("UPDATE borrow_info SET bill = 0 WHERE USER_ID = ?")) {

            pstmt.setString(1, userIdText);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Bill paid successfully! All dues cleared.", "Payment Successful", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No outstanding bills found or user does not exist.", "Payment Unsuccessful", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error while updating the bill: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
>>>>>>> 00c9df7 (Describe what you updated)
