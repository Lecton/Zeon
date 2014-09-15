import connection.DBConnect;
import java.sql.Date;
import manager.DatabaseManager;
import java.util.UUID;
import utilities.User;

public class Main {
    static DatabaseManager dm;
    public static void main(String[] args) {
        dm = new DatabaseManager("127.0.0.1", 5433, "stream2me", "postgres", "root");
        dm.connect();
        addUsers();
        getUserID();
    }
    
    public static void getUserID(){
        System.out.println("bmwilhelm@gmail.com\tid\t" + dm.getUserID("bmwilhelm@gmail.com"));
        System.out.println("lecton@gmail.com\tid\t" + dm.getUserID("lecton@gmail.com"));
        System.out.println("zgroenewald@gmail.com\tid\t" + dm.getUserID("zgroenewald@gmail.com"));
        System.out.println("xavier@gmail.com\tid\t" + dm.getUserID("xavier@gmail.com"));
    }
    
    public static void addUsers(){
        //Inserts into the clients table
        dm.addUser(new User(UUID.randomUUID().toString(), UUID.randomUUID().toString(), "Bernhard", "Bernhard",
                    "Muller","bmwilhelm@gmail.com", null, "Mr", dm.getCurrentDate(), false));
        dm.addUser(new User(UUID.randomUUID().toString(), UUID.randomUUID().toString(), "Lecton", "Lecton",
                    "Ramasila","lecton@gmail.com", null, "Mr", dm.getCurrentDate(), false));
        dm.addUser(new User(UUID.randomUUID().toString(), UUID.randomUUID().toString(), "Zenadia", "Zenadia",
                    "Groenewald","zgroenewald@gmail.com", null, "Miss", dm.getCurrentDate(), false));
        dm.addUser(new User(UUID.randomUUID().toString(), UUID.randomUUID().toString(), "Xavier", "Xavier",
                    "Smith","xavier@gmail.com", null, "Mr", dm.getCurrentDate(), false));
        
    }
}
