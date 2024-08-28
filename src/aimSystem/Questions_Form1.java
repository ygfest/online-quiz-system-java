/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package aimSystem;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;


/**
 *
 * @author stefa
 */
public class Questions_Form1 extends javax.swing.JFrame {
    private String userName;
    private String subjectz;
    private String titlez;
   private ArrayList<String> questionList = new ArrayList<>();
    private ArrayList<String> choice1List = new ArrayList<>();
    private ArrayList<String> choice2List = new ArrayList<>();
    private ArrayList<String> choice3List = new ArrayList<>();
    private ArrayList<String> choice4List = new ArrayList<>();
    private ArrayList<String> answerList = new ArrayList<>();
    private ArrayList<Integer> idList = new ArrayList<>();
    private int currentQuestionIndex = 0;
    
    
    
    ButtonGroup bg = new ButtonGroup();
    /**
     * Creates new form Quiz_Form
     */
   public Questions_Form1(String subjectz, String titlez) {
    initComponents();
    Connect();
    this.titlez = titlez;
    this.subjectz = subjectz;
    Fetch(subjectz, titlez);
}
    
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    
    public void Connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con =DriverManager.getConnection("jdbc:mysql://localhost/online_quizz","root","");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Questions_Form1.class.getName()).log(Level.SEVERE, null, ex);
        }catch (SQLException ex) {
            Logger.getLogger(Questions_Form1.class.getName()).log(Level.SEVERE, null, ex);    
        }
    }
  public void Fetch(String subjectz, String titlez) {
    try {
        // Prepare the SQL statement
        String sql = "SELECT no, questions, choice1, choice2, choice3, choice4, answer FROM questions WHERE subject = ? AND quizTitle = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, subjectz);
        pst.setString(2, titlez);

        ResultSet rs = pst.executeQuery();

        // Clear the lists to store new data
        questionList.clear();
        choice1List.clear();
        choice2List.clear();
        choice3List.clear();
        choice4List.clear();
        answerList.clear();
        idList.clear(); // Clear the idList

        // Retrieve all available questions for the current subject and quiz title and store them in the lists
        while (rs.next()) {
            idList.add(rs.getInt("no")); // Store the id (no) value
            questionList.add(rs.getString("questions"));
            choice1List.add(rs.getString("choice1"));
            choice2List.add(rs.getString("choice2"));
            choice3List.add(rs.getString("choice3"));
            choice4List.add(rs.getString("choice4"));
            answerList.add(rs.getString("answer"));
        }

        // Display the first question
        currentQuestionIndex = 0;
        if (!questionList.isEmpty()) {
            displayCurrentQuestion();
        } else {
            // If there are no questions for the current subject and quiz title, show a message or perform any other necessary actions
        }

        // Close the resources
        rs.close();
        pst.close();
    } catch (SQLException e) {
        // Handle any exceptions that might occur while executing the query
        e.printStackTrace();
    }
}

    private void displayCurrentQuestion() {
        // Retrieve the data for the current question
        String questionText = questionList.get(currentQuestionIndex);
        String choice1Text = choice1List.get(currentQuestionIndex);
        String choice2Text = choice2List.get(currentQuestionIndex);
        String choice3Text = choice3List.get(currentQuestionIndex);
        String choice4Text = choice4List.get(currentQuestionIndex);
        String answerText = answerList.get(currentQuestionIndex);

        // Display the retrieved data in the JTextFields
        question.setText(questionText);
        choice_1.setText(choice1Text);
        choice_2.setText(choice2Text);
        choice_3.setText(choice3Text);
        choice_4.setText(choice4Text);
        correct_answer.setText(answerText);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        back = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        question = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        choice_2 = new javax.swing.JTextField();
        choice_3 = new javax.swing.JTextField();
        choice_1 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        choice_4 = new javax.swing.JTextField();
        correct_answer = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(248, 248, 248));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        back.setBackground(new java.awt.Color(253, 225, 8));
        back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backMouseClicked(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(253, 225, 8));
        jLabel1.setFont(new java.awt.Font("Manrope ExtraBold", 0, 18)); // NOI18N
        jLabel1.setText(" <");

        javax.swing.GroupLayout backLayout = new javax.swing.GroupLayout(back);
        back.setLayout(backLayout);
        backLayout.setHorizontalGroup(
            backLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backLayout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(23, 23, 23))
        );
        backLayout.setVerticalGroup(
            backLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        question.setBackground(new java.awt.Color(255, 255, 255));
        question.setFont(new java.awt.Font("Manrope", 0, 14)); // NOI18N
        question.setForeground(new java.awt.Color(51, 51, 51));
        question.setActionCommand("<Not Set>");
        question.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel12.setFont(new java.awt.Font("Manrope ExtraBold", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setText("Question");

        jLabel18.setFont(new java.awt.Font("Manrope ExtraBold", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(51, 51, 51));
        jLabel18.setText("Choice 1");

        jLabel19.setFont(new java.awt.Font("Manrope ExtraBold", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(51, 51, 51));
        jLabel19.setText("Choice 2");

        jLabel22.setFont(new java.awt.Font("Manrope ExtraBold", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(51, 51, 51));
        jLabel22.setText("Choice 3");

        jButton2.setBackground(new java.awt.Color(253, 225, 8));
        jButton2.setFont(new java.awt.Font("Manrope ExtraBold", 0, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(51, 51, 51));
        jButton2.setText("Update question");
        jButton2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(254, 211, 44), 3, true));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        choice_2.setBackground(new java.awt.Color(255, 255, 255));
        choice_2.setFont(new java.awt.Font("Manrope", 0, 14)); // NOI18N
        choice_2.setForeground(new java.awt.Color(51, 51, 51));
        choice_2.setActionCommand("<Not Set>");
        choice_2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        choice_3.setBackground(new java.awt.Color(255, 255, 255));
        choice_3.setFont(new java.awt.Font("Manrope", 0, 14)); // NOI18N
        choice_3.setForeground(new java.awt.Color(51, 51, 51));
        choice_3.setActionCommand("<Not Set>");
        choice_3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        choice_1.setBackground(new java.awt.Color(255, 255, 255));
        choice_1.setFont(new java.awt.Font("Manrope", 0, 14)); // NOI18N
        choice_1.setForeground(new java.awt.Color(51, 51, 51));
        choice_1.setActionCommand("<Not Set>");
        choice_1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        choice_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                choice_1ActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Manrope ExtraBold", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(51, 51, 51));
        jLabel20.setText("Choice 4");

        choice_4.setBackground(new java.awt.Color(255, 255, 255));
        choice_4.setFont(new java.awt.Font("Manrope", 0, 14)); // NOI18N
        choice_4.setForeground(new java.awt.Color(51, 51, 51));
        choice_4.setActionCommand("<Not Set>");
        choice_4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        correct_answer.setBackground(new java.awt.Color(255, 255, 255));
        correct_answer.setFont(new java.awt.Font("Manrope", 0, 14)); // NOI18N
        correct_answer.setForeground(new java.awt.Color(51, 51, 51));
        correct_answer.setActionCommand("<Not Set>");
        correct_answer.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel23.setFont(new java.awt.Font("Manrope ExtraBold", 0, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(51, 51, 51));
        jLabel23.setText("Correct Answer");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(back, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(57, 57, 57)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel19)
                                    .addComponent(choice_2, javax.swing.GroupLayout.PREFERRED_SIZE, 547, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(choice_1, javax.swing.GroupLayout.PREFERRED_SIZE, 547, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18)
                            .addComponent(question, javax.swing.GroupLayout.PREFERRED_SIZE, 547, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(choice_3, javax.swing.GroupLayout.PREFERRED_SIZE, 547, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel20)
                                    .addComponent(choice_4, javax.swing.GroupLayout.PREFERRED_SIZE, 547, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel23)
                                    .addComponent(correct_answer, javax.swing.GroupLayout.PREFERRED_SIZE, 547, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(61, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(back, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel12)
                .addGap(5, 5, 5)
                .addComponent(question, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(choice_1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel19)
                .addGap(2, 2, 2)
                .addComponent(choice_2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(choice_3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel20)
                .addGap(2, 2, 2)
                .addComponent(choice_4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(correct_answer, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void backMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backMouseClicked

        
        dispose();
    }//GEN-LAST:event_backMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       String question_ = question.getText();
        String choice1 = choice_1.getText();
        String choice2 = choice_2.getText();
        String choice3 = choice_3.getText();
        String choice4 = choice_4.getText();
        String correctAnswer = correct_answer.getText();

        if (!question_.isEmpty() && !choice1.isEmpty() && !choice2.isEmpty() && !choice3.isEmpty() && !choice4.isEmpty() && !correctAnswer.isEmpty()) {
            // Update the current question
            updateCurrentQuestion(question_, choice1, choice2, choice3, choice4, correctAnswer);

            // Clear input fields after successful update
            question.setText("");
            choice_1.setText("");
            choice_2.setText("");
            choice_3.setText("");
            choice_4.setText("");
            correct_answer.setText("");

            // Fetch the next question after successful update
            currentQuestionIndex++;
            if (currentQuestionIndex < questionList.size()) {
                displayCurrentQuestion();
            } else {
                // If there are no more questions, reset the counter to show the first question again
                currentQuestionIndex = 0;
                JOptionPane.showMessageDialog(this, "No more questions available.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
// Add a new method to update the current question
private void updateCurrentQuestion(String question_, String choice1, String choice2, String choice3, String choice4, String correctAnswer) {
        try {
            // Prepare the SQL update statement
            String updateSql = "UPDATE questions SET questions=?, choice1=?, choice2=?, choice3=?, choice4=?, answer=? WHERE no=?";
            PreparedStatement updateStatement = con.prepareStatement(updateSql);
            updateStatement.setString(1, question_);
            updateStatement.setString(2, choice1);
            updateStatement.setString(3, choice2);
            updateStatement.setString(4, choice3);
            updateStatement.setString(5, choice4);
            updateStatement.setString(6, correctAnswer);
            updateStatement.setInt(7, idList.get(currentQuestionIndex)); // Use the id (no) from idList for the current question

            // Execute the update statement
            updateStatement.executeUpdate();

            // Close the statement
            updateStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(Questions_Form1.class.getName()).log(Level.SEVERE, null, ex);
        }
    


    }//GEN-LAST:event_jButton2ActionPerformed

    private void choice_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_choice_1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_choice_1ActionPerformed

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
            java.util.logging.Logger.getLogger(Questions_Form1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Questions_Form1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Questions_Form1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Questions_Form1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private javax.swing.JPanel back;
    private javax.swing.JTextField choice_1;
    private javax.swing.JTextField choice_2;
    private javax.swing.JTextField choice_3;
    private javax.swing.JTextField choice_4;
    private javax.swing.JTextField correct_answer;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField question;
    // End of variables declaration//GEN-END:variables
}
