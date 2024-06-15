package com.jannetta.workshopscheduler.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;

/**
 * Table model for handling data of the schedule table
 */
public class ScheduleTableModel extends DefaultTableModel implements TableModelListener {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    boolean dirtyFlag = false;

    public ScheduleTableModel(Vector<Vector<String>> data, Vector<String> column_names, boolean dirtyFlag ) {
        super(data, column_names);
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        logger.debug("Change to table content detected");
        dirtyFlag = true;
    }
}
