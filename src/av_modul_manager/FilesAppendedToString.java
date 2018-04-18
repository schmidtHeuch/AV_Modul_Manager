/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package av_modul_manager;

import java.io.File;

/**
 *
 * @author schmidtu
 */
public class FilesAppendedToString {    
    
    public String stringOfFiles;    
    
    public String getStringOfFiles(String aPath) {
        File f = new File(aPath);
        //File[] fileArray = f.listFiles();
        String [] stringArrayOfFiles = f.list(); 
        String tempStringOfFiles = getFilesAsString(stringArrayOfFiles);
        stringOfFiles = tempStringOfFiles;
        return stringOfFiles;
    }
/**
 *
 * @param anArrayOfStrings
 * @return
 */
    private static String getFilesAsString(String anArrayOfStrings[]) {
        String stringOfFiles;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < anArrayOfStrings.length; i++) {
            sb.append(anArrayOfStrings[i]);
            sb.append( ", ");            
        }
        stringOfFiles = sb.toString();
        return stringOfFiles;
        }
    
}
