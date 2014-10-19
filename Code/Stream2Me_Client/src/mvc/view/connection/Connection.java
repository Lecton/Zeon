/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.view.connection;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import mvc.controller.connection.ConnectionControl;

/**
 *
 * @author Bernhard
 */
public class Connection extends javax.swing.JFrame {
    private final static Logger LOGGER = Logger.getLogger(Connection.class.getName());
    

    /**
     * Creates new form Connection
     */
    public Connection() {
        initComponents();
        tblHosts.setDefaultRenderer(Delete.class, new TablePanelRenderer());
//        tblHosts.getColumn(0).setWidth(20);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblLogo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtIP = new javax.swing.JTextField();
        lblPORT = new javax.swing.JLabel();
        txtPORT = new javax.swing.JTextField();
        chkSave = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHosts = new javax.swing.JTable();
        btnConnect = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo.png"))); // NOI18N

        jLabel1.setText("CUSTOM");

        jLabel2.setText("IP");

        txtIP.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtIPFocusGained(evt);
            }
        });

        lblPORT.setText("PORT");

        txtPORT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPORTFocusGained(evt);
            }
        });

        chkSave.setText("Save");

        jLabel3.setText("HOST");

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tblHosts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Server Name", "IP:PORT", "Remove"
            }
        ) {
            Class[] types = new Class [] {
				java.lang.String.class, java.lang.String.class, Delete.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHosts.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblHosts.getColumnModel().getColumn(0).setPreferredWidth(220);
        tblHosts.getColumnModel().getColumn(0).setWidth(220);
        tblHosts.getColumnModel().getColumn(1).setPreferredWidth(220);
        tblHosts.getColumnModel().getColumn(1).setWidth(220);
        tblHosts.getColumnModel().getColumn(2).setPreferredWidth(55);
        tblHosts.getColumnModel().getColumn(2).setWidth(55);
        tblHosts.getTableHeader().setResizingAllowed(false);
        tblHosts.getTableHeader().setReorderingAllowed(false);
        tblHosts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHostsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblHosts);

        btnConnect.setText("Connect");
        btnConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIP, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblPORT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPORT, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(chkSave))
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane1)
                    .addComponent(btnConnect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblLogo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPORT)
                    .addComponent(txtPORT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkSave))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnConnect)
                .addGap(0, 10, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectActionPerformed
        String IP_PORT ="";
        int row =tblHosts.getSelectedRow();
        if (row == -1) {
            IP_PORT =txtIP.getText()+":"+txtPORT.getText();
        } else {
            IP_PORT =(String)tblHosts.getModel().getValueAt(row, 1);
        }
		
        if (row == -1 && txtIP.getText().isEmpty()) {
        } else {
            ConnectionControl.INSTANCE.connect(IP_PORT, row == -1 && chkSave.isSelected());
        }
    }//GEN-LAST:event_btnConnectActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        List<HostEntry> entries =ConnectionControl.INSTANCE.getHostData();
        LOGGER.log(Level.INFO, "Entries: "+entries.size());
        
        final DefaultTableModel dtm =(DefaultTableModel)tblHosts.getModel();
        
        for (int i=0; i<dtm.getRowCount(); i++) {dtm.removeRow(i);}
        for (int i=0; i<dtm.getRowCount(); i++) {dtm.removeRow(i);}
        
        for (final HostEntry entry: entries) {
            (new Thread(new Runnable() {
                @Override
                public void run() {
                    if (entry.isActive()) {
                        dtm.addRow(entry.getData());
                    }
                }
            })).start();
        }
    }//GEN-LAST:event_formWindowActivated

    private void txtIPFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIPFocusGained
        tblHosts.clearSelection();
    }//GEN-LAST:event_txtIPFocusGained

    private void txtPORTFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPORTFocusGained
        tblHosts.clearSelection();
    }//GEN-LAST:event_txtPORTFocusGained

    private void tblHostsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHostsMouseClicked
        int row =tblHosts.getSelectedRow();
        int column =tblHosts.getSelectedColumn();
        if (row >= 0 && column == 2) {
            ConnectionControl.INSTANCE.removeHostDataRow(row);
            ((DefaultTableModel)tblHosts.getModel()).removeRow(row);
        }
    }//GEN-LAST:event_tblHostsMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConnect;
    private javax.swing.JCheckBox chkSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblPORT;
    private javax.swing.JTable tblHosts;
    private javax.swing.JTextField txtIP;
    private javax.swing.JTextField txtPORT;
    // End of variables declaration//GEN-END:variables
}