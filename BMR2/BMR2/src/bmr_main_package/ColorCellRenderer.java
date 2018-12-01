/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bmr_main_package;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Cell color is passed as cell value
 * @author Krzysztof
 */
public class ColorCellRenderer extends DefaultTableCellRenderer
{

    private int selectedRow = -1;

    public ColorCellRenderer()
    {
        super();
        //this.selectedRow = row;
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {       
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        Color color = (Color) value;
        cell.setForeground(color);
        cell.setBackground(color);
        
        ((JLabel) cell).setHorizontalAlignment(SwingConstants.CENTER);
        ((JLabel) cell).setText("1234");
              
        return this;       
    }

}
