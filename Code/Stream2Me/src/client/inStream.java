/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import Messages.Greeting;
import Messages.Message;
import Messages.NewUser;
import Messages.RemoveUser;
import Messages.StringMessage;
import Messages.UpdateUser;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 *
 * @author Lecton
 */
public class inStream implements Runnable {
    private ObjectInputStream ois = null;
    private Client client = null;

    public inStream(InputStream input, Client c) throws IOException {
        ois = new ObjectInputStream(input);
        this.client =c;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Object o = ois.readObject();
                Message m =(Message)o;

                if(m instanceof NewUser){
                    NewUser um =(NewUser)m;
                    Colleague c =new Colleague();
                    c.ID =um.ID;
                    if (c.ID == client.ID) {
                        um.name =c.name;
                    }
                    c.name = um.name;
                    c.localName =um.name;
                    if (c.contained(client.coll)) {
                        c.update(client.coll);
                    } else {
                        client.coll.add(c);
                    }
                    client.updateGUI();
                    System.out.println("Added New User " + client.coll.size());
                } else if (m instanceof RemoveUser) {
                    RemoveUser ru =(RemoveUser)m;
                    boolean done =false;
                    for (int i=0; i<client.coll.size() && !done; i++) {
                        if (client.coll.get(i).ID == ru.ID) {
                            client.coll.remove(i);
                            done =true;
                        }
                    }
                    client.updateGUI();
                    System.out.println("Removed User");
                } else if (m instanceof Greeting) {
                    Greeting g =(Greeting) m;
                    client.ID =g.ID;
                    Colleague cc =new Colleague();
                    cc.ID =g.ID;
                    cc.name =client.name;
                    cc.localName =client.name;
                    client.coll.add(cc);

                    for (int i=0; i<g.size; i++) {
                        Colleague c =new Colleague();
                        c.ID =g.colleagueIDs[i];
                        c.name =g.colleagueNames[i];
                        c.localName =c.name;
                        client.coll.add(c);
                    }
                    client.setGUI();
                } else if (m instanceof UpdateUser) {
                    UpdateUser uu =(UpdateUser) m;
                    for (int i=0; i<client.coll.size(); i++) {
                        if (client.coll.get(i).ID == uu.ID) {
                            if (client.coll.get(i).name.equalsIgnoreCase(client.coll.get(i).localName)) {
                                client.coll.get(i).localName =uu.name;
                            }
                            client.coll.get(i).name =uu.name;
                        }
                    }
                    client.updateGUI();
                } else if (m instanceof StringMessage) {
                    System.out.println(m.getMessage());

                    for (int i = 0;i < client.coll.size(); i++){
                        if(client.coll.get(i).ID == m.ID){
                            client.coll.get(i).content += m.getMessage() + "\n";
                        }
                    }
                    client.updateGUI();
                }
                else {
                    System.out.println("Message: "+m.getMessage());
                }
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }

}