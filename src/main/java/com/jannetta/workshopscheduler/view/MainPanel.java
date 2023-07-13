package com.jannetta.workshopscheduler.view;


import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image image = Toolkit.getDefaultToolkit().getImage("icon.png");
        g.drawImage(image, 0, 0, this);
    }
}
