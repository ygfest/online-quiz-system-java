
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
public class ManageQuiz extends javax.swing.JFrame {
    private String subjectz;
    private String titlez;
    

    public ManageQuiz(String subjectz, String titlez){ 
        initComponents();
        Connect();
        
        Fetch(subjectz,titlez);
        this.titlez = titlez;
        this.subjectz = subjectz;
        
        
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
    
    
   public void Fetch(String subjectz, String titlez){
    try {
        pst = con.prepareStatement("SELECT subject, title, timeLimit, pointsperitem, faculty FROM quiz_list WHERE subject=? AND title=?");
        pst.setString(1, subjectz);
        pst.setString(2, titlez);
       
        rs = pst.executeQuery();

        // Assuming you have initialized your JTextField components named rsubject, rtitle, rtl, rpp, and rfac

        // Assuming the query will return only one row (the latest quiz_list entry)
        if (rs.next()) {
            // Retrieve data from the resultSet
            String rSubject = rs.getString("subject");
            String rTitle = rs.getString("title");
            int rTimeLimit = rs.getInt("timeLimit");
            String rPoints = rs.getString("pointsperitem");
            String faculty = rs.getString("faculty");

            // Display the retrieved data in the JTextFields
            rsubject.setText(rSubject);
            rtitle.setText(rTitle);
            rtl.setText(String.valueOf(rTimeLimit));
            rpp.setText(rPoints);
            rfac.setText(faculty);
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
        rtl = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        rpp = new javax.swing.JTextField();
        rfac = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        rsubject = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        rtitle = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        addQuizPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Manrope ExtraBold", 0, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Manage Quiz");

        jLabel2.setFont(new java.awt.Font("Manrope ExtraBold", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Time Limit(in minutes)");

        jLabel4.setFont(new java.awt.Font("Manrope ExtraBold", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Points per item");

        jLabel5.setFont(new java.awt.Font("Manrope ExtraBold", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Faculty");

        rtl.setBackground(new java.awt.Color(255, 255, 255));
        rtl.setFont(new java.awt.Font("Manrope", 0, 14)); // NOI18N
        rtl.setForeground(new java.awt.Color(51, 51, 51));
        rtl.setActionCommand("<Not Set>");
        rtl.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));

        jSeparator1.setForeground(new java.awt.Color(102, 102, 102));

        rpp.setBackground(new java.awt.Color(255, 255, 255));
        rpp.setFont(new java.awt.Font("Manrope", 0, 14)); // NOI18N
        rpp.setForeground(new java.awt.Color(51, 51, 51));
        rpp.setActionCommand("<Not Set>");
        rpp.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));

        rfac.setBackground(new java.awt.Color(255, 255, 255));
        rfac.setFont(new java.awt.Font("Manrope", 0, 14)); // NOI18N
        rfac.setForeground(new java.awt.Color(51, 51, 51));
        rfac.setActionCommand("<Not Set>");
        rfac.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));

        jButton2.setBackground(new java.awt.Color(254, 211, 44));
        jButton2.setFont(new java.awt.Font("Manrope ExtraBold", 0, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(51, 51, 51));
        jButton2.setText("Update this Quiz");
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
        jLabel7.setText("Subject");

        rsubject.setBackground(new java.awt.Color(255, 255, 255));
        rsubject.setFont(new java.awt.Font("Manrope", 0, 14)); // NOI18N
        rsubject.setForeground(new java.awt.Color(51, 51, 51));
        rsubject.setActionCommand("<Not Set>");
        rsubject.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));

        jLabel8.setFont(new java.awt.Font("Manrope ExtraBold", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("Title");

        rtitle.setBackground(new java.awt.Color(255, 255, 255));
        rtitle.setFont(new java.awt.Font("Manrope", 0, 14)); // NOI18N
        rtitle.setForeground(new java.awt.Color(51, 51, 51));
        rtitle.setActionCommand("<Not Set>");
        rtitle.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));

        javax.swing.GroupLayout addQuizPanelLayout = new javax.swing.GroupLayout(addQuizPanel);
        addQuizPanel.setLayout(addQuizPanelLayout);
        addQuizPanelLayout.setHorizontalGroup(
            addQuizPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(addQuizPanelLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel6)
                .addGap(221, 221, 221)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addQuizPanelLayout.createSequentialGroup()
                .addContainerGap(184, Short.MAX_VALUE)
                .addGroup(addQuizPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(rsubject, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(addQuizPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel7)
                        .addComponent(jLabel8)
                        .addComponent(jLabel2)
                        .addComponent(jLabel4)
                        .addComponent(jLabel5)
                        .addComponent(rfac)
                        .addComponent(rpp)
                        .addComponent(rtl)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rtitle, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addComponent(rsubject, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rtitle, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rtl, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(rpp, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(rfac, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
        );

        getContentPane().add(addQuizPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 690, 590));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        dispose();
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
                                    
    try {
        // Get the data from the JTextFields
        String rSubject = rsubject.getText();
        String rTitle = rtitle.getText();
        int rTimeLimit = Integer.parseInt(rtl.getText());
        String rPoints = rpp.getText();
        String faculty = rfac.getText();

        // Prepare the SQL update statement for the quiz_list table
        String sqlUpdateQuizList = "UPDATE quiz_list SET timeLimit=?, pointsperitem=?, faculty=?, subject=?, title=? WHERE subject=? AND title=?";
        PreparedStatement updateQuizListPst = con.prepareStatement(sqlUpdateQuizList);
        updateQuizListPst.setInt(1, rTimeLimit);
        updateQuizListPst.setString(2, rPoints);
        updateQuizListPst.setString(3, faculty);
        updateQuizListPst.setString(4, rSubject);
        updateQuizListPst.setString(5, rTitle);
        updateQuizListPst.setString(6, subjectz); // Filter by subjectz
        updateQuizListPst.setString(7, titlez);   // Filter by titlez

        // Execute the update for quiz_list table
        int quizListRowsAffected = updateQuizListPst.executeUpdate();
        updateQuizListPst.close();

        // Prepare the SQL update statement for the questions table
        String sqlUpdateQuestions = "UPDATE questions SET subject=?, quizTitle=?, timeLimit=? WHERE subject=? AND quizTitle=?";
        PreparedStatement updateQuestionsPst = con.prepareStatement(sqlUpdateQuestions);
        updateQuestionsPst.setString(1, rSubject);
        updateQuestionsPst.setString(2, rTitle);
        updateQuestionsPst.setInt(3, rTimeLimit);
        updateQuestionsPst.setString(4, subjectz); // Filter by subjectz
        updateQuestionsPst.setString(5, titlez);   // Filter by titlez

        // Execute the update for questions table
        int questionsRowsAffected = updateQuestionsPst.executeUpdate();
        updateQuestionsPst.close();

        if (quizListRowsAffected > 0 && questionsRowsAffected > 0) {
            // Successful updates
            System.out.println("Quiz List and Questions table updated successfully!");
        } else {
            // Updates failed
            System.out.println("Failed to update Quiz List and Questions table.");
        }

        // Close the resources
        Questions_Form1 qf1 = new Questions_Form1(rSubject, rTitle); // Pass the updated subject and title to the Questions_Form1 constructor
        qf1.show();
        dispose();
        String userName = null;
        DashBoard db = new DashBoard(userName);
        db.Fetch(userName);
    } catch (SQLException e) {
        // Handle any exceptions that might occur while executing the updates
        e.printStackTrace();
    }



    }//GEN-LAST:event_jButton2ActionPerformed

    
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
            java.util.logging.Logger.getLogger(ManageQuiz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManageQuiz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManageQuiz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManageQuiz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField rfac;
    private javax.swing.JTextField rpp;
    private javax.swing.JTextField rsubject;
    private javax.swing.JTextField rtitle;
    private javax.swing.JTextField rtl;
    // End of variables declaration//GEN-END:variables
}
