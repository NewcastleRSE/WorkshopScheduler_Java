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
  private JButton remButt = new JButton();
//  private JPanel panel = new JPanel();
//  private JLabel label = new JLabel("Hello");
  public tableCSV(String filePath){

//    label.setBounds(0,0,100,500);
//    label.setFont(new Font(null,Font.PLAIN,25));
//    f3.add(label);
//    String data[][]={ {"101","Amit","670000"},
//        {"102","Jai","780000"},
//        {"101","Sachin","700000"}};
//    String column[]={"ID","NAME","SALARY"};
//    JTable jt=new JTable(data,column);
//    jt.setBounds(30,40,200,300);
//    JScrollPane sp=new JScrollPane(jt);

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

    remButt = new JButton("Remove row");
    jt.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    remButt.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        // check for selected row first
        if(jt.getSelectedRow() != -1) {
          // remove selected row from the model
          csvData.removeRow(jt.getSelectedRow());
//          JOptionPane.showMessageDialog(null, "Selected row deleted successfully");
        }
      }
    });


    JScrollPane sp=new JScrollPane(jt);
    f3.add(sp, BorderLayout.CENTER);
    f3.add(remButt, BorderLayout.SOUTH);
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
