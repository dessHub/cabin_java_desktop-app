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
import javax.swing.*;
import java.sql.*;
import java.util.Date;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
public class ClientRegistration extends javax.swing.JDialog {

   Connection conn=null;
   PreparedStatement pst=null;
   ResultSet rs=null;
    public ClientRegistration(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        conn=ClassConnect.connectCabin();
        currentDate();
        displayClients();
    }
    
    public void currentDate(){
        Date date=new Date();
        jDate.setDate(date);
        
    }
    
    public void displayClients(){
        try{
        String sql="SELECT ID_NO,FNAME,LNAME,RESIDENCE,PHONE FROM client_db";
        pst=conn.prepareStatement(sql);
        rs=pst.executeQuery();
        jClient.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
     public void getClientDetails(){
        String id=txt_ID.getText();
               
         try{
        String check="SELECT * FROM client_db WHERE ID_NO='"+id+"'";
        pst=conn.prepareStatement(check);
        rs=pst.executeQuery();
        if(rs.next()){
            String val1=rs.getString("FNAME");
            String val2=rs.getString("LNAME");
            String val3=rs.getString("GENDER");
            String val4=rs.getString("RESIDENCE");
            String val5=rs.getString("PHONE");
            String val6=rs.getString("PHONE2");
            String val7=rs.getString("WORK_PHONE");
            String val8=rs.getString("EMAIL");
            String val9=rs.getString("OCCUPATION");
            String val10=rs.getString("WORK_PLACE");
            String val11=rs.getString("POSITION");
            String val12=rs.getString("DATE");
        txt_fName.setText(val1);
        txt_lName.setText(val2);
        cb_gender.setSelectedItem(val3);
        txt_area.setText(val4);
         txt_phone.setText(val5);
        txt_phone2.setText(val6);
        txt_workPhone.setText(val7);
        txt_email.setText(val8);
        txt_occupation.setText(val9);
        txt_workPlace.setText(val10);
        txt_position.setText(val11);
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
        txt_area.setText(null);
         txt_phone.setText(null);
        txt_phone2.setText(null);
        txt_workPhone.setText(null);
        txt_email.setText(null);
        txt_occupation.setText(null);
        txt_workPlace.setText(null);
        txt_position.setText(null);
        txt_ID.setText(null);
        currentDate();
     }
    
    public void register(){
       String id=txt_ID.getText().toUpperCase();
        String fName=txt_fName.getText().toUpperCase();
        String lName=txt_lName.getText().toUpperCase();
        String gender=cb_gender.getSelectedItem().toString();
        String area=txt_area.getText().toUpperCase();
         String phone=txt_phone.getText().toUpperCase();
        String phone2=txt_phone2.getText().toUpperCase();
        String wPhone=txt_workPhone.getText().toUpperCase();
        String email=txt_email.getText().toLowerCase();
        String occupation=txt_occupation.getText().toUpperCase();
        String place=txt_workPlace.getText().toUpperCase();
        String position=txt_position.getText().toUpperCase();
        String date=((JTextField)jDate.getDateEditor().getUiComponent()).getText();
        if(id.isEmpty()||fName.isEmpty()||phone.isEmpty()||occupation.isEmpty()){
            JOptionPane.showMessageDialog(null, "Input data into all fields");
        }else{
        try{
       String checkId="SELECT * FROM client_db WHERE ID_NO='"+id+"'";
       pst=conn.prepareStatement(checkId);
       rs=pst.executeQuery();
       if(rs.next()){
           JOptionPane.showMessageDialog(null, "ID NO already exist");
       }else{
           String sql="INSERT INTO client_db (ID_NO,FNAME,LNAME,GENDER,RESIDENCE,PHONE,PHONE2,WORK_PHONE,EMAIL,OCCUPATION,WORK_PLACE,POSITION,DATE) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
           pst=conn.prepareStatement(sql);
           pst.setString(1, id);
           pst.setString(2, fName);
           pst.setString(3, lName);
           pst.setString(4, gender);
           pst.setString(5, area);
           pst.setString(6, phone);
           pst.setString(7, phone2);
           pst.setString(8, wPhone);
            pst.setString(9, email);
           pst.setString(10, occupation);
           pst.setString(11, place);
           pst.setString(12, position);
           pst.setString(13, date);
           pst.execute();
            JOptionPane.showMessageDialog(null, "Client Successfully registered");
             displayClients();
        }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        }
    }
    
    public void update(){
         String id=txt_ID.getText().toUpperCase();
        String fName=txt_fName.getText().toUpperCase();
        String lName=txt_lName.getText().toUpperCase();
        String gender=cb_gender.getSelectedItem().toString();
        String area=txt_area.getText();
         String phone=txt_phone.getText().toUpperCase();
        String phone2=txt_phone2.getText().toUpperCase();
        String wPhone=txt_workPhone.getText().toUpperCase();
        String email=txt_email.getText().toLowerCase();
        String occupation=txt_occupation.getText();
        String place=txt_workPlace.getText().toUpperCase();
        String position=txt_position.getText().toUpperCase();
        String date=((JTextField)jDate.getDateEditor().getUiComponent()).getText();
        if(id.isEmpty()||fName.isEmpty()||phone.isEmpty()||occupation.isEmpty()){
            JOptionPane.showMessageDialog(null, "Input data into all fields");
        }else{
        try{
       
           String sql="UPDATE client_db SET ID_NO=?,FNAME=?,LNAME=?,GENDER=?,RESIDENCE=?,PHONE=?,PHONE2=?,WORK_PHONE=?,EMAIL=?,OCCUPATION=?,WORK_PLACE=?,POSITION=?,DATE=? WHERE ID_NO='"+id+"' OR PHONE='"+phone+"' ";
           pst=conn.prepareStatement(sql);
           pst.setString(1, id);
           pst.setString(2, fName);
           pst.setString(3, lName);
           pst.setString(4, gender);
           pst.setString(5, area);
           pst.setString(6, phone);
           pst.setString(7, phone2);
           pst.setString(8, wPhone);
            pst.setString(9, email);
           pst.setString(10, occupation);
           pst.setString(11, place);
           pst.setString(12, position);
           pst.setString(13, date);
           pst.execute();
            JOptionPane.showMessageDialog(null, "Successfully updated");
             displayClients();
        
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
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
        jSeparator1 = new javax.swing.JSeparator();
        jPanel5 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txt_occupation = new javax.swing.JTextField();
        txt_workPlace = new javax.swing.JTextField();
        txt_position = new javax.swing.JTextField();
        jDate = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_phone = new javax.swing.JTextField();
        txt_phone2 = new javax.swing.JTextField();
        txt_workPhone = new javax.swing.JTextField();
        txt_email = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_ID = new javax.swing.JTextField();
        txt_fName = new javax.swing.JTextField();
        txt_lName = new javax.swing.JTextField();
        txt_area = new javax.swing.JTextField();
        cb_gender = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jClient = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        cmdRegister = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("CLIENT PORTAL");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Others", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Arial", 0, 10), new java.awt.Color(204, 204, 0))); // NOI18N

        jLabel11.setText("Ocupation:");

        jLabel12.setText("Work place:");

        jLabel13.setText("Position:");

        jLabel14.setText("Date:");

        jDate.setDateFormatString(" yyyy-MM-dd");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14))
                .addGap(40, 40, 40)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_occupation)
                    .addComponent(txt_workPlace)
                    .addComponent(txt_position)
                    .addComponent(jDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txt_occupation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txt_workPlace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txt_position, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 20, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Contact Info", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Arial", 0, 10), new java.awt.Color(204, 204, 0))); // NOI18N

        jLabel7.setText("Phone NO:*");

        jLabel8.setText("Phone No:");

        jLabel9.setText("Work Phone No:");

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
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_phone)
                    .addComponent(txt_phone2)
                    .addComponent(txt_workPhone)
                    .addComponent(txt_email))
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
                    .addComponent(jLabel9)
                    .addComponent(txt_workPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        jLabel6.setText("Residential Area:");

        cb_gender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-select-", "MALE", "FEMALE" }));

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
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_ID)
                    .addComponent(txt_fName)
                    .addComponent(txt_lName)
                    .addComponent(txt_area)
                    .addComponent(cb_gender, 0, 102, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txt_area, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Clients Table", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Arial", 0, 10), new java.awt.Color(204, 204, 0))); // NOI18N

        jClient.setModel(new javax.swing.table.DefaultTableModel(
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
        jClient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jClientMouseClicked(evt);
            }
        });
        jClient.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jClientKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jClient);

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
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

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

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cmdRegister, jButton3});

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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(202, 202, 202)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 557, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cmdRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRegisterActionPerformed
        register();
    }//GEN-LAST:event_cmdRegisterActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        getClientDetails();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jClientMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jClientMouseClicked
       int row=jClient.getSelectedRow();
       String tableClick=(jClient.getModel().getValueAt(row, 0).toString());
        try{
        String check="SELECT * FROM client_db WHERE ID_NO='"+tableClick+"'";
        pst=conn.prepareStatement(check);
        rs=pst.executeQuery();
        if(rs.next()){
            String val=rs.getString("ID_NO");
            String val1=rs.getString("FNAME");
            String val2=rs.getString("LNAME");
            String val3=rs.getString("GENDER");
            String val4=rs.getString("RESIDENCE");
            String val5=rs.getString("PHONE");
            String val6=rs.getString("PHONE2");
            String val7=rs.getString("WORK_PHONE");
            String val8=rs.getString("EMAIL");
            String val9=rs.getString("OCCUPATION");
            String val10=rs.getString("WORK_PLACE");
            String val11=rs.getString("POSITION");
            String val12=rs.getString("DATE");
            txt_ID.setText(val);
        txt_fName.setText(val1);
        txt_lName.setText(val2);
        cb_gender.setSelectedItem(val3);
        txt_area.setText(val4);
         txt_phone.setText(val5);
        txt_phone2.setText(val6);
        txt_workPhone.setText(val7);
        txt_email.setText(val8);
        txt_occupation.setText(val9);
        txt_workPlace.setText(val10);
        txt_position.setText(val11);
        ((JTextField)jDate.getDateEditor().getUiComponent()).setText(val12);
            
          }else{
            JOptionPane.showMessageDialog(null, "No registrations found"); 
        }
           
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jClientMouseClicked

    private void jClientKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jClientKeyReleased
        if(evt.getKeyCode()==KeyEvent.VK_DOWN||evt.getKeyCode()==KeyEvent.VK_UP){
            int row=jClient.getSelectedRow();
       String tableClick=(jClient.getModel().getValueAt(row, 0).toString());
        try{
        String check="SELECT * FROM client_db WHERE ID_NO='"+tableClick+"'";
        pst=conn.prepareStatement(check);
        rs=pst.executeQuery();
        if(rs.next()){
            String val=rs.getString("ID_NO");
            String val1=rs.getString("FNAME");
            String val2=rs.getString("LNAME");
            String val3=rs.getString("GENDER");
            String val4=rs.getString("RESIDENCE");
            String val5=rs.getString("PHONE");
            String val6=rs.getString("PHONE2");
            String val7=rs.getString("WORK_PHONE");
            String val8=rs.getString("EMAIL");
            String val9=rs.getString("OCCUPATION");
            String val10=rs.getString("WORK_PLACE");
            String val11=rs.getString("POSITION");
            String val12=rs.getString("DATE");
            txt_ID.setText(val);
        txt_fName.setText(val1);
        txt_lName.setText(val2);
        cb_gender.setSelectedItem(val3);
        txt_area.setText(val4);
         txt_phone.setText(val5);
        txt_phone2.setText(val6);
        txt_workPhone.setText(val7);
        txt_email.setText(val8);
        txt_occupation.setText(val9);
        txt_workPlace.setText(val10);
        txt_position.setText(val11);
        ((JTextField)jDate.getDateEditor().getUiComponent()).setText(val12);
            
          }else{
            JOptionPane.showMessageDialog(null, "No registrations found"); 
        }
           
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        }
    }//GEN-LAST:event_jClientKeyReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        update();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        refresh();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
       try{
       //String report="E:\\netbeans projects\\Cabin Management system\\clients.jrxml";
       JasperDesign jd=JRXmlLoader.load("E:\\netbeans projects\\Cabin Management system\\clients.jrxml");
       String sql="SELECT * FROM client_db";
       JRDesignQuery newQuery=new JRDesignQuery();
       newQuery.setText(sql);
       jd.setQuery(newQuery);
       JasperReport jr=JasperCompileManager.compileReport(jd);
       JasperPrint jp=JasperFillManager.fillReport(jr, null,conn);
       JasperViewer.viewReport(jp,false);
       
       
       }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

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
            java.util.logging.Logger.getLogger(ClientRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ClientRegistration dialog = new ClientRegistration(new javax.swing.JFrame(), true);
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
    private javax.swing.JComboBox<String> cb_gender;
    private javax.swing.JButton cmdRegister;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JTable jClient;
    private com.toedter.calendar.JDateChooser jDate;
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
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField txt_ID;
    private javax.swing.JTextField txt_area;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_fName;
    private javax.swing.JTextField txt_lName;
    private javax.swing.JTextField txt_occupation;
    private javax.swing.JTextField txt_phone;
    private javax.swing.JTextField txt_phone2;
    private javax.swing.JTextField txt_position;
    private javax.swing.JTextField txt_workPhone;
    private javax.swing.JTextField txt_workPlace;
    // End of variables declaration//GEN-END:variables
}
