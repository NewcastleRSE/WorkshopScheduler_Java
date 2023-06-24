package controller;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import view.GUI;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;
import java.util.Vector;

public class tableCSV {

  private JFrame fr_MainFrame = new JFrame();
  private JTable tbl_Schedule = new JTable();
  private JButton btn_remove, btn_add, btn_lunch, btn_break, btn_update, btn_up, btn_down;

//  GUI gui =new GUI();
  JTextArea mainText = GUI.ta_ViewCSV;

  public tableCSV(String filePath){

        File csv_data = new File(filePath);
//        String column_names[]= {"duration","index","name","summary","web"};
        DefaultTableModel csvData = new DefaultTableModel(0,0);
    try {
      int start = 0;
      BufferedReader bufferedReader = new BufferedReader(new FileReader(csv_data));
      String[] headers = bufferedReader.readLine().split(",");
      Vector headerRow = new Vector(Arrays.asList(headers));

      InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(csv_data));
      CSVParser csvParser = CSVFormat.DEFAULT.parse(inputStreamReader);

      for (CSVRecord csvRecord : csvParser) {

        if (start == 0) {
          start = 1;

//          for (int i = 0; i < csvRecord.size(); i++) {
//            csvData.addColumn("Column " + (i + 1));
//          }

          csvData.addColumn("Duration(min.)");
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
        }
    } catch (Exception ex) {
      System.out.println(ex);
      System.out.println("Error in Parsing CSV File");
    }

    JPanel panel2 = new JPanel();
    GridBagConstraints gridBagConstraints = new GridBagConstraints();
    GridBagLayout layout = new GridBagLayout();
    panel2.setLayout(layout);

    tbl_Schedule.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    tbl_Schedule.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    tbl_Schedule.getTableHeader().setOpaque(false);
    tbl_Schedule.getTableHeader().setFont(new Font("Segue UI", Font.BOLD, 12));
    for (int i = 0; i < tbl_Schedule.getColumnCount(); i++) {
      TableColumn column = tbl_Schedule.getColumnModel().getColumn(i);
      column.setCellRenderer(new WrapCellRenderer());
    }
    JScrollPane sp=new JScrollPane(tbl_Schedule);
    sp.setVerticalScrollBarPolicy(
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    sp.setHorizontalScrollBarPolicy(
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    sp.setPreferredSize(new Dimension(700, 400));

    gridBagConstraints.gridx=0;
    gridBagConstraints.gridy=0;
    gridBagConstraints.gridwidth = 3;
    panel2.add(sp,gridBagConstraints);

    btn_update = new JButton("Update view");
    gridBagConstraints.insets = new Insets(0,0,0,0);
    gridBagConstraints.fill = GridBagConstraints.BOTH;
    gridBagConstraints.ipadx=0;
    gridBagConstraints.ipady=0;
    gridBagConstraints.gridx=0;
    gridBagConstraints.gridy=3;//row
    gridBagConstraints.gridheight=1;
    gridBagConstraints.fill=GridBagConstraints.BOTH;
    gridBagConstraints.weighty=0.1;
    gridBagConstraints.weightx=0.1;
    panel2.add(btn_update,gridBagConstraints);

    btn_up = new JButton("Row UP");
    gridBagConstraints.gridwidth = 1;
    gridBagConstraints.gridx=0;
    gridBagConstraints.gridy=1;//row
    panel2.add(btn_up,gridBagConstraints);

    btn_down = new JButton("Row DOWN");
    gridBagConstraints.gridx=0;
    gridBagConstraints.gridy=2;//row
    panel2.add(btn_down,gridBagConstraints);

    btn_remove = new JButton("Remove row");
//    gridBagConstraints.ipadx=1;
//    gridBagConstraints.ipady=1;
    gridBagConstraints.gridx=1;
    gridBagConstraints.gridy=1;//row
    panel2.add(btn_remove,gridBagConstraints);
    btn_add = new JButton("Add row");
    gridBagConstraints.gridx=2;
    gridBagConstraints.gridy=1;//row
    panel2.add(btn_add,gridBagConstraints);

    btn_lunch = new JButton("Add lunch");
    gridBagConstraints.gridx=1;
    gridBagConstraints.gridy=2;//row
    panel2.add(btn_lunch,gridBagConstraints);
    btn_break = new JButton("Add break");
    gridBagConstraints.gridx=2;
    gridBagConstraints.gridy=2;//row
    panel2.add(btn_break,gridBagConstraints);


    ActionListener remButtListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        // check for selected row first
        if(tbl_Schedule.getSelectedRow() != -1) {
          // remove selected row from the model
          csvData.removeRow(tbl_Schedule.getSelectedRow());
        } else {
          JOptionPane.showMessageDialog(null, "Please select row to remove.");
          JOptionPane.showMessageDialog(null, tbl_Schedule.getColumnModel().getColumn(1));
          JOptionPane.showMessageDialog(null, tbl_Schedule.getColumnModel().getColumnCount());

        }
      }
    };

    ActionListener addButtListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        int rowIndex = tbl_Schedule.getSelectedRow();
        if(rowIndex != -1) {
//        JOptionPane.showMessageDialog(null, rowIndex);
          // add row from the model
          csvData.insertRow(rowIndex + 1, new Object[]{"-", "-", "-", "-", "-"});
        } else {
          csvData.addRow(new Object[]{"-", "-", "-", "-", "-"});
        }
      }
    };

    ActionListener lunchButtListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        int rowIndex = tbl_Schedule.getSelectedRow();
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
        int rowIndex = tbl_Schedule.getSelectedRow();
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
        for(int i = 0; i < tbl_Schedule.getRowCount(); i++){
          String duration = tbl_Schedule.getValueAt(i, 0).toString();
          String name = tbl_Schedule.getValueAt(i, 1).toString();
          String summary = tbl_Schedule.getValueAt(i, 2).toString();
          String web = tbl_Schedule.getValueAt(i, 3).toString();
          mainText.setText(mainText.getText()+duration+","+name+","+summary+","+web+"\n");
        }
      }
    };

    ActionListener upButtListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        int rowIndex = tbl_Schedule.getSelectedRow();
        if (tbl_Schedule.getSelectedRow() == 0) {
//          JOptionPane.showMessageDialog(null, "You are already at the top");
        } else if (tbl_Schedule.getSelectedRow() != -1) {
          csvData.moveRow(rowIndex, rowIndex, rowIndex - 1);
          tbl_Schedule.setRowSelectionInterval( rowIndex -1, rowIndex -1);
        } else {
          JOptionPane.showMessageDialog(null, "Please select row to move.");
        }
      }
    };

    ActionListener downButtListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        int rowIndex = tbl_Schedule.getSelectedRow();
        if (rowIndex < csvData.getRowCount() - 1 ){
        if(tbl_Schedule.getSelectedRow() != -1) {
          csvData.moveRow(rowIndex, rowIndex, rowIndex + 1);
          tbl_Schedule.setRowSelectionInterval( rowIndex +1, rowIndex +1);
        }else {
          JOptionPane.showMessageDialog(null, "Please select row to move.");
        }}
      }
    };

    btn_remove.addActionListener(remButtListener);
    btn_add.addActionListener(addButtListener);
    btn_lunch.addActionListener(lunchButtListener);
    btn_break.addActionListener(breakButtListener);
    btn_update.addActionListener(updateButtListener);
    btn_up.addActionListener(upButtListener);
    btn_down.addActionListener(downButtListener);

    panel2.setPreferredSize(new Dimension(710, 550));
//    panel2.setBorder(new EmptyBorder(50, 50, 50, 50));
    fr_MainFrame.setMinimumSize(new Dimension(710, 550));
    fr_MainFrame.setMaximumSize(new Dimension(710, 550));
    fr_MainFrame.add(panel2);
//    f3.add(sp);
    fr_MainFrame.setSize(700,900);
//    f3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    sp.getViewport().add(tbl_Schedule);
//    f3.setLayout(null);
    tbl_Schedule.setModel(csvData);
    fr_MainFrame.setVisible(true);
    fr_MainFrame.pack();
  }



  public static void main(String filePath) {

    new tableCSV(filePath);
  }
}
