package visual_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.*;
import java.sql.ResultSet;

public class SQLiteConnection {

    public static Connection connect() {
        try {
            String url = "jdbc:sqlite:db_";
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
                + "    code INTEGER NOT NULL,\n"
                + "     branch TEXT NOT NULL"
                + ");";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("TeacherUsers table created successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertTeacherUser(String number, String name, String password, int code, String branch) {
        String sql = "INSERT INTO TeacherUsers(number, name, password, code, branch) VALUES(?, ?, ?, ?,?)";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, number);
            pstmt.setString(2, name);
            pstmt.setString(3, password);
            pstmt.setInt(4, code);
            pstmt.setString(5, branch);
            pstmt.executeUpdate();
            System.out.println("Teacher user added successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createConfirmedStudentLessonsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS ConfirmedLessons (\n"
                + "    number TEXT NOT NULL,\n"
                + "    day TEXT NOT NULL,\n"
                + "    hour TEXT NOT NULL,\n"
                + "    lecture TEXT NOT NULL\n"
                + ");";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("ConfirmedLessons table created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating ConfirmedLessons table: " + e.getMessage());
        }
    }

    public static void insertConfirmedStudentLessonForStudent(String number, String day, String hour, String lecture) {
        String sql = "INSERT INTO ConfirmedLessons(number, day, hour, lecture) VALUES(?, ?, ?, ?)";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, number);
            pstmt.setString(2, day);
            pstmt.setString(3, hour);
            pstmt.setString(4, lecture);
            pstmt.executeUpdate();
            System.out.println("Lesson inserted for student number: " + number);
        } catch (SQLException e) {
            System.out.println("Error inserting lesson: " + e.getMessage());
        }
    }

    public static List<Object[]> getconfirmedStudentLessons() {
        List<Object[]> lessons = new ArrayList<>();
        String sql = "SELECT number, day, hour, lecture FROM ConfirmedLessons";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String number = rs.getString("number");
                String day = rs.getString("day");
                String hour = rs.getString("hour");
                String lecture = rs.getString("lecture");
                lessons.add(new Object[]{number, day, hour, lecture});
            }
            System.out.println("Confirmed lessons fetched successfully.");
        } catch (SQLException e) {
            System.out.println("Error fetching confirmed lessons: " + e.getMessage());
        }

        return lessons;
    }

    public static void createPublicNoticeTable() {
        String sql = "CREATE TABLE IF NOT EXISTS PublicNotice (\n"
                + "    name TEXT NOT NULL,\n"
                + "    notice TEXT NOT NULL,\n"
                + "    publishedDate DATETIME DEFAULT CURRENT_TIMESTAMP\n"
                + ");";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("PublicNotice table created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating PublicNotice table: " + e.getMessage());
        }
    }

    public static void insertNotice(String teacherNumber, String noticeText) {
        String getTeacherNameSQL = "SELECT name FROM TeacherUsers WHERE number = ?";
        String teacherName = null;

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(getTeacherNameSQL)) {
            pstmt.setString(1, teacherNumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                teacherName = rs.getString("name");
            } else {
                System.out.println("Teacher not found with number: " + teacherNumber);
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error fetching teacher name: " + e.getMessage());
            return;
        }

        if (teacherName != null) {
            String sql = "INSERT INTO PublicNotice (notice, name) VALUES (?, ?)";
            try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, noticeText);
                pstmt.setString(2, teacherName);
                pstmt.executeUpdate();
                System.out.println("Notice inserted into the database.");
            } catch (SQLException e) {
                System.out.println("Error inserting notice: " + e.getMessage());
            }
        }
    }

    public static void create() {
        SQLiteConnection.createTable();
        SQLiteConnection.createTeacherTable();
        SQLiteConnection.createPublicNoticeTable();
        SQLiteConnection.createConfirmedStudentLessonsTable();
        SQLiteConnection.insertTeacherUser("00", "ali", "00", 0, "Math");
        SQLiteConnection.insertTeacherUser("11", "mustafa", "11", 1, "Differantial");
        SQLiteConnection.insertTeacherUser("22", "kemal", "22", 2, "Programing");
        SQLiteConnection.insertTeacherUser("33", "ayşe", "33", 3, "Numeric");
        SQLiteConnection.insertTeacherUser("44", "hafsa", "44", 4, "Linear");
    }

}
