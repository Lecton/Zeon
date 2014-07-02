/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import Messages.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 *
 * @author Lecton
 * @author Bernhard
 */
public class inStream implements Runnable {
    public ArrayList<Colleague> colleagues = new ArrayList<>();
    private ObjectInputStream ois = null;
    private UI userInterface =null;

    public inStream(UI userInterface) {
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
    
    /**
     * Find the colleague with the same ID as provided and returns his index
     * @param ID of the colleague to find
     * @return the colleague index or -1 if no one has that ID
     */
    private int find(int ID) {
        for (int i=0; i<colleagues.size(); i++) {
            if (colleagues.get(i).ID == ID) {
                return i;
            }
        }
        return -1;
    }

    private void greetUserMessage(Message m) {
        Greeting g =(Greeting) m;
        userInterface.ID =g.ID;
        Colleague cc =new Colleague();
        
        cc.ID =g.ID;
        cc.name =userInterface.name;
        cc.localName =userInterface.name;
        colleagues.add(cc);
        userInterface.updateGUI(cc, UI.Action.ADD);

        for (int i=0; i<g.size; i++) {
            Colleague c =new Colleague();
            c.ID =g.colleagueIDs[i];
            c.name =g.colleagueNames[i];
            c.localName =c.name;
            colleagues.add(c);
            userInterface.updateGUI(c, UI.Action.ADD);
        }
    }
    
    private void newUserMessage(Message m) {
        NewUser um =(NewUser)m;
        
        if (um.ID != userInterface.ID) {
            Colleague tempColleague =new Colleague();
            tempColleague.ID =um.ID;
            tempColleague.name =um.name;
            tempColleague.localName =um.name;
            
            colleagues.add(tempColleague);
            userInterface.updateGUI(tempColleague, UI.Action.ADD);
        }
    }

    private void removeUserMessage(Message m) {
        RemoveUser ru =(RemoveUser)m;
        boolean done =false;
        
        int index =find(ru.ID);
        if (index != -1) {
            userInterface.updateGUI(colleagues.get(index), UI.Action.REMOVE);
            colleagues.remove(index);
            for (int i=index; i<colleagues.size(); i++) {
                colleagues.get(i).tabIndex--;
            }
        }
        
        System.out.println("Removed User");
    }

    private void updateUserMessage(Message m) {
        UpdateUser uu =(UpdateUser) m;
        
        int index =-1;
        if ((index =find(uu.ID)) != -1) {
            if (colleagues.get(index).name.equalsIgnoreCase(colleagues.get(index).localName)) {
                colleagues.get(index).localName =uu.name;
            }
            colleagues.get(index).name =uu.name;
            userInterface.updateGUI(colleagues.get(index), UI.Action.UPDATE);
        }
    }

    private void stringUserMessage(Message m) {
        System.out.println(m.getMessage());

        StringMessage sm =(StringMessage)m;
        
        int index =-1;
        if((index =find(sm.ID)) != -1){
            colleagues.get(index).content += m.getMessage() + "\n";
            userInterface.updateGUI(colleagues.get(index), UI.Action.UPDATE);
        }
    }
}