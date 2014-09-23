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
public class ConnectionMatcher extends ClientMatcher {
    
    public ConnectionMatcher(String connectionID) {
        super(connectionID);
    }

    public String getGroupID() {
        return targetID;
    }
    
    @Override
    public boolean matches(Channel channel) {
        if (channel instanceof ClientChannel) {
            if (((ClientChannel)channel).getConnectionID().equals(targetID)) {
                System.out.println("CLIENTGROUP: Writing to "+((ClientChannel)channel).getUserID());
                return true;
            }
        }
        return false;
    }
}