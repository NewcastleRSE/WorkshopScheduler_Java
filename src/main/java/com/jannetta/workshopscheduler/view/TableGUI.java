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

        MainMenuBar menuBar = new MainMenuBar();

        setJMenuBar(menuBar);
        add(textFieldPanel, "span, grow");
        add(tablePanel, "wrap");
        add(buttonPanel, "span, grow");
        setVisible(true);
        pack();
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