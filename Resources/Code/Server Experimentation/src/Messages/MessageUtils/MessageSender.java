/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Messages.MessageUtils;

import Messages.*;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 *
 * @author Bernhard
 */
public class MessageSender {
    
    public static void write(ObjectOutputStream oos, String[] values) throws IOException {
        oos.writeInt(values.length);
        for (int i=0; i<values.length; i++) {
            write(oos, values[i]);
        }
    }
    
    public static void write(ObjectOutputStream oos, int[] values) throws IOException {
        oos.writeInt(values.length);
        for (int i=0; i<values.length; i++) {
            write(oos, values[i]);
        }
    }
    
    public static void write(ObjectOutputStream oos, String message) throws IOException {
        write(oos, MessageUtils.stringToBytes(message));
    }
    
    public static void write(ObjectOutputStream oos, byte[] message) throws IOException {
        oos.writeInt(message.length);
        oos.write(message);
    }
    
    public static void write(ObjectOutputStream oos, byte value) throws IOException {
        oos.writeByte(value);
    }
    
    public static void write(ObjectOutputStream oos, int value) throws IOException {
        oos.writeInt(value);
    }
    
    public static void flush(ObjectOutputStream oos) throws IOException {
        oos.flush();
    }
    
    public static void relay(ObjectOutputStream oos, byte type, int to, int from, byte[] message) throws IOException {
        write(oos, type);
        write(oos, to);
        write(oos, from);
        oos.write(message);
        flush(oos);
    }
    
    public static void ClientInit(ObjectOutputStream oos, int to, int from, String name) throws IOException {
        System.out.println("ClientInit Sent");
        write(oos, MessageUtils.CLIENTINIT);
        write(oos, to);
        write(oos, from);
        write(oos, from);
        write(oos, name);
        flush(oos);
    }
    
    public static void Greeting(ObjectOutputStream oos, int to, int from, int[] colleagueIDs, String[] colleagueNames) throws IOException {
        System.out.println("Greeting Sent");
        write(oos, MessageUtils.GREETING);
        write(oos, to);
        write(oos, from);
        write(oos, colleagueIDs);
        write(oos, colleagueNames);
        flush(oos);
    }
    
    public static void UpdateUser(ObjectOutputStream oos, int to, int from, String name) throws IOException {
        System.out.println("UpdateUser Sent");
        write(oos, MessageUtils.UPDATEUSER);
        write(oos, to);
        write(oos, from);
        write(oos, name);
        flush(oos);
    }
    
    public static void RemoveUser(ObjectOutputStream oos, int ID, int size) throws IOException {
        System.out.println("RemoveUser Sent");
        write(oos, MessageUtils.REMOVEUSER);
        write(oos, -1);
        write(oos, ID);
        write(oos, size);
        flush(oos);
    }
}
