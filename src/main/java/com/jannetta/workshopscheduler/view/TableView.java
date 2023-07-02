package com.jannetta.workshopscheduler.view;

import com.jannetta.workshopscheduler.controller.FileUtilities;
import com.jannetta.workshopscheduler.controller.WrapCellRenderer;
import com.jannetta.workshopscheduler.model.ScheduleTableModel;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Vector;

/**
 * Frame for displaying schedule as a table
 */
public class TableView extends JFrame {

    final private JTable scheduleTable = new JTable();

    JTextArea mainText = MainGUI.viewCsvTextArea;
    ScheduleTableModel scheduleTableModel;
    String startTime;
    String[] column_names = {"Start", "Duration(min.)", "Name", "Summary", "URL"};

    public TableView(String filePath ,String startTime) {
        JButton btn_add;
        JButton btn_lunch;
        JButton btn_break;
        JButton btn_update;
        JButton btn_up;
        JButton btn_down;
        File csv_data = new File(filePath);
        this.startTime = startTime;

        JPanel textFieldPanel = new JPanel();
        JTextField titleTextField = new JTextField("Python");
        JTextField startTimeTextField = new JTextField("10:00");
        textFieldPanel.add(titleTextField);
        textFieldPanel.add(titleTextField);

        JPanel buttonPanel = new JPanel();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        GridBagLayout layout = new GridBagLayout();
        buttonPanel.setLayout(layout);

        scheduleTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scheduleTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        scheduleTable.getTableHeader().setOpaque(false);
        scheduleTable.getTableHeader().setFont(new Font("Segue UI", Font.BOLD, 12));
        for (int i = 0; i < scheduleTable.getColumnCount(); i++) {
            TableColumn column = scheduleTable.getColumnModel().getColumn(i);
            column.setCellRenderer(new WrapCellRenderer());
        }
        JScrollPane scrollPane = new JScrollPane(scheduleTable);
        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(700, 400));

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        buttonPanel.add(scrollPane, gridBagConstraints);

        btn_update = new JButton("Update CSV area");
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        gridBagConstraints.ipadx = 0;
        gridBagConstraints.ipady = 0;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;//row
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.weightx = 0.1;
        buttonPanel.add(btn_update, gridBagConstraints);

        btn_up = new JButton("Row UP");
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;//row
        buttonPanel.add(btn_up, gridBagConstraints);

        btn_down = new JButton("Row DOWN");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;//row
        buttonPanel.add(btn_down, gridBagConstraints);

        JButton btn_createHTML = new JButton("Create HTML");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        buttonPanel.add(btn_createHTML, gridBagConstraints);

        JButton btn_remove = new JButton("Remove row");
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;//row
        buttonPanel.add(btn_remove, gridBagConstraints);

        btn_add = new JButton("Add row");
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;//row
        buttonPanel.add(btn_add, gridBagConstraints);

        btn_lunch = new JButton("Add lunch");
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;//row
        buttonPanel.add(btn_lunch, gridBagConstraints);

        JButton btn_saveChanges = new JButton("Save changes");
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;//row
        buttonPanel.add(btn_saveChanges, gridBagConstraints);

        btn_break = new JButton("Add break");
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;//row
        buttonPanel.add(btn_break, gridBagConstraints);

        add(textFieldPanel);
        buttonPanel.setPreferredSize(new Dimension(710, 550));
        setMinimumSize(new Dimension(710, 550));
        setMaximumSize(new Dimension(710, 550));
        add(buttonPanel);
        setSize(700, 1000);
        scrollPane.getViewport().add(scheduleTable);
        Vector<Vector<String>> data = FileUtilities.readData(csv_data, startTime);
        scheduleTableModel = new ScheduleTableModel(data, new Vector<String>(Arrays.asList(column_names)));
        scheduleTable.setModel(scheduleTableModel);


        btn_remove.addActionListener(e -> remButtListener());
        btn_add.addActionListener(e -> addButtListener());
        btn_lunch.addActionListener(e -> lunchButtListener());
        btn_break.addActionListener(e -> breakButtListener());
        btn_update.addActionListener(e -> updateButtListener());
        btn_up.addActionListener(e -> upButtListener());
        btn_down.addActionListener(e -> downButtListener());
        btn_createHTML.addActionListener(e -> createHtmlButtListener(startTime));
        btn_saveChanges.addActionListener(e -> saveChangesButtListener(scheduleTableModel.getDataVector()));

        setVisible(true);
        pack();
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
        if (rowIndex != -1) {
//        JOptionPane.showMessageDialog(null, rowIndex);
            // add row from the com.jannetta.workshopscheduler.model
            scheduleTableModel.insertRow(rowIndex + 1, new Object[]{"-", "-", "-", "-", "-"});
        } else {
            scheduleTableModel.addRow(new Object[]{"-", "-", "-", "-", "-"});
        }
    }

    public void lunchButtListener() {
        int rowIndex = scheduleTable.getSelectedRow();
        if (rowIndex != -1) {
//        JOptionPane.showMessageDialog(null, rowIndex);
            // add row from the com.jannetta.workshopscheduler.model
            scheduleTableModel.insertRow(rowIndex, new Object[]{"60", "LUNCH", "-", "-", "-"});
        } else {
            scheduleTableModel.addRow(new Object[]{"60", "LUNCH", "-", "-", "-"});
        }
    }

    public void breakButtListener() {
        int rowIndex = scheduleTable.getSelectedRow();
        if (rowIndex != -1) {
//        JOptionPane.showMessageDialog(null, rowIndex);
            // add row from the com.jannetta.workshopscheduler.model
            scheduleTableModel.insertRow(rowIndex, new Object[]{"15", "BREAK", "-", "-", "-"});
        } else {
            scheduleTableModel.addRow(new Object[]{"15", "BREAK", "-", "-", "-"});
        }
    }

    ;

    public void updateButtListener() {
        mainText.selectAll();
        mainText.replaceSelection("");
        for (int i = 0; i < scheduleTable.getRowCount(); i++) {
            String duration = scheduleTable.getValueAt(i, 0).toString();
            String name = scheduleTable.getValueAt(i, 1).toString();
            String summary = scheduleTable.getValueAt(i, 2).toString();
            String web = scheduleTable.getValueAt(i, 3).toString();
            mainText.setText(mainText.getText() + duration + "," + name + "," + summary + "," + web + "\n");
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

/*
 * 2023-07-01: Change actionEvents to lambdas
 * 2023-07-01: Refactor variable names to use fully qualified names
 * 2023-07-01: Extract column names to an array
 */