/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;
import java.util.*;
import java.io.File;
import javax.swing.JOptionPane;
/**
 *
 * @author schmidtu
 */
public class AV_MM_MainFrame extends javax.swing.JFrame {

    public String [] stringOfFiles; 
    /**
     * Creates new form AV_MM_MainFrame
     */
    public AV_MM_MainFrame() {
        initComponents();
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
        ToolBar_MainFrame = new javax.swing.JToolBar();
        btn_close = new javax.swing.JButton();
        panel_modulConverter = new javax.swing.JPanel();
        lbl_info_ModulConverter = new javax.swing.JLabel();
        btn_openModul_Converter = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(20, 20, 0, 0));

        ToolBar_MainFrame.setRollover(true);

        btn_close.setText("Schließen");
        btn_close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_closeActionPerformed(evt);
            }
        });

        panel_modulConverter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lbl_info_ModulConverter.setText("Modul: Konverter Excel -> PDF");

        btn_openModul_Converter.setText("Modul starten: Konverter");
        btn_openModul_Converter.setToolTipText("Öffnet das Modul zum Umwandeln von Excel-Dateien -> PDF.");
        btn_openModul_Converter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_openModul_ConverterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_modulConverterLayout = new javax.swing.GroupLayout(panel_modulConverter);
        panel_modulConverter.setLayout(panel_modulConverterLayout);
        panel_modulConverterLayout.setHorizontalGroup(
            panel_modulConverterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_modulConverterLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panel_modulConverterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_openModul_Converter, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_info_ModulConverter))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        panel_modulConverterLayout.setVerticalGroup(
            panel_modulConverterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_modulConverterLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_info_ModulConverter)
                .addGap(18, 18, 18)
                .addComponent(btn_openModul_Converter, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 954, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(ToolBar_MainFrame, javax.swing.GroupLayout.PREFERRED_SIZE, 904, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_close)
                        .addComponent(panel_modulConverter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(40, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 722, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(ToolBar_MainFrame, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(panel_modulConverter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 480, Short.MAX_VALUE)
                    .addComponent(btn_close)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 499, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private String [] getArrayOfFiles(String aPath) {
        int myCountOfFiles;
        File f = new File(aPath);
        myCountOfFiles = f.list().length;
        Vector <String> tempVector_allNeeded = new Vector <String> ();
        for (int currentIndex = 0; currentIndex < 10; currentIndex++)
        {   
//            System.out.println(currentIndex);
            if (f.list()[currentIndex].startsWith("kp"))
            {   
                if (!(f.list()[currentIndex].contains("vk")))
                {
                    if ((f.list()[currentIndex].endsWith(".xls")) || (f.list()[currentIndex].endsWith(".xlsx"))) 
                    {
                        tempVector_allNeeded.add(f.list()[currentIndex]);
                    }
                }
            }
        }        
        String [] stringArrayOfFiles = new String [tempVector_allNeeded.size()];        
        for ( int index=0; index < (tempVector_allNeeded.size()); ++index)
        {
            stringArrayOfFiles[index] = tempVector_allNeeded.elementAt(index);
        }
        Arrays.sort(stringArrayOfFiles);
        return stringArrayOfFiles;
    }
/**
 *
 * @param anArrayOfStrings
 * @return
 */
/*    private static String getFilesAsString(String anArrayOfStrings[]) {
        String stringOfFiles;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < anArrayOfStrings.length; i++) {
           // sb.append("\"");            
            sb.append(anArrayOfStrings[i]);
            sb.append(",");   
            sb.append("\n");                     
        }
        stringOfFiles = sb.toString();
        return stringOfFiles;
        }
*/
    private void btn_openModul_ConverterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_openModul_ConverterActionPerformed
        // TODO add your handling code here:
        
        String pathWithGreenMaps = "U:\\Ablage\\kunst_mi\\excel\\materialwirtschaft - produktion\\arbeitsvorbereitung\\1stammdaten";    
        //FilesAppendedToString allFiles = new FilesAppendedToString(pathWithGreenMaps);
        String [] allFiles;
        allFiles = getArrayOfFiles(pathWithGreenMaps);
        //if (allFiles instanceof String) {
        //    System.out.println(allFiles);
    //}
        ConverterFrame converterFrame = new ConverterFrame(allFiles);
        converterFrame.setTitle("Datei-Konverter");
        converterFrame.setSize(1024,900); 
        converterFrame.setVisible(true);  
    }//GEN-LAST:event_btn_openModul_ConverterActionPerformed

    private void btn_closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_closeActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btn_closeActionPerformed
 
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AV_MM_MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new AV_MM_MainFrame().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToolBar ToolBar_MainFrame;
    private javax.swing.JButton btn_close;
    private javax.swing.JButton btn_openModul_Converter;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbl_info_ModulConverter;
    private javax.swing.JPanel panel_modulConverter;
    // End of variables declaration//GEN-END:variables
}
