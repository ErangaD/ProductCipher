/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package productcipher;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Eranga
 */
public class Permutation {
    final static Map<String, String> encryptMap;
    final static Map<String, String> decryptMap;
    static{
        encryptMap= new HashMap();
        decryptMap = new HashMap();
        //All the 4bits values are substituted with a different 4bits value
        encryptMap.put("0000", "0010");
        encryptMap.put("0001", "0100");
        encryptMap.put("0010", "0110");
        encryptMap.put("0011", "1000");
        encryptMap.put("0100", "1010");
        encryptMap.put("0101", "1100");
        encryptMap.put("0110", "1110");
        encryptMap.put("0111", "0000");
        encryptMap.put("1000", "0001");
        encryptMap.put("1001", "0011");
        encryptMap.put("1010", "0101");
        encryptMap.put("1011", "0111");
        encryptMap.put("1100", "1001");
        encryptMap.put("1101", "1011");
        encryptMap.put("1110", "1101");
        encryptMap.put("1111", "1111");
        
         //To take the original value reverse mapping
        decryptMap.put( "0010","0000");
        decryptMap.put( "0100","0001");
        decryptMap.put( "0110","0010");
        decryptMap.put( "1000","0011");
        decryptMap.put( "1010","0100");
        decryptMap.put( "1100","0101");
        decryptMap.put( "1110","0110");
        decryptMap.put("0000","0111");
        decryptMap.put( "0001","1000");
        decryptMap.put( "0011","1001");
        decryptMap.put( "0101","1010");
        decryptMap.put("0111","1011");
        decryptMap.put( "1001","1100");
        decryptMap.put( "1011","1101");
        decryptMap.put( "1101","1110");
        decryptMap.put( "1111","1111");
    }
    
    public static String getEncryptConversion(String x){
        return encryptMap.get(x);
    }
    public static String getDecryptConversion(String x){
        return decryptMap.get(x);
    }
}
