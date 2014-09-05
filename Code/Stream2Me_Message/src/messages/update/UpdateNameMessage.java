package messages.update;

import messages.Message;

public class UpdateNameMessage extends Message {
    private String name;
    private String surname;
    
    public UpdateNameMessage(int userID, String name, String surname) {
        this.userID = userID;
        this.name = name;
        this.surname =surname;
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

    @Override
    public MessageType handle() {
        return MessageType.updateName;
    }
}
