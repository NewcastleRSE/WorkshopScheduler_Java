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

import controller.CSVReaderScanner;

//takes input from the user to create a CSV
//the name of the lesson coincides with the name of the output file
//it appends every line to the same file as long as the title of the lesson/file stays the same
//work in progress; need to include a construct and create a separate class for the ActionEvent
public class GUI {

  //private JLabel label;
  //private JPanel panel;
  private JFrame f;
  private JTextField t0;
  private JTextArea t1;
  private JTextField t4;

  private JButton b1,b2,b3;
  private JLabel l1,l2;

  public GUI(){
    f = new JFrame("Scheduler");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    l1=new JLabel("Welcome to The Scheduler. It converts CSVs into HTMLs. In other words, endless fun!");
    l1.setFont(new Font("Calibri", Font.ITALIC, 18));
    l1.setBounds(50,50, 750,30);
    l2=new JLabel("Write or");
    l2.setBounds(115,165, 190,40);
    l2.setFont(new Font("Calibri", Font.PLAIN, 16));

    t0 = new JTextField("Insert name lesson");
//    t0.setToolTipText();
    t0.setBounds(100, 100, 200, 30);
    t1 = new JTextArea("");
    t1.setBounds(115, 230, 500, 300);
//    t1 = new JTextField("Insert duration (minutes), eg. 30");
//    t1.setBounds(50, 150, 200, 30);
//    t2 = new JTextField("Insert name episode");
//    t2.setBounds(50, 200, 200, 30);
//    t3 = new JTextField("Insert URL");
//    t3.setBounds(50, 250, 200, 30);
    t4 = new JTextField("Insert start hour");
    t4.setBounds(430, 100, 200, 30);


    b1 = new JButton("Create CSV");
    b1.setBounds(115, 550, 100, 50);
    b2 = new JButton("Upload CSV");
    b2.setBounds(190, 165, 100, 50);
    b3 = new JButton("Create HTML");
    b3.setBounds(240, 550, 100, 50);
//    b1.addActionListener(this);


    f.add(t0);
    f.add(t1);
//    f.add(t2);
//    f.add(t3);
    f.add(t4); //do something with t4
    f.add(b1);
    f.add(b2);
    f.add(b3);
    f.add(l1); f.add(l2);

    f.setSize(850, 750);
    f.setLayout(null);
    f.setVisible(true);

    ActionListener buttonListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

//        JOptionPane.showMessageDialog(null, "fucking hell");

    File file = new File(t0.getText()+".csv");

      try {
        try (FileWriter writer = new FileWriter(file, true)) {

          writer.write(t1.getText());

        }
//        System.out.println("Progress saved");
      } catch (IOException | HeadlessException z) {
        JOptionPane.showMessageDialog(null, e);
      }
//        String startTime = t4.getText();
//        CSVReaderScanner.main("/tmp/" + t0.getText(), startTime);
      }
    };

    ActionListener buttonListener2 = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        if(e.getSource()==b2){
          JFileChooser file_upload = new JFileChooser();
          int res_2 = file_upload.showOpenDialog(null);
          if (res_2 == JFileChooser.APPROVE_OPTION){
            String startTime = t4.getText();
            CSVReaderScanner.main(file_upload.getSelectedFile().getAbsolutePath(), startTime);
          }
        }

      }
    };

    b1.addActionListener(buttonListener);
    b2.addActionListener(buttonListener2);

  }

  public static void main(String args[]) {
        new GUI();
  }
}
