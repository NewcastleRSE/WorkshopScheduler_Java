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
    LogoPanel logoPanel = new LogoPanel();
    JPanel textPanel = new JPanel();


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

        JTextArea aboutLabel = new JTextArea("Version: 1.1\nAuthors:\nJannetta S. Steyn (GitHub: @jsteyn)\nRuxandra Neatu (GitHub: @NeatuR)\n" +
                "\nWorkshopScheduler takes a CSV file \n" +
                "which consists of four columns,\n" +
                "duration, Episode name, a summary \n" +
                "and a URL and creates an HTML file\n" +
                "that can be included as schedule.html\n" +
                "in the _include directory of a\n" +
                "workshop website, created with the \n" +
                "Carpentries template.");

        setJMenuBar(new MainMenuBar());
        setLayout(new FlowLayout());

        textPanel.add(aboutLabel);
        add(logoPanel);
        add(textPanel);
        pack();
        setVisible(true);
        setSize(512, 800);
    }


}
