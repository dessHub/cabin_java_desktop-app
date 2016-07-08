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
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import javax.swing.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import net.proteanit.sql.DbUtils;
import java.util.Date;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
public class CarDetails extends javax.swing.JDialog {

    Connection conn=null;
   PreparedStatement pst=null;
   ResultSet rs=null;
    public CarDetails(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        conn=ClassConnect.connectCabin();
        displayCars();
        countCars();
        showAvailable();
        updateJAvailable();
    }
    
    public void updateJAvailable(){
        try{
        String sql="SELECT * FROM available_db";
        pst=conn.prepareStatement(sql);
        rs=pst.executeQuery();
        jAvailable.setModel(DbUtils.resultSetToTableModel(rs));
        
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void delete(){
      String regNo=txt_reg.getText();
      String fleet=txt_fleetNo.getText();
      int p=JOptionPane.showConfirmDialog(null,"Selected field will be deleted.Continue?", "Delete",JOptionPane.YES_NO_OPTION);
      if(regNo.isEmpty()||fleet.isEmpty()){
          JOptionPane.showMessageDialog(null, "select field to be deleted");
      }else{
          if(p==0){
              try{
              String sql="";
              
              }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
          }
      }
    }
    
    public void closerFrame(){
            WindowEvent winClosingEvent=new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
    }
    
    public void updateCarDetails(){
        String regNo=txt_reg.getText().toUpperCase();
        String manufacturer=txt_manufacturer.getText().toUpperCase();
        String model=txt_model.getText().toUpperCase();
        String make=txt_make.getText().toUpperCase();
        String modelYear=txt_modelYear.getText();
        String color=txt_colour.getText().toUpperCase();
        String fleet=txt_fleetNo.getText().toUpperCase();
        String track=txt_tracker.getText().toUpperCase();
        String date=((JTextField)txt_date.getDateEditor().getUiComponent()).getText();
        if(regNo.isEmpty()||manufacturer.isEmpty()||model.isEmpty()||make.isEmpty()||modelYear.isEmpty()||color.isEmpty()||fleet.isEmpty()){
            JOptionPane.showMessageDialog(null, "Select row from table to be updated");
        }else{
            try{
                String state="AVAILABLE";
            String sql="UPDATE car_db SET TRACKER_CODE='"+track+"',REG_NO='"+regNo+"',FLEET_NO='"+fleet+"',MANUFACTURER='"+manufacturer+"',MODEL='"+model+"',MAKE='"+make+"',MODEL_YEAR='"+modelYear+"',COLOUR='"+color+"',REG_DATE='"+date+"' WHERE REG_NO='"+regNo+"' OR FLEET_NO='"+fleet+"'";
            pst=conn.prepareStatement(sql);
            pst.execute();
            {
                String avail="UPDATE available_db SET REG_NO='"+regNo+"',FLEET_NO='"+fleet+"' WHERE REG_NO='"+regNo+"' OR FLEET_NO='"+fleet+"'";
                pst=conn.prepareStatement(avail);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Succesfully updated");
            displayCars();
            updateJAvailable();
            }
            }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            
        }
        }
    }
    
     public void getCurrentDate(){
        
       DateFormat frmt=new SimpleDateFormat("yyyy-MM-dd");
       Calendar cal=Calendar.getInstance();
       Date date=new Date();
       txt_date.setDate(date);
         
    }
    
    public void getCarDetails(){
        String searchReg=txtReg.getText();
        String searchFleet=txtFleet.getText();
        
         try{
        String check="SELECT * FROM car_db WHERE REG_NO='"+searchReg+"' OR FLEET_NO='"+searchFleet+"'";
        pst=conn.prepareStatement(check);
        rs=pst.executeQuery();
        if(rs.next()){
            String val1=rs.getString("REG_NO");
            String val2=rs.getString("FLEET_NO");
            String val3=rs.getString("MANUFACTURER");
            String val4=rs.getString("MODEL");
            String val5=rs.getString("MAKE");
            String val6=rs.getString("MODEL_YEAR");
            String val7=rs.getString("COLOUR");
            String val8=rs.getString("REG_DATE");
            String val9=rs.getString("TRACKER_CODE");
        txt_reg.setText(val1);
        txt_manufacturer.setText(val3);
        txt_model.setText(val4);
        txt_make.setText(val5);
        txt_modelYear.setText(val6);
        txt_colour.setText(val7);
        txt_fleetNo.setText(val2);
        txt_tracker.setText(val9);
        ((JTextField)txt_date.getDateEditor().getUiComponent()).setText(val8);
            
          }else{
            JOptionPane.showMessageDialog(null, "No registrations found"); 
        }
           
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
     public void countCars(){
         try{
         String sql="SELECT count(REG_NO) FROM car_db";
         pst=conn.prepareStatement(sql);
         rs=pst.executeQuery();
         if(rs.next()){
             String val=rs.getString("count(REG_NO)");
             txt_noCars.setText(val);
         }
         }catch(Exception e){
              JOptionPane.showMessageDialog(null, e);
         }
     }
     
     public void showAvailable(){
         String state="AVAILABLE";
         try{
         String sql="SELECT count(REG_NO) FROM available_db WHERE STATE='"+state+"'";
         pst=conn.prepareStatement(sql);
         rs=pst.executeQuery();
         if(rs.next()){
             String val=rs.getString("count(REG_NO)");
             txt_avail.setText(val);
         }
         }catch(Exception e){
              JOptionPane.showMessageDialog(null, e);
         }
     }
    
    public void displayCars(){
        try{
            String sql="SELECT REG_NO,FLEET_NO,MANUFACTURER,COLOUR FROM car_db";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            jCar.setModel(DbUtils.resultSetToTableModel(rs));
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
        txt_modelYear = new javax.swing.JTextField();
        txt_fleetNo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_make = new javax.swing.JTextField();
        txt_date = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        txt_reg = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_model = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txt_manufacturer = new javax.swing.JTextField();
        txt_colour = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txt_tracker = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtReg = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtFleet = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txt_noCars = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txt_avail = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel7 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jCar = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jAvailable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Car Details", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 12), new java.awt.Color(0, 0, 204))); // NOI18N

        jLabel8.setForeground(new java.awt.Color(51, 51, 0));
        jLabel8.setText("Registration Date:");

        jLabel7.setForeground(new java.awt.Color(51, 51, 0));
        jLabel7.setText("Model Year:");

        jLabel5.setForeground(new java.awt.Color(51, 51, 0));
        jLabel5.setText("Colour:");

        jLabel4.setForeground(new java.awt.Color(51, 51, 0));
        jLabel4.setText("Make:");

        jLabel2.setForeground(new java.awt.Color(51, 51, 0));
        jLabel2.setText("Registration No:");

        txt_date.setDateFormatString("yyyy-MM-dd");

        jLabel9.setForeground(new java.awt.Color(51, 51, 0));
        jLabel9.setText("Manufacturer:");

        jLabel3.setForeground(new java.awt.Color(51, 51, 0));
        jLabel3.setText("Model:");

        jLabel6.setForeground(new java.awt.Color(51, 51, 0));
        jLabel6.setText("Fleet No:");

        jLabel15.setText("Tracker Code:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_date, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel4)
                                .addComponent(jLabel9)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3)
                                .addComponent(jLabel8)
                                .addComponent(jLabel6)
                                .addComponent(jLabel5)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(2, 2, 2)
                                    .addComponent(jLabel7))
                                .addComponent(txt_fleetNo, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                                .addComponent(txt_colour)
                                .addComponent(txt_modelYear)
                                .addComponent(txt_make)
                                .addComponent(txt_model)
                                .addComponent(txt_manufacturer)
                                .addComponent(txt_reg))
                            .addComponent(jLabel15)
                            .addComponent(txt_tracker, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 9, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_reg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_manufacturer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_model, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_make, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_modelYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_colour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_fleetNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_tracker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search By", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 12), new java.awt.Color(0, 51, 153))); // NOI18N

        jLabel1.setText("Registration No:");

        jLabel10.setText("OR");

        jLabel11.setText("Fleet  No:");

        jButton1.setBackground(new java.awt.Color(255, 255, 204));
        jButton1.setForeground(new java.awt.Color(102, 102, 0));
        jButton1.setText("GO");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 255, 204));
        jButton2.setForeground(new java.awt.Color(102, 102, 0));
        jButton2.setText("GO");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtReg, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(20, 20, 20))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addGap(37, 37, 37)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtFleet, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtReg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFleet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setForeground(new java.awt.Color(102, 102, 0));

        jButton3.setBackground(new java.awt.Color(255, 255, 204));
        jButton3.setForeground(new java.awt.Color(102, 102, 0));
        jButton3.setText("UPDATE");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(255, 255, 204));
        jButton4.setForeground(new java.awt.Color(102, 102, 0));
        jButton4.setText("DELETE");

        jButton5.setBackground(new java.awt.Color(255, 255, 204));
        jButton5.setForeground(new java.awt.Color(102, 102, 0));
        jButton5.setText("REPORT");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(255, 255, 204));
        jButton6.setForeground(new java.awt.Color(102, 102, 0));
        jButton6.setText("REFRESH");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
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
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jButton3)
                .addGap(18, 18, 18)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setText("No Of Cars");

        jLabel13.setText("Available");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel12))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel13))
                            .addComponent(txt_noCars, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txt_avail)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_noCars, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_avail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(102, 102, 0));
        jLabel14.setText("CAR PORTAL");

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jCar.setModel(new javax.swing.table.DefaultTableModel(
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
        jCar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCarMouseClicked(evt);
            }
        });
        jCar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jCarKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jCar);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("ALL CARS", jPanel5);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "AVAILABLE CARS", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Batang", 0, 12), new java.awt.Color(204, 204, 0))); // NOI18N

        jAvailable.setModel(new javax.swing.table.DefaultTableModel(
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
        jAvailable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jAvailableMouseClicked(evt);
            }
        });
        jAvailable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jAvailableKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jAvailable);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 300, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("AVAILABLE CARS", jPanel6);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(230, 230, 230))
            .addGroup(layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        getCarDetails();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
         getCarDetails();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jCarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCarMouseClicked
     int row=jCar.getSelectedRow();
    String tableClick=(jCar.getModel().getValueAt(row, 0).toString());
        try{
        String check="SELECT * FROM car_db WHERE REG_NO='"+tableClick+"'";
        pst=conn.prepareStatement(check);
        rs=pst.executeQuery();
        if(rs.next()){
            String val1=rs.getString("REG_NO");
            String val2=rs.getString("FLEET_NO");
            String val3=rs.getString("MANUFACTURER");
            String val4=rs.getString("MODEL");
            String val5=rs.getString("MAKE");
            String val6=rs.getString("MODEL_YEAR");
            String val7=rs.getString("COLOUR");
            String val8=rs.getString("REG_DATE");
            String val9=rs.getString("TRACKER_CODE");
        txt_reg.setText(val1);
        txt_manufacturer.setText(val3);
        txt_model.setText(val4);
        txt_make.setText(val5);
        txt_modelYear.setText(val6);
        txt_colour.setText(val7);
        txt_fleetNo.setText(val2);
        txt_tracker.setText(val9);
        ((JTextField)txt_date.getDateEditor().getUiComponent()).setText(val8);
            
          }else{
            JOptionPane.showMessageDialog(null, "No registrations found"); 
        }
           
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jCarMouseClicked

    private void jCarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCarKeyReleased
        if(evt.getKeyCode()==KeyEvent.VK_UP ||evt.getKeyCode()==KeyEvent.VK_DOWN ){
              int row=jCar.getSelectedRow();
    String tableClick=(jCar.getModel().getValueAt(row, 0).toString());
        try{
        String check="SELECT * FROM car_db WHERE REG_NO='"+tableClick+"'";
        pst=conn.prepareStatement(check);
        rs=pst.executeQuery();
        if(rs.next()){
            String val1=rs.getString("REG_NO");
            String val2=rs.getString("FLEET_NO");
            String val3=rs.getString("MANUFACTURER");
            String val4=rs.getString("MODEL");
            String val5=rs.getString("MAKE");
            String val6=rs.getString("MODEL_YEAR");
            String val7=rs.getString("COLOUR");
            String val8=rs.getString("REG_DATE");
            String val9=rs.getString("TRACKER_CODE");
        txt_reg.setText(val1);
        txt_manufacturer.setText(val3);
        txt_model.setText(val4);
        txt_make.setText(val5);
        txt_modelYear.setText(val6);
        txt_colour.setText(val7);
        txt_fleetNo.setText(val2);
        txt_tracker.setText(val9);
        ((JTextField)txt_date.getDateEditor().getUiComponent()).setText(val8);
            
          }else{
            JOptionPane.showMessageDialog(null, "No registrations found"); 
        }
           
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        }
    }//GEN-LAST:event_jCarKeyReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        updateCarDetails();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        txt_reg.setText(null);
        txt_manufacturer.setText(null);
        txt_model.setText(null);
        txt_make.setText(null);
        txt_modelYear.setText(null);
        txt_colour.setText(null);
        txt_fleetNo.setText(null);
        txt_tracker.setText(null);
        ((JTextField)txt_date.getDateEditor().getUiComponent()).setText(null);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jAvailableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jAvailableKeyReleased
         if(evt.getKeyCode()==KeyEvent.VK_UP ||evt.getKeyCode()==KeyEvent.VK_DOWN ){
              int row=jAvailable.getSelectedRow();
    String tableClick=(jAvailable.getModel().getValueAt(row, 0).toString());
        try{
        String check="SELECT * FROM car_db WHERE REG_NO='"+tableClick+"'";
        pst=conn.prepareStatement(check);
        rs=pst.executeQuery();
        if(rs.next()){
            String val1=rs.getString("REG_NO");
            String val2=rs.getString("FLEET_NO");
            String val3=rs.getString("MANUFACTURER");
            String val4=rs.getString("MODEL");
            String val5=rs.getString("MAKE");
            String val6=rs.getString("MODEL_YEAR");
            String val7=rs.getString("COLOUR");
            String val8=rs.getString("REG_DATE");
            String val9=rs.getString("TRACKER_CODE");
        txt_reg.setText(val1);
        txt_manufacturer.setText(val3);
        txt_model.setText(val4);
        txt_make.setText(val5);
        txt_modelYear.setText(val6);
        txt_colour.setText(val7);
        txt_fleetNo.setText(val2);
        txt_tracker.setText(val9);
        ((JTextField)txt_date.getDateEditor().getUiComponent()).setText(val8);
            
          }else{
            JOptionPane.showMessageDialog(null, "No registrations found"); 
        }
           
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        }
    }//GEN-LAST:event_jAvailableKeyReleased

    private void jAvailableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jAvailableMouseClicked
          int row=jAvailable.getSelectedRow();
    String tableClick=(jAvailable.getModel().getValueAt(row, 0).toString());
        try{
        String check="SELECT * FROM car_db WHERE REG_NO='"+tableClick+"'";
        pst=conn.prepareStatement(check);
        rs=pst.executeQuery();
        if(rs.next()){
            String val1=rs.getString("REG_NO");
            String val2=rs.getString("FLEET_NO");
            String val3=rs.getString("MANUFACTURER");
            String val4=rs.getString("MODEL");
            String val5=rs.getString("MAKE");
            String val6=rs.getString("MODEL_YEAR");
            String val7=rs.getString("COLOUR");
            String val8=rs.getString("REG_DATE");
            String val9=rs.getString("TRACKER_CODE");
        txt_reg.setText(val1);
        txt_manufacturer.setText(val3);
        txt_model.setText(val4);
        txt_make.setText(val5);
        txt_modelYear.setText(val6);
        txt_colour.setText(val7);
        txt_fleetNo.setText(val2);
        txt_tracker.setText(val9);
        ((JTextField)txt_date.getDateEditor().getUiComponent()).setText(val8);
            
          }else{
            JOptionPane.showMessageDialog(null, "No registrations found"); 
        }
           
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jAvailableMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
       // closerFrame();
        try{
        JasperDesign jd=JRXmlLoader.load("E:\\netbeans projects\\Cabin Management system\\car_report.jrxml");
        String sql="SELECT * FROM car_db";
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
            java.util.logging.Logger.getLogger(CarDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CarDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CarDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CarDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CarDetails dialog = new CarDetails(new javax.swing.JFrame(), true);
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
    private javax.swing.JTable jAvailable;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JTable jCar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField txtFleet;
    private javax.swing.JTextField txtReg;
    private javax.swing.JTextField txt_avail;
    private javax.swing.JTextField txt_colour;
    public static com.toedter.calendar.JDateChooser txt_date;
    public static javax.swing.JTextField txt_fleetNo;
    public static javax.swing.JTextField txt_make;
    public static javax.swing.JTextField txt_manufacturer;
    public static javax.swing.JTextField txt_model;
    public static javax.swing.JTextField txt_modelYear;
    private javax.swing.JTextField txt_noCars;
    public static javax.swing.JTextField txt_reg;
    private javax.swing.JTextField txt_tracker;
    // End of variables declaration//GEN-END:variables
}
