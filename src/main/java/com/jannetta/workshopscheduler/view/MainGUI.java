// See the end of the class for comments on changes
package com.jannetta.workshopscheduler.view;

import javax.swing.JFrame;
import javax.swing.*;
import java.io.File;
import java.nio.file.Path;

/**
 * takes input from the user to create a CSV or/and HTML
 * the name of the lesson coincides with the name of the output file
 */
public class MainGUI extends JFrame {

    public static JTextArea viewCsvTextArea;
    private final String currentPath = Path.of("").toAbsolutePath().toString() + "/";
    private String currentCSVFile = "Python.csv";
    JPanel panel = new JPanel();

    public MainGUI() {
        JFileChooser file_upload = new JFileChooser(currentPath);
        int res_2 = file_upload.showOpenDialog(null);
        if (res_2 == JFileChooser.APPROVE_OPTION) {
            File file_to_load = file_upload.getSelectedFile();
            currentCSVFile = file_to_load.getName();
            TableGUI tableGUI = new TableGUI(currentCSVFile);
        }
    }
}


/**
 * 2023-07-01: Renamed variables again to be fully qualified names
 * 2023-07-01: Used lambdas to move actioncommands into functions, making it easier to follow the code
 */