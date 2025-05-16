<<<<<<< HEAD
package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UploadPDFApp extends JFrame {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/library";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    private int bookId;
    private String bookName;
    private String category;
    private String author;
    private JTextField pdfFilePathField;

    public UploadPDFApp(int bookId, String bookName, String category, String author) {
        super("PDF Uploader");
        this.bookId = bookId;
        this.bookName = bookName;
        this.category = category;
        this.author = author;
        initComponents();
        setSize(500, 180);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        JLabel pdfFilePathLabel = new JLabel("PDF File Path:");
        pdfFilePathField = new JTextField(25);
        pdfFilePathField.setEditable(false);

        JButton browseButton = new JButton("Browse");
        JButton uploadButton = new JButton("Upload PDF");
        JButton closeButton = new JButton("Close");

        panel.add(pdfFilePathLabel);
        panel.add(pdfFilePathField);
        panel.add(browseButton);
        panel.add(uploadButton);
        panel.add(closeButton);

        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choosePDFFile();
            }
        });

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uploadPDF();
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        add(panel, BorderLayout.CENTER);
    }

    private void choosePDFFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            pdfFilePathField.setText(selectedFile.getAbsolutePath());
        }
    }

    private void uploadPDF() {
        String pdfFilePath = pdfFilePathField.getText();
        if (pdfFilePath.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a PDF file.", "Upload Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        File pdfFile = new File(pdfFilePath);
        if (pdfFile.exists() && pdfFile.isFile()) {
            uploadPDFToDatabase(bookId, bookName, category, author, pdfFile);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid file path.", "Upload Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void uploadPDFToDatabase(int bookId, String bookName, String category, String author, File pdfFile) {
        String insertQuery = "INSERT INTO books (BOOK_ID, CATEGORY, NAME, AUTHOR, PDF, FILE_NAME) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(insertQuery);
             FileInputStream fis = new FileInputStream(pdfFile)) {

            pstmt.setInt(1, bookId);
            pstmt.setString(2, category);
            pstmt.setString(3, bookName);
            pstmt.setString(4, author);
            pstmt.setBinaryStream(5, fis, (int) pdfFile.length());
            pstmt.setString(6, pdfFile.getName());
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Book and PDF uploaded successfully!");
            dispose();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error uploading the book and PDF file: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
=======
package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UploadPDFApp extends JFrame {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/library";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "246810";

    private int bookId;
    private String bookName;
    private String category;
    private String author;
    private JTextField pdfFilePathField;

    public UploadPDFApp(int bookId, String bookName, String category, String author) {
        super("PDF Uploader");
        this.bookId = bookId;
        this.bookName = bookName;
        this.category = category;
        this.author = author;
        initComponents();
        setSize(500, 180);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        JLabel pdfFilePathLabel = new JLabel("PDF File Path:");
        pdfFilePathField = new JTextField(25);
        pdfFilePathField.setEditable(false);

        JButton browseButton = new JButton("Browse");
        JButton uploadButton = new JButton("Upload PDF");
        JButton closeButton = new JButton("Close");

        panel.add(pdfFilePathLabel);
        panel.add(pdfFilePathField);
        panel.add(browseButton);
        panel.add(uploadButton);
        panel.add(closeButton);

        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choosePDFFile();
            }
        });

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uploadPDF();
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        add(panel, BorderLayout.CENTER);
    }

    private void choosePDFFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            pdfFilePathField.setText(selectedFile.getAbsolutePath());
        }
    }

    private void uploadPDF() {
        String pdfFilePath = pdfFilePathField.getText();
        if (pdfFilePath.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a PDF file.", "Upload Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        File pdfFile = new File(pdfFilePath);
        if (pdfFile.exists() && pdfFile.isFile()) {
            uploadPDFToDatabase(bookId, bookName, category, author, pdfFile);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid file path.", "Upload Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void uploadPDFToDatabase(int bookId, String bookName, String category, String author, File pdfFile) {
        String insertQuery = "INSERT INTO books (BOOK_ID, CATEGORY, NAME, AUTHOR, PDF, FILE_NAME) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(insertQuery);
             FileInputStream fis = new FileInputStream(pdfFile)) {

            pstmt.setInt(1, bookId);
            pstmt.setString(2, category);
            pstmt.setString(3, bookName);
            pstmt.setString(4, author);
            pstmt.setBinaryStream(5, fis, (int) pdfFile.length());
            pstmt.setString(6, pdfFile.getName());
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Book and PDF uploaded successfully!");
            dispose();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error uploading the book and PDF file: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
>>>>>>> 00c9df7 (Describe what you updated)
