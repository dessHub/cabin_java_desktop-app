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
public class Employee extends javax.swing.JDialog {

     Connection conn=null;
   PreparedStatement pst=null;
   ResultSet rs=null;
    public Employee(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
         conn=ClassConnect.connectCabin();
         currentDate();
         displayEmployee();
         addDept();
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
    
     public void addPos(){
         String dept=cbDept.getSelectedItem().toString();
        int index=cbDept.getSelectedIndex();
       // String pos=cbPos.getSelectedItem().toString();
        cbPos.removeAllItems();
        if(index==0){
             JOptionPane.showMessageDialog(null, "SELECT DEPARTMENT");
        }else{
            
        try{
        String sql="SELECT DISTINCT POSITION FROM department_db WHERE DEPARTMENT='"+dept+"'";
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
    }
     
    public void currentDate(){
        Date today=new Date();
        jDate.setDate(today);
    }
    
     public void register(){
       String id=txt_ID.getText().toUpperCase();
        String fName=txt_fName.getText().toUpperCase();
        String lName=txt_lName.getText().toUpperCase();
        String gender=cb_gender.getSelectedItem().toString();
        String phone=txt_phone.getText().toUpperCase();
        String phone2=txt_phone2.getText().toUpperCase();
        String email=txt_email.getText().toLowerCase();
        String empNo=txt_empNo.getText().toUpperCase();
        String dept=cbDept.getSelectedItem().toString();
        String position=cbPos.getSelectedItem().toString();
        String date=((JTextField)jDate.getDateEditor().getUiComponent()).getText();
        if(id.isEmpty()||fName.isEmpty()||phone.isEmpty()||empNo.isEmpty()){
            JOptionPane.showMessageDialog(null, "Input data into all fields");
        }else{
        try{
       String checkId="SELECT * FROM employee_db WHERE ID_NO='"+id+"' OR EMPLOYEE_NO='"+empNo+"'";
       pst=conn.prepareStatement(checkId);
       rs=pst.executeQuery();
       if(rs.next()){
           JOptionPane.showMessageDialog(null, "ID NO already exist");
       }else{
           String sql="INSERT INTO employee_db (ID_NO,FNAME,LNAME,GENDER,EMPLOYEE_NO,PHONE,PHONE2,EMAIL,DEPARTMENT,POSITION,DATE) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
           pst=conn.prepareStatement(sql);
           pst.setString(1, id);
           pst.setString(2, fName);
           pst.setString(3, lName);
           pst.setString(4, gender);
           pst.setString(5, empNo);
           pst.setString(6, phone);
           pst.setString(7, phone2);
           pst.setString(8, email);
           pst.setString(9, dept);
           pst.setString(10, position);
           pst.setString(11, date);
           pst.execute();
            JOptionPane.showMessageDialog(null, "Client Successfully registered");
             displayEmployee();
        }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        EmployeePortal.getDeptNo();
        EmployeePortal.getEmployeeNo();
        }
    }
     
      public void update(){
         String id=txt_ID.getText().toUpperCase();
        String fName=txt_fName.getText().toUpperCase();
        String lName=txt_lName.getText().toUpperCase();
        String gender=cb_gender.getSelectedItem().toString();
        String dept=cbDept.getSelectedItem().toString();
         String phone=txt_phone.getText().toUpperCase();
        String phone2=txt_phone2.getText().toUpperCase();
         String email=txt_email.getText().toLowerCase();
         String empNo=txt_empNo.getText().toUpperCase();
         String position=cbPos.getSelectedItem().toString();
        String date=((JTextField)jDate.getDateEditor().getUiComponent()).getText();
        if(id.isEmpty()||fName.isEmpty()||phone.isEmpty()||dept.isEmpty()){
            JOptionPane.showMessageDialog(null, "Input data into all fields");
        }else{
        try{
       
           String sql="UPDATE employee_db SET ID_NO='"+id+"',FNAME='"+fName+"',LNAME='"+lName+"',GENDER='"+gender+"',EMPLOYEE_NO='"+empNo+"',PHONE='"+phone+"',PHONE2='"+phone2+"',EMAIL='"+email+"',DEPARTMENT='"+dept+"',POSITION='"+position+"',DATE='"+date+"' WHERE ID_NO='"+id+"' AND EMPLOYEE_NO='"+empNo+"' ";
           pst=conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Successfully updated");
             displayEmployee();
        
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        }
    }
      
      public void getEmployeeDetails(){
        //String id=txt_ID.getText();
        String emp=txt_empNo.getText();
         try{
        String check="SELECT * FROM employee_db WHERE EMPLOYEE_NO='"+emp+"'";
        pst=conn.prepareStatement(check);
        rs=pst.executeQuery();
        if(rs.next()){
            String val1=rs.getString("FNAME");
            String val2=rs.getString("LNAME");
            String val3=rs.getString("GENDER");
            String val4=rs.getString("DEPARTMENT");
            String val5=rs.getString("PHONE");
            String val6=rs.getString("PHONE2");
            String val7=rs.getString("ID_NO");
            String val8=rs.getString("EMAIL");
            String val11=rs.getString("POSITION");
            String val12=rs.getString("DATE");
        txt_fName.setText(val1);
        txt_lName.setText(val2);
        cb_gender.setSelectedItem(val3);
        cbDept.setSelectedItem(val4);
         txt_phone.setText(val5);
        txt_phone2.setText(val6);
        txt_ID.setText(val7);
        txt_email.setText(val8);
        cbPos.setSelectedItem(val11);
        ((JTextField)jDate.getDateEditor().getUiComponent()).setText(val12);
            
          }else{
            JOptionPane.showMessageDialog(null, "No registrations found"); 
        }
           
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
      
      public void refresh(){
          txt_fName.setText(null);
        txt_lName.setText(null);
        cb_gender.setSelectedIndex(0);
        cbDept.setSelectedItem(null);
         txt_phone.setText(null);
        txt_phone2.setText(null);
        txt_empNo.setText(null);
        txt_email.setText(null);
        cbPos.setSelectedItem(null);
        txt_ID.setText(null);
        currentDate();
     }
     
     public void displayEmployee(){
         try{
         String sql="SELECT ID_NO,EMPLOYEE_NO,FNAME,LNAME,DEPARTMENT,POSITION FROM employee_db ";
         pst=conn.prepareStatement(sql);
         rs=pst.executeQuery();
         jEmployee.setModel(DbUtils.resultSetToTableModel(rs));
         
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

        jPanel5 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jDate = new com.toedter.calendar.JDateChooser();
        cbDept = new javax.swing.JComboBox<>();
        cbPos = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_phone = new javax.swing.JTextField();
        txt_phone2 = new javax.swing.JTextField();
        txt_email = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_ID = new javax.swing.JTextField();
        txt_fName = new javax.swing.JTextField();
        txt_lName = new javax.swing.JTextField();
        cb_gender = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        txt_empNo = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jEmployee = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        cmdRegister = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Others", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Arial", 0, 10), new java.awt.Color(204, 204, 0))); // NOI18N

        jLabel11.setText("Department");

        jLabel13.setText("Position:");

        jLabel14.setText("Date:");

        jDate.setDateFormatString(" yyyy-MM-dd");

        cbDept.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-select-" }));
        cbDept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbDeptActionPerformed(evt);
            }
        });

        cbPos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-select-" }));
        cbPos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel14)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbDept, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbPos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDate, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(cbDept, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(cbPos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Contact Info", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Arial", 0, 10), new java.awt.Color(204, 204, 0))); // NOI18N

        jLabel7.setText("Phone NO:*");

        jLabel8.setText("Phone No:");

        jLabel10.setText("Email:*");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_phone2, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                    .addComponent(txt_email)
                    .addComponent(txt_phone))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txt_phone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txt_phone2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Personal Informations", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Arial", 0, 10), new java.awt.Color(204, 204, 0))); // NOI18N

        jLabel2.setText("ID NO");

        jLabel3.setText("First Name:");

        jLabel4.setText("Last Name:");

        jLabel5.setText("Gender:");

        cb_gender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-select-", "MALE", "FEMALE" }));

        jLabel12.setText("Employee no:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txt_empNo, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_ID)
                        .addComponent(txt_fName)
                        .addComponent(txt_lName))
                    .addComponent(cb_gender, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cb_gender, txt_ID, txt_empNo, txt_fName, txt_lName});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txt_empNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_fName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_lName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cb_gender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 8, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Employee Table", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Arial", 0, 10), new java.awt.Color(204, 204, 0))); // NOI18N

        jEmployee.setModel(new javax.swing.table.DefaultTableModel(
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
        jEmployee.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jEmployeeMouseClicked(evt);
            }
        });
        jEmployee.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jEmployeeKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jEmployee);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        cmdRegister.setText("REGISTER");
        cmdRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdRegisterActionPerformed(evt);
            }
        });

        jButton2.setText("NEW");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("SEARCH");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("UPDATE");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("REPORT");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmdRegister)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdRegister)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Batang", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 153, 0));
        jLabel1.setText("EMPLOYEE SITE");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 653, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(193, 193, 193)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jEmployeeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jEmployeeMouseClicked
       int row=jEmployee.getSelectedRow();
            String tableClick=(jEmployee.getModel().getValueAt(row, 0).toString());
            try{
                String check="SELECT * FROM employee_db WHERE ID_NO='"+tableClick+"'";
                pst=conn.prepareStatement(check);
                rs=pst.executeQuery();
                if(rs.next()){
                    String val=rs.getString("ID_NO");
                    String val1=rs.getString("FNAME");
                    String val2=rs.getString("LNAME");
                    String val3=rs.getString("GENDER");
                   String val4=rs.getString("EMPLOYEE_NO");
                    String val5=rs.getString("PHONE");
                    String val6=rs.getString("PHONE2");
                  String val7=rs.getString("DEPARTMENT");
                    String val8=rs.getString("EMAIL");
                   // String val9=rs.getString("OCCUPATION");
                   // String val10=rs.getString("WORK_PLACE");
                    String val11=rs.getString("POSITION");
                    String val12=rs.getString("DATE");
                    txt_ID.setText(val);
                    txt_fName.setText(val1);
                    txt_lName.setText(val2);
                    cb_gender.setSelectedItem(val3);
                    txt_phone.setText(val5);
                    txt_phone2.setText(val6);
                    txt_email.setText(val8);
                    cbDept.setSelectedItem(val7);
                    txt_empNo.setText(val4);
                    cbPos.setSelectedItem(val11);
                    ((JTextField)jDate.getDateEditor().getUiComponent()).setText(val12);

                }else{
                    JOptionPane.showMessageDialog(null, "No registrations found");
                }

            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
    }//GEN-LAST:event_jEmployeeMouseClicked

    private void jEmployeeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jEmployeeKeyReleased
        if(evt.getKeyCode()==KeyEvent.VK_DOWN||evt.getKeyCode()==KeyEvent.VK_UP){
            int row=jEmployee.getSelectedRow();
            String tableClick=(jEmployee.getModel().getValueAt(row, 0).toString());
            try{
                String check="SELECT * FROM employee_db WHERE ID_NO='"+tableClick+"'";
                pst=conn.prepareStatement(check);
                rs=pst.executeQuery();
                if(rs.next()){
                    String val=rs.getString("ID_NO");
                    String val1=rs.getString("FNAME");
                    String val2=rs.getString("LNAME");
                    String val3=rs.getString("GENDER");
                   String val4=rs.getString("EMPLOYEE_NO");
                    String val5=rs.getString("PHONE");
                    String val6=rs.getString("PHONE2");
                  String val7=rs.getString("DEPARTMENT");
                    String val8=rs.getString("EMAIL");
                   // String val9=rs.getString("OCCUPATION");
                   // String val10=rs.getString("WORK_PLACE");
                    String val11=rs.getString("POSITION");
                    String val12=rs.getString("DATE");
                    txt_ID.setText(val);
                    txt_fName.setText(val1);
                    txt_lName.setText(val2);
                    cb_gender.setSelectedItem(val3);
                    txt_phone.setText(val5);
                    txt_phone2.setText(val6);
                    txt_email.setText(val8);
                    cbDept.setSelectedItem(val7);
                    txt_empNo.setText(val4);
                    cbPos.setSelectedItem(val11);
                    ((JTextField)jDate.getDateEditor().getUiComponent()).setText(val12);

                }else{
                    JOptionPane.showMessageDialog(null, "No registrations found");
                }

            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_jEmployeeKeyReleased

    private void cmdRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRegisterActionPerformed
        register();
    }//GEN-LAST:event_cmdRegisterActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        refresh();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        getEmployeeDetails();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        update();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void cbPosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPosActionPerformed
        
    }//GEN-LAST:event_cbPosActionPerformed

    private void cbDeptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbDeptActionPerformed
       addPos();
    }//GEN-LAST:event_cbDeptActionPerformed

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
            java.util.logging.Logger.getLogger(Employee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Employee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Employee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Employee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Employee dialog = new Employee(new javax.swing.JFrame(), true);
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
    private javax.swing.JComboBox<String> cbPos;
    private javax.swing.JComboBox<String> cb_gender;
    private javax.swing.JButton cmdRegister;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private com.toedter.calendar.JDateChooser jDate;
    private javax.swing.JTable jEmployee;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField txt_ID;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_empNo;
    private javax.swing.JTextField txt_fName;
    private javax.swing.JTextField txt_lName;
    private javax.swing.JTextField txt_phone;
    private javax.swing.JTextField txt_phone2;
    // End of variables declaration//GEN-END:variables
}
