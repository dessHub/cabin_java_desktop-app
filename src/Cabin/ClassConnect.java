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
import java.sql.*;
import javax.swing.*;
public class ClassConnect {
    Connection conn=null;
    public static Connection connectCabin(){
        try{
           Class.forName("com.mysql.jdbc.Driver");
           Connection conn=DriverManager.getConnection("jdbc:mysql://localhost/cabin_database","root","");
           return conn;
           
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        return null;
    }
}
