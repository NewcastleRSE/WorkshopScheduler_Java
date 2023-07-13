package com.jannetta.workshopscheduler.view;

import com.jannetta.workshopscheduler.controller.FileUtilities;
import com.jannetta.workshopscheduler.controller.WrapCellRenderer;
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

        File csv_data = new File(filePath);
        Vector<Vector<String>> data = FileUtilities.readData(csv_data, gui.getTextFieldPanel().getStartTimeTextField().getText());
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
