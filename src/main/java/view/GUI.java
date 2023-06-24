package view;

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
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;


import controller.CSVReaderScanner;
import controller.tableCSV;

/**
 * takes input from the user to create a CSV or/and HTML
 * the name of the lesson coincides with the name of the output file
 * variable name conventions:
 * fr - frame
 * tf - textfield
 * mi - menu item
 * ta - text area
 * bl - button listener
 * btn - button
 * tbl - table
 * lbl - label
 * mil - menu item listener
 */
public class GUI {

    private JFrame fr_MainFrame;
    private JTextField tf_LessonName;
    public static JTextArea ta_ViewCSV;
    private JTextField tf_StartingTime;
    private JButton btn_EditTable, btn_CreateHTML;
    private JLabel lbl_WelcomeText;
    private JMenuItem mi_SelectDark, mi_SelectLight, mi_LoadCSV, mi_SaveCSV;
    private String currentPath = Path.of("").toAbsolutePath().toString() + "/";
    private String currentCSVFile = "";

    public GUI() {
        System.out.println("Current path: " + currentPath);
        fr_MainFrame = new JFrame("Scheduler");
        fr_MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);

        JMenuBar menuBar = new JMenuBar();
        JMenu viewMenu = new JMenu("View");
        mi_SelectDark = new JMenuItem("Dark mode");
        mi_SelectLight = new JMenuItem("Light mode");
        JMenu fileMenu = new JMenu("File");
        mi_LoadCSV = new JMenuItem("Load CSV");
        mi_SaveCSV = new JMenuItem("Save as CSV");

        viewMenu.add(mi_SelectDark);
        viewMenu.add(mi_SelectLight);
        fileMenu.add(mi_LoadCSV);
        fileMenu.add(mi_SaveCSV);
        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
        tf_LessonName = new JTextField("Python");
        tf_LessonName.setToolTipText("Lesson name");
        gridBagConstraints.insets = new Insets(4, 4, 8, 4);
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;//row
        panel.add(tf_LessonName, gridBagConstraints);

        tf_StartingTime = new JTextField("10:00");
        tf_StartingTime.setToolTipText("Start hour of the lesson");
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;//row
        panel.add(tf_StartingTime, gridBagConstraints);

        btn_EditTable = new JButton("Edit as table");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 1;
        panel.add(btn_EditTable, gridBagConstraints);

        btn_CreateHTML = new JButton("Create HTML");
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 1;
        panel.add(btn_CreateHTML, gridBagConstraints);

        lbl_WelcomeText = new JLabel("<html><h2>Userguide</h2><p>Welcome to The Scheduler." +
                "Here you can convert your lesson into HTML. Given the start hour of your lesson, it will calculate the start time of each episode." +
                "It takes as input 4 columns, the duration of each episode (in minutes) must be first; to include an website the URL must start with 'https'.</p><p><a href=\"https://github.com/NewcastleRSE/WorkshopScheduler_Java\" target=\"_blank\">More info on GitHub</a></p></html>", SwingConstants.CENTER);
        lbl_WelcomeText.setFont(new Font("Calibri", Font.PLAIN, 17));
        Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3, true);
        lbl_WelcomeText.setBorder(border);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 3;
        panel.add(lbl_WelcomeText, gridBagConstraints);

        ta_ViewCSV = new JTextArea("30,Python Fundamentals,Summary,https\n60,Analyzing Data,Summary,https");
        gridBagConstraints.insets = new Insets(4, 8, 12, 8);
        gridBagConstraints.ipady = 400;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 1;
        JScrollPane areaScrollPane = new JScrollPane(ta_ViewCSV);
        areaScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        areaScrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//    panel.add(t1,gridBagConstraints);
        panel.add(areaScrollPane, gridBagConstraints);

        panel.setPreferredSize(new Dimension(700, 900));
        panel.setBorder(new EmptyBorder(50, 50, 50, 50));
        fr_MainFrame.add(panel);
        fr_MainFrame.setJMenuBar(menuBar);
        fr_MainFrame.setMinimumSize(new Dimension(700, 900));
        fr_MainFrame.setVisible(true);
        fr_MainFrame.pack();

        ActionListener bl_SaveCSV = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                            writer.write(ta_ViewCSV.getText());
                        }
                    } catch (IOException | HeadlessException z) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                }
            }
        };


        ActionListener mil_LoadCSV = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (e.getSource() == mi_LoadCSV) {
                    JFileChooser file_upload = new JFileChooser(currentPath);
                    int res_2 = file_upload.showOpenDialog(null);
                    if (res_2 == JFileChooser.APPROVE_OPTION) {
                        File file_to_load = file_upload.getSelectedFile();
                        currentCSVFile = file_to_load.getName();
                        System.out.println("Loading file: " + currentCSVFile);
                        try {
                            BufferedReader input = new BufferedReader(new InputStreamReader(
                                    new FileInputStream(file_to_load)));
                            ta_ViewCSV.read(input, "READING FILE :-)");
                        } catch (Exception ae) {
                            ae.printStackTrace();
                        }
                    } else {
                        System.out.println("Operation is CANCELLED :(");

                    }
                }
            }
        };

        ActionListener bl_CreateHTML = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new File(tf_LessonName.getText() + ".csv");
                try {
                    try (FileWriter writer = new FileWriter(file, false)) {
                        writer.write(ta_ViewCSV.getText());
                    }
                } catch (IOException | HeadlessException z) {
                    JOptionPane.showMessageDialog(null, e);
                }
                String startTime = tf_StartingTime.getText();
                // Check if startTime is a valid value
                if (!isValidStartTime(startTime)) {
                    JOptionPane.showMessageDialog(null, "Invalid start time. Please enter a valid value to match the format \"hh:mm\". Example: 10:00");
                    return; // Exit the ActionListener if the start time is invalid
                }
                System.out.println("Filename: " + currentPath + currentCSVFile);
                CSVReaderScanner.main(currentPath + currentCSVFile, startTime, currentCSVFile.substring(0, currentCSVFile.length() - 4));
            }

            private boolean isValidStartTime(String startTime) {
                return startTime.matches("\\d{2}:\\d{2}");
            }
        };

        ActionListener mil_SelectDark = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setBackground(Color.BLACK);
                tf_LessonName.setBackground(Color.GRAY);
                ta_ViewCSV.setBackground(Color.GRAY);
                tf_StartingTime.setBackground(Color.GRAY);
                lbl_WelcomeText.setForeground(Color.WHITE);

            }
        };

        ActionListener mil_SelectLight = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setBackground(new java.awt.Color(238, 238, 238));
                tf_LessonName.setBackground(Color.WHITE);
                ta_ViewCSV.setBackground(Color.WHITE);
                tf_StartingTime.setBackground(Color.WHITE);
                lbl_WelcomeText.setForeground(Color.BLACK);
            }
        };

        ActionListener bl_EditTable = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == btn_EditTable) {
//            tableGUI table = new tableGUI();
                    File file = new File(tf_LessonName.getText() + ".csv");

                    try {
                        try (FileWriter writer = new FileWriter(file, false)) {

                            writer.write(ta_ViewCSV.getText());

                        }
//        System.out.println("Progress saved");
                    } catch (IOException | HeadlessException z) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                    tableCSV csvTable = new tableCSV(tf_LessonName.getText() + ".csv");

                }
            }
        };

        mi_SaveCSV.addActionListener(bl_SaveCSV);
        mi_LoadCSV.addActionListener(mil_LoadCSV);
        btn_CreateHTML.addActionListener(bl_CreateHTML);
        mi_SelectDark.addActionListener(mil_SelectDark);
        mi_SelectLight.addActionListener(mil_SelectLight);
        btn_EditTable.addActionListener(bl_EditTable);

    }

    public static void main(String[] args) {
        new GUI();
    }
}
