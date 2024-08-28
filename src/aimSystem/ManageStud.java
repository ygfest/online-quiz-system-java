
package aimSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author stefa
 */
public class ManageStud extends javax.swing.JFrame {
    private String firstn;
    private String lastn;
    

    public ManageStud(String firstn,String lastn){ 
        initComponents();
        Connect();
        
        this.firstn = firstn;
        this.lastn = lastn;
        Fetch(firstn,lastn);
        
        
    }
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
  
    
    public void Connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con =DriverManager.getConnection("jdbc:mysql://localhost/online_quizz","root","");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SignUp.class.getName()).log(Level.SEVERE, null, ex);
        }catch (SQLException ex) {
            Logger.getLogger(SignUp.class.getName()).log(Level.SEVERE, null, ex);    
        }
    }
    
    
   public void Fetch(String firstn, String lastn){
    try {
        pst = con.prepareStatement("SELECT sNumber, firstname, lastname, sex, sProg, sSec, status FROM reg_student WHERE firstname=? AND lastname=?");
        pst.setString(1, firstn);
        pst.setString(2, lastn);
       
        rs = pst.executeQuery();

        // Assuming you have initialized your JTextField components named rsubject, rtitle, rtl, rpp, and rfac

        // Assuming the query will return only one row (the latest quiz_list entry)
        if (rs.next()) {
            // Retrieve data from the resultSet
            String rSNumber = rs.getString("sNumber");
            String rFirstname = rs.getString("firstname");
            String rLastname = rs.getString("lastname");
            String rSex = rs.getString("sex");
            String rProgr = rs.getString("sProg");
            String rSect = rs.getString("sSec");
             String rStat = rs.getString("status");

            // Display the retrieved data in the JTextFields
            rNum.setText(rSNumber);
            rFN.setText(rFirstname);
            rLN.setText(rLastname);
            rS.setText(rSex);
            rProg.setText(rProgr);
            rSec.setText(rSect);
            rStatus.setText(rStat);
        }

        // Close the resources
        rs.close();
    } catch (SQLException e) {
        // Handle any exceptions that might occur while executing the query
        e.printStackTrace();
    }
}
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addQuizPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        rProg = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        rSec = new javax.swing.JTextField();
        rStatus = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        rNum = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        rFN = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        rLN = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        rS = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        addQuizPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Manrope ExtraBold", 0, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Manage Student Record");

        jLabel2.setFont(new java.awt.Font("Manrope ExtraBold", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Program");

        jLabel4.setFont(new java.awt.Font("Manrope ExtraBold", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Section");

        jLabel5.setFont(new java.awt.Font("Manrope ExtraBold", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Status");

        rProg.setBackground(new java.awt.Color(255, 255, 255));
        rProg.setFont(new java.awt.Font("Manrope", 0, 14)); // NOI18N
        rProg.setForeground(new java.awt.Color(51, 51, 51));
        rProg.setActionCommand("<Not Set>");
        rProg.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));

        jSeparator1.setForeground(new java.awt.Color(102, 102, 102));

        rSec.setBackground(new java.awt.Color(255, 255, 255));
        rSec.setFont(new java.awt.Font("Manrope", 0, 14)); // NOI18N
        rSec.setForeground(new java.awt.Color(51, 51, 51));
        rSec.setActionCommand("<Not Set>");
        rSec.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));

        rStatus.setBackground(new java.awt.Color(255, 255, 255));
        rStatus.setFont(new java.awt.Font("Manrope", 0, 14)); // NOI18N
        rStatus.setForeground(new java.awt.Color(51, 51, 51));
        rStatus.setActionCommand("<Not Set>");
        rStatus.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));

        jButton2.setBackground(new java.awt.Color(254, 211, 44));
        jButton2.setFont(new java.awt.Font("Manrope ExtraBold", 0, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(51, 51, 51));
        jButton2.setText("Update this Student");
        jButton2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(254, 211, 44), 3, true));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Manrope ExtraBold", 1, 30)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("<");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Manrope ExtraBold", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Student Number");

        rNum.setBackground(new java.awt.Color(255, 255, 255));
        rNum.setFont(new java.awt.Font("Manrope", 0, 14)); // NOI18N
        rNum.setForeground(new java.awt.Color(51, 51, 51));
        rNum.setActionCommand("<Not Set>");
        rNum.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));

        jLabel8.setFont(new java.awt.Font("Manrope ExtraBold", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("First Name");

        rFN.setBackground(new java.awt.Color(255, 255, 255));
        rFN.setFont(new java.awt.Font("Manrope", 0, 14)); // NOI18N
        rFN.setForeground(new java.awt.Color(51, 51, 51));
        rFN.setActionCommand("<Not Set>");
        rFN.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));

        jLabel9.setFont(new java.awt.Font("Manrope ExtraBold", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("Last Name");

        rLN.setBackground(new java.awt.Color(255, 255, 255));
        rLN.setFont(new java.awt.Font("Manrope", 0, 14)); // NOI18N
        rLN.setForeground(new java.awt.Color(51, 51, 51));
        rLN.setActionCommand("<Not Set>");
        rLN.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));

        jLabel10.setFont(new java.awt.Font("Manrope ExtraBold", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("Sex");

        rS.setBackground(new java.awt.Color(255, 255, 255));
        rS.setFont(new java.awt.Font("Manrope", 0, 14)); // NOI18N
        rS.setForeground(new java.awt.Color(51, 51, 51));
        rS.setActionCommand("<Not Set>");
        rS.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));

        jButton3.setBackground(new java.awt.Color(255, 102, 102));
        jButton3.setFont(new java.awt.Font("Manrope ExtraBold", 0, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(51, 51, 51));
        jButton3.setText("Delete this Student");
        jButton3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout addQuizPanelLayout = new javax.swing.GroupLayout(addQuizPanel);
        addQuizPanel.setLayout(addQuizPanelLayout);
        addQuizPanelLayout.setHorizontalGroup(
            addQuizPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(addQuizPanelLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel6)
                .addGap(183, 183, 183)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addQuizPanelLayout.createSequentialGroup()
                .addContainerGap(184, Short.MAX_VALUE)
                .addGroup(addQuizPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(rNum, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(addQuizPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel7)
                        .addComponent(jLabel8)
                        .addComponent(jLabel2)
                        .addComponent(jLabel4)
                        .addComponent(jLabel5)
                        .addComponent(rStatus)
                        .addComponent(rSec)
                        .addComponent(rProg)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rFN, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)
                        .addComponent(jLabel10)
                        .addComponent(rS, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(rLN, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(166, 166, 166))
        );
        addQuizPanelLayout.setVerticalGroup(
            addQuizPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addQuizPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(addQuizPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rNum, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rFN, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rLN, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rS, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rProg, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rSec, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        getContentPane().add(addQuizPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 690, 650));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        dispose();
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
                                      
    try {
        // Get the data from the JTextFields
        String rSNumber = rNum.getText();
        String rFirst = rFN.getText();
        String rLast = rLN.getText();
        String rSex = rS.getText();
        String rProgram = rProg.getText();
        String rSect = rSec.getText();
        String rStatuss = rStatus.getText();

        // Prepare the SQL update statement for the reg_student table
        String sql = "UPDATE reg_student SET sNumber=?, firstname=?, lastname=?, sex=?, sProg=?, sSec=?, status=? WHERE firstname=? AND lastname=?";
        PreparedStatement updateRegStudentPst = con.prepareStatement(sql);
        updateRegStudentPst.setString(1, rSNumber);
        updateRegStudentPst.setString(2, rFirst);
        updateRegStudentPst.setString(3, rLast);
        updateRegStudentPst.setString(4, rSex);
        updateRegStudentPst.setString(5, rProgram);
        updateRegStudentPst.setString(6, rSect);
        updateRegStudentPst.setString(7, rStatuss);
        updateRegStudentPst.setString(8, firstn); // Filter by firstname
        updateRegStudentPst.setString(9, lastn);  // Filter by lastname

        // Execute the update for reg_student table
        int rowsAffected = updateRegStudentPst.executeUpdate();

        if (rowsAffected > 0) {
            // Successful update
            System.out.println("reg_student table updated successfully!");
        } else {
            // Update failed
            System.out.println("Failed to update reg_student table.");
        }
updateRegStudentPst.close();

        // Clear the JTextFields after successful update
        rNum.setText("");
        rFN.setText("");
        rLN.setText("");
        rS.setText("");
        rProg.setText("");
        rSec.setText("");
        rStatus.setText("");

        // Display a pop-up message for successful update
        JOptionPane.showMessageDialog(this, "Student record updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        // Now you can call the Fetch method to refresh the data in the GUI
        String userName = null;
        DashBoard db = new DashBoard(userName);
        db.Fetch(userName);
    } catch (SQLException e) {
        // Handle any exceptions that might occur while executing the updates
        e.printStackTrace();
    
}
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
                                       
    try {
        // Get the data from the JTextFields
        String rFirst = rFN.getText();
        String rLast = rLN.getText();

        // Prepare the SQL delete statement for the reg_student table
        String sql = "DELETE FROM reg_student WHERE firstname=? AND lastname=?";
        PreparedStatement deleteRegStudentPst = con.prepareStatement(sql);
        deleteRegStudentPst.setString(1, rFirst); // Filter by firstname
        deleteRegStudentPst.setString(2, rLast);  // Filter by lastname

        // Execute the delete for reg_student table
        int rowsAffected = deleteRegStudentPst.executeUpdate();

        if (rowsAffected > 0) {
            // Successful delete
            System.out.println("Student record deleted successfully!");
        } else {
            // Delete failed
            System.out.println("Failed to delete student record.");
        }
        deleteRegStudentPst.close();

        // Clear the JTextFields after successful delete
        rNum.setText("");
        rFN.setText("");
        rLN.setText("");
        rS.setText("");
        rProg.setText("");
        rSec.setText("");
        rStatus.setText("");

        // Display a pop-up message for successful delete
        JOptionPane.showMessageDialog(this, "Student record deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        // Now you can call the Fetch method to refresh the data in the GUI
        String userName = null;
        DashBoard db = new DashBoard(userName);
        db.Fetch(userName);
    } catch (SQLException e) {
        // Handle any exceptions that might occur while executing the delete
        e.printStackTrace();
    }


    }//GEN-LAST:event_jButton3ActionPerformed

    
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
            java.util.logging.Logger.getLogger(ManageStud.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManageStud.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManageStud.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManageStud.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPanel addQuizPanel;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField rFN;
    private javax.swing.JTextField rLN;
    private javax.swing.JTextField rNum;
    private javax.swing.JTextField rProg;
    private javax.swing.JTextField rS;
    private javax.swing.JTextField rSec;
    private javax.swing.JTextField rStatus;
    // End of variables declaration//GEN-END:variables
}
