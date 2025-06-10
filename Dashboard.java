import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class Dashboard extends JFrame {
    public Dashboard(User user) {
        setTitle("Dashboard - " + user.username);
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField serviceField = new JTextField();
        JTextField dateField = new JTextField();
        JButton bookBtn = new JButton("Book");
        JTextArea bookingsArea = new JTextArea();

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(new JLabel("Service")); add(serviceField);
        add(new JLabel("Date (YYYY-MM-DD)")); add(dateField);
        add(bookBtn); add(new JLabel("Bookings")); add(new JScrollPane(bookingsArea));

        bookBtn.addActionListener(e -> {
            try (Connection conn = DBHelper.connect()) {
                String sql = "INSERT INTO bookings (user_id, service, date) VALUES (?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, user.id);
                ps.setString(2, serviceField.getText());
                ps.setString(3, dateField.getText());
                ps.executeUpdate();
                loadBookings(user, bookingsArea);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        loadBookings(user, bookingsArea);
        setVisible(true);
    }

    private void loadBookings(User user, JTextArea area) {
        area.setText("");
        try (Connection conn = DBHelper.connect()) {
            String sql = user.isAdmin ?
                    "SELECT * FROM bookings" :
                    "SELECT * FROM bookings WHERE user_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            if (!user.isAdmin) ps.setInt(1, user.id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                area.append("Service: " + rs.getString("service") + ", Date: " + rs.getString("date") + "\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
