import javax.swing.*;
import java.sql.*;

public class RegisterForm extends JFrame {
    public RegisterForm() {
        setTitle("Register");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField username = new JTextField();
        JPasswordField password = new JPasswordField();
        JButton registerBtn = new JButton("Register");

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(new JLabel("Username")); add(username);
        add(new JLabel("Password")); add(password);
        add(registerBtn);

        registerBtn.addActionListener(e -> {
            try (Connection conn = DBHelper.connect()) {
                String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, username.getText());
                ps.setString(2, new String(password.getPassword()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Registered successfully!");
                new LoginForm();
                dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "User exists or error!");
            }
        });

        setVisible(true);
    }
}
