/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Messages.MessageUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;

/**
 *
 * @author Bernhard
 */
public class MessageUtils {
    public static final byte MESSAGE =0;
    public static final byte CLIENTINIT =1;
    public static final byte GREETING =2;
    public static final byte NEWUSER =3;
    public static final byte REMOVEUSER =4;
    public static final byte STRINGMESSAGE =5;
    public static final byte UPDATEUSER =6;
    public static final byte VIDEOSTREAM =7;
    public static final byte AUDIOSTREAM =8;
    public static final byte MEDIASTREAM =9;
    
    public static byte[] intToBytes(final int value) {
        BigInteger bi =BigInteger.valueOf(value);
        return bi.toByteArray();
    }
    
    public static byte[] stringToBytes(final String value) {
        return value.getBytes();
    }
    
    public static String bytesToString(byte[] byteString) {
        String value ="";
        for (int i=0; i<byteString.length; i++) {
            value +=(char)byteString[i];
        }
        return value;
    }
}
