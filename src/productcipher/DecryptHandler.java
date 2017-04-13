/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package productcipher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Eranga
 */
public class DecryptHandler {
    
    public String readOutput(){
        String content="";
        try {
            //taking encrypted data from the file
            Scanner sc = new Scanner(new File("Output.txt"));
            //read the data until data ends
            content = sc.useDelimiter("\\Z").next();
            sc.close();
        } catch (FileNotFoundException | NoSuchElementException ex){
            System.out.println("No elements");
            JOptionPane.showMessageDialog(null,"There is no data in the Output file \n"
                    + "Please encrypt first",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
        } 
        //read values from the input file is returned
        return content;
    }
    public String[] doDecryption(String key){
        //take the byte value of the key
        byte[] keyBytes=this.convertToBinary(key);
         String content=this.readOutput();
         //input data is devided, if there is data in seperate lines
         String[] layers = content.split("\\n");
         String[] output = new String[layers.length];
         int y=0;
         for(String x:layers){
             String original="";
             //Since one character is stored using 8 bits
             int[] byteVal=new int[x.length()/8];
             //read data is divided to groups of four since data has been transpositioned
             //using 4bit groups
             String[] converted = x.split("(?<=\\G.{4})");
             int n=0;
             for(int i=0;i<converted.length-1;i=i+2){
                 String firstPart = converted[i];
                 String secondPart = converted[i+1];
                 //taking original data by retransforming 4bit groups
                 String originlaFormPart1=Permutation.getDecryptConversion(secondPart);
                 //first and second part have been exchanged to achieve the transposition
                 String originalFormPart2=Permutation.getDecryptConversion(firstPart);
                 //getting the original bit stream value which has 8 bits
                 String originalVal=originlaFormPart1+originalFormPart2;
                 //taking the ascii value
                 int asciiVal=Integer.parseInt(originalVal,2);
                 byteVal[n]=asciiVal;
                 n++;
             }
             int j=0;
             for(int i=0;i<byteVal.length;i++){
                while(j<keyBytes.length){
                    //rexoring to get the original value.
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
         //returning the original content after decrypting
         return output;
    }
    public byte[] convertToBinary(String layer){
        byte[] byteArray=null;
        try {
            //getting the arrayy of bytes for each char
            byteArray = layer.getBytes("UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(EncryptHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return byteArray;
        
    }
}
