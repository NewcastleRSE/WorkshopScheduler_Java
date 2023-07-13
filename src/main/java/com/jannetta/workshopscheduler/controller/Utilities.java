package com.jannetta.workshopscheduler.controller;

import com.jannetta.workshopscheduler.view.TableGUI;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class Utilities {

    public static void updateTimes(TableGUI gui) {
        Vector<Vector> data = gui.getTablePanel().getModel().getDataVector();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date d = null;
        String startTime = gui.getTextFieldPanel().getStartTimeTextField().getText();
        try {
            d = df.parse(startTime);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            String previousData = "0";
            for (int row = 0; row < data.size(); row++) {
                if (row == 0) {
                    data.get(0).set(0, startTime);
                } else {
                    calendar.add(Calendar.MINUTE, Integer.parseInt(previousData));
                    data.get(row).set(0, df.format(calendar.getTime()));
                }
                int columns = data.get(row).size();
                for (int col = 0; col < columns; col++) {
                    System.out.print(data.get(row).get(col) + "\t");
                }
                System.out.println();
                //calendar.add(Calendar.MINUTE, Integer.parseInt(previousData));
                startTime = df.format(calendar.getTime());
                previousData = (String) data.get(row).get(1);
            }
            gui.getTablePanel().updateUI();
            System.out.println("Update UI");
        } catch (ParseException e) {
            String input = JOptionPane.showInputDialog(null, "The start time you entered is not in the correct format.");
            gui.getTextFieldPanel().getStartTimeTextField().setText(input);
        }
    }
}
