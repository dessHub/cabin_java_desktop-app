/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cabin;

/**
 *
 * @author desmond
 */
import java.awt.event.KeyEvent;
import java.sql.*;
import javax.swing.*;
import java.util.Date;
import net.proteanit.sql.DbUtils;
public class SalaryPortal extends javax.swing.JDialog {

     Connection conn=null;
   PreparedStatement pst=null;
   ResultSet rs=null;
    public SalaryPortal(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
         conn=ClassConnect.connectCabin();
         getPosition();
         displaySalary();
    }
    
    public void getPosition(){
        try{
        String sql="SELECT POSITION FROM department_db ";
        pst=conn.prepareStatement(sql);
        rs=pst.executeQuery();
        while(rs.next()){
            String val=rs.getString("POSITION");
            cbPos.addItem(val);
        }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void setSalary(){
        int index=cbPos.getSelectedIndex();
        String pos=cbPos.getSelectedItem().toString();
        String sal=txt_salary.getText();
        try{
        if(index==0||sal.isEmpty()){
            JOptionPane.showMessageDialog(null, "Select Position and enter salary");
        }else{
            String chck="SELECT * FROM salary_db WHERE POSITION='"+pos+"'";
            pst=conn.prepareStatement(chck);
            rs=pst.executeQuery();
            if(rs.next()){
                JOptionPane.showMessageDialog(null, "Position already exist");
            }else{
                String sql="INSERT INTO salary_db (POSITION,SALARY) VALUES (?,?)";
                pst=conn.prepareStatement(sql);
                pst.setString(1, pos);
                pst.setString(2, sal);
                pst.execute();
                JOptionPane.showMessageDialog(null, "Saved");
            }
        }
        displaySalary();
        refresh();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void updateSalary(){
         int index=cbPos.getSelectedIndex();
        String pos=cbPos.getSelectedItem().toString();
        String sal=txt_salary.getText();
        try{
           if(index==0||sal.isEmpty()){
               JOptionPane.showMessageDialog(null, "Select position to Update");
           }else{
               String sql="UPDATE salary_db SET POSITION='"+pos+"' , SALARY='"+sal+"' WHERE POSITION='"+pos+"'";
               pst=conn.prepareStatement(sql);
               pst.execute();
               JOptionPane.showMessageDialog(null, "Update");
           } 
           displaySalary();
           refresh();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void displaySalary(){
        try{
        String sql="SELECT * FROM salary_db ";
        pst=conn.prepareStatement(sql);
        rs=pst.executeQuery();
        jSalary.setModel(DbUtils.resultSetToTableModel(rs));
        
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void refresh(){
        cbPos.setSelectedIndex(0);
        txt_salary.setText(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbPos = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        txt_salary = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jSalary = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Position:");

        cbPos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-select-" }));

        jLabel2.setText("Salary:");

        jButton1.setText("Save");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jSalary.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jSalary.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jSalaryMouseClicked(evt);
            }
        });
        jSalary.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jSalaryKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jSalary);

        jButton2.setText("UPDATE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("DELETE");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbPos, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_salary, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbPos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txt_salary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(138, 138, 138))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        setSalary();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jSalaryKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSalaryKeyReleased
        int row=jSalary.getSelectedRow();
        String click=(jSalary.getModel().getValueAt(row, 0)).toString();
        if(evt.getKeyCode()==KeyEvent.VK_UP||evt.getKeyCode()==KeyEvent.VK_DOWN){
            try{
               String sql="SELECT * FROM salary_db WHERE POSITION='"+click+"'";
               pst=conn.prepareStatement(sql);
               rs=pst.executeQuery();
               if(rs.next()){
                   String val1=rs.getString("POSITION");
                   String val2=rs.getString("SALARY");
                   cbPos.setSelectedItem(val1);
                   txt_salary.setText(val2);
               }
                       
            }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        }
    }//GEN-LAST:event_jSalaryKeyReleased

    private void jSalaryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSalaryMouseClicked
      int row=jSalary.getSelectedRow();
      String click=(jSalary.getModel().getValueAt(row, 0)).toString();
      try{
      String sql="SELECT * FROM salary_db WHERE POSITION='"+click+"'";
      pst=conn.prepareStatement(sql);
      rs=pst.executeQuery();
      if(rs.next()){
                   String val1=rs.getString("POSITION");
                   String val2=rs.getString("SALARY");
                   cbPos.setSelectedItem(val1);
                   txt_salary.setText(val2);
      }
      }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jSalaryMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String pos=cbPos.getSelectedItem().toString();
        String sal=txt_salary.getText();
        if(sal.isEmpty()){
            JOptionPane.showMessageDialog(null, "Select field to Edit");
        }else{
            try{
            String sql="UPDATE salary_db SET POSITION='"+pos+"',SALARY='"+sal+"' WHERE POSITION='"+pos+"'";
            pst=conn.prepareStatement(sql);
            pst.execute();
             JOptionPane.showMessageDialog(null, "UPDATED");
            
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            displaySalary();
            refresh();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int p=JOptionPane.showConfirmDialog(null,"Data will be deleted.Continue?","delete",JOptionPane.YES_NO_OPTION);
        String sal=txt_salary.getText();
        String pos=cbPos.getSelectedItem().toString();
        if(sal.isEmpty()){
            JOptionPane.showMessageDialog(null, "Select field to Delete");
        }else{
            if(p==0){
                try{
                String sql="DELETE FROM salary_db WHERE POSITION='"+pos+"' AND SALARY='"+sal+"'";
                pst=conn.prepareStatement(sql);
                pst.execute();
                JOptionPane.showMessageDialog(null, "Selected field has been Deleted");
                
                }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
                displaySalary();
            refresh();
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SalaryPortal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SalaryPortal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SalaryPortal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SalaryPortal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SalaryPortal dialog = new SalaryPortal(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbPos;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTable jSalary;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txt_salary;
    // End of variables declaration//GEN-END:variables
}
