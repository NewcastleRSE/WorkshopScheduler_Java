package com.jannetta.workshopscheduler.view;

import javax.swing.*;
import java.awt.*;

public class tableGUI {

  private JFrame f2 = new JFrame();
  private JLabel label = new JLabel("Hello");
  tableGUI(){
    label.setBounds(0,0,100,500);
    label.setFont(new Font(null,Font.PLAIN,25));
//    tableCSV CSVtable = new tableCSV("data/lesson.csv");
//    f2.add();

    f2.add(label);

    f2.setSize(700,900);
    f2.setMinimumSize(new Dimension(700, 900));
//    f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f2.setLayout(null);
    f2.setVisible(true);
  }

}
