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
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;


import controller.CSVReaderScanner;

//takes input from the user to create a CSV or/and HTML
//the name of the lesson coincides with the name of the output file
public class GUI {

  //private JLabel label;
  //private JPanel panel;
  private JFrame f;
  private JTextField t0;
  private JTextArea t1;
  private JTextField t4;

  private JButton b1,b2,b3;
  private JLabel l1,l2,l3,l4,l5;
  JPanel panel = new JPanel();


  public GUI(){
    f = new JFrame("Scheduler");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    panel.setLayout(new GroupLayout());
    GroupLayout layout = new GroupLayout(panel);
    panel.setLayout(layout);
    layout.setAutoCreateGaps(true);
    layout.setAutoCreateContainerGaps(true);


    l1=new JLabel("Welcome to The Scheduler. Just add the episodes of your lessons and convert them into HTML.");
    l1.setFont(new Font("Calibri", Font.ITALIC, 18));
    l1.setBounds(50,20, 850,30);
    l2=new JLabel("Write or");
    l2.setBounds(115,165, 190,40);
    l2.setFont(new Font("Calibri", Font.PLAIN, 16));
    l3=new JLabel("Lesson Name");
    l3.setBounds(100,80, 190,40);
    l3.setFont(new Font("Calibri", Font.PLAIN, 12));
    l4=new JLabel("Start hour");
    l4.setBounds(430,80, 190,40);
    l4.setFont(new Font("Calibri", Font.PLAIN, 12));
    l5=new JLabel("<html><p>Type as many columns as you want, separated by commas, but the duration of each episode (in minutes) must be first; the links will be detected by the 'https' motif.</p></html>", SwingConstants.CENTER);
    l5.setBounds(50,50, 800,30);
    l5.setFont(new Font("Calibri", Font.PLAIN, 13));

    t0 = new JTextField("Python");
    t0.setBounds(100, 110, 200, 30);
    t1 = new JTextArea("30,1,Python Fundamentals,Summary,https");
    t1.setBounds(115, 230, 500, 300);
    t4 = new JTextField("10:00");
    t4.setBounds(430, 110, 200, 30);
    JScrollPane scrollPane = new JScrollPane(t1,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);


    b1 = new JButton("Create CSV");
    b1.setBounds(115, 550, 100, 50);
    b2 = new JButton("Upload CSV");
    b2.setBounds(190, 165, 100, 50);
    b3 = new JButton("Create HTML");
    b3.setBounds(240, 550, 100, 50);
//    b1.addActionListener(this);



    panel.add(scrollPane);
    f.setContentPane(panel);
    f.add(t0);
    f.add(t1);
//    f.add(t2);
//    f.add(t3);
    f.add(t4); //do something with t4
    f.add(b1);
    f.add(b2);
    f.add(b3);
    f.add(l1); f.add(l2);f.add(l3);f.add(l4);f.add(l5);

    f.setSize(1000, 800);
//    f.setLayout(null);
    f.setVisible(true);

//    layout.setHorizontalGroup(
//        layout.createSequentialGroup()
//            .addComponent(l3)
//            .addComponent(l4)
//            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
//                .addComponent(t0)
//                .addComponent(t4))
//    );
//    layout.setVerticalGroup(
//        layout.createSequentialGroup()
//            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
//                .addComponent(b1)
//                .addComponent(b2)
//                .addComponent(b3))
//            .addComponent(t1)
//    );

    ActionListener buttonListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

//        JOptionPane.showMessageDialog(null, "hello");

    File file = new File(t0.getText()+".csv");

      try {
        try (FileWriter writer = new FileWriter(file, true)) {

          writer.write(t1.getText());

        }
//        System.out.println("Progress saved");
      } catch (IOException | HeadlessException z) {
        JOptionPane.showMessageDialog(null, e);
      }
      }
    };

    ActionListener buttonListener2 = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        if(e.getSource()==b2){
          JFileChooser file_upload = new JFileChooser();
          int res_2 = file_upload.showOpenDialog(null);
          if (res_2 == JFileChooser.APPROVE_OPTION){
            File gummy = file_upload.getSelectedFile();
            try {
              BufferedReader input = new BufferedReader(new InputStreamReader(
                  new FileInputStream(gummy)));
              t1.read(input, "READING FILE :-)");
            } catch (Exception ae) {
              ae.printStackTrace();
            }
          } else {
            System.out.println("Operation is CANCELLED :(");
//            String startTime = t4.getText();
//            CSVReaderScanner.main(file_upload.getSelectedFile().getAbsolutePath(), startTime, t0.getText());
          }
        }

      }
    };

    ActionListener buttonListener3 = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        File file = new File(t0.getText()+".csv");

        try {
          try (FileWriter writer = new FileWriter(file, true)) {

            writer.write(t1.getText());

          }
//        System.out.println("Progress saved");
        } catch (IOException | HeadlessException z) {
          JOptionPane.showMessageDialog(null, e);
        }
        String startTime = t4.getText();
        CSVReaderScanner.main(file.getAbsolutePath(), startTime, t0.getText());
//        JOptionPane.showMessageDialog(null,  t0.getText());
//        System.out.println(t0.getText());
        }
    };

    b1.addActionListener(buttonListener);
    b2.addActionListener(buttonListener2);
    b3.addActionListener(buttonListener3);

  }

  public static void main(String args[]) {
        new GUI();
  }
}
