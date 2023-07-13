package com.jannetta.workshopscheduler.view;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;

/**
 * Frame for displaying schedule as a table
 */
public class TableGUI extends JFrame {



    private JTextArea mainText = MainGUI.viewCsvTextArea;
    private String startTime;
    private String currentCSVFile = "";
    private final String currentPath = Path.of("").toAbsolutePath().toString() + "/";
    private TablePanel tablePanel;
    private TextFieldPanel textFieldPanel;
    private ButtonPanel buttonPanel;

    public TableGUI(String filePath) {
        this.startTime = startTime;
        MigLayout migLayout = new MigLayout("fillx", "[]rel[]", "[]10[]");
        setLayout(migLayout);
        textFieldPanel = new TextFieldPanel(this);
        tablePanel = new TablePanel(filePath, this);
        buttonPanel = new ButtonPanel(this);

        JMenuBar menuBar = new JMenuBar();
        JMenu viewMenu = new JMenu("View");
        JMenuItem selectDarkMenuItem = new JMenuItem("Dark mode");
        JMenuItem selectLightMenuItem = new JMenuItem("Light mode");
        JMenu fileMenu = new JMenu("File");
        JMenuItem loadCsvMenuItem = new JMenuItem("Load CSV");
        loadCsvMenuItem.addActionListener(e -> {loadCSV();});
        fileMenu.add(loadCsvMenuItem);
        menuBar.add(fileMenu);

        setJMenuBar(menuBar);
        add(textFieldPanel, "span, grow");
        add(tablePanel, "wrap");
        add(buttonPanel, "span, grow");
        setVisible(true);
        pack();
    }

    private void loadCSV() {
        JFileChooser file_upload = new JFileChooser(currentPath);
        int res_2 = file_upload.showOpenDialog(null);
        if (res_2 == JFileChooser.APPROVE_OPTION) {
            File file_to_load = file_upload.getSelectedFile();
            currentCSVFile = file_to_load.getName();
            TableGUI tableGUI = new TableGUI(currentCSVFile);
        }
    }

    public TablePanel getTablePanel() {
        return tablePanel;
    }

    public TextFieldPanel getTextFieldPanel() {
        return textFieldPanel;
    }

    public ButtonPanel getButtonPanel() {
        return buttonPanel;
    }
}

/*
 * 2023-07-01: Change actionEvents to lambdas
 * 2023-07-01: Refactor variable names to use fully qualified names
 * 2023-07-01: Extract column names to an array
 */