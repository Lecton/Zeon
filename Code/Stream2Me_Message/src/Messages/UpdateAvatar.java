package Messages;

public class UpdateAvatar extends Message {
    private String avatar;
    
    public UpdateAvatar(int userID, String avatar) {
        this.userID =userID;
        this.avatar =avatar;
    }

    @Override
    public String getMessage() {
        return this.getClass().getSimpleName()+" - ID: "+userID;
    }

    @Override
    public MessageType handle() {
        return MessageType.updateAvatar;
    }
}
