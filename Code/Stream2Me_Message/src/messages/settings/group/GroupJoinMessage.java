/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messages.settings.group;

import messages.Message;
import messages.MessageType;

/**
 *
 * @author Bernhard
 */
public class GroupJoinMessage extends Message {
    boolean success;
    String error ="";

    public GroupJoinMessage(boolean success) {
        this.success =success;
    }

    @Override
    public String getMessage() {
        return "Group join "+(success ? "success" : "failure. "+error);
    }

    @Override
    public MessageType handle() {
        return MessageType.settingsGroupJoin;
    }

    public boolean isSuccessful() {
        return success;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
