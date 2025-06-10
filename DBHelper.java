import java.sql.*;

public class DBHelper {
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:online_booking.db");
    }

    public static void setupDatabase() {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT UNIQUE," +
                    "password TEXT," +
                    "is_admin INTEGER DEFAULT 0)");

            stmt.execute("CREATE TABLE IF NOT EXISTS bookings (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "user_id INTEGER," +
                    "service TEXT," +
                    "date TEXT," +
                    "FOREIGN KEY(user_id) REFERENCES users(id))");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
