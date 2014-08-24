
package server.database;

/**
 *
 * @author Zenadia
 */
public class Users{
    private int userID;
    private String title;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String avatar;
    private String registrationDate;
    public boolean loggedIn = false;
    
    public Users(int userID, String title, String name, String surname, String email, String avatar){
        this.userID = userID;
        this.title = title;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.avatar = avatar;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        this.registrationDate = dateFormat.format(date);
        loggedIn = true;
    }
    
    public int getUserID() {
        return this.userID;
    }
    
    public String getUsername() {
        return this.email;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public String getPassword() {
        return this.password;
    }
    
    public String getTitle(){
        return this.title;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    
    public void setTitle(String title){
        this.title = title;
    }
    
}
