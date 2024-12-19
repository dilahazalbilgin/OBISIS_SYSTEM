package visual_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.*;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class SqlConnect {

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
                + "    number TEXT PRIMARY KEY,\n"
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

    public static void insertPublicNotice(String teacherNumber, String noticeText) {
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

    public static List<String> fetchPublicNotices() {
        List<String> notices = new ArrayList<>();
        String sql = "SELECT name, notice FROM PublicNotice";

        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String name = rs.getString("name");
                String notice = rs.getString("notice");
                notices.add(String.format("Teacher %s:\n %s", name, notice));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching notices: " + e.getMessage());
        }

        return notices;
    }

    public static void createPrivateNoticeTable() {
        String sql = "CREATE TABLE IF NOT EXISTS PrivateNotice (\n"
                + "    name TEXT NOT NULL,\n"
                + "    class TEXT NOT NULL,\n"
                + "    notice TEXT NOT NULL,\n"
                + "    publishedDate DATETIME DEFAULT CURRENT_TIMESTAMP\n"
                + ");";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("PrvateNotice table created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating PrvateNotice table: " + e.getMessage());
        }
    }

    public static void insertPrivateNotice(String teacherNumber, String noticeText, String className) {
        String getTeacherNameSQL = "SELECT name FROM TeacherUsers WHERE number = ?";
        String teacherName = null;

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(getTeacherNameSQL)) {
            pstmt.setString(1, teacherNumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                teacherName = rs.getString("name");
            } else {
                JOptionPane.showMessageDialog(null, "Teacher not found!");
                return;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching teacher name: " + e.getMessage());
            return;
        }

        if (teacherName != null) {
            String sql = "INSERT INTO PrivateNotice (notice, name, class) VALUES (?, ?, ?)";
            try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, noticeText);
                pstmt.setString(2, teacherName);
                pstmt.setString(3, className);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error inserting notice: " + e.getMessage());
            }
        }
    }

    public static List<String> fetchPrivateNotices(String studentClass) {
        List<String> notices = new ArrayList<>();
        String sql = "SELECT name, notice, class FROM PrivateNotice WHERE class = ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentClass);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String clas = rs.getString("class");
                String name = rs.getString("name");
                String notice = rs.getString("notice");
                notices.add(String.format("Class: %s \nTeacher %s: \n%s", clas, name, notice));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching notices: " + e.getMessage());
        }

        return notices;
    }

    public static void createTeacherCourseTable() {
        String sql = " CREATE TABLE IF NOT EXISTS TeacherCourse (\n"
                + "    id INT AUTO_INCREMENT PRIMARY KEY,\n"
                + "    teacherNumber VARCHAR(50),\n"
                + "    teacherName VARCHAR(100),\n"
                + "    day VARCHAR(50),\n"
                + "    hour VARCHAR(50),\n"
                + "    courseName VARCHAR(100)\n"
                + ");";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("TeacherCourse table created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating TeacherCourse table: " + e.getMessage());
        }
    }

    public static void insertTeacherCourse(String teacherNumber, String teacherName, String day, String hour, String courseName) {
        String sql = "INSERT INTO TeacherCourse (teacherNumber, teacherName, day, hour, courseName) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, teacherNumber);
            pstmt.setString(2, teacherName);
            pstmt.setString(3, day);
            pstmt.setString(4, hour);
            pstmt.setString(5, courseName);
            pstmt.executeUpdate();
            System.out.println("Course inserted successfully!");
        } catch (SQLException e) {
            System.out.println("Error inserting course: " + e.getMessage());
        }
    }

    public static List<String[]> getTeacherCourses() {
        List<String[]> courses = new ArrayList<>();
        String sql = "SELECT day, hour, courseName FROM TeacherCourse";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                courses.add(new String[]{
                    rs.getString("day"),
                    rs.getString("hour"),
                    rs.getString("courseName")
                });
            }
        } catch (SQLException e) {
            System.out.println("Error fetching courses: " + e.getMessage());
        }
        return courses;
    }

    public static List<String[]> fetchCourses() {
        List<String[]> courses = new ArrayList<>();
        String sql = "SELECT teacherNumber, teacherName, selectedSchedule FROM Courses";
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String teacherNumber = rs.getString("teacherNumber");
                String teacherName = rs.getString("teacherName");
                String schedule = rs.getString("selectedSchedule");
                courses.add(new String[]{teacherNumber, teacherName, schedule});
            }
        } catch (SQLException e) {
            System.out.println("Error fetching courses: " + e.getMessage());
        }
        return courses;
    }

    public static void createMathTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Math (\n"
                + "    number TEXT PRIMARY KEY,\n"
                + "    name TEXT NOT NULL,\n"
                + "    class TEXT NOT NULL,\n"
                + "    note TEXT NOT NULL,\n"
                + "    attendance TEXT NOT NULL DEFAULT '0'\n"
                + ");";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Math table created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating Math table: " + e.getMessage());
        }
    }

    public static void insertMath(String number, String name, String studentClass, String note, String attendance) {
        String sql = "INSERT INTO Math (number, name, class, note, attendance) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, number);
            pstmt.setString(2, name);
            pstmt.setString(3, studentClass);
            pstmt.setString(4, note);
            pstmt.setString(5, attendance);
            pstmt.executeUpdate();
            System.out.println("Student added to Math table successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding student to Math table: " + e.getMessage());
        }
    }

    public static void updateMathAttedance(String number, String attendance) {
        String sql = "UPDATE Math SET attendance = ? WHERE number = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, attendance);
            pstmt.setString(2, number);
            pstmt.executeUpdate();
            System.out.println("Student updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error updating student: " + e.getMessage());
        }
    }

    public static List<Object[]> getMathAttedanceStudentsByClass(String studentClass, String branch) {
        List<Object[]> students = new ArrayList<>();
        String sql = "SELECT number, name, class, attendance FROM Math WHERE class = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentClass);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                students.add(new Object[]{
                    rs.getString("number"),
                    rs.getString("name"),
                    branch,
                    rs.getString("attendance")
                });
            }
        } catch (SQLException e) {
            System.out.println("Error fetching students: " + e.getMessage());
        }
        return students;
    }

    public static void updateMathNote(String number, String note) {
        String sql = "UPDATE Math SET note = ? WHERE number = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, note);
            pstmt.setString(2, number);
            pstmt.executeUpdate();
            System.out.println("Student updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error updating student: " + e.getMessage());
        }
    }

    public static List<Object[]> getMathNoteStudentsByClass(String studentClass, String branch) {
        List<Object[]> students = new ArrayList<>();
        String sql = "SELECT number, name, class, note FROM Math WHERE class = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentClass);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                students.add(new Object[]{
                    rs.getString("number"),
                    rs.getString("name"),
                    branch,
                    rs.getString("note")
                });
            }
        } catch (SQLException e) {
            System.out.println("Error fetching students: " + e.getMessage());
        }
        return students;
    }

    public static void createDifferantialTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Differantial (\n"
                + "    number TEXT PRIMARY KEY,\n"
                + "    name TEXT NOT NULL,\n"
                + "    class TEXT NOT NULL,\n"
                + "    note TEXT NOT NULL,\n"
                + "    attendance TEXT NOT NULL DEFAULT '0'\n"
                + ");";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Differantial table created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating Differantial table: " + e.getMessage());
        }
    }

    public static void insertDifferantial(String number, String name, String studentClass, String note, String attendance) {
        String sql = "INSERT INTO Differantial (number, name, class, note, attendance) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, number);
            pstmt.setString(2, name);
            pstmt.setString(3, studentClass);
            pstmt.setString(4, note);
            pstmt.setString(5, attendance);
            pstmt.executeUpdate();
            System.out.println("Student added to Differantial table successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding student to Differantial table: " + e.getMessage());
        }
    }

    public static void updateDifferantialAttedance(String number, String attendance) {
        String sql = "UPDATE Differantial SET attendance = ? WHERE number = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, attendance);
            pstmt.setString(2, number);
            pstmt.executeUpdate();
            System.out.println("Student updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error updating student: " + e.getMessage());
        }
    }

    public static List<Object[]> getDifferantialAttedanceStudentsByClass(String studentClass, String branch) {
        List<Object[]> students = new ArrayList<>();
        String sql = "SELECT number, name, class, attendance FROM Differantial WHERE class = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentClass);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                students.add(new Object[]{
                    rs.getString("number"),
                    rs.getString("name"),
                    branch,
                    rs.getString("attendance")
                });
            }
        } catch (SQLException e) {
            System.out.println("Error fetching students: " + e.getMessage());
        }
        return students;
    }

    public static void updateDifferantialNote(String number, String note) {
        String sql = "UPDATE Differantial SET note = ? WHERE number = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, note);
            pstmt.setString(2, number);
            pstmt.executeUpdate();
            System.out.println("Student updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error updating student: " + e.getMessage());
        }
    }

    public static List<Object[]> getDifferantialNoteStudentsByClass(String studentClass, String branch) {
        List<Object[]> students = new ArrayList<>();
        String sql = "SELECT number, name, class, note FROM Differantial WHERE class = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentClass);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                students.add(new Object[]{
                    rs.getString("number"),
                    rs.getString("name"),
                    branch,
                    rs.getString("note")
                });
            }
        } catch (SQLException e) {
            System.out.println("Error fetching students: " + e.getMessage());
        }
        return students;
    }

    public static void createProgramingTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Programing (\n"
                + "    number TEXT PRIMARY KEY,\n"
                + "    name TEXT NOT NULL,\n"
                + "    class TEXT NOT NULL,\n"
                + "    note TEXT NOT NULL,\n"
                + "    attendance TEXT NOT NULL DEFAULT '0'\n"
                + ");";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Programing table created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating Programing table: " + e.getMessage());
        }
    }

    public static void insertPrograming(String number, String name, String studentClass, String note, String attendance) {
        String sql = "INSERT INTO Programing (number, name, class, note, attendance) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, number);
            pstmt.setString(2, name);
            pstmt.setString(3, studentClass);
            pstmt.setString(4, note);
            pstmt.setString(5, attendance);
            pstmt.executeUpdate();
            System.out.println("Student added to Programing table successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding student to Programing table: " + e.getMessage());
        }
    }

    public static void updateProgramingAttedance(String number, String attendance) {
        String sql = "UPDATE Programing SET attendance = ? WHERE number = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, attendance);
            pstmt.setString(2, number);
            pstmt.executeUpdate();
            System.out.println("Student updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error updating student: " + e.getMessage());
        }
    }

    public static List<Object[]> getProgramingAttedanceStudentsByClass(String studentClass, String branch) {
        List<Object[]> students = new ArrayList<>();
        String sql = "SELECT number, name, class, attendance FROM Programing WHERE class = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentClass);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                students.add(new Object[]{
                    rs.getString("number"),
                    rs.getString("name"),
                    branch,
                    rs.getString("attendance")
                });
            }
        } catch (SQLException e) {
            System.out.println("Error fetching students: " + e.getMessage());
        }
        return students;
    }

    public static void updateProgramingNote(String number, String note) {
        String sql = "UPDATE Programing SET note = ? WHERE number = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, note);
            pstmt.setString(2, number);
            pstmt.executeUpdate();
            System.out.println("Student updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error updating student: " + e.getMessage());
        }
    }

    public static List<Object[]> getProgramingNoteStudentsByClass(String studentClass, String branch) {
        List<Object[]> students = new ArrayList<>();
        String sql = "SELECT number, name, class, note FROM Programing WHERE class = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentClass);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                students.add(new Object[]{
                    rs.getString("number"),
                    rs.getString("name"),
                    branch,
                    rs.getString("note")
                });
            }
        } catch (SQLException e) {
            System.out.println("Error fetching students: " + e.getMessage());
        }
        return students;
    }

    public static void createNumericTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Numeric (\n"
                + "    number TEXT PRIMARY KEY,\n"
                + "    name TEXT NOT NULL,\n"
                + "    class TEXT NOT NULL,\n"
                + "    note TEXT NOT NULL,\n"
                + "    attendance TEXT NOT NULL DEFAULT '0'\n"
                + ");";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Numeric table created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating Numeric table: " + e.getMessage());
        }
    }

    public static void insertNumeric(String number, String name, String studentClass, String note, String attendance) {
        String sql = "INSERT INTO Numeric (number, name, class, note, attendance) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, number);
            pstmt.setString(2, name);
            pstmt.setString(3, studentClass);
            pstmt.setString(4, note);
            pstmt.setString(5, attendance);
            pstmt.executeUpdate();
            System.out.println("Student added to Numeric table successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding student to Numeric table: " + e.getMessage());
        }
    }

    public static void updateNumericAttedance(String number, String attendance) {
        String sql = "UPDATE Numeric SET attendance = ? WHERE number = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, attendance);
            pstmt.setString(2, number);
            pstmt.executeUpdate();
            System.out.println("Student updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error updating student: " + e.getMessage());
        }
    }

    public static List<Object[]> getNumericAttedanceStudentsByClass(String studentClass, String branch) {
        List<Object[]> students = new ArrayList<>();
        String sql = "SELECT number, name, class, attendance FROM Numeric WHERE class = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentClass);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                students.add(new Object[]{
                    rs.getString("number"),
                    rs.getString("name"),
                    branch,
                    rs.getString("attendance")
                });
            }
        } catch (SQLException e) {
            System.out.println("Error fetching students: " + e.getMessage());
        }
        return students;
    }

    public static void updateNumericNote(String number, String note) {
        String sql = "UPDATE Numeric SET note = ? WHERE number = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, note);
            pstmt.setString(2, number);
            pstmt.executeUpdate();
            System.out.println("Student updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error updating student: " + e.getMessage());
        }
    }

    public static List<Object[]> getNumericNoteStudentsByClass(String studentClass, String branch) {
        List<Object[]> students = new ArrayList<>();
        String sql = "SELECT number, name, class, note FROM Numeric WHERE class = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentClass);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                students.add(new Object[]{
                    rs.getString("number"),
                    rs.getString("name"),
                    branch,
                    rs.getString("note")
                });
            }
        } catch (SQLException e) {
            System.out.println("Error fetching students: " + e.getMessage());
        }
        return students;
    }

    public static void createLinearTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Linear (\n"
                + "    number TEXT PRIMARY KEY,\n"
                + "    name TEXT NOT NULL,\n"
                + "    class TEXT NOT NULL,\n"
                + "    note TEXT NOT NULL,\n"
                + "    attendance TEXT NOT NULL DEFAULT '0'\n"
                + ");";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Numeric table created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating Numeric table: " + e.getMessage());
        }
    }

    public static void insertLinear(String number, String name, String studentClass, String note, String attendance) {
        String sql = "INSERT INTO Linear (number, name, class, note, attendance) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, number);
            pstmt.setString(2, name);
            pstmt.setString(3, studentClass);
            pstmt.setString(4, note);
            pstmt.setString(5, attendance);
            pstmt.executeUpdate();
            System.out.println("Student added to Linear table successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding student to Linear table: " + e.getMessage());
        }
    }

    public static void updateLinearAttedance(String number, String attendance) {
        String sql = "UPDATE Linear SET attendance = ? WHERE number = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, attendance);
            pstmt.setString(2, number);
            pstmt.executeUpdate();
            System.out.println("Student updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error updating student: " + e.getMessage());
        }
    }

    public static List<Object[]> getLinearAttedanceStudentsByClass(String studentClass, String branch) {
        List<Object[]> students = new ArrayList<>();
        String sql = "SELECT number, name, class, attendance FROM Linear WHERE class = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentClass);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                students.add(new Object[]{
                    rs.getString("number"),
                    rs.getString("name"),
                    branch,
                    rs.getString("attendance")
                });
            }
        } catch (SQLException e) {
            System.out.println("Error fetching students: " + e.getMessage());
        }
        return students;
    }

    public static void updateLinearNote(String number, String note) {
        String sql = "UPDATE Linear SET note = ? WHERE number = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, note);
            pstmt.setString(2, number);
            pstmt.executeUpdate();
            System.out.println("Student updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error updating student: " + e.getMessage());
        }
    }

    public static List<Object[]> getLinearNoteStudentsByClass(String studentClass, String branch) {
        List<Object[]> students = new ArrayList<>();
        String sql = "SELECT number, name, class, note FROM Linear WHERE class = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentClass);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                students.add(new Object[]{
                    rs.getString("number"),
                    rs.getString("name"),
                    branch,
                    rs.getString("note")
                });
            }
        } catch (SQLException e) {
            System.out.println("Error fetching students: " + e.getMessage());
        }
        return students;
    }
    

    public static void create() {
        SqlConnect.createTable();
        SqlConnect.createTeacherTable();
        SqlConnect.createPublicNoticeTable();
        SqlConnect.createConfirmedStudentLessonsTable();
        SqlConnect.createPrivateNoticeTable();
        SqlConnect.createMathTable();
        SqlConnect.createDifferantialTable();
        SqlConnect.createProgramingTable();
        SqlConnect.createNumericTable();
        SqlConnect.createLinearTable();
        StudentList.lecture();
        SqlConnect.insertTeacherUser("00", "ali", "00", 0, "Math");
        SqlConnect.insertTeacherUser("11", "mustafa", "11", 1, "Differantial");
        SqlConnect.insertTeacherUser("22", "kemal", "22", 2, "Programing");
        SqlConnect.insertTeacherUser("33", "ayşe", "33", 3, "Numeric");
        SqlConnect.insertTeacherUser("44", "hafsa", "44", 4, "Linear");
    }

}
