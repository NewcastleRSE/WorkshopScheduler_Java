// See the end of the class for comments on changes
package com.jannetta.workshopscheduler.view;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.*;
import javax.swing.JMenuBar;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;

/**
 * takes input from the user to create a CSV or/and HTML
 * the name of the lesson coincides with the name of the output file
 */
public class MainGUI extends JFrame{

    private final JTextField lessonNameTextField;
    public static JTextArea viewCsvTextArea;
    private final JTextField startTimeTextField;
    private final JLabel welcomeTextLabel;
    private final String currentPath = Path.of("").toAbsolutePath().toString() + "/";
    private String currentCSVFile = "";
    JPanel panel = new JPanel();

    public MainGUI() {
        System.out.println("Current path: " + currentPath);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        GridBagLayout gridBagLayout = new GridBagLayout();
        panel.setLayout(gridBagLayout);

        JMenuBar menuBar = new JMenuBar();
        JMenu viewMenu = new JMenu("View");
        JMenuItem selectDarkMenuItem = new JMenuItem("Dark mode");
        JMenuItem selectLightMenuItem = new JMenuItem("Light mode");
        JMenu fileMenu = new JMenu("File");
        JMenuItem loadCsvMenuItem = new JMenuItem("Load CSV");
        JMenuItem saveCsvMenuItem = new JMenuItem("Save as CSV");

        viewMenu.add(selectDarkMenuItem);
        viewMenu.add(selectLightMenuItem);
        fileMenu.add(loadCsvMenuItem);
        fileMenu.add(saveCsvMenuItem);
        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
        lessonNameTextField = new JTextField("Python");
        lessonNameTextField.setToolTipText("Lesson name");
        gridBagConstraints.insets = new Insets(4, 4, 8, 4);
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;//row
        panel.add(lessonNameTextField, gridBagConstraints);

        startTimeTextField = new JTextField("10:00");
        startTimeTextField.setToolTipText("Start hour of the lesson");
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;//row
        panel.add(startTimeTextField, gridBagConstraints);

        welcomeTextLabel = new JLabel("<html><h2>Userguide</h2><p>Welcome to The Scheduler." +
                "Here you can convert your lesson into HTML. Given the start hour of your lesson, it will calculate the start time of each episode." +
                "It takes as input 4 columns, the duration of each episode (in minutes) must be first; to include an website the URL must start with 'https'.</p><p><a href=\"https://github.com/NewcastleRSE/WorkshopScheduler_Java\" target=\"_blank\">More info on GitHub</a></p></html>", SwingConstants.CENTER);
        welcomeTextLabel.setFont(new Font("Calibri", Font.PLAIN, 17));
        Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3, true);
        welcomeTextLabel.setBorder(border);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 3;
        panel.add(welcomeTextLabel, gridBagConstraints);

        viewCsvTextArea = new JTextArea("30,Python Fundamentals,Summary,https\n60,Analyzing Data,Summary,https");
        gridBagConstraints.insets = new Insets(4, 8, 12, 8);
        gridBagConstraints.ipady = 400;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 1;
        JScrollPane areaScrollPane = new JScrollPane(viewCsvTextArea);
        areaScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        areaScrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panel.add(areaScrollPane, gridBagConstraints);

        panel.setPreferredSize(new Dimension(700, 900));
        panel.setBorder(new EmptyBorder(50, 50, 50, 50));
        add(panel);
        setJMenuBar(menuBar);
        setMinimumSize(new Dimension(700, 900));
        setVisible(true);
        pack();


        saveCsvMenuItem.addActionListener(e -> {saveCSV();});
        loadCsvMenuItem.addActionListener(e -> {loadCSV();});
        selectDarkMenuItem.addActionListener(e -> selectDarkTheme());
        selectLightMenuItem.addActionListener(e -> selectLightTheme());

    }

    void saveCSV() {
        JFileChooser fileChooser = new JFileChooser(currentPath);
        fileChooser.setDialogTitle("Save CSV File");
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            currentCSVFile = fileToSave.getName();
            System.out.println("Save to file: " + currentCSVFile);
            if (fileToSave.exists()) {
                int overwriteConfirmation = JOptionPane.showConfirmDialog(null,
                        "The file already exists. Do you want to overwrite it?",
                        "File Exists", JOptionPane.YES_NO_OPTION);
                if (overwriteConfirmation == JOptionPane.NO_OPTION) {
                    return; // User chose not to overwrite, so exit the ActionListener
                }
            }
            try {
                try (FileWriter writer = new FileWriter(fileToSave, false)) {
                    writer.write(viewCsvTextArea.getText());
                }
            } catch (IOException | HeadlessException z) {
                JOptionPane.showMessageDialog(null, "Save CSV File");
            }
        }
    }

    public void loadCSV() {
        JFileChooser file_upload = new JFileChooser(currentPath);
        int res_2 = file_upload.showOpenDialog(null);
        if (res_2 == JFileChooser.APPROVE_OPTION) {
            File file_to_load = file_upload.getSelectedFile();
            currentCSVFile = file_to_load.getName();
            System.out.println("Loading file: " + currentCSVFile);
            try {
                BufferedReader input = new BufferedReader(new InputStreamReader(
                        new FileInputStream(file_to_load)));
                viewCsvTextArea.read(input, "READING FILE :-)");
                new TableView(currentCSVFile, startTimeTextField.getText());

            } catch (Exception ae) {
                ae.printStackTrace();
            }
        } else {
            System.out.println("Operation is CANCELLED :(");

        }
    }

    public void selectDarkTheme() {
        panel.setBackground(Color.BLACK);
        lessonNameTextField.setBackground(Color.GRAY);
        viewCsvTextArea.setBackground(Color.GRAY);
        startTimeTextField.setBackground(Color.GRAY);
        welcomeTextLabel.setForeground(Color.WHITE);
    }

    public void selectLightTheme() {
        panel.setBackground(new java.awt.Color(238, 238, 238));
        lessonNameTextField.setBackground(Color.WHITE);
        viewCsvTextArea.setBackground(Color.WHITE);
        startTimeTextField.setBackground(Color.WHITE);
        welcomeTextLabel.setForeground(Color.BLACK);
    }

    private boolean isValidStartTime(String startTime) {
        return startTime.matches("\\d{2}:\\d{2}");
    }
}

/**
 * 2023-07-01: Renamed variables again to be fully qualified names
 * 2023-07-01: Used lambdas to move actioncommands into functions, making it easier to follow the code
 */