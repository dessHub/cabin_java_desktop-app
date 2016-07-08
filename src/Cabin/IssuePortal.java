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
import java.awt.Color;
import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
public class IssuePortal extends javax.swing.JFrame {

    /**
     * Creates new form IssuePortal
     */Connection conn=null;
         PreparedStatement pst=null;
        ResultSet rs=null;
    public IssuePortal() {
        initComponents();
         conn=ClassConnect.connectCabin();
         getFleet();
         currentDate();
    }
    
      public void currentDate(){
        Date today=new Date();
        jToday.setDate(today);
        jToday1.setDate(today);
        jTodayCharge.setDate(today);
        getTime();
        
    }
    
    public void getTime(){
       
       
        Calendar cal=new GregorianCalendar();
        int hour=cal.get(Calendar.HOUR_OF_DAY);
       int min=cal.get(Calendar.MINUTE);
       int sec=cal.get(Calendar.SECOND);
       String h=Integer.toString(hour);
       String m=Integer.toString(min);
       String s=Integer.toString(sec);
       time.setText(hour+":"+min+":"+sec);
           
         
}
    
    public void getFleet(){
        
        try{
        String sql="SELECT FLEET_NO FROM available_db WHERE STATE='AVAILABLE'";
        pst=conn.prepareStatement(sql);
        rs=pst.executeQuery();
        while(rs.next()){
            String val=rs.getString("FLEET_NO");
            cbFleet.addItem(val);
        }
        
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
}
    }
    
    public void getActiveFleet(){
       String id=txt_ID.getText();
        try{
        String sql="SELECT FLEET_NO FROM issue_db WHERE STATE='ACTIVE' AND ID_NO='"+id+"'";
        pst=conn.prepareStatement(sql);
        rs=pst.executeQuery();
        while(rs.next()){
            String val=rs.getString("FLEET_NO");
            cbFleet1.addItem(val);
        }
        
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
}
    }
    
    public void getCarDetails(){
        String id=txt_ID.getText();
        String searchFleet=cbFleet.getSelectedItem().toString();
        int indexFleet=cbFleet.getSelectedIndex();
        if(id.isEmpty()){
            JOptionPane.showMessageDialog(null, "Enter Valid Id No to proceed");
        }else{
        if(indexFleet==0){
            JOptionPane.showMessageDialog(null, "Select Fleet No to proceed");
        }else{
             try{
        String check="SELECT * FROM car_db WHERE FLEET_NO='"+searchFleet+"'";
        pst=conn.prepareStatement(check);
        rs=pst.executeQuery();
        if(rs.next()){
            String val1=rs.getString("REG_NO");
            String val3=rs.getString("MANUFACTURER");
            String val4=rs.getString("MODEL");
            String val7=rs.getString("COLOUR");
            String val9=rs.getString("TRACKER_CODE");
        txt_reg.setText(val1);
        txt_manufacturer.setText(val3);
        txt_model.setText(val4);
        txt_colour.setText(val7);
        txt_tracker.setText(val9);
                   
          }else{
            JOptionPane.showMessageDialog(null, "No registrations found"); 
        }
          getPrice(); 
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        }
        }
    }
    
     public void getCarDetails1(){
        String id=txt_ID.getText();
        String searchFleet=cbFleet1.getSelectedItem().toString();
        int indexFleet=cbFleet1.getSelectedIndex();
        if(id.isEmpty()){
            JOptionPane.showMessageDialog(null, "Enter Valid Id No to proceed");
        }else{
        if(indexFleet==0){
            JOptionPane.showMessageDialog(null, "Select Fleet No to proceed");
        }else{
             try{
        String check="SELECT * FROM car_db WHERE FLEET_NO='"+searchFleet+"'";
        pst=conn.prepareStatement(check);
        rs=pst.executeQuery();
        if(rs.next()){
            String val1=rs.getString("REG_NO");
            String val3=rs.getString("MANUFACTURER");
            String val4=rs.getString("MODEL");
            String val7=rs.getString("COLOUR");
            String val9=rs.getString("TRACKER_CODE");
        txt_reg1.setText(val1);
        txt_manufacturer1.setText(val3);
        txt_model1.setText(val4);
        txt_colour1.setText(val7);
        txt_tracker1.setText(val9);
        {
            String expect="SELECT EXPECTED_DATE FROM issue_db WHERE ID_NO='"+id+"' AND FLEET_NO='"+searchFleet+"' AND STATE='ACTIVE'";
            pst=conn.prepareStatement(expect);
            rs=pst.executeQuery();
            if(rs.next()){
                String val=rs.getString("EXPECTED_DATE");
                        ((JTextField)jExpectedDate1.getDateEditor().getUiComponent()).setText(val);
            }
        }
                   
          }else{
            JOptionPane.showMessageDialog(null, "No registrations found"); 
        }
          
          getDateDiff();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        }
        }
    }
    
    public static void getNextDate(int days){
       // Date date=new Date();
       days=IssuePortal.getDays(days);
        Calendar date=Calendar.getInstance();
        date.add(Calendar.DAY_OF_MONTH, days);
       jExpectedDate.setCalendar(date);
        //((JTextField)jExpectedDate.getDateEditor().getUiComponent())(date);
        
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
        txt_fName.setText(val1);
        txt_lName.setText(val2);
        txt_gender.setText(val3);
        txt_area.setText(val4);
         
          }else{
            JOptionPane.showMessageDialog(null, "No registrations found"); 
        }
           
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void getPrice(){
       String model=txt_model.getText();
        try{
        String sql="SELECT PRICE FROM price_db WHERE MODEL='"+model+"'";
        pst=conn.prepareStatement(sql);
        rs=pst.executeQuery();
        if(rs.next()){
            String val=rs.getString("PRICE");
            txt_charges.setText(val);
        }
        
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
     
    public static int getTotal(int days,int charges){
       days=IssuePortal.getDays(days);
       charges=IssuePortal.dailyCharge(charges);
        int total=0;
        total=days*charges;
        return total;
        
    }
    
    public static int dailyCharge(int price){
        String val1=txt_charges.getText();
       price=Integer.parseInt(val1);
       return price;
    }
    
    public static int getDays(int days){
         String val2=cbDays.getSelectedItem().toString();
         days=Integer.parseInt(val2);
         return days;
    }
    
    public static void displayTotal(){
      String price=txt_charges.getText();
        if(price.isEmpty()){
            JOptionPane.showMessageDialog(null, "Select Fleet No to proceed");
        }else{
        int no=IssuePortal.getTotal(0, 0);
       String dis=Integer.toString(no);
       txt_total.setText(dis);
        
        }
        getNextDate(0);
    }
    
    public void pay(){
        int index=cbBank.getSelectedIndex();
        String bank=cbBank.getSelectedItem().toString();
        String chaque=txt_cheque.getText();
        String slipDate=((JTextField)jSlipDate.getDateEditor().getUiComponent()).getText();
        String id=txt_ID.getText();
        String date=((JTextField)jToday.getDateEditor().getUiComponent()).getText();
        String price=txt_total.getText();
        
        if(index==0||chaque.isEmpty()||price.isEmpty()){
            JOptionPane.showMessageDialog(null, "Fill all the fields to proceed");
        }else{
            try{
             String checkSlip="SELECT * FROM transaction_db WHERE SLIP_NO='"+chaque+"'";
             pst=conn.prepareStatement(checkSlip);
             rs=pst.executeQuery();
             if(rs.next()){
                 JOptionPane.showMessageDialog(null, "Bank slip no already in use");
                 
             }else{
                 String sql="INSERT INTO transaction_db (ID_NO,SLIP_NO,BANK,SLIP_DATE,DATE_PAID,PRICE) VALUES (?,?,?,?,?,?)";
                 pst=conn.prepareStatement(sql);
                 pst.setString(1, id);
                 pst.setString(2, chaque);
                 pst.setString(3, bank);
                 pst.setString(4, slipDate);
                 pst.setString(5, date);
                 pst.setString(6, price);
                 
                 pst.execute();
                 JOptionPane.showMessageDialog(null, "Successfull.Continue");
             }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        }
       
    }
    
    public void payExtra(){
        int index=cbMode.getSelectedIndex();
        String mode=cbMode.getSelectedItem().toString();
        String date=((JTextField)jTodayCharge.getDateEditor().getUiComponent()).getText();
        String id=txt_ID.getText();
        String price=txt_sum.getText();
        int val=Integer.parseInt(price);
        if(val==0){
            JOptionPane.showMessageDialog(null, "Clear the client");
            jPanel12.setBackground(Color.GREEN);
        }else{
        if(index==0){
            JOptionPane.showMessageDialog(null, "Select mode of payment to proceed");
        }else{
            try{
             
                 String sql="INSERT INTO transaction_db (ID_NO,MODE,DATE_PAID,PRICE) VALUES (?,?,?,?)";
                 pst=conn.prepareStatement(sql);
                 pst.setString(1, id);
                 pst.setString(2, mode);
                 pst.setString(3, date);
                 pst.setString(4, price);
                 
                 pst.execute();
                 JOptionPane.showMessageDialog(null, "Charges Cleared.Continue");
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        }
        }
    }
    
    public void printIssueReport(){
        String today=((JTextField)jToday.getDateEditor().getUiComponent()).getText();
        String id=txt_ID.getText();
        String slip=((JTextField)jSlipDate.getDateEditor().getUiComponent()).getText();
        try{
            JasperDesign jd=JRXmlLoader.load("E:\\netbeans projects\\Cabin Management system\\REPORTS\\transaction_report.jrxml ");
            String sql="SELECT * FROM transaction_db WHERE ID_NO='"+id+"' AND DATE_PAID='"+today+"' AND SLIP_DATE='"+slip+"'";
            JRDesignQuery newQuery=new JRDesignQuery();
            newQuery.setText(sql);
            jd.setQuery(newQuery);
            JasperReport jr=JasperCompileManager.compileReport(jd);
            JasperPrint jp=JasperFillManager.fillReport(jr, null, conn);
            JasperViewer.viewReport(jp,false);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void issue(){
        String id=txt_ID.getText();
        String fleet=cbFleet.getSelectedItem().toString();
        String date=((JTextField)jToday.getDateEditor().getUiComponent()).getText();
        String expected=((JTextField)jExpectedDate.getDateEditor().getUiComponent()).getText();
        String state="ACTIVE";
        String days=cbDays.getSelectedItem().toString();
        try{
        String check="SELECT * FROM transaction_db WHERE DATE_PAID='"+date+"' AND ID_NO='"+id+"'";
        pst=conn.prepareStatement(check);
        rs=pst.executeQuery();
        if(rs.next()){
            String sql="INSERT INTO issue_db (ID_NO,FLEET_NO,ISSUE_DATE,EXPECTED_DATE,STATE,DAYS) VALUES (?,?,?,?,?,?)";
            pst=conn.prepareStatement(sql);
            pst.setString(1, id);
            pst.setString(2, fleet);
            pst.setString(3, date);
            pst.setString(4, expected);
            pst.setString(5, state);
            pst.setString(6, days);
            pst.execute();
            JOptionPane.showMessageDialog(null, "SUCCESSFULLY ISSUED");
            displayRecords();
            {
                String avail="UPDATE available_db SET STATE='"+state+"' WHERE FLEET_NO='"+fleet+"'";
                pst=conn.prepareStatement(avail);
                pst.execute();
                printIssueReport();
            }
        }else{
            JOptionPane.showMessageDialog(null, "Complete the payment before proceeding");
        }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        
    }
    
    public void displayRecords(){
        String id=txt_ID.getText();
        try{
        String sql="SELECT * FROM issue_db where ID_NO='"+id+"'";
        pst=conn.prepareStatement(sql);
        rs=pst.executeQuery();
       // if(rs.next()){
            jRecords.setModel(DbUtils.resultSetToTableModel(rs));
       // }
        
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void getDateDiff(){
        SimpleDateFormat myFormat=new SimpleDateFormat("yyyy-MM-dd");
    String date1=((JTextField)jToday1.getDateEditor().getUiComponent()).getText();
    String date2=((JTextField)jExpectedDate1.getDateEditor().getUiComponent()).getText();
    try{
         Date d1=myFormat.parse(date1);
        Date d2=myFormat.parse(date2);
        long d3=d1.getTime()-d2.getTime();
        long days=d3/(1000*60*60*24);
        String d=Long.toString(days);
           txt_days.setText(d);
        getDueCharges();
       }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void getDamage(){
        String condition=cbCondition.getSelectedItem().toString();
        int index=cbCondition.getSelectedIndex();
        String charge=txt_charge.getText();
        if(index==1){
            int val=0;
            String dis=Integer.toString(val);
            txt_charge.setText(dis);
        }else{
           if(index==0){
               JOptionPane.showMessageDialog(null, "SELECT CONDITION");
           } else{
              JOptionPane.showMessageDialog(null, "Input The Cost"); 
           }
        }
        
        
    }
    
    public void getDueCharges(){
        String days=txt_days.getText();
        String charge=txt_due.getText();
        try{
        String sql="SELECT * FROM charges_db WHERE REASON='DUE'";
        pst=conn.prepareStatement(sql);
        rs=pst.executeQuery();
        if(rs.next()){
            int val=rs.getInt("PRICE");
            int val2=Integer.parseInt(days);
            int val3=val*val2;
            String dis=Integer.toString(val3);
            txt_due.setText(dis);
        }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void getTotalCharges(){
         String due=txt_due.getText();
         String charge=txt_charge.getText();
         int val1=Integer.parseInt(due);
         int val2=Integer.parseInt(charge);
         int val3=val1+val2;
         String dis=Integer.toString(val3);
         txt_sum.setText(dis);
         if(val3==0){
             JOptionPane.showMessageDialog(null, "Clear the client");
             jButton8.setBackground(Color.GREEN);
         }else{
             JOptionPane.showMessageDialog(null, "Pay charges then clear");
         }
        
    }
    
    public void clearClient(){
        String sum=txt_sum.getText();
        String id=txt_ID.getText();
        String fleet=cbFleet1.getSelectedItem().toString();
        String date=((JTextField)jToday1.getDateEditor().getUiComponent()).getText();
        String state="CLEARED";
        String condition=cbCondition.getSelectedItem().toString();
        int index=cbCondition.getSelectedIndex();
        try{
        if(index==0){
            JOptionPane.showMessageDialog(null, "SELECT CONDITION");
        }else{
            if(sum.isEmpty()){
               JOptionPane.showMessageDialog(null, "Get Total charges to proceed."); 
               jButton6.setBackground(Color.RED);
            }else{
                int val=Integer.parseInt(sum);
                if(val==0){
                    String clear1="UPDATE issue_db SET RETURN_DATE='"+date+"',CONDITIONS='"+condition+"',STATE='"+state+"' WHERE ID_NO='"+id+"' AND FLEET_NO='"+fleet+"' AND STATE='ACTIVE' ";
                    pst=conn.prepareStatement(clear1);
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "Client Cleared.");
                    setAvailable();
                    displayRecords();
                }else{
                    String check="SELECT * FROM transaction_db WHERE ID_NO='"+id+"' AND DATE_PAID='"+date+"' AND PRICE='"+val+"' ";
                    pst=conn.prepareStatement(check);
                    rs=pst.executeQuery();
                    if(rs.next()){
                        String clear1="UPDATE issue_db SET RETURN_DATE='"+date+"',CONDITIONS='"+condition+"',STATE='"+state+"' WHERE ID_NO='"+id+"' AND FLEET_NO='"+fleet+"' AND STATE='ACTIVE' ";
                    pst=conn.prepareStatement(clear1);
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "Client Cleared.");
                    setAvailable();
                    displayRecords();
                    }else{
                        JOptionPane.showMessageDialog(null, "Clear the charges to proceed.");
                        jPanel11.setBackground(Color.RED);
                    }
                }
            }
        }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void refresh(){
        txt_ID.setText(null);
        ((JTextField)jToday.getDateEditor().getUiComponent()).setText(null);
        //cbFleet.getSelectedItem().toString();
        ((JTextField)jExpectedDate.getDateEditor().getUiComponent()).setText(null);
        txt_fName.setText(null);
        txt_lName.setText(null);
        txt_gender.setText(null);
        txt_area.setText(null);
        txt_reg.setText(null);
        txt_manufacturer.setText(null);
        txt_model.setText(null);
        txt_colour.setText(null);
        txt_tracker.setText(null);
       //cbFleet.setSelectedIndex(0);
        cbBank.setSelectedIndex(0);
       txt_cheque.setText(null);
        ((JTextField)jSlipDate.getDateEditor().getUiComponent()).setText(null);
        txt_charges.setText(null);
        txt_total.setText(null);
         //getFleet();
         currentDate();
    }
    
    public void setAvailable(){
        String state="AVAILABLE";
        String fleet=cbFleet1.getSelectedItem().toString();
        try{
        String sql="UPDATE available_db SET STATE='"+state+"' WHERE FLEET_NO='"+fleet+"'";
        pst=conn.prepareStatement(sql);
        pst.execute();
        
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

        jScrollPane2 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        txt_ID = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_fName = new javax.swing.JTextField();
        txt_lName = new javax.swing.JTextField();
        txt_area = new javax.swing.JTextField();
        txt_gender = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        cbFleet = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        txt_reg = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txt_manufacturer = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txt_model = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txt_colour = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txt_tracker = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txt_total = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_charges = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jToday = new com.toedter.calendar.JDateChooser();
        jExpectedDate = new com.toedter.calendar.JDateChooser();
        jLabel19 = new javax.swing.JLabel();
        time = new javax.swing.JLabel();
        cbDays = new javax.swing.JComboBox<>();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel17 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        cbBank = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        txt_cheque = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jSlipDate = new com.toedter.calendar.JDateChooser();
        jButton5 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jRecords = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        cbFleet1 = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        txt_reg1 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txt_manufacturer1 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        txt_model1 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txt_colour1 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        txt_tracker1 = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txt_charge = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jToday1 = new com.toedter.calendar.JDateChooser();
        jExpectedDate1 = new com.toedter.calendar.JDateChooser();
        jLabel34 = new javax.swing.JLabel();
        txt_days = new javax.swing.JTextField();
        cbCondition = new javax.swing.JComboBox<>();
        jLabel35 = new javax.swing.JLabel();
        txt_due = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        txt_sum = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        cbMode = new javax.swing.JComboBox<>();
        jLabel39 = new javax.swing.JLabel();
        jTodayCharge = new com.toedter.calendar.JDateChooser();
        jButton7 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();

        jScrollPane2.setViewportView(jEditorPane1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("APPLICATION PORTAL");

        jLabel2.setText("ID NO");

        jButton1.setText("GO");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Personal Informations", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Arial", 0, 10), new java.awt.Color(204, 204, 0))); // NOI18N

        jLabel3.setText("First Name:");

        jLabel4.setText("Last Name:");

        jLabel5.setText("Gender:");

        jLabel6.setText("Residential Area:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_fName, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                    .addComponent(txt_lName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_area)
                    .addComponent(txt_gender, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txt_gender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_area, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txt_fName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txt_lName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)))))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jButton2.setText("ISSUE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setText("CANCEL");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jButton2)
                .addGap(18, 18, 18)
                .addComponent(jButton4)
                .addContainerGap(80, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setText("FLEET NO:");

        cbFleet.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-select-" }));
        cbFleet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFleetActionPerformed(evt);
            }
        });

        jLabel8.setText("REG NO:");

        jLabel14.setForeground(new java.awt.Color(51, 51, 0));
        jLabel14.setText("Manufacturer:");

        jLabel15.setForeground(new java.awt.Color(51, 51, 0));
        jLabel15.setText("Model:");

        jLabel11.setForeground(new java.awt.Color(51, 51, 0));
        jLabel11.setText("Colour:");

        jLabel16.setText("Tracker Code:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbFleet, 0, 100, Short.MAX_VALUE)
                    .addComponent(txt_reg))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_tracker, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_manufacturer)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_colour, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                    .addComponent(txt_model))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(cbFleet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14)
                            .addComponent(txt_manufacturer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txt_reg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txt_model, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txt_colour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_tracker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setText("Days :");

        jLabel12.setText("Total charges:");

        jLabel9.setText("CHARGES PER DAY:");

        txt_charges.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_chargesActionPerformed(evt);
            }
        });

        jLabel18.setText("Todays Date:");

        jToday.setDateFormatString("yyyy-MM-dd");

        jExpectedDate.setDateFormatString("yyyy-MM-dd");

        jLabel19.setText("Expected Date:");

        time.setText("Time");

        cbDays.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30" }));
        cbDays.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbDaysActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbDays, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(txt_charges, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(81, 81, 81)
                                .addComponent(jToday, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jExpectedDate, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(time)))
                        .addGap(55, 55, 55))))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel12)
                            .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(txt_charges, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbDays, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel18))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jExpectedDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel19)
                                    .addComponent(time)))))
                    .addComponent(jToday, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel17.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(153, 153, 0));
        jLabel17.setText("Car Details");

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Payment Site", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 10), new java.awt.Color(204, 204, 0))); // NOI18N

        jLabel20.setText("BANK:");

        cbBank.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-select-", "CO-OPERATIVE", "EQUITY", "KCB" }));

        jLabel21.setText("CHEQUE NO:");

        txt_cheque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_chequeActionPerformed(evt);
            }
        });

        jLabel22.setText("DATE");

        jSlipDate.setDateFormatString(" yyyy-MM-dd");

        jButton5.setText("PAY");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbBank, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_cheque, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel22)
                .addGap(18, 18, 18)
                .addComponent(jSlipDate, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton5)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(cbBank, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21)
                            .addComponent(txt_cheque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22))
                        .addComponent(jSlipDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("LEND", jPanel5);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jRecords.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jRecords);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 543, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(114, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("CLIENTS RECORDS", jPanel6);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jLabel23.setText("FLEET NO:");

        cbFleet1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-select-" }));
        cbFleet1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFleet1ActionPerformed(evt);
            }
        });

        jLabel24.setText("REG NO:");

        jLabel25.setForeground(new java.awt.Color(51, 51, 0));
        jLabel25.setText("Manufacturer:");

        jLabel26.setForeground(new java.awt.Color(51, 51, 0));
        jLabel26.setText("Model:");

        jLabel27.setForeground(new java.awt.Color(51, 51, 0));
        jLabel27.setText("Colour:");

        jLabel28.setText("Tracker Code:");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbFleet1, 0, 100, Short.MAX_VALUE)
                    .addComponent(txt_reg1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_tracker1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_manufacturer1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_colour1, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                    .addComponent(txt_model1))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(cbFleet1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25)
                            .addComponent(txt_manufacturer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(txt_reg1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(txt_model1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(txt_colour1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_tracker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jLabel29.setText("Due Days :");

        jLabel30.setText("Damage Charges:");

        jLabel32.setText("Car Condition:");

        jLabel33.setText("Todays Date:");

        jToday1.setDateFormatString("yyyy-MM-dd");

        jExpectedDate1.setDateFormatString("yyyy-MM-dd");

        jLabel34.setText("Expected Date:");

        cbCondition.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-select-", "GOOD", "DAMAGED" }));
        cbCondition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbConditionActionPerformed(evt);
            }
        });

        jLabel35.setText("Due Charges:");

        jLabel36.setText("Total charges:");

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Cabin/[012551].png"))); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel31))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel33)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jToday1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(jLabel36)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txt_sum))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                                    .addGap(8, 8, 8)
                                    .addComponent(jLabel32)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(cbCondition, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addComponent(jLabel34)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jExpectedDate1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addComponent(jLabel30)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txt_charge, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel35)
                                    .addComponent(jLabel29)))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(10, 10, 10)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_due, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                    .addComponent(txt_days))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33)
                    .addComponent(jToday1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34)
                    .addComponent(jExpectedDate1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel29)
                        .addComponent(txt_days, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(txt_charge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35)
                            .addComponent(txt_due, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1))
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel32)
                        .addComponent(cbCondition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel36)
                        .addComponent(txt_sum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Payment Site", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 10), new java.awt.Color(204, 204, 0))); // NOI18N

        jLabel37.setText("MODE:");

        cbMode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-select-", "M-PESA", "CASH" }));

        jLabel39.setText("DATE");

        jTodayCharge.setDateFormatString(" yyyy-MM-dd");

        jButton7.setText("PAY");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton10.setText("RECEIPT");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbMode, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTodayCharge, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton10)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton7)
                        .addComponent(jButton10))
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel37)
                        .addComponent(cbMode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel39))
                    .addComponent(jTodayCharge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Cabin/save.png"))); // NOI18N
        jButton8.setText("CLEAR");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setText("RECEIPT");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jButton8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton9)
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(137, 137, 137)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("CLEARANCE", jPanel8);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(163, 163, 163)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txt_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        getClientDetails();
        displayRecords();
        getActiveFleet();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        issue();
        displayRecords();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void cbFleetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFleetActionPerformed
        getCarDetails();
    }//GEN-LAST:event_cbFleetActionPerformed

    private void txt_chargesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_chargesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_chargesActionPerformed

    private void cbDaysActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbDaysActionPerformed
        displayTotal();
    }//GEN-LAST:event_cbDaysActionPerformed

    private void txt_chequeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_chequeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_chequeActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        pay();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void cbFleet1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFleet1ActionPerformed
        getCarDetails1();
    }//GEN-LAST:event_cbFleet1ActionPerformed

    private void cbConditionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbConditionActionPerformed
        getDamage();
    }//GEN-LAST:event_cbConditionActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        getTotalCharges();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        payExtra();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        clearClient();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        //conn=ClassConnect.connectCabin();
         //getFleet();
         //currentDate();
         refresh();
    }//GEN-LAST:event_jButton4ActionPerformed

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
            java.util.logging.Logger.getLogger(IssuePortal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IssuePortal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IssuePortal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IssuePortal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IssuePortal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbBank;
    private javax.swing.JComboBox<String> cbCondition;
    public static javax.swing.JComboBox<String> cbDays;
    public static javax.swing.JComboBox<String> cbFleet;
    public static javax.swing.JComboBox<String> cbFleet1;
    private javax.swing.JComboBox<String> cbMode;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JEditorPane jEditorPane1;
    public static com.toedter.calendar.JDateChooser jExpectedDate;
    public static com.toedter.calendar.JDateChooser jExpectedDate1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    public static javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    public static javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    public static javax.swing.JPanel jPanel9;
    private javax.swing.JTable jRecords;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private com.toedter.calendar.JDateChooser jSlipDate;
    private javax.swing.JTabbedPane jTabbedPane1;
    private com.toedter.calendar.JDateChooser jToday;
    private com.toedter.calendar.JDateChooser jToday1;
    private com.toedter.calendar.JDateChooser jTodayCharge;
    private javax.swing.JLabel time;
    public static javax.swing.JTextField txt_ID;
    public static javax.swing.JTextField txt_area;
    public static javax.swing.JTextField txt_charge;
    public static javax.swing.JTextField txt_charges;
    private javax.swing.JTextField txt_cheque;
    public static javax.swing.JTextField txt_colour;
    public static javax.swing.JTextField txt_colour1;
    private javax.swing.JTextField txt_days;
    private javax.swing.JTextField txt_due;
    public static javax.swing.JTextField txt_fName;
    public static javax.swing.JTextField txt_gender;
    public static javax.swing.JTextField txt_lName;
    public static javax.swing.JTextField txt_manufacturer;
    public static javax.swing.JTextField txt_manufacturer1;
    public static javax.swing.JTextField txt_model;
    public static javax.swing.JTextField txt_model1;
    public static javax.swing.JTextField txt_reg;
    public static javax.swing.JTextField txt_reg1;
    private javax.swing.JTextField txt_sum;
    public static javax.swing.JTextField txt_total;
    public static javax.swing.JTextField txt_tracker;
    public static javax.swing.JTextField txt_tracker1;
    // End of variables declaration//GEN-END:variables
}
