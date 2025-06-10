import javax.swing.*;
import java.sql.*;

public class LoginForm extends JFrame {
    public LoginForm() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField username = new JTextField();
        JPasswordField password = new JPasswordField();
        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register");

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(new JLabel("Username")); add(username);
        add(new JLabel("Password")); add(password);
        add(loginBtn); add(registerBtn);

        loginBtn.addActionListener(e -> {
            try (Connection conn = DBHelper.connect()) {
                String sql = "SELECT id, is_admin FROM users WHERE username=? AND password=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, username.getText());
                ps.setString(2, new String(password.getPassword()));
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    User user = new User(rs.getInt("id"), username.getText(), rs.getInt("is_admin") == 1);
                    new Dashboard(user);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid login");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        registerBtn.addActionListener(e -> {
            new RegisterForm();
            dispose();
        });

        setVisible(true);
    }
}
