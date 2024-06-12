package com.jannetta.workshopscheduler.view;

import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import static com.jannetta.workshopscheduler.controller.Utilities.updateTimes;

public class TextFieldPanel extends JPanel {
    JTextField titleTextField = new JTextField("Python", 25);
    JTextField startTimeTextField = new JTextField("10:00", 25);

    public TextFieldPanel(TableGUI gui) {
        titleTextField.setToolTipText("This field should contain the lesson name");
        startTimeTextField.addActionListener(e -> updateTimes(gui));
        startTimeTextField.setToolTipText("This field should contain the start time of the\nworkshop in the format: HH:MM using a 24 hour clock");
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
