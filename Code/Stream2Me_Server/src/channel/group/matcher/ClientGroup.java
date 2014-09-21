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
public class ClientGroup extends ClientMatcher {
    String userID;
    
    public ClientGroup(String userID, String groupID) {
        super(groupID);
        this.userID =userID;
    }

    public String getGroupID() {
        return targetID;
    }
    
    @Override
    public boolean matches(Channel channel) {
        if (channel instanceof ClientChannel) {
            if (((ClientChannel)channel).getGroupID().equals(targetID)) {
                if (!((ClientChannel)channel).getUserID().equals(userID)) {
                    System.out.println("CLIENTGROUP: Writing to "+((ClientChannel)channel).getUserID());
                    return true;
                }
            }
        }
        return false;
    }
}
