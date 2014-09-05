package messages;

public class StringMessage extends Message {
    private String message ="";
    
    public StringMessage(int userID, int targetID, String Sender, String message) {
        this.userID =userID;
        this.targetID =targetID;
        this.Sender =Sender;
        this.message =message;
//        this.timestamp = d.getHours()+
//                         ":"+d.getMinutes();
    }
    
    public String getUsername() {
        return Sender;
    }
    
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public MessageType handle() {
        return MessageType.string;
    }
}
