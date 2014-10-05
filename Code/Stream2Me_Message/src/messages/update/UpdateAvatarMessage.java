package messages.update;

import messages.Message;
import messages.MessageType;

public class UpdateAvatarMessage extends Message {
    private String avatar;
    
    public UpdateAvatarMessage(String userID, String avatar) {
        this.userID =userID;
        this.avatar =avatar;
        this.targetID =Message.SERVER;
    }

    @Override
    public String getMessage() {
        return this.getClass().getSimpleName()+" - ID: "+userID;
    }

    public String getAvatar() {
        return avatar;
    }

    @Override
    public MessageType handle() {
        return MessageType.updateAvatar;
    }
}
