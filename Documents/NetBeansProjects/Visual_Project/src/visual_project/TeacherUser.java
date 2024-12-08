package visual_project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class TeacherUser {
    public TeacherUser(JFrame teacherFrame) {
        teacherFrame.setTitle("Teacher Page");
        teacherFrame.getContentPane().removeAll();

        
        JPanel btnpanel = new JPanel();
        btnpanel.setLayout(null);
        btnpanel.setBounds(5, 5, 150, 550);
        TitledBorder border1 = new TitledBorder("Menu");
        btnpanel.setBorder(border1);

        JButton noticebtn = new JButton("Notice");
        noticebtn.setBounds(15, 25, 120, 60);
        btnpanel.add(noticebtn);
        noticebtn.addActionListener(e ->{
            teacherFrame.getContentPane().removeAll();
            teacherFrame.add(btnpanel);
            teacherFrame.revalidate();
            teacherFrame.repaint();
            JOptionPane.showMessageDialog(teacherFrame, "You can publish the announcement with school permission.");

                
                String pin = JOptionPane.showInputDialog(teacherFrame, "Please enter your PIN");

                
                if (pin != null && pin.equals("61")) {  // Burada 1234 bir örnektir, gerçek bir PIN kontrolü yapılabilir.
                    JOptionPane.showMessageDialog(teacherFrame, "You can publish the announcement.");
                    JLabel notice=new JLabel("Please write a notice: ");
                    notice.setBounds(180,30,150,30);
                    teacherFrame.add(notice);
                    JTextArea noticearea=new JTextArea();
                    noticearea.setBounds(180,70,380,350);
                    teacherFrame.add(noticearea);
                    JButton publishedbtn=new JButton("Published");
                    publishedbtn.setBounds(430,440,120,30);
                    teacherFrame.add(publishedbtn);
                    publishedbtn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                    if(!noticearea.getText().isEmpty()){
                    int response = JOptionPane.showConfirmDialog(teacherFrame, "Do you want to publish this notice?\n "+noticearea.getText(), "Alert", JOptionPane.YES_NO_OPTION);
                    if(response==JOptionPane.YES_OPTION){
                    JOptionPane.showMessageDialog(teacherFrame, "You published!");
                    }else if (response == JOptionPane.NO_OPTION) {
                    
                    }
                    }
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(teacherFrame, "You do not have the authority to post announcements");
                }
                teacherFrame.revalidate();
                teacherFrame.repaint();
        });

        JButton attendance = new JButton("Attendance");
        attendance.setBounds(15, 100, 120, 60);
        btnpanel.add(attendance);
        attendance.addActionListener(e -> {
            // Burada absenteeism ekranı açılacak
            teacherFrame.getContentPane().removeAll();
            teacherFrame.add(btnpanel);
            String[] clas = {"P", "1", "2", "3", "4"};
            JComboBox<String> combo = new JComboBox<>(clas);
            combo.setBounds(450, 30, 100, 30);
            teacherFrame.add(combo);

            JLabel classeslbl = new JLabel("Choose a class:");
            classeslbl.setBounds(350, 30, 150, 30);
            teacherFrame.add(classeslbl);

            DefaultTableModel model = new DefaultTableModel(new String[]{"Number", "Name", "Surname","Lecture", "Attendance"}, 0);
            JTable noteTable = new JTable(model);
            JScrollPane noteScroll = new JScrollPane(noteTable);
            noteScroll.setBounds(180, 80, 370, 380);
            teacherFrame.add(noteScroll);

            JLabel searchlbl = new JLabel("Search number:");
            searchlbl.setBounds(180, 480, 150, 30);
            teacherFrame.add(searchlbl);

            JTextField searchField = new JTextField();
            searchField.setBounds(280, 480, 100, 30);
            teacherFrame.add(searchField);

            JLabel attendancelbl = new JLabel("Attendance Status:");
            attendancelbl.setBounds(180, 520, 150, 30);
            teacherFrame.add(attendancelbl);

            JCheckBox check1 = new JCheckBox("Continous");
            check1.setBounds(300, 520, 120, 30);
            JCheckBox check2 = new JCheckBox("Noncontinous");
            check2.setBounds(300, 550, 120, 30);

            ButtonGroup group = new ButtonGroup();
            group.add(check1);
            group.add(check2);

            teacherFrame.add(check1);
            teacherFrame.add(check2);

            JButton addbtn = new JButton("Add");
            addbtn.setBounds(400, 480, 110, 30);
            teacherFrame.add(addbtn);

            teacherFrame.revalidate();
            teacherFrame.repaint();
        });
        

        JButton notebtn = new JButton("Note");
        notebtn.setBounds(15, 175, 120, 60);
        btnpanel.add(notebtn);
        notebtn.addActionListener(e -> {
            teacherFrame.getContentPane().removeAll();
            teacherFrame.add(btnpanel);
            String[] clas = {"P", "1", "2", "3", "4"};
            JComboBox<String> combo = new JComboBox<>(clas);
            combo.setBounds(450, 30, 100, 30);
            teacherFrame.add(combo);

            JLabel classeslbl = new JLabel("Choose a class:");
            classeslbl.setBounds(350, 30, 150, 30);
            teacherFrame.add(classeslbl);

            DefaultTableModel model = new DefaultTableModel(new String[]{"Number", "Name", "Surname","Lecture","Notice"}, 0);
            JTable noteTable = new JTable(model);
            JScrollPane noteScroll = new JScrollPane(noteTable);
            noteScroll.setBounds(180, 80, 370, 380);
            teacherFrame.add(noteScroll);

            JLabel searchlbl = new JLabel("Search number:");
            searchlbl.setBounds(180, 480, 150, 30);
            teacherFrame.add(searchlbl);

            JTextField searchField = new JTextField();
            searchField.setBounds(280, 480, 100, 30);
            teacherFrame.add(searchField);

            JLabel notelbl = new JLabel("Note:");
            notelbl.setBounds(400, 480, 100, 30);
            teacherFrame.add(notelbl);

            JTextField noteField = new JTextField();
            noteField.setBounds(440, 480, 70, 30);
            teacherFrame.add(noteField);

            JButton addbtn = new JButton("Add");
            addbtn.setBounds(400, 530, 110, 30);
            teacherFrame.add(addbtn);

            teacherFrame.revalidate();
            teacherFrame.repaint();
        });
        

        JButton courseregistrationbtn = new JButton("Course Registration");
        courseregistrationbtn.setBounds(15, 250, 120, 60);
        btnpanel.add(courseregistrationbtn);
        courseregistrationbtn.addActionListener(e ->{
        teacherFrame.getContentPane().removeAll();
        teacherFrame.add(btnpanel);
        
            JLabel avabilitylbl=new JLabel("Please select to avaible days:");
            avabilitylbl.setBounds(200,50,200,30);
            teacherFrame.add(avabilitylbl);
            String[] avability={"Monday","Thusday","Wednesday","Thursday","Friday"};
            JComboBox<String> combo=new JComboBox<>(avability);
            combo.setBounds(400,50,110,30);
            teacherFrame.add(combo);
            JLabel avabilityhours=new JLabel("Please select to avaible hours:");
            avabilityhours.setBounds(200,90,200,40);
            teacherFrame.add(avabilityhours);
            String[] hours={"09.00-11.00","11.00-13.00","13.00-15.00","15.00-17.00"};
            JList<String> list=new JList<>(hours);
            list.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            list.setVisibleRowCount(4);
            JScrollPane scroll=new JScrollPane(list);
            scroll.setBounds(400,90,110,40);
            JLabel noticelbl=new JLabel("You must select three avaible day and hours!");
            noticelbl.setBounds(200,150,350,30);
            
            DefaultListModel model=new DefaultListModel();
            JList<String> list2=new JList<>(model);
            list2.setBounds(200,220,300,200);
            JButton selectbtn=new JButton("Select");
            selectbtn.setBounds(450,180,100,30);
            selectbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.addElement("Day: "+combo.getSelectedItem()+" Hour: "+list.getSelectedValue());
            }
        });
            JButton deletebtn=new JButton("Delete");
            deletebtn.setBounds(450,450,100,30);
            deletebtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(list2.isSelectionEmpty()){
                JOptionPane.showMessageDialog(teacherFrame, "You must select an object");
                }else{
                    int select=list2.getSelectedIndex();
                    model.remove(select);
                }
            }
        });
            JButton approvebtn=new JButton("Aprove");
            approvebtn.setBounds(450,520,100,30);
            teacherFrame.add(approvebtn);
            teacherFrame.add(deletebtn);
            teacherFrame.add(selectbtn);
            teacherFrame.add(list2);
            teacherFrame.add(noticelbl);
            teacherFrame.add(scroll);
            teacherFrame.revalidate();
            teacherFrame.repaint();
        });

        JButton programebtn = new JButton("Programe");
        programebtn.setBounds(15, 325, 120, 60);
        btnpanel.add(programebtn);
        programebtn.addActionListener(e ->{
        teacherFrame.getContentPane().removeAll();
        teacherFrame.add(btnpanel);
        JOptionPane.showMessageDialog(teacherFrame, "You can see the program after the teacher and student approve it.");
                DefaultTableModel model = new DefaultTableModel(new String[]{"Day","Hour","Lecture"}, 0);
                JTable noteTable = new JTable(model);
                JScrollPane noteScroll = new JScrollPane(noteTable);
                noteScroll.setBounds(180, 80, 370, 380);
                teacherFrame.add(noteScroll);
        teacherFrame.revalidate();
        teacherFrame.repaint();
        
        });

        JButton personalNoticebtn = new JButton("Personal Notice");
        personalNoticebtn.setBounds(15, 400, 120, 60);
        btnpanel.add(personalNoticebtn);
        personalNoticebtn.addActionListener(e ->{
        teacherFrame.getContentPane().removeAll();
        teacherFrame.add(btnpanel);
            String[] clas = {"P", "1", "2", "3", "4"};
            JComboBox<String> combo = new JComboBox<>(clas);
            combo.setBounds(450, 30, 100, 30);
            teacherFrame.add(combo);
            JLabel classeslbl = new JLabel("Choose a class:");
            classeslbl.setBounds(350, 30, 150, 30);
            teacherFrame.add(classeslbl);
            JLabel noticelbl=new JLabel("Add a notice:");
            noticelbl.setBounds(200,70,120,30);
            teacherFrame.add(noticelbl);
            JTextArea noticearea=new JTextArea();
            noticearea.setBounds(200,110,350,300);
            teacherFrame.add(noticearea);
            JButton addbtn=new JButton("Published");
            addbtn.setBounds(450,420,100,30);
            teacherFrame.add(addbtn);
            addbtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!noticearea.getText().isEmpty()){
                    String selected=(String) combo.getSelectedItem();
                    int response = JOptionPane.showConfirmDialog(teacherFrame, "Do you want to publish this class and this notice?"+"\nClass: "+selected+"\nNotice: "+noticearea.getText(), "Alert", JOptionPane.YES_NO_OPTION);
                    if(response==JOptionPane.YES_OPTION){
                    JOptionPane.showMessageDialog(teacherFrame, "You published!");
                    }else if (response == JOptionPane.NO_OPTION) {
                    
                    }
                    }
            }
        });
        teacherFrame.revalidate();
        teacherFrame.repaint();
      });

        JButton removeBtn = new JButton("Remove");
        removeBtn.setBounds(15, 475, 120, 60);
        btnpanel.add(removeBtn);
        removeBtn.addActionListener(e -> {
            teacherFrame.getContentPane().removeAll();
            new LogIn(teacherFrame); 
            teacherFrame.revalidate();                 
            teacherFrame.repaint();   
        });
        teacherFrame.add(btnpanel);

        teacherFrame.setVisible(true);
    }
}
