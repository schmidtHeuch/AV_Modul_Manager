/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Comparators.DateStringComparator;
import DBTables.GET_ResultSet_From_DBTableData;
import DBTools.DB_ConnectionManager;
import JTable.ColorTableCellRenderer;
import java.awt.Color;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
//import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableModelEvent;
//import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
//import javax.swing.table.TableRowSorter;

/**
 *
 * @author schmidtu
 * erstellt:  15.01.2019
 * @version 1.01
 * letzte Änderung: 14.02.2019
 * @since 1.8.0
 */
public class Frame_PersonalAnwesenheit extends javax.swing.JFrame {
    static int InstanceCount;

    /**
     * Creates new form Frame_PersonalAnwesenheit
     */
    public Frame_PersonalAnwesenheit() {
        this.DateFormat = new SimpleDateFormat("dd.MM.yyyy");
    }
    public Frame_PersonalAnwesenheit(String aSchichtNummerWithName, String aSchicht) {
        Version = "1.01";
        this.DateFormat = new SimpleDateFormat("dd.MM.yyyy");
        InstanceCount = InstanceCount + 1;
        SchichtNummerWithName = aSchichtNummerWithName;
        SchichtNummer = aSchichtNummerWithName.substring(0, 9);
        Schicht = aSchicht;
        if(Schicht.equals("Frühschicht")) { SchichtKuerzel = "S1";}
        if(Schicht.equals("Spätschicht")) { SchichtKuerzel = "S2";}
        if(Schicht.equals("Nachtschicht")) { SchichtKuerzel = "S3";}
        initComponents();
        do_postBuild();
        Count = get_countOfSelectedValues();
        lbl_rowCountWithSelectedValues.setText(Count + " von " + String.valueOf(myTableModel_Mitarbeiter.getRowCount()));
    }
    public int get_Instance() {
        return InstanceCount;
    }
    String Version;
    /** Instanz von <code>DefaultTableModel</code> */
    DefaultTableModel myTableModel_Mitarbeiter;
    /** Instanz von <code>MyDBDataGetter</code> */
    GET_ResultSet_From_DBTableData MyDBDataGetter;
//    /** Instanz von <code>TableRowSorter</code> */
//    TableRowSorter<DefaultTableModel> mySorter;
    /** Instanz von <code>ColorTableCellRenderer</code> */
    ColorTableCellRenderer ctcr;
    String SchichtNummerWithName; // Schicht 1 (Ellenbruch) ... 
    String SchichtNummer; // Schicht 1 ...
    String Schicht; // Frühschicht ...
    String SchichtKuerzel; // S1, S2, S3
    Date Speicherdatum;
    String Speicherdatum_asString;
    int Count = 0; // zählt die selektierten Mitarbeiter
    DateStringComparator myComparator = new DateStringComparator();
    boolean myAnswerIfConnected;
    Connection myConnection;
    DB_ConnectionManager MY_DBCM;
    DateFormat DateFormat;
/** executeUpdate Rückgabewert:
*   Ein int-Wert, der die Anzahl der betroffenen Zeilen angibt, oder „0“ bei Verwendung einer DDL-Anweisung.
**/
    int statementResult;
    
    private void do_postBuild(){ 
        lbl_version.setText("Version:" + Version);
        lbl_schichtNummerWithName.setText(SchichtNummerWithName);
        lbl_schicht.setText(Schicht);        
        
        lbl_currentDate.setText("Aktuelles Datum: " + DateFormat.format(new Date())); // aktuelles Datum
        
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss"); // "yyyy.MM.dd - HH:mm:ss ");
        Date currentTime = new Date();

//            Rückgabewerte:            
//            Das Objekt ist kleiner als das übergebene Objekt: Negativer int-Wert, z.B. -1
//            Das Objekt ist gleich groß wie das übergebene Objekt: Null als Zahl: 0
//            Das Objekt ist größer als das übergebene Objekt: Positiver int-Wert, z.B. 1
            
        int compResultStart = myComparator.compare(timeFormat.format(currentTime), "00:00:00");  
        int compResultEnde = myComparator.compare(timeFormat.format(currentTime), "07:59:59");  
        if (Schicht.equals("Nachtschicht") && compResultStart == 1 && compResultEnde == -1) {   
            
             // create Calendar instance with actual date
            Date now = new Date();
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(now);

            // add -1 days to calendar instance
            calendar.add(Calendar.DAY_OF_MONTH, -1);

            // get the date instance
            Speicherdatum = calendar.getTime();
//            Speicherdatum = calendar.getTime();
        }
        else {Speicherdatum = new Date();}
        Speicherdatum_asString = DateFormat.format(Speicherdatum);
//        System.out.println(compResultStart + "<->" + compResultEnde);
        lbl_DateForSaveDataSet.setText("Speicherdatum:     " + DateFormat.format(Speicherdatum)); // Speicherdatum, für S3 aktuell - 1 Tag (wenn nach 24 Uhr)
        myTableModel_Mitarbeiter = (DefaultTableModel) jTable_Mitarbeiter.getModel();
        get_DBTableData();   
        
        TableColumn colVon = jTable_Mitarbeiter.getColumnModel().getColumn(4);
        JComboBox comboBox_von = new JComboBox();
        do_fillComboBox(lbl_schicht.getText(), comboBox_von);
        colVon.setCellEditor(new DefaultCellEditor(comboBox_von));
        
        TableColumn colBis = jTable_Mitarbeiter.getColumnModel().getColumn(5);
        JComboBox comboBox_bis = new JComboBox();
        do_fillComboBox(lbl_schicht.getText(), comboBox_bis);
        colBis.setCellEditor(new DefaultCellEditor(comboBox_bis));
                        
        TableColumn Bemerkungen = jTable_Mitarbeiter.getColumnModel().getColumn(6);
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("");
        comboBox.addItem("Erster Tag");
        comboBox.addItem("Konfi");
        comboBox.addItem("krank");
        comboBox.addItem("Maschine mit 2 Personen");
        comboBox.addItem("Rollenwechsler");
        comboBox.addItem("Schichtführer Vertretung");
        comboBox.addItem("Urlaub (HT)");
        comboBox.addItem("Versorger");
        Bemerkungen.setCellEditor(new DefaultCellEditor(comboBox));
                      
        myTableModel_Mitarbeiter.addTableModelListener((TableModelEvent ev) -> {                
            Count = get_countOfSelectedValues();
            lbl_rowCountWithSelectedValues.setText(Count + " von " + String.valueOf(myTableModel_Mitarbeiter.getRowCount()));
        });
//        jTextPane_Kurzanleitung.setContentType("text/html");
//        jTextPane_Kurzanleitung.setText("<html><body>Kurzanleitung:<p>1. Anwesende erfassen:<br>Haken in der 1. Spalte 'Anwesend?' setzen und bei Bedarf alle Informationen setzen.<br>Das Feld 'Bemerkungen' gilt für alle pro Schicht.<br>Auf 'Speichern' klicken.</p>" +
//            "<p>2. Anwesende ändern:<br>Haken in der 1. Spalte 'Anwesend?' gesetzt lassen und bei Bedarf Informationen ändern.<br>Auf 'Speichern' klicken.</p>" + 
//            "<p>3. Anwesende löschen:<br>Haken in der 1. Spalte 'Anwesend?' entfernen.<br>Auf 'Speichern' klicken.</p></body></html>");
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel_base = new javax.swing.JPanel();
        lbl_titel = new javax.swing.JLabel();
        jPanelSchicht = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Mitarbeiter = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea_Bemerkungen = new javax.swing.JTextArea();
        lbl_rowCountWithSelectedValues = new javax.swing.JLabel();
        lbl_currentDate = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lbl_schichtNummerWithName = new javax.swing.JLabel();
        lbl_schicht = new javax.swing.JLabel();
        lbl_DateForSaveDataSet = new javax.swing.JLabel();
        btn_save = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextPane_Kurzanleitung = new javax.swing.JTextPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jPanel_footer = new javax.swing.JPanel();
        btn_close = new javax.swing.JButton();
        lbl_version = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 255, 255));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel_base.setBackground(new java.awt.Color(204, 255, 255));

        lbl_titel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl_titel.setText("Erfassung der Personal-Anwesenheit");

        jPanelSchicht.setBackground(new java.awt.Color(204, 255, 255));
        jPanelSchicht.setOpaque(false);

        jTable_Mitarbeiter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Anwesend?", "Name", "Vorname", "PN", "von", "bis", "Besonderheiten"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable_Mitarbeiter.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable_Mitarbeiter.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable_Mitarbeiter);
        if (jTable_Mitarbeiter.getColumnModel().getColumnCount() > 0) {
            jTable_Mitarbeiter.getColumnModel().getColumn(0).setResizable(false);
            jTable_Mitarbeiter.getColumnModel().getColumn(0).setPreferredWidth(100);
            jTable_Mitarbeiter.getColumnModel().getColumn(1).setResizable(false);
            jTable_Mitarbeiter.getColumnModel().getColumn(1).setPreferredWidth(150);
            jTable_Mitarbeiter.getColumnModel().getColumn(2).setResizable(false);
            jTable_Mitarbeiter.getColumnModel().getColumn(2).setPreferredWidth(150);
            jTable_Mitarbeiter.getColumnModel().getColumn(3).setResizable(false);
            jTable_Mitarbeiter.getColumnModel().getColumn(3).setPreferredWidth(40);
            jTable_Mitarbeiter.getColumnModel().getColumn(4).setResizable(false);
            jTable_Mitarbeiter.getColumnModel().getColumn(4).setPreferredWidth(60);
            jTable_Mitarbeiter.getColumnModel().getColumn(5).setResizable(false);
            jTable_Mitarbeiter.getColumnModel().getColumn(5).setPreferredWidth(70);
            jTable_Mitarbeiter.getColumnModel().getColumn(6).setResizable(false);
            jTable_Mitarbeiter.getColumnModel().getColumn(6).setPreferredWidth(250);
        }

        jTextArea_Bemerkungen.setColumns(1);
        jTextArea_Bemerkungen.setLineWrap(true);
        jTextArea_Bemerkungen.setRows(1);
        jTextArea_Bemerkungen.setTabSize(5);
        jTextArea_Bemerkungen.setBorder(javax.swing.BorderFactory.createTitledBorder("Bemerkungen"));
        jScrollPane2.setViewportView(jTextArea_Bemerkungen);

        lbl_rowCountWithSelectedValues.setText("jLabel1");

        lbl_currentDate.setText("jLabel1");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setOpaque(false);

        lbl_schichtNummerWithName.setText("jLabel1");

        lbl_schicht.setText("jLabel1");

        lbl_DateForSaveDataSet.setText("jLabel1");

        btn_save.setText("Speichern");
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_save, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_schicht)
                            .addComponent(lbl_schichtNummerWithName)
                            .addComponent(lbl_DateForSaveDataSet))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_DateForSaveDataSet)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_schichtNummerWithName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_schicht)
                .addGap(18, 18, 18)
                .addComponent(btn_save)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTextPane_Kurzanleitung.setEditable(false);
        jTextPane_Kurzanleitung.setBackground(new java.awt.Color(204, 255, 255));
        jTextPane_Kurzanleitung.setContentType("text/html"); // NOI18N
        jTextPane_Kurzanleitung.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jTextPane_Kurzanleitung.setText("Kurzanleitung:<p>1. Anwesende erfassen:<br><br>Haken in der 1. Spalte 'Anwesend?' setzen und bei Bedarf alle Informationen setzen.<br>Das Feld 'Bemerkungen' gilt für alle pro Schicht.<br>Auf 'Speichern' klicken.</p><br>\n            <p>2. Anwesende ändern:<br><br>Haken in der 1. Spalte 'Anwesend?' gesetzt lassen und bei Bedarf Informationen ändern.<br>Auf 'Speichern' klicken.</p><br>\n            <p>3. Anwesende löschen:<br><br>Haken in der 1. Spalte 'Anwesend?' entfernen.<br>Auf 'Speichern' klicken.</p></body></html>");
        jScrollPane4.setViewportView(jTextPane_Kurzanleitung);

        jTextPane1.setEditable(false);
        jTextPane1.setBackground(new java.awt.Color(204, 255, 255));
        jTextPane1.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        jTextPane1.setText("Schlüssel sind:\n-Tag\n- Schicht\n- Name\nDas heißt, ein Mitarbeiter kann pro Tag und pro Schicht nur einmal als anwesend erfasst werden.");
        jScrollPane5.setViewportView(jTextPane1);

        javax.swing.GroupLayout jPanelSchichtLayout = new javax.swing.GroupLayout(jPanelSchicht);
        jPanelSchicht.setLayout(jPanelSchichtLayout);
        jPanelSchichtLayout.setHorizontalGroup(
            jPanelSchichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSchichtLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSchichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelSchichtLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lbl_rowCountWithSelectedValues))
                    .addGroup(jPanelSchichtLayout.createSequentialGroup()
                        .addComponent(lbl_currentDate)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelSchichtLayout.createSequentialGroup()
                        .addGroup(jPanelSchichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelSchichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE)
                            .addComponent(jScrollPane2))))
                .addContainerGap())
        );
        jPanelSchichtLayout.setVerticalGroup(
            jPanelSchichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSchichtLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_currentDate)
                .addGap(5, 5, 5)
                .addComponent(lbl_rowCountWithSelectedValues)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelSchichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelSchichtLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelSchichtLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel_footer.setOpaque(false);

        btn_close.setText("Schließen");
        btn_close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_closeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_footerLayout = new javax.swing.GroupLayout(jPanel_footer);
        jPanel_footer.setLayout(jPanel_footerLayout);
        jPanel_footerLayout.setHorizontalGroup(
            jPanel_footerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_footerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_close)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_footerLayout.setVerticalGroup(
            jPanel_footerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_footerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_close)
                .addContainerGap())
        );

        lbl_version.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        lbl_version.setForeground(new java.awt.Color(204, 204, 204));
        lbl_version.setText("xfdghjgfhjdghjdgjdgjdgjdgj");

        javax.swing.GroupLayout jPanel_baseLayout = new javax.swing.GroupLayout(jPanel_base);
        jPanel_base.setLayout(jPanel_baseLayout);
        jPanel_baseLayout.setHorizontalGroup(
            jPanel_baseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_footer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel_baseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_baseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_baseLayout.createSequentialGroup()
                        .addComponent(lbl_titel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_version))
                    .addComponent(jPanelSchicht, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel_baseLayout.setVerticalGroup(
            jPanel_baseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_baseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_baseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_titel)
                    .addComponent(lbl_version))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelSchicht, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel_footer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_base, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_base, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        InstanceCount = 0;
        dispose();
    }//GEN-LAST:event_formWindowClosing

    private void btn_closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_closeActionPerformed
        // TODO add your handling code here:
        InstanceCount = 0;
        dispose();
    }//GEN-LAST:event_btn_closeActionPerformed

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        // TODO add your handling code here:
        do_saveDataInDB();
    }//GEN-LAST:event_btn_saveActionPerformed

    private void get_DBTableData() {
        get_DBTableData_Mitarbeiter();
        get_DBTableData_Anwesenheit();
    }
    private void get_DBTableData_Mitarbeiter() {
        MyDBDataGetter = new GET_ResultSet_From_DBTableData("HV-ABAS-SQL", "SyncDiaf", "V_diaf_Miatarbeiter", "View");
        String [][] myResultAsArray = MyDBDataGetter.getResultAsArray();
        int allOldRows = myTableModel_Mitarbeiter.getRowCount();
        if (allOldRows > 0) {
            myTableModel_Mitarbeiter.setRowCount(0);
        }
        if (myResultAsArray != null) {
            int x = 0;
            for (int i=0; i<myResultAsArray.length; i++) {
                if (myResultAsArray[i][4].trim().equals(SchichtNummer)) {
                        myResultAsArray[i][4] = "";
                        myTableModel_Mitarbeiter.addRow(myResultAsArray[i]); 
                        x = x + 1;
                }
            }
        }

        ctcr = new ColorTableCellRenderer();
        jTable_Mitarbeiter.getColumnModel().getColumn(1).setCellRenderer(ctcr);
        int lastRow_Festangestellte = myTableModel_Mitarbeiter.getRowCount();
        for (int myRow = 0; myRow < lastRow_Festangestellte; myRow ++) {
                ctcr.setColor(myRow,1,Color.ORANGE);
        }
        
        if (myResultAsArray != null) {
            int x = 0;
            for (int i=0; i<myResultAsArray.length; i++) {
                if (myResultAsArray[i][4].trim().equals("Aush. Prod.") || myResultAsArray[i][4].trim().equals("Leiharbeiter")) {
                    if (test_isDataSetInTable(myResultAsArray[i][1]) == false) {
                        myResultAsArray[i][4] = "";
                        myTableModel_Mitarbeiter.addRow(myResultAsArray[i]); 
                        x = x + 1;
                    }
                }
            }
        }
        
        for (int i = 0; i < jTable_Mitarbeiter.getRowCount(); ++i ) {
            myTableModel_Mitarbeiter.setValueAt(false, i, 0);
        } 
//        jTable_Mitarbeiter.setSelectionForeground(Color.yellow);    // [184,207,229]
        lbl_rowCountWithSelectedValues.setText(Count + " von " + String.valueOf(myTableModel_Mitarbeiter.getRowCount()));
    }
    private void get_DBTableData_Anwesenheit() {
        MyDBDataGetter = new GET_ResultSet_From_DBTableData("HV-ABAS-SQL", "DiafBDE", "T_PersonalAnwesenheitProdWIII", "View");
        String [][] myResultAsArray = MyDBDataGetter.getResultAsArray();
        
        if (myResultAsArray != null && myResultAsArray.length > 0) {
            for (int myRow = 0; myRow < jTable_Mitarbeiter.getRowCount(); myRow++ ) {
                for (int myDS = 0; myDS < myResultAsArray.length; myDS++ ) {
                    if ((myResultAsArray[myDS][0].trim().equals(SchichtKuerzel)) &&
                            (myResultAsArray[myDS][1].trim().equals(Speicherdatum_asString)) &&
                            (myResultAsArray[myDS][6].trim().equals(myTableModel_Mitarbeiter.getValueAt(myRow, 3)))) {
                        myTableModel_Mitarbeiter.setValueAt(true, myRow, 0);
                        myTableModel_Mitarbeiter.setValueAt(myResultAsArray[myDS][7], myRow, 4);
                        myTableModel_Mitarbeiter.setValueAt(myResultAsArray[myDS][8], myRow, 5);
                        myTableModel_Mitarbeiter.setValueAt(myResultAsArray[myDS][9], myRow, 6);
                        jTextArea_Bemerkungen.setText(myResultAsArray[myDS][10]);
                    }
                }
            }
        }
    }
    private void getDBConnection() { 
        MY_DBCM = new DB_ConnectionManager("HV-ABAS-SQL", "DiafBDE", "true", "CONNECT");
        if (!MY_DBCM.isConnnected()) {
            JOptionPane.showMessageDialog(null,
                    "Der Verbindungs-Aufbau zur Datenbank ist gescheitert. Bitte wenden Sie sich an den Entwickler.",
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE);
        }        
    }
    
    private void do_saveDataInDB() {  
        
        do_insertDataSet_intoDB();        
        do_updateDataSet_inDB();
        
        statementResult = -1;
        statementResult = do_deleteDataSet_inDB();
        if (statementResult == 0) {
            JOptionPane.showMessageDialog(null,
            "Dieser Datensatz kann nicht gelöscht werden, da er von anderen Daten referenziert wird.");
        }  
        
        get_DBTableData_Mitarbeiter();
        get_DBTableData_Anwesenheit();
    }
    private void do_insertDataSet_intoDB() {        
        try
        {                            
            getDBConnection();
            MY_DBCM.setConnection_CLOSED("HV-ABAS-SQL", "DiafBDE", "true", "DISCONNECT");
            MY_DBCM = new DB_ConnectionManager("HV-ABAS-SQL", "DiafBDE", "true", "CONNECT");
            String von;
            String bis;
            String Besonderheiten;
            if (MY_DBCM.isConnnected()) 
            {    
                myConnection = MY_DBCM.getConnection();
            
                for (int myRow = 0; myRow < jTable_Mitarbeiter.getRowCount(); myRow++ ) {
                    boolean theAnswer = test_isDataSetInDB(SchichtKuerzel, Speicherdatum_asString, myTableModel_Mitarbeiter.getValueAt(myRow, 3).toString().trim());
                    if (myTableModel_Mitarbeiter.getValueAt(myRow, 0).equals(true) && theAnswer == false) {
                        if (myTableModel_Mitarbeiter.getValueAt(myRow, 4) == null) 
                            {von = "";} else {von = myTableModel_Mitarbeiter.getValueAt(myRow, 4).toString();}
                        if (myTableModel_Mitarbeiter.getValueAt(myRow, 5) == null) 
                            {bis = "";} else {bis = myTableModel_Mitarbeiter.getValueAt(myRow, 5).toString();}
                        if (myTableModel_Mitarbeiter.getValueAt(myRow, 6) == null) 
                            {Besonderheiten = "";} else {Besonderheiten = myTableModel_Mitarbeiter.getValueAt(myRow, 6).toString();}
                        Statement myStatement = myConnection.createStatement();
                        myStatement.executeUpdate("INSERT INTO DiafBDE.dbo.T_PersonalAnwesenheitProdWIII (pKey_Schicht, pKey_Datum, SchichtNummer,"
                            + " Anwesend, Name, Vorname, pKey_Personalnummer, von, bis, Besonderheiten, Bemerkungen)" 
                            + "VALUES ('" + SchichtKuerzel + "', '" 
                            + Speicherdatum_asString + "', '" 
                            + SchichtNummer + "', '" 
                            + myTableModel_Mitarbeiter.getValueAt(myRow, 0).toString().trim() + "', '" 
                            + myTableModel_Mitarbeiter.getValueAt(myRow, 1).toString().trim() + "', '" 
                            + myTableModel_Mitarbeiter.getValueAt(myRow, 2).toString().trim() + "', '" 
                            + myTableModel_Mitarbeiter.getValueAt(myRow, 3).toString().trim() + "', '" 
                            + von + "', '" 
                            + bis + "', '" 
                            + Besonderheiten + "', '" 
                            + jTextArea_Bemerkungen.getText().trim() +"')"); 
                    }
                } 
            }
        }
        catch (/*ClassNotFoundException |*/ SQLException myException )
        {
        }
        finally {
            try {
                if (myConnection != null && !myConnection.isClosed()) {
                    myConnection.close();
                }
            } catch (SQLException myException) {
                System.out.println(myException);
            }
        }         
    }
    private void do_updateDataSet_inDB() {        
        try
        {                            
            getDBConnection();
            MY_DBCM.setConnection_CLOSED("HV-ABAS-SQL", "DiafBDE", "true", "DISCONNECT");
            MY_DBCM = new DB_ConnectionManager("HV-ABAS-SQL", "DiafBDE", "true", "CONNECT");
            String von;
            String bis;
            String Besonderheiten;
            if (MY_DBCM.isConnnected()) 
            {    
                myConnection = MY_DBCM.getConnection();
            
                for (int myRow = 0; myRow < jTable_Mitarbeiter.getRowCount(); myRow++ ) {
                    boolean theAnswer = test_isDataSetInDB(SchichtKuerzel, Speicherdatum_asString, myTableModel_Mitarbeiter.getValueAt(myRow, 3).toString().trim());
                    if (myTableModel_Mitarbeiter.getValueAt(myRow, 0).equals(true) && theAnswer == true) {
                        if (myTableModel_Mitarbeiter.getValueAt(myRow, 4) == null) 
                            {von = "";} else {von = myTableModel_Mitarbeiter.getValueAt(myRow, 4).toString();}
                        if (myTableModel_Mitarbeiter.getValueAt(myRow, 5) == null) 
                            {bis = "";} else {bis = myTableModel_Mitarbeiter.getValueAt(myRow, 5).toString();}
                        if (myTableModel_Mitarbeiter.getValueAt(myRow, 6) == null) 
                            {Besonderheiten = "";} else {Besonderheiten = myTableModel_Mitarbeiter.getValueAt(myRow, 6).toString();}
                        Statement myStatement = myConnection.createStatement();
                        myStatement.executeUpdate("UPDATE DiafBDE.dbo.T_PersonalAnwesenheitProdWIII SET von = '" + von +
                            "', bis = '" + bis +
                            "', Besonderheiten = '" + Besonderheiten +
                            "', Bemerkungen = '" + jTextArea_Bemerkungen.getText().trim() +
                            "' WHERE pKey_Schicht = '" + SchichtKuerzel +
                            "' AND pKey_Datum = '" + Speicherdatum_asString +
                            "' AND pKey_Personalnummer = '" + myTableModel_Mitarbeiter.getValueAt(myRow, 3) + "'");
                    }
                }
            }
        }
        catch (/*ClassNotFoundException |*/ SQLException myException )
        {
        }
        finally {
            try {
                if (myConnection != null && !myConnection.isClosed()) {
                    myConnection.close();
                }
            } catch (SQLException myException) {
                System.out.println(myException);
            }
        }         
    } 
    
    private int do_deleteDataSet_inDB() {        
        try
        {
            
        for (int myRow = 0; myRow < jTable_Mitarbeiter.getRowCount(); myRow++ ) {
            boolean theAnswer = test_isDataSetInDB(SchichtKuerzel, Speicherdatum_asString, myTableModel_Mitarbeiter.getValueAt(myRow, 3).toString().trim());
            if (myTableModel_Mitarbeiter.getValueAt(myRow, 0).equals(false) && theAnswer == true) {
                MY_DBCM.setConnection_CLOSED("HV-ABAS-SQL", "DiafBDE", "true", "DISCONNECT");
                MY_DBCM = new DB_ConnectionManager("HV-ABAS-SQL", "DiafBDE", "true", "CONNECT");
                if (MY_DBCM.isConnnected()) 
                {   
                myConnection = MY_DBCM.getConnection();
                Statement myStatement = myConnection.createStatement();
                statementResult = myStatement.executeUpdate("DELETE FROM DiafBDE.dbo.T_PersonalAnwesenheitProdWIII WHERE " +
                        "pKey_Schicht = '" + SchichtKuerzel +
                        "' AND pKey_Datum = '" + Speicherdatum_asString +
                        "' AND pKey_Personalnummer = '" + myTableModel_Mitarbeiter.getValueAt(myRow, 3).toString().trim() + "'");             
                }   
            }
        }
        }
        catch (/*ClassNotFoundException |*/ SQLException myException )
        {
        }
        finally {
            try {
                if (myConnection != null && !myConnection.isClosed()) {
                    myConnection.close();
                }
            } catch (SQLException myException) {
            }
        }
        return statementResult;          
    }
    private boolean test_isDataSetInDB(String aSchichtKuerzel, String aSpeicherdatum_asString, String aPersonalnummer) {
        MyDBDataGetter = new GET_ResultSet_From_DBTableData("HV-ABAS-SQL", "DiafBDE", "T_PersonalAnwesenheitProdWIII", "View");
        String [][] myResultAsArray = MyDBDataGetter.getResultAsArray();
        boolean myAnswer = false;
        
        if (myResultAsArray != null && myResultAsArray.length > 0) {
            for (int myDS = 0; myDS < myResultAsArray.length; myDS++ ) {
                if ((myResultAsArray[myDS][0].trim().equals(aSchichtKuerzel)) &&
                        (myResultAsArray[myDS][1].trim().equals(aSpeicherdatum_asString)) &&
                        (myResultAsArray[myDS][6].trim().equals(aPersonalnummer))) {
                    myAnswer = true;
                }
            }
        }
        return myAnswer;
    }
    private void do_fillComboBox(String aSchicht, JComboBox aCombobox) {
        if (aSchicht.equals("Frühschicht")) {
            aCombobox.addItem("");
            aCombobox.addItem("06:00");
            aCombobox.addItem("06:30");
            aCombobox.addItem("07:00");
            aCombobox.addItem("07:30");
            aCombobox.addItem("08:00");
            aCombobox.addItem("08:30");
            aCombobox.addItem("09:00");
            aCombobox.addItem("09:30");
            aCombobox.addItem("10:00");
            aCombobox.addItem("10:30");
            aCombobox.addItem("11:00");
            aCombobox.addItem("11:30");
            aCombobox.addItem("12:00");
            aCombobox.addItem("12:30");
            aCombobox.addItem("13:00");
            aCombobox.addItem("13:30");
            aCombobox.addItem("14:00");
        }
        if (aSchicht.equals("Nachtschicht")) {
            aCombobox.addItem("");
            aCombobox.addItem("22:00");
            aCombobox.addItem("22:30");
            aCombobox.addItem("23:00");
            aCombobox.addItem("23:30");
            aCombobox.addItem("00:00");
            aCombobox.addItem("00:30");
            aCombobox.addItem("01:00");
            aCombobox.addItem("01:30");
            aCombobox.addItem("02:00");
            aCombobox.addItem("02:30");
            aCombobox.addItem("03:00");
            aCombobox.addItem("03:30");
            aCombobox.addItem("04:00");
            aCombobox.addItem("04:30");
            aCombobox.addItem("05:00");
            aCombobox.addItem("05:30");
            aCombobox.addItem("06:00");
        }
        if (aSchicht.equals("Spätschicht")) {
            aCombobox.addItem("");
            aCombobox.addItem("14:00");
            aCombobox.addItem("14:30");
            aCombobox.addItem("15:00");
            aCombobox.addItem("15:30");
            aCombobox.addItem("16:00");
            aCombobox.addItem("16:30");
            aCombobox.addItem("17:00");
            aCombobox.addItem("17:30");
            aCombobox.addItem("18:00");
            aCombobox.addItem("18:30");
            aCombobox.addItem("19:00");
            aCombobox.addItem("19:30");
            aCombobox.addItem("20:00");
            aCombobox.addItem("20:30");
            aCombobox.addItem("21:00");
            aCombobox.addItem("21:30");
            aCombobox.addItem("22:00");
        }
    }
    private int get_countOfSelectedValues() {
        int count = 0;
        for (int i = 0; i < jTable_Mitarbeiter.getRowCount(); ++i ) {
            int myRow = jTable_Mitarbeiter.convertRowIndexToModel(i);
            if (myTableModel_Mitarbeiter.getValueAt(myRow, 0).equals(true))
                count = count + 1;           
        }
        return count;
    }
   private boolean test_isDataSetInTable(String aString) {
        boolean myAnswer = false;
        for (int i = 0; i < jTable_Mitarbeiter.getRowCount(); ++i ) {
            int myRow = jTable_Mitarbeiter.convertRowIndexToModel(i);
            if (myTableModel_Mitarbeiter.getValueAt(myRow, 1).toString().trim().equals(aString))
                myAnswer = true;           
        }
        return myAnswer;
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
            java.util.logging.Logger.getLogger(Frame_PersonalAnwesenheit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame_PersonalAnwesenheit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame_PersonalAnwesenheit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame_PersonalAnwesenheit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Frame_PersonalAnwesenheit().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_close;
    private javax.swing.JButton btn_save;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelSchicht;
    private javax.swing.JPanel jPanel_base;
    private javax.swing.JPanel jPanel_footer;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable_Mitarbeiter;
    private javax.swing.JTextArea jTextArea_Bemerkungen;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPane_Kurzanleitung;
    private javax.swing.JLabel lbl_DateForSaveDataSet;
    private javax.swing.JLabel lbl_currentDate;
    private javax.swing.JLabel lbl_rowCountWithSelectedValues;
    private javax.swing.JLabel lbl_schicht;
    private javax.swing.JLabel lbl_schichtNummerWithName;
    private javax.swing.JLabel lbl_titel;
    private javax.swing.JLabel lbl_version;
    // End of variables declaration//GEN-END:variables
}
