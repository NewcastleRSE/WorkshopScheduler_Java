package com.jannetta.workshopscheduler.view;

import com.jannetta.workshopscheduler.controller.FileUtilities;
import com.jannetta.workshopscheduler.controller.WrapCellRenderer;
import com.jannetta.workshopscheduler.model.Schedule;
import com.jannetta.workshopscheduler.model.ScheduleTableModel;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.Vector;

public class TablePanel extends JPanel {
    ScheduleTableModel scheduleTableModel;
    final JTable scheduleTable = new JTable();

    public TablePanel(String filePath, TableGUI gui) {
        String[] column_names = {"Start", "Duration(min.)", "Name", "Summary", "URL"};

        // Table panel
        JScrollPane scrollPane = new JScrollPane(scheduleTable);
        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new

        Dimension(700, 400));
        JPanel tablePanel = new JPanel();
        tablePanel.add(scrollPane);
        scheduleTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scheduleTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        scheduleTable.getTableHeader().

        setOpaque(false);
        scheduleTable.getTableHeader().setFont(new Font("Segue UI", Font.BOLD, 12));
        for (
            int i = 0; i < scheduleTable.getColumnCount(); i++) {
            TableColumn column = scheduleTable.getColumnModel().getColumn(i);
            column.setCellRenderer(new WrapCellRenderer());
        }
        Vector<Vector<String>> data;
        if (!filePath.equals("")) {
            File csv_data = new File(filePath);
            Schedule schedule = FileUtilities.readData(csv_data);
            data = schedule.getSchedule();
            gui.getTextFieldPanel().startTimeTextField.setText(schedule.getTime());
            gui.getTextFieldPanel().titleTextField.setText(schedule.getTitle());
        } else {
            data = new Vector<Vector<String>>();
            String[] row = {"-", "-", "-", "-"};
            Vector<String> vector = new Vector<String>(Arrays.asList(row));
            data.add(vector);
        }
        scheduleTableModel = new ScheduleTableModel(data, new Vector<String>(Arrays.asList(column_names)));
        scheduleTable.setModel(scheduleTableModel);
        add(scrollPane);
    }

    public ScheduleTableModel getModel() {
        return scheduleTableModel;
    }

    public JTable getTable() {
        return scheduleTable;
    }
}
