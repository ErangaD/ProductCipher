/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package productcipher;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Eranga
 */
public class EncryptHandler {
 
    private BufferedWriter bw = null;
    private FileWriter fw = null;
    public EncryptHandler() {
        
        try {
         
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
            Scanner sc = new Scanner(new File("input.txt"));
            content = sc.useDelimiter("\\Z").next();
            sc.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DecryptHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return content;
    }
    public void doEncryption(String key){
        String content=this.readInput();
        String[] layers = content.split("\\n");
        byte[] keyBytes = this.convertToBinary(key);
        for(String x:layers){
            byte[] layerBytes = this.convertToBinary(x);
            int j=0;
            String byteString="";
            for(int i=0;i<layerBytes.length;i++){
                while(j<keyBytes.length){
                    String beforeConverted;
                    int afterConversion = layerBytes[i]^keyBytes[j];
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
                    byteString+=Permutation.getEncryptConversion(firstPart);
                    byteString+=Permutation.getEncryptConversion(secondPart);
                    j++;
                    break;
                }
                if(j==keyBytes.length)j=0;
                
            }
            System.out.println(byteString);
            this.writeData(byteString);
        }
    }
    public void writeData(String data){
        bw=new BufferedWriter(fw);
        try {
            bw.write(data);
            
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
            byteArray = layer.getBytes("UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(EncryptHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return byteArray;
        
    }
    
}
