package project;

public class Main {
    public static void main(String[] args) {
        // Create and show the login dialog
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Create an instance of the Login class
                Login login = new Login(null); // Assuming null as the parent frame

            }
        });
    }
}
