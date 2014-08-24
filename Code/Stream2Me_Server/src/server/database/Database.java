package server.database;
import com.db4o.Db4o;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import java.io.BufferedReader;
import java.util.ArrayList;

/**
 *
 * @author Zenadia
 */
public class Database {
    private ObjectContainer database;
    private final String fileName = "database.db";
    private ArrayList<Integer> loggedIn = new ArrayList<>();
    
    public void open(){
        database = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), fileName);
    }
    
    public void close(){
        System.out.println("Exiting DB");
        database.close();
    }
    
    public int login(String username, String passwordHash) {
        ObjectSet<Users> result = database.queryByExample(Users.class);
        
        for (Users p: result) {
            if (!loggedIn.contains(p.getUserID()) && p.getUsername().equalsIgnoreCase(username)) {// && p.getPassword().equalsIgnoreCase(passwordHash)) {
                p.loggedIn = true;
                loggedIn.add(p.getUserID());
                return p.getUserID();
            }
        }
        return server.Server.ERROR;
    }
    
    public void logout(int userID) {
        loggedIn.remove((Object)userID);
    }
    
    public void addUser(int userID, String title, String name, String surname, String email, String avatar){
        Users user = new Users(userID, title, name, surname, email, avatar);        
        database.store(user);
    }
    
    public void addMessageHistory(){
        MessageHistory mh = new MessageHistory();
        database.store(mh);
    }
    
    public void addStreamProperties(){
        StreamProperty sp = new StreamProperty(ownerID, streamID);
    }
        
}
