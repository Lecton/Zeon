package core;

import user.Profile;
import utils.Log;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import java.util.ArrayList;
import messages.userConnection.NewUserMessage;

/**
 *
 * @author Bernhard
 * @author Zenadia
 */
public class Database {
    protected ObjectContainer db;
    private ArrayList<String> loggedIn =new ArrayList<>();
    
    public Database() {
        db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "stream2Me.db");
                                            
        ObjectSet<Profile> result =db.queryByExample(Profile.class);
        System.out.println("Profile count: "+result.size());
        
//        for (Profile p: result) {
//            System.out.println("User ID: "+p.getUserID());
//            System.out.println("Username: "+p.getUsername());
//            System.out.println("Email: "+p.getEmail());
//            System.out.println("");
//        }
    }
    
    public void close() {
        System.out.println("Exiting DB");
        db.close();
    }
    
    public int login(String username, String passwordHash) {
        ObjectSet<Profile> result =db.queryByExample(Profile.class);
        
        for (Profile p: result) {
            if (!loggedIn.contains(p.getUserID()) && p.getEmail().equalsIgnoreCase(username)) {// && p.getPassword().equalsIgnoreCase(passwordHash)) {
                loggedIn.add(""+p.getUserID());
                return p.getUserID();
            }
        }
        return Server.ERROR;
    }
    
    public boolean logoff(int userID) {
        return loggedIn.remove(""+userID);
    }
    
    public void updateName(int userID, String name, String surname) {
        Profile person = getUserProfile(userID);
        if (person != null) {
            person.setName(name);
            person.setSurname(surname);
            db.store(person);
        }
    }
    
    public void updateAvatar(int userID, String avatar) {
        Profile person = getUserProfile(userID);
        if (person != null) {
            person.setAvatar(avatar);
            db.store(person);
        }
    }
    
    public void updateTitle(int userID, String newTitle){
        Profile person = getUserProfile(userID);
        if(person != null){
            person.setTitle(newTitle);
            db.store(person);
        }
    }
    
    protected Profile getUserProfile(int userID) {
        ObjectSet<Profile> result = db.queryByExample(new Profile(userID, null, null, null, null, null));
        
        for(Profile p: result) {
            if (p.getUserID() == userID) {
                return p;
            }
        }
        Log.write(this.getClass(),"User not found");
        return null;
    }
    
    public NewUserMessage getNewUserMessage(int userID) {
        Profile p = getUserProfile(userID);
        
        if (p != null) {
            return new NewUserMessage(p.getUserID(), p.getName(), p.getSurname(), p.getEmail(), p.getAvatar());
        } else {
            return null;
        }
    }
}
