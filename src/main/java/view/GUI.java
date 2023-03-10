package view;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;

//takes input from the user to create a CSV
//the name of the lesson coincides with the name of the output file
//it appends every line to the same file as long as the title of the lesson/file stays the same
//work in progress; need to include a construct and create a separate class for the ActionEvent
public class GUI implements ActionListener{

  //private JLabel label;
  //private JPanel panel;
  private JFrame f;
  private JTextField t0,t1,t2,t3,t4;

  public GUI(){
    f = new JFrame("Scheduler");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JButton b1;

    t0 = new JTextField("Insert name lesson");
//    t0.setToolTipText();
    t0.setBounds(50, 100, 200, 30);
    t1 = new JTextField("Insert duration (minutes), eg. 30");
    t1.setBounds(50, 150, 200, 30);
    t2 = new JTextField("Insert name episode");
    t2.setBounds(50, 200, 200, 30);
    t3 = new JTextField("Insert URL");
    t3.setBounds(50, 250, 200, 30);
    t4 = new JTextField("Insert start hour");
    t4.setBounds(50, 50, 200, 30);


    b1 = new JButton("Enter");
    b1.setBounds(50, 300, 100, 50);
    b1.addActionListener(this);


    f.add(t0);
    f.add(t1);
    f.add(t2);
    f.add(t3);
    f.add(t4); //do something with t4
    f.add(b1);

    f.setSize(400, 400);
    f.setLayout(null);
    f.setVisible(true);

    //b1.addActionListener(new ActionListener() {
    //  @Override
      //public void actionPerformed(ActionEvent e) {}}
  }

  public static void main(String args[]) {
        new GUI();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
      File file = new File(t0.getText());

      try {
        try (FileWriter writer = new FileWriter(file, true)) {

          writer.write(t1.getText()+","+t2.getText()+","+t3.getText()+"\n");

        }
        System.out.println("Progress saved");
      } catch (IOException | HeadlessException z) {
        JOptionPane.showMessageDialog(null, e);
      }

  }
}
