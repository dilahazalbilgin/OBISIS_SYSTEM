package visual_project;

import static visual_project.SQLiteConnection.connect;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class Insert {
    
    public static void insertTeacherUser(String number, String name, String password, int code) {
    String sql = "INSERT INTO TeacherUsers(number, name, password, code) VALUES(?, ?, ?, ?)";

    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
