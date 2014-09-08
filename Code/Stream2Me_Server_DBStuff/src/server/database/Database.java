package server.database;
import server.database.objects.StreamingHandler;
import server.database.objects.UserMessageHistory;
import server.database.objects.StreamingProperty;
import server.database.objects.User;
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

    public Database() {
        open();
        
        ObjectSet<User> result =database.query(User.class);
        System.out.println("Users: "+result.size());
    }
    
    public void open(){
        database = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), fileName);
        StreamingHandler.db =this;
    }
    
    public void close(){
        System.out.println("Exiting DB");
        database.close();
    }
    
    public int login(String username, String passwordHash) {
        ObjectSet<User> result = database.queryByExample(User.class);        
        for (User p: result) {
            if (!p.isLoggedIn() && p.getUsername().equalsIgnoreCase(username)) {// && p.getPassword().equalsIgnoreCase(passwordHash)) {
                p.setLoggedIn(true);
                return p.getUserID();
            }
        }
        return server.Server.ERROR;
    }
    
    public void logout(int userID) {
        ObjectSet<User> result = database.queryByExample(User.class);        
        for (User p: result) {
            if (p.getUserID() == userID) {// && p.getPassword().equalsIgnoreCase(passwordHash)) {
                p.setLoggedIn(false);
                break;
            }
        }
    }
    
    public void addUser(int userID, String title, String name, String surname, String email, String avatar){
        User user = new User(userID, title, name, surname, email, avatar);        
        database.store(user);
    }
    
    public void removeUser(int userID){
        ObjectSet<User> result = database.queryByExample(User.class);        
        for(User p: result){
            if(p.getUserID() == userID) database.delete(p);
        }
    }
    
    public User getNewUser(int userID){
        ObjectSet<User> result = database.queryByExample(User.class);
        for(User p: result){
            if(p.getUserID() == userID) return p;
        }
        return null;
    }
    
    public ObjectSet<User> getAllUsers(){
        ObjectSet<User> users = database.queryByExample(User.class);
        return users;
    }
    
    public void addMessageHistory(){
        UserMessageHistory mh = new UserMessageHistory();
        database.store(mh);
    }
    
    public void removeMessageHistory(int id){
        ObjectSet<User> result = database.queryByExample(User.class);        
        for(User p: result){
            if(p.getUserID() == id) database.delete(p.getMyMessageHistory());
        }
    }
    
    public void addStreamProperties(StreamingProperty sp){
        database.store(sp);
    }
    
    public void removeStreamProperties(StreamingProperty sp){
        database.delete(sp);
    }
    
    public ObjectSet<StreamingProperty> getStreamProperty(String streamID){
        return database.queryByExample(new StreamingProperty(0, streamID));
    }
    
    public void addStreamHandler(StreamingHandler sh){
        database.store(sh);
    }
    
    public void removeStreamHandler(StreamingHandler sh){
        database.delete(sh);
    }
       
}
