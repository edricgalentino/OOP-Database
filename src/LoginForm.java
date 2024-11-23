import javax.swing.*;
import java.sql.*;

public class LoginForm extends JFrame {
    private JTextField usernameInput;
    private JTextField passwordInput;
    private JButton loginButton;
    private JPanel mainPanel;
    private JButton registerButton;
    private JPanel panelButton;
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

    public LoginForm() throws SQLException {
        connection = getConnection();
        setContentPane(mainPanel);
        setTitle("Login Form");
        pack();
        setVisible(true);

        loginButton.addActionListener(e -> {
            String username = usernameInput.getText();
            String password = passwordInput.getText();

            if (username == null || password == null) {
                JOptionPane.showMessageDialog(this, "Username and password must be filled!");
            } else {
                try {
                    if (validateLogin(username, password)) {
                        JOptionPane.showMessageDialog(this, "Login Berhasil");
                    } else {
                        JOptionPane.showMessageDialog(this, "Login Gagal");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        registerButton.addActionListener(e -> {
            String username = usernameInput.getText();
            String password = passwordInput.getText();

            if (username == null || password == null) {
                JOptionPane.showMessageDialog(this, "Username and password must be filled!");
            } else {
                try {
                    connection.prepareStatement("INSERT INTO users (username, password) VALUES ('" + username + "', '" + password + "')");
                    JOptionPane.showMessageDialog(this, "Register Berhasil");
                } catch (SQLException throwables) {
                    JOptionPane.showMessageDialog(this, "Register Gagal");
                }
            }
        });
    }

    private boolean validateLogin(String username, String password) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
        return statement.executeQuery(query).next();
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        new LoginForm();
    }
}
