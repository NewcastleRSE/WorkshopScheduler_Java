package com.jannetta.workshopscheduler.controller;

import com.jannetta.workshopscheduler.view.TableGUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

public class Utilities {

    private static final Logger logger = LoggerFactory.getLogger(Utilities.class);
    private static final String propertyFile = "system.properties";

    public static void updateTimes(TableGUI gui) {
        Vector<Vector> data = gui.getTablePanel().getModel().getDataVector();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date d;
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
            String input = JOptionPane.showInputDialog(null, "The start time you entered is not in the correct format. Please enter it as HH:MM using a 24 hour clock.");
            gui.getTextFieldPanel().getStartTimeTextField().setText(input);
        }
    }

    /**
     * Load properties from system.properties file
     */
    public static Properties loadProperties(String configDirectory) {
        String propertiesFile = configDirectory.concat(propertyFile);
        logger.trace("Load properties from " + propertiesFile);
        Properties properties = new Properties();
        try {
            File f = new File(propertiesFile);
            // If the file doesn't exist, create it
            Files.createDirectories(Paths.get(configDirectory));

            if (!(f.exists())) {
                OutputStream out = new FileOutputStream(f);
                logger.trace("Create ".concat(f.getAbsolutePath()));
                out.close();
            }
            InputStream is = new FileInputStream(f);
            properties.load(is);
            if (properties.isEmpty()) {
                logger.trace("Add basic properties");
                properties.setProperty("workingDirectory", System.getProperty("user.dir"));
                properties.setProperty("configDirectory", System.getProperty("user.home").concat("/.workshopscheduler/"));
            }
            FileOutputStream out = new FileOutputStream(propertiesFile);
            properties.store(out, "");
            is.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static void saveProperties() {
    }

    /**
     * Set the specified property with the specified value
     *
     */
    public static void savePropertyFile(Properties properties, String configDirectory) {
        File f = new File(configDirectory.concat("/system.properties"));
        logger.debug("Save file: " + f.getAbsolutePath());
        logger.debug(properties.getProperty("workingDirectory"));
        try {
            OutputStream out = new FileOutputStream(f);
            properties.store(out, "This is an optional header comment string");
            out.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


}
