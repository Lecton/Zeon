/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Messages.MessageUtils;

import Messages.*;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 *
 * @author Bernhard
 */
public class MessageReceiver {
    public static String readString(ObjectInputStream ois) throws IOException {
        int length =ois.readInt();
        byte[] message =new byte[length];
        ois.readFully(message);
        
        return MessageUtils.bytesToString(message);
    }
    
    //Assumed Type is already read
    
    public static ClientInit ClientInit(ObjectInputStream ois) throws IOException {
        int to =ois.readInt();
        int from =ois.readInt();
        
        String name =readString(ois);
        
        ClientInit ci =new ClientInit(from, to, name);
        
        return ci;
    }
    
    public static Greeting Greeting(ObjectInputStream ois) throws IOException {
        int to =ois.readInt();
        int from =ois.readInt();
        
        int size =ois.readInt();
        int[] colleagueIDs =new int[size];
        
        for (int i=0; i<size; i++) {
            colleagueIDs[i] =ois.readInt();
        }
        
        size =ois.readInt();
        String[] colleagueNames =new String[size];
        for (int i=0; i<size; i++) {
            colleagueNames[i] =readString(ois);
        }
        
        return new Greeting(from, to, colleagueIDs, colleagueNames);
    }
    
    public static UpdateUser UpdateUser(ObjectInputStream ois) throws IOException {
        int to =ois.readInt();
        int from =ois.readInt();
        String name =readString(ois);
        
        return new UpdateUser(from, to, name);
    }
    
    public static RemoveUser RemoveUser(ObjectInputStream ois) throws IOException {
        int to =ois.readInt();
        int from =ois.readInt();
        int size =ois.readInt();
        
        return new RemoveUser(from, size);
    }
}
