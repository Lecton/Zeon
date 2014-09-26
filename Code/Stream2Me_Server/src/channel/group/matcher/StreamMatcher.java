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
public class StreamMatcher extends ClientMatcher {
    String[] targetIDs;

    public StreamMatcher(String streamID, String[] targetIDs) {
        super(streamID);
        this.targetIDs =targetIDs;
    }
    
    private boolean contains(String userID) {
        for (int i=0; i<targetIDs.length; i++) {
            if (targetIDs[i].equals(userID)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean matches(Channel channel) {
        if (channel instanceof ClientChannel) {
            return contains(((ClientChannel)channel).getUserID());
        }
        return false;
    }
}
