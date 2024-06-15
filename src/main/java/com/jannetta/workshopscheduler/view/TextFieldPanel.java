package com.jannetta.workshopscheduler.view;

import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import static com.jannetta.workshopscheduler.controller.Utilities.updateTimes;

public class TextFieldPanel extends JPanel {
    private JTextField titleTextField = new JTextField("Python", 25);
    private JTextField startTimeTextField = new JTextField("10:00", 25);
    private JTextField extraHeader = new JTextField(50);

    public TextFieldPanel(TableGUI gui) {
        getTitleTextField().setToolTipText("This field should contain the lesson name");
        getStartTimeTextField().addActionListener(e -> updateTimes(gui));
        getStartTimeTextField().setToolTipText("This field should contain the start time of the\nworkshop in the format: HH:MM using a 24 hour clock");
        getExtraHeader().setToolTipText("This field can contain an extra header to be included above the table.");
        MigLayout migLayout = new MigLayout("fillx", "[]rel[]", "[]10[]");
        setLayout(migLayout);
        add(titleTextField, "grow");
        add(startTimeTextField, "grow, wrap");
        add(extraHeader, "span");
    }

    public JTextField getTitleTextField() {
        return titleTextField;
    }

    public JTextField getStartTimeTextField() {
        return startTimeTextField;
    }

    public JTextField getExtraHeader() {
        return extraHeader;
    }

    public void setExtraHeader(JTextField extraHeader) {
        this.extraHeader = extraHeader;
    }
}
