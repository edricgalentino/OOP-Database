import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CRUDUser extends JFrame {
    private JButton newButton;
    private JButton updateButton;
    private JButton cancelButton;
    private JTextField usernameInput;
    private JTextField emailInput;
    private JPasswordField repeatPasswordInput;
    private JPasswordField passwordInput;
    private JComboBox roleInput;
    private JPanel mainPanel;
    private JTextField userIdInput;
    private static final String URL = "jdbc:mysql://localhost:3306/ecommerce";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "12345";
    private final Connection connection;

    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            return connection;
        } catch (Exception e) {
            throw new SQLException("Failed to connect to database", e);
        }
    }

    public CRUDUser() throws SQLException {
        connection = getConnection();
        setContentPane(mainPanel);
        setTitle("CRUD User Form");
        pack();
        setVisible(true);

        newButton.addActionListener(e -> {
            String username = usernameInput.getText();
            String email = emailInput.getText();
            String password = new String(passwordInput.getPassword());
            String repeatPassword = new String(repeatPasswordInput.getPassword());
            String role = roleInput.getSelectedItem().toString();

            if (username == null || email == null || password == null || repeatPassword == null || role == null) {
                JOptionPane.showMessageDialog(null, "All fields must be filled!");
            } else if (!password.equals(repeatPassword)) {
                JOptionPane.showMessageDialog(null, "Password and Repeat Password must be the same!");
            } else {
                try {
                    Statement statement = connection.createStatement();
                    statement.executeUpdate("INSERT INTO users (username, email, password, role) VALUES ('" + username + "', '" + email + "', '" + password + "', '" + role + "')");
                    JOptionPane.showMessageDialog(null, "Insert User Berhasil");
                    clearForm();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        updateButton.addActionListener(e -> {
            String username = usernameInput.getText();
            String email = emailInput.getText();
            String password = new String(passwordInput.getPassword());
            String repeatPassword = new String(repeatPasswordInput.getPassword());
            String role = roleInput.getSelectedItem().toString();
            String userId = userIdInput.getText();

            if (username == null || email == null || password == null || repeatPassword == null || role == null) {
                JOptionPane.showMessageDialog(null, "All fields must be filled!");
            } else if (!password.equals(repeatPassword)) {
                JOptionPane.showMessageDialog(null, "Password and Repeat Password must be the same!");
            } else {
                // Update user
                try {
                    connection.prepareStatement("UPDATE users SET username = '" + username + "', email = '" + email + "', password = '" + password + "', role = '" + role + "' WHERE id = " + userId);
                    JOptionPane.showMessageDialog(null, "Update User Berhasil");
                    clearForm();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        cancelButton.addActionListener(e -> {
            clearForm();
        });
    }

    public void clearForm() {
        usernameInput.setText("");
        emailInput.setText("");
        passwordInput.setText("");
        repeatPasswordInput.setText("");
        roleInput.setSelectedIndex(0);
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        new CRUDUser();
    }

}
