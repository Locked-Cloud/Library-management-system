    package project;

    import javax.swing.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;

    public class Dashboardadmin extends JFrame {
        private JPanel dashboardPanel;
        private JButton button1;
        private JButton button2;
        private JButton button3;
        private JButton button4;
        private JButton button5;
        private JButton button6 ;

        private JPanel Dashboardadmin;
        private JButton reportButton;
        private JButton loginOutButton;
        public Dashboardadmin() {
            setTitle("Admin Dashboard");
            setSize(600, 400);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null); // Center the frame on the screen

            // Create a JPanel to hold the components
            dashboardPanel = new JPanel();

            // Create buttons
            button1 = new JButton("Show All Books");
            button2 = new JButton("Edit Books");
            button3 = new JButton("Borrow history");
            button4 = new JButton("Manager Users");
            button5 = new JButton("View Books");
            button6 = new JButton("Borrow");

            reportButton = new JButton("Report");
            loginOutButton = new JButton("Logout");
            // Add buttons to the panel
            dashboardPanel.add(button1);
            dashboardPanel.add(button2);
            dashboardPanel.add(button3);
            dashboardPanel.add(button4);
            dashboardPanel.add(button5);
            dashboardPanel.add(button6);
            dashboardPanel.add(reportButton);
            dashboardPanel.add(loginOutButton);
            // Set the content pane of the frame to the dashboard panel
            setContentPane(dashboardPanel);

            // Set the frame visible
            setVisible(true);
            loginOutButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    help run = new help();
                    run.setVisible(true);

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
                    Edit edit = new Edit();
                    edit.setVisible(true);
                }
            });
            button3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    history_books run = new history_books(null);
                    run.setVisible(true);
                }
            });
            button4.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    manager_users manager = new manager_users();
                    manager.setVisible(true);
                }
            });

            button5.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    PDFViewerApp PDF = new PDFViewerApp();
                    PDF.setVisible(true);
                }
            });
            button6.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    BorrowBook borrow = new BorrowBook();
                    borrow.setVisible(true);
                }
            });
            reportButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Show_report run = new Show_report(null);
                    run.setVisible(true);
                }
            });
        }
    }
