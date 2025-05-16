<<<<<<< HEAD
package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BorrowWindow extends JFrame {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/borrow_system";
    private static final String DB_USER = "root"; // Update with your MySQL username
    private static final String DB_PASSWORD = "root"; // Update with your MySQL password

    private JTextField bookIdField;
    private JTextField userIdField;
    private JTextField takenDateField;
    private JTextField returnDateField;
    private JButton calculateButton;
    private JButton borrowButton;
    private JLabel billLabel;

    public BorrowWindow() {
        super("Borrow Book");
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel bookIdLabel = new JLabel("Enter BOOK_ID:");
        bookIdField = new JTextField(10);

        JLabel userIdLabel = new JLabel("Enter USER_ID:");
        userIdField = new JTextField(10);

        JLabel takenDateLabel = new JLabel("Taken Date (yyyy-MM-dd):");
        takenDateField = new JTextField(10);

        JLabel returnDateLabel = new JLabel("Return Date (yyyy-MM-dd):");
        returnDateField = new JTextField(10);

        calculateButton = new JButton("Calculate Bill");
        borrowButton = new JButton("Borrow Book");
        billLabel = new JLabel("Bill: £0.00");

        mainPanel.add(bookIdLabel);
        mainPanel.add(bookIdField);
        mainPanel.add(userIdLabel);
        mainPanel.add(userIdField);
        mainPanel.add(takenDateLabel);
        mainPanel.add(takenDateField);
        mainPanel.add(returnDateLabel);
        mainPanel.add(returnDateField);
        mainPanel.add(calculateButton);
        mainPanel.add(borrowButton);

        add(mainPanel, BorderLayout.CENTER);
        add(billLabel, BorderLayout.SOUTH);

        setSize(350, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window
        setLocationRelativeTo(null);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateBill();
            }
        });

        borrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrowBook();
            }
        });
    }

    private void calculateBill() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date takenDate = sdf.parse(takenDateField.getText());
            Date returnDate = sdf.parse(returnDateField.getText());

            long diff = returnDate.getTime() - takenDate.getTime();
            long days = (diff / (1000 * 60 * 60 * 24));

            double bill = days * 1.0;
            Date currentDate = new Date();

            if (returnDate.before(currentDate)) {
                long lateDays = (currentDate.getTime() - returnDate.getTime()) / (1000 * 60 * 60 * 24);
                bill += lateDays * 0.10;
            }

            billLabel.setText(String.format("Bill: £%.2f", bill));

        } catch (ParseException e) {
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
            System.out.println("Please fill in all fields.");
            return;
        }

        int bookId, userId;
        try {
            bookId = Integer.parseInt(bookIdText);
            userId = Integer.parseInt(userIdText);
        } catch (NumberFormatException e) {
            System.out.println("Invalid BOOK_ID or USER_ID format.");
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO borrow_info (book_id, user_id, taken_date, return_date) VALUES (?, ?, ?, ?)")) {

            pstmt.setInt(1, bookId);
            pstmt.setInt(2, userId);
            pstmt.setString(3, takenDateText);
            pstmt.setString(4, returnDateText);
            pstmt.executeUpdate();

            System.out.println("Book borrowed successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error borrowing the book.");
        }
    }

}
=======
package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BorrowWindow extends JFrame {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/borrow_system";
    private static final String DB_USER = "root"; // Update with your MySQL username
    private static final String DB_PASSWORD = "246810"; // Update with your MySQL password

    private JTextField bookIdField;
    private JTextField userIdField;
    private JTextField takenDateField;
    private JTextField returnDateField;
    private JButton calculateButton;
    private JButton borrowButton;
    private JLabel billLabel;

    public BorrowWindow() {
        super("Borrow Book");
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel bookIdLabel = new JLabel("Enter BOOK_ID:");
        bookIdField = new JTextField(10);

        JLabel userIdLabel = new JLabel("Enter USER_ID:");
        userIdField = new JTextField(10);

        JLabel takenDateLabel = new JLabel("Taken Date (yyyy-MM-dd):");
        takenDateField = new JTextField(10);

        JLabel returnDateLabel = new JLabel("Return Date (yyyy-MM-dd):");
        returnDateField = new JTextField(10);

        calculateButton = new JButton("Calculate Bill");
        borrowButton = new JButton("Borrow Book");
        billLabel = new JLabel("Bill: £0.00");

        mainPanel.add(bookIdLabel);
        mainPanel.add(bookIdField);
        mainPanel.add(userIdLabel);
        mainPanel.add(userIdField);
        mainPanel.add(takenDateLabel);
        mainPanel.add(takenDateField);
        mainPanel.add(returnDateLabel);
        mainPanel.add(returnDateField);
        mainPanel.add(calculateButton);
        mainPanel.add(borrowButton);

        add(mainPanel, BorderLayout.CENTER);
        add(billLabel, BorderLayout.SOUTH);

        setSize(350, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window
        setLocationRelativeTo(null);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateBill();
            }
        });

        borrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrowBook();
            }
        });
    }

    private void calculateBill() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date takenDate = sdf.parse(takenDateField.getText());
            Date returnDate = sdf.parse(returnDateField.getText());

            long diff = returnDate.getTime() - takenDate.getTime();
            long days = (diff / (1000 * 60 * 60 * 24));

            double bill = days * 1.0;
            Date currentDate = new Date();

            if (returnDate.before(currentDate)) {
                long lateDays = (currentDate.getTime() - returnDate.getTime()) / (1000 * 60 * 60 * 24);
                bill += lateDays * 0.10;
            }

            billLabel.setText(String.format("Bill: £%.2f", bill));

        } catch (ParseException e) {
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
            System.out.println("Please fill in all fields.");
            return;
        }

        int bookId, userId;
        try {
            bookId = Integer.parseInt(bookIdText);
            userId = Integer.parseInt(userIdText);
        } catch (NumberFormatException e) {
            System.out.println("Invalid BOOK_ID or USER_ID format.");
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO borrow_info (book_id, user_id, taken_date, return_date) VALUES (?, ?, ?, ?)")) {

            pstmt.setInt(1, bookId);
            pstmt.setInt(2, userId);
            pstmt.setString(3, takenDateText);
            pstmt.setString(4, returnDateText);
            pstmt.executeUpdate();

            System.out.println("Book borrowed successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error borrowing the book.");
        }
    }

}
>>>>>>> 00c9df7 (Describe what you updated)
