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
    int[] targetIDs;

    public StreamMatcher(int streamID, int[] targetIDs) {
        super(streamID);
        this.targetIDs =targetIDs;
    }
    
    private boolean contains(int userID) {
        for (int i=0; i<targetIDs.length; i++) {
            if (targetIDs[i] == userID) {
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
