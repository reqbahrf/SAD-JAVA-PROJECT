/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sad_pos_toppings_express;

import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author reqba
 */
public class Admin_Panel extends javax.swing.JFrame {
    
    POS_MAIN pm = new POS_MAIN();
    private POS_MAIN frame = null;
    
    /**
     * Creates new form Admin_Panel
     */
    public Admin_Panel() {
        
        initComponents();
        connectDb();
        toC();
        show_date();
        show_time();
        Connect();
        connectP();
    }
    
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
     void show_date(){
        java.util.Date d = new java.util.Date();
        SimpleDateFormat s = new SimpleDateFormat("YYYY-MM-dd");
        txt_d.setText(" "+ s.format(d));
        
       
        
    }
    void show_time(){
       new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                  java.util.Date d = new java.util.Date();
                  SimpleDateFormat s = new SimpleDateFormat("hh:mm:ss a");
                  txt_t.setText(" " + s.format(d));
              
            }
        }).start();
       
    }
    
     public void connectDb() {

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Toppings_Express_db", "root", "");
            String sql = "SELECT Item, SUM(Qty) AS TotalQty, SUM(Price) AS TotalPrice FROM Sold_Ps GROUP BY Item";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) TopS.getModel();
            model.setRowCount(0); // clear the table before adding new rows

            while (rs.next()) {
                String itemName = rs.getString("Item");
                int totalQty = rs.getInt("TotalQty");
                BigDecimal totalPrice = rs.getBigDecimal("TotalPrice");
                Object[] row = { itemName, totalQty, totalPrice };
                model.addRow(row);
            }

            // create a TableRowSorter and set it as the sorter for the JTable
            TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
            TopS.setRowSorter(sorter);

            // specify the column to sort by (TotalQty column) and the sort order (descending)
            sorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(1, SortOrder.DESCENDING)));

            // perform the sort
            sorter.sort();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
}
     
    public void toC(){
    
    
        int numOfRow = TopS.getRowCount();
        double tot = 0.0;
        
        for (int i = 0; i < numOfRow; i++){
           
            double value = Double.valueOf(TopS.getValueAt(i, 2).toString());
            tot += value; 
        
        }
       DecimalFormat df = new DecimalFormat("00.00") ;
       TCal.setText(df.format(tot));
       
}
    
     public void Connect(){
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Toppings_Express_db","root","");
                String sql = "select * from Registered_User";
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                user_L.setModel(DbUtils.resultSetToTableModel(rs));
                
                while(rs.next()){
                
                    String id = String.valueOf(rs.getInt("id"));
                    String name = rs.getString("Name");
                    String UName = rs.getString("User_Name");
                    String pword = rs.getString("Password");
                    String UType = rs.getString("User_Type");
                
                    
                    String tb[] = {id, name, UName, pword, UType};
                    DefaultTableModel t = (DefaultTableModel)user_L.getModel();
                    
                    t.addRow(tb);
                }
                
                
                
            } catch (Exception e) {
                 JOptionPane.showMessageDialog(null, e);
                
            }
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, e);
        }
    
    
    
    }
     
     public void connectP(){
         
         try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Toppings_Express_db","root","");
                String sql = "select * from Products";
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                T_Product.setModel(DbUtils.resultSetToTableModel(rs));
                
                while(rs.next()){
                
                    String id = String.valueOf(rs.getInt("ID"));
                    String Product_name = rs.getString("Product_Name");
                    String Price = rs.getString("Price");
                    int AStock = rs.getInt("Available_Stock");
                    
                
                    
                    String tb[] = {id, Product_name, Price};
                    DefaultTableModel t = (DefaultTableModel)T_Product.getModel();
                    
                    t.addRow(tb);
                }
                
                
                
            } catch (Exception e) {
                 JOptionPane.showMessageDialog(null, e);
                
            }
        } catch (Exception e) {
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

        jPanel24 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        txt_d = new javax.swing.JLabel();
        txt_t = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TopS = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        TCal = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        SR = new javax.swing.JTextArea();
        jButton30 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        user_L = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        t_search = new javax.swing.JTextField();
        tName = new javax.swing.JTextField();
        tUName = new javax.swing.JTextField();
        tPass = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        User_A = new javax.swing.JComboBox<>();
        jPanel17 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        SearchU = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        T_Product = new javax.swing.JTable();
        jPanel14 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        P_ID = new javax.swing.JTextField();
        P_Name = new javax.swing.JTextField();
        P_Price = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        Available_S = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        SearchP = new javax.swing.JTextField();
        jButton29 = new javax.swing.JButton();
        jButton31 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Admin");
        setSize(new java.awt.Dimension(1195, 763));

        jPanel24.setBackground(new java.awt.Color(204, 0, 0));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTabbedPane1.setFont(new java.awt.Font("Unispace", 0, 12)); // NOI18N

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.orange, java.awt.Color.orange, java.awt.Color.orange, java.awt.Color.orange));
        jPanel1.setForeground(new java.awt.Color(60, 63, 65));

        jPanel4.setBackground(new java.awt.Color(255, 153, 0));

        jLabel1.setBackground(new java.awt.Color(153, 153, 153));
        jLabel1.setFont(new java.awt.Font("Unispace", 1, 48)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationIcon/icons8_combo_chart_80px.png"))); // NOI18N
        jLabel1.setText("Dashboard");

        jPanel19.setBackground(java.awt.Color.black);
        jPanel19.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txt_d.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        txt_d.setForeground(new java.awt.Color(255, 255, 255));
        txt_d.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationIcon/icons8_calendar_35px.png"))); // NOI18N

        txt_t.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        txt_t.setForeground(new java.awt.Color(255, 255, 255));
        txt_t.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationIcon/icons8_clock_35px.png"))); // NOI18N

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_d, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txt_t, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_d)
                    .addComponent(txt_t))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Unispace", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Sales: ");

        TopS.setFont(new java.awt.Font("DialogInput", 0, 12)); // NOI18N
        TopS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item", "Total Qty Sold", "Total Price"
            }
        ));
        TopS.setShowHorizontalLines(true);
        TopS.setShowVerticalLines(true);
        jScrollPane1.setViewportView(TopS);

        jPanel5.setBackground(new java.awt.Color(0, 0, 0));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Total Income:");

        TCal.setBackground(new java.awt.Color(51, 255, 0));
        TCal.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        TCal.setForeground(new java.awt.Color(0, 255, 102));
        TCal.setText("00.00");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(417, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TCal, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(TCal))
                .addGap(16, 16, 16))
        );

        jPanel10.setBackground(new java.awt.Color(255, 153, 0));

        jButton2.setBackground(java.awt.Color.black);
        jButton2.setFont(new java.awt.Font("Unispace", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationIcon/icons8_report_card_50px.png"))); // NOI18N
        jButton2.setText("SALES REPORT");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(java.awt.Color.black);
        jButton3.setFont(new java.awt.Font("Unispace", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationIcon/icons8_send_to_printer_50px.png"))); // NOI18N
        jButton3.setText("PRINT");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jPanel9.setBackground(new java.awt.Color(204, 0, 0));

        SR.setColumns(20);
        SR.setFont(new java.awt.Font("DialogInput", 0, 12)); // NOI18N
        SR.setRows(5);
        jScrollPane2.setViewportView(SR);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(103, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jButton30.setBackground(java.awt.Color.black);
        jButton30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationIcon/icons8_Refresh_25px.png"))); // NOI18N
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addGap(12, 12, 12)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("DASHBOARD", jPanel1);

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.orange, java.awt.Color.orange, java.awt.Color.orange, java.awt.Color.orange));

        jPanel6.setBackground(new java.awt.Color(255, 153, 0));

        jLabel4.setBackground(new java.awt.Color(153, 153, 153));
        jLabel4.setFont(new java.awt.Font("Unispace", 1, 48)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationIcon/icons8_User_Folder_80px.png"))); // NOI18N
        jLabel4.setText("Manage Users");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(739, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, Short.MAX_VALUE)
                .addContainerGap())
        );

        user_L.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "User Name", "Password", "User Type"
            }
        ));
        jScrollPane3.setViewportView(user_L);

        jPanel8.setBackground(new java.awt.Color(255, 153, 0));
        jPanel8.setForeground(new java.awt.Color(204, 0, 0));

        jLabel5.setFont(new java.awt.Font("Unispace", 1, 24)); // NOI18N
        jLabel5.setText("ID:");

        jLabel9.setFont(new java.awt.Font("Unispace", 1, 24)); // NOI18N
        jLabel9.setText("Name:");

        jLabel8.setFont(new java.awt.Font("Unispace", 1, 24)); // NOI18N
        jLabel8.setText("User Name:");

        jLabel6.setFont(new java.awt.Font("Unispace", 1, 24)); // NOI18N
        jLabel6.setText("Password:");

        t_search.setFont(new java.awt.Font("Unispace", 0, 14)); // NOI18N
        t_search.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        tName.setFont(new java.awt.Font("Unispace", 0, 14)); // NOI18N
        tName.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        tName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tNameActionPerformed(evt);
            }
        });

        tUName.setFont(new java.awt.Font("Unispace", 0, 14)); // NOI18N
        tUName.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        tUName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tUNameActionPerformed(evt);
            }
        });

        tPass.setFont(new java.awt.Font("Unispace", 0, 14)); // NOI18N
        tPass.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        tPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tPassActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Unispace", 1, 24)); // NOI18N
        jLabel7.setText("Access Type:");

        User_A.setFont(new java.awt.Font("Unispace", 1, 18)); // NOI18N
        User_A.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Owner", "Employee" }));

        jPanel17.setBackground(new java.awt.Color(204, 0, 0));

        jButton1.setBackground(java.awt.Color.black);
        jButton1.setFont(new java.awt.Font("Unispace", 0, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationIcon/icons8_Add_Male_User_Group_42px.png"))); // NOI18N
        jButton1.setText("ADD");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton4.setBackground(java.awt.Color.black);
        jButton4.setFont(new java.awt.Font("Unispace", 0, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationIcon/icons8_change_user_42px.png"))); // NOI18N
        jButton4.setText("UPDATE");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        delete.setBackground(java.awt.Color.black);
        delete.setFont(new java.awt.Font("Unispace", 0, 12)); // NOI18N
        delete.setForeground(new java.awt.Color(255, 255, 255));
        delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationIcon/icons8_delete_user_male_42px.png"))); // NOI18N
        delete.setText("DELETE");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });

        jButton5.setBackground(java.awt.Color.black);
        jButton5.setFont(new java.awt.Font("Unispace", 0, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationIcon/icons8_edit_35px.png"))); // NOI18N
        jButton5.setText("SELECT");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jButton4)
                .addGap(18, 18, 18)
                .addComponent(delete)
                .addGap(11, 11, 11))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap(7, Short.MAX_VALUE)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(delete, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tPass, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tName, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel5)
                            .addComponent(jLabel9)
                            .addComponent(jLabel6)
                            .addComponent(tUName, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(t_search, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(User_A, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(t_search, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tName, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tUName, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tPass, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(User_A, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel13.setBackground(new java.awt.Color(204, 0, 0));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 39, Short.MAX_VALUE)
        );

        jPanel15.setBackground(new java.awt.Color(204, 0, 0));

        jLabel15.setFont(new java.awt.Font("Unispace", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Search User:");

        SearchU.setFont(new java.awt.Font("Unispace", 0, 12)); // NOI18N
        SearchU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchUActionPerformed(evt);
            }
        });
        SearchU.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                SearchUKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SearchU, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel15))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(SearchU, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Manage Users", jPanel2);

        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.orange, java.awt.Color.orange, java.awt.Color.orange, java.awt.Color.orange));

        jPanel11.setBackground(new java.awt.Color(255, 153, 0));

        jLabel10.setBackground(new java.awt.Color(153, 153, 153));
        jLabel10.setFont(new java.awt.Font("Unispace", 1, 48)); // NOI18N
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationIcon/icons8_add_shopping_cart_80px.png"))); // NOI18N
        jLabel10.setText("Manage Products");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0)));
        jTabbedPane2.setFont(new java.awt.Font("Unispace", 0, 12)); // NOI18N

        T_Product.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Product Name", "Price", "Available_Stock"
            }
        ));
        jScrollPane4.setViewportView(T_Product);

        jPanel14.setBackground(new java.awt.Color(255, 153, 0));

        jLabel11.setFont(new java.awt.Font("Unispace", 1, 24)); // NOI18N
        jLabel11.setText("ID:");

        jLabel12.setFont(new java.awt.Font("Unispace", 1, 24)); // NOI18N
        jLabel12.setText("Product Name:");

        jLabel13.setFont(new java.awt.Font("Unispace", 1, 24)); // NOI18N
        jLabel13.setText("Price:");

        jPanel16.setBackground(new java.awt.Color(204, 0, 0));

        jButton6.setBackground(java.awt.Color.black);
        jButton6.setFont(new java.awt.Font("Unispace", 0, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationIcon/icons8_add_product_42px.png"))); // NOI18N
        jButton6.setText("ADD");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setBackground(java.awt.Color.black);
        jButton7.setFont(new java.awt.Font("Unispace", 0, 12)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationIcon/icons8_available_updates_42px.png"))); // NOI18N
        jButton7.setText("UPDATE");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setBackground(java.awt.Color.black);
        jButton8.setFont(new java.awt.Font("Unispace", 0, 12)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationIcon/icons8_clear_shopping_cart_42px.png"))); // NOI18N
        jButton8.setText("DELETE");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setBackground(java.awt.Color.black);
        jButton9.setFont(new java.awt.Font("Unispace", 0, 12)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationIcon/icons8_edit_35px.png"))); // NOI18N
        jButton9.setText("SELECT");
        jButton9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addContainerGap(16, Short.MAX_VALUE)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(21, 21, 21))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        P_ID.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        P_Name.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        P_Price.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        P_Price.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                P_PriceActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Unispace", 1, 24)); // NOI18N
        jLabel14.setText("Stock:");

        Available_S.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Available_S.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Available_SActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13)
                        .addComponent(P_Price, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                        .addComponent(P_Name)
                        .addComponent(P_ID)
                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Available_S)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(P_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(P_Name, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(P_Price, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Available_S, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        jPanel18.setBackground(new java.awt.Color(204, 0, 0));

        jLabel16.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Search Product:");

        SearchP.setFont(new java.awt.Font("Unispace", 0, 10)); // NOI18N
        SearchP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                SearchPKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SearchP, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(SearchP))
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 787, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4)))
                .addGap(0, 34, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Product ", jPanel12);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 578, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Manage Products", jPanel3);

        jButton29.setBackground(new java.awt.Color(0, 0, 0));
        jButton29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationIcon/icons8_Logout_25px.png"))); // NOI18N
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });

        jButton31.setBackground(java.awt.Color.black);
        jButton31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationIcon/icons8_cash_register_25px.png"))); // NOI18N
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 688, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 30, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // Delete

        int y = JOptionPane.showConfirmDialog(null, "Printing will result in deleting data in database","PRINT REPORT",JOptionPane.YES_NO_OPTION);

        if(y == 0){
            try{
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Toppings_Express_db","root","");
                String sql = "TRUNCATE TABLE Sold_Ps";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.executeUpdate(sql);
                System.out.println("Table Cleared");
                ps.close();
                con.close();
                connectDb();
                toC();
            }catch(SQLException ex){

            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        String current_Date = txt_t.getText();
        String current_Time = txt_d.getText();
        String TotalInc = TCal.getText();

        try {
            SR.setText("                                 TOPPING'S EXPRESS\n");
            SR.setText(SR.getText() +"                                     SALES REPORT\n");
            SR.setText(SR.getText() +"                  Report Generated: "  + current_Date +""+ current_Time +"\n");
             SR.setText(SR.getText() + "---------------------------------------------------------------------------\n");
    SR.setText(SR.getText() + "STOCK REPORT\n");
    SR.setText(SR.getText() + "---------------------------------------------------------------------------\n");
    SR.setText(SR.getText() + "Product Name\t\t\t\t Remaining Stock \t " +"\n");
    SR.setText(SR.getText() + "---------------------------------------------------------------------------\n");
    Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Toppings_Express_db","root","");
    String sql1 = "SELECT Product_Name, Available_Stock FROM Products";
    PreparedStatement ps1 = con.prepareStatement(sql1);
    ResultSet rs1 = ps1.executeQuery();
    
    while (rs1.next()) {
        String productName = rs1.getString("Product_Name");
        int availableStock = rs1.getInt("Available_Stock");

        SR.setText(SR.getText() + productName + "\t\t\t\t   " + availableStock + "\n");
    }
            SR.setText(SR.getText() + "---------------------------------------------------------------------------\n");
            SR.setText(SR.getText() + "SOLD PRODUCT REPORT\n");
            SR.setText(SR.getText() + "---------------------------------------------------------------------------\n");
            SR.setText(SR.getText() + "Product Name\t\t\t Stock_sold \t\t TotalPrice" +"\n");
            SR.setText(SR.getText() + "---------------------------------------------------------------------------\n");

           
            String sql = "SELECT Item, SUM(Qty) AS TotalQty, SUM(Price) AS TotalPrice FROM Sold_Ps GROUP BY Item";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String item = rs.getString("Item");
                int totalQty = rs.getInt("TotalQty");
                BigDecimal totalPrice = rs.getBigDecimal("TotalPrice");

                SR.setText(SR.getText() + item + "\t\t\t\t   " + totalQty + "\t\t\t\t   " + totalPrice.toString() +"\n");
            }

            
    
    
    SR.setText(SR.getText() + "---------------------------------------------------------------------------\n");
    SR.setText(SR.getText() + "                                                   Total Income:" + TotalInc + "\n");
                                                   

    
   


        } catch (Exception e) {
            DefaultTableModel tb = (DefaultTableModel) TopS.getModel();
            if (tb.getRowCount() == 0){
                JOptionPane.showMessageDialog(this, "Table is empty", "Notice", JOptionPane.INFORMATION_MESSAGE);
            }
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void tPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tPassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tPassActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        int y = JOptionPane.showConfirmDialog(null, "Are you sure you want to add this user?","Add new user",JOptionPane.YES_NO_OPTION);
        if(y == 0 ){

            try {

               
                String Name = tName.getText();
                String User_Name = tUName.getText();
                String Password = tPass.getText();
                String user_type = User_A.getSelectedItem().toString();

                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Toppings_Express_db","root","");
                PreparedStatement ps = con.prepareStatement("INSERT INTO `Registered_User` ( `Name`, `User_Name`, `Password`, `User_Type`) VALUES (?,?,?,?)");

                
                ps.setString(1, Name);
                ps.setString(2, User_Name);
                ps.setString(3, Password);
                ps.setString(4, user_type);
                ps.execute();
                JOptionPane.showMessageDialog(null,"Data is saved successfully");
                Connect();

            } catch (Exception e) {
            }

        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // Update
        int y = JOptionPane.showConfirmDialog(null, "Are you sure you want update this user?","Update user",JOptionPane.YES_NO_OPTION);

        if(y ==0){
            try {
                String value = t_search.getText();
                String value1 = tName.getText();
                String value2 = tUName.getText();
                String value3 = tPass.getText();
                String value4 = User_A.getSelectedItem().toString();

                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/Toppings_Express_db","root","");

                ps = conn.prepareStatement("UPDATE `Registered_User` SET `Name`='"+value1+"',"
                    + "`User_Name`='"+value2+"',`Password`= '"+value3+"',`User_Type`= '"+value4+"' WHERE `id`='"+value+"'");
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null,"User data Update successfully");
                Connect();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

         int selectedRow = user_L.getSelectedRow();
           TableModel model = user_L.getModel();
           
        if (selectedRow != -1) {
        int id = (int) model.getValueAt(selectedRow, 0);
        String Name = (String) model.getValueAt(selectedRow, 1);
        String UserN = (String) model.getValueAt(selectedRow, 2);
        String password = (String) model.getValueAt(selectedRow, 3);
        
           
        t_search.setText(String.valueOf(id));
        tName.setText(Name);
        tUName.setText(UserN);
        tPass.setText(password);
        
         
           }

    }//GEN-LAST:event_jButton5ActionPerformed

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        // Delete
       int y = JOptionPane.showConfirmDialog(null, "Are you sure you want to Delete this user?", "Delete user", JOptionPane.YES_NO_OPTION);
if (y == 0) {
    try {
        String value1 = t_search.getText();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/Toppings_Express_db", "root", "");

        // Delete the record
        PreparedStatement deletePs = conn.prepareStatement("DELETE FROM `Registered_User` WHERE id = ?");
        deletePs.setString(1, value1);
        deletePs.executeUpdate();

        // Reorganize the IDs
        PreparedStatement updatePs = conn.prepareStatement("SET @count = 0");
        updatePs.executeUpdate();

        updatePs = conn.prepareStatement("UPDATE `Registered_User` SET `id` = @count:= @count + 1");
        updatePs.executeUpdate();

        updatePs = conn.prepareStatement("ALTER TABLE `Registered_User` AUTO_INCREMENT = 1");
        updatePs.executeUpdate();

        JOptionPane.showMessageDialog(null, "user Deleted successfully");
        Connect();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
} else {
    JOptionPane.showMessageDialog(null, "Data not found");
}

    }//GEN-LAST:event_deleteActionPerformed

    private void tNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tNameActionPerformed

    private void tUNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tUNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tUNameActionPerformed

    private void P_PriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_P_PriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_P_PriceActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
         int y = JOptionPane.showConfirmDialog(null, "Are you sure you want to add this Product?","Add new Product",JOptionPane.YES_NO_OPTION);
        if(y == 0 ){

            try {

                
                String Product_N = P_Name.getText();
                String P = P_Price.getText();
                String AS = Available_S.getText();
                

                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Toppings_Express_db","root","");
                PreparedStatement ps = con.prepareStatement("INSERT INTO `Products` ( `Product_Name`, `Price`, Available_Stock) VALUES (?,?,?)");

               
                ps.setString(1, Product_N);
                ps.setString(2, P);
                ps.setString(3, AS);
               
                ps.execute();
                JOptionPane.showMessageDialog(null,"Data is saved successfully");
                connectP();
              

            } catch (Exception e) {
            }

        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // Update
        int y = JOptionPane.showConfirmDialog(null, "Are you sure you want update this Product?","Update Product",JOptionPane.YES_NO_OPTION);

        if(y ==0){
            try {
                String value = P_ID.getText();
                String value1 = P_Name.getText();
                String value2 = P_Price.getText();
                String value3 = Available_S.getText();
               

                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/Toppings_Express_db","root","");

               ps = conn.prepareStatement("UPDATE `Products` SET `Product_Name`=?, `Price`=?, `Available_Stock`=? WHERE `ID`=?");
               ps.setString(1, value1);
               ps.setString(2, value2);
               ps.setString(3, value3);
               ps.setString(4, value);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null,"User data Update successfully");
                connectP();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // Delete
      int y = JOptionPane.showConfirmDialog(null, "Are you sure you want to Delete this Product?", "Delete Product", JOptionPane.YES_NO_OPTION);
if (y == 0) {
    try {
        String value1 = P_ID.getText();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/Toppings_Express_db", "root", "");

        // Delete the record
        PreparedStatement deletePs = conn.prepareStatement("DELETE FROM `Products` WHERE ID = ?");
        deletePs.setString(1, value1);
        deletePs.executeUpdate();

        // Reorganize the IDs
        PreparedStatement updatePs = conn.prepareStatement("SET @count = 0");
        updatePs.executeUpdate();
        
        updatePs = conn.prepareStatement("UPDATE `Products` SET `Products`.`ID` = @count:= @count + 1");
        updatePs.executeUpdate();
        
        updatePs = conn.prepareStatement("ALTER TABLE `Products` AUTO_INCREMENT = 1");
        updatePs.executeUpdate();

        JOptionPane.showMessageDialog(null, "Record Deleted successfully");
        connectP();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }

} else {
    JOptionPane.showMessageDialog(null, "Data not found");
}

    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // Search
           int selectedRow = T_Product.getSelectedRow();
           TableModel model =T_Product.getModel();
           
        if (selectedRow != -1) {
        int id = (int) model.getValueAt(selectedRow, 0);
        String productName = (String) model.getValueAt(selectedRow, 1);
        double price = (double) model.getValueAt(selectedRow, 2);
        int AvailableS = (int) model.getValueAt(selectedRow, 3);
           
         P_ID.setText(String.valueOf(id));
         P_Name.setText(productName);
         P_Price.setText(String.valueOf(price));
         Available_S.setText(String.valueOf(AvailableS));
           }
       
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
        // TODO add your handling code here:
          int y = JOptionPane.showConfirmDialog(null, "Are you sure you want logout","Logout",JOptionPane.YES_NO_OPTION);
        
        if(y == 0){
           
            dispose();
            
            Login_window lw = new Login_window();
            lw.setVisible(true);}
    }//GEN-LAST:event_jButton29ActionPerformed

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed
        // TODO add your handling code here:
     if (frame == null) {
        frame = new POS_MAIN();
    }

    if (!frame.isVisible()) {
        frame.setVisible(true);
    } else if (frame.getState() == Frame.ICONIFIED) {
        frame.setExtendedState(Frame.NORMAL);
    }
        
    }//GEN-LAST:event_jButton31ActionPerformed

    private void Available_SActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Available_SActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Available_SActionPerformed

    private void SearchUKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SearchUKeyReleased
        // TODO add your handling code here:
        DefaultTableModel table = (DefaultTableModel) user_L.getModel();
        String search = SearchU.getText().trim();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<DefaultTableModel>(table);
        user_L.setRowSorter(trs);

// Make the search case-insensitive
        String regex = "(?i)" + Pattern.quote(search);
        trs.setRowFilter(RowFilter.regexFilter(regex));
    }//GEN-LAST:event_SearchUKeyReleased

    private void SearchPKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SearchPKeyReleased
        // TODO add your handling code here:
        DefaultTableModel table = (DefaultTableModel) T_Product.getModel();
        String search = SearchP.getText().trim();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<DefaultTableModel>(table);
        T_Product.setRowSorter(trs);

// Make the search case-insensitive
String regex = "(?i)" + Pattern.quote(search);
trs.setRowFilter(RowFilter.regexFilter(regex));
        
    }//GEN-LAST:event_SearchPKeyReleased

    private void SearchUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchUActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchUActionPerformed

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton30ActionPerformed
        // TODO add your handling code here:
        connectDb();
        toC();
    }//GEN-LAST:event_jButton30ActionPerformed

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
            java.util.logging.Logger.getLogger(Admin_Panel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Admin_Panel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Admin_Panel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Admin_Panel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Admin_Panel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Available_S;
    private javax.swing.JTextField P_ID;
    private javax.swing.JTextField P_Name;
    private javax.swing.JTextField P_Price;
    private javax.swing.JTextArea SR;
    private javax.swing.JTextField SearchP;
    private javax.swing.JTextField SearchU;
    private javax.swing.JLabel TCal;
    private javax.swing.JTable T_Product;
    private javax.swing.JTable TopS;
    private javax.swing.JComboBox<String> User_A;
    private javax.swing.JButton delete;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
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
    private javax.swing.JPanel jPanel24;
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
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextField tName;
    private javax.swing.JTextField tPass;
    private javax.swing.JTextField tUName;
    private javax.swing.JTextField t_search;
    private javax.swing.JLabel txt_d;
    private javax.swing.JLabel txt_t;
    private javax.swing.JTable user_L;
    // End of variables declaration//GEN-END:variables
}
