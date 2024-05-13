package project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboarduser extends JFrame {
    private JPanel dashboardPanel;
    private JButton button1;
    private JButton button2;
    private JButton button3;

    private JPanel Dashboarduser;
    private JButton borrowBookButton;
    private JButton reportButton;
    private JButton loginOutButton;
    private JButton returnButton;

    public Dashboarduser() {
        setTitle("User Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        // Create a JPanel to hold the components
        dashboardPanel = new JPanel();

        // Create buttons
        button1 = new JButton("Show Books");
        button2 = new JButton("View Books");
        borrowBookButton = new JButton("Borrow Book");
        returnButton = new JButton("Return Books");
        reportButton = new JButton("Report Book");
        loginOutButton = new JButton("Logout");
        // Add buttons to the panel
        dashboardPanel.add(button1);
        dashboardPanel.add(button2);
        dashboardPanel.add(borrowBookButton);
        dashboardPanel.add(returnButton);
        dashboardPanel.add(reportButton);
        dashboardPanel.add(loginOutButton);
        // Set the content pane of the frame to the dashboard panel
        setContentPane(dashboardPanel);

        // Set the frame visible

        loginOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                    help run = new help();

            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Show_All show = new Show_All();

            }
        });


        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PDFViewerApp run = new PDFViewerApp();
                run.setVisible(true);
            }
        });
        borrowBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BorrowBook borrow = new BorrowBook();
                borrow.setVisible(true);
            }
        });
        reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Report report = new Report(null);
                report.setVisible(true);
            }
        });
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Return return_ = new Return();
                return_.setVisible(true);
            }
        });
    }
}
