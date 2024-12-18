package visual_project;

import java.awt.Font;
import javax.swing.*;

public class Visual_Project {

    public static void main(String[] args) {
        SqlConnect.create();

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
