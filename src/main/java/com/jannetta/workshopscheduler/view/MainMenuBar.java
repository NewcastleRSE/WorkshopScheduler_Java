package com.jannetta.workshopscheduler.view;

import com.jannetta.workshopscheduler.controller.Globals;
import com.jannetta.workshopscheduler.controller.Utilities;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class MainMenuBar extends JMenuBar{
    private String currentPath = Path.of("").toAbsolutePath().toString() + "/";
    private String currentCSVFile = "";
    private Globals globals;

    public MainMenuBar(Globals globals) {
        this.globals = globals;
        JMenu viewMenu = new JMenu("View");
        JMenuItem selectDarkMenuItem = new JMenuItem("Dark mode");
        JMenuItem selectLightMenuItem = new JMenuItem("Light mode");
        JMenu fileMenu = new JMenu("File");
        JMenuItem loadCsvMenuItem = new JMenuItem("Load CSV (creates a new schedule)");
        JMenuItem newFile = new JMenuItem("New schedule (creates empty schedule");
        loadCsvMenuItem.addActionListener(e -> {loadCSV();});
        newFile.addActionListener(e -> {newSchedule();});
        fileMenu.add(loadCsvMenuItem);
        fileMenu.add(newFile);
        add(fileMenu);
    }


    private void loadCSV() {
        currentPath = globals.getProperties().getProperty("workingDirectory");
        JFileChooser file_upload = new JFileChooser(currentPath);
        int res_2 = file_upload.showOpenDialog(null);
        if (res_2 == JFileChooser.APPROVE_OPTION) {
            try {
                File file_to_load = file_upload.getSelectedFile();
                currentCSVFile = file_to_load.getAbsolutePath();
                TableGUI tableGUI = new TableGUI(currentCSVFile, globals);
                tableGUI.setTitle(file_to_load.getName());
                globals.getProperties().setProperty("workingDirectory", file_to_load.toPath().getParent().toString());
                System.out.println(file_to_load.getCanonicalPath());
                Utilities.savePropertyFile(globals.getProperties(), globals.getConfigDirectory());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void newSchedule() {
        TableGUI tableGUI = new TableGUI("", globals);
        tableGUI.setTitle("New schedule");
    }
}
