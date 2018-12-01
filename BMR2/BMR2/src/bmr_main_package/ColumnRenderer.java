/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bmr_main_package;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Rendering attributes for entire column
 *
 * @author Krzysztof
 */
public class ColumnRenderer extends DefaultTableCellRenderer
{

    private int selectedRow = -1;
    private int alignment = SwingConstants.CENTER;
    private Color foreground = null;

    public ColumnRenderer(int alignment)
    {
        super();
        this.alignment = alignment;
    }

    public ColumnRenderer(int alignment, Color foreground)
    {
        super();
        this.alignment = alignment;
        this.foreground = foreground;
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (this.foreground != null)
        {
            cell.setForeground(foreground);
        }
        //cell.setBackground(background);

        if (this.alignment != -1)
        {
            ((JLabel) cell).setHorizontalAlignment(this.alignment);
            //((JLabel) cell).setText("1234");
        }

        return this;
    }
}
