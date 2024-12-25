package visual_project;

import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import static visual_project.StudentUser.exportTableToPDF;
import visual_project.SqlConnect;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.File;
import javax.swing.table.DefaultTableModel;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class TeacherUser {

    public TeacherUser(JFrame teacherFrame, String branch, String name, String number) {
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
        noticebtn.addActionListener(e -> {
            teacherFrame.getContentPane().removeAll();
            teacherFrame.add(btnpanel);
            teacherFrame.revalidate();
            teacherFrame.repaint();
            JOptionPane.showMessageDialog(teacherFrame, "You can publish the announcement with school permission.");

            String pin = JOptionPane.showInputDialog(teacherFrame, "Please enter your PIN");

            if (pin != null && pin.equals("61")) {
                JOptionPane.showMessageDialog(teacherFrame, "You can publish the announcement.");
                JLabel notice = new JLabel("Please write a notice: ");
                notice.setBounds(180, 30, 150, 30);
                teacherFrame.add(notice);
                JTextArea noticearea = new JTextArea();
                noticearea.setBounds(180, 70, 380, 350);
                teacherFrame.add(noticearea);
                JButton publishedbtn = new JButton("Published");
                publishedbtn.setBounds(430, 440, 120, 30);
                teacherFrame.add(publishedbtn);
                publishedbtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!noticearea.getText().isEmpty()) {
                            int response = JOptionPane.showConfirmDialog(teacherFrame, "Do you want to publish this notice?\n " + noticearea.getText(), "Alert", JOptionPane.YES_NO_OPTION);
                            if (response == JOptionPane.YES_OPTION) {
                                String teacherNumber = JOptionPane.showInputDialog(teacherFrame, "Please enter your teacher number:");
                                if (teacherNumber != null && !teacherNumber.isEmpty()) {
                                    SqlConnect.insertPublicNotice(teacherNumber, noticearea.getText());
                                    JOptionPane.showMessageDialog(teacherFrame, "You published!");
                                } else {
                                    JOptionPane.showMessageDialog(teacherFrame, "You didn't enter a valid teacher number!");
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(teacherFrame, "You didn't write anything. If you want to publish notice please write the notice!");
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
            teacherFrame.getContentPane().removeAll();
            teacherFrame.add(btnpanel);
            String[] clas = {"P", "1", "2", "3", "4"};
            JComboBox<String> combo = new JComboBox<>(clas);
            combo.setBounds(450, 30, 100, 30);
            teacherFrame.add(combo);

            JLabel classeslbl = new JLabel("Choose a class:");
            classeslbl.setBounds(350, 30, 150, 30);
            teacherFrame.add(classeslbl);

            DefaultTableModel model = new DefaultTableModel(new String[]{"Number", "Name", "Lecture", "Attendance"}, 0);
            JTable attedanceTable = new JTable(model);
            JScrollPane attedanceScroll = new JScrollPane(attedanceTable);
            attedanceScroll.setBounds(180, 80, 370, 380);
            teacherFrame.add(attedanceScroll);

            if (branch.equals("Math")) {
                combo.addActionListener(event -> {
                    String selectedClass = (String) combo.getSelectedItem();
                    model.setRowCount(0);
                    List<Object[]> students = SqlConnect.getMathAttedanceStudentsByClass(selectedClass, branch);
                    for (Object[] row : students) {
                        model.addRow(row);
                    }
                });
            } else if (branch.equals("Differantial")) {
                combo.addActionListener(event -> {
                    String selectedClass = (String) combo.getSelectedItem();
                    model.setRowCount(0);
                    List<Object[]> students = SqlConnect.getDifferantialAttedanceStudentsByClass(selectedClass, branch);
                    for (Object[] row : students) {
                        model.addRow(row);
                    }
                });
            } else if (branch.equals("Programing")) {
                combo.addActionListener(event -> {
                    String selectedClass = (String) combo.getSelectedItem();
                    model.setRowCount(0);
                    List<Object[]> students = SqlConnect.getProgramingAttedanceStudentsByClass(selectedClass, branch);
                    for (Object[] row : students) {
                        model.addRow(row);
                    }
                });
            } else if (branch.equals("Numeric")) {
                combo.addActionListener(event -> {
                    String selectedClass = (String) combo.getSelectedItem();
                    model.setRowCount(0);
                    List<Object[]> students = SqlConnect.getDifferantialAttedanceStudentsByClass(selectedClass, branch);
                    for (Object[] row : students) {
                        model.addRow(row);
                    }
                });
            } else if (branch.equals("Linear")) {
                combo.addActionListener(event -> {
                    String selectedClass = (String) combo.getSelectedItem();
                    model.setRowCount(0);
                    List<Object[]> students = SqlConnect.getLinearAttedanceStudentsByClass(selectedClass, branch);
                    for (Object[] row : students) {
                        model.addRow(row);
                    }
                });
            }
            JLabel searchlbl = new JLabel("Search number:");
            searchlbl.setBounds(180, 480, 150, 30);
            teacherFrame.add(searchlbl);

            JTextField searchField = new JTextField();
            searchField.setBounds(280, 480, 100, 30);
            teacherFrame.add(searchField);

            JLabel attendancelbl = new JLabel("Attendance Status:");
            attendancelbl.setBounds(180, 520, 150, 30);
            teacherFrame.add(attendancelbl);

            JRadioButton check1 = new JRadioButton("Continous");
            check1.setBounds(300, 520, 100, 30);
            JRadioButton check2 = new JRadioButton("Notcontinous");
            check2.setBounds(300, 550, 100, 30);

            ButtonGroup group = new ButtonGroup();
            group.add(check1);
            group.add(check2);

            teacherFrame.add(check1);
            teacherFrame.add(check2);

            JButton addbtn = new JButton("Add");
            addbtn.setBounds(400, 480, 110, 30);
            teacherFrame.add(addbtn);
            addbtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String searchNumber = searchField.getText();
                    String selectedAttendance = null;
                    boolean found = false;

                    int selectedRow = attedanceTable.getSelectedRow();
                    if (selectedRow != -1) {
                        String selectedNumber = model.getValueAt(selectedRow, 0).toString();
                        searchField.setText(selectedNumber);
                    }

                    if (searchNumber.isEmpty() || group.getSelection() == null) {
                        JOptionPane.showMessageDialog(teacherFrame, "Please fill in both the search number and attendance fields.");
                        return;
                    } else {
                        if (group.getSelection().equals(check1.getModel())) {
                            selectedAttendance = "Continous";
                        } else if (group.getSelection().equals(check2.getModel())) {
                            selectedAttendance = "NotContinous";
                        }
                    }
                    for (int i = 0; i < model.getRowCount(); i++) {
                        String tableNumber = model.getValueAt(i, 0).toString();
                        if (tableNumber.equals(searchNumber)) {
                            found = true;
                            attedanceTable.setRowSelectionInterval(i, i);

                            String existingAttendance = model.getValueAt(i, 3).toString();
                            if (!existingAttendance.equals("0")) {
                                JOptionPane.showMessageDialog(teacherFrame, "Attendance is already assigned. Use the Update button to modify.");
                            } else {
                                model.setValueAt(selectedAttendance, i, 3);
                                if (branch.equals("Math")) {
                                    SqlConnect.updateMathAttedance(searchNumber, selectedAttendance);
                                    model.setRowCount(0);
                                    List<Object[]> updatedStudents = SqlConnect.getMathAttedanceStudentsByClass((String) combo.getSelectedItem(), branch);
                                    for (Object[] row : updatedStudents) {
                                        if (row.length >= 4) {
                                            model.addRow(row);
                                        } else {
                                            System.out.println("Skipping row with invalid data: " + Arrays.toString(row));
                                        }
                                    }
                                } else if (branch.equals("Differantial")) {
                                    SqlConnect.updateDifferantialAttedance(searchNumber, selectedAttendance);
                                    model.setRowCount(0);
                                    List<Object[]> updatedStudents = SqlConnect.getDifferantialAttedanceStudentsByClass((String) combo.getSelectedItem(), branch);
                                    for (Object[] row : updatedStudents) {
                                        if (row.length >= 4) {
                                            model.addRow(row);
                                        } else {
                                            System.out.println("Skipping row with invalid data: " + Arrays.toString(row));
                                        }
                                    }
                                } else if (branch.equals("Programing")) {
                                    SqlConnect.updateProgramingAttedance(searchNumber, selectedAttendance);
                                    model.setRowCount(0);
                                    List<Object[]> updatedStudents = SqlConnect.getProgramingAttedanceStudentsByClass((String) combo.getSelectedItem(), branch);
                                    for (Object[] row : updatedStudents) {
                                        if (row.length >= 4) {
                                            model.addRow(row);
                                        } else {
                                            System.out.println("Skipping row with invalid data: " + Arrays.toString(row));
                                        }
                                    }
                                } else if (branch.equals("Numeric")) {
                                    SqlConnect.updateNumericAttedance(searchNumber, selectedAttendance);
                                    model.setRowCount(0);
                                    List<Object[]> updatedStudents = SqlConnect.getNumericAttedanceStudentsByClass((String) combo.getSelectedItem(), branch);
                                    for (Object[] row : updatedStudents) {
                                        if (row.length >= 4) {
                                            model.addRow(row);
                                        } else {
                                            System.out.println("Skipping row with invalid data: " + Arrays.toString(row));
                                        }
                                    }
                                } else if (branch.equals("Linear")) {
                                    SqlConnect.updateLinearAttedance(searchNumber, selectedAttendance);
                                    model.setRowCount(0);
                                    List<Object[]> updatedStudents = SqlConnect.getLinearAttedanceStudentsByClass((String) combo.getSelectedItem(), branch);
                                    for (Object[] row : updatedStudents) {
                                        if (row.length >= 4) {
                                            model.addRow(row);
                                        } else {
                                            System.out.println("Skipping row with invalid data: " + Arrays.toString(row));
                                        }
                                    }
                                }

                                JOptionPane.showMessageDialog(teacherFrame,
                                        "Attendance added successfully for:\nNumber: " + searchNumber
                                        + "\nName: " + model.getValueAt(i, 2)
                                        + "\nLecture: " + branch);
                            }
                            break;
                        }
                    }

                    if (!found) {
                        JOptionPane.showMessageDialog(teacherFrame, "No match found for number: " + searchNumber);
                    }
                }
            });

            JButton updatebtn = new JButton("Update");
            updatebtn.setBounds(400, 520, 110, 30);
            teacherFrame.add(updatebtn);
            updatebtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String searchNumber = searchField.getText();
                    String selectedAttendance = null;
                    boolean found = false;
                    if (group.getSelection().equals(check1.getModel())) {
                        selectedAttendance = "Continous";
                    } else if (group.getSelection().equals(check2.getModel())) {
                        selectedAttendance = "NotContinous";
                    }

                    int selectedRow = attedanceTable.getSelectedRow();
                    if (selectedRow != -1) {
                        String selectedNumber = model.getValueAt(selectedRow, 0).toString();
                        searchField.setText(selectedNumber);
                    }

                    if (searchNumber.isEmpty() || group.getSelection() == null) {
                        JOptionPane.showMessageDialog(teacherFrame, "Please fill in both the search number and note fields.");
                        return;
                    } else {

                        for (int i = 0; i < model.getRowCount(); i++) {
                            String tableNumber = model.getValueAt(i, 0).toString();
                            if (tableNumber.equals(searchNumber)) {
                                found = true;
                                attedanceTable.setRowSelectionInterval(i, i);
                                model.setValueAt(selectedAttendance, i, 3);
                                model.setValueAt(branch, i, 2);
                                if (branch.equals("Math")) {
                                    SqlConnect.updateMathAttedance(searchNumber, selectedAttendance);
                                    model.setRowCount(0);
                                    List<Object[]> updatedStudents = SqlConnect.getMathAttedanceStudentsByClass((String) combo.getSelectedItem(), branch);
                                    for (Object[] row : updatedStudents) {
                                        if (row.length >= 4) {
                                            model.addRow(row);
                                        } else {
                                            System.out.println("Skipping row with invalid data: " + Arrays.toString(row));
                                        }
                                    }
                                } else if (branch.equals("Differantial")) {
                                    SqlConnect.updateDifferantialAttedance(searchNumber, selectedAttendance);
                                    model.setRowCount(0);
                                    List<Object[]> updatedStudents = SqlConnect.getDifferantialAttedanceStudentsByClass((String) combo.getSelectedItem(), branch);
                                    for (Object[] row : updatedStudents) {
                                        if (row.length >= 4) {
                                            model.addRow(row);
                                        } else {
                                            System.out.println("Skipping row with invalid data: " + Arrays.toString(row));
                                        }
                                    }
                                } else if (branch.equals("Programing")) {
                                    SqlConnect.updateProgramingAttedance(searchNumber, selectedAttendance);
                                    model.setRowCount(0);
                                    List<Object[]> updatedStudents = SqlConnect.getProgramingAttedanceStudentsByClass((String) combo.getSelectedItem(), branch);
                                    for (Object[] row : updatedStudents) {
                                        if (row.length >= 4) {
                                            model.addRow(row);
                                        } else {
                                            System.out.println("Skipping row with invalid data: " + Arrays.toString(row));
                                        }
                                    }
                                } else if (branch.equals("Numeric")) {
                                    SqlConnect.updateNumericAttedance(searchNumber, selectedAttendance);
                                    model.setRowCount(0);
                                    List<Object[]> updatedStudents = SqlConnect.getNumericAttedanceStudentsByClass((String) combo.getSelectedItem(), branch);
                                    for (Object[] row : updatedStudents) {
                                        if (row.length >= 4) {
                                            model.addRow(row);
                                        } else {
                                            System.out.println("Skipping row with invalid data: " + Arrays.toString(row));
                                        }
                                    }
                                } else if (branch.equals("Linear")) {
                                    SqlConnect.updateLinearAttedance(searchNumber, selectedAttendance);
                                    model.setRowCount(0);
                                    List<Object[]> updatedStudents = SqlConnect.getLinearAttedanceStudentsByClass((String) combo.getSelectedItem(), branch);
                                    for (Object[] row : updatedStudents) {
                                        if (row.length >= 4) {
                                            model.addRow(row);
                                        } else {
                                            System.out.println("Skipping row with invalid data: " + Arrays.toString(row));
                                        }
                                    }
                                }
                                JOptionPane.showMessageDialog(teacherFrame,
                                        "Attedance added successfully for:\nNumber: " + tableNumber
                                        + "\nName: " + model.getValueAt(i, 1)
                                        + "\nLecture: " + branch);

                                break;
                            }
                        }
                    }
                    if (!found) {
                        JOptionPane.showMessageDialog(teacherFrame, "No match found for number: " + searchNumber);
                    }
                }
            });

            JButton exportPdfBtn = new JButton("Export to PDF");
            exportPdfBtn.setBounds(400, 560, 110, 30);
            teacherFrame.add(exportPdfBtn);

            exportPdfBtn.addActionListener(event -> {
                try {
                    String selectedClass = (String) combo.getSelectedItem();
                    exportTableToPDF(attedanceTable, "Attendance_Class" + selectedClass + ".pdf", selectedClass, branch);
                    JOptionPane.showMessageDialog(teacherFrame, "PDF successfully created!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(teacherFrame, "Error creating PDF: " + ex.getMessage());
                }
            });

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

            DefaultTableModel model = new DefaultTableModel(new String[]{"Number", "Name", "Lecture", "Note"}, 0);
            JTable noteTable = new JTable(model);
            JScrollPane noteScroll = new JScrollPane(noteTable);
            noteScroll.setBounds(180, 80, 370, 380);
            teacherFrame.add(noteScroll);

            if (branch.equals("Math")) {
                combo.addActionListener(event -> {
                    String selectedClass = (String) combo.getSelectedItem();
                    model.setRowCount(0);
                    List<Object[]> students = SqlConnect.getMathNoteStudentsByClass(selectedClass, branch);
                    for (Object[] row : students) {
                        model.addRow(row);
                    }
                });
            } else if (branch.equals("Differantial")) {
                combo.addActionListener(event -> {
                    String selectedClass = (String) combo.getSelectedItem();
                    model.setRowCount(0);
                    List<Object[]> students = SqlConnect.getDifferantialAttedanceStudentsByClass(selectedClass, branch);
                    for (Object[] row : students) {
                        model.addRow(row);
                    }
                });
            } else if (branch.equals("Programing")) {
                combo.addActionListener(event -> {
                    String selectedClass = (String) combo.getSelectedItem();
                    model.setRowCount(0);
                    List<Object[]> students = SqlConnect.getProgramingAttedanceStudentsByClass(selectedClass, branch);
                    for (Object[] row : students) {
                        model.addRow(row);
                    }
                });
            } else if (branch.equals("Numeric")) {
                combo.addActionListener(event -> {
                    String selectedClass = (String) combo.getSelectedItem();
                    model.setRowCount(0);
                    List<Object[]> students = SqlConnect.getDifferantialAttedanceStudentsByClass(selectedClass, branch);
                    for (Object[] row : students) {
                        model.addRow(row);
                    }
                });
            } else if (branch.equals("Linear")) {
                combo.addActionListener(event -> {
                    String selectedClass = (String) combo.getSelectedItem();
                    model.setRowCount(0);
                    List<Object[]> students = SqlConnect.getLinearAttedanceStudentsByClass(selectedClass, branch);
                    for (Object[] row : students) {
                        model.addRow(row);
                    }
                });
            }

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
            addbtn.setBounds(190, 530, 80, 30);
            teacherFrame.add(addbtn);
            addbtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String searchNumber = searchField.getText();
                    String newNote = noteField.getText();
                    boolean found = false;

                    if (searchNumber.isEmpty() || newNote.isEmpty()) {
                        JOptionPane.showMessageDialog(teacherFrame, "Please fill in both the search number and note fields.");
                        return;
                    }

                    int selectedRow = noteTable.getSelectedRow();
                    if (selectedRow != -1) {
                        String selectedNumber = model.getValueAt(selectedRow, 0).toString();
                        searchField.setText(selectedNumber);
                    }

                    for (int i = 0; i < model.getRowCount(); i++) {
                        String tableNumber = model.getValueAt(i, 0).toString();
                        if (tableNumber.equals(searchNumber)) {
                            found = true;
                            noteTable.setRowSelectionInterval(i, i);
                            String existingNote = model.getValueAt(i, 3).toString();
                            if (!existingNote.equals("0")) {
                                JOptionPane.showMessageDialog(teacherFrame,
                                        "The note is already assigned. Please use the Update button to change it.\n"
                                        + "Existing Note: " + existingNote);
                            } else {
                                model.setValueAt(newNote, i, 3);
                                if (branch.equals("Math")) {
                                    SqlConnect.updateMathNote(searchNumber, newNote);
                                    model.setRowCount(0);
                                    List<Object[]> updatedStudents = SqlConnect.getMathNoteStudentsByClass((String) combo.getSelectedItem(), branch);
                                    for (Object[] row : updatedStudents) {
                                        if (row.length >= 4) {
                                            model.addRow(row);
                                        } else {
                                            System.out.println("Skipping row with invalid data: " + Arrays.toString(row));
                                        }
                                    }
                                } else if (branch.equals("Differantial")) {
                                    SqlConnect.updateDifferantialNote(searchNumber, newNote);
                                    model.setRowCount(0);
                                    List<Object[]> updatedStudents = SqlConnect.getDifferantialNoteStudentsByClass((String) combo.getSelectedItem(), branch);
                                    for (Object[] row : updatedStudents) {
                                        if (row.length >= 4) {
                                            model.addRow(row);
                                        } else {
                                            System.out.println("Skipping row with invalid data: " + Arrays.toString(row));
                                        }
                                    }
                                } else if (branch.equals("Programing")) {
                                    SqlConnect.updateProgramingNote(searchNumber, newNote);
                                    model.setRowCount(0);
                                    List<Object[]> updatedStudents = SqlConnect.getProgramingNoteStudentsByClass((String) combo.getSelectedItem(), branch);
                                    for (Object[] row : updatedStudents) {
                                        if (row.length >= 4) {
                                            model.addRow(row);
                                        } else {
                                            System.out.println("Skipping row with invalid data: " + Arrays.toString(row));
                                        }
                                    }
                                } else if (branch.equals("Numeric")) {
                                    SqlConnect.updateNumericNote(searchNumber, newNote);
                                    model.setRowCount(0);
                                    List<Object[]> updatedStudents = SqlConnect.getNumericNoteStudentsByClass((String) combo.getSelectedItem(), branch);
                                    for (Object[] row : updatedStudents) {
                                        if (row.length >= 4) {
                                            model.addRow(row);
                                        } else {
                                            System.out.println("Skipping row with invalid data: " + Arrays.toString(row));
                                        }
                                    }
                                } else if (branch.equals("Linear")) {
                                    SqlConnect.updateLinearNote(searchNumber, newNote);
                                    model.setRowCount(0);
                                    List<Object[]> updatedStudents = SqlConnect.getLinearNoteStudentsByClass((String) combo.getSelectedItem(), branch);
                                    for (Object[] row : updatedStudents) {
                                        if (row.length >= 4) {
                                            model.addRow(row);
                                        } else {
                                            System.out.println("Skipping row with invalid data: " + Arrays.toString(row));
                                        }
                                    }
                                }
                                JOptionPane.showMessageDialog(teacherFrame,
                                        "Note added successfully for:\nNumber: " + tableNumber
                                        + "\nName: " + model.getValueAt(i, 1)
                                        + "\nNote: " + model.getValueAt(i, 3));
                            }
                            break;
                        }
                    }

                    if (!found) {
                        JOptionPane.showMessageDialog(teacherFrame, "No match found for number: " + searchNumber);
                    }
                }
            });

            JButton updatebtn = new JButton("Update");
            updatebtn.setBounds(290, 530, 80, 30);
            teacherFrame.add(updatebtn);
            updatebtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String searchNumber = searchField.getText();
                    String newNote = noteField.getText();
                    boolean found = false;

                    if (searchNumber.isEmpty() || newNote.isEmpty()) {
                        JOptionPane.showMessageDialog(teacherFrame, "Please fill in both the search number and note fields.");
                        return;
                    }

                    int selectedRow = noteTable.getSelectedRow();
                    if (selectedRow != -1) {
                        String selectedNumber = model.getValueAt(selectedRow, 0).toString();
                        searchField.setText(selectedNumber);
                    }

                    for (int i = 0; i < model.getRowCount(); i++) {
                        String tableNumber = model.getValueAt(i, 0).toString();
                        if (tableNumber.equals(searchNumber)) {
                            found = true;
                            noteTable.setRowSelectionInterval(i, i);
                            model.setValueAt(newNote, i, 3);
                            if (branch.equals("Math")) {
                                SqlConnect.updateMathNote(searchNumber, newNote);
                                model.setRowCount(0);
                                List<Object[]> updatedStudents = SqlConnect.getMathNoteStudentsByClass((String) combo.getSelectedItem(), branch);
                                for (Object[] row : updatedStudents) {
                                    if (row.length >= 4) {
                                        model.addRow(row);
                                    } else {
                                        System.out.println("Skipping row with invalid data: " + Arrays.toString(row));
                                    }
                                }
                            } else if (branch.equals("Differantial")) {
                                SqlConnect.updateDifferantialNote(searchNumber, newNote);
                                model.setRowCount(0);
                                List<Object[]> updatedStudents = SqlConnect.getDifferantialNoteStudentsByClass((String) combo.getSelectedItem(), branch);
                                for (Object[] row : updatedStudents) {
                                    if (row.length >= 4) {
                                        model.addRow(row);
                                    } else {
                                        System.out.println("Skipping row with invalid data: " + Arrays.toString(row));
                                    }
                                }
                            } else if (branch.equals("Programing")) {
                                SqlConnect.updateProgramingNote(searchNumber, newNote);
                                model.setRowCount(0);
                                List<Object[]> updatedStudents = SqlConnect.getProgramingNoteStudentsByClass((String) combo.getSelectedItem(), branch);
                                for (Object[] row : updatedStudents) {
                                    if (row.length >= 4) {
                                        model.addRow(row);
                                    } else {
                                        System.out.println("Skipping row with invalid data: " + Arrays.toString(row));
                                    }
                                }
                            } else if (branch.equals("Numeric")) {
                                SqlConnect.updateNumericNote(searchNumber, newNote);
                                model.setRowCount(0);
                                List<Object[]> updatedStudents = SqlConnect.getNumericNoteStudentsByClass((String) combo.getSelectedItem(), branch);
                                for (Object[] row : updatedStudents) {
                                    if (row.length >= 4) {
                                        model.addRow(row);
                                    } else {
                                        System.out.println("Skipping row with invalid data: " + Arrays.toString(row));
                                    }
                                }
                            } else if (branch.equals("Linear")) {
                                SqlConnect.updateLinearNote(searchNumber, newNote);
                                model.setRowCount(0);
                                List<Object[]> updatedStudents = SqlConnect.getLinearNoteStudentsByClass((String) combo.getSelectedItem(), branch);
                                for (Object[] row : updatedStudents) {
                                    if (row.length >= 4) {
                                        model.addRow(row);
                                    } else {
                                        System.out.println("Skipping row with invalid data: " + Arrays.toString(row));
                                    }
                                }
                            }
                            JOptionPane.showMessageDialog(teacherFrame,
                                    "Note updated successfully for:\nNumber: " + tableNumber
                                    + "\nName: " + model.getValueAt(i, 1)
                                    + "\nNote: " + model.getValueAt(i, 3));
                            break;
                        }
                    }

                    if (!found) {
                        JOptionPane.showMessageDialog(teacherFrame, "No match found for number: " + searchNumber);
                    }
                }
            });

            JButton deletebtn = new JButton("Delete");
            deletebtn.setBounds(390, 530, 80, 30);
            teacherFrame.add(deletebtn);
            deletebtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String searchNumber = searchField.getText();
                    boolean found = false;

                    if (searchNumber.isEmpty()) {
                        JOptionPane.showMessageDialog(teacherFrame, "Please fill in the search number field.");
                        return;
                    }

                    for (int i = 0; i < model.getRowCount(); i++) {
                        String tableNumber = model.getValueAt(i, 0).toString();
                        if (tableNumber.equals(searchNumber)) {
                            found = true;
                            noteTable.setRowSelectionInterval(i, i);
                            model.setValueAt("0", i, 3);
                            if (branch.equals("Math")) {
                                SqlConnect.updateMathNote(searchNumber, "0");
                                model.setRowCount(0);
                                List<Object[]> updatedStudents = SqlConnect.getMathNoteStudentsByClass((String) combo.getSelectedItem(), branch);
                                for (Object[] row : updatedStudents) {
                                    if (row.length >= 4) {
                                        model.addRow(row);
                                    } else {
                                        System.out.println("Skipping row with invalid data: " + Arrays.toString(row));
                                    }
                                }
                            } else if (branch.equals("Differantial")) {
                                SqlConnect.updateDifferantialNote(searchNumber, "0");
                                model.setRowCount(0);
                                List<Object[]> updatedStudents = SqlConnect.getDifferantialNoteStudentsByClass((String) combo.getSelectedItem(), branch);
                                for (Object[] row : updatedStudents) {
                                    if (row.length >= 4) {
                                        model.addRow(row);
                                    } else {
                                        System.out.println("Skipping row with invalid data: " + Arrays.toString(row));
                                    }
                                }
                            } else if (branch.equals("Programing")) {
                                SqlConnect.updateProgramingNote(searchNumber, "0");
                                model.setRowCount(0);
                                List<Object[]> updatedStudents = SqlConnect.getProgramingNoteStudentsByClass((String) combo.getSelectedItem(), branch);
                                for (Object[] row : updatedStudents) {
                                    if (row.length >= 4) {
                                        model.addRow(row);
                                    } else {
                                        System.out.println("Skipping row with invalid data: " + Arrays.toString(row));
                                    }
                                }
                            } else if (branch.equals("Numeric")) {
                                SqlConnect.updateNumericNote(searchNumber, "0");
                                model.setRowCount(0);
                                List<Object[]> updatedStudents = SqlConnect.getNumericNoteStudentsByClass((String) combo.getSelectedItem(), branch);
                                for (Object[] row : updatedStudents) {
                                    if (row.length >= 4) {
                                        model.addRow(row);
                                    } else {
                                        System.out.println("Skipping row with invalid data: " + Arrays.toString(row));
                                    }
                                }
                            } else if (branch.equals("Linear")) {
                                SqlConnect.updateLinearNote(searchNumber, "0");
                                model.setRowCount(0);
                                List<Object[]> updatedStudents = SqlConnect.getLinearNoteStudentsByClass((String) combo.getSelectedItem(), branch);
                                for (Object[] row : updatedStudents) {
                                    if (row.length >= 4) {
                                        model.addRow(row);
                                    } else {
                                        System.out.println("Skipping row with invalid data: " + Arrays.toString(row));
                                    }
                                }
                            }

                            JOptionPane.showMessageDialog(teacherFrame,
                                    "Note reset to 0 successfully for:\nNumber: " + tableNumber
                                    + "\nName: " + model.getValueAt(i, 1)
                                    + "\nNote: " + model.getValueAt(i, 3));
                            break;
                        }
                    }

                    if (!found) {
                        JOptionPane.showMessageDialog(teacherFrame, "No match found for number: " + searchNumber);
                    }
                }
            });
            
            JButton exportPdfBtn = new JButton("Export to PDF");
            exportPdfBtn.setBounds(490, 530, 80, 30);
            teacherFrame.add(exportPdfBtn);

            exportPdfBtn.addActionListener(event -> {
                try {
                    String selectedClass = (String) combo.getSelectedItem();
                    exportTableToPDF(noteTable, "Note_Class" + selectedClass + ".pdf", selectedClass, branch);
                    JOptionPane.showMessageDialog(teacherFrame, "PDF successfully created!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(teacherFrame, "Error creating PDF: " + ex.getMessage());
                }
            });
            
            teacherFrame.revalidate();
            teacherFrame.repaint();
        });

        JButton courseregistrationbtn = new JButton("Course Registration");
        courseregistrationbtn.setBounds(15, 250, 120, 60);
        btnpanel.add(courseregistrationbtn);
        courseregistrationbtn.addActionListener(e -> {
            teacherFrame.getContentPane().removeAll();
            teacherFrame.add(btnpanel);

            JLabel avabilitylbl = new JLabel("Please select to avaible days:");
            avabilitylbl.setBounds(200, 50, 200, 30);
            teacherFrame.add(avabilitylbl);
            String[] avability = {"Monday", "Thusday", "Wednesday", "Thursday", "Friday"};
            JComboBox<String> combo = new JComboBox<>(avability);
            combo.setBounds(400, 50, 110, 30);
            teacherFrame.add(combo);
            JLabel avabilityhours = new JLabel("Please select to avaible hours:");
            avabilityhours.setBounds(200, 90, 200, 40);
            teacherFrame.add(avabilityhours);
            String[] hours = {"09.00-11.00", "11.00-13.00", "13.00-15.00", "15.00-17.00"};
            JList<String> list = new JList<>(hours);
            list.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            list.setVisibleRowCount(4);
            JScrollPane scroll = new JScrollPane(list);
            scroll.setBounds(400, 90, 110, 40);
            JLabel noticelbl = new JLabel("You must select three avaible day and hours!");
            noticelbl.setBounds(200, 150, 350, 30);

            DefaultListModel<String> model = new DefaultListModel<>();
            JList<String> list2 = new JList<>(model);
            list2.setBounds(200, 220, 300, 200);
            JButton selectbtn = new JButton("Select");
            selectbtn.setBounds(450, 180, 100, 30);

            selectbtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedDay = (String) combo.getSelectedItem();
                    String selectedHour = list.getSelectedValue();
                    if (selectedDay != null && selectedHour != null) {
                        model.addElement("Day: " + selectedDay + " Hour: " + selectedHour);
                    }
                }
            });
            JButton deletebtn = new JButton("Delete");
            deletebtn.setBounds(450, 450, 100, 30);
            deletebtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedIndex = list2.getSelectedIndex();
                    if (selectedIndex != -1) {
                        model.remove(selectedIndex);
                    } else {
                        JOptionPane.showMessageDialog(teacherFrame, "You must select an item to delete.");
                    }
                }
            });

            JButton approvebtn = new JButton("Approve");
            approvebtn.setBounds(450, 520, 100, 30);
            approvebtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (model.size() >= 3) {
                        StringBuilder confirmationMessage = new StringBuilder("Do you want to approve this list?\n");
                        List<String[]> approvedLessons = new ArrayList<>();
                        for (int i = 0; i < model.size(); i++) {
                            String[] details = model.get(i).split(" ");
                            String day = details[1];
                            String hour = details[3];
                            approvedLessons.add(new String[]{day, hour, branch});
                            confirmationMessage.append("Day: ").append(day).append(" Hour: ").append(hour).append("\n");
                        }

                        int response = JOptionPane.showConfirmDialog(
                                teacherFrame,
                                confirmationMessage.toString(),
                                "Confirmation",
                                JOptionPane.YES_NO_OPTION
                        );

                        if (response == JOptionPane.YES_OPTION) {
                            for (String[] lesson : approvedLessons) {
                                SqlConnect.insertConfirmedTeacherLesson(number, lesson[0], lesson[1], branch);
                            }
                            JOptionPane.showMessageDialog(teacherFrame, "Program approved and saved!");
                            model.clear();
                        }
                    } else {
                        JOptionPane.showMessageDialog(teacherFrame, "You must select at least 3 items before approving.");
                    }

                }
            });

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
        programebtn.addActionListener(e -> {
            teacherFrame.getContentPane().removeAll();
            teacherFrame.add(btnpanel);
            JOptionPane.showMessageDialog(teacherFrame, "You can see the program after the teacher and student approve it.");

            DefaultTableModel model = new DefaultTableModel(new String[]{"Number", "Day", "Hour", "Lecture"}, 0);
            JTable programeTable = new JTable(model);
            JScrollPane noteScroll = new JScrollPane(programeTable);
            noteScroll.setBounds(180, 80, 370, 380);
            teacherFrame.add(noteScroll);

            List<Object[]> confirmedLessons = SqlConnect.getconfirmedTeacherLessons();
            for (Object[] lesson : confirmedLessons) {
                model.addRow(lesson);
            }
            
            JButton exportPdfBtn = new JButton("Export to PDF");
            exportPdfBtn.setBounds(490, 530, 80, 30);
            teacherFrame.add(exportPdfBtn);

            exportPdfBtn.addActionListener(event -> {
                try {
                    exportTableToPDF(programeTable, "Programe.pdf", "", branch);
                    JOptionPane.showMessageDialog(teacherFrame, "PDF successfully created!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(teacherFrame, "Error creating PDF: " + ex.getMessage());
                }
            });

            teacherFrame.revalidate();
            teacherFrame.repaint();
        });

        JButton personalNoticebtn = new JButton("Personal Notice");
        personalNoticebtn.setBounds(15, 400, 120, 60);
        btnpanel.add(personalNoticebtn);
        personalNoticebtn.addActionListener(e -> {
            teacherFrame.getContentPane().removeAll();
            teacherFrame.add(btnpanel);
            String[] clas = {"P", "1", "2", "3", "4"};
            JComboBox<String> combo = new JComboBox<>(clas);
            combo.setBounds(450, 30, 100, 30);
            teacherFrame.add(combo);
            JLabel classeslbl = new JLabel("Choose a class:");
            classeslbl.setBounds(350, 30, 150, 30);
            teacherFrame.add(classeslbl);
            JLabel noticelbl = new JLabel("Add a notice:");
            noticelbl.setBounds(200, 70, 120, 30);
            teacherFrame.add(noticelbl);
            JTextArea noticearea = new JTextArea();
            noticearea.setBounds(200, 110, 350, 300);
            teacherFrame.add(noticearea);
            JButton addbtn = new JButton("Published");
            addbtn.setBounds(450, 420, 100, 30);
            teacherFrame.add(addbtn);
            addbtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if (!noticearea.getText().isEmpty()) {
                        String selected = (String) combo.getSelectedItem();
                        int response = JOptionPane.showConfirmDialog(teacherFrame, "Do you want to publish this class and this notice?" + "\nClass: " + selected + "\nNotice: " + noticearea.getText(), "Alert", JOptionPane.YES_NO_OPTION);
                        if (response == JOptionPane.YES_OPTION) {
                            String teacherNumber = JOptionPane.showInputDialog(teacherFrame, "Please enter your teacher number:");
                            if (teacherNumber != null && !teacherNumber.isEmpty()) {
                                SqlConnect.insertPrivateNotice(teacherNumber, noticearea.getText(), selected);
                                JOptionPane.showMessageDialog(teacherFrame, "You published!");
                            } else {
                                JOptionPane.showMessageDialog(teacherFrame, "You didn't enter a valid teacher number!");
                            }
                        } else if (response == JOptionPane.NO_OPTION) {
                        }
                    } else {
                        JOptionPane.showMessageDialog(teacherFrame, "You didn't write notice!");
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

    public void exportTableToPDF(JTable table, String fileName, String selectedClass, String branch) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(fileName));
        document.open();

        document.add(new Paragraph("Class: " + selectedClass));
        document.add(new Paragraph("Branch: " + branch));
        document.add(new Paragraph("\n"));

        PdfPTable pdfTable = new PdfPTable(table.getColumnCount());

        for (int i = 0; i < table.getColumnCount(); i++) {
            pdfTable.addCell(table.getColumnName(i));
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
