package com.jannetta.workshopscheduler.model;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Table model for handling data of the schedule table
 */
public class ScheduleTableModel extends DefaultTableModel {

    public ScheduleTableModel(Vector<Vector<String>> data, Vector<String> column_names ) {
        super(data, column_names);
    }
}
