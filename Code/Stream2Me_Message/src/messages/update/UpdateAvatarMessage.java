package messages.update;

import messages.Message;

public class UpdateAvatarMessage extends Message {
    private String avatar;
    
    public UpdateAvatarMessage(int userID, String avatar) {
        this.userID =userID;
        this.avatar =avatar;
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
