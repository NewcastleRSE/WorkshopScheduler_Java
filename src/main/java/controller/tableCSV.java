package controller;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import view.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;
import java.util.Vector;

public class tableCSV {

  private JFrame f3 = new JFrame();
  private JTable jt = new JTable();
  private JButton remButt, addButt, lunchButt, breakButt, updateButt, upButt, downButt;

//  GUI gui =new GUI();
  JTextArea mainText = GUI.t1;

  public tableCSV(String filePath){

//            parsing CSV
//        jt.setTableHeader(null);
        File csv_data = new File(filePath);
//        String column_names[]= {"duration","index","name","summary","web"};
        DefaultTableModel csvData = new DefaultTableModel(0,0);
    try {
      int start = 0;
      BufferedReader br = new BufferedReader(new FileReader(csv_data));
      String[] headers = br.readLine().split(",");
      Vector headerRow = new Vector(Arrays.asList(headers));

      InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(csv_data));
      CSVParser csvParser = CSVFormat.DEFAULT.parse(inputStreamReader);

      for (CSVRecord csvRecord : csvParser) {

        if (start == 0) {
          start = 1;

//          for (int i = 0; i < csvRecord.size(); i++) {
//            csvData.addColumn("Column " + (i + 1));
//          }

          csvData.addColumn("Duration");
//          csvData.addColumn("Index");
          csvData.addColumn("Name");
          csvData.addColumn("Summary");
          csvData.addColumn("URL");

          Vector row = new Vector();
          for (int i = 0; i < csvRecord.size(); i++) {
            row.add(csvRecord.get(i));
          }
          csvData.addRow(row);

        } else {
            Vector row = new Vector();
            for (int i = 0; i < csvRecord.size(); i++) {
              row.add(csvRecord.get(i));
            }
            csvData.addRow(row);
          }
//          Vector row = new Vector();
//          row.add(csvRecord.get(0));
//          row.add(csvRecord.get(1));
//          row.add(csvRecord.get(2));
//          row.add(csvRecord.get(3));
//          row.add(csvRecord.get(4));
        }
//      JOptionPane.showMessageDialog(null, headerRow);
    } catch (Exception ex) {
      System.out.println(ex);
      System.out.println("Error in Parsing CSV File");
    }

    JPanel panel2 = new JPanel();
    GridBagConstraints gbc2 = new GridBagConstraints();
    GridBagLayout layout = new GridBagLayout();
    panel2.setLayout(layout);

    jt.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    JScrollPane sp=new JScrollPane(jt);
    sp.setVerticalScrollBarPolicy(
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    sp.setHorizontalScrollBarPolicy(
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    sp.setPreferredSize(new Dimension(700, 400));
    jt.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    jt.getTableHeader().setOpaque(false);
    jt.getTableHeader().setFont(new Font("Segue UI", Font.BOLD, 12));

    gbc2.gridx=0;
    gbc2.gridy=0;
    gbc2.gridwidth = 3;
    panel2.add(sp,gbc2);

    updateButt = new JButton("Update view");
    gbc2.insets = new Insets(0,0,0,0);
    gbc2.fill = GridBagConstraints.BOTH;
    gbc2.ipadx=0;
    gbc2.ipady=0;
    gbc2.gridx=0;
    gbc2.gridy=3;//row
    gbc2.gridheight=1;
    gbc2.fill=GridBagConstraints.BOTH;
    gbc2.weighty=0.1;
    gbc2.weightx=0.1;
    panel2.add(updateButt,gbc2);

    upButt = new JButton("Row UP");
    gbc2.gridwidth = 1;
    gbc2.gridx=0;
    gbc2.gridy=1;//row
    panel2.add(upButt,gbc2);

    downButt = new JButton("Row DOWN");
    gbc2.gridx=0;
    gbc2.gridy=2;//row
    panel2.add(downButt,gbc2);

    remButt = new JButton("Remove row");
//    gbc2.ipadx=1;
//    gbc2.ipady=1;
    gbc2.gridx=1;
    gbc2.gridy=1;//row
    panel2.add(remButt,gbc2);
    addButt = new JButton("Add row");
    gbc2.gridx=2;
    gbc2.gridy=1;//row
    panel2.add(addButt,gbc2);

    lunchButt = new JButton("Add lunch");
    gbc2.gridx=1;
    gbc2.gridy=2;//row
    panel2.add(lunchButt,gbc2);
    breakButt = new JButton("Add break");
    gbc2.gridx=2;
    gbc2.gridy=2;//row
    panel2.add(breakButt,gbc2);

    ActionListener remButtListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        // check for selected row first
        if(jt.getSelectedRow() != -1) {
          // remove selected row from the model
          csvData.removeRow(jt.getSelectedRow());
        } else {
          JOptionPane.showMessageDialog(null, "Row not selected");
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

    ActionListener updateButtListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        mainText.selectAll();
        mainText.replaceSelection("");
        for(int i = 0; i < jt.getRowCount(); i++){
          String duration = jt.getValueAt(i, 0).toString();
//          String index = jt.getValueAt(i, 1).toString();
          String name = jt.getValueAt(i, 1).toString();
          String summary = jt.getValueAt(i, 2).toString();
          String web = jt.getValueAt(i, 3).toString();
          mainText.setText(mainText.getText()+duration+","+name+","+summary+","+web+"\n");
        }
      }
    };

    ActionListener upButtListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        int rowIndex = jt.getSelectedRow();
        if (jt.getSelectedRow() == 0) {
//          JOptionPane.showMessageDialog(null, "You are already at the top");
        } else if (jt.getSelectedRow() != -1) {
          csvData.moveRow(rowIndex, rowIndex, rowIndex - 1);
          jt.setRowSelectionInterval( rowIndex -1, rowIndex -1);
        } else {
          JOptionPane.showMessageDialog(null, "Row not selected");
        }
      }
    };

    ActionListener downButtListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        int rowIndex = jt.getSelectedRow();
        if (rowIndex < csvData.getRowCount() - 1 ){
        if(jt.getSelectedRow() != -1) {
          csvData.moveRow(rowIndex, rowIndex, rowIndex + 1);
          jt.setRowSelectionInterval( rowIndex +1, rowIndex +1);
        }else {
          JOptionPane.showMessageDialog(null, "Row not selected");
        }}
      }
    };

    remButt.addActionListener(remButtListener);
    addButt.addActionListener(addButtListener);
    lunchButt.addActionListener(lunchButtListener);
    breakButt.addActionListener(breakButtListener);
    updateButt.addActionListener(updateButtListener);
    upButt.addActionListener(upButtListener);
    downButt.addActionListener(downButtListener);

    panel2.setPreferredSize(new Dimension(710, 550));
//    panel2.setBorder(new EmptyBorder(50, 50, 50, 50));
    f3.setMinimumSize(new Dimension(710, 550));
    f3.setMaximumSize(new Dimension(710, 550));
    f3.add(panel2);
//    f3.add(sp);
    f3.setSize(700,900);
//    f3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    sp.getViewport().add(jt);
//    f3.setLayout(null);
    jt.setModel(csvData);
    f3.setVisible(true);
    f3.pack();
  }
  public static void main(String filePath) {

    new tableCSV(filePath);
  }
}
