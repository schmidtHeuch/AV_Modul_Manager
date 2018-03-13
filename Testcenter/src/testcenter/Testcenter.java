/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testcenter;
import javax.swing.*;
//import UI.Testcenter_MainFrame;

/**
 *
 * @author schmidtu
 */
public class Testcenter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
 /*       Testcenter_MainFrame testcenterFrame = new Testcenter_MainFrame();
        testcenterFrame.setTitle("Testcenter");
        testcenterFrame.setSize(1024, 900);
        testcenterFrame.pack();
        testcenterFrame.setVisible(true);
*/                  
        JFrame basicFrame = new JFrame();
        JPanel basicPanel = new JPanel();
        JButton btn_openFileChooser = new JButton();
        JList list_courceFiles = new JList();
        JButton btn_closeApp = new JButton();
        
        btn_openFileChooser.setText("Quellordner öffnen");
        btn_closeApp.setText("Schließen");
        
        basicPanel.add(btn_openFileChooser);
        basicPanel.add(list_courceFiles);
        basicPanel.add(btn_closeApp);
        
        basicFrame.add(basicPanel);
        basicFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        basicFrame.setTitle("Testcenter");
        basicFrame.setSize(1024,900);
        
        basicFrame.setVisible(true);        
        
    }    
   
}
