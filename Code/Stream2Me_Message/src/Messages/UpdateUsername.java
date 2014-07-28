package Messages;

public class UpdateUsername extends Message {
    public UpdateUsername(int userID, String username) {
        this.userID = userID;
        this.Sender = username;
        this.Title = "User Update";
    }
    
    @Override
    public String getMessage() {
        return "My name is "+this.Sender+". My ID is "+this.userID+".";
    }

    @Override
    public MessageType handle() {
        return MessageType.updateName;
    }
}
