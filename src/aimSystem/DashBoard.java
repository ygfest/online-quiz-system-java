
package aimSystem;

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
import javax.swing.BorderFactory;
import javax.swing.ScrollPaneConstants;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.DayOfWeek;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import net.proteanit.sql.DbUtils;
import java.sql.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class DashBoard extends javax.swing.JFrame {
    private String userName;
   
    
    
    public DashBoard(String userName)  {
    
        initComponents();
        Connect();
        Fetch(userName);
        jPanel24.setVisible(false);
        
         TableActionEvent1 event = new TableActionEvent1() {
    @Override
    public void onDelete(int row) {
        if (row >= 0 && row < tbl4.getRowCount()) {
            System.out.print("yowwwwwwwwww");

            if (tbl4.isEditing()) {
                tbl4.getCellEditor().stopCellEditing();
            }

            String query1 = "DELETE FROM quiz_list WHERE subject = ? AND title = ?";
            String query2 = "DELETE FROM quiz_record WHERE subject = ? AND quiz = ?";
            String query3 = "DELETE FROM questions WHERE subject = ? AND quizTitle = ?";

            try {
                pst = con.prepareStatement(query1);
                pst.setString(1, (String) tbl4.getValueAt(row, 1));
                pst.setString(2, (String) tbl4.getValueAt(row, 2));
                pst.executeUpdate();

                pst = con.prepareStatement(query2);
                pst.setString(1, (String) tbl4.getValueAt(row, 1));
                pst.setString(2, (String) tbl4.getValueAt(row, 2));
                pst.executeUpdate();

                pst = con.prepareStatement(query3);
                pst.setString(1, (String) tbl4.getValueAt(row, 1));
                pst.setString(2, (String) tbl4.getValueAt(row, 2));
                pst.executeUpdate();
                
                

                DefaultTableModel model = (DefaultTableModel) tbl4.getModel();
                model.removeRow(row);

                Fetch(userName);

                String titlez = (String) tbl4.getValueAt(row, 2);
                String subjectz = (String) tbl4.getValueAt(row, 2);
                System.out.print("Deleted " + titlez);

            } catch (SQLException ex) {
                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                // Close the resources (pst and rs) if needed, in a real application
                try {
                    if (pst != null) {
                        pst.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            System.out.println("Invalid row index: " + row);
        }
    }
            @Override
            public void onManage(int row) {
                String query = "SELECT * FROM quiz_list WHERE subject = ? AND title = ?";
                String titlez = (String) tbl4.getValueAt(row, 2);
                String subjectz = (String) tbl4.getValueAt(row, 1);
                ManageQuiz mq = new ManageQuiz(subjectz,titlez);
                mq.show();
            }
        };
         
         TableActionEvent2 event2 = new TableActionEvent2() {
    @Override
    public void onManages(int row) {
        String query = "SELECT * FROM reg_student WHERE firstname = ? AND lastname = ?";
        String firstn = (String) tbl3.getValueAt(row, 1);
        String lastn = (String) tbl3.getValueAt(row, 2);
        ManageStud ms = new ManageStud(firstn,lastn);
        ms.show();
    }
};
       try {
    // Retrieve distinct programs from the reg_student table
    PreparedStatement pstProgram = con.prepareStatement("SELECT DISTINCT sProg FROM reg_student");
    ResultSet rsProgram = pstProgram.executeQuery();

    // Retrieve distinct sections from the reg_student table
    PreparedStatement pstSection = con.prepareStatement("SELECT DISTINCT sSec FROM reg_student");
    ResultSet rsSection = pstSection.executeQuery();

    // Retrieve distinct subjects from the faculty_list table
    PreparedStatement pstSubject = con.prepareStatement("SELECT DISTINCT subject FROM faculty_list");
    ResultSet rsSubject = pstSubject.executeQuery();

    // Retrieve faculty names from the faculty_list table
    PreparedStatement pstFaculty = con.prepareStatement("SELECT firstname, lastname FROM faculty_list");
    ResultSet rsFaculty = pstFaculty.executeQuery();

    // Clear the combo boxes
    qprogram.removeAllItems();
    qsection.removeAllItems();
    qsubject.removeAllItems();
    qfaculty.removeAllItems();
    timelimit.removeAllItems();

    // Add default items to the combo boxes
    qprogram.addItem("Select Program");
    qsection.addItem("Select Section");
    qsubject.addItem("Select Subject");
    qfaculty.addItem("Select Faculty");
    timelimit.addItem("Select Time");
    timelimit.addItem("10");
    timelimit.addItem("20");
    timelimit.addItem("30");
    timelimit.addItem("40");
    timelimit.addItem("50");
    timelimit.addItem("60");

    // Add retrieved programs to the qprogram combo box
    while (rsProgram.next()) {
        String program = rsProgram.getString("sProg");
        qprogram.addItem(program);
    }

    // Add retrieved sections to the qsection combo box
    while (rsSection.next()) {
        String section = rsSection.getString("sSec");
        qsection.addItem(section);
    }

    // Add retrieved subjects to the qsubject combo box
    while (rsSubject.next()) {
        String subject = rsSubject.getString("subject");
        qsubject.addItem(subject);
    }

    // Add retrieved faculty names to the qfaculty combo box
    while (rsFaculty.next()) {
        String firstName = rsFaculty.getString("firstname");
        String lastName = rsFaculty.getString("lastname");
        String fullName = firstName + " " + lastName;
        qfaculty.addItem(fullName);
    }

    // Close the result sets and statements
    rsProgram.close();
    pstProgram.close();
    rsSection.close();
    pstSection.close();
    rsSubject.close();
    pstSubject.close();
    rsFaculty.close();
    pstFaculty.close();
} catch (SQLException ex) {
    Logger.getLogger(addQuiz.class.getName()).log(Level.SEVERE, null, ex);
}



        tbl4.getColumnModel().getColumn(5).setCellRenderer(new DashBoard.TableActionCellRenderer(userName));
        tbl4.getColumnModel().getColumn(5).setCellEditor(new TableActionCellEditor1(event));
        tbl3.getColumnModel().getColumn(6).setCellRenderer(new DashBoard.TableActionCellRenderer1(userName));
        tbl3.getColumnModel().getColumn(6).setCellEditor(new TableActionCellEditor2(event2));

        
        tbl0.setShowGrid(false);
        tbl0.setBorder(BorderFactory.createEmptyBorder());
        tbl0.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
        tbl0.setIntercellSpacing(new Dimension(0, 0));
        tbl0.getTableHeader().setPreferredSize(new Dimension(0, 40));
        tbl0.getTableHeader().setFont(new Font("Manrope", Font.BOLD, 14));
        tbl0.getTableHeader().setOpaque(false);
        tbl0.getTableHeader().setBackground(new Color(25,49,83));
        tbl0.getTableHeader().setForeground(new Color(200,200,200));
        tbl0.setRowHeight(40);
        tbl0.setSelectionBackground(new Color(255,204,102));
        tbl0.setFont(new Font("Manrope", Font.PLAIN, 14));
        jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        
        tbl3.setShowGrid(false);
        tbl3.setBorder(BorderFactory.createEmptyBorder());
        tbl3.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
        tbl3.setIntercellSpacing(new Dimension(0, 0));
        tbl3.getTableHeader().setPreferredSize(new Dimension(0, 40));
        tbl3.getTableHeader().setFont(new Font("Manrope", Font.BOLD, 14));
        tbl3.getTableHeader().setOpaque(false);
        tbl3.getTableHeader().setBackground(new Color(25,49,83));
        tbl3.getTableHeader().setForeground(new Color(200,200,200));
        tbl3.setRowHeight(40);
        tbl3.setSelectionBackground(new Color(255,204,102));
        tbl3.setFont(new Font("Manrope", Font.PLAIN, 14));
        jScrollPane3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        
        
        
        tbl2.setShowGrid(false);
        tbl2.setBorder(BorderFactory.createEmptyBorder());
        tbl2.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
        tbl2.setIntercellSpacing(new Dimension(0, 0));
        tbl2.getTableHeader().setPreferredSize(new Dimension(0, 40));
        tbl2.getTableHeader().setFont(new Font("Manrope", Font.BOLD, 14));
        tbl2.getTableHeader().setOpaque(false);
        tbl2.getTableHeader().setBackground(new Color(25,49,83));
        tbl2.getTableHeader().setForeground(new Color(200,200,200));
        tbl2.setRowHeight(40);
        tbl2.setSelectionBackground(new Color(255,204,102));
        tbl2.setFont(new Font("Manrope", Font.PLAIN, 14));
        jScrollPane4.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        
        tbl4.setShowGrid(false);
        tbl4.setBorder(BorderFactory.createEmptyBorder());
        tbl4.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
        tbl4.setIntercellSpacing(new Dimension(0, 0));
        tbl4.getTableHeader().setPreferredSize(new Dimension(0, 40));
        tbl4.getTableHeader().setFont(new Font("Manrope", Font.BOLD, 14));
        tbl4.getTableHeader().setOpaque(false);
        tbl4.getTableHeader().setBackground(new Color(25,49,83));
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
        tbl5.getTableHeader().setBackground(new Color(25,49,83));
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
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);    
        }
    
    }
        public void Fetch(String userName){
            try {
    pst = con.prepareStatement("SELECT quiz_list.title, quiz_list.subject, COUNT(*) AS completion_count FROM quiz_list INNER JOIN quiz_record ON quiz_record.quiz = quiz_list.title WHERE quiz_list.completion_status = 'completed' GROUP BY quiz_list.title, quiz_list.username");
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
            vr.add(subject);
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
    int q;
    pst = con.prepareStatement("SELECT * FROM reg_student ORDER BY lastname ASC");
    rs = pst.executeQuery();
    ResultSetMetaData rss = rs.getMetaData();
    q = rss.getColumnCount();

    DefaultTableModel df = (DefaultTableModel) tbl3.getModel();
    df.setRowCount(0);

    int rowCounter = 0;
    while (rs.next()) {
        rowCounter++; // Increment the row counter
        Vector<Object> vr = new Vector<>();

        for (int a = 1; a <= q; a++) {
            vr.add(rs.getString("sNumber"));
            vr.add(rs.getString("firstname"));
            vr.add(rs.getString("lastname"));
            vr.add(rs.getString("sProg"));
            vr.add(rs.getString("sSec"));
            vr.add(rs.getString("status"));
        }

        df.addRow(vr);
        String rowString = Integer.toString(rowCounter);
        jLabelStudents.setText(rowString);
    }
} catch (SQLException ex) {
    Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
}

       String query = "SELECT firstname, lastname, sex, subject FROM faculty_list WHERE username = ?";
       
try {
    pst = con.prepareStatement(query);
    pst.setString(1, userName);
    
    rs = pst.executeQuery();

    if (rs.next()) {
        String firstName = rs.getString("firstname");
        String lastName = rs.getString("lastname");
        String sEx = rs.getString("sex");
        String sUbject = rs.getString("subject");

        // Generate the greeting for the user
        String greeting;
        LocalTime currentTime = LocalTime.now();
        int hour = currentTime.getHour();

        if (hour >= 0 && hour < 12) {
            greeting = "Good morning, " + firstName;
        } else if (hour >= 12 && hour < 18) {
            greeting = "Good afternoon, " + firstName;
        } else if (hour >= 18 && hour < 20) {
            greeting = "Good evening, " + firstName;
        } else {
            greeting = "Good night, " + firstName;
        }

        // Print or display the greeting
        greetingLabel.setText(greeting);
        fullname.setText(firstName + " " + lastName);
        sProgSec.setText(sUbject);
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
    
    Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
   
}        try {
            int q;
            pst = con.prepareStatement("SELECT * FROM faculty_list");
            rs =pst.executeQuery();
            ResultSetMetaData rss = rs.getMetaData();
            q = rss.getColumnCount();
            
            DefaultTableModel df = (DefaultTableModel)tbl2.getModel();
            df.setRowCount(0);
            int rowCounter = 0;
            while(rs.next()){
                Vector vr = new Vector();
                rowCounter++;
                for(int a=1; a<=q; a++){
                    vr.add(rowCounter);
                    vr.add(rs.getString("firstname"));
                    vr.add(rs.getString("lastname"));
                    vr.add(rs.getString("subject"));
                }
                df.addRow(vr);
                String rowString = Integer.toString(rowCounter);
                jLabel10.setText(rowString);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }

      try {
    pst = con.prepareStatement("SELECT DISTINCT title, subject, pointsperitem, faculty FROM quiz_list");
    rs = pst.executeQuery();

    DefaultTableModel df = (DefaultTableModel) tbl4.getModel();
    df.setRowCount(0);

    int rowCounter = 0;
    while (rs.next()) {
        rowCounter++; // Increment the row counter
        Vector<Object> vr = new Vector<>();
        vr.add(rowCounter); // Add the row counter value to the vector
        vr.add(rs.getString("subject"));
        vr.add(rs.getString("title"));
        vr.add(rs.getString("pointsperitem"));
        vr.add(rs.getString("faculty"));

        df.addRow(vr);
    }
} catch (SQLException ex) {
    Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
}




try {
    String selectedQuiz = comboQRec.getSelectedItem().toString();
    pst = con.prepareStatement("SELECT * FROM quiz_record" + (selectedQuiz.equals("All") ? "" : " WHERE quiz = ?"));
    if (!selectedQuiz.equals("All")) {
        pst.setString(1, selectedQuiz);
    }
    rs = pst.executeQuery();
    ResultSetMetaData rss = rs.getMetaData();
    int columnCount = rss.getColumnCount();

    DefaultTableModel df = (DefaultTableModel) tbl5.getModel();
    df.setRowCount(0);
    int rowCounter = 0;

    // Use a Set to store unique quiz names
    Set<String> uniqueQuizzes = new HashSet<>();

    while (rs.next()) {
    Vector vr = new Vector();
    rowCounter++;
    String name = rs.getString("studName");
    String subj = rs.getString("subject");
    String quiz = rs.getString("quiz");
     String sCore = rs.getString("score");
    String ratings = rs.getString("ratings"); // Get the ratings value from the result set

    comboQRec.addItem("All");
    uniqueQuizzes.add(quiz);

    vr.add(rowCounter);
    vr.add(name);
    vr.add(subj);
    vr.add(quiz);
    vr.add(sCore);
    vr.add(ratings); // Add the ratings value to the row

    for (int a = 4; a <= columnCount; a++) {
        vr.add(rs.getString(a));
    }
    df.addRow(vr);
}


    // Clear and update combo box with unique quiz names
    comboQRec.removeAllItems();
    comboQRec.addItem("All");  // Add 'All' option back

    for (String quiz : uniqueQuizzes) {
        comboQRec.addItem(quiz);
    }

    String rowString = Integer.toString(rowCounter);
    jLabelQuizList.setText(rowString);
} catch (SQLException ex) {
    Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
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
        Component com = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        // Customize the rendering of the table cell
         StatusPanel1 stat = new StatusPanel1();
                    tbl4.setEnabled(true);
                    stat.setBackground(com.getBackground());
                    return stat;
    }}
    
    private class TableActionCellRenderer1 extends DefaultTableCellRenderer {
    private String userName;

    public TableActionCellRenderer1(String userName) {
        super();
        this.userName = userName;
    }
     // Additional method for tbl3
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component com = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        // Customize the rendering of the table cell for tbl3
        StatusPanel2 stat1 = new StatusPanel2();
        tbl3.setEnabled(true);
        stat1.setBackground(com.getBackground());
        return stat1;
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
        jLabel10 = new javax.swing.JLabel();
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
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        searchfac = new javax.swing.JTextField();
        addFaculty = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl2 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        searchStud = new javax.swing.JTextField();
        addFaculty1 = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl3 = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jLabel56 = new javax.swing.JLabel();
        searchQlist = new javax.swing.JTextField();
        addQuiz = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        addQuizPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jButton2 = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        title1 = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        pointper1 = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        qprogram = new javax.swing.JComboBox<>();
        qsection = new javax.swing.JComboBox<>();
        qsubject = new javax.swing.JComboBox<>();
        qfaculty = new javax.swing.JComboBox<>();
        jLabel34 = new javax.swing.JLabel();
        timelimit = new javax.swing.JComboBox<>();
        jPanel26 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbl4 = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jLabel57 = new javax.swing.JLabel();
        searchQrec = new javax.swing.JTextField();
        comboQRec = new javax.swing.JComboBox<>();
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
        tab2 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tab3 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
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
        jLabel9.setText("Faculty List");

        jLabel10.setBackground(new java.awt.Color(51, 51, 51));
        jLabel10.setFont(new java.awt.Font("Manrope Medium", 0, 16)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("1");

        jLabel20.setBackground(new java.awt.Color(51, 51, 51));
        jLabel20.setFont(new java.awt.Font("Gotham Thin", 0, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/icons8-training-30 (1).png"))); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel9)
                .addContainerGap(153, Short.MAX_VALUE))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jLabel10))
                .addContainerGap(23, Short.MAX_VALUE))
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
        jLabel21.setText("Total Quiz             ");

        jLabelQuizList.setBackground(new java.awt.Color(51, 51, 51));
        jLabelQuizList.setFont(new java.awt.Font("Manrope Medium", 0, 16)); // NOI18N
        jLabelQuizList.setForeground(new java.awt.Color(255, 255, 255));
        jLabelQuizList.setText("1");

        jLabel23.setBackground(new java.awt.Color(51, 51, 51));
        jLabel23.setFont(new java.awt.Font("Gotham Thin", 0, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/icons8-quiz-30.png"))); // NOI18N

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel21)
                .addContainerGap(131, Short.MAX_VALUE))
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabelQuizList, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel23)
                .addGap(15, 15, 15))
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
        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/stud_cap.png"))); // NOI18N

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
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setForeground(new java.awt.Color(0, 0, 0));
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
        jPanel10.add(pfpman, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, 150, -1));

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
        jPanel15.setForeground(new java.awt.Color(255, 255, 255));

        greetingLabel.setFont(new java.awt.Font("Manrope ExtraBold", 0, 24)); // NOI18N
        greetingLabel.setForeground(new java.awt.Color(0, 0, 0));
        greetingLabel.setText("yoww");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 97, Short.MAX_VALUE)
                .addComponent(tag1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tag2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tag3)
                .addGap(20, 20, 20))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tag2)
                    .addComponent(tag3)
                    .addComponent(tag1)
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

        tbl0.setBackground(new java.awt.Color(255, 255, 255));
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
                false, true, false, false
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 782, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
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

        jLabel35.setFont(new java.awt.Font("Gotham", 1, 9)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(51, 51, 51));
        jLabel35.setText("BY ESTEPANO");

        jLabel36.setFont(new java.awt.Font("Gotham", 1, 9)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35)
                    .addComponent(jLabel36)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(200, 200, 200)
                                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                                .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(0, 33, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel36)
                .addGap(324, 324, 324)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("tab1", jPanel3);

        jPanel4.setBackground(new java.awt.Color(230, 233, 238));

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        jLabel54.setFont(new java.awt.Font("Manrope ExtraBold", 0, 18)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(0, 0, 0));
        jLabel54.setText("Faculty List");

        searchfac.setBackground(new java.awt.Color(230, 233, 238));
        searchfac.setFont(new java.awt.Font("Manrope", 0, 14)); // NOI18N
        searchfac.setForeground(new java.awt.Color(153, 153, 153));
        searchfac.setText("Search");
        searchfac.setBorder(null);
        searchfac.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                searchfacFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                searchfacFocusLost(evt);
            }
        });
        searchfac.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchfacMouseClicked(evt);
            }
        });
        searchfac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchfacActionPerformed(evt);
            }
        });
        searchfac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchfacKeyReleased(evt);
            }
        });

        addFaculty.setBackground(new java.awt.Color(255, 255, 255));
        addFaculty.setFont(new java.awt.Font("Manrope", 0, 14)); // NOI18N
        addFaculty.setForeground(new java.awt.Color(0, 0, 0));
        addFaculty.setText("Add New");
        addFaculty.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(253, 225, 8), 1, true));
        addFaculty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFacultyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(searchfac, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(addFaculty, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(addFaculty, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(searchfac, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel54, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));

        tbl2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "First Name", "Last Name", "Subject Taught"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl2.setFocusable(false);
        tbl2.setGridColor(new java.awt.Color(204, 204, 204));
        tbl2.setRowHeight(40);
        tbl2.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(tbl2);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1065, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(67, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("tab2", jPanel4);

        jPanel5.setBackground(new java.awt.Color(230, 233, 238));

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));

        jLabel55.setFont(new java.awt.Font("Manrope ExtraBold", 0, 18)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(0, 0, 0));
        jLabel55.setText("Enrolled Students");

        searchStud.setBackground(new java.awt.Color(230, 233, 238));
        searchStud.setFont(new java.awt.Font("Manrope", 0, 14)); // NOI18N
        searchStud.setForeground(new java.awt.Color(153, 153, 153));
        searchStud.setText("Search");
        searchStud.setBorder(null);
        searchStud.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                searchStudFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                searchStudFocusLost(evt);
            }
        });
        searchStud.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchStudMouseClicked(evt);
            }
        });
        searchStud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchStudActionPerformed(evt);
            }
        });
        searchStud.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchStudKeyReleased(evt);
            }
        });

        addFaculty1.setBackground(new java.awt.Color(255, 255, 255));
        addFaculty1.setFont(new java.awt.Font("Manrope", 0, 14)); // NOI18N
        addFaculty1.setForeground(new java.awt.Color(0, 0, 0));
        addFaculty1.setText("Add New");
        addFaculty1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(253, 225, 8), 1, true));
        addFaculty1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFaculty1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(searchStud, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(addFaculty1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel55, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(searchStud)
                    .addComponent(addFaculty1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));

        tbl3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Student Number", "First Name", "Last Name", "Program", "Section", "Status", "Action"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl3.setFocusable(false);
        tbl3.setMinimumSize(new java.awt.Dimension(60, 64));
        tbl3.setOpaque(false);
        tbl3.setRowHeight(40);
        tbl3.setSelectionBackground(new java.awt.Color(255, 204, 102));
        tbl3.setSelectionForeground(new java.awt.Color(51, 51, 51));
        tbl3.setShowGrid(false);
        jScrollPane3.setViewportView(tbl3);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1065, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("tab3", jPanel5);

        jPanel6.setBackground(new java.awt.Color(230, 233, 238));

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));

        jLabel56.setFont(new java.awt.Font("Manrope ExtraBold", 0, 18)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(0, 0, 0));
        jLabel56.setText("Quiz List");

        searchQlist.setBackground(new java.awt.Color(230, 233, 238));
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

        addQuiz.setBackground(new java.awt.Color(255, 255, 255));
        addQuiz.setFont(new java.awt.Font("Manrope", 0, 14)); // NOI18N
        addQuiz.setForeground(new java.awt.Color(0, 0, 0));
        addQuiz.setText("Add New");
        addQuiz.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(253, 225, 8), 1, true));
        addQuiz.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addQuizMouseClicked(evt);
            }
        });
        addQuiz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addQuizActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 476, Short.MAX_VALUE)
                .addComponent(searchQlist, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(addQuiz, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel56, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(searchQlist)
                    .addComponent(addQuiz, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));

        jPanel24.setBackground(new java.awt.Color(204, 204, 204));
        jPanel24.setForeground(new java.awt.Color(51, 51, 51));

        addQuizPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Manrope", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Add New Quiz");

        jLabel12.setFont(new java.awt.Font("Manrope", 1, 11)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setText("Program");

        jLabel19.setFont(new java.awt.Font("Manrope", 1, 11)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(51, 51, 51));
        jLabel19.setText("Year and Section");

        jLabel22.setFont(new java.awt.Font("Manrope", 1, 11)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(51, 51, 51));
        jLabel22.setText("Faculty");

        jSeparator1.setForeground(new java.awt.Color(102, 102, 102));

        jButton2.setBackground(new java.awt.Color(255, 233, 0));
        jButton2.setFont(new java.awt.Font("Manrope", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(51, 51, 51));
        jButton2.setText("Add this Quiz");
        jButton2.setBorder(null);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Manrope", 1, 11)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(51, 51, 51));
        jLabel31.setText("Title");

        title1.setBackground(new java.awt.Color(255, 255, 255));
        title1.setFont(new java.awt.Font("Manrope", 0, 11)); // NOI18N
        title1.setForeground(new java.awt.Color(51, 51, 51));
        title1.setActionCommand("<Not Set>");
        title1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));

        jLabel32.setFont(new java.awt.Font("Manrope", 1, 11)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(51, 51, 51));
        jLabel32.setText("Points per item");

        pointper1.setBackground(new java.awt.Color(255, 255, 255));
        pointper1.setFont(new java.awt.Font("Manrope", 0, 11)); // NOI18N
        pointper1.setForeground(new java.awt.Color(51, 51, 51));
        pointper1.setActionCommand("<Not Set>");
        pointper1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));

        jLabel33.setFont(new java.awt.Font("Manrope", 1, 11)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(51, 51, 51));
        jLabel33.setText("Subject");

        qprogram.setBackground(new java.awt.Color(255, 255, 255));
        qprogram.setFont(new java.awt.Font("Manrope", 0, 11)); // NOI18N
        qprogram.setForeground(new java.awt.Color(51, 51, 51));
        qprogram.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        qprogram.setFocusable(false);
        qprogram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qprogramActionPerformed(evt);
            }
        });

        qsection.setBackground(new java.awt.Color(255, 255, 255));
        qsection.setFont(new java.awt.Font("Manrope", 0, 11)); // NOI18N
        qsection.setForeground(new java.awt.Color(51, 51, 51));
        qsection.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        qsection.setFocusable(false);
        qsection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qsectionActionPerformed(evt);
            }
        });

        qsubject.setBackground(new java.awt.Color(255, 255, 255));
        qsubject.setFont(new java.awt.Font("Manrope", 0, 11)); // NOI18N
        qsubject.setForeground(new java.awt.Color(51, 51, 51));
        qsubject.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        qsubject.setFocusable(false);
        qsubject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qsubjectActionPerformed(evt);
            }
        });

        qfaculty.setBackground(new java.awt.Color(255, 255, 255));
        qfaculty.setFont(new java.awt.Font("Manrope", 0, 11)); // NOI18N
        qfaculty.setForeground(new java.awt.Color(51, 51, 51));
        qfaculty.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        qfaculty.setFocusable(false);
        qfaculty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qfacultyActionPerformed(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Manrope", 1, 11)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(51, 51, 51));
        jLabel34.setText("Time Limit(in minutes)");

        timelimit.setBackground(new java.awt.Color(255, 255, 255));
        timelimit.setFont(new java.awt.Font("Manrope", 0, 11)); // NOI18N
        timelimit.setForeground(new java.awt.Color(51, 51, 51));
        timelimit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        timelimit.setFocusable(false);
        timelimit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timelimitActionPerformed(evt);
            }
        });

        jPanel26.setBackground(new java.awt.Color(253, 225, 8));
        jPanel26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel26MouseClicked(evt);
            }
        });

        jLabel27.setBackground(new java.awt.Color(253, 225, 8));
        jLabel27.setFont(new java.awt.Font("Manrope ExtraBold", 0, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(51, 51, 51));
        jLabel27.setText(" <");

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel27)
                .addGap(23, 23, 23))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout addQuizPanelLayout = new javax.swing.GroupLayout(addQuizPanel);
        addQuizPanel.setLayout(addQuizPanelLayout);
        addQuizPanelLayout.setHorizontalGroup(
            addQuizPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(addQuizPanelLayout.createSequentialGroup()
                .addGroup(addQuizPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addQuizPanelLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(addQuizPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel34)
                            .addComponent(jLabel32)
                            .addComponent(qsection, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel19)
                            .addComponent(jLabel31)
                            .addComponent(title1)
                            .addComponent(jLabel33)
                            .addComponent(qprogram, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pointper1)
                            .addComponent(qsubject, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel22)
                            .addComponent(qfaculty, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                            .addComponent(timelimit, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(addQuizPanelLayout.createSequentialGroup()
                        .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(123, 123, 123)
                        .addComponent(jLabel1)))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        addQuizPanelLayout.setVerticalGroup(
            addQuizPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addQuizPanelLayout.createSequentialGroup()
                .addGroup(addQuizPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(addQuizPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(qprogram, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(qsection, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel33)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(qsubject, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(title1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(timelimit, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pointper1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(qfaculty, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addQuizPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addQuizPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tbl4.setFont(new java.awt.Font("Manrope", 0, 14)); // NOI18N
        tbl4.setForeground(new java.awt.Color(25, 49, 83));
        tbl4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "#", "Subject", "Quiz Title", "Points per Item", "Faculty"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl4.setFocusable(false);
        tbl4.setRowHeight(40);
        tbl4.setSelectionBackground(new java.awt.Color(255, 204, 102));
        jScrollPane5.setViewportView(tbl4);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(300, 300, 300)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 1065, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
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
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("tab4", jPanel6);

        jPanel7.setBackground(new java.awt.Color(230, 233, 238));

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));

        jLabel57.setFont(new java.awt.Font("Manrope ExtraBold", 0, 18)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(0, 0, 0));
        jLabel57.setText("Quiz Record");

        searchQrec.setBackground(new java.awt.Color(230, 233, 238));
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

        comboQRec.setBackground(new java.awt.Color(255, 255, 255));
        comboQRec.setFont(new java.awt.Font("Manrope", 0, 12)); // NOI18N
        comboQRec.setForeground(new java.awt.Color(51, 51, 51));
        comboQRec.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All" }));
        comboQRec.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(253, 225, 8), 1, true));
        comboQRec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboQRecActionPerformed(evt);
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
                .addGap(31, 31, 31)
                .addComponent(comboQRec, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(comboQRec)
                    .addComponent(searchQrec))
                .addContainerGap())
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
                "#", "Name of Student", "Subject", "Quiz", "Score", "Grade"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
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
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
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
                .addContainerGap(68, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("tab5", jPanel7);

        jPanel8.add(jTabbedPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, 1160, 640));

        jPanel2.setBackground(new java.awt.Color(25, 49, 83));
        jPanel2.setToolTipText("");
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
                .addGap(32, 32, 32)
                .addComponent(jLabel13)
                .addContainerGap(118, Short.MAX_VALUE))
        );
        tab1Layout.setVerticalGroup(
            tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel4)))
        );

        jPanel2.add(tab1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 300, 50));

        tab2.setBackground(new java.awt.Color(25, 49, 83));
        tab2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                tab2MouseDragged(evt);
            }
        });
        tab2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tab2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                tab2MouseExited(evt);
            }
        });

        jLabel14.setBackground(new java.awt.Color(51, 51, 51));
        jLabel14.setFont(new java.awt.Font("Manrope SemiBold", 0, 13)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("FACULTY");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/fac.png"))); // NOI18N

        javax.swing.GroupLayout tab2Layout = new javax.swing.GroupLayout(tab2);
        tab2.setLayout(tab2Layout);
        tab2Layout.setHorizontalGroup(
            tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab2Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(jLabel5)
                .addGap(33, 33, 33)
                .addComponent(jLabel14)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tab2Layout.setVerticalGroup(
            tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab2Layout.createSequentialGroup()
                .addGroup(tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tab2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel14))
                    .addGroup(tab2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(tab2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 300, -1));

        tab3.setBackground(new java.awt.Color(25, 49, 83));
        tab3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tab3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                tab3MouseExited(evt);
            }
        });

        jLabel15.setBackground(new java.awt.Color(51, 51, 51));
        jLabel15.setFont(new java.awt.Font("Manrope SemiBold", 0, 13)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("STUDENT LIST");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/cap.png"))); // NOI18N
        jLabel6.setText("o");

        javax.swing.GroupLayout tab3Layout = new javax.swing.GroupLayout(tab3);
        tab3.setLayout(tab3Layout);
        tab3Layout.setHorizontalGroup(
            tab3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab3Layout.createSequentialGroup()
                .addContainerGap(72, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel15)
                .addGap(69, 69, 69))
        );
        tab3Layout.setVerticalGroup(
            tab3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tab3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel15))
                .addContainerGap())
        );

        jPanel2.add(tab3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 300, 50));

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

        jPanel2.add(tab5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 440, 300, -1));

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
                .addContainerGap(92, Short.MAX_VALUE))
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

        jPanel2.add(tab4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, 300, 50));

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
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 622, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(Home, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 622, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        jTabbedPane2.setSelectedIndex(3);
    }//GEN-LAST:event_tab4MouseClicked

    private void tab5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab5MouseExited
       tab5.setBackground(new Color(25,49,83));
    }//GEN-LAST:event_tab5MouseExited

    private void tab5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab5MouseEntered
        tab5.setBackground(new Color(70,130,180));
    }//GEN-LAST:event_tab5MouseEntered

    private void tab5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab5MouseClicked
        jTabbedPane2.setSelectedIndex(4);
    }//GEN-LAST:event_tab5MouseClicked

    private void tab3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab3MouseExited
       tab3.setBackground(new Color(25,49,83));
    }//GEN-LAST:event_tab3MouseExited

    private void tab3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab3MouseEntered
       tab3.setBackground(new Color(70,130,180));
    }//GEN-LAST:event_tab3MouseEntered

    private void tab3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab3MouseClicked
        jTabbedPane2.setSelectedIndex(2);
    }//GEN-LAST:event_tab3MouseClicked

    private void tab2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab2MouseExited
        tab2.setBackground(new Color(25,49,83));
    }//GEN-LAST:event_tab2MouseExited

    private void tab2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab2MouseEntered
        tab2.setBackground(new Color(70,130,180));
    }//GEN-LAST:event_tab2MouseEntered

    private void tab2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab2MouseClicked
        jTabbedPane2.setSelectedIndex(1);
    }//GEN-LAST:event_tab2MouseClicked

    private void tab2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab2MouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_tab2MouseDragged

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

    private void addFacultyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFacultyActionPerformed
        SignUp su = new SignUp();
        
        su.setVisible(true);
        dispose();
    }//GEN-LAST:event_addFacultyActionPerformed

    private void searchfacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchfacActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchfacActionPerformed

    private void searchStudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchStudActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchStudActionPerformed

    private void addFaculty1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFaculty1ActionPerformed
        SignUp su = new SignUp();
        
        su.setVisible(true);
        dispose();
    }//GEN-LAST:event_addFaculty1ActionPerformed

    private void searchQlistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchQlistActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchQlistActionPerformed

    private void addQuizActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addQuizActionPerformed
            jPanel24.setVisible(true);
            jPanel24.requestFocus();
            
    }//GEN-LAST:event_addQuizActionPerformed
  

    private void searchQrecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchQrecActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchQrecActionPerformed

    private void searchfacFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchfacFocusGained
        
    }//GEN-LAST:event_searchfacFocusGained

    private void searchfacFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchfacFocusLost
        
    }//GEN-LAST:event_searchfacFocusLost

    private void searchStudFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchStudFocusGained
       
    }//GEN-LAST:event_searchStudFocusGained

    private void searchStudFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchStudFocusLost
        
    }//GEN-LAST:event_searchStudFocusLost

    private void searchQlistFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchQlistFocusGained
      
    }//GEN-LAST:event_searchQlistFocusGained

    private void searchQlistFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchQlistFocusLost
       
    }//GEN-LAST:event_searchQlistFocusLost

    private void searchQrecFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchQrecFocusGained
       
    }//GEN-LAST:event_searchQrecFocusGained

    private void searchQrecFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchQrecFocusLost
        
    }//GEN-LAST:event_searchQrecFocusLost

    private void searchQrecMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchQrecMouseClicked
        searchQrec.setText("");
    }//GEN-LAST:event_searchQrecMouseClicked

    private void searchQrecKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchQrecKeyReleased
        String sEarch = searchQrec.getText();
        try {
            String query = "SELECT * FROM quiz_record WHERE no LIKE '%" + sEarch + "%' OR studName LIKE '%" + sEarch + "%' OR quiz LIKE '%" + sEarch + "%' OR score LIKE '%" + sEarch + "%'";
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();
            tbl5.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
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

    private void searchfacMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchfacMouseClicked
        searchfac.setText("");
    }//GEN-LAST:event_searchfacMouseClicked

    private void searchfacKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchfacKeyReleased
        String sEarch = searchfac.getText();
        try {
            String query = "SELECT firstname, lastname, sex, subject FROM faculty_list WHERE firstname LIKE '%" + sEarch + "%' OR lastname LIKE '%" + sEarch + "%' OR sex LIKE '%" + sEarch + "%' OR subject LIKE '%" + sEarch + "%'";
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();
            tbl2.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
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
    }//GEN-LAST:event_searchfacKeyReleased

    private void searchStudMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchStudMouseClicked
        searchStud.setText("");
    }//GEN-LAST:event_searchStudMouseClicked

    private void searchStudKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchStudKeyReleased

    String sEarch = searchStud.getText();
    try {
        String query = "SELECT firstname, lastname, sex, sNumber, sProg, sSec, status FROM reg_student WHERE firstname LIKE '%" + sEarch + "%' OR lastname LIKE '%" + sEarch + "%' OR sex LIKE '%" + sEarch + "%' OR sNumber LIKE '%" + sEarch + "%' OR sProg LIKE '%" + sEarch + "%' OR sSec LIKE '%" + sEarch + "%' OR status LIKE '%" + sEarch + "%'";
        pst = con.prepareStatement(query);
        rs = pst.executeQuery();

        // Convert the ResultSet to a DefaultTableModel
        DefaultTableModel model = new DefaultTableModel();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        // Add column names to the model
        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
            model.addColumn(metaData.getColumnLabel(columnIndex));
        }

        // Add data rows to the model
        while (rs.next()) {
            Object[] rowData = new Object[columnCount];
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                rowData[columnIndex - 1] = rs.getObject(columnIndex);
            }
            model.addRow(rowData);
        }

        // Set the model to the table
        tbl3.setModel(model);
    } catch (SQLException ex) {
        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
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

    }//GEN-LAST:event_searchStudKeyReleased

    private void searchQlistMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchQlistMouseClicked
        searchQlist.setText("");
    }//GEN-LAST:event_searchQlistMouseClicked

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

    private void comboQRecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboQRecActionPerformed
        
    }//GEN-LAST:event_comboQRecActionPerformed

    private void jPanel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseClicked
        jTabbedPane2.setSelectedIndex(1);
    }//GEN-LAST:event_jPanel9MouseClicked

    private void jPanel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel13MouseClicked
        jTabbedPane2.setSelectedIndex(2);
    }//GEN-LAST:event_jPanel13MouseClicked

    private void jPanel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel12MouseClicked
        jTabbedPane2.setSelectedIndex(4);
    }//GEN-LAST:event_jPanel12MouseClicked

    private void addQuizMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addQuizMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_addQuizMouseClicked

    private void timelimitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timelimitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_timelimitActionPerformed

    private void qfacultyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qfacultyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_qfacultyActionPerformed

    private void qsubjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qsubjectActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_qsubjectActionPerformed

    private void qsectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qsectionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_qsectionActionPerformed

    private void qprogramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qprogramActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_qprogramActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        String selectedProgram = qprogram.getSelectedItem().toString();
        String selectedSection = qsection.getSelectedItem().toString();
        String selectedSubject = qsubject.getSelectedItem().toString();
        String selectedTimeLimit = timelimit.getSelectedItem().toString();

        String qTitle = title1.getText();
        String qPtPerItm = pointper1.getText();
        String selectedFaculty = qfaculty.getSelectedItem().toString();

        try {
        // Retrieve only the usernames from the reg_student table for the selected program and section
        PreparedStatement pstUsername = con.prepareStatement("SELECT username FROM reg_student WHERE sProg = ? AND sSec = ?");
        pstUsername.setString(1, selectedProgram);
        pstUsername.setString(2, selectedSection);
        ResultSet rsUsername = pstUsername.executeQuery();

            // Prepare the SQL statement
            PreparedStatement pst = con.prepareStatement("INSERT INTO quiz_list (program, yrSection, subject, title, pointsperitem, timelimit, faculty, username, completion_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

            // Create an instance of Questions_Form outside the loop
            Questions_Form qform = new Questions_Form();

            while (rsUsername.next()) {
                String studentUsername = rsUsername.getString("username");

                // Set the values for the SQL statement
                pst.setString(1, selectedProgram);
                pst.setString(2, selectedSection);
                pst.setString(3, selectedSubject);
                pst.setString(4, qTitle);
                pst.setString(5, qPtPerItm);
                pst.setInt(6, Integer.parseInt(selectedTimeLimit));
                pst.setString(7, selectedFaculty);
                pst.setString(8, studentUsername);
                pst.setString(9, "Not Yet Taken"); // Set the default completion status

                // Execute the SQL statement
                int k = pst.executeUpdate();

                if (k == 1) {
                    // Successful insertion
                    title1.setText("");
                    pointper1.setText("");
                    Fetch(userName);
                    DashBoardStud dash = new DashBoardStud(userName);
                    dash.FetchStud(userName);
                    jPanel24.setVisible(false);

                    // Open the Questions_Form only once
                    if (!qform.isVisible()) {
                        qform.setVisible(true);
                    }
                } else {
                    // Error during insertion
                    JOptionPane.showMessageDialog(this, "Invalid Input");
                }
            }

            // Close the result set and statements
            rsUsername.close();
            pstUsername.close();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(addQuiz.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jPanel26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel26MouseClicked
       jPanel24.setVisible(false);


    }//GEN-LAST:event_jPanel26MouseClicked

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
            java.util.logging.Logger.getLogger(DashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Home;
    private javax.swing.JButton addFaculty;
    private javax.swing.JButton addFaculty1;
    private javax.swing.JButton addQuiz;
    public javax.swing.JPanel addQuizPanel;
    private javax.swing.JComboBox<String> comboQRec;
    private javax.swing.JLabel date;
    private javax.swing.JLabel day;
    private javax.swing.JLabel fullname;
    public javax.swing.JLabel greetingLabel;
    private javax.swing.JButton jButton2;
    private javax.swing.JEditorPane jEditorPane1;
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel6;
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
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel pfpman;
    private javax.swing.JLabel pfpwoman;
    private javax.swing.JTextField pointper1;
    private javax.swing.JComboBox<String> qfaculty;
    private javax.swing.JComboBox<String> qprogram;
    private javax.swing.JComboBox<String> qsection;
    private javax.swing.JComboBox<String> qsubject;
    private javax.swing.JLabel sProgSec;
    private javax.swing.JTextField searchQlist;
    private javax.swing.JTextField searchQrec;
    private javax.swing.JTextField searchStud;
    private javax.swing.JTextField searchfac;
    public javax.swing.JLabel storeUsernames;
    private javax.swing.JPanel tab1;
    private javax.swing.JPanel tab2;
    private javax.swing.JPanel tab3;
    private javax.swing.JPanel tab4;
    private javax.swing.JPanel tab5;
    public javax.swing.JLabel tag1;
    public javax.swing.JLabel tag2;
    private javax.swing.JLabel tag3;
    private javax.swing.JTable tbl0;
    private javax.swing.JTable tbl2;
    private javax.swing.JTable tbl3;
    private javax.swing.JTable tbl4;
    private javax.swing.JTable tbl5;
    private javax.swing.JComboBox<String> timelimit;
    private javax.swing.JTextField title1;
    // End of variables declaration//GEN-END:variables

   

    
}
