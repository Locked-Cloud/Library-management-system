package project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Show_report extends JDialog {
    private JPanel panel;
    private JTable table;
    private JButton closeButton;

    public Show_report(JFrame parentFrame) {
        super(parentFrame, "Show Reports", true);

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create table model with column names
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("BOOK_ID");
        model.addColumn("USER_ID");
        model.addColumn("DESCRIPTION");

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        closeButton = new JButton("Close");

        closeButton.addActionListener(e -> dispose());

        // Load data from database into the table
        loadReportsData(model);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(closeButton, BorderLayout.SOUTH);

        setContentPane(panel);
        setSize(new Dimension(600, 400));
        setLocationRelativeTo(parentFrame);
    }

    private void loadReportsData(DefaultTableModel model) {
        String url = "jdbc:mysql://localhost:3306/library";
        String dbUser = "root";
        String password = "root";
        String sql = "SELECT * FROM reports";

        try (Connection connection = DriverManager.getConnection(url, dbUser, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            // Clear existing rows
            model.setRowCount(0);

            // Iterate through the result set and add rows to the table model
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String bookId = resultSet.getString("book_id");
                String userId = resultSet.getString("user_id");
                String description = resultSet.getString("description");
                model.addRow(new Object[]{id, bookId, userId, description});
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
