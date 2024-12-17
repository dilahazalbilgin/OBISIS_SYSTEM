package visual_project;

import java.awt.Font;
import javax.swing.*;

public class Visual_Project {

    public static void main(String[] args) {

        SQLiteConnection.createTable();
        SQLiteConnection.createTeacherTable();
        SQLiteConnection.insertTeacherUser("00", "ali", "00", 0, "Math");
        SQLiteConnection.insertTeacherUser("11", "mustafa", "11", 1, "Differantial");
        SQLiteConnection.insertTeacherUser("22", "kemal", "22", 2, "Programing");
        SQLiteConnection.insertTeacherUser("33", "ayşe", "33", 3, "Numeric");
        SQLiteConnection.insertTeacherUser("44", "hafsa", "44", 4, "Linear");

        JFrame mainframe = new JFrame("Obisis Project");
        mainframe.setSize(600, 630);
        mainframe.setLayout(null);
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel obsislbl = new JLabel("OBISIS");
        obsislbl.setFont(new Font("OBISIS", Font.BOLD, 30));
        obsislbl.setBounds(240, 50, 150, 30);

        new LogIn(mainframe);

        mainframe.setVisible(true);
    }
}
