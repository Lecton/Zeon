package messages;

public class StringMessage extends Message {
    private String message ="";
    
    public StringMessage(int userID, int targetID, String message) {
        this.userID =userID;
        this.targetID =targetID;
        this.message =message;
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
