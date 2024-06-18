package kasirrpl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

public class MenuTransaksi extends javax.swing.JFrame {
    private int level_id;
    private String username;
    private DefaultTableModel model = null;
    private DefaultTableModel keranjangModel;
    private ResultSet rs;
    private PreparedStatement statement;
    connector k = new connector();
    
    public MenuTransaksi(int level_id, String username) {
        this.level_id = level_id;
        this.username = username;
        initComponents();
        
        keranjangModel = new DefaultTableModel();
        keranjangModel.addColumn("Nama Pelanggan");
        keranjangModel.addColumn("ID Item");
        keranjangModel.addColumn("Nama Item");
        keranjangModel.addColumn("Harga");
        keranjangModel.addColumn("Jumlah Beli");
        keranjangModel.addColumn("Total Bayar");
        jTableKeranjang.setModel(keranjangModel);
        
        k.connection();
        refreshCombo();
        refreshTable();
    }
    
    class transaksi {
        int transaksi_id, item_id, harga, jumlah_beli, total_bayar, user_id;
        String nama_pelanggan, tanggal, nama_item, nama_user;

        public transaksi() {
            this.nama_pelanggan = jTextFieldNamapelanggan.getText();
            String combo = jComboBoxIDitem.getSelectedItem().toString();
            String[] arr = combo.split(":");
            this.item_id = Integer.parseInt(arr[0]);
            try {
                Date date = jDateChooser.getDate();
                DateFormat dateformat = new SimpleDateFormat("YYYY-MM-dd");
                this.tanggal = dateformat.format(date);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            this.nama_item = arr[1];
            this.harga = Integer.parseInt(arr[2]);
            this.jumlah_beli = Integer.parseInt(jTextFieldJumlah.getText());
            this.total_bayar = this.harga * this.jumlah_beli;
            this.user_id = getUserID(username); 
            this.nama_user = getUserName(this.user_id); 
        }
    }
    
    private void addItemToKeranjang() {
    try {
        transaksi trans = new transaksi();
        int totalItem = getTotalItem(trans.item_id);
        if (trans.jumlah_beli > totalItem) {
            JOptionPane.showMessageDialog(null, "Jumlah item yang diminta melebihi jumlah yang tersedia!");
            return;
        }
        
        Object[] data = {
            trans.nama_pelanggan,
            trans.item_id,
            trans.nama_item,
            trans.harga,
            trans.jumlah_beli,
            trans.total_bayar
        };
        keranjangModel.addRow(data);
        jTextFieldTotalbayar.setText("" + trans.total_bayar);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    
     private int getUserID(String username) {
        int id = 0;
        try {
            statement = k.connection().prepareStatement("SELECT user_id FROM user WHERE username = ?");
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                id = rs.getInt("user_id");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return id;
    }

    // Method to get nama_user
    private String getUserName(int user_id) {
        String name = "";
        try {
            statement = k.connection().prepareStatement("SELECT nama_user FROM user WHERE user_id = ?");
            statement.setInt(1, user_id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                name = rs.getString("nama_user");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return name;
    }
    
    public void refreshTable() {
        model = new DefaultTableModel();
        model.addColumn("ID Transaksi");
        model.addColumn("Nama Pelanggan");
        model.addColumn("ID Item");
        model.addColumn("Tanggal");
        model.addColumn("Nama Item");
        model.addColumn("Harga");
        model.addColumn("Jumlah Beli");
        model.addColumn("Total Bayar");
        model.addColumn("User ID");
        model.addColumn("Nama User");
        jTableTransaksi.setModel(model);
        try {
            this.statement = k.connection().prepareStatement( 
            "SELECT t.transaksi_id, t.nama_pelanggan, t.item_id, t.tanggal, t.nama_item, t.harga, t.jumlah_beli, t.total_bayar, t.user_id, u.nama_user " +
            "FROM transaksi t JOIN user u ON t.user_id = u.user_id ORDER BY t.transaksi_id ASC"
            );
            this.rs = this.statement.executeQuery();
            while (rs.next()) {
                Object[] data = {
                    rs.getString("transaksi_id"),
                    rs.getString("nama_pelanggan"),
                    rs.getString("item_id"),
                    rs.getString("tanggal"),
                    rs.getString("nama_item"),
                    rs.getString("harga"),
                    rs.getString("jumlah_beli"),
                    rs.getString("total_bayar"),
                    rs.getString("user_id"),
                    rs.getString("nama_user")
                };
                model.addRow(data);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        jTextFieldID.setText("");
        jTextFieldNamapelanggan.setText("Pelanggan");
        jTextFieldJumlah.setText("");
        jTextFieldTotalbayar.setText("");
    }

    public void refreshCombo() {
        try {
            this.statement = k.connection().prepareStatement("select * from item where status = 'Tersedia'");
            this.rs = this.statement.executeQuery();
            while (rs.next()) {
                jComboBoxIDitem.addItem(rs.getString("item_id") + ":" + rs.getString("nama_item") + ":" + rs.getString("harga"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
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

        jLabel2 = new javax.swing.JLabel();
        jTextFieldID = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableTransaksi = new javax.swing.JTable();
        jTextFieldNamapelanggan = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jButtonLogout = new javax.swing.JButton();
        jTextFieldJumlah = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jComboBoxIDitem = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldTotalbayar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButtonLihatmenu = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jDateChooser = new com.toedter.calendar.JDateChooser();
        jButtonDelete = new javax.swing.JButton();
        jButtonUpdate = new javax.swing.JButton();
        jButtonBack = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableKeranjang = new javax.swing.JTable();
        jButtonProsesTransaksi = new javax.swing.JButton();
        jButtonTambahKeranjang = new javax.swing.JButton();
        jButtonKosongkanKeranjang = new javax.swing.JButton();
        jButtonDeteteKeranjang = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Transaksi");
        setResizable(false);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel2.setText("ID Transaksi");

        jTextFieldID.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jTextFieldID.setEnabled(false);
        jTextFieldID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldIDActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel4.setText("Nama Pelanggan");

        jTableTransaksi.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableTransaksiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableTransaksi);

        jTextFieldNamapelanggan.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jTextFieldNamapelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNamapelangganActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel6.setText("Jumlah Beli");

        jButtonLogout.setBackground(new java.awt.Color(255, 51, 51));
        jButtonLogout.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonLogout.setForeground(new java.awt.Color(255, 255, 255));
        jButtonLogout.setText("LOGOUT");
        jButtonLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogoutActionPerformed(evt);
            }
        });

        jTextFieldJumlah.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jTextFieldJumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldJumlahActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel7.setText("ID Item");

        jComboBoxIDitem.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jComboBoxIDitem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxIDitemActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel8.setText("Total Bayar");

        jTextFieldTotalbayar.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jTextFieldTotalbayar.setEnabled(false);
        jTextFieldTotalbayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldTotalbayarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 255));
        jLabel1.setText("MENU TRANSAKSI");

        jButtonLihatmenu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButtonLihatmenu.setText("LIHAT ITEM");
        jButtonLihatmenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLihatmenuActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel9.setText("Tanggal");

        jDateChooser.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jButtonDelete.setBackground(new java.awt.Color(255, 51, 51));
        jButtonDelete.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jButtonDelete.setForeground(new java.awt.Color(255, 255, 255));
        jButtonDelete.setText("DELETE");
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });

        jButtonUpdate.setBackground(new java.awt.Color(51, 153, 255));
        jButtonUpdate.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jButtonUpdate.setForeground(new java.awt.Color(255, 255, 255));
        jButtonUpdate.setText("UPDATE");
        jButtonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateActionPerformed(evt);
            }
        });

        jButtonBack.setBackground(new java.awt.Color(255, 102, 102));
        jButtonBack.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonBack.setForeground(new java.awt.Color(255, 255, 255));
        jButtonBack.setText("BACK TO MENU");
        jButtonBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBackActionPerformed(evt);
            }
        });

        jTableKeranjang.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTableKeranjang);

        jButtonProsesTransaksi.setBackground(new java.awt.Color(51, 153, 255));
        jButtonProsesTransaksi.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jButtonProsesTransaksi.setForeground(new java.awt.Color(255, 255, 255));
        jButtonProsesTransaksi.setText("PROSES KE TRANSAKSI");
        jButtonProsesTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonProsesTransaksiActionPerformed(evt);
            }
        });

        jButtonTambahKeranjang.setBackground(new java.awt.Color(51, 153, 255));
        jButtonTambahKeranjang.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jButtonTambahKeranjang.setForeground(new java.awt.Color(255, 255, 255));
        jButtonTambahKeranjang.setText("PROSES KE KERANJANG");
        jButtonTambahKeranjang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTambahKeranjangActionPerformed(evt);
            }
        });

        jButtonKosongkanKeranjang.setBackground(new java.awt.Color(255, 51, 51));
        jButtonKosongkanKeranjang.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jButtonKosongkanKeranjang.setForeground(new java.awt.Color(255, 255, 255));
        jButtonKosongkanKeranjang.setText("CLEAR");
        jButtonKosongkanKeranjang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonKosongkanKeranjangActionPerformed(evt);
            }
        });

        jButtonDeteteKeranjang.setBackground(new java.awt.Color(255, 51, 51));
        jButtonDeteteKeranjang.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jButtonDeteteKeranjang.setForeground(new java.awt.Color(255, 255, 255));
        jButtonDeteteKeranjang.setText("DELETE");
        jButtonDeteteKeranjang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeteteKeranjangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonBack, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(597, 597, 597)
                                .addComponent(jButtonProsesTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)
                                .addComponent(jButtonUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(53, 53, 53))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(161, 161, 161)
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBoxIDitem, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonLihatmenu, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButtonTambahKeranjang, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextFieldNamapelanggan)
                                            .addComponent(jTextFieldID)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jTextFieldJumlah)
                                            .addComponent(jTextFieldTotalbayar)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jButtonDeteteKeranjang, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButtonKosongkanKeranjang, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))))
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 642, Short.MAX_VALUE)
                            .addComponent(jScrollPane2))
                        .addGap(18, 18, 18))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonBack, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(jTextFieldID, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(85, 85, 85)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldNamapelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonLihatmenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jComboBoxIDitem, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldTotalbayar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButtonKosongkanKeranjang, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButtonDeteteKeranjang, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButtonTambahKeranjang, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButtonUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(jButtonProsesTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(67, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldIDActionPerformed

    private void jTextFieldNamapelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNamapelangganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNamapelangganActionPerformed
    
    private int getTotalItem(int item_id) {
        int total = 0;
        try {
            this.statement = k.connection().prepareStatement("SELECT total FROM item WHERE item_id = ?");
            statement.setInt(1, item_id);
            this.rs = statement.executeQuery();
            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return total;
    }
    
    private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoutActionPerformed
        int pilih = JOptionPane.showConfirmDialog(null,
                    "Ingin Logout sekarang ?",
                   "Logout", JOptionPane.YES_NO_OPTION );
        if (pilih == JOptionPane.YES_OPTION){
                Login login = new Login();
                login.setLocationRelativeTo(null);
                login.setVisible(true);
                this.setVisible(false);
                Menu menu = new Menu(level_id, username);
                menu.ButtonItem.setEnabled(false);
                menu.ButtonLaporan.setEnabled(false);
                menu.ButtonRegis.setEnabled(false);
            }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonLogoutActionPerformed

    private void jTextFieldJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldJumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldJumlahActionPerformed

    private void jTextFieldTotalbayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldTotalbayarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldTotalbayarActionPerformed

    private void jTableTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableTransaksiMouseClicked
        jTextFieldID.setText(model.getValueAt(jTableTransaksi.getSelectedRow(), 0).toString());
        jTextFieldNamapelanggan.setText(model.getValueAt(jTableTransaksi.getSelectedRow(), 1).toString());
        jTextFieldJumlah.setText(model.getValueAt(jTableTransaksi.getSelectedRow(), 6).toString());
        jTextFieldTotalbayar.setText(model.getValueAt(jTableTransaksi.getSelectedRow(), 7).toString());
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableTransaksiMouseClicked

    private void jButtonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateActionPerformed
        try {
            transaksi trans = new transaksi();
            trans.transaksi_id = Integer.parseInt(jTextFieldID.getText());
            this.statement = k.connection().prepareStatement("update transaksi set nama_pelanggan = ?, item_id = ?, tanggal= ? , nama_item = ?, harga = ?, jumlah_beli = ?, total_bayar=? where transaksi_id = ?");
            
            statement.setString(1, trans.nama_pelanggan);
            statement.setInt(2, trans.item_id);
            statement.setString(3, trans.tanggal);
            statement.setString(4, trans.nama_item);
            statement.setInt(5, trans.harga);
            statement.setInt(6, trans.jumlah_beli);
            statement.setInt(7, trans.total_bayar);
            statement.setInt(8, trans.transaksi_id);
            statement.executeUpdate();
            refreshTable();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonUpdateActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        try {
        int selectedRow = jTableTransaksi.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Pilih transaksi yang ingin dihapus.");
            return;
        }
        
        String transaksiId = model.getValueAt(selectedRow, 0).toString();
        
        this.statement = k.connection().prepareStatement("delete from transaksi where transaksi_id = ?");
        statement.setString(1, transaksiId);
        int rowsAffected = statement.executeUpdate();
        
        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Penghapusan transaksi berhasil dilakukan.");
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(null, "Transaksi tidak ditemukan atau penghapusan gagal.");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void jButtonLihatmenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLihatmenuActionPerformed
        MenuItem item = new MenuItem(level_id, username);
        item.setVisible(true);
        this.setVisible(false);
        item.jButtonDelete.setEnabled(true);
        item.jButtonInput.setEnabled(true);
        item.jButtonUpdate.setEnabled(true);
        item.jButtonBack.setEnabled(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonLihatmenuActionPerformed

    private void jComboBoxIDitemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxIDitemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxIDitemActionPerformed

    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackActionPerformed
        Menu menu = new Menu(level_id, username);
        menu.setLocationRelativeTo(null);
        menu.setVisible(true);
        this.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonBackActionPerformed

    private void jButtonProsesTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonProsesTransaksiActionPerformed
        // TODO add your handling code here:
        try {
        for (int i = 0; i < keranjangModel.getRowCount(); i++) {
            String namaPelanggan = keranjangModel.getValueAt(i, 0).toString();
            int itemId = Integer.parseInt(keranjangModel.getValueAt(i, 1).toString());
            String tanggal = new SimpleDateFormat("YYYY-MM-dd").format(new Date());
            String namaItem = keranjangModel.getValueAt(i, 2).toString();
            int harga = Integer.parseInt(keranjangModel.getValueAt(i, 3).toString());
            int jumlahBeli = Integer.parseInt(keranjangModel.getValueAt(i, 4).toString());
            int totalBayar = Integer.parseInt(keranjangModel.getValueAt(i, 5).toString());
            
            int userId = getUserID(username); // Mendapatkan user_id berdasarkan username
            String namaUser = getUserName(userId);

            this.statement = k.connection().prepareStatement("insert into transaksi (nama_pelanggan, item_id, tanggal, nama_item, harga, jumlah_beli, total_bayar, user_id, nama_user) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, namaPelanggan);
            statement.setInt(2, itemId);
            statement.setString(3, tanggal);
            statement.setString(4, namaItem);
            statement.setInt(5, harga);
            statement.setInt(6, jumlahBeli);
            statement.setInt(7, totalBayar);
            statement.setInt(8, userId); // Assuming this is user_id
            statement.setString(9, namaUser); // Assuming this is nama_user
            statement.executeUpdate();

            kurangiTotalItem(itemId, jumlahBeli);
        }
        
        keranjangModel.setRowCount(0); // Kosongkan keranjang
        JOptionPane.showMessageDialog(null, "Transaksi Berhasil");
        refreshTable(); // Refresh tabel transaksi
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_jButtonProsesTransaksiActionPerformed

    private void jButtonTambahKeranjangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTambahKeranjangActionPerformed
        // TODO add your handling code here:
        addItemToKeranjang();
    }//GEN-LAST:event_jButtonTambahKeranjangActionPerformed

    private void jButtonKosongkanKeranjangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonKosongkanKeranjangActionPerformed
        // TODO add your handling code here:
        keranjangModel.setRowCount(0);
    }//GEN-LAST:event_jButtonKosongkanKeranjangActionPerformed

    private void jButtonDeteteKeranjangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeteteKeranjangActionPerformed
        // TODO add your handling code here:
         int row = jTableKeranjang.getSelectedRow();
        if (row != -1) {
            keranjangModel.removeRow(row);
            JOptionPane.showMessageDialog(null, "Item dihapus dari keranjang!");
        } else {
            JOptionPane.showMessageDialog(null, "Pilih item yang akan dihapus dari keranjang!");
        }
    }//GEN-LAST:event_jButtonDeteteKeranjangActionPerformed
    
     //        try {
            //        transaksi trans = new transaksi();
            //        int totalItem = getTotalItem(trans.item_id);
            //        if (trans.jumlah_beli > totalItem) {
                //            JOptionPane.showMessageDialog(null, "Jumlah item yang diminta melebihi jumlah yang tersedia!");
                //            return;
                //        }
            //        jTextFieldTotalbayar.setText("" + trans.total_bayar);
            //        this.statement = k.connection().prepareStatement(
                //            "INSERT INTO transaksi (nama_pelanggan, item_id, tanggal, nama_item, harga, jumlah_beli, total_bayar, user_id, nama_user) " +
                //            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
                //        );
            //        // Exclude transaksi_id because it's auto-increment
            //        statement.setString(1, trans.nama_pelanggan);
            //        statement.setInt(2, trans.item_id);
            //        statement.setString(3, trans.tanggal);
            //        statement.setString(4, trans.nama_item);
            //        statement.setInt(5, trans.harga);
            //        statement.setInt(6, trans.jumlah_beli);
            //        statement.setInt(7, trans.total_bayar);
            //        statement.setInt(8, trans.user_id);
            //        statement.setString(9, trans.nama_user);
            //
            //        int pilihan = JOptionPane.showConfirmDialog(null,
                //            "Tanggal: " + trans.tanggal +
                //            "\nNama Pelanggan: " + trans.nama_pelanggan +
                //            "\nPembelian: " + trans.jumlah_beli + " " + trans.nama_item +
                //            "\nTotal Bayar: " + trans.total_bayar + "\n",
                //            "Tambahkan Transaksi?", JOptionPane.YES_NO_OPTION
                //        );
            //
            //        if (pilihan == JOptionPane.YES_OPTION) {
                //            statement.executeUpdate();
                //            kurangiTotalItem(trans.item_id, trans.jumlah_beli);
                //            refreshTable();
                //        } else if (pilihan == JOptionPane.NO_OPTION) {
                //            refreshTable();
                //        }
            //    } catch (Exception e) {
            //        JOptionPane.showMessageDialog(null, e.getMessage());
            //    }
        //        // TODO add your handling code here:
        //        // TODO add your handling code here:
    
    private void jTableKeranjangMouseClicked(java.awt.event.MouseEvent evt) {
    if (evt.getClickCount() == 1) {
        int row = jTableKeranjang.getSelectedRow();
        if (row != -1) {
            jTextFieldID.setText(keranjangModel.getValueAt(row, 0).toString());
            }
        }
    }
    
    private void kurangiTotalItem(int item_id, int jumlah_beli) {
    try {
        this.statement = k.connection().prepareStatement("UPDATE item SET total = total - ? WHERE item_id = ?");
        statement.setInt(1, jumlah_beli);
        statement.setInt(2, item_id);
        statement.executeUpdate();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
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
            java.util.logging.Logger.getLogger(MenuTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
           try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MenuTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                int level_id = LoginService.getLoggedInLevelId();
                String username = LoginService.getLoggedInUsername();
                new MenuTransaksi(level_id, username).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButtonBack;
    public javax.swing.JButton jButtonDelete;
    public javax.swing.JButton jButtonDeteteKeranjang;
    public javax.swing.JButton jButtonKosongkanKeranjang;
    private javax.swing.JButton jButtonLihatmenu;
    public javax.swing.JButton jButtonLogout;
    public javax.swing.JButton jButtonProsesTransaksi;
    public javax.swing.JButton jButtonTambahKeranjang;
    public javax.swing.JButton jButtonUpdate;
    private javax.swing.JComboBox<String> jComboBoxIDitem;
    private com.toedter.calendar.JDateChooser jDateChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableKeranjang;
    private javax.swing.JTable jTableTransaksi;
    private javax.swing.JTextField jTextFieldID;
    private javax.swing.JTextField jTextFieldJumlah;
    private javax.swing.JTextField jTextFieldNamapelanggan;
    private javax.swing.JTextField jTextFieldTotalbayar;
    // End of variables declaration//GEN-END:variables
}