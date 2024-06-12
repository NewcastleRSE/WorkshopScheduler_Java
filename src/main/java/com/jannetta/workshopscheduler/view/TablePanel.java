package com.jannetta.workshopscheduler.view;

import com.jannetta.workshopscheduler.controller.FileUtilities;
import com.jannetta.workshopscheduler.controller.WrapCellRenderer;
import com.jannetta.workshopscheduler.model.Schedule;
import com.jannetta.workshopscheduler.model.ScheduleTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.Vector;

public class TablePanel extends JPanel {
    private ScheduleTableModel scheduleTableModel;
    final JTable scheduleTable = new JTable();
    private Vector<Vector<String>> data;

    // Create a custom cell renderer
    DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value,
                    isSelected, hasFocus, row, column);
            // Check if the cell contains "Snowboarding" and set the background color to green
            if ("DAY BREAK".equals(value)) {
                c.setBackground(Color.RED);
            } else if ("BREAK".equals(value)) {
                c.setBackground(new Color(46,49,146));
                c.setForeground(Color.WHITE);
            } else {
                c.setBackground(table.getBackground());
                c.setForeground(Color.BLACK);
            }
            return c;
        }
    };

    /**
     * Constructor
     * @param filePath The file to be loaded into the table contained in this panel
     * @param gui A back reference to TableGUI which holds this TablePanel
     */
    public TablePanel(String filePath, TableGUI gui) {
        String[] column_names = {"Start", "Duration(min.)", "Name", "Summary", "URL"};

        // Table panel
        setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(scheduleTable);
        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //scrollPane.setPreferredSize(new Dimension(700, 400));
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
        // Set the cell renderer that will display BREAK in green and DAY BREAK in red
        scheduleTable.getColumnModel().getColumn(2).setCellRenderer(renderer);
        add(scrollPane);
    }

    /**
     * Returns the model of the table
     * @return
     */
    public ScheduleTableModel getModel() {
        return scheduleTableModel;
    }

    /**
     * Returns the table contained in the panel
     * @return
     */
    public JTable getTable() {
        return scheduleTable;
    }



}
