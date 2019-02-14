/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author schmidtu
 */
public class Bildermanager extends javax.swing.JDialog {

    /**
     * Creates new form Bildermanager
     * @param parent
     * @param modal
     */
    public Bildermanager(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.PicPath = "W:\\diaf003bilder\\Verpackung\\";
        initComponents();
    }
    /**
     * Creates new form Bildermanager
     * @param parent
     * @param modal
     * @param anArticleNumber
     */
    public Bildermanager(java.awt.Frame parent, boolean modal, String anArticleNumber, int aCountOfPictures) {
        super(parent, modal);
        PicPath = "W:\\diaf003bilder\\Verpackung\\";
        ArticleNumber = anArticleNumber;
        initComponents();
        do_postBuild();
        setLocationRelativeTo(getParent());
        do_loadPictureNamesIntoTable(ArticleNumber);
    }

    String PicPath;
    String ArticleNumber;
    /** Instanz von <code>DefaultTableModel</code> */
    DefaultTableModel myTableModel;
    
    private void do_postBuild() {
        myTableModel = (DefaultTableModel) jTable_verfuegbareBilder.getModel();
        lbl_picturePath.setText(PicPath);               
        jTable_verfuegbareBilder.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
            if(jTable_verfuegbareBilder.getSelectedRow() > -1) {
                do_showSelectedPicture(PicPath + myTableModel.getValueAt(jTable_verfuegbareBilder.getSelectedRow(), 0));
                btn_deletePicture.setEnabled(true);
                btn_renamePicture.setEnabled(true);
            }
            else {btn_deletePicture.setEnabled(false);
                btn_renamePicture.setEnabled(false);}
        });
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_verfuegbareBilder = new javax.swing.JTable();
        jPanel_Vorschau = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btn_close = new javax.swing.JButton();
        btn_deletePicture = new javax.swing.JButton();
        btn_renamePicture = new javax.swing.JButton();
        lbl_picturePath = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(204, 255, 255));

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));

        jTable_verfuegbareBilder.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Verfügbare Bilder:"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable_verfuegbareBilder.setOpaque(false);
        jTable_verfuegbareBilder.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jTable_verfuegbareBilder);

        jPanel_Vorschau.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel_VorschauLayout = new javax.swing.GroupLayout(jPanel_Vorschau);
        jPanel_Vorschau.setLayout(jPanel_VorschauLayout);
        jPanel_VorschauLayout.setHorizontalGroup(
            jPanel_VorschauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 674, Short.MAX_VALUE)
        );
        jPanel_VorschauLayout.setVerticalGroup(
            jPanel_VorschauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel3.setOpaque(false);

        btn_close.setText("Schließen");
        btn_close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_closeActionPerformed(evt);
            }
        });

        btn_deletePicture.setText("Bild löschen");
        btn_deletePicture.setEnabled(false);
        btn_deletePicture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deletePictureActionPerformed(evt);
            }
        });

        btn_renamePicture.setText("Bild umbenennen");
        btn_renamePicture.setEnabled(false);
        btn_renamePicture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_renamePictureActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_close)
                .addGap(134, 134, 134)
                .addComponent(btn_renamePicture)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_deletePicture)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_close)
                    .addComponent(btn_deletePicture)
                    .addComponent(btn_renamePicture))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lbl_picturePath.setText("jLabel1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_picturePath, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel_Vorschau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel_Vorschau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lbl_picturePath)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_closeActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btn_closeActionPerformed

    private void btn_renamePictureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_renamePictureActionPerformed
        // TODO add your handling code here:
        do_renameSelectedPicture(PicPath + myTableModel.getValueAt(jTable_verfuegbareBilder.getSelectedRow(), 0));
    }//GEN-LAST:event_btn_renamePictureActionPerformed

    private void btn_deletePictureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deletePictureActionPerformed
        // TODO add your handling code here:
        do_deleteSelectedPicture(PicPath + myTableModel.getValueAt(jTable_verfuegbareBilder.getSelectedRow(), 0));
    }//GEN-LAST:event_btn_deletePictureActionPerformed

    private void do_loadPictureNamesIntoTable(String anArticleNumber) {   
        myTableModel.setRowCount(0);       
//        do_clearePicturePreview(); 
        File f = new File(PicPath);
        File [] myFileList;
        myFileList = f.listFiles();
        int myCountOfFiles = myFileList.length;
        String[] allPictures = new String[1];
        String currentPiture;
        
        for (int currentIndex = 0; currentIndex < myCountOfFiles; currentIndex++)
        {   
            if (myFileList[currentIndex].getName().toLowerCase().startsWith(anArticleNumber.toLowerCase())) { 
                currentPiture = myFileList[currentIndex].getName();
                allPictures[0] = currentPiture.substring(currentPiture.lastIndexOf("\\") + 1);
                myTableModel.addRow(allPictures);
            }
        }        
    }
    private void do_clearePicturePreview() {
        jPanel_Vorschau.removeAll();
        jPanel_Vorschau.repaint();
    }
    private void do_showSelectedPicture(String aPictureFile) {
        do_clearePicturePreview();
        BufferedImage myImage;
        Image scaledImage;
        File myPictureFile = new File(aPictureFile);
        float width = jPanel_Vorschau.getWidth(); //674;
        float height = jPanel_Vorschau.getHeight(); //509;
        float original_width;
        float original_height;
        float new_width;
        float new_height;
            try {
                myImage = ImageIO.read(myPictureFile);
                original_width = myImage.getWidth();
                original_height = myImage.getHeight();
                if (original_width > original_height) {
                    new_width = (original_height / original_width) * width;
                    new_height = (original_height / original_width) * height;
                }
                else {
                    new_width = (original_width / original_height) * width;
                    new_height = (original_width / original_height) * height;
                }
                scaledImage = myImage.getScaledInstance(Math.round(new_width), Math.round(new_height), Image.SCALE_FAST);
//                        scaledImage = myImage.getScaledInstance(380, 350, Image.SCALE_FAST );

                JLabel label = new JLabel(new ImageIcon(scaledImage));
                label.setLocation(10, 10);
                label.setSize(Math.round(new_width), Math.round(new_height)); //jPanel_Bilder.getHeight() - 25);
//                        label.setSize(380, 350); //jPanel_Bilder.getHeight() - 25);
                jPanel_Vorschau.add(label);
                jPanel_Vorschau.repaint();

            } catch (IOException ex) {
                Logger.getLogger(Frame_Verpackungsvorschrift.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    private void do_renameSelectedPicture(String aPictureFile) {
        File myOldPictureName = new File(aPictureFile);
        Object myNewName = JOptionPane.showInputDialog(this, "Neuer Namen (ohne Dateiendung!):", "Bild umbenennen",
                JOptionPane.INFORMATION_MESSAGE); 
//        if (myNewName == null) {
//                JOptionPane.showMessageDialog(null,
//                "Bitte geben Sie einen Namen ein.", 
//                "Fehler",
//                JOptionPane.ERROR_MESSAGE);
//                return;
//        }
        if (myNewName != null && !myNewName.equals(myTableModel.getValueAt(jTable_verfuegbareBilder.getSelectedRow(), 0))) {
            File myNewPictureName = new File(PicPath + myNewName.toString() + ".jpg");
            boolean success = myOldPictureName.renameTo(myNewPictureName);
            if (!success) {
                JOptionPane.showMessageDialog(null,
                "Das Bild konnte nicht umbenannt werden. Bitte wählen Sie einen anderen Namen, dieser muss mit dem nächst höheren Buchstaben enden.", 
                "Fehler",
                JOptionPane.ERROR_MESSAGE);
            }
            do_loadPictureNamesIntoTable(ArticleNumber);
        }
    }
    
    private void do_deleteSelectedPicture(String aPictureFile) {        
        File myPictureFile = new File(aPictureFile);
        int myAnswer = JOptionPane.showConfirmDialog(this,
            "Soll das Bild >> " + myTableModel.getValueAt(jTable_verfuegbareBilder.getSelectedRow(), 0) + " << wirklich gelöscht werden?",
            "Bild löschen?",
            JOptionPane.YES_NO_OPTION);
        if (myAnswer == 0) {
            boolean myResult = myPictureFile.delete();        
            if (myResult) {
                JOptionPane.showMessageDialog(null,
                "Das Bild wurde gelöscht.", 
                "Rückmeldung",
                JOptionPane.INFORMATION_MESSAGE);    
                do_loadPictureNamesIntoTable(ArticleNumber);
            }
        }
        do_clearePicturePreview();
    }
    
//    public boolean isCellEditable(int row, int col) {
//        if (col == 0) {
//            return true;
//        }
//        return true;
//    }
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
            java.util.logging.Logger.getLogger(Bildermanager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Bildermanager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Bildermanager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Bildermanager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            Bildermanager dialog = new Bildermanager(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_close;
    private javax.swing.JButton btn_deletePicture;
    private javax.swing.JButton btn_renamePicture;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel_Vorschau;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_verfuegbareBilder;
    private javax.swing.JLabel lbl_picturePath;
    // End of variables declaration//GEN-END:variables
}