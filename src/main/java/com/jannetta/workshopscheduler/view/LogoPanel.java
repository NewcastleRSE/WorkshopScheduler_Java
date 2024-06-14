package com.jannetta.workshopscheduler.view;


import javax.swing.*;
import java.awt.*;

public class LogoPanel extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage(ClassLoader.getSystemResource("icon.png"));
        g.drawImage(image, 0, 0, this);
        setPreferredSize(new Dimension(512,512));
        revalidate();
        repaint();
    }
}
