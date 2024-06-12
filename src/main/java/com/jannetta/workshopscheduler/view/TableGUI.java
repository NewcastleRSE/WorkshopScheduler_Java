package com.jannetta.workshopscheduler.view;

import com.jannetta.workshopscheduler.controller.FileUtilities;
import com.jannetta.workshopscheduler.controller.Globals;
import com.jannetta.workshopscheduler.model.Schedule;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.util.Vector;

/**
 * Frame for displaying schedule as a table
 */
public class TableGUI extends JFrame {

    private String startTime;
    private final String currentPath = Path.of("").toAbsolutePath().toString() + "/";
    private final TablePanel tablePanel;
    private final TextFieldPanel textFieldPanel;
    private final ButtonPanel buttonPanel;
    private String currentCSVFile = "";
    private final Globals globals;

    public TableGUI(String filePath, Globals globals) {
        this.globals = globals;
        this.startTime = startTime;
        MigLayout migLayout = new MigLayout("fillx", "", "[]10[]10[]");
        setLayout(migLayout);
        textFieldPanel = new TextFieldPanel(this);
        textFieldPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tablePanel = new TablePanel(filePath, this);
        tablePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        buttonPanel = new ButtonPanel(this, globals);
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        MainMenuBar menuBar = new MainMenuBar(globals);
        setJMenuBar(menuBar);
        JMenuItem importFile = new JMenuItem("Import CSV (loads file into current)");
        JMenu menu = menuBar.getMenu(0);
        importFile.addActionListener(e -> {importSchedule();});
        menu.add(importFile);
        add(textFieldPanel, "wrap");
        add(tablePanel, "grow, wrap");
        add(buttonPanel, "");
        setVisible(true);
        pack();
    }

    private void importSchedule() {
        String currentPath = globals.getProperties().getProperty("workingDirectory");
        JFileChooser file_upload = new JFileChooser(currentPath);
        int res_2 = file_upload.showOpenDialog(null);
        if (res_2 == JFileChooser.APPROVE_OPTION) {
            File file_to_load = file_upload.getSelectedFile();
            currentCSVFile = file_to_load.getAbsolutePath();
            globals.getProperties().setProperty("workingDirectory", file_to_load.toPath().getParent().toString());
            Schedule schedule = FileUtilities.readData(new File(currentCSVFile));
            schedule.getSchedule().forEach(s -> {
                tablePanel.getModel().addRow((Vector<?>) s);
            });

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

    public String getCurrentPath() {
        return currentPath;
    }
}

/*
 * 2023-07-01: Change actionEvents to lambdas
 * 2023-07-01: Refactor variable names to use fully qualified names
 * 2023-07-01: Extract column names to an array
 */