package controller;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import view.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Vector;

public class tableCSV {

  private JFrame f3 = new JFrame();
  private JTable jt = new JTable();
  private JButton remButt, addButt, lunchButt, breakButt, uploadButt;

  GUI gui =new GUI();
  JTextArea mainText = gui.t1;

  public tableCSV(String filePath){

//            parsing CSV
        File csv_data = new File(filePath);
        DefaultTableModel csvData = new DefaultTableModel();
    try {

      int start = 0;
      InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(csv_data));
      CSVParser csvParser = CSVFormat.DEFAULT.parse(inputStreamReader);
      for (CSVRecord csvRecord : csvParser) {
        if (start == 0) {
          start = 1;
          csvData.addColumn(csvRecord.get(0));
          csvData.addColumn(csvRecord.get(1));
          csvData.addColumn(csvRecord.get(2));
          csvData.addColumn(csvRecord.get(3));
          csvData.addColumn(csvRecord.get(4));
        } else {
          Vector row = new Vector();
          row.add(csvRecord.get(0));
          row.add(csvRecord.get(1));
          row.add(csvRecord.get(2));
          row.add(csvRecord.get(3));
          row.add(csvRecord.get(4));
          csvData.addRow(row);
        }
      }

    } catch (Exception ex) {
      System.out.println(ex);
      System.out.println("Error in Parsing CSV File");
    }

    JPanel panel2 = new JPanel();
    GridBagConstraints gbc2 = new GridBagConstraints();
    GridBagLayout layout = new GridBagLayout();
    panel2.setLayout(layout);

    remButt = new JButton("Remove row");
    gbc2.insets = new Insets(4,4,8,4);
    gbc2.gridx=0;
    gbc2.gridy=2;//row
    panel2.add(remButt,gbc2);
    addButt = new JButton("Add row");
    gbc2.gridx=1;
    gbc2.gridy=2;//row
    panel2.add(addButt,gbc2);
    lunchButt = new JButton("Add lunch");
    gbc2.insets = new Insets(4,4,8,4);
    gbc2.gridx=0;
    gbc2.gridy=3;//row
    panel2.add(lunchButt,gbc2);
    breakButt = new JButton("Add break");
    gbc2.gridx=1;
    gbc2.gridy=3;//row
    panel2.add(breakButt,gbc2);
    uploadButt = new JButton("Upload main CSV");
    gbc2.gridx=1;
    gbc2.gridy=0;//row
    panel2.add(uploadButt,gbc2);
    jt.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    JScrollPane sp=new JScrollPane(jt);
    gbc2.gridx=0;
    gbc2.gridy=4;
    gbc2.gridwidth = 2;
    panel2.add(sp, gbc2);

    ActionListener remButtListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        // check for selected row first
        if(jt.getSelectedRow() != -1) {
          // remove selected row from the model
          csvData.removeRow(jt.getSelectedRow());
        }
      }
    };

    ActionListener addButtListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        int rowIndex = jt.getSelectedRow();
        if(rowIndex != -1) {
//        JOptionPane.showMessageDialog(null, rowIndex);
          // add row from the model
          csvData.insertRow(rowIndex, new Object[]{"-", "-", "-", "-", "-"});
        } else {
          csvData.addRow(new Object[]{"-", "-", "-", "-", "-"});
        }
      }
    };

    ActionListener lunchButtListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        int rowIndex = jt.getSelectedRow();
        if(rowIndex != -1) {
//        JOptionPane.showMessageDialog(null, rowIndex);
          // add row from the model
          csvData.insertRow(rowIndex, new Object[]{"60", "LUNCH", "-", "-", "-"});
        } else {
          csvData.addRow(new Object[]{"60", "LUNCH", "-", "-", "-"});
        }
      }
    };

    ActionListener breakButtListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        int rowIndex = jt.getSelectedRow();
        if(rowIndex != -1) {
//        JOptionPane.showMessageDialog(null, rowIndex);
          // add row from the model
          csvData.insertRow(rowIndex, new Object[]{"15", "BREAK", "-", "-", "-"});
        } else {
          csvData.addRow(new Object[]{"15", "BREAK", "-", "-", "-"});
        }
      }
    };

    ActionListener uploadButtListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        mainText.selectAll();
        mainText.replaceSelection("");
        for(int i = 0; i < jt.getRowCount(); i++){
          String duration = jt.getValueAt(i, 0).toString();
          String index = jt.getValueAt(i, 1).toString();
          String name = jt.getValueAt(i, 2).toString();
          String summary = jt.getValueAt(i, 3).toString();
          String web = jt.getValueAt(i, 4).toString();
          mainText.setText(mainText.getText()+duration+","+index+","+name+","+summary+","+web+"\n");
        }
      }
    };

    remButt.addActionListener(remButtListener);
    addButt.addActionListener(addButtListener);
    lunchButt.addActionListener(lunchButtListener);
    breakButt.addActionListener(breakButtListener);
    uploadButt.addActionListener(uploadButtListener);

    f3.add(panel2);
//    f3.add(sp);
    f3.setSize(700,900);
//    f3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    sp.getViewport().add(jt);
//    f3.setLayout(null);
    jt.setModel(csvData);
    f3.setVisible(true);
  }
  public static void main(String filePath) {

    new tableCSV(filePath);
  }
}
