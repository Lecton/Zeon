/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package channel.group.matcher;

import channel.ClientChannel;
import io.netty.channel.Channel;

/**
 *
 * @author Bernhard
 */
public class StringMatcher extends ClientMatcher {
    String connectionID;
    String userID;
    String groupID;
    
    public StringMatcher(String targetID, String connectionID, String userID, String groupID) {
        super(targetID);
        this.connectionID =connectionID;
        this.userID =userID;
        this.groupID =groupID;
    }

    public String getGroupID() {
        return targetID;
    }
    
    @Override
    public boolean matches(Channel channel) {
        if (channel instanceof ClientChannel) {
            ClientChannel cc =(ClientChannel)channel;
            if (!cc.getConnectionID().equals(connectionID)) {
                if (targetID.equals(messages.Message.ALL)) {
                    return true;
                } else {
                    if (cc.getUserID().equals(userID)) {
                        return true;
                    } else if (cc.getUserID().equals(targetID)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}