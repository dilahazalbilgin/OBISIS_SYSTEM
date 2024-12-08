package visual_project;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SignUp {
    public SignUp(JFrame signUpFrame){
        signUpFrame.getContentPane().removeAll();
        
        JLabel backLabel = new JLabel("←");
        backLabel.setBounds(10, 10, 50, 50);
        backLabel.setFont(new Font("Back", Font.BOLD, 30));
        backLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        backLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new LogIn(signUpFrame); 
                signUpFrame.revalidate(); 
                signUpFrame.repaint();   
            }
        });
        
        JLabel logInlbl = new JLabel("SignUp");
        logInlbl.setFont(new Font("SignUp", Font.BOLD, 25));
        logInlbl.setBounds(255, 80, 120, 50);

        JLabel numberlbl = new JLabel("Number:");
        numberlbl.setBounds(110, 150, 100, 40);
        numberlbl.setFont(new Font("Number", Font.BOLD, 15));

        JTextField emailtext = new JTextField();
        emailtext.setBounds(200, 150, 220, 40);

        JLabel passwordlbl = new JLabel("Password:");
        passwordlbl.setBounds(110, 210, 100, 40);
        passwordlbl.setFont(new Font("Password", Font.BOLD, 15));
        
        JPasswordField passwordtext = new JPasswordField();
        passwordtext.setBounds(200, 210, 220, 40);
        
        JButton registerBtn=new JButton("Register");
        registerBtn.setBounds(230,270,120,40);
        registerBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(signUpFrame, "Account Created");
                new LogIn(signUpFrame); 
                signUpFrame.revalidate(); 
                signUpFrame.repaint();   
            }
        });

        signUpFrame.add(registerBtn);
        signUpFrame.add(passwordtext);
        signUpFrame.add(passwordlbl);
        signUpFrame.add(emailtext);
        signUpFrame.add(numberlbl);
        signUpFrame.add(logInlbl);
        signUpFrame.add(backLabel);       
        signUpFrame.revalidate();
        signUpFrame.repaint();
        JOptionPane.showMessageDialog(backLabel,"If you are a teacher, please contact the school to obtain a code.");
    }
}
