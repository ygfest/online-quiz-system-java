/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aimSystem;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class TableActionCellEditor2 extends DefaultCellEditor {
    private TableActionEvent2 event;
    public TableActionCellEditor2(TableActionEvent2 event){
    super (new JCheckBox());
    this.event = event;
    }
    
@Override
public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int row, int column) {
StatusPanel2 action2=new StatusPanel2();
action2.initEvent(event, row);
action2.setBackground(jtable.getSelectionBackground());
return action2.manages;

}
}