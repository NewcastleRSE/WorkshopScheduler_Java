// See the end of the class for comments on changes
package com.jannetta.workshopscheduler.view;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;


/**
 * takes input from the user to create a CSV or/and HTML
 * the name of the lesson coincides with the name of the output file
 */
public class MainGUI extends JFrame {

    public static JTextArea viewCsvTextArea;
    private final String currentPath = Path.of("").toAbsolutePath().toString() + "/";
    private String currentCSVFile = "Python.csv";
    MainPanel panel = new MainPanel();

    public MainGUI() {
        setTitle("Workshop Scheduler");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        try {
            Image icon = toolkit.getImage(ClassLoader.getSystemResource("favicon.png"));
            setIconImage(icon);
        } catch (NullPointerException e) {
            System.out.println("favicon.png not found.");
        }
        setJMenuBar(new MainMenuBar());
        add(panel);
        pack();
        setVisible(true);
        setSize(512, 512);
    }


}
