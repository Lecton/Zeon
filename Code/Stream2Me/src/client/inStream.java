/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import Messages.*;
import client.GUI.GUI;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 *
 * @author Lecton
 * @author Bernhard
 */
public class inStream implements Runnable {
    private ObjectInputStream ois = null;
    private GUI userInterface =null;

    public inStream(GUI userInterface) {
        this.userInterface =userInterface;
    }
    
    public void setInputStream(ObjectInputStream ois) {
        this.ois =ois;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Object o = ois.readObject();
                Message m =(Message)o;
                if (m instanceof Greeting) {
                    greetUserMessage(m);
                } else if (m instanceof NewUser) {
                    newUserMessage(m);
                } else if (m instanceof RemoveUser) {
                    removeUserMessage(m);
                } else if (m instanceof UpdateUser) {
                    updateUserMessage(m);
                } else if (m instanceof StringMessage) {
                    stringUserMessage(m);
                } else {
                    System.out.println("Message: "+m.getMessage());
                }
            } catch (IOException ioe) {
                System.err.println("RUN - IOException");
            } catch (ClassNotFoundException cnfe) {
                System.err.println("RUN - ClassNotFoundException");
            }
        }
    }

    private void greetUserMessage(Message m) {
        Greeting g =(Greeting) m;
        userInterface.ID =g.ID;
        Colleague cc =new Colleague();
        
        cc.ID =g.ID;
        cc.name =userInterface.name;
        cc.localName =userInterface.name;
        userInterface.ContactPane.addContact(cc);

        for (int i=0; i<g.size; i++) {
            Colleague c =new Colleague();
            c.ID =g.colleagueIDs[i];
            c.name =g.colleagueNames[i];
            c.localName =c.name;
            userInterface.ContactPane.addContact(c);
        }
    }
    
    private void newUserMessage(Message m) {
        NewUser um =(NewUser)m;
        
        if (um.ID != userInterface.ID) {
            Colleague tempColleague =new Colleague();
            tempColleague.ID =um.ID;
            tempColleague.name =um.name;
            tempColleague.localName =um.name;
            userInterface.ContactPane.addContact(tempColleague);
//            colleagues.add(tempColleague);
//            userInterface.updateGUI(tempColleague, GUI.Action.ADD);
        }
    }

    private void removeUserMessage(Message m) {
        RemoveUser ru =(RemoveUser)m;
        boolean done =false;
        
        userInterface.ContactPane.removeContact(ru.ID);
        System.out.println("Removed User");
    }

    private void updateUserMessage(Message m) {
        UpdateUser uu =(UpdateUser) m;
        userInterface.ContactPane.updateUser(uu);
    }

    private void stringUserMessage(Message m) {
        StringMessage sm =(StringMessage)m;
        
        userInterface.setChatMessage(sm.getMessage());
//        userInterface.ContactPane.acceptMessage(sm);
//        int index =-1;
//        if((index =find(sm.ID)) != -1){
//            userInterface.ContactPane.colleagues.get(index).content += m.getMessage() + "\n";
//            userInterface.updateGUI(colleagues.get(index), GUI.Action.UPDATE);
//        }
    }
}