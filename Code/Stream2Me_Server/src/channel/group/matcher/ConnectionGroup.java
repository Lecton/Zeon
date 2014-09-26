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
public class ConnectionGroup extends ClientMatcher {
    String connectionID;
    
    public ConnectionGroup(String connectionID, String groupID) {
        super(groupID);
        this.connectionID =connectionID;
    }

    public String getGroupID() {
        return targetID;
    }
    
    @Override
    public boolean matches(Channel channel) {
        if (channel instanceof ClientChannel) {
            if (((ClientChannel)channel).getGroupID().equals(targetID)) {
                if (!((ClientChannel)channel).getConnectionID().equals(connectionID)) {
                    System.out.println("CLIENTGROUP: Writing to "+((ClientChannel)channel).getUserID());
                    return true;
                }
            }
        }
        return false;
    }
}
