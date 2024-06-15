package com.jannetta.workshopscheduler.view;

import com.jannetta.workshopscheduler.controller.FileUtilities;
import com.jannetta.workshopscheduler.controller.Globals;
import com.jannetta.workshopscheduler.controller.Utilities;
import com.jannetta.workshopscheduler.model.ScheduleTableModel;
import net.miginfocom.swing.MigLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.File;
import java.util.Vector;

import static com.jannetta.workshopscheduler.controller.Utilities.updateTimes;

public class ButtonPanel extends JPanel {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    JTable scheduleTable;
    ScheduleTableModel scheduleTableModel;
    TableGUI gui;
    Globals globals;
    public ButtonPanel(TableGUI gui, Globals globals) {
        this.gui = gui;
        this.globals = globals;
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {

                        UIManager.setLookAndFeel(info.getClassName());

                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        // Button panel
        scheduleTableModel = gui.getTablePanel().getModel();
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
        JButton btn_dayBreak = new JButton("Day Break");

        btn_remove.addActionListener(e -> remButtListener());
        btn_add.addActionListener(e -> addButtListener());
        btn_lunch.addActionListener(e -> lunchButtListener());
        btn_break.addActionListener(e -> breakButtListener());
        btn_up.addActionListener(e -> upButtListener());
        btn_down.addActionListener(e -> downButtListener());
        btn_saveChanges.addActionListener(e -> saveChangesButtListener(scheduleTableModel.getDataVector(),
                gui.getTextFieldPanel().getStartTimeTextField().getText(),
                gui.getTextFieldPanel().getTitleTextField().getText()));
        btn_createHTML.addActionListener(e -> createHtmlButtListener(gui.getTextFieldPanel().getStartTimeTextField().getText()));
        btn_updateTimes.addActionListener(e -> updateTimes(gui));
        btn_dayBreak.addActionListener(e -> dayBreakListener());

        add(btn_up, "growx");
        add(btn_down, "growx");
        add(btn_updateTimes, "growx, wrap");
        add(btn_createHTML, "growx");
        add(btn_remove, "growx");
        add(btn_add, "growx, wrap");
        add(btn_lunch, "growx");
        add(btn_saveChanges, "growx");
        add(btn_break, "growx, wrap");
        add(btn_dayBreak, "growx");
    }

    public void dayBreakListener() {
        int rowIndex = scheduleTable.getSelectedRow();
        if (rowIndex != -1) {
            scheduleTableModel.insertRow(rowIndex, new Object[]{"","15", "DAY BREAK", "-", "-", "-"});

        } else {
            scheduleTableModel.addRow(new Object[]{"","", "DAY BREAK", "", "", ""});
        }
    }
    public void remButtListener() {
        // check for selected row first
        if (scheduleTable.getSelectedRow() != -1) {
            // remove selected row from the com.jannetta.workshopscheduler.model
            scheduleTableModel.removeRow(scheduleTable.getSelectedRow());
        } else {
            JOptionPane.showMessageDialog(null, "Please select row to remove.");
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

    public void saveChangesButtListener(Vector<Vector> data, String time, String title) {
        String currentPath = globals.getProperties().getProperty("workingDirectory");
        JFileChooser fileChooser = new JFileChooser(currentPath);
        fileChooser.setDialogTitle("HTML file to save to.");
        fileChooser.setSelectedFile(new File(gui.getTitle()));
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getAbsolutePath();
            if (!(filename.endsWith(".csv")))
                filename += ".csv";
            FileUtilities.saveCsvFile(filename, time, title, data);
            globals.getProperties().setProperty("workingDirectory", (new File(filename).getPath()));
            Utilities.savePropertyFile(globals.getProperties(), globals.getConfigDirectory());
            gui.setDirtyFlag(false);
        } else {
            logger.debug("File saving cancelled");
        }
    }

    public void createHtmlButtListener(String startTime) {
        System.out.println("Start time: " + startTime);
        String currentPath = globals.getProperties().getProperty("workingDirectory");
        System.out.println("Save html file to: " + currentPath);
        // Check if startTime is a valid value
        if (!isValidStartTime(startTime)) {
            String time = JOptionPane.showInputDialog("Invalid start time. Please enter a valid value to match the format \"hh:mm\". Example: 10:00");
            startTime = time;
        }
        JFileChooser fileChooser = new JFileChooser(currentPath);
        fileChooser.setDialogTitle("HTML file to save to.");
        String filename = gui.getTitle();
        if (filename.equals("New schedule"))
            filename = "NewSchedule.html";
        else
            filename = filename.substring(0,gui.getTitle().lastIndexOf('.'));
        fileChooser.setSelectedFile(new File(filename));
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