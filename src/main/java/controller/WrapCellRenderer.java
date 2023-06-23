package controller;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class WrapCellRenderer extends JTextArea implements TableCellRenderer {
  private int columnWidth;

  public WrapCellRenderer() {
    setLineWrap(true);
    setWrapStyleWord(true);
    setOpaque(true);
  }

  @Override
  public Dimension getPreferredSize() {
    Dimension dim = super.getPreferredSize();
    dim.width = columnWidth;
    return dim;
  }

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value,
                                                 boolean isSelected, boolean hasFocus, int row, int column) {
    try {
      if (column < 0 || column >= table.getColumnCount()) {
        throw new IndexOutOfBoundsException("Invalid column index: " + column);
      }
      setText((value == null) ? "" : value.toString());
      StringBuilder textBuilder = new StringBuilder(getText());
      textBuilder.append(System.lineSeparator());
      setText(textBuilder.toString());
      columnWidth = table.getColumnModel().getColumn(column).getWidth();
      if (isSelected) {
        setForeground(table.getSelectionForeground());
        setBackground(table.getSelectionBackground());
      } else {
        setForeground(table.getForeground());
        setBackground(table.getBackground());
      }
      return this;
    } catch (Exception e) {
      e.printStackTrace();
      return new JLabel("Error rendering cell");
    }
  }
}
