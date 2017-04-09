/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package productcipher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eranga
 */
public class DecryptHandler {
    
    public String readOutput(){
        String content="";
        try {
            Scanner sc = new Scanner(new File("Output.txt"));
            content = sc.useDelimiter("\\Z").next();
            sc.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DecryptHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return content;
    }
    public String[] doDecryption(String key){
        byte[] keyBytes=this.convertToBinary(key);
         String content=this.readOutput();
         String[] layers = content.split("\\n");
         String[] output = new String[layers.length];
         int y=0;
         for(String x:layers){
             String original="";
             int[] byteVal=new int[x.length()/8];
             String[] converted = x.split("(?<=\\G.{4})");
             int n=0;
             for(int i=0;i<converted.length-1;i=i+2){
                 String firstPart = converted[i];
                 String secondPart = converted[i+1];
                 String originlaFormPart1=Permutation.getDecryptConversion(firstPart);
                 String originalFormPart2=Permutation.getDecryptConversion(secondPart);
                 String originalVal=originlaFormPart1+originalFormPart2;
                 int asciiVal=Integer.parseInt(originalVal,2);
                 byteVal[n]=asciiVal;
                 n++;
             }
             int j=0;
             for(int i=0;i<byteVal.length;i++){
                while(j<keyBytes.length){
                    int afterConversion = byteVal[i]^keyBytes[j];
                    original+=(char)afterConversion;
                    j++;
                    break;
                }
                if(j==keyBytes.length)j=0;
                
            }
             output[y]= original;
             y++;
         }
         return output;
    }
    public byte[] convertToBinary(String layer){
        byte[] byteArray=null;
        try {
            byteArray = layer.getBytes("UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(EncryptHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return byteArray;
        
    }
}
