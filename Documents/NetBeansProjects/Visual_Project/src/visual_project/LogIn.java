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

public class LogIn {
    public LogIn(JFrame logFrame){
    logFrame.getContentPane().removeAll();

        JLabel logInlbl = new JLabel("LogIn");
        logInlbl.setFont(new Font("LogIn", Font.BOLD, 25));
        logInlbl.setBounds(255, 80, 120, 50);

        JLabel emaillbl = new JLabel("Email:");
        emaillbl.setBounds(110, 150, 100, 40);
        emaillbl.setFont(new Font("Email", Font.BOLD, 15));

        JTextField emailtext = new JTextField();
        emailtext.setBounds(200, 150, 220, 40);

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
                logFrame.getContentPane().removeAll();
                if (!codetext.getText().isEmpty()) {
                    new TeacherUser(logFrame);
                } else {
                    new StudentUser(logFrame);                    
                }
                logFrame.revalidate();
                logFrame.repaint();
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
        logFrame.add(emailtext);
        logFrame.add(emaillbl);
        logFrame.add(logInlbl);
        logFrame.revalidate();
        logFrame.repaint();
    }
}
