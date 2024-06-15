package com.jannetta.workshopscheduler.view;

import com.jannetta.workshopscheduler.controller.FileUtilities;
import com.jannetta.workshopscheduler.controller.Globals;
import com.jannetta.workshopscheduler.model.Schedule;
import net.miginfocom.swing.MigLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Path;
import java.util.Vector;

/**
 * Frame for displaying schedule as a table
 */
public class TableGUI extends JFrame {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String currentPath = Path.of("").toAbsolutePath().toString() + "/";
    private final TablePanel tablePanel;
    private final TextFieldPanel textFieldPanel;
    private final ButtonPanel buttonPanel;
    private final Globals globals;
    private boolean dirtyFlag = false;

    public TableGUI(String filePath, Globals globals) {
        this.globals = globals;
        MigLayout migLayout = new MigLayout("fillx", "", "[]10[]10[]");
        setLayout(migLayout);
        textFieldPanel = new TextFieldPanel(this);
        textFieldPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tablePanel = new TablePanel(filePath, this);
        tablePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        buttonPanel = new ButtonPanel(this, globals);
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent arg0) {
                if (!dirtyFlag) {
                    logger.debug("Close window");
                } else {
                    logger.debug("Prompt to save file");
                    buttonPanel.saveChangesButtListener(getTablePanel().getModel().getDataVector(),
                            getTextFieldPanel().getStartTimeTextField().getText(),
                            getTextFieldPanel().getTitleTextField().getText(),
                            getTextFieldPanel().getExtraHeader().getText());
                }
            }
        });

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
        FileNameExtensionFilter extFilter = new FileNameExtensionFilter("Comma Separated Value File", "csv", "CSV");
        file_upload.addChoosableFileFilter(extFilter);
        int res_2 = file_upload.showOpenDialog(null);
        if (res_2 == JFileChooser.APPROVE_OPTION) {
            File file_to_load = file_upload.getSelectedFile();
            String currentCSVFile = file_to_load.getAbsolutePath();
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

    public boolean isDirtyFlag() {
        return dirtyFlag;
    }

    public void setDirtyFlag(boolean dirtyFlag) {
        this.dirtyFlag = dirtyFlag;
    }
}

/*
 * 2023-07-01: Change actionEvents to lambdas
 * 2023-07-01: Refactor variable names to use fully qualified names
 * 2023-07-01: Extract column names to an array
 */