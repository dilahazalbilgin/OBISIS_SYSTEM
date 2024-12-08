package visual_project;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class StudentUser {
    public StudentUser(JFrame studentFrame) {
        studentFrame.setTitle("Student Page");
        studentFrame.getContentPane().removeAll();

        JPanel btnpanel = new JPanel();
        btnpanel.setLayout(null);
        btnpanel.setBounds(5, 5, 150, 550);
        TitledBorder border1 = new TitledBorder("Menu");
        btnpanel.setBorder(border1);
        studentFrame.add(btnpanel);
        
        JButton noticebtn = new JButton("Notice");
        noticebtn.setBounds(15, 25, 120, 60);
        btnpanel.add(noticebtn);
        noticebtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                studentFrame.getContentPane().removeAll();
                studentFrame.add(btnpanel);
                studentFrame.revalidate();
                studentFrame.repaint();
                JLabel lbl=new JLabel("Notice");
                lbl.setFont(new Font("Notice",Font.BOLD,20));
                lbl.setBounds(340,30,100,30);
                studentFrame.add(lbl);
                DefaultListModel<String> model = new DefaultListModel<>();
                JList<String> list = new JList<>(model);
                JScrollPane scrollPane = new JScrollPane(list);
                scrollPane.setBounds(200, 80, 330, 450);
                studentFrame.add(scrollPane);
                studentFrame.revalidate();
                studentFrame.repaint();
            }
        });
        
        JButton attendancebtn = new JButton("Attendance");
        attendancebtn.setBounds(15, 100, 120, 60);
        btnpanel.add(attendancebtn);
        attendancebtn.addActionListener(e -> {
                studentFrame.getContentPane().removeAll();
                studentFrame.add(btnpanel);
                studentFrame.revalidate();
                studentFrame.repaint();
                
            DefaultTableModel model = new DefaultTableModel(new String[]{"Number", "Name", "Surname","Lecture", "Attendance"}, 0);
            JTable noteTable = new JTable(model);
            JScrollPane noteScroll = new JScrollPane(noteTable);
            noteScroll.setBounds(180, 80, 370, 380);
            studentFrame.add(noteScroll);
        });
        
        JButton notebtn = new JButton("Note");
        notebtn.setBounds(15, 175, 120, 60);
        btnpanel.add(notebtn);
        notebtn.addActionListener(e -> {
                studentFrame.getContentPane().removeAll();
                studentFrame.add(btnpanel);
                studentFrame.revalidate();
                studentFrame.repaint();
                
            DefaultTableModel model = new DefaultTableModel(new String[]{"Number", "Name", "Surname","Lecture","Note"}, 0);
            JTable noteTable = new JTable(model);
            JScrollPane noteScroll = new JScrollPane(noteTable);
            noteScroll.setBounds(180, 80, 370, 380);
            studentFrame.add(noteScroll);
        });
        
        JButton courseregistrationbtn = new JButton("Course Registration");
        courseregistrationbtn.setBounds(15, 250, 120, 60);
        btnpanel.add(courseregistrationbtn);
        courseregistrationbtn.addActionListener(e -> {
                studentFrame.getContentPane().removeAll();
                studentFrame.add(btnpanel);
                
                
            String[] clas = {"P", "1", "2", "3", "4"};
            JComboBox<String> combo = new JComboBox<>(clas);
            combo.setBounds(450, 30, 100, 30);
            studentFrame.add(combo);

            JLabel classeslbl = new JLabel("Choose a class:");
            classeslbl.setBounds(350, 30, 150, 30);
            studentFrame.add(classeslbl);
            
            JLabel searchlbl=new JLabel("Search Lesson:");
            searchlbl.setBounds(200,80,120,30);
            studentFrame.add(searchlbl);
            JTextField searchField =new JTextField();
            searchField.setBounds(300,80,120,30);
            studentFrame.add(searchField);
            JButton searchbtn=new JButton("Search");
            searchbtn.setBounds(450,80,100,30);
            studentFrame.add(searchbtn);
            
            DefaultTableModel model = new DefaultTableModel(new String[]{"Number","Lecture","Hour","Select"}, 0);
            JTable noteTable = new JTable(model);
            JScrollPane noteScroll = new JScrollPane(noteTable);
            noteScroll.setBounds(180, 130, 370, 380);
            studentFrame.add(noteScroll);
            
            JButton selectbtn=new JButton("Select");
            selectbtn.setBounds(450,520,100,30);
            studentFrame.add(selectbtn);
            selectbtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(studentFrame, "Do you want to confirm this programe");
                    }
                });
                studentFrame.revalidate();
                studentFrame.repaint();
            
        });
        
        JButton programebtn = new JButton("Programe");
        programebtn.setBounds(15, 325, 120, 60);
        btnpanel.add(programebtn);
        programebtn.addActionListener(e -> {
                studentFrame.getContentPane().removeAll();
                studentFrame.add(btnpanel);
                JOptionPane.showMessageDialog(studentFrame, "You can see the program after the teacher and student approve it.");
                DefaultTableModel model = new DefaultTableModel(new String[]{"Day","Hour","Lecture"}, 0);
                JTable noteTable = new JTable(model);
                JScrollPane noteScroll = new JScrollPane(noteTable);
                noteScroll.setBounds(180, 80, 370, 380);
                studentFrame.add(noteScroll);
                
                studentFrame.revalidate();
                studentFrame.repaint();
        });
        
        JButton personalNoticebtn = new JButton("Personal Notice");
        personalNoticebtn.setBounds(15, 400, 120, 60);
        btnpanel.add(personalNoticebtn);
        personalNoticebtn.addActionListener(e -> {
                studentFrame.getContentPane().removeAll();
                studentFrame.add(btnpanel);
                JLabel lbl=new JLabel("Personal Notice");
                lbl.setFont(new Font("Personal Notice",Font.BOLD,20));
                lbl.setBounds(300,30,200,30);
                studentFrame.add(lbl);
                DefaultListModel<String> model = new DefaultListModel<>();
                JList<String> list = new JList<>(model);
                JScrollPane scrollPane = new JScrollPane(list);
                scrollPane.setBounds(200, 80, 330, 450);
                studentFrame.add(scrollPane);
                
                studentFrame.revalidate();
                studentFrame.repaint();
        });
        
        JButton removeBtn = new JButton("Remove");
        removeBtn.setBounds(15, 475, 120, 60);
        btnpanel.add(removeBtn);
        removeBtn.addActionListener(e -> {
            studentFrame.getContentPane().removeAll();
            studentFrame.revalidate();                 
            studentFrame.repaint();        
            new LogIn(studentFrame);
        });

        studentFrame.revalidate();
        studentFrame.repaint();
    }
}
