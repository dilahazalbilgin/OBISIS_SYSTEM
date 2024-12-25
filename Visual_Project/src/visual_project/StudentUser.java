package visual_project;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import visual_project.SqlConnect;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.File;
import javax.swing.table.DefaultTableModel;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

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

                List<String> notices = SqlConnect.fetchPublicNotices();
                for (String notice : notices) {
                    model.addElement(notice);
                }

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

            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
            JTable table = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBounds(180, 80, 370, 380);
            studentFrame.add(scrollPane);

            List<Object[]> students1 = SqlConnect.getLinearAttedanceStudentsByClass(clases, "Linear");
            List<Object[]> students2 = SqlConnect.getDifferantialAttedanceStudentsByClass(clases, "Differantial");
            List<Object[]> students3 = SqlConnect.getMathAttedanceStudentsByClass(clases, "Math");
            List<Object[]> students4 = SqlConnect.getProgramingAttedanceStudentsByClass(clases, "Programing");
            List<Object[]> students5 = SqlConnect.getNumericAttedanceStudentsByClass(clases, "Numeric");

            for (Object[] student : students1) {
                String studentNumber = (String) student[0];
                if (studentNumber.equals(number)) {
                    tableModel.addRow(new Object[]{
                        student[0],
                        student[1],
                        clases,
                        "linear",
                        student[3]
                    });
                }
            }
            for (Object[] student : students2) {
                String studentNumber = (String) student[0];
                if (studentNumber.equals(number)) {
                    tableModel.addRow(new Object[]{
                        student[0],
                        student[1],
                        clases,
                        "Differantial",
                        student[3]
                    });
                }
            }
            for (Object[] student : students3) {
                String studentNumber = (String) student[0];
                if (studentNumber.equals(number)) {
                    tableModel.addRow(new Object[]{
                        student[0],
                        student[1],
                        clases,
                        "Math",
                        student[3]
                    });
                }
            }
            for (Object[] student : students4) {
                String studentNumber = (String) student[0];
                if (studentNumber.equals(number)) {
                    tableModel.addRow(new Object[]{
                        student[0],
                        student[1],
                        clases,
                        "Numeric",
                        student[3]
                    });
                }
            }
            for (Object[] student : students5) {
                String studentNumber = (String) student[0];
                if (studentNumber.equals(number)) {
                    tableModel.addRow(new Object[]{
                        student[0],
                        student[1],
                        clases,
                        "linear",
                        student[3]
                    });
                }
            }
            JButton exportPdfBtn = new JButton("Export to PDF");
            exportPdfBtn.setBounds(420, 520, 120, 30);
            studentFrame.add(exportPdfBtn);

            exportPdfBtn.addActionListener(event -> {
                try {
                    exportTableToPDF(table, "StudentAttedance.pdf");
                    JOptionPane.showMessageDialog(studentFrame, "PDF successfully created!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(studentFrame, "Error creating PDF: " + ex.getMessage());
                }
            });

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

            List<Object[]> students1 = SqlConnect.getLinearNoteStudentsByClass(clases, "Linear");
            List<Object[]> students2 = SqlConnect.getDifferantialNoteStudentsByClass(clases, "Differantial");
            List<Object[]> students3 = SqlConnect.getMathNoteStudentsByClass(clases, "Math");
            List<Object[]> students4 = SqlConnect.getProgramingNoteStudentsByClass(clases, "Programing");
            List<Object[]> students5 = SqlConnect.getNumericNoteStudentsByClass(clases, "Numeric");

            for (Object[] student : students1) {
                String studentNumber = (String) student[0];
                if (studentNumber.equals(number)) {
                    model.addRow(new Object[]{
                        student[0],
                        student[1],
                        clases,
                        "linear",
                        student[3]
                    });
                }
            }
            for (Object[] student : students2) {
                String studentNumber = (String) student[0];
                if (studentNumber.equals(number)) {
                    model.addRow(new Object[]{
                        student[0],
                        student[1],
                        clases,
                        "Differantial",
                        student[3]
                    });
                }
            }
            for (Object[] student : students3) {
                String studentNumber = (String) student[0];
                if (studentNumber.equals(number)) {
                    model.addRow(new Object[]{
                        student[0],
                        student[1],
                        clases,
                        "Math",
                        student[3]
                    });
                }
            }
            for (Object[] student : students4) {
                String studentNumber = (String) student[0];
                if (studentNumber.equals(number)) {
                    model.addRow(new Object[]{
                        student[0],
                        student[1],
                        clases,
                        "Numeric",
                        student[3]
                    });
                }
            }
            for (Object[] student : students5) {
                String studentNumber = (String) student[0];
                if (studentNumber.equals(number)) {
                    model.addRow(new Object[]{
                        student[0],
                        student[1],
                        clases,
                        "linear",
                        student[3]
                    });
                }
            }
            
            JButton exportPdfBtn = new JButton("Export to PDF");
            exportPdfBtn.setBounds(420, 520, 120, 30);
            studentFrame.add(exportPdfBtn);

            exportPdfBtn.addActionListener(event -> {
                try {
                    exportTableToPDF(noteTable, "StudentNote.pdf");
                    JOptionPane.showMessageDialog(studentFrame, "PDF successfully created!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(studentFrame, "Error creating PDF: " + ex.getMessage());
                }
            });
            
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
                {number, "Math", "12.00-14.00", "Monday", false},
                {number, "Math", "14.00-16.00", "Friday", false},
                {number, "Math", "11.00-13.00", "Monday", false},
                {number, "Calculus", "12.00-14.00", "Monday", false},
                {number, "Calculus", "09.00-11.00", "Monday", false},
                {number, "Diff", "12.00-14.00", "Friday", false},
                {number, "Programming", "10.00-12.00", "Monday", false},
                {number, "Programming", "12.00-14.00", "Monday", false},
                {number, "Numeric", "12.00-14.00", "Wednesday", false},
                {number, "Numeric", "11.00-13.00", "Tuesday", false},
                {number, "Numeric", "09.00-11.00", "Thursday", false},
                {number, "Numeric", "12.00-14.00", "Monday", false},
                {number, "Linear", "08.00-10.00", "Monday", false},
                {number, "Linear", "11.00-13.00", "Thursday", false},
                {number, "Linear", "09.00-11.00", "Wednesday", false},
                {number, "Linear", "12.00-14.00", "Thursday", false}
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
                        String day = (String) model.getValueAt(rowIndex, 3);
                        String hour = (String) model.getValueAt(rowIndex, 2);
                        String lecture = (String) model.getValueAt(rowIndex, 1);

                        hasSelection = true;
                        SqlConnect.insertConfirmedStudentLessonForStudent(number, day, hour, lecture);
                    }
                }

                if (hasSelection) {
                    int confirmation = JOptionPane.showConfirmDialog(studentFrame, selectedLessons.toString() + "\nDo you confirm your selection?", "Confirm Selection", JOptionPane.YES_NO_OPTION
                    );

                    if (confirmation == JOptionPane.YES_OPTION) {
                        JOptionPane.showMessageDialog(studentFrame, "Selection confirmed.You can check Programe Button\nThank you!", "Confirmation", JOptionPane.INFORMATION_MESSAGE
                        );
                    } else {
                        JOptionPane.showMessageDialog(studentFrame, "Selection was not confirmed.", "Canceled", JOptionPane.WARNING_MESSAGE
                        );
                    }
                } else {
                    JOptionPane.showMessageDialog(studentFrame, "No lessons selected. Please select at least one lesson.", "Warning", JOptionPane.WARNING_MESSAGE
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

            List<Object[]> confirmedStudentLessons = SqlConnect.getconfirmedStudentLessons();

            List<Object[]> studentLessons = new ArrayList<>();
            for (Object[] lesson : confirmedStudentLessons) {
                if (lesson[0].equals(number)) {
                    studentLessons.add(lesson);
                }
            }

            DefaultTableModel model = new DefaultTableModel(new String[]{"Number", "Day", "Hour", "Lecture"}, 0);
            JTable noteTable = new JTable(model);
            JScrollPane noteScroll = new JScrollPane(noteTable);
            noteScroll.setBounds(180, 80, 370, 380);
            studentFrame.add(noteScroll);

            for (Object[] lesson : studentLessons) {
                model.addRow(lesson);
            }

            JButton exportPdfBtn = new JButton("Export to PDF");
            exportPdfBtn.setBounds(420, 520, 120, 30);
            studentFrame.add(exportPdfBtn);

            exportPdfBtn.addActionListener(event -> {
                try {
                    exportTableToPDF(noteTable, "StudentProgram.pdf");
                    JOptionPane.showMessageDialog(studentFrame, "PDF successfully created!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(studentFrame, "Error creating PDF: " + ex.getMessage());
                }
            });
            studentFrame.revalidate();
            studentFrame.repaint();
            File file = new File("StudentProgram.pdf");
            if (file.exists()) {
                System.out.println("PDF dosyası başarıyla oluşturuldu: " + file.getAbsolutePath());
            } else {
                System.out.println("PDF dosyası oluşturulamadı!");
            }
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

            List<String> notices = SqlConnect.fetchPrivateNotices(clases);
            for (String notice : notices) {
                model.addElement(notice);
            }
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

    public static void exportTableToPDF(JTable table, String fileName) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(fileName));
        document.open();

        document.add(new Paragraph("Student Program Report"));
        document.add(new Paragraph(" ")); // Boşluk

        PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
        for (int i = 0; i < table.getColumnCount(); i++) {
            pdfTable.addCell(new PdfPCell(new Phrase(table.getColumnName(i))));
        }

        for (int rows = 0; rows < table.getRowCount(); rows++) {
            for (int cols = 0; cols < table.getColumnCount(); cols++) {
                pdfTable.addCell(table.getValueAt(rows, cols).toString());
            }
        }

        document.add(pdfTable);
        document.close();
    }
}
