package messages.update;

import messages.Message;
import messages.MessageType;

public class UpdateProfileMessage extends Message {
    private String name;
    private String surname;
    private String email;
    private String title;
    private String aboutMe;
    
    public UpdateProfileMessage(String userID, String name, String surname, 
            String email, String title, String aboutMe) {
        this.userID = userID;
        this.name = name;
        this.surname =surname;
        this.email =email;
        this.title =title;
        this.aboutMe =aboutMe;
    }
    
    @Override
    public String getMessage() {
        return "My name is "+name+" "+surname+". My ID is "+this.userID+".";
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getTitle() {
        return title;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    @Override
    public MessageType handle() {
        return MessageType.updateProfile;
    }
}
