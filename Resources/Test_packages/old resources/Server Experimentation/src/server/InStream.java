/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import Messages.ClientInit;
import static Messages.MessageUtils.MessageReceiver.readString;
import Messages.MessageUtils.MessageSender;
import Messages.MessageUtils.MessageUtils;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;

/**
 *
 * @author Bernhard
 */
public class InStream implements Runnable {
    private clientConnection cc =null;

    public InStream(clientConnection cc) throws IOException {
        this.cc =cc;
    }

    @Override
    public void run() {
        ObjectInputStream ois;
        try {
            ois = cc.getObjectInputStream();
            while (true) {
                try {
                    byte type =ois.readByte();
                    int to =ois.readInt();
                    int from =ois.readInt();
                    
                    switch (type) {
                        case MessageUtils.MESSAGE:
                            break;
                        case MessageUtils.CLIENTINIT: //ClientInit
                            System.out.println("ClientInit received");
                            
                            String name =readString(ois);
                            
                            cc.setName(name);
                            
                            MessageSender.UpdateUser(cc.getObjectOutputStream(), to, from, cc.getName());
                            break;
                        case MessageUtils.AUDIOSTREAM: //AudioStream
                            System.out.println("AudioStream Message");
//                            cc.pipe(type);
                            break;
                        case MessageUtils.VIDEOSTREAM: //VideoStream
                            System.out.println("VideoStream Message");
//                            cc.pipe(type);
                            break;
                        case MessageUtils.MEDIASTREAM: //MediaStream
                            System.out.println("MediaStream Message");
//                            cc.pipe(type);
                            break;
                        case MessageUtils.STRINGMESSAGE: //StringMessage
                            System.out.println("String message");
                            ByteBuffer bb =ByteBuffer.allocate(1024*1024);
                            
                            int val =-1;
                            while ((val =ois.read()) != -1) {
                                bb.putInt(val);
                            }
                            
                            cc.relayMessage(type, to, from, bb.array());
                            break;
                        default:
                            System.out.println("Some type '"+type+"' of message received");
                            bb =ByteBuffer.allocate(1024*1024);
                            
                            val =-1;
                            while ((val =ois.read()) != -1) {
                                bb.putInt(val);
                            }
                            
                            cc.relayMessage(type, to, from, bb.array());
                            break;
                    }



    //                Message o =(Message) read();
    //
    //                if (o instanceof ClientInit) {
    //                    System.out.println("ClientInit received");
    //                    ClientInit ci =(ClientInit) o;
    //                    cc.setName(ci.name);
    //
    //                    UpdateUser um =new UpdateUser(id, name);
    //                    relay.relayMessage(clientConnection.this, um);
    //                    
    //                } else if (o instanceof AudioStream) {
    //                    System.out.println("Audio Message received");
    //                    relay.relayMessage(clientConnection.this, o);
    //                } else if (o instanceof VideoStream) {
    //                    System.out.println("Video Message received");
    //                    relay.relayMessage(clientConnection.this, o);
    //                } else if(o instanceof StringMessage) {
    //                    System.out.println("StringMessage received");
    //                    relay.relayMessage(clientConnection.this, o);
    //                } else {
    //                    System.out.println("Some message received");
    //                    System.out.println("Message: "+o.getMessage());
    //                    relay.relayMessage(clientConnection.this, o);
    //                }
                } catch (IOException ex) {
                    System.out.println("Close connection.");
                    try {
                        cc.closeConnection();
                        break;
                    } catch (IOException ex1) {
                        System.out.println("Error closing connection");
                        ex1.printStackTrace();
                    }
                }
            }   
        } catch (IOException ex) {
        }
    }
}