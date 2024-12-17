package visual_project;

import java.awt.Component;
import java.awt.Font;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class StudentUser {

    public StudentUser(JFrame studentFrame, String number, String name, String clases) {
        studentFrame.setTitle("Student Page");
        studentFrame.getContentPane().removeAll();

        List<Object[]> confirmedLessons = new ArrayList<>();

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
                JLabel lbl = new JLabel("Notice");
                lbl.setFont(new Font("Notice", Font.BOLD, 20));
                lbl.setBounds(340, 30, 100, 30);
                studentFrame.add(lbl);
                DefaultListModel<String> model = new DefaultListModel<>();
                JList<String> list = new JList<>(model);
                JScrollPane scrollPane = new JScrollPane(list);
                scrollPane.setBounds(200, 80, 330, 450);

                model.addElement("Notice 1: School will be closed tomorrow.".toUpperCase(Locale.ITALY));
                model.addElement("Notice 2: Parent-teacher meetings scheduled for Friday.".toUpperCase(Locale.ITALY));
                model.addElement("Notice 3: New library books available for checkout.".toUpperCase(Locale.ITALY));

                list.addListSelectionListener(e1 -> {
                    if (!e1.getValueIsAdjusting()) {
                        String selectedNotice = list.getSelectedValue();
                        if (selectedNotice != null) {
                            JOptionPane.showMessageDialog(studentFrame, selectedNotice, "Notice", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                });

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

            String[] columnNames = {"Number", "Name", "Class", "Lecture", "Attendance"};
            Object[][] data = {
            {number, name, clases, "Math", "Continous"},
            {number, name, clases, "Linear Algebra", "Continous"},
            {number, name, clases, "Differential Equations", "NotContinous"},
            {number, name, clases, "Programming", "NotContinous"},
            {number, name, clases, "Numerical Analysis", "Continous"}
        };

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(180, 80, 370, 380);
        studentFrame.add(scrollPane);
        
         studentFrame.revalidate();
        studentFrame.repaint();
        });

        JButton notebtn = new JButton("Note");
        notebtn.setBounds(15, 175, 120, 60);
        btnpanel.add(notebtn);
        notebtn.addActionListener(e -> {
            studentFrame.getContentPane().removeAll();
            studentFrame.add(btnpanel);

            DefaultTableModel model = new DefaultTableModel(new String[]{"Number", "Name", "Class", "Lecture", "Note"}, 0);
            JTable noteTable = new JTable(model);
            JScrollPane noteScroll = new JScrollPane(noteTable);
            noteScroll.setBounds(180, 80, 370, 380);
            studentFrame.add(noteScroll);

            model.addRow(new Object[]{number, name, clases, "Math", "45"});
            model.addRow(new Object[]{number, name, clases, "Linear", "73"});
            model.addRow(new Object[]{number, name, clases, "Differantial", "30"});
            model.addRow(new Object[]{number, name, clases, "Programing", "70"});
            model.addRow(new Object[]{number, name, clases, "Numeric", "90"});

            studentFrame.revalidate();
            studentFrame.repaint();
        });

        HashMap<String, Boolean> selectionMap = new HashMap<>();

        JButton courseregistrationbtn = new JButton("Course Registration");
        courseregistrationbtn.setBounds(15, 250, 120, 60);
        btnpanel.add(courseregistrationbtn);

        courseregistrationbtn.addActionListener(e -> {
            studentFrame.getContentPane().removeAll();
            studentFrame.add(btnpanel);

            JLabel searchlbl = new JLabel("Search Lesson:");
            searchlbl.setBounds(200, 50, 120, 30);
            studentFrame.add(searchlbl);
            JTextField searchField = new JTextField();
            searchField.setBounds(300, 50, 120, 30);
            studentFrame.add(searchField);

            DefaultTableModel model = new DefaultTableModel(new String[]{"Number", "Lecture", "Hour", "Day", "isSelected"}, 0);
            JTable selectProgrameTable = new JTable(model);
            selectProgrameTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            JScrollPane selectProgrameScroll = new JScrollPane(selectProgrameTable);
            selectProgrameScroll.setBounds(180, 130, 370, 380);
            studentFrame.add(selectProgrameScroll);

            Object[][] allRows = {
                {"61", "Math", "12.00-14.00", "Monday", false},
                {"61", "Math", "14.00-16.00", "Friday", false},
                {"61", "Math", "11.00-13.00", "Monday", false},
                {"61", "Calculus", "12.00-14.00", "Monday", false},
                {"61", "Calculus", "09.00-11.00", "Monday", false},
                {"61", "Diff", "12.00-14.00", "Friday", false},
                {"61", "Programming", "10.00-12.00", "Monday", false},
                {"61", "Programming", "12.00-14.00", "Monday", false},
                {"61", "Numeric", "12.00-14.00", "Wednesday", false},
                {"61", "Numeric", "11.00-13.00", "Tuesday", false},
                {"61", "Numeric", "09.00-11.00", "Thursday", false},
                {"61", "Numeric", "12.00-14.00", "Monday", false},
                {"61", "Linear", "08.00-10.00", "Monday", false},
                {"61", "Linear", "11.00-13.00", "Thursday", false},
                {"61", "Linear", "09.00-11.00", "Wednesday", false},
                {"61", "Linear", "12.00-14.00", "Thursday", false}
            };

            for (Object[] row : allRows) {
                model.addRow(row);
            }
            selectProgrameTable.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(new JCheckBox()));

            selectProgrameTable.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    JCheckBox checkBox = new JCheckBox();
                    checkBox.setSelected(value != null && (Boolean) value);
                    checkBox.setHorizontalAlignment(SwingConstants.CENTER);
                    return checkBox;
                }
            });

            JButton searchbtn = new JButton("Search");
            searchbtn.setBounds(450, 50, 100, 30);
            studentFrame.add(searchbtn);
            searchbtn.addActionListener(e1 -> {
                String searchTerm = searchField.getText().toLowerCase();

                // Tüm satırları sakla
                for (int i = 0; i < model.getRowCount(); i++) {
                    String key = (String) model.getValueAt(i, 1);
                    Boolean isSelected = (Boolean) model.getValueAt(i, 4);
                    selectionMap.put(key, isSelected);
                }

                model.setRowCount(0);
                for (Object[] row : allRows) {
                    String lecture = (String) row[1];
                    if (lecture.toLowerCase().contains(searchTerm)) {
                        row[4] = selectionMap.getOrDefault(lecture, false);
                        model.addRow(row);
                    }
                }
                selectProgrameTable.revalidate();
                selectProgrameTable.repaint();
            });

            JButton selectbtn = new JButton("Select");
            selectbtn.setBounds(450, 520, 100, 30);
            studentFrame.add(selectbtn);
            selectbtn.addActionListener(e1 -> {
                StringBuilder selectedLessons = new StringBuilder("You have selected the following lessons:\n");
                boolean hasSelection = false;
                confirmedLessons.clear();

                for (int rowIndex = 0; rowIndex < model.getRowCount(); rowIndex++) {
                    Boolean isSelected = (Boolean) model.getValueAt(rowIndex, 4);
                    if (isSelected != null && isSelected) {
                        hasSelection = true;
                        Object lecture = model.getValueAt(rowIndex, 1);
                        Object hour = model.getValueAt(rowIndex, 2);
                        Object day = model.getValueAt(rowIndex, 3);

                        confirmedLessons.add(new Object[]{day, hour, lecture});

                        selectedLessons.append("- ").append(lecture)
                                .append(" (").append(hour).append(", ").append(day).append(")\n");
                    }
                }

                if (hasSelection) {
                    int confirmation = JOptionPane.showConfirmDialog(
                            studentFrame,
                            selectedLessons.toString() + "\nDo you confirm your selection?",
                            "Confirm Selection",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (confirmation == JOptionPane.YES_OPTION) {
                        JOptionPane.showMessageDialog(
                                studentFrame,
                                "Selection confirmed.You can check Programe Button\nThank you!",
                                "Confirmation",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    } else {
                        JOptionPane.showMessageDialog(
                                studentFrame,
                                "Selection was not confirmed.",
                                "Canceled",
                                JOptionPane.WARNING_MESSAGE
                        );
                    }
                } else {
                    JOptionPane.showMessageDialog(
                            studentFrame,
                            "No lessons selected. Please select at least one lesson.",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE
                    );
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

            DefaultTableModel model = new DefaultTableModel(new String[]{"Day", "Hour", "Lecture"}, 0);
            JTable noteTable = new JTable(model);
            JScrollPane noteScroll = new JScrollPane(noteTable);
            noteScroll.setBounds(180, 80, 370, 380);
            studentFrame.add(noteScroll);

            for (Object[] lesson : confirmedLessons) {
                model.addRow(lesson);
            }

            studentFrame.revalidate();
            studentFrame.repaint();
        });

        JButton personalNoticebtn = new JButton("Personal Notice");
        personalNoticebtn.setBounds(15, 400, 120, 60);
        btnpanel.add(personalNoticebtn);
        personalNoticebtn.addActionListener(e -> {
            studentFrame.getContentPane().removeAll();
            studentFrame.add(btnpanel);
            JLabel lbl = new JLabel("Personal Notice");
            lbl.setFont(new Font("Personal Notice", Font.BOLD, 20));
            lbl.setBounds(300, 30, 200, 30);
            studentFrame.add(lbl);
            DefaultListModel<String> model = new DefaultListModel<>();
            JList<String> list = new JList<>(model);
            JScrollPane scrollPane = new JScrollPane(list);
            scrollPane.setBounds(200, 80, 330, 450);
            studentFrame.add(scrollPane);

            model.addElement("Notice 1: School will be closed tomorrow.".toUpperCase(Locale.ITALY));
            model.addElement("Notice 2: Parent-teacher meetings scheduled for Friday.".toUpperCase(Locale.ITALY));
            model.addElement("Notice 3: New library books available for checkout.".toUpperCase(Locale.ITALY));

            list.addListSelectionListener(e1 -> {
                if (!e1.getValueIsAdjusting()) {
                    String selectedNotice = list.getSelectedValue();
                    if (selectedNotice != null) {
                        JOptionPane.showMessageDialog(studentFrame, selectedNotice, "Notice", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            });

            studentFrame.revalidate();
            studentFrame.repaint();
        });

        JButton removeBtn = new JButton("Remove");
        removeBtn.setBounds(15, 475, 120, 60);
        btnpanel.add(removeBtn);
        removeBtn.addActionListener(e -> {
            studentFrame.getContentPane().removeAll();
            new LogIn(studentFrame);
            studentFrame.revalidate();
            studentFrame.repaint();
        });

    }
}
