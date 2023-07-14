package com.jannetta.workshopscheduler.view;

import com.jannetta.workshopscheduler.controller.FileUtilities;
import com.jannetta.workshopscheduler.model.ScheduleTableModel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.io.File;
import java.nio.file.Path;
import java.util.Vector;

import static com.jannetta.workshopscheduler.controller.Utilities.updateTimes;

public class ButtonPanel extends JPanel {
    JTable scheduleTable;
    ScheduleTableModel scheduleTableModel;
    public ButtonPanel(TableGUI gui) {
        // Button panel
        scheduleTableModel = gui.getTablePanel().scheduleTableModel;
        scheduleTable = gui.getTablePanel().getTable();
        MigLayout migLayout = new MigLayout("fillx", "[grow]rel[grow]rel[grow]", "[]10[]10[]");
        setLayout(migLayout);
        JButton btn_add = new JButton("Add row");
        JButton btn_lunch = new JButton("Add lunch");
        JButton btn_break = new JButton("Add break");
        JButton btn_up = new JButton("Row UP");
        JButton btn_down = new JButton("Row DOWN");
        JButton btn_createHTML = new JButton("Create HTML");
        JButton btn_remove = new JButton("Remove row");
        JButton btn_saveChanges = new JButton("Save changes");
        JButton btn_updateTimes = new JButton("Update times");

        btn_remove.addActionListener(e -> remButtListener());
        btn_add.addActionListener(e -> addButtListener());
        btn_lunch.addActionListener(e -> lunchButtListener());
        btn_break.addActionListener(e -> breakButtListener());
        btn_up.addActionListener(e -> upButtListener());
        btn_down.addActionListener(e -> downButtListener());
        btn_saveChanges.addActionListener(e -> saveChangesButtListener(scheduleTableModel.getDataVector()));
        btn_createHTML.addActionListener(e -> createHtmlButtListener(gui.getTextFieldPanel().getStartTimeTextField().getText()));
        btn_updateTimes.addActionListener(e -> updateTimes(gui));

        add(btn_up, "growx");
        add(btn_down, "growx");
        add(btn_updateTimes, "growx, wrap");
        add(btn_createHTML, "growx");
        add(btn_remove, "growx");
        add(btn_add, "growx, wrap");
        add(btn_lunch, "growx");
        add(btn_saveChanges, "growx");
        add(btn_break, "growx, wrap");
    }

    public void remButtListener() {
        // check for selected row first
        if (scheduleTable.getSelectedRow() != -1) {
            // remove selected row from the com.jannetta.workshopscheduler.model
            scheduleTableModel.removeRow(scheduleTable.getSelectedRow());
        } else {
            JOptionPane.showMessageDialog(null, "Please select row to remove.");
            JOptionPane.showMessageDialog(null, scheduleTable.getColumnModel().getColumn(1));
            JOptionPane.showMessageDialog(null, scheduleTable.getColumnModel().getColumnCount());

        }
    }

    public void addButtListener() {
        int rowIndex = scheduleTable.getSelectedRow();
        System.out.println("Selected row: " + rowIndex);
        if (rowIndex != -1) {
//        JOptionPane.showMessageDialog(null, rowIndex);
            // add row from the com.jannetta.workshopscheduler.model
            scheduleTableModel.insertRow(rowIndex + 1, new Object[]{"-", "0", "-", "-", "-"});
        } else {
            scheduleTableModel.addRow(new Object[]{"-", "0", "-", "-", "-"});
        }
    }

    public void lunchButtListener() {
        int rowIndex = scheduleTable.getSelectedRow();
        if (rowIndex != -1) {
//        JOptionPane.showMessageDialog(null, rowIndex);
            // add row from the com.jannetta.workshopscheduler.model
            scheduleTableModel.insertRow(rowIndex, new Object[]{"","60", "LUNCH", "-", "-", "-"});

        } else {
            scheduleTableModel.addRow(new Object[]{"","60", "LUNCH", "-", "-", "-"});
        }
    }

    public void breakButtListener() {
        int rowIndex = scheduleTable.getSelectedRow();
        if (rowIndex != -1) {
//        JOptionPane.showMessageDialog(null, rowIndex);
            // add row from the com.jannetta.workshopscheduler.model
            scheduleTableModel.insertRow(rowIndex, new Object[]{"","15", "BREAK", "-", "-", "-"});
        } else {
            scheduleTableModel.addRow(new Object[]{"","15", "BREAK", "-", "-", "-"});
        }
    }

    public void upButtListener() {
        int rowIndex = scheduleTable.getSelectedRow();
        if (scheduleTable.getSelectedRow() == 0) {
            JOptionPane.showMessageDialog(null, "You are already at the top");
        } else if (scheduleTable.getSelectedRow() != -1) {
            scheduleTableModel.moveRow(rowIndex, rowIndex, rowIndex - 1);
            scheduleTable.setRowSelectionInterval(rowIndex - 1, rowIndex - 1);
        } else {
            JOptionPane.showMessageDialog(null, "Please select row to move.");
        }
    }

    public void downButtListener() {
        int rowIndex = scheduleTable.getSelectedRow();
        if (rowIndex < scheduleTableModel.getRowCount() - 1) {
            if (scheduleTable.getSelectedRow() != -1) {
                scheduleTableModel.moveRow(rowIndex, rowIndex, rowIndex + 1);
                scheduleTable.setRowSelectionInterval(rowIndex + 1, rowIndex + 1);
            } else {
                JOptionPane.showMessageDialog(null, "Please select row to move.");
            }
        }
    }

    public void saveChangesButtListener(Vector<Vector> data) {
        String currentPath = Path.of("").toAbsolutePath().toString() + "/";
        JFileChooser fileChooser = new JFileChooser(currentPath);
        fileChooser.setDialogTitle("HTML file to save to.");
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getAbsolutePath();
            if (!(filename.endsWith(".csv")))
                filename += ".csv";
            FileUtilities.saveCsvFile(filename, data);
        }
    }

    public void createHtmlButtListener(String startTime) {
        System.out.println("Start time: " + startTime);
        String currentPath = Path.of("").toAbsolutePath().toString() + "/";
        System.out.println("Save html file to: " + currentPath);
        // Check if startTime is a valid value
        if (!isValidStartTime(startTime)) {
            String time = JOptionPane.showInputDialog("Invalid start time. Please enter a valid value to match the format \"hh:mm\". Example: 10:00");
            startTime = time;
        }
        JFileChooser fileChooser = new JFileChooser(currentPath);
        fileChooser.setDialogTitle("HTML file to save to.");
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String htmlFile = fileChooser.getSelectedFile().getAbsolutePath();
            if (!(htmlFile.endsWith(".html")))
                htmlFile += ".html";
            FileUtilities.createHTML(scheduleTableModel.getDataVector(), startTime, new File(htmlFile));
        }
    }

    private boolean isValidStartTime(String startTime) {
        return startTime.matches("\\d{2}:\\d{2}");
    }

}