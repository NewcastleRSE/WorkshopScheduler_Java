package view;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
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

  private JFrame f;
  private JTextField t0;
  private JTextArea t1;
  private JTextField t4;
  private JButton b1,b2,b3;
  private JLabel l5;
  JPanel panel = new JPanel();

  public GUI(){
    f = new JFrame("Scheduler");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    GridBagConstraints gbc = new GridBagConstraints();
    GridBagLayout layout = new GridBagLayout();
    panel.setLayout(layout);


    t0 = new JTextField("Python");
    gbc.insets = new Insets(4,4,8,4);
    gbc.fill=GridBagConstraints.BOTH;
    gbc.weighty=0.1;
    gbc.weightx=0.1;
    gbc.gridx=0;
    gbc.gridy=2;//row
    panel.add(t0,gbc);

    t4 = new JTextField("10:00");
    gbc.gridx=1;
    gbc.gridy=2;//row
    panel.add(t4,gbc);

    b1 = new JButton("Create CSV");
    gbc.gridx=0;
    gbc.gridy=5;
    gbc.gridwidth = 1;
    panel.add(b1,gbc);

    b2 = new JButton("Upload CSV");
    gbc.gridx=0;
    gbc.gridy=4;
    gbc.gridwidth = 1;
    panel.add(b2,gbc);

    b3 = new JButton("Create HTML");
    gbc.gridx=0;
    gbc.gridy=6;
    gbc.gridwidth = 1;
    panel.add(b3,gbc);

    l5=new JLabel("<html><h2>Userguide</h2><p>Welcome to The Scheduler. Just add the episodes of your lessons and convert them into HTML.Type as many columns as you want, separated by commas, but the duration of each episode (in minutes) must be first; the links will be detected by the 'https' motif.</p></html>", SwingConstants.CENTER);
    l5.setFont(new Font("Calibri", Font.PLAIN, 17));
    Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3);
    l5.setBorder(border);
    gbc.gridx=1;
    gbc.gridy=4;
    gbc.gridheight = 3;
    panel.add(l5,gbc);

    t1 = new JTextArea("30,1,Python Fundamentals,Summary,https",15,0);
    gbc.insets = new Insets(4,8,12,8);
    gbc.ipady = 400;
    gbc.gridx=0;
    gbc.gridy=3;
    gbc.gridwidth = 2;
    gbc.gridheight = 1;
    JScrollPane areaScrollPane = new JScrollPane(t1);
    areaScrollPane.setVerticalScrollBarPolicy(
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    areaScrollPane.setHorizontalScrollBarPolicy(
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//    panel.add(t1,gbc);
    panel.add(areaScrollPane,gbc);


//    panel.setBackground(Color.BLACK);
    panel.setPreferredSize(new Dimension(700, 900));
    panel.setBorder(new EmptyBorder(50, 50, 50, 50));
    f.add(panel);
    f.setVisible(true);
    f.pack();



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
