package visual_project;

import java.awt.Font;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ForgetPassword {

    public ForgetPassword(JFrame forgetPassFrame) {
        forgetPassFrame.getContentPane().removeAll();

        JLabel numberlbl = new JLabel("Number:");
        numberlbl.setBounds(110, 150, 100, 40);
        numberlbl.setFont(new Font("Number", Font.BOLD, 15));

        JTextField numbertext = new JTextField();
        numbertext.setBounds(200, 150, 220, 40);

        JLabel passwordlbl = new JLabel("New Password:");
        passwordlbl.setBounds(110, 210, 150, 40);
        passwordlbl.setFont(new Font("Password", Font.BOLD, 15));

        JPasswordField passwordtext = new JPasswordField();
        passwordtext.setBounds(250, 210, 170, 40);

        JLabel passwordlbl2 = new JLabel("Again Password:");
        passwordlbl2.setBounds(110, 270, 150, 40);
        passwordlbl2.setFont(new Font("Code", Font.BOLD, 15));

        JPasswordField passwordtext2 = new JPasswordField();
        passwordtext2.setBounds(250, 270, 170, 40);

        JButton resetbtn = new JButton("Reset");
        resetbtn.setBounds(210, 350, 150, 40);
        resetbtn.addActionListener(e -> {
            String number = numbertext.getText().trim();
            String newPassword = new String(passwordtext.getPassword()).trim();
            String confirmPassword = new String(passwordtext2.getPassword()).trim();

            if (number.isEmpty()) {
                JOptionPane.showMessageDialog(forgetPassFrame, "You must enter the number");
            } else if (!userExists(number)) {
                JOptionPane.showMessageDialog(forgetPassFrame, "This number is not registered");
            } else if (newPassword.isEmpty() || !newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(forgetPassFrame, "Passwords do not match. Please try again.");
            } else {
                resetPassword(number, newPassword);
                JOptionPane.showMessageDialog(forgetPassFrame, "Password has been reset successfully");

                new LogIn(forgetPassFrame);
                forgetPassFrame.revalidate();
                forgetPassFrame.repaint();
            }
        });

        JLabel backLabel = new JLabel("←");
        backLabel.setBounds(10, 10, 50, 50);
        backLabel.setFont(new Font("Back", Font.BOLD, 30));
        backLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        backLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new LogIn(forgetPassFrame);
                forgetPassFrame.revalidate();
                forgetPassFrame.repaint();
            }
        });

        forgetPassFrame.add(backLabel);

        forgetPassFrame.add(resetbtn);
        forgetPassFrame.add(passwordtext2);
        forgetPassFrame.add(passwordlbl2);
        forgetPassFrame.add(passwordlbl);
        forgetPassFrame.add(passwordtext);
        forgetPassFrame.add(numbertext);
        forgetPassFrame.add(numberlbl);

        forgetPassFrame.revalidate();
        forgetPassFrame.repaint();
    }

    private boolean userExists(String number) {
        try (Connection conn = SQLiteConnection.connect()) {
            String query = "SELECT COUNT(*) FROM users WHERE number = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, number);
                return pstmt.executeQuery().getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void resetPassword(String number, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE number = ?";

        try (Connection conn = SQLiteConnection.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newPassword);
            pstmt.setString(2, number);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
