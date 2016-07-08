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
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import net.proteanit.sql.DbUtils;
public class CarService extends javax.swing.JDialog {

    PreparedStatement pst=null;
    ResultSet rs=null;
    Connection conn=null;
    public CarService(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        conn=ClassConnect.connectCabin();
        addMechanic();
        display();
    }
    
    public void getCar(){
        String fleet=txt_fleet.getText();
        try{
        String sql="SELECT REG_NO,MAKE FROM car_db WHERE FLEET_NO='"+fleet+"'";
        pst=conn.prepareStatement(sql);
        rs=pst.executeQuery();
        if(rs.next()){
            String val1=rs.getString("REG_NO");
            String val2=rs.getString("MAKE");
            txt_reg.setText(val1);
            txt_make.setText(val2);
        }
        }catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
    }
  
               
    }
   
    public void addMechanic(){
        
        try{
        String sql="SELECT EMPLOYEE_NO FROM employee_db WHERE DEPARTMENT='SERVICE'";
        pst=conn.prepareStatement(sql);
        rs=pst.executeQuery();
        while(rs.next()){
            String val=rs.getString("EMPLOYEE_NO");
            cbIdNo.addItem(val);
        }
        }catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
    }
    }
    
    public void getDetails(){
        String id=cbIdNo.getSelectedItem().toString();
        try{
        String sql="SELECT FNAME,LNAME FROM employee_db WHERE EMPLOYEE_NO='"+id+"'";
        pst=conn.prepareStatement(sql);
        rs=pst.executeQuery();
        if(rs.next()){
            String val1=rs.getString("FNAME");
            String val2=rs.getString("LNAME");
            txt_fname.setText(val1);
            txt_lname.setText(val2);
        }
        }catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
    }
    }
    
    public void update(){
      String fleet=txt_fleet.getText();
        String id=cbIdNo.getSelectedItem().toString();
        String parts=txt_parts.getText();
        String cost=txt_cost.getText();
        String dat=((JTextField)date.getDateEditor().getUiComponent()).getText();
        try{
        String sql="UPDATE service_db SET FLEET_NO='"+fleet+"',MECHANIC_ID='"+id+"',PARTS_SERVICED='"+parts+"',COST='"+cost+"',DATE='"+dat+"' WHERE DATE='"+dat+"' AND FLEET_NO='"+fleet+"' ";
        pst=conn.prepareStatement(sql);
        pst.execute();
        JOptionPane.showMessageDialog(null, "UPDATED");
        
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        display();
        refresh();
    }
    
    public void delete(){
        String fleet=txt_fleet.getText();
        String dat=((JTextField)date.getDateEditor().getUiComponent()).getText();
        try{
        String sql="DELETE FROM service_db WHERE DATE='"+dat+"' AND FLEET_NO='"+fleet+"'";
        pst=conn.prepareStatement(sql);
        pst.execute();
        
            JOptionPane.showMessageDialog(null, "DELETED");
            display();
        
        
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void refresh(){
        try{
        txt_fleet.setText(null);
        txt_reg.setText(null);
        txt_make.setText(null);
        cbIdNo.setSelectedIndex(0);
        txt_parts.setText(null);
        txt_cost.setText(null);
        ((JTextField)date.getDateEditor().getUiComponent()).setText(null);
        txt_fname.setText(null);
        txt_lname.setText(null);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void saveInfo(){
        String fleet=txt_fleet.getText();
        String id=cbIdNo.getSelectedItem().toString();
        String parts=txt_parts.getText();
        String cost=txt_cost.getText();
        String dat=((JTextField)date.getDateEditor().getUiComponent()).getText();
        try{
        String sql="INSERT INTO service_db (FLEET_NO,MECHANIC_ID,PARTS_SERVICED,COST,DATE) VALUES (?,?,?,?,?)";
        pst=conn.prepareStatement(sql);
        pst.setString(1, fleet);
        pst.setString(2, id);
        pst.setString(3, parts);
        pst.setString(4, cost);
        pst.setString(5, dat);
        pst.execute();
        JOptionPane.showMessageDialog(null, "SAVED");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
         display();
         refresh();
    }
    
    public void display(){
        try{
        String sql="SELECT * FROM service_db";
        pst=conn.prepareStatement(sql);
        rs=pst.executeQuery();
        {
            jDisplay.setModel(DbUtils.resultSetToTableModel(rs));
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_fleet = new javax.swing.JTextField();
        txt_reg = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_make = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txt_parts = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_cost = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        date = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        cbIdNo = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        txt_fname = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txt_lname = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        cmdSave = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jDisplay = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Car Details", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Adobe Arabic", 2, 14), new java.awt.Color(153, 204, 0))); // NOI18N

        jLabel1.setText("Registration No:");

        jLabel2.setText("Fleet No:");

        txt_fleet.setForeground(new java.awt.Color(51, 51, 51));
        txt_fleet.setText("search");
        txt_fleet.setToolTipText("Type Fleet no and press enter");
        txt_fleet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_fleetKeyReleased(evt);
            }
        });

        jLabel3.setText("Make:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_fleet, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_reg, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_make)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(txt_fleet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_reg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txt_make, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Services", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Adobe Arabic", 2, 14), new java.awt.Color(153, 204, 0))); // NOI18N

        jLabel4.setText("Parts Serviced:");

        jLabel5.setText("Cost:");

        jLabel6.setText("Date:");

        date.setDateFormatString(" yyyy-MM-dd,");

        jLabel7.setText("Mechanic Id No:");

        cbIdNo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-select-" }));
        cbIdNo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cbIdNoMouseEntered(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbIdNoMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cbIdNoMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cbIdNoMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                cbIdNoMouseReleased(evt);
            }
        });

        jLabel8.setText("First Name:");

        jLabel9.setText("Last Name:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txt_parts, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbIdNo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_cost, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(date, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_fname, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_lname, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 20, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(txt_parts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(txt_cost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6))
                    .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cbIdNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txt_fname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txt_lname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        cmdSave.setText("SAVE");
        cmdSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSaveActionPerformed(evt);
            }
        });

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

        jButton1.setText("REFRESH");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(cmdSave, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jButton1))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cmdSave, jButton1, jButton2, jButton3});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmdSave)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jDisplay.setModel(new javax.swing.table.DefaultTableModel(
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
        jDisplay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jDisplayMouseClicked(evt);
            }
        });
        jDisplay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jDisplayKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jDisplay);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 499, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 331, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(29, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(677, 637));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_fleetKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_fleetKeyReleased
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            getCar();
        }
    }//GEN-LAST:event_txt_fleetKeyReleased

    private void cmdSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSaveActionPerformed
         String fleet=txt_fleet.getText();
        int id=cbIdNo.getSelectedIndex();
        String parts=txt_parts.getText();
        String cost=txt_cost.getText();
        String dat=((JTextField)date.getDateEditor().getUiComponent()).getText();
        if(fleet.isEmpty()){
            JOptionPane.showMessageDialog(null, "Type Fleet no to proceed");
        }else{
            if(parts.isEmpty()){
              JOptionPane.showMessageDialog(null, "Type parts serviced to proceed");  
            }else{
                if(id==0){
                    JOptionPane.showMessageDialog(null, "Select mechanic id to proceed");
                }else{
                    saveInfo();
                }
            }
        }
    }//GEN-LAST:event_cmdSaveActionPerformed

    private void cbIdNoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbIdNoMouseClicked
        
    }//GEN-LAST:event_cbIdNoMouseClicked

    private void cbIdNoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbIdNoMousePressed
        //getDetails();
    }//GEN-LAST:event_cbIdNoMousePressed

    private void cbIdNoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbIdNoMouseReleased
        //getDetails();
    }//GEN-LAST:event_cbIdNoMouseReleased

    private void cbIdNoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbIdNoMouseEntered
        getDetails();
    }//GEN-LAST:event_cbIdNoMouseEntered

    private void cbIdNoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbIdNoMouseExited
        //getDetails();
    }//GEN-LAST:event_cbIdNoMouseExited

    private void jDisplayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDisplayMouseClicked
        int row=jDisplay.getSelectedRow();
       String tableClick=jDisplay.getValueAt(row, 0).toString();
       try{
       String sql="SELECT * FROM service_db WHERE COUNT='"+tableClick+"'";
       pst=conn.prepareStatement(sql);
       rs=pst.executeQuery();
       if(rs.next()){
           String val1=rs.getString("FLEET_NO");
           String val2=rs.getString("MECHANIC_ID");
           String val3=rs.getString("PARTS_SERVICED");
           String val4=rs.getString("COST");
           String val5=rs.getString("DATE");
           txt_fleet.setText(val1);
           txt_parts.setText(val3);
           cbIdNo.setSelectedItem(val2);
           txt_cost.setText(val4);
           ((JTextField)date.getDateEditor().getUiComponent()).setText(val5);
                   
       }
       
       
       }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
       }
    }//GEN-LAST:event_jDisplayMouseClicked

    private void jDisplayKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDisplayKeyReleased
        if(evt.getKeyCode()==KeyEvent.VK_UP || evt.getKeyCode()==KeyEvent.VK_DOWN){
              int row=jDisplay.getSelectedRow();
       String tableClick=jDisplay.getValueAt(row, 0).toString();
       try{
       String sql="SELECT * FROM service_db WHERE COUNT='"+tableClick+"'";
       pst=conn.prepareStatement(sql);
       rs=pst.executeQuery();
       if(rs.next()){
           String val1=rs.getString("FLEET_NO");
           String val2=rs.getString("MECHANIC_ID");
           String val3=rs.getString("PARTS_SERVICED");
           String val4=rs.getString("COST");
           String val5=rs.getString("DATE");
           txt_fleet.setText(val1);
           txt_parts.setText(val3);
           cbIdNo.setSelectedItem(val2);
           txt_cost.setText(val4);
           ((JTextField)date.getDateEditor().getUiComponent()).setText(val5);
                   
       }
       
       
       }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
       }
        }
    }//GEN-LAST:event_jDisplayKeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        update();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int p=JOptionPane.showConfirmDialog(null, "Data will be deleted", "Continue", JOptionPane.YES_NO_OPTION);
        if(p==0){
            delete();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        refresh();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(CarService.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CarService.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CarService.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CarService.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CarService dialog = new CarService(new javax.swing.JFrame(), true);
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
    private javax.swing.JComboBox<String> cbIdNo;
    private javax.swing.JButton cmdSave;
    private com.toedter.calendar.JDateChooser date;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JTable jDisplay;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txt_cost;
    private javax.swing.JTextField txt_fleet;
    private javax.swing.JTextField txt_fname;
    private javax.swing.JTextField txt_lname;
    private javax.swing.JTextField txt_make;
    private javax.swing.JTextField txt_parts;
    private javax.swing.JTextField txt_reg;
    // End of variables declaration//GEN-END:variables
}
