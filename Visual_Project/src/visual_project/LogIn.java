package visual_project;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogIn {

    public LogIn(JFrame logFrame) {
        logFrame.getContentPane().removeAll();

        JLabel logInlbl = new JLabel("LogIn");
        logInlbl.setFont(new Font("LogIn", Font.BOLD, 25));
        logInlbl.setBounds(255, 80, 120, 50);

        JLabel numberllbl = new JLabel("Number:");
        numberllbl.setBounds(110, 150, 100, 40);
        numberllbl.setFont(new Font("Number", Font.BOLD, 15));

        JTextField numbertext = new JTextField();
        numbertext.setBounds(200, 150, 220, 40);

        JLabel passwordlbl = new JLabel("Password:");
        passwordlbl.setBounds(110, 210, 100, 40);
        passwordlbl.setFont(new Font("Password", Font.BOLD, 15));

        JPasswordField passwordtext = new JPasswordField();
        passwordtext.setBounds(200, 210, 220, 40);

        JLabel kodlbl = new JLabel("Code:");
        kodlbl.setBounds(110, 270, 100, 40);
        kodlbl.setFont(new Font("Code", Font.BOLD, 15));

        JLabel kodlbl1 = new JLabel("(If you are teacher)");
        kodlbl1.setBounds(110, 290, 150, 40);
        kodlbl1.setFont(new Font("Code", Font.PLAIN, 12));

        JTextField codetext = new JTextField();
        codetext.setBounds(250, 270, 80, 40);

        JButton logInbtn = new JButton("LogIn");
        logInbtn.setBounds(230, 350, 150, 40);
        logInbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String number = numbertext.getText();
                String password = new String(passwordtext.getPassword());
                String teacherName = "";

                if (number.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(logFrame, "Empty number or password");
                } else if (!codetext.getText().isEmpty()) {
                    int code = Integer.parseInt(codetext.getText());
                    if (validateTeacher(number, password, code)) {
                        String[] teacherInfo = getTeacherInfo(number, password, code);
                        if (teacherInfo != null) {
                            String name = teacherInfo[0];
                            String branch = teacherInfo[1];

                            logFrame.getContentPane().removeAll();
                            new TeacherUser(logFrame, branch, name);
                            logFrame.revalidate();
                            logFrame.repaint();
                        } else {
                            JOptionPane.showMessageDialog(logFrame, "Unable to retrieve teacher information.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(logFrame, "Invalid teacher credentials or code");
                    }
                } else if (validateStudent(number, password)) {
                    String[] userInfo = getStudentInfo(number, password);
                    if (userInfo != null) {
                        logFrame.getContentPane().removeAll();
                        new StudentUser(logFrame, userInfo[0], userInfo[1], userInfo[2]);
                        logFrame.revalidate();
                        logFrame.repaint();
                    }
                } else {
                    JOptionPane.showMessageDialog(logFrame, "Invalid number or password");
                }
            }
        });

        JLabel forgotPasswordLabel = new JLabel("<html><a href=''>Forgot Password</a></html>");
        forgotPasswordLabel.setBounds(370, 400, 200, 20);
        forgotPasswordLabel.setFont(new Font("Code", Font.PLAIN, 12));
        forgotPasswordLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        forgotPasswordLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JOptionPane.showMessageDialog(logFrame, "Do you want to reset the password?");
                new ForgetPassword(logFrame);
            }
        });

        JLabel SignUpLabel = new JLabel("<html><a href=''>If you don't have an account click!</a></html>");
        SignUpLabel.setBounds(370, 560, 250, 20);
        SignUpLabel.setFont(new Font("SignUp", Font.PLAIN, 12));
        SignUpLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        SignUpLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new SignUp(logFrame);
            }
        });

        logFrame.add(SignUpLabel);
        logFrame.add(forgotPasswordLabel);
        logFrame.add(logInbtn);
        logFrame.add(codetext);
        logFrame.add(kodlbl1);
        logFrame.add(kodlbl);
        logFrame.add(passwordtext);
        logFrame.add(passwordlbl);
        logFrame.add(numbertext);
        logFrame.add(numberllbl);
        logFrame.add(logInlbl);
        logFrame.revalidate();
        logFrame.repaint();

    }

    private boolean validateTeacher(String number, String password, int code) {
        try (Connection conn = SqlConnect.connect()) {
            String query = "SELECT password, code FROM TeacherUsers WHERE number = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, number);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getString("password").equals(password) && rs.getInt("code") == code;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean validateStudent(String number, String password) {
        try (Connection conn = SqlConnect.connect()) {
            String query = "SELECT password FROM users WHERE number = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, number);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getString("password").equals(password);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String[] getStudentInfo(String number, String password) {
        try (Connection conn = SqlConnect.connect()) {
            String query = "SELECT number, name, class FROM users WHERE number = ? AND password = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, number);
                pstmt.setString(2, password);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return new String[]{rs.getString("number"), rs.getString("name"), rs.getString("class")};
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String[] getTeacherInfo(String number, String password, int code) {
        try (Connection conn = SqlConnect.connect()) {
            String query = "SELECT name, branch FROM TeacherUsers WHERE number = ? AND password = ? AND code = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, number);
                pstmt.setString(2, password);
                pstmt.setInt(3, code);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return new String[]{rs.getString("name"), rs.getString("branch")};
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

}
