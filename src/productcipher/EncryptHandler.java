/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package productcipher;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
public class EncryptHandler {
 
    private BufferedWriter bw = null;
    private FileWriter fw = null;
    public EncryptHandler() {
        
        try {
            //initializing the output file.
            fw=new FileWriter("Output.txt");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DecryptHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EncryptHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public String readInput(){
        String content="";
        try {
            //reading the input untill end of the file
            Scanner sc = new Scanner(new File("input.txt"));
            content = sc.useDelimiter("\\Z").next();
            sc.close();
        } catch (FileNotFoundException ex){
            JOptionPane.showMessageDialog(null,"There is no input file",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
        }
        catch( NoSuchElementException ex) {
            System.out.println("No elements");
        }
        return content;
    }
    public String[] doEncryption(String key){
        String content=this.readInput();
        if(content.length()==0){
             JOptionPane.showMessageDialog(null,"There is no data in the input file",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
        }else{
            String[] layers = content.split("\\n");
            String[] output = new String[layers.length];
            byte[] keyBytes = this.convertToBinary(key);
            int y=0;
            for(String x:layers){
                byte[] layerBytes = this.convertToBinary(x);
                int j=0;
                String byteString="";
                for(int i=0;i<layerBytes.length;i++){
                    while(j<keyBytes.length){
                        String beforeConverted;
                        //xoring each byte to achieve substitution
                        int afterConversion = layerBytes[i]^keyBytes[j];
                        //getting 8 bit length binary vlues
                        if(afterConversion<2){
                            beforeConverted=("0000000"+Integer.toBinaryString(afterConversion));
                        }else if(afterConversion<4){
                            beforeConverted=("0000000"+Integer.toBinaryString(afterConversion)).substring(1);
                        }
                        else if(afterConversion<8){
                            beforeConverted=("0000000"+Integer.toBinaryString(afterConversion)).substring(2);
                        }else if(afterConversion<16){
                            beforeConverted=("0000000"+Integer.toBinaryString(afterConversion)).substring(3);
                        }
                        else if(afterConversion<32){
                            beforeConverted=("0000000"+Integer.toBinaryString(afterConversion)).substring(4);
                        }else if((afterConversion<64)){
                            beforeConverted=("0000000"+Integer.toBinaryString(afterConversion)).substring(5);
                        }else{
                            beforeConverted=("0000000"+Integer.toBinaryString(afterConversion)).substring(6);
                        }
                        String firstPart = beforeConverted.substring(0,4);
                        String secondPart = beforeConverted.substring(4,8);
                        byteString+=Permutation.getEncryptConversion(secondPart);
                        //first and second part are exchanged to achieve the transposition
                        byteString+=Permutation.getEncryptConversion(firstPart);                   
                        j++;
                        break;
                    }
                    if(j==keyBytes.length)j=0;

                }

                output[y]=byteString;
                y++;
            }
                this.writeData(output);
                return output;
        }
        
       return null;
    }
    public void writeData(String[] data){
        bw=new BufferedWriter(fw);
        try {
            for(String x:data){
                //writing data to the Output file
                bw.write(x);
                bw.newLine();
            }
            
        } catch (IOException ex) {
            Logger.getLogger(EncryptHandler.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                bw.close();
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(EncryptHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
