/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package aimSystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;


/**
 *
 * @author stefa
 * /*PLEASE DON'T REMOVE CREDITS SECTION
 *                              -GRP 4

 */
public class Quiz_Form extends javax.swing.JFrame {
    private String userName;
    private String subjectz;
    private String titlez;
    private int questionCounter = 0;
    private int correct = 0;
    private String selectedAnswer;
private Timer timer;
private int timeLimitInMinutes = 1; // Set your desired time limit here (in minutes)
private JLabel timerLabel;


    
    ButtonGroup bg = new ButtonGroup();
    /**
     * Creates new form Quiz_Form
     */
    public Quiz_Form(String userName,String subjectz, String titlez) {
        initComponents();
        Connect();
        Fetch(userName, subjectz, titlez);
        this.userName=userName;
        this.titlez = titlez;
        this.subjectz = subjectz;
        bg.add(choice1);
        bg.add(choice2);
        bg.add(choice3);
        bg.add(choice4);
         startTimer();
    }
    
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    
    public void Connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con =DriverManager.getConnection("jdbc:mysql://localhost/online_quizz","root","");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Quiz_Form.class.getName()).log(Level.SEVERE, null, ex);
        }catch (SQLException ex) {
            Logger.getLogger(Quiz_Form.class.getName()).log(Level.SEVERE, null, ex);    
        }
    }
    
     
public void Fetch(String userName, String subjectz, String titlez) {
    try {
        pst = con.prepareStatement("SELECT * FROM questions WHERE subject = ? AND quizTitle = ? AND quizTitle IN (SELECT title FROM quiz_list WHERE username = ? AND completion_status = 'Not Yet Taken')");
        pst.setString(1, subjectz);
        pst.setString(2, titlez);
        pst.setString(3, userName);
        rs = pst.executeQuery();

        // Move to the next question based on the question counter
        for (int i = 0; i < questionCounter; i++) {
            if (!rs.next()) {
                // Handle the case when there are no more questions
                JOptionPane.showMessageDialog(null, "No more questions available.");
                throw new IllegalStateException("No more questions available.");
            }
        }

        // Retrieve the current question, choices, and answer
        if (rs.next()) {
            String question = rs.getString("questions");
            String choice_1 = rs.getString("choice1");
            String choice_2 = rs.getString("choice2");
            String choice_3 = rs.getString("choice3");
            String choice_4 = rs.getString("choice4");
            String answer = rs.getString("answer");
            int timeLimitFromDB = rs.getInt("timeLimit"); // Assuming the timeLimit column is stored as an integer in the database.
            
            timeLimitInMinutes = timeLimitFromDB;

            // Set the question and choices in the UI components
            questionLabel.setText(question);
            choice1.setText(choice_1);
            choice2.setText(choice_2);
            choice3.setText(choice_3);
            choice4.setText(choice_4);
            
            startTimer();


            // Check if the selected answer matches the correct answer
            String selectedAnswer = null;
            if (choice1.isSelected()) {
                selectedAnswer = choice_1;
            } else if (choice2.isSelected()) {
                selectedAnswer = choice_2;
            } else if (choice3.isSelected()) {
                selectedAnswer = choice_3;
            } else if (choice4.isSelected()) {
                selectedAnswer = choice_4;
            }

            if (selectedAnswer != null && selectedAnswer.equals(answer)) {
                // Increment the counter for correct answers
                correct++;
            }

            // Increment the question counter
            questionCounter++;
        } else {
            // Handle the case when there are no more questions
            JOptionPane.showMessageDialog(null, "No more questions available.");
            throw new IllegalStateException("No more questions available.");
        }
    } catch (SQLException ex) {
        Logger.getLogger(Quiz_Form.class.getName()).log(Level.SEVERE, null, ex);
    }
}

private void startTimer() {
    // Convert the time limit from minutes to milliseconds
    int timeLimitInMillis = timeLimitInMinutes * 60 * 1000;

    // Set the initial delay and create the Timer instance
    int delayInMillis = 1000; // 1 second
    timer = new Timer(delayInMillis, new ActionListener() {
        int remainingTimeInMillis = timeLimitInMillis;

        @Override
        public void actionPerformed(ActionEvent e) {
            // Calculate the remaining minutes and seconds
            int remainingMinutes = remainingTimeInMillis / (60 * 1000);
            int remainingSeconds = (remainingTimeInMillis / 1000) % 60;

            // Format the remaining time as "mm:ss"
            String formattedTime = String.format("%02d:%02d", remainingMinutes, remainingSeconds);

            // Set the formatted time as the text of the timer label
            timerz.setText(formattedTime);

            // Check if the time has run out
            if (remainingTimeInMillis <= 0) {
                timer.stop();
                // Perform any actions you want when time is up
                // For example, auto-click the "Next" button, display an error message, etc.
                // You can call a method here to handle the time-up scenario.
                autoClickNextButton();
            }

            // Decrement the remaining time
            remainingTimeInMillis -= delayInMillis;
        }
    });

    // Start the timer
    timer.start();
}

private void autoClickNextButton() {
    // Perform automatic click on the "Next" button here
    Next.doClick();
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        Next = new javax.swing.JButton();
        questionLabel = new javax.swing.JLabel();
        choice1 = new javax.swing.JRadioButton();
        choice2 = new javax.swing.JRadioButton();
        choice3 = new javax.swing.JRadioButton();
        choice4 = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        timerz = new javax.swing.JLabel();

        jPanel4.setBackground(new java.awt.Color(253, 225, 8));
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(253, 225, 8));
        jLabel2.setFont(new java.awt.Font("Manrope ExtraBold", 0, 18)); // NOI18N
        jLabel2.setText(" <");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(23, 23, 23))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(248, 248, 248));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        Next.setBackground(new java.awt.Color(51, 51, 51));
        Next.setFont(new java.awt.Font("Manrope", 0, 12)); // NOI18N
        Next.setForeground(new java.awt.Color(255, 255, 255));
        Next.setText("Next");
        Next.setBorder(null);
        Next.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NextActionPerformed(evt);
            }
        });

        questionLabel.setFont(new java.awt.Font("Manrope Medium", 0, 16)); // NOI18N
        questionLabel.setText("1) A Database Management System is a type of _________software.");

        choice1.setBackground(new java.awt.Color(255, 255, 255));
        choice1.setFont(new java.awt.Font("Manrope Medium", 0, 14)); // NOI18N
        choice1.setText("jRadioButton1");
        choice1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                choice1ActionPerformed(evt);
            }
        });

        choice2.setBackground(new java.awt.Color(255, 255, 255));
        choice2.setFont(new java.awt.Font("Manrope Medium", 0, 14)); // NOI18N
        choice2.setText("jRadioButton2");
        choice2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                choice2ActionPerformed(evt);
            }
        });

        choice3.setBackground(new java.awt.Color(255, 255, 255));
        choice3.setFont(new java.awt.Font("Manrope Medium", 0, 14)); // NOI18N
        choice3.setText("jRadioButton3");
        choice3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                choice3ActionPerformed(evt);
            }
        });

        choice4.setBackground(new java.awt.Color(255, 255, 255));
        choice4.setFont(new java.awt.Font("Manrope Medium", 0, 14)); // NOI18N
        choice4.setText("jRadioButton4");
        choice4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                choice4ActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(253, 225, 8));
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(253, 225, 8));
        jLabel1.setFont(new java.awt.Font("Manrope ExtraBold", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText(" <");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(23, 23, 23))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(51, 51, 51));
        jPanel5.setForeground(new java.awt.Color(255, 255, 255));
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel5MouseClicked(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(253, 225, 8));
        jLabel3.setFont(new java.awt.Font("Manrope SemiBold", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Time Left");

        timerz.setBackground(new java.awt.Color(253, 225, 8));
        timerz.setFont(new java.awt.Font("Manrope SemiBold", 0, 14)); // NOI18N
        timerz.setForeground(new java.awt.Color(255, 255, 255));
        timerz.setText("00:00");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(timerz, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timerz)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(choice4, javax.swing.GroupLayout.PREFERRED_SIZE, 648, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addComponent(Next, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(315, 315, 315))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addComponent(choice3, javax.swing.GroupLayout.PREFERRED_SIZE, 648, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(86, 86, 86))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(questionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(59, 59, 59))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(choice1, javax.swing.GroupLayout.PREFERRED_SIZE, 648, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(choice2, javax.swing.GroupLayout.PREFERRED_SIZE, 648, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(31, 31, 31)
                .addComponent(questionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71)
                .addComponent(choice1)
                .addGap(18, 18, 18)
                .addComponent(choice2)
                .addGap(18, 18, 18)
                .addComponent(choice3)
                .addGap(18, 18, 18)
                .addComponent(choice4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                .addComponent(Next, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(92, 92, 92))
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

    private void NextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NextActionPerformed

    try {
            // Retrieve the current question and answer from the database
            String question = rs.getString("questions");
            String answer = rs.getString("answer");
            String quiz_title = rs.getString("quizTitle");

            // Check if the selected answer matches the correct answer
            String selectedAnswer = null;
            if (choice1.isSelected()) {
                selectedAnswer = choice1.getText();
            } else if (choice2.isSelected()) {
                selectedAnswer = choice2.getText();
            } else if (choice3.isSelected()) {
                selectedAnswer = choice3.getText();
            } else if (choice4.isSelected()) {
                selectedAnswer = choice4.getText();
            }

            if (selectedAnswer != null && selectedAnswer.equals(answer)) {
                // Increment the counter for correct answers
                correct++;
            }

            // Move to the next question
            if (rs.next()) {
                // Retrieve the next question, choices, and answer
                String nextQuestion = rs.getString("questions");
                String choice_1 = rs.getString("choice1");
                String choice_2 = rs.getString("choice2");
                String choice_3 = rs.getString("choice3");
                String choice_4 = rs.getString("choice4");

                // Set the question and choices in the UI components
                questionLabel.setText(nextQuestion);
                choice1.setText(choice_1);
                choice2.setText(choice_2);
                choice3.setText(choice_3);
                choice4.setText(choice_4);
                bg.clearSelection();
            } else {
                // Handle the case when there are no more questions
                Next.setText("Finished");
                Next.setEnabled(false);

                // Retrieve the total number of questions
                pst = con.prepareStatement("SELECT COUNT(*) AS total FROM questions WHERE quizTitle = ? AND subject = ?");
                pst.setString(1, titlez);
                pst.setString(2, subjectz);
                rs = pst.executeQuery();
                int totalQuestions = 0;
                if (rs.next()) {
                    totalQuestions = rs.getInt("total");
                }

                // Retrieve the quiz title from the questions table
                pst = con.prepareStatement("SELECT * FROM quiz_list WHERE title=?");
                pst.setString(1, titlez);
                rs = pst.executeQuery();
                int points = 0;
                if (rs.next()) {
                    points = rs.getInt("pointsperitem");
                }       

                // Insert the score, username, full name, and quiz title into the quiz_record table
                String score = (correct * points) + " / " + (totalQuestions * points);
                String rating = (correct * points * 100) / (totalQuestions * points) + "%";

                String fullName = "";

                // Retrieve the firstname and lastname from the reg_student table
                pst = con.prepareStatement("SELECT firstname, lastname FROM reg_student WHERE username = ?");
                pst.setString(1, userName);
                rs = pst.executeQuery();
                if (rs.next()) {
                    String firstName = rs.getString("firstname");
                    String lastName = rs.getString("lastname");
                    fullName = firstName + " " + lastName;
                }

                pst = con.prepareStatement("INSERT INTO quiz_record (username, studName, score, ratings, quiz, subject) VALUES (?, ?, ?, ?, ?, ?)");
                pst.setString(1, userName);
                pst.setString(2, fullName);
                pst.setString(3, score);
                pst.setString(4, rating);
                pst.setString(5, quiz_title);
                pst.setString(6, subjectz);
                pst.executeUpdate();

                // Update the completion_status column in the quiz_list table
                pst = con.prepareStatement("UPDATE quiz_list SET completion_status = 'completed' WHERE username = ? AND title = ?");
                pst.setString(1, userName);
                pst.setString(2, quiz_title);
                pst.executeUpdate();

                // Update the status column in the reg_student table
                String query = "SELECT COUNT(*) AS total FROM quiz_list WHERE username = ?";
                pst = con.prepareStatement(query);
                pst.setString(1, userName);
                rs = pst.executeQuery();
                int totalQuizzes = 0;
                if (rs.next()) {
                    totalQuizzes = rs.getInt("total");
                }

                query = "SELECT COUNT(*) AS completed FROM quiz_list WHERE username = ? AND completion_status = 'completed'";
                pst = con.prepareStatement(query);
                pst.setString(1, userName);
                rs = pst.executeQuery();
                int completedQuizzes = 0;
                if (rs.next()) {
                    completedQuizzes = rs.getInt("completed");
                }

                if (completedQuizzes == totalQuizzes) {
                    pst = con.prepareStatement("UPDATE reg_student SET status = 'Complete Quizzes' WHERE username = ?");
                    pst.setString(1, userName);
                    pst.executeUpdate();
                }

                questionLabel.setText("You Scored: " + score);

                DashBoardStud db = new DashBoardStud(userName);
                // Call the Fetch method on the instance
                db.FetchStud(userName);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Quiz_Form.class.getName()).log(Level.SEVERE, null, ex);
        }
    

    }//GEN-LAST:event_NextActionPerformed

    private void choice1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_choice1ActionPerformed
     selectedAnswer = choice1.getText();
    }//GEN-LAST:event_choice1ActionPerformed

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        DashBoardStud dash = new DashBoardStud(userName);
        dash.setVisible(true);
        
        dispose();
    }//GEN-LAST:event_jPanel3MouseClicked

    private void choice2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_choice2ActionPerformed
        selectedAnswer = choice2.getText();
    }//GEN-LAST:event_choice2ActionPerformed

    private void choice3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_choice3ActionPerformed
     selectedAnswer = choice3.getText();
    }//GEN-LAST:event_choice3ActionPerformed

    private void choice4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_choice4ActionPerformed
      selectedAnswer = choice4.getText();
    }//GEN-LAST:event_choice4ActionPerformed

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel4MouseClicked

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel5MouseClicked

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
            java.util.logging.Logger.getLogger(Quiz_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Quiz_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Quiz_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Quiz_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Next;
    private javax.swing.JRadioButton choice1;
    private javax.swing.JRadioButton choice2;
    private javax.swing.JRadioButton choice3;
    private javax.swing.JRadioButton choice4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel questionLabel;
    private javax.swing.JLabel timerz;
    // End of variables declaration//GEN-END:variables
}
