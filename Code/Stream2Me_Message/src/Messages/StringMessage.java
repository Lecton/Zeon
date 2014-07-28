package Messages;

public class StringMessage extends Message {
    private String message ="";
    
    public StringMessage(int userID, int targetID, String message) {
        this.message =message;
        this.userID =userID;
        this.Title ="String Message";
//        this.timestamp = d.getHours()+
//                         ":"+d.getMinutes();
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
