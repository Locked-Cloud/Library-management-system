package project;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

public class PDFViewerApp extends JFrame {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/library";
    private static final String DB_USER = "root"; // Update with your MySQL username
    private static final String DB_PASSWORD = "root"; // Update with your MySQL password

    private JTextField bookIdField;
    private JPanel pdfPanel;
    private JScrollPane scrollPane;

    public PDFViewerApp() {
        super("PDF Viewer");
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        JLabel bookIdLabel = new JLabel("Enter BOOK_ID:");
        bookIdField = new JTextField(10);
        JButton openButton = new JButton("Open PDF");
        JButton closeButton = new JButton("Close");

        topPanel.add(bookIdLabel);
        topPanel.add(bookIdField);
        topPanel.add(openButton);
        topPanel.add(closeButton);

        pdfPanel = new JPanel();
        pdfPanel.setLayout(new GridLayout(0, 1)); // 0 rows allows dynamic number of rows

        scrollPane = new JScrollPane(pdfPanel);
        scrollPane.setPreferredSize(new Dimension(600, 800));

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        openButton.addActionListener(e -> openPDF());
        closeButton.addActionListener(e -> dispose());

        setSize(800, 1000);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Set dialog default close operation
        setLocationRelativeTo(null);
    }

    private void openPDF() {
        String bookIdText = bookIdField.getText();

        if (bookIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid BOOK_ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int bookId;
        try {
            bookId = Integer.parseInt(bookIdText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid BOOK_ID format.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        byte[] pdfData = fetchPDFDataFromDatabase(bookId);

        if (pdfData != null) {
            displayAllPagesInGUI(pdfData);
        } else {
            JOptionPane.showMessageDialog(this, "No PDF found for the given BOOK_ID.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private byte[] fetchPDFDataFromDatabase(int bookId) {
        byte[] pdfData = null;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("SELECT PDF FROM books WHERE BOOK_ID = ?")) {
            pstmt.setInt(1, bookId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                pdfData = rs.getBytes("PDF");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pdfData;
    }

    private void displayAllPagesInGUI(byte[] pdfData) {
        pdfPanel.removeAll(); // Clear any existing content

        try (InputStream inputStream = new ByteArrayInputStream(pdfData);
             PDDocument pdfDocument = PDDocument.load(inputStream)) {
            PDFRenderer pdfRenderer = new PDFRenderer(pdfDocument);
            int totalPages = pdfDocument.getNumberOfPages();

            for (int i = 0; i < totalPages; i++) {
                Image pdfImage = pdfRenderer.renderImageWithDPI(i, 100);
                ImageIcon pdfIcon = new ImageIcon(pdfImage.getScaledInstance(600, -1, Image.SCALE_SMOOTH));
                JLabel pageLabel = new JLabel(pdfIcon);
                pdfPanel.add(pageLabel);
            }

            pdfPanel.revalidate();
            pdfPanel.repaint();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error displaying the PDF file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


}
