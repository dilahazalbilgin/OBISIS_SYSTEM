package visual_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class SQLiteConnection {
    public static Connection connect() {
        try {
            String url = "jdbc:sqlite:obisis.db";  // Veritabanı adı
            Connection conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
            return conn;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (\n"
                   + "    number TEXT PRIMARY KEY,\n"
                   + "    name TEXT NOT NULL,\n"
                   + "    password TEXT NOT NULL,\n"
                   + "    class TEXT\n"
                   + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table created successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
