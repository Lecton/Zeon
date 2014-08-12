package Server;

import Messages.UserConnection.NewUser;
import User.Profile;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import java.util.ArrayList;

/**
 *
 * @author Bernhard
 * @author Zenadia
 */
public class Database {
    private ObjectContainer db;
    private ArrayList<Integer> loggedIn = new ArrayList<>();
    private boolean logged = false;
    
    public Database() {
        db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "stream2Me.db");
                                            
        ObjectSet<Profile> result = db.queryByExample(Profile.class);
        System.out.println("Profile count: " + result.size());
        
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
    
    public int login(String email, String passwordHash) {
        ObjectSet<Profile> result = db.queryByExample(Profile.class);
        
        for (Profile p: result) {
            if (!loggedIn.contains(p.getUserID()) && p.getEmail().equalsIgnoreCase(email)) {// && p.getPassword().equalsIgnoreCase(passwordHash)) {
                loggedIn.add(p.getUserID());
                return p.getUserID();
            }
        }
        logged = true;
        return Messages.Message.ERROR;
    }
    
    public void logoff(int userID) {        
          loggedIn.remove((Object)userID);                 
    }
    
    public void updateUsername(int userID, String username) {
        Profile person = getUserProfile(userID);
        if (person != null) {
            person.setUsername(username);
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
    
    protected Profile getUserProfile(int userID) {
        ObjectSet<Profile> result = db.queryByExample(new Profile(userID, null, null, null));
        
        for(Profile p: result) {
            if (p.getUserID() == userID) {
                return p;
            }
        }
        Utils.Log.write(this,"User not found");
        return null;
    }
    
    public NewUser getNewUser(int userID) {
        Profile p = getUserProfile(userID);
        
        if (p != null) {
            return new NewUser(p.getUserID(), p.getUsername(), p.getEmail(), p.getAvatar());
        } else {
            return null;
        }
    }
}
