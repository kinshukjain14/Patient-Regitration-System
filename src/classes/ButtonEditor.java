package classes;

import eyehospital.PrescriptionRegenerate;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class ButtonEditor extends DefaultCellEditor{
    
  protected JButton button;
  private String  label;
  private boolean isPushed;
  public JTable  table1;
 public String sno=null;
  
  public ButtonEditor(JCheckBox checkBox,JTable table) {
    super(checkBox);
    table1=table;
    button = new JButton();
    button.setOpaque(true);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        fireEditingStopped();
      }
    });
  }
 
  
  public Component getTableCellEditorComponent(JTable table, Object value,
                   boolean isSelected, int row, int column) {
    if (isSelected) {
      button.setForeground(table.getSelectionForeground());
      button.setBackground(table.getSelectionBackground());
    } else{
      button.setForeground(table.getForeground());
      button.setBackground(table.getBackground());
    }
    label = (value ==null) ? "Generate" : value.toString();
    button.setText( label );
    isPushed = true;
    
    return button;
  }
  
    @Override
  public Object getCellEditorValue() {
    if (isPushed)  {
        int r=table1.getSelectedRow();
          int c;  
//          System.out.println(table1.getColumnName(1));
          
        if(table1.getColumnName(1).equalsIgnoreCase("Select"))
          c=0;
        else
          c=1;
        
          ArrayList<String> al = new ArrayList<>();
          
          al.add((String)table1.getValueAt(r,1));
          al.add((String)table1.getValueAt(r,2));
          al.add((String)table1.getValueAt(r,4));
          al.add((String)table1.getValueAt(r,5));
          al.add((String)table1.getValueAt(r,7));
          
//          sno=(String)table1.getValueAt(r, c);
//          System.out.println(sno);
//          new PrescriptionRegenerate(sno).setVisible(true);
            new PrescriptionRegenerate(al).setVisible(true);
     
      
    }
    isPushed = false;
    return new String(label) ;
  }
    
  public boolean stopCellEditing() {
    isPushed = false;
    return super.stopCellEditing();
  }
  
  protected void fireEditingStopped() {
    super.fireEditingStopped();
  }
}
