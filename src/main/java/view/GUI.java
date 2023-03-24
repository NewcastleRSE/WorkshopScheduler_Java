package view;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.*;
import javax.swing.JMenuBar;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;


import controller.CSVReaderScanner;
import controller.tableCSV;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

//takes input from the user to create a CSV or/and HTML
//the name of the lesson coincides with the name of the output file
public class GUI {

  private JFrame f;
  private JTextField t0;
  public JTextArea t1;
  private JTextField t4;
  private JButton b2,b3;
  private JLabel l5;
  private JMenuItem i1, i2, i3, i4;

  public GUI(){
    f = new JFrame("Scheduler");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel panel = new JPanel();
    GridBagConstraints gbc = new GridBagConstraints();
    GridBagLayout layout = new GridBagLayout();
    panel.setLayout(layout);

    JMenuBar menuBar = new JMenuBar();
    JMenu viewMenu=new JMenu("View");
    JMenuItem i1 = new JMenuItem("Dark mode");
    JMenuItem i2 = new JMenuItem("Light mode");
    JMenu fileMenu= new JMenu("File");
    JMenuItem i3 = new JMenuItem("Load CSV");
    JMenuItem i4 = new JMenuItem("Save as CSV");

    viewMenu.add(i1);
    viewMenu.add(i2);
    fileMenu.add(i3);
    fileMenu.add(i4);
    menuBar.add(fileMenu);
    menuBar.add(viewMenu);
    t0 = new JTextField("Python");
    t0.setToolTipText("Lesson name");
    gbc.insets = new Insets(4,4,8,4);
    gbc.fill=GridBagConstraints.BOTH;
    gbc.weighty=0.1;
    gbc.weightx=0.1;
    gbc.gridx=0;
    gbc.gridy=2;//row
    panel.add(t0,gbc);

    t4 = new JTextField("10:00");
    t4.setToolTipText("Start hour of the lesson");
    gbc.gridx=1;
    gbc.gridy=2;//row
    panel.add(t4,gbc);

    b2 = new JButton("Edit as table");
    gbc.gridx=0;
    gbc.gridy=4;
    gbc.gridwidth = 1;
    panel.add(b2,gbc);

    b3 = new JButton("Create HTML");
    gbc.gridx=1;
    gbc.gridy=4;
    gbc.gridwidth = 1;
    panel.add(b3,gbc);

    l5=new JLabel("<html><h2>Userguide</h2><p>Welcome to The Scheduler." +
        "Just add the episodes of your lesson and convert them into HTML. Given the start hour of your lesson, it will calculate the start time of each episode." +
        "Type as many columns as you want, separated by commas, but the duration of each episode (in minutes) must be first; to include an website the URL must start with 'https'.</p><p><a href=\"https://github.com/NewcastleRSE/WorkshopScheduler_Java\" target=\"_blank\">More info on GitHub</a></p></html>", SwingConstants.CENTER);
    l5.setFont(new Font("Calibri", Font.PLAIN, 17));
    Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3,true);
    l5.setBorder(border);
    gbc.gridx=0;
    gbc.gridy=6;
    gbc.gridwidth = 2;
    gbc.gridheight = 3;
    panel.add(l5,gbc);

    t1 = new JTextArea("duration,index,name,summary,web\n30,1,Python Fundamentals,Summary,https\n60,2,Analyzing Data,Summary,https");
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


    panel.setPreferredSize(new Dimension(700, 900));
    panel.setBorder(new EmptyBorder(50, 50, 50, 50));
    f.add(panel);
    f.setJMenuBar(menuBar);
    f.setMinimumSize(new Dimension(700, 900));
    f.setVisible(true);
    f.pack();

    ActionListener buttonListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

//        JOptionPane.showMessageDialog(null, "hello");

    File file = new File(t0.getText()+".csv");

      try {
        try (FileWriter writer = new FileWriter(file, false)) {

          writer.write(t1.getText());

        }
//        System.out.println("Progress saved");
      } catch (IOException | HeadlessException z) {
        JOptionPane.showMessageDialog(null, e);
      }
      }
    };

    ActionListener menuItemListener3 = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        if(e.getSource()==i3){
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
          try (FileWriter writer = new FileWriter(file, false)) {

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

    ActionListener menuItemListener1 = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
            panel.setBackground(Color.BLACK);
            t0.setBackground(Color.GRAY);
            t1.setBackground(Color.GRAY);
            t4.setBackground(Color.GRAY);
            l5.setForeground(Color.WHITE);

//        b1.setOpaque(true);
//        b1.setContentAreaFilled(true);
//        b1.setBorderPainted(false);
//        b1.setFocusPainted(false);
//        b1.setBackground(Color.CYAN);
//        b1.setForeground(new java.awt.Color(204, 166, 166)); //letters
      }
    };

    ActionListener menuItemListener2 = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        panel.setBackground(new java.awt.Color(238, 238, 238));
        t0.setBackground(Color.WHITE);
        t1.setBackground(Color.WHITE);
        t4.setBackground(Color.WHITE);
        l5.setForeground(Color.BLACK);
      }
    };

    ActionListener buttonListener2 = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
          if(e.getSource()==b2){
//            tableGUI table = new tableGUI();
            File file = new File(t0.getText()+".csv");

            try {
              try (FileWriter writer = new FileWriter(file, false)) {

                writer.write(t1.getText());

              }
//        System.out.println("Progress saved");
            } catch (IOException | HeadlessException z) {
              JOptionPane.showMessageDialog(null, e);
            }
            tableCSV CSVtable = new tableCSV(t0.getText()+".csv");

          }
      }
    };

    i4.addActionListener(buttonListener);
    i3.addActionListener(menuItemListener3);
    b3.addActionListener(buttonListener3);
    i1.addActionListener(menuItemListener1);
    i2.addActionListener(menuItemListener2);
    b2.addActionListener(buttonListener2);

  }

  public static void main(String args[]) {
        new GUI();
  }
}
