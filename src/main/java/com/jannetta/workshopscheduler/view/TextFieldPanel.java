package com.jannetta.workshopscheduler.view;

import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import static com.jannetta.workshopscheduler.controller.Utilities.updateTimes;

public class TextFieldPanel extends JPanel {
    JTextField titleTextField = new JTextField("Python");
    JTextField startTimeTextField = new JTextField("10:00");

    public TextFieldPanel(TableGUI gui) {
        startTimeTextField.addActionListener(e -> updateTimes(gui));
        MigLayout migLayout = new MigLayout("fillx", "[]rel[]", "[]10[]");
        setLayout(migLayout);
        add(titleTextField, "grow");
        add(startTimeTextField, "grow, wrap");
    }

    public JTextField getTitleTextField() {
        return titleTextField;
    }

    public JTextField getStartTimeTextField() {
        return startTimeTextField;
    }
}
