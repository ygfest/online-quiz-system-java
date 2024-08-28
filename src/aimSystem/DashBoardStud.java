
package aimSystem;
 /*PLEASE DON'T REMOVE CREDITS SECTION
 *                              -GRP 4
*/
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ScrollPaneConstants;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import net.proteanit.sql.DbUtils;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.JButton;


public class DashBoardStud extends javax.swing.JFrame{
     private String userName;
    
    public DashBoardStud(String userName)  {
    
        initComponents();
        Connect();
        FetchStud(userName);
        this.userName = userName;
        
       TableActionEvent event = new TableActionEvent() {
    @Override
    public void onTakeQuiz(int row) {
        System.out.print(row);
        String query = "SELECT completion_status FROM quiz_list WHERE username = ? AND subject = ? AND title = ?";
        try {
            pst = con.prepareStatement(query);
            pst.setString(1, userName);
            pst.setString(2, (String) tbl4.getValueAt(row, 1));
            pst.setString(3, (String) tbl4.getValueAt(row, 2));

            String subjectz = (String) tbl4.getValueAt(row, 1);
            String titlez = (String) tbl4.getValueAt(row, 2);

            System.out.print(subjectz + titlez);
            rs = pst.executeQuery();

            if (rs.next()) {
                String completionStatus = rs.getString("completion_status");

                if (completionStatus.equalsIgnoreCase("Not Yet Taken")) {
                    Quiz_Form qform = new Quiz_Form(userName, subjectz, titlez);
                    qform.setVisible(true);
                    dispose();
                } else if (completionStatus.equalsIgnoreCase("Completed")) {
                    JOptionPane.showMessageDialog(null, "Quiz already completed");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashBoardStud.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Close the resources (pst and rs) if needed, in a real application
        }
    }
};


        tbl4.getColumnModel().getColumn(5).setCellRenderer(new TableActionCellRenderer(userName));
        tbl4.getColumnModel().getColumn(5).setCellEditor(new TableActionCellEditor(event));
        
        
        
        
        
        tbl0.setShowGrid(false);
        tbl0.setBorder(BorderFactory.createEmptyBorder());
        tbl0.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
        tbl0.setIntercellSpacing(new Dimension(0, 0));
        tbl0.getTableHeader().setPreferredSize(new Dimension(0, 40));
        tbl0.getTableHeader().setFont(new Font("Manrope", Font.BOLD, 14));
        tbl0.getTableHeader().setOpaque(false);
        tbl0.getTableHeader().setBackground(new Color(51,51,51));
        tbl0.getTableHeader().setForeground(new Color(200,200,200));
        tbl0.setRowHeight(40);
        tbl0.setSelectionBackground(new Color(255,204,102));
        tbl0.setFont(new Font("Manrope", Font.PLAIN, 14));
        jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
  
        tbl4.setShowGrid(false);
        tbl4.setBorder(BorderFactory.createEmptyBorder());
        tbl4.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
        tbl4.setIntercellSpacing(new Dimension(0, 0));
        tbl4.getTableHeader().setPreferredSize(new Dimension(0, 40));
        tbl4.getTableHeader().setFont(new Font("Manrope", Font.BOLD, 14));
        tbl4.getTableHeader().setOpaque(false);
        tbl4.getTableHeader().setBackground(new Color(51,51,51));
        tbl4.getTableHeader().setForeground(new Color(200,200,200));
        tbl4.setRowHeight(40);
        tbl4.setSelectionBackground(new Color(255,204,102));
        tbl4.setFont(new Font("Manrope", Font.PLAIN, 14));
        
        tbl5.setShowGrid(false);
        tbl5.setBorder(BorderFactory.createEmptyBorder());
        tbl5.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
        tbl5.setIntercellSpacing(new Dimension(0, 0));
        tbl5.getTableHeader().setPreferredSize(new Dimension(0, 40));
        tbl5.getTableHeader().setFont(new Font("Manrope", Font.BOLD, 14));
        tbl5.getTableHeader().setOpaque(false);
        tbl5.getTableHeader().setBackground(new Color(51,51,51));
        tbl5.getTableHeader().setForeground(new Color(200,200,200));
        tbl5.setRowHeight(40);
        tbl5.setSelectionBackground(new Color(255,204,102));
        tbl5.setFont(new Font("Manrope", Font.PLAIN, 14));
        
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Get the day of the week
        DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
        String dayOfWeekString = dayOfWeek.toString(); // e.g., "MONDAY"

        // Format the day of the week string as desired
        // Here, we'll convert it to title case (e.g., "Monday")
        dayOfWeekString = dayOfWeekString.substring(0, 1) + dayOfWeekString.substring(1).toLowerCase();

        // Get the date in the desired format
        String dateString = currentDate.toString(); // e.g., "2023-06-20"

        // Set the day of the week and date in the respective text fields

        day.setText(dayOfWeekString);
        day.setHorizontalAlignment(SwingConstants.CENTER); // Center the content text
        date.setText(dateString);
        date.setHorizontalAlignment(SwingConstants.CENTER); // Center the content text
 
    }
    
  
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
 
            
    
    public void Connect(){
        
      
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con =DriverManager.getConnection("jdbc:mysql://localhost/online_quizz","root","");
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DashBoardStud.class.getName()).log(Level.SEVERE, null, ex);
        }catch (SQLException ex) {
            Logger.getLogger(DashBoardStud.class.getName()).log(Level.SEVERE, null, ex);    
        }
    
    }
 public void FetchStud(String userName){
 try {
    // Retrieve program and yrSection for the given username
    String program;
    String section;
    pst = con.prepareStatement("SELECT sProg, sSec FROM reg_student WHERE username = ?");
    pst.setString(1, userName);
    rs = pst.executeQuery();

    if (rs.next()) {
        program = rs.getString("sProg");
        section = rs.getString("sSec");
    } else {
        // Handle the case where the username is not found
        // You can show an error message or take any other appropriate action.
        // For example, you can return from the method to prevent further execution.
        return;
    }

    pst = con.prepareStatement("SELECT quiz_list.title, quiz_list.subject, COUNT(*) AS completion_count FROM quiz_list INNER JOIN quiz_record ON quiz_record.quiz = quiz_list.title WHERE quiz_list.completion_status = 'completed' AND quiz_list.subject = quiz_record.subject AND quiz_list.program = ? AND quiz_list.yrSection = ? GROUP BY quiz_list.title, quiz_list.subject, quiz_list.username");
    pst.setString(1, program);
    pst.setString(2, section);
    rs = pst.executeQuery();

    DefaultTableModel df = (DefaultTableModel) tbl0.getModel();
    df.setRowCount(0);

    int counter = 1; // Initialize the row counter

    Set<String> uniqueTitles = new HashSet<>();

    while (rs.next()) {
        String title = rs.getString("title");
        String subject = rs.getString("subject");

        if (!uniqueTitles.contains(title)) {
            Vector vr = new Vector();

            vr.add(counter); // Add the row counter value
            vr.add(subject); // Add the subject value in the second column
            vr.add(title);
            vr.add(rs.getInt("completion_count") + " student(s)");
            df.addRow(vr);
            uniqueTitles.add(title);
            counter++; // Increment the row counter
        }
    }
} catch (SQLException ex) {
    Logger.getLogger(DashBoardStud.class.getName()).log(Level.SEVERE, null, ex);
}
  
    try {
        // Retrieve program and section for the given username
        String program;
        String section;
        pst = con.prepareStatement("SELECT sProg, sSec FROM reg_student WHERE username = ?");
        pst.setString(1, userName);
        rs = pst.executeQuery();

        if (rs.next()) {
            program = rs.getString("sProg");
            section = rs.getString("sSec");
        } else {
            // Handle the case where the username is not found
            // You can show an error message or take any other appropriate action.
            // For example, you can return from the method to prevent further execution.
            return;
        }
        // Assuming 'program' and 'section' variables are already defined and contain the desired program and section values

pst = con.prepareStatement("SELECT COUNT(*) as count FROM reg_student WHERE sProg = ? AND sSec = ?");
pst.setString(1, program);
pst.setString(2, section);
rs = pst.executeQuery();

String studentCount = "0"; // Initialize as string

if (rs.next()) {
    studentCount = Integer.toString(rs.getInt("count")); // Convert the int to a string
}

jLabelStudents.setText(studentCount);


        
    } catch (SQLException ex) {
        Logger.getLogger(DashBoardStud.class.getName()).log(Level.SEVERE, null, ex);
    }
       String query = "SELECT firstname, lastname, sex, sProg, sSec FROM reg_student WHERE username = ?";
       
try {
    pst = con.prepareStatement(query);
    pst.setString(1, userName);

    
    rs = pst.executeQuery();

    if (rs.next()) {
        String firstName = rs.getString("firstname");
        String lastName = rs.getString("lastname");
        String sEx = rs.getString("sex");
        String program = rs.getString("sProg");
        String section = rs.getString("sSec");
        

        // Generate the greeting for the user
        String greeting;
        LocalTime currentTime = LocalTime.now();
        int hour = currentTime.getHour();

        if (hour >= 0 && hour < 12) {
            greeting = "Good morning, " + firstName;
        } else if (hour >= 12 && hour < 17) {
            greeting = "Good afternoon, " + firstName;
        } else if (hour >= 17 && hour < 20) {
            greeting = "Good evening, " + firstName;
        } else {
            greeting = "Good night, " + firstName;
        }

        // Print or display the greeting
        greetingLabel.setText(greeting);
        fullname.setText(firstName + " " + lastName);
        sProgSec.setText(program + " " +section);
        fullname.setHorizontalAlignment(SwingConstants.CENTER);
        sProgSec.setHorizontalAlignment(SwingConstants.CENTER);
        
       if (sEx.equalsIgnoreCase("male")) {
            pfpman.setVisible(true);
            pfpwoman.setVisible(false);
            pfpman.setHorizontalAlignment(SwingConstants.CENTER); // Center the content text
       } else if (sEx.equalsIgnoreCase("female")) {
            pfpwoman.setVisible(true);
            pfpman.setVisible(false);
            pfpwoman.setHorizontalAlignment(SwingConstants.CENTER); // Center the content text
        }


    }
} catch (SQLException ex) {
    
    Logger.getLogger(DashBoardStud.class.getName()).log(Level.SEVERE, null, ex);
   
}        
       try {
    // Retrieve program and yrSection for the given username
    String program;
    String yrSection;
    pst = con.prepareStatement("SELECT sProg, sSec FROM reg_student WHERE username = ?");
    pst.setString(1, userName);
    rs = pst.executeQuery();

    if (rs.next()) {
        program = rs.getString("sProg");
        yrSection = rs.getString("sSec");
    } else {
        // Handle the case where the username is not found
        // You can show an error message or take any other appropriate action.
        // For example, you can return from the method to prevent further execution.
        return;
    }

    // Use the program and yrSection to filter the results
    pst = con.prepareStatement("SELECT * FROM quiz_list WHERE username = ? AND program = ? AND yrSection = ?");
    pst.setString(1, userName);
    pst.setString(2, program);
    pst.setString(3, yrSection);
    rs = pst.executeQuery();

    ResultSetMetaData rss = rs.getMetaData();
    int q = rss.getColumnCount();

    DefaultTableModel df = (DefaultTableModel) tbl4.getModel();
    df.setRowCount(0);
    int rowCounter = 0;
    int notTakenYetCounter = 0;
    while (rs.next()) {
        String completionStatus = rs.getString("completion_status");
        if (completionStatus.equalsIgnoreCase("Not yet Taken")) {
            notTakenYetCounter++;
        }
        Vector vr = new Vector();
        rowCounter++;
        for (int a = 1; a <= q; a++) {
            vr.add(rowCounter); // Add the row counter value to the vector
            vr.add(rs.getString("subject"));
            vr.add(rs.getString("title"));
            vr.add(rs.getString("pointsperitem"));
            vr.add(rs.getString("faculty"));
        }
        df.addRow(vr);
    }
    String notTakenYetString = Integer.toString(notTakenYetCounter);
    // Update the label displaying the count of "Not taken yet" quizzes
    pendingQ.setText(notTakenYetString);

    // Check if there is "Not yet Taken" data
    if (notTakenYetCounter > 0) {
        // Update the status column in the reg_student table to "Incomplete"
        pst = con.prepareStatement("UPDATE reg_student SET status = 'Incomplete' WHERE username = ?");
        pst.setString(1, userName);
        pst.executeUpdate();
    } else {
        // Update the status column in the reg_student table to "Complete Quizzes"
        pst = con.prepareStatement("UPDATE reg_student SET status = 'Complete Quizzes' WHERE username = ?");
        pst.setString(1, userName);
        pst.executeUpdate();
    }
} catch (SQLException ex) {
    Logger.getLogger(DashBoardStud.class.getName()).log(Level.SEVERE, null, ex);
}

try {
    int q;
    pst = con.prepareStatement("SELECT * FROM quiz_record WHERE username = ?");
    pst.setString(1, userName); // Set the username parameter
    rs = pst.executeQuery();
    ResultSetMetaData rss = rs.getMetaData();
    q = rss.getColumnCount();

    DefaultTableModel df = (DefaultTableModel) tbl5.getModel();
    df.setRowCount(0);
    int rowCounter = 0;
    while (rs.next()) {
        Vector vr = new Vector();
        rowCounter++;
        for (int a = 1; a <= q; a++) {
            vr.add(rowCounter);
            vr.add(rs.getString("studName"));
            vr.add(rs.getString("subject"));
            vr.add(rs.getString("quiz"));
            vr.add(rs.getString("score"));
            vr.add(rs.getString("ratings"));
        }
        df.addRow(vr);
    }
    String rowString = Integer.toString(rowCounter);
    jLabelQuizList.setText(rowString);
} catch (SQLException ex) {
    Logger.getLogger(DashBoardStud.class.getName()).log(Level.SEVERE, null, ex);
}
        }

private class TableActionCellRenderer extends DefaultTableCellRenderer {
    private String userName;

    public TableActionCellRenderer(String userName) {
        super();
        this.userName = userName;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // Customize the rendering of the table cell
        String query = "SELECT completion_status FROM quiz_list WHERE username = ? AND subject = ? AND title = ?";
        try {
            pst = con.prepareStatement(query);
            pst.setString(1, userName);
            pst.setString(2, (String) tbl4.getValueAt(row, 1));
            pst.setString(3, (String) tbl4.getValueAt(row, 2));

            rs = pst.executeQuery();

            if (rs.next()) {
                String status = rs.getString("completion_status");

                if (status.equalsIgnoreCase("Not Yet Taken")) {
                    StatusPanel stat = new StatusPanel();
                    tbl4.setEnabled(true);
                    return stat.takequiz;
                } else if (status.equalsIgnoreCase("Completed")) {
                    StatusPanel stats = new StatusPanel();
                    return stats.completed;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashBoardStud.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Return a default component if none of the conditions are met
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
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
        Home = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        pendingQ = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabelQuizList = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabelStudents = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        fullname = new javax.swing.JLabel();
        pfpman = new javax.swing.JLabel();
        pfpwoman = new javax.swing.JLabel();
        sProgSec = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        greetingLabel = new javax.swing.JLabel();
        tag1 = new javax.swing.JLabel();
        tag2 = new javax.swing.JLabel();
        tag3 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        day = new javax.swing.JLabel();
        date = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl0 = new javax.swing.JTable();
        jPanel25 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbl4 = new javax.swing.JTable();
        jPanel19 = new javax.swing.JPanel();
        jLabel56 = new javax.swing.JLabel();
        searchQlist = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jLabel57 = new javax.swing.JLabel();
        searchQrec = new javax.swing.JTextField();
        jPanel22 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbl5 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        storeUsernames = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tab1 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tab5 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tab4 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        jScrollPane2.setViewportView(jEditorPane1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Home.setBackground(new java.awt.Color(255, 255, 255));
        Home.setForeground(new java.awt.Color(255, 255, 255));
        Home.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        Home.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -38, -1, 660));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane2.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane2.setForeground(new java.awt.Color(255, 255, 255));
        jTabbedPane2.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);

        jPanel3.setBackground(new java.awt.Color(230, 233, 238));
        jPanel3.setMaximumSize(new java.awt.Dimension(40000, 32767));

        jPanel9.setBackground(new java.awt.Color(17, 157, 101));
        jPanel9.setForeground(new java.awt.Color(255, 255, 255));
        jPanel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel9MouseClicked(evt);
            }
        });

        jLabel9.setBackground(new java.awt.Color(51, 51, 51));
        jLabel9.setFont(new java.awt.Font("Manrope Medium", 0, 16)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Pending Quizzes");

        pendingQ.setBackground(new java.awt.Color(51, 51, 51));
        pendingQ.setFont(new java.awt.Font("Manrope Medium", 0, 16)); // NOI18N
        pendingQ.setForeground(new java.awt.Color(255, 255, 255));
        pendingQ.setText("1");

        jLabel20.setBackground(new java.awt.Color(51, 51, 51));
        jLabel20.setFont(new java.awt.Font("Gotham Thin", 0, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/pend.png"))); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel9)
                .addContainerGap(112, Short.MAX_VALUE))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(pendingQ, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel20)
                .addGap(15, 15, 15))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel20)
                    .addComponent(pendingQ))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel12.setBackground(new java.awt.Color(28, 121, 206));
        jPanel12.setForeground(new java.awt.Color(255, 255, 255));
        jPanel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel12MouseClicked(evt);
            }
        });

        jLabel21.setBackground(new java.awt.Color(51, 51, 51));
        jLabel21.setFont(new java.awt.Font("Manrope Medium", 0, 16)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Completed Quizzes");

        jLabelQuizList.setBackground(new java.awt.Color(51, 51, 51));
        jLabelQuizList.setFont(new java.awt.Font("Manrope Medium", 0, 16)); // NOI18N
        jLabelQuizList.setForeground(new java.awt.Color(255, 255, 255));
        jLabelQuizList.setText("1");

        jLabel23.setBackground(new java.awt.Color(51, 51, 51));
        jLabel23.setFont(new java.awt.Font("Gotham Thin", 0, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/icons8-quiz-30 (1).png"))); // NOI18N

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabelQuizList, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel23)
                .addGap(15, 15, 15))
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel23)
                    .addComponent(jLabelQuizList))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel13.setBackground(new java.awt.Color(244, 203, 52));
        jPanel13.setForeground(new java.awt.Color(255, 255, 255));
        jPanel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel13MouseClicked(evt);
            }
        });

        jLabel24.setBackground(new java.awt.Color(51, 51, 51));
        jLabel24.setFont(new java.awt.Font("Manrope Medium", 0, 16)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Enrolled Students");

        jLabelStudents.setBackground(new java.awt.Color(51, 51, 51));
        jLabelStudents.setFont(new java.awt.Font("Manrope Medium", 0, 16)); // NOI18N
        jLabelStudents.setForeground(new java.awt.Color(255, 255, 255));
        jLabelStudents.setText("1");

        jLabel26.setBackground(new java.awt.Color(51, 51, 51));
        jLabel26.setFont(new java.awt.Font("Gotham Thin", 0, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/icons8-student-33.png"))); // NOI18N

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel24)
                .addContainerGap(115, Short.MAX_VALUE))
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabelStudents, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel26)
                .addGap(15, 15, 15))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel26)
                    .addComponent(jLabelStudents))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setForeground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        fullname.setBackground(new java.awt.Color(51, 51, 51));
        fullname.setFont(new java.awt.Font("Manrope Medium", 0, 16)); // NOI18N
        fullname.setForeground(new java.awt.Color(0, 0, 0));
        fullname.setText("Pending Quizzes");
        jPanel10.add(fullname, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 200, -1));

        pfpman.setBackground(new java.awt.Color(51, 51, 51));
        pfpman.setFont(new java.awt.Font("Gotham Thin", 0, 18)); // NOI18N
        pfpman.setForeground(new java.awt.Color(255, 255, 255));
        pfpman.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/man.png"))); // NOI18N
        jPanel10.add(pfpman, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, -1, -1));

        pfpwoman.setBackground(new java.awt.Color(51, 51, 51));
        pfpwoman.setFont(new java.awt.Font("Gotham Thin", 0, 18)); // NOI18N
        pfpwoman.setForeground(new java.awt.Color(255, 255, 255));
        pfpwoman.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/woman.png"))); // NOI18N
        jPanel10.add(pfpwoman, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, -1, -1));

        sProgSec.setBackground(new java.awt.Color(51, 51, 51));
        sProgSec.setFont(new java.awt.Font("Manrope Medium", 0, 16)); // NOI18N
        sProgSec.setForeground(new java.awt.Color(0, 0, 0));
        sProgSec.setText("1");
        jPanel10.add(sProgSec, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 230, 140, -1));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

        greetingLabel.setFont(new java.awt.Font("Manrope ExtraBold", 0, 24)); // NOI18N
        greetingLabel.setForeground(new java.awt.Color(0, 0, 0));
        greetingLabel.setText("noice");

        tag1.setFont(new java.awt.Font("Manrope ExtraBold", 0, 16)); // NOI18N
        tag1.setForeground(new java.awt.Color(28, 121, 206));
        tag1.setText("Revolutionizing Education");

        tag2.setFont(new java.awt.Font("Manrope ExtraBold", 0, 16)); // NOI18N
        tag2.setForeground(new java.awt.Color(17, 157, 101));
        tag2.setText("Reinventing Education");

        tag3.setFont(new java.awt.Font("Manrope ExtraBold", 0, 16)); // NOI18N
        tag3.setForeground(new java.awt.Color(255, 204, 0));
        tag3.setText("Empowering Tomorrow");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(greetingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tag1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tag2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tag3)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tag2)
                        .addComponent(tag3)
                        .addComponent(tag1))
                    .addComponent(greetingLabel))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setForeground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        day.setBackground(new java.awt.Color(255, 255, 255));
        day.setFont(new java.awt.Font("Manrope Medium", 0, 16)); // NOI18N
        day.setForeground(new java.awt.Color(0, 0, 0));
        day.setText("Day of the Week");
        jPanel11.add(day, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, -1, -1));

        date.setBackground(new java.awt.Color(255, 255, 255));
        date.setFont(new java.awt.Font("Manrope Medium", 0, 16)); // NOI18N
        date.setForeground(new java.awt.Color(0, 0, 0));
        date.setText("Date");
        jPanel11.add(date, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 100, -1));

        jLabel25.setBackground(new java.awt.Color(51, 51, 51));
        jLabel25.setFont(new java.awt.Font("Gotham Thin", 0, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/weath.png"))); // NOI18N
        jPanel11.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, -1, -1));

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));

        tbl0.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "#", "Subject", "Quiz Title", "Had Taken"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl0.setFocusable(false);
        tbl0.setGridColor(new java.awt.Color(255, 255, 255));
        tbl0.setRowHeight(40);
        tbl0.setShowGrid(false);
        jScrollPane1.setViewportView(tbl0);

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 785, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel25.setBackground(new java.awt.Color(230, 233, 238));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/icons8-facebook-18.png"))); // NOI18N

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/icons8-twitter-18.png"))); // NOI18N

        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/icons8-instagram-18.png"))); // NOI18N

        jLabel30.setBackground(new java.awt.Color(255, 255, 255));
        jLabel30.setFont(new java.awt.Font("Manrope ExtraBold", 0, 10)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(51, 51, 51));
        jLabel30.setText("         PHITECH");

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel29)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel30)
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel18)
                    .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel10.setFont(new java.awt.Font("Gotham", 1, 9)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("SAGUM   SAN ESTEBAN");

        jLabel12.setFont(new java.awt.Font("Gotham", 1, 9)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setText("BY  CRUZ  DE CASTRO  FRANCISCO");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)))
                    .addComponent(jPanel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(62, 62, 62))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
                    .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)))
                .addContainerGap())
        );

        jTabbedPane2.addTab("tab1", jPanel3);

        jPanel6.setBackground(new java.awt.Color(230, 233, 238));

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));

        tbl4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "#", "Subject", "Quiz Title", "Point per Item", "Faculty", "Completion Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl4.setFocusable(false);
        tbl4.setRowHeight(40);
        jScrollPane5.setViewportView(tbl4);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 1065, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));

        jLabel56.setFont(new java.awt.Font("Manrope ExtraBold", 0, 18)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(0, 0, 0));
        jLabel56.setText("Quiz List");

        searchQlist.setBackground(new java.awt.Color(255, 255, 255));
        searchQlist.setFont(new java.awt.Font("Manrope", 0, 14)); // NOI18N
        searchQlist.setForeground(new java.awt.Color(153, 153, 153));
        searchQlist.setText("Search");
        searchQlist.setBorder(null);
        searchQlist.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                searchQlistFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                searchQlistFocusLost(evt);
            }
        });
        searchQlist.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchQlistMouseClicked(evt);
            }
        });
        searchQlist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchQlistActionPerformed(evt);
            }
        });
        searchQlist.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchQlistKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(searchQlist, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel56, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(searchQlist))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("tab4", jPanel6);

        jPanel7.setBackground(new java.awt.Color(230, 233, 238));

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));

        jLabel57.setFont(new java.awt.Font("Manrope ExtraBold", 0, 18)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(0, 0, 0));
        jLabel57.setText("Quiz Record");

        searchQrec.setBackground(new java.awt.Color(255, 255, 255));
        searchQrec.setFont(new java.awt.Font("Manrope", 0, 14)); // NOI18N
        searchQrec.setForeground(new java.awt.Color(153, 153, 153));
        searchQrec.setText("Search");
        searchQrec.setBorder(null);
        searchQrec.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                searchQrecFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                searchQrecFocusLost(evt);
            }
        });
        searchQrec.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchQrecMouseClicked(evt);
            }
        });
        searchQrec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchQrecActionPerformed(evt);
            }
        });
        searchQrec.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchQrecKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(searchQrec, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(searchQrec)
                    .addComponent(jLabel57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));

        tbl5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "#", "Name of Student", "Subject", "Quiz", "Score", "Ratings"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl5.setFocusable(false);
        tbl5.setRowHeight(40);
        jScrollPane6.setViewportView(tbl5);

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 1065, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("tab5", jPanel7);

        jPanel8.add(jTabbedPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, 1160, 640));

        jPanel2.setBackground(new java.awt.Color(25, 49, 83));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setBackground(new java.awt.Color(51, 51, 51));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/BY CRUZ  de CASTRO  FRANCISCO  SAGUM  SAN ESTEBAN (3).png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, -1, -1));

        storeUsernames.setBackground(new java.awt.Color(0, 0, 0));
        storeUsernames.setFont(new java.awt.Font("Gotham XLight", 0, 10)); // NOI18N
        jPanel2.add(storeUsernames, new org.netbeans.lib.awtextra.AbsoluteConstraints(223, 592, 55, -1));

        jLabel3.setFont(new java.awt.Font("Manrope Medium", 0, 10)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Terms");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 592, 37, -1));

        tab1.setBackground(new java.awt.Color(25, 49, 83));
        tab1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tab1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                tab1MouseExited(evt);
            }
        });

        jLabel13.setBackground(new java.awt.Color(51, 51, 51));
        jLabel13.setFont(new java.awt.Font("Manrope SemiBold", 0, 13)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("HOME");
        jLabel13.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                jLabel13ComponentAdded(evt);
            }
        });
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel13MouseEntered(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/cloudzz.png"))); // NOI18N
        jLabel4.setText("o");

        javax.swing.GroupLayout tab1Layout = new javax.swing.GroupLayout(tab1);
        tab1.setLayout(tab1Layout);
        tab1Layout.setHorizontalGroup(
            tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab1Layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jLabel13)
                .addContainerGap(115, Short.MAX_VALUE))
        );
        tab1Layout.setVerticalGroup(
            tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab1Layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addGroup(tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel4)))
        );

        jPanel2.add(tab1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 300, 50));

        tab5.setBackground(new java.awt.Color(25, 49, 83));
        tab5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tab5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                tab5MouseExited(evt);
            }
        });

        jLabel17.setBackground(new java.awt.Color(51, 51, 51));
        jLabel17.setFont(new java.awt.Font("Manrope SemiBold", 0, 13)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("QUIZ RECORD");

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/rec.png"))); // NOI18N
        jLabel8.setText("o");

        javax.swing.GroupLayout tab5Layout = new javax.swing.GroupLayout(tab5);
        tab5.setLayout(tab5Layout);
        tab5Layout.setHorizontalGroup(
            tab5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab5Layout.createSequentialGroup()
                .addContainerGap(75, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jLabel17)
                .addGap(71, 71, 71))
        );
        tab5Layout.setVerticalGroup(
            tab5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(tab5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel8)))
        );

        jPanel2.add(tab5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 280, 300, -1));

        tab4.setBackground(new java.awt.Color(25, 49, 83));
        tab4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tab4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                tab4MouseExited(evt);
            }
        });

        jLabel16.setBackground(new java.awt.Color(51, 51, 51));
        jLabel16.setFont(new java.awt.Font("Manrope SemiBold", 0, 13)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("QUIZ LIST");

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/quizx.png"))); // NOI18N
        jLabel7.setText("o");

        javax.swing.GroupLayout tab4Layout = new javax.swing.GroupLayout(tab4);
        tab4.setLayout(tab4Layout);
        tab4Layout.setHorizontalGroup(
            tab4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab4Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(jLabel16)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tab4Layout.setVerticalGroup(
            tab4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tab4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16))
                .addContainerGap())
        );

        jPanel2.add(tab4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 300, 50));

        jLabel11.setFont(new java.awt.Font("Manrope Medium", 0, 10)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Privacy");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 572, 55, -1));

        jPanel8.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 650));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 1430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Home, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 622, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Home, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 622, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tab4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab4MouseExited
        tab4.setBackground(new Color(25,49,83));
    }//GEN-LAST:event_tab4MouseExited

    private void tab4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab4MouseEntered
        tab4.setBackground(new Color(70,130,180));
    }//GEN-LAST:event_tab4MouseEntered

    private void tab4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab4MouseClicked
        jTabbedPane2.setSelectedIndex(1);
    }//GEN-LAST:event_tab4MouseClicked

    private void tab5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab5MouseExited
        tab5.setBackground(new Color(25,49,83));
    }//GEN-LAST:event_tab5MouseExited

    private void tab5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab5MouseEntered
        tab5.setBackground(new Color(70,130,180));
    }//GEN-LAST:event_tab5MouseEntered

    private void tab5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab5MouseClicked
        jTabbedPane2.setSelectedIndex(2);
    }//GEN-LAST:event_tab5MouseClicked

    private void tab1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab1MouseExited
        tab1.setBackground(new Color(25,49,83));
    }//GEN-LAST:event_tab1MouseExited

    private void tab1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab1MouseEntered
        tab1.setBackground(new Color(70,130,180));
    }//GEN-LAST:event_tab1MouseEntered

    private void tab1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab1MouseClicked
        jTabbedPane2.setSelectedIndex(0);

    }//GEN-LAST:event_tab1MouseClicked

    private void jLabel13MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel13MouseEntered

    private void jLabel13ComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_jLabel13ComponentAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel13ComponentAdded

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        HomePage home = new HomePage();

        home.show();
        dispose();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void searchQrecKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchQrecKeyReleased
        String sEarch = searchQrec.getText();
        try {
            String query = "SELECT * FROM quiz_record WHERE (no LIKE '%" + sEarch + "%' OR studName LIKE '%" + sEarch + "%' OR quiz LIKE '%" + sEarch + "%' OR score LIKE '%" + sEarch + "%') AND username=?";
            pst = con.prepareStatement(query);
            pst.setString(1, userName);
            rs = pst.executeQuery();
            tbl5.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException ex) {
            Logger.getLogger(DashBoardStud.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (pst != null) {
                    rs.close();
                    pst.close();
                }
            } catch (SQLException e) {
                // Handle exception
            }
        }

    }//GEN-LAST:event_searchQrecKeyReleased

    private void searchQrecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchQrecActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchQrecActionPerformed

    private void searchQrecMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchQrecMouseClicked
        searchQrec.setText("");
    }//GEN-LAST:event_searchQrecMouseClicked

    private void searchQrecFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchQrecFocusLost

    }//GEN-LAST:event_searchQrecFocusLost

    private void searchQrecFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchQrecFocusGained

    }//GEN-LAST:event_searchQrecFocusGained

    private void searchQlistKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchQlistKeyReleased
        String search = searchQlist.getText();
        try {
            String query = "SELECT DISTINCT * FROM quiz_list WHERE no LIKE '%" + search + "%' OR title LIKE '%" + search + "%' OR pointsperitem LIKE '%" + search + "%' OR faculty LIKE '%" + search + "%'";
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();

            DefaultTableModel model = (DefaultTableModel) tbl4.getModel();
            model.setRowCount(0);
            int rowCounter = 0;

            HashSet<String> addedTitles = new HashSet<>();

            while (rs.next()) {
                rowCounter++;
                String quizTitle = rs.getString("title");
                if (!addedTitles.contains(quizTitle)) {
                    String quizNo = rs.getString("no");
                    String pointsPerItem = rs.getString("pointsperitem");
                    String faculty = rs.getString("faculty");

                    model.addRow(new Object[]{quizNo, quizTitle, pointsPerItem, faculty});

                    addedTitles.add(quizTitle);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException e) {
                // Handle exception
            }
        }
    }//GEN-LAST:event_searchQlistKeyReleased

    private void searchQlistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchQlistActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchQlistActionPerformed

    private void searchQlistMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchQlistMouseClicked
        searchQlist.setText("");
    }//GEN-LAST:event_searchQlistMouseClicked

    private void searchQlistFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchQlistFocusLost

    }//GEN-LAST:event_searchQlistFocusLost

    private void searchQlistFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchQlistFocusGained

    }//GEN-LAST:event_searchQlistFocusGained

    private void jPanel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel13MouseClicked
   
    }//GEN-LAST:event_jPanel13MouseClicked

    private void jPanel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel12MouseClicked
        jTabbedPane2.setSelectedIndex(2);
    }//GEN-LAST:event_jPanel12MouseClicked

    private void jPanel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseClicked

            jTabbedPane2.setSelectedIndex(1);
    }//GEN-LAST:event_jPanel9MouseClicked
  

    /**
     * @param args the command line arguments
     */
       
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
            java.util.logging.Logger.getLogger(DashBoardStud.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DashBoardStud.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DashBoardStud.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DashBoardStud.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private javax.swing.JPanel Home;
    private javax.swing.JLabel date;
    private javax.swing.JLabel day;
    private javax.swing.JLabel fullname;
    public javax.swing.JLabel greetingLabel;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelQuizList;
    private javax.swing.JLabel jLabelStudents;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel pendingQ;
    private javax.swing.JLabel pfpman;
    private javax.swing.JLabel pfpwoman;
    private javax.swing.JLabel sProgSec;
    private javax.swing.JTextField searchQlist;
    private javax.swing.JTextField searchQrec;
    public javax.swing.JLabel storeUsernames;
    private javax.swing.JPanel tab1;
    private javax.swing.JPanel tab4;
    private javax.swing.JPanel tab5;
    public javax.swing.JLabel tag1;
    public javax.swing.JLabel tag2;
    private javax.swing.JLabel tag3;
    private javax.swing.JTable tbl0;
    public javax.swing.JTable tbl4;
    private javax.swing.JTable tbl5;
    // End of variables declaration//GEN-END:variables

   

    
}
