<<<<<<< HEAD
package project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class manager_users extends JFrame {
    private JButton addButton;
    private JButton removeButton;
    private JLabel managerUsersLabel;
    private JButton closeButton;

    public manager_users() {
        setTitle("Manage Users");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        managerUsersLabel = new JLabel("Manage Users:");
        addButton = new JButton("Add User");
        removeButton = new JButton("Remove User");
        closeButton = new JButton("Close");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic for adding user
                Create create = new Create();
                create.setVisible(true);
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic for removing user
                Remove user = new Remove();
                user.setVisible(true);
            }
        });

        panel.add(managerUsersLabel);
        panel.add(addButton);
        panel.add(removeButton);
        panel.add(closeButton);
        add(panel);

        setVisible(true);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

    }

}
=======
package project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class manager_users extends JFrame {
    private JButton addButton;
    private JButton removeButton;
    private JLabel managerUsersLabel;
    private JButton closeButton;
    private JButton AddStuffButton;

    public manager_users() {
        setTitle("Manage Users");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        managerUsersLabel = new JLabel("Manage Users:");
        addButton = new JButton("Add User");
        removeButton = new JButton("Remove User");
        AddStuffButton = new JButton("Add Stuff");
        closeButton = new JButton("Close");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic for adding user
                Create create = new Create();
                create.setVisible(true);
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic for removing user
                Remove user = new Remove();
                user.setVisible(true);
            }
        });

        panel.add(managerUsersLabel);
        panel.add(addButton);
        panel.add(removeButton);
        panel.add(AddStuffButton);
        panel.add(closeButton);
        add(panel);

        setVisible(true);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        AddStuffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StaffGUI staff = new StaffGUI();
                staff.setVisible(true);
            }
        });

    }

}
>>>>>>> 00c9df7 (Describe what you updated)
