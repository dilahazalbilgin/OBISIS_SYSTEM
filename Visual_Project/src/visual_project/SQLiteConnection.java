package visual_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;

public class SQLiteConnection {

    public static Connection connect() {
        try {
            String url = "jdbc:sqlite:new_database.db";
            Connection conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
            return conn;
        } catch (SQLException e) {
            System.out.println("Connection error: " + e.getMessage());
            return null;
        }
    }

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Users (\n"
                + "    number TEXT PRIMARY KEY,\n"
                + "    name TEXT NOT NULL,\n"
                + "    password TEXT NOT NULL,\n"
                + "    class TEXT\n"
                + ");";

        try (Connection conn = connect()) {
            if (conn == null) {
                System.out.println("Connection is null. Unable to proceed.");
                return;
            }
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            System.out.println("Teacher table created successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createTeacherTable() {
        String sql = "CREATE TABLE IF NOT EXISTS TeacherUsers (\n"
                + "    number TEXT PRIMARY KEY,\n"
                + "    name TEXT NOT NULL,\n"
                + "    password TEXT NOT NULL,\n"
                + "    code INTEGER NOT NULL\n"
                + ");";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("TeacherUsers table created successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertTeacherUser(String number, String name, String password, int code) {
        String sql = "INSERT INTO TeacherUsers(number, name, password, code) VALUES(?, ?, ?, ?)";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, number);
            pstmt.setString(2, name);
            pstmt.setString(3, password);
            pstmt.setInt(4, code);
            pstmt.executeUpdate();
            System.out.println("Teacher user added successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
