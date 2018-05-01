/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Juan
 */
public class CambiarColorGantt extends DefaultTableCellRenderer {

    private Component componente;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        componente = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.
        if (value == "Ejecutando") {
            componente.setBackground(Color.green);
            componente.setForeground(Color.green);
        } else if (value == "Espera") {
            componente.setBackground(Color.yellow);
            componente.setForeground(Color.yellow);

        } else {
            componente.setBackground(Color.white);
            componente.setForeground(Color.black);
        }
        return componente;
    }

}
