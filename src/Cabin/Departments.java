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
public class Departments extends javax.swing.JDialog {

    Connection conn=null;
   PreparedStatement pst=null;
   ResultSet rs=null;
    public Departments(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        conn=ClassConnect.connectCabin();
        displayDepartment();
        displayDept();
        addDept();
    }
    
    public void editDept(){
        
        String dept=txt_dept.getText();
        String code=txt_code.getText();
        if(dept.isEmpty()||code.isEmpty()){
            JOptionPane.showMessageDialog(null, "Select department to edit");
        }else{
            try{
            String sql="UPDATE dept_db SET DEPARTMENT='"+dept+"' ,CODE='"+code+"' WHERE DEPARTMENT='"+dept+"'";
            pst=conn.prepareStatement(sql);
            pst.execute();
             JOptionPane.showMessageDialog(null, "Updated");
            }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
            displayDept();
        }
    }
    
     public void editDepartment(){
        int dep=cbDept.getSelectedIndex();
        String dept=cbDept.getSelectedItem().toString();
        String pos=txt_pos.getText();
        if(dep==0||pos.isEmpty()){
            JOptionPane.showMessageDialog(null, "Select department to edit");
        }else{
            try{
            String sql="UPDATE department_db SET DEPARTMENT='"+dept+"' ,POSITION='"+pos+"' WHERE DEPARTMENT='"+dept+"' AND POSITION='"+pos+"'";
            pst=conn.prepareStatement(sql);
            pst.execute();
             JOptionPane.showMessageDialog(null, "Updated");
            }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
            displayDepartment();
        }
    }
     
     public void deleteDept(){
         int p=JOptionPane.showConfirmDialog(null, "Department will be deleted.","Continue",JOptionPane.YES_NO_OPTION);
         if(p==0){
             try{
             String sql="DELETE FROM dept_db WHERE DEPARTMENT=?";
             pst=conn.prepareStatement(sql);
             pst.setString(1, txt_dept.getText());
             pst.execute();
             JOptionPane.showMessageDialog(null, "Department Deleted");
             displayDept();
             }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
         }
                 
     }
     
     public void deleteDepartment(){
         int p=JOptionPane.showConfirmDialog(null, "Department will be deleted.","Continue",JOptionPane.YES_NO_OPTION);
         if(p==0){
             try{
             String sql="DELETE FROM department_db WHERE POSITION=?";
             pst=conn.prepareStatement(sql);
             
              pst.setString(1, txt_pos.getText());
             pst.execute();
             JOptionPane.showMessageDialog(null, "Department Deleted");
             displayDepartment();
             }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
         }
                 
     }
    
    public void setDepartment(){
        String dept=txt_dept.getText();
        String code=txt_code.getText();
        try{
        String check="SELECT * FROM dept_db WHERE DEPARTMENT='"+dept+"' AND CODE='"+code+"'";
        pst=conn.prepareStatement(check);
        rs=pst.executeQuery();
        if(rs.next()){
            JOptionPane.showMessageDialog(null, "Department name or Code already exist");
        }else{
            String sql="INSERT INTO dept_db (DEPARTMENT,CODE) VALUES (?,?)";
            pst=conn.prepareStatement(sql);
            pst.setString(1, dept);
            pst.setString(2, code);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Department set successfully");
        }
        displayDept();
        addDept();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void setDepartmentPosition(){
        String dept=cbDept.getSelectedItem().toString();
        String pos=txt_pos.getText();
        try{
        String check="SELECT * FROM department_db WHERE DEPARTMENT='"+dept+"' AND POSITION='"+pos+"'";
        pst=conn.prepareStatement(check);
        rs=pst.executeQuery();
        if(rs.next()){
            JOptionPane.showMessageDialog(null, "Position already exist");
        }else{
            String sql="INSERT INTO department_db (DEPARTMENT,POSITION) VALUES (?,?)";
            pst=conn.prepareStatement(sql);
            pst.setString(1, dept);
            pst.setString(2, pos);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Position set successfully");
        }
        displayDepartment();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void displayDept(){
        try{
        String sql="SELECT * FROM dept_db ";
        pst=conn.prepareStatement(sql);
        rs=pst.executeQuery();
        jDept.setModel(DbUtils.resultSetToTableModel(rs));
        
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void displayDepartment(){
        try{
        String sql="SELECT * FROM department_db ";
        pst=conn.prepareStatement(sql);
        rs=pst.executeQuery();
        jDepartment.setModel(DbUtils.resultSetToTableModel(rs));
        
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void addDept(){
        try{
        String sql="SELECT * FROM dept_db ";
        pst=conn.prepareStatement(sql);
        rs=pst.executeQuery();
        while(rs.next()){
            String val=rs.getString("DEPARTMENT");
            cbDept.addItem(val);
        }
        
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_dept = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jDept = new javax.swing.JTable();
        txt_code = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_pos = new javax.swing.JTextField();
        cbDept = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jDepartment = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setBackground(new java.awt.Color(255, 255, 204));
        jLabel1.setFont(new java.awt.Font("Vijaya", 2, 18)); // NOI18N
        jLabel1.setText("DEPARTMENTS MANAGEMENT");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setText("Code:");

        jLabel2.setText("Department :");

        jDept.setModel(new javax.swing.table.DefaultTableModel(
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
        jDept.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jDeptMouseClicked(evt);
            }
        });
        jDept.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jDeptKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jDept);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Cabin/save.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("UPDATE");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("DELETE");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_dept, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_code, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txt_dept, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(txt_code, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap())
        );

        jTabbedPane1.addTab("SET DEPT", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setText("Department:");

        jLabel5.setText("Position:");

        cbDept.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-select-" }));

        jDepartment.setModel(new javax.swing.table.DefaultTableModel(
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
        jDepartment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jDepartmentMouseClicked(evt);
            }
        });
        jDepartment.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jDepartmentKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jDepartment);

        jButton1.setBackground(new java.awt.Color(153, 153, 0));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Cabin/save.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton5.setText("UPDATE");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("DELETE");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(2, 2, 2)
                                .addComponent(cbDept, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_pos, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton6)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(jLabel5)
                        .addComponent(txt_pos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbDept, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
                .addGap(6, 6, 6))
        );

        jTabbedPane1.addTab("MANAGE DEPT", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        setDepartment();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        setDepartmentPosition();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        editDepartment();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        editDept();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        int dep=cbDept.getSelectedIndex();
        String pos=txt_pos.getText();
        if(dep==0 ||pos.isEmpty() ){
         JOptionPane.showMessageDialog(null, "SELECT POSITION TO DELETE");   
        }else{
        deleteDepartment();
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        String dep=txt_dept.getText();
        if(dep.isEmpty()){
            JOptionPane.showMessageDialog(null, "SELECT DEPARTMENT TO DELETE");
        }else{
             deleteDept();
        }
        
       
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jDeptKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDeptKeyReleased
       int row=jDept.getSelectedRow();
       String click=(jDept.getModel().getValueAt(row, 0)).toString();
        
        if(evt.getKeyCode()==KeyEvent.VK_UP||evt.getKeyCode()==KeyEvent.VK_DOWN){
           try{
           String sql="SELECT * FROM dept_db WHERE DEPARTMENT='"+click+"'";
           pst=conn.prepareStatement(sql);
           rs=pst.executeQuery();
           if(rs.next()){
               String val1=rs.getString("DEPARTMENT");
               String val2=rs.getString("CODE");
               txt_dept.setText(val1);
               txt_code.setText(val2);
           }
           }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
       }
    }//GEN-LAST:event_jDeptKeyReleased

    private void jDepartmentKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDepartmentKeyReleased
        int row=jDepartment.getSelectedRow();
       String click=(jDepartment.getModel().getValueAt(row, 1)).toString();
        
        if(evt.getKeyCode()==KeyEvent.VK_UP||evt.getKeyCode()==KeyEvent.VK_DOWN){
           try{
           String sql="SELECT * FROM department_db WHERE POSITION='"+click+"'";
           pst=conn.prepareStatement(sql);
           rs=pst.executeQuery();
           if(rs.next()){
               String val1=rs.getString("DEPARTMENT");
               String val2=rs.getString("POSITION");
               cbDept.setSelectedItem(val1);
               txt_pos.setText(val2);
           }
           }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
       }
    }//GEN-LAST:event_jDepartmentKeyReleased

    private void jDepartmentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDepartmentMouseClicked
         int row=jDepartment.getSelectedRow();
       String click=(jDepartment.getModel().getValueAt(row, 1)).toString();
        
        
           try{
           String sql="SELECT * FROM department_db WHERE POSITION='"+click+"'";
           pst=conn.prepareStatement(sql);
           rs=pst.executeQuery();
           if(rs.next()){
               String val1=rs.getString("DEPARTMENT");
               String val2=rs.getString("POSITION");
               cbDept.setSelectedItem(val1);
               txt_pos.setText(val2);
           }
           }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
       
    }//GEN-LAST:event_jDepartmentMouseClicked

    private void jDeptMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDeptMouseClicked
        int row=jDept.getSelectedRow();
       String click=(jDept.getModel().getValueAt(row, 0)).toString();
        
        
           try{
           String sql="SELECT * FROM dept_db WHERE DEPARTMENT='"+click+"'";
           pst=conn.prepareStatement(sql);
           rs=pst.executeQuery();
           if(rs.next()){
               String val1=rs.getString("DEPARTMENT");
               String val2=rs.getString("CODE");
               txt_dept.setText(val1);
               txt_code.setText(val2);
           }
           }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
      
    }//GEN-LAST:event_jDeptMouseClicked

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
            java.util.logging.Logger.getLogger(Departments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Departments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Departments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Departments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Departments dialog = new Departments(new javax.swing.JFrame(), true);
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
    private javax.swing.JComboBox<String> cbDept;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JTable jDepartment;
    private javax.swing.JTable jDept;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField txt_code;
    private javax.swing.JTextField txt_dept;
    private javax.swing.JTextField txt_pos;
    // End of variables declaration//GEN-END:variables
}
