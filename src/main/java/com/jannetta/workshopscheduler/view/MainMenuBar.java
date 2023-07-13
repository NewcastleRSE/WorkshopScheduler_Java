package com.jannetta.workshopscheduler.view;

import javax.swing.*;
import java.io.File;
import java.nio.file.Path;

public class MainMenuBar extends JMenuBar{
    private final String currentPath = Path.of("").toAbsolutePath().toString() + "/";
    private String currentCSVFile = "";

    public MainMenuBar() {
        JMenu viewMenu = new JMenu("View");
        JMenuItem selectDarkMenuItem = new JMenuItem("Dark mode");
        JMenuItem selectLightMenuItem = new JMenuItem("Light mode");
        JMenu fileMenu = new JMenu("File");
        JMenuItem loadCsvMenuItem = new JMenuItem("Load CSV");
        loadCsvMenuItem.addActionListener(e -> {
            loadCSV();
        });
        fileMenu.add(loadCsvMenuItem);
        add(fileMenu);
    }

    private void loadCSV() {
        JFileChooser file_upload = new JFileChooser(currentPath);
        int res_2 = file_upload.showOpenDialog(null);
        if (res_2 == JFileChooser.APPROVE_OPTION) {
            File file_to_load = file_upload.getSelectedFile();
            currentCSVFile = file_to_load.getName();
            TableGUI tableGUI = new TableGUI(currentCSVFile);
            tableGUI.setTitle(currentCSVFile);
        }
    }


}
