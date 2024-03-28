/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sad_pos_toppings_express;


import com.mysql.cj.jdbc.Driver;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.Timer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author reqba
 */
public class POS_MAIN extends javax.swing.JFrame {

    /**
     * Creates new form POS_MAIN
     */
    private int s = 0;
    public JButton myButton;
    private Map<String, Integer> productQuantities = new HashMap<>();
    
    
    
    
    
    
    public POS_MAIN() {
        
        
        initComponents();
        show_date();
        show_time();
        connectDb();
        connectP();
        
        
        
       
        
        
        ((DefaultTableModel) jTable1.getModel()).setColumnCount(4);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(30);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(200);
    }
    
    com.sun.jdi.connect.spi.Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
   
  
    
    
    
    
     void show_date(){
        java.util.Date d = new java.util.Date();
        SimpleDateFormat s = new SimpleDateFormat("YYYY-MM-dd");
        txt_d.setText(""+ s.format(d));
        
       
        
    }
    void show_time(){
       new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                  java.util.Date d = new java.util.Date();
                  SimpleDateFormat s = new SimpleDateFormat("hh:mm:ss a");
                  txt_t.setText("" + s.format(d));
              
            }
        }).start();
       
    }
    
     public void connectP(){
         
         try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Toppings_Express_db","root","");
                String sql = "select * from Products";
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                T_productList.setModel(DbUtils.resultSetToTableModel(rs));
                
                while(rs.next()){
                
                    String id = String.valueOf(rs.getInt("ID"));
                    String Product_name = rs.getString("Product_Name");
                    String Price = rs.getString("Price");
                    String Av = rs.getString("Available_Stock");
                    
                
                    
                    String tb[] = {id, Product_name, Price, Av};
                    DefaultTableModel t = (DefaultTableModel)T_productList.getModel();
                    
                    t.addRow(tb);
                }
                
                
                
            } catch (Exception e) {
                 JOptionPane.showMessageDialog(null, e);
                
            }
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, e);
        }
     }
    
    
    
   public void addtable(int ID, String Item, int Qty, Double Price){
   
   
   DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
   Vector v = new Vector();
   
   
   
   v.add(ID);
   v.add(Item);
   v.add(Qty);
   v.add(Price);
   
    
   dt.addRow(v); 
      
    
    
    }
   
   
    
    public void addtables(int ID, String Item, int Qty, Double Price){
        
   
    
    DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
     DecimalFormat df = new DecimalFormat("00.00");
     double totalP = Price * Double.valueOf(Qty);
     String TP = df.format(totalP);
     
     int rowCount = jTable1.getRowCount();
     
      for(int row = rowCount - 1; row >= 0; row--){
          
        Object value = jTable1.getValueAt(row, 1);
       
   
   
       if (Item.equals(jTable1.getValueAt(row, 1))){
           int modelRow = jTable1.convertColumnIndexToModel(row);
       dt.removeRow(modelRow);
    
   }
       
    
   }
      
     
   
   
   Vector v = new Vector();
   
   v.add(ID);
   v.add(Item);
   v.add(Qty);
   v.add(TP);
   
   
   dt.addRow(v);
   
   
    }
    
    
    public void toC(){
    
    
        int numOfRow = jTable1.getRowCount();
        double tot = 0.0;
        
        for (int i = 0; i < numOfRow; i++){
           
            double value = Double.valueOf(jTable1.getValueAt(i, 3).toString());
            tot += value; 
        
        }
       DecimalFormat df = new DecimalFormat("00.00") ;
       Total.setText(df.format(tot));
    }
    
    public void connectDb() {

   try {
    Class.forName("com.mysql.cj.jdbc.Driver");
    try {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Toppings_Express_db", "root", "");
        String sql = "SELECT Item, SUM(Qty) AS TotalQty FROM Sold_Ps GROUP BY Item ORDER BY TotalQty DESC";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        DefaultTableModel model = (DefaultTableModel) TopS.getModel();
        model.setRowCount(0); // clear the table before adding new rows

        while (rs.next()) {
            String itemName = rs.getString("Item");
            Object[] row = { itemName };
            model.addRow(row);
        }

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        TopS.setRowSorter(sorter);

        // specify the column to sort by (the only column in this case) and the sort order (descending)
        sorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(0, SortOrder.DESCENDING)));

        // perform the sort
        sorter.sort();

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

        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu5 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        jMenu7 = new javax.swing.JMenu();
        jMenuBar3 = new javax.swing.JMenuBar();
        jMenu8 = new javax.swing.JMenu();
        jMenu9 = new javax.swing.JMenu();
        jMenuBar4 = new javax.swing.JMenuBar();
        jMenu10 = new javax.swing.JMenu();
        jMenu11 = new javax.swing.JMenu();
        jMenuBar5 = new javax.swing.JMenuBar();
        jMenu12 = new javax.swing.JMenu();
        jMenu13 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu14 = new javax.swing.JMenu();
        jMenuBar6 = new javax.swing.JMenuBar();
        jMenu15 = new javax.swing.JMenu();
        jMenu16 = new javax.swing.JMenu();
        jMenuBar7 = new javax.swing.JMenuBar();
        jMenu17 = new javax.swing.JMenu();
        jMenu18 = new javax.swing.JMenu();
        jMenuBar8 = new javax.swing.JMenuBar();
        jMenu19 = new javax.swing.JMenu();
        jMenu20 = new javax.swing.JMenu();
        jMenuBar9 = new javax.swing.JMenuBar();
        jMenu21 = new javax.swing.JMenu();
        jMenu22 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuBar10 = new javax.swing.JMenuBar();
        jMenu23 = new javax.swing.JMenu();
        jMenu24 = new javax.swing.JMenu();
        jMenuBar11 = new javax.swing.JMenuBar();
        jMenu25 = new javax.swing.JMenu();
        jMenu26 = new javax.swing.JMenu();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel11 = new javax.swing.JPanel();
        jButton12 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        TopS = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        pay = new javax.swing.JTextField();
        Cha = new javax.swing.JLabel();
        Total = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        R = new javax.swing.JTextArea();
        jButton6 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        txt_d = new javax.swing.JLabel();
        txt_t = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        T_productList = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        searchkey = new javax.swing.JTextField();
        jButton13 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();

        jMenu1.setText("jMenu1");

        jMenu2.setText("jMenu2");

        jMenu3.setText("jMenu3");

        jMenu4.setText("jMenu4");

        jMenu5.setText("File");
        jMenuBar2.add(jMenu5);

        jMenu6.setText("Edit");
        jMenuBar2.add(jMenu6);

        jMenu7.setText("jMenu7");

        jMenu8.setText("File");
        jMenuBar3.add(jMenu8);

        jMenu9.setText("Edit");
        jMenuBar3.add(jMenu9);

        jMenu10.setText("File");
        jMenuBar4.add(jMenu10);

        jMenu11.setText("Edit");
        jMenuBar4.add(jMenu11);

        jMenu12.setText("File");
        jMenuBar5.add(jMenu12);

        jMenu13.setText("Edit");
        jMenuBar5.add(jMenu13);

        jMenuItem1.setText("jMenuItem1");

        jMenu14.setText("jMenu14");

        jMenu15.setText("File");
        jMenuBar6.add(jMenu15);

        jMenu16.setText("Edit");
        jMenuBar6.add(jMenu16);

        jMenu17.setText("File");
        jMenuBar7.add(jMenu17);

        jMenu18.setText("Edit");
        jMenuBar7.add(jMenu18);

        jMenu19.setText("File");
        jMenuBar8.add(jMenu19);

        jMenu20.setText("Edit");
        jMenuBar8.add(jMenu20);

        jMenu21.setText("File");
        jMenuBar9.add(jMenu21);

        jMenu22.setText("Edit");
        jMenuBar9.add(jMenu22);

        jMenuItem2.setText("jMenuItem2");

        jMenu23.setText("File");
        jMenuBar10.add(jMenu23);

        jMenu24.setText("Edit");
        jMenuBar10.add(jMenu24);

        jMenu25.setText("File");
        jMenuBar11.add(jMenu25);

        jMenu26.setText("Edit");
        jMenuBar11.add(jMenu26);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("POS");
        setBackground(new java.awt.Color(0, 0, 0));

        jPanel11.setBackground(new java.awt.Color(204, 0, 0));

        jButton12.setBackground(new java.awt.Color(0, 0, 0));
        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationIcon/icons8_Logout_25px.png"))); // NOI18N
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setForeground(new java.awt.Color(51, 51, 51));

        jPanel2.setBackground(new java.awt.Color(255, 153, 0));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(204, 0, 0));
        jPanel3.setForeground(new java.awt.Color(204, 0, 0));

        jButton4.setBackground(java.awt.Color.black);
        jButton4.setFont(new java.awt.Font("Unispace", 1, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationIcon/icons8_Pay_in_Cash_40px.png"))); // NOI18N
        jButton4.setText("Pay");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(java.awt.Color.black);
        jButton5.setFont(new java.awt.Font("Unispace", 1, 18)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationIcon/icons8_receipt_40px.png"))); // NOI18N
        jButton5.setText("Print");

        TopS.setBackground(new java.awt.Color(204, 204, 204));
        TopS.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 1, true));
        TopS.setFont(new java.awt.Font("DialogInput", 1, 12)); // NOI18N
        TopS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product"
            }
        ));
        jScrollPane3.setViewportView(TopS);

        jLabel1.setFont(new java.awt.Font("Unispace", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Top Sales:");

        jLabel4.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jLabel4.setText("Total:");

        jLabel5.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jLabel5.setText("Cash:");

        jLabel6.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jLabel6.setText("Change:");

        pay.setFont(new java.awt.Font("DialogInput", 1, 14)); // NOI18N
        pay.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        Cha.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        Cha.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Cha.setText("00.00");

        Total.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        Total.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Total.setText("00.00");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Cha, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pay, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Total, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(7, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(Total))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(pay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(Cha))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(91, 91, 91)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton5))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.setBackground(new java.awt.Color(255, 153, 0));
        jTabbedPane1.setFont(new java.awt.Font("Unispace", 0, 12)); // NOI18N

        jPanel4.setBackground(new java.awt.Color(102, 102, 102));

        jTable1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jTable1.setFont(new java.awt.Font("DialogInput", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Item", "Qty", "Price"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        R.setColumns(20);
        R.setFont(new java.awt.Font("DialogInput", 0, 12)); // NOI18N
        R.setRows(5);
        jScrollPane2.setViewportView(R);

        jButton6.setBackground(java.awt.Color.black);
        jButton6.setFont(new java.awt.Font("Unispace", 0, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationIcon/icons8_delete_25px.png"))); // NOI18N
        jButton6.setText("Delete");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton8.setBackground(java.awt.Color.black);
        jButton8.setFont(new java.awt.Font("Unispace", 0, 14)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationIcon/icons8_Delete_Table_25px.png"))); // NOI18N
        jButton8.setText("Clear");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton11.setBackground(java.awt.Color.black);
        jButton11.setFont(new java.awt.Font("Unispace", 0, 14)); // NOI18N
        jButton11.setForeground(new java.awt.Color(255, 255, 255));
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationIcon/icons8_hand_cursor_25px.png"))); // NOI18N
        jButton11.setText("Select");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton8))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(7, 7, 7)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton8)
                            .addComponent(jButton6)
                            .addComponent(jButton11)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Product", jPanel4);

        jPanel9.setBackground(java.awt.Color.black);
        jPanel9.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txt_d.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        txt_d.setForeground(new java.awt.Color(255, 255, 255));
        txt_d.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationIcon/icons8_calendar_35px.png"))); // NOI18N

        txt_t.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        txt_t.setForeground(new java.awt.Color(255, 255, 255));
        txt_t.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationIcon/icons8_clock_35px.png"))); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_d, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txt_t, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_d)
                    .addComponent(txt_t))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 682, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        T_productList.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        T_productList.setFont(new java.awt.Font("DialogInput", 0, 14)); // NOI18N
        T_productList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Product Name", "Price", "Available_Stock"
            }
        ));
        T_productList.setShowHorizontalLines(true);
        jScrollPane4.setViewportView(T_productList);

        jPanel8.setBackground(new java.awt.Color(255, 153, 0));

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setFont(new java.awt.Font("Unispace", 1, 18)); // NOI18N
        jLabel7.setText("Search:");

        searchkey.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        searchkey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchkeyActionPerformed(evt);
            }
        });
        searchkey.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchkeyKeyReleased(evt);
            }
        });

        jButton13.setBackground(java.awt.Color.black);
        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationIcon/icons8_Refresh_25px.png"))); // NOI18N
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchkey, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(searchkey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 646, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 749, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        
        String current_Date = txt_d.getText();
        String current_Time = txt_t.getText();
        double total = Double.valueOf(Total.getText());
        double py = Double.valueOf(pay.getText());
        double Change = py - total;
                
              DecimalFormat df = new DecimalFormat("00.00");
              
              Cha.setText(String.valueOf(df.format(Change)));
              
       if(total > py){
            
            JOptionPane.showMessageDialog(this, "Not enough Cash", "Notice",  JOptionPane.INFORMATION_MESSAGE);
        
          } else {
        try {
            
            R.setText("                        Toppings Express \n");
            R.setText(R.getText() + "                   Panabo Polymedic Hospital \n");
            R.setText(R.getText() + "                   Brgy. Gredu, Panabo City \n");
            R.setText(R.getText() + "                   Contact no 0935-240-7874 \n");
            R.setText(R.getText() + "-------------------------------------------------------\n");
            R.setText(R.getText() + "Item \t\t\t    Qty \tPrice" +"\n");
            R.setText(R.getText() + "-------------------------------------------------------\n");
            
             DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
            
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                
                String Item = dt.getValueAt(i, 1).toString();
                String Qty = dt.getValueAt(i, 2).toString();
                String Price = dt.getValueAt(i, 3).toString();
            
                 R.setText(R.getText() +""+Item+"\t\t\t     "+Qty +"\t        "+Price + "\n");
                 
            
            
            }
         
        R.setText(R.getText() + "-------------------------------------------------------\n");
            R.setText(R.getText() + "Transaction Paid on: " + current_Date +"   "+ current_Time +"\n");
            R.setText(R.getText() + "Sub Total : " + Total.getText() +"\n");
            R.setText(R.getText() + "Cash      : " + pay.getText() +"\n");
            R.setText(R.getText() + "Change   : " + Cha.getText() +"\n");
        } catch (Exception e) {
        }
        
        DefaultTableModel tb = (DefaultTableModel) jTable1.getModel();
        
        
            
        
            
        if (tb.getRowCount() == 0){
            
            JOptionPane.showMessageDialog(this, "Table is empty", "Notice", JOptionPane.INFORMATION_MESSAGE);
        
        }else{
           
          
            try {
    Class.forName("com.mysql.jdbc.Driver");
    Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Toppings_Express_db", "root", "");

    for (int i = 0; i < jTable1.getRowCount(); i++) {
        String ID = tb.getValueAt(i, 0).toString();
        String Item = tb.getValueAt(i, 1).toString();
        String Qty = tb.getValueAt(i, 2).toString();
        String Price = tb.getValueAt(i, 3).toString();

        
        String selectQuery = "SELECT * FROM Sold_Ps WHERE ID = ?";
        PreparedStatement selectPs = con.prepareStatement(selectQuery);
        selectPs.setString(1, ID);
        ResultSet rs = selectPs.executeQuery();

        if (rs.next()) {
           
            String updateQuery = "UPDATE Sold_Ps SET Qty = Qty + ?, Price = ? WHERE ID = ?";
            PreparedStatement updatePs = con.prepareStatement(updateQuery);
            updatePs.setString(1, Qty);
            updatePs.setString(2, Price);
            updatePs.setString(3, ID);
            updatePs.executeUpdate();
        } else {
           
            String insertQuery = "INSERT INTO Sold_Ps (ID, Item, Qty, Price) VALUES (?, ?, ?, ?)";
            PreparedStatement insertPs = con.prepareStatement(insertQuery);
            insertPs.setString(1, ID);
            insertPs.setString(2, Item);
            insertPs.setString(3, Qty);
            insertPs.setString(4, Price);
            insertPs.executeUpdate();
        }
        
        String updateQuery = "UPDATE Products SET Available_Stock = Available_Stock - ? WHERE Product_Name = ?";
                PreparedStatement updatePs = con.prepareStatement(updateQuery);
                updatePs.setString(1, Qty);
                updatePs.setString(2, Item);
                updatePs.executeUpdate();
    }
    
    con.close();
} catch (ClassNotFoundException | SQLException e) {
    e.printStackTrace();
}

            
        }    
        
            connectDb();
            connectP();
       }
        
     
       
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void searchkeyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchkeyKeyReleased
          DefaultTableModel table = (DefaultTableModel) T_productList.getModel();
         String search = searchkey.getText().trim();
         TableRowSorter<DefaultTableModel> trs = new TableRowSorter<DefaultTableModel>(table);
         T_productList.setRowSorter(trs);


         String regex = "(?i)" + Pattern.quote(search);
         trs.setRowFilter(RowFilter.regexFilter(regex));
    }//GEN-LAST:event_searchkeyKeyReleased

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        int y = JOptionPane.showConfirmDialog(null, "Are you sure you want logout","Logout",JOptionPane.YES_NO_OPTION);
        
        if(y == 0){
           
            dispose();
            
            Login_window lw = new Login_window();
            lw.setVisible(true);
            
        
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:

     int selectedRow = T_productList.getSelectedRow();
    TableModel model = T_productList.getModel();

    if (selectedRow != -1) {
        int id = (int) model.getValueAt(selectedRow, 0);
        String productName = (String) model.getValueAt(selectedRow, 1);
        double price = (double) model.getValueAt(selectedRow, 2);

        // Check if the product is already in the Map
        if (productQuantities.containsKey(productName)) {
            // If the product exists, increment the quantity
            int quantity = productQuantities.get(productName);
            quantity++;
            productQuantities.put(productName, quantity);
        } else {
            // If the product doesn't exist, initialize the quantity to 1
            productQuantities.put(productName, 1);
        }
        addtables(id, productName, productQuantities.get(productName), price);

        // Perform any other actions with the retrieved data
        // ...

        toC();
    }

    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
        dt.setRowCount(0);
        productQuantities.clear();
        Total.setText("00.00");
        Cha.setText("00.00");
        pay.setText("");

        toC();

    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        //delete

      DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
int selectedRow = jTable1.getSelectedRow();

if (selectedRow != -1) {
    String productName = dt.getValueAt(selectedRow, 1).toString();

    // Reset the quantity for the specific product in the productQuantities map
    productQuantities.remove(productName);

   dt.removeRow(selectedRow);

    toC();

}

    }//GEN-LAST:event_jButton6ActionPerformed

    private void searchkeyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchkeyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchkeyActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // refresh
        connectP();
    }//GEN-LAST:event_jButton13ActionPerformed

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
            java.util.logging.Logger.getLogger(POS_MAIN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(POS_MAIN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(POS_MAIN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(POS_MAIN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new POS_MAIN().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Cha;
    private javax.swing.JTextArea R;
    private javax.swing.JTable T_productList;
    private javax.swing.JTable TopS;
    private javax.swing.JLabel Total;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu10;
    private javax.swing.JMenu jMenu11;
    private javax.swing.JMenu jMenu12;
    private javax.swing.JMenu jMenu13;
    private javax.swing.JMenu jMenu14;
    private javax.swing.JMenu jMenu15;
    private javax.swing.JMenu jMenu16;
    private javax.swing.JMenu jMenu17;
    private javax.swing.JMenu jMenu18;
    private javax.swing.JMenu jMenu19;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu20;
    private javax.swing.JMenu jMenu21;
    private javax.swing.JMenu jMenu22;
    private javax.swing.JMenu jMenu23;
    private javax.swing.JMenu jMenu24;
    private javax.swing.JMenu jMenu25;
    private javax.swing.JMenu jMenu26;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar10;
    private javax.swing.JMenuBar jMenuBar11;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JMenuBar jMenuBar4;
    private javax.swing.JMenuBar jMenuBar5;
    private javax.swing.JMenuBar jMenuBar6;
    private javax.swing.JMenuBar jMenuBar7;
    private javax.swing.JMenuBar jMenuBar8;
    private javax.swing.JMenuBar jMenuBar9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField pay;
    private javax.swing.JTextField searchkey;
    private javax.swing.JLabel txt_d;
    private javax.swing.JLabel txt_t;
    // End of variables declaration//GEN-END:variables
}
