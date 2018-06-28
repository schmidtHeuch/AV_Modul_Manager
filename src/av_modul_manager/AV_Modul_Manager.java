/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package av_modul_manager;
import UI.AV_MM_MainFrame;
import java.awt.Toolkit;
/**
 *
 * @author schmidtu
 */
public class AV_Modul_Manager {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        AV_MM_MainFrame av_mainFrame = new AV_MM_MainFrame();
        av_mainFrame.setTitle("AV Modul-Manager");
        av_mainFrame.setSize(345, Toolkit.getDefaultToolkit().getScreenSize().height - 30);
        av_mainFrame.setLocation(0, 0);
        av_mainFrame.setVisible(true);      
    }
    
}
