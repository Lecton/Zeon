/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package channel.group.matcher;

import channel.ClientChannel;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelMatcher;
import messages.Message;

/**
 *
 * @author Bernhard
 */
public class ClientMatcher implements ChannelMatcher {
    private static final ClientGroup ALL_MATCHER = new ClientGroup("ALL") {
        @Override
        public boolean matches(Channel channel) {
            return true;
        }
    };
    private static final ClientGroup NON_MATCHER = new ClientGroup("IGNORE") {
        @Override
        public boolean matches(Channel channel) {
            return false;
        }
    };
    
    public static ClientGroup all() {
        return ALL_MATCHER;
    }
    
    public static ClientGroup non() {
        return NON_MATCHER;
    }
    
    public static ClientMatcher generateMatcher(Message message) {
        if (message.getTargetID().equals(Message.ALL)) {
            if (message.getTargetGroupID().equals(Message.IGNORE)) {
                return ClientMatcher.non();
            }else if (message.getTargetGroupID().equals(Message.IGNORE)) {
                return ClientMatcher.non();
            } else if (message.getTargetGroupID().equals(Message.ERROR)) {
                return ClientMatcher.non();
            } else if (message.getTargetGroupID().equals(Message.SERVER)) {
                return ClientMatcher.non();
            } else {
                return new ClientGroup(message.getTargetGroupID());
            }
        } else if (message.getTargetID().equals(Message.IGNORE)) {
            return ClientMatcher.non();
        } else if (message.getTargetID().equals(Message.ERROR)) {
            return ClientMatcher.non();
        } else if (message.getTargetID().equals(Message.SERVER)) {
            return ClientMatcher.non();
        } else {
            return new ClientMatcher(message.getTargetID());
        }
    }
    
    public static final String DEFAULTGROUP ="default";
    protected final String targetID;

    public ClientMatcher(String targetID) {
        this.targetID = targetID;
    }

    public String getTargetID() {
        return targetID;
    }
    
    @Override
    public boolean matches(Channel channel) {
        if (channel instanceof ClientChannel) {
            if (((ClientChannel)channel).getUserID().equals(targetID)) {
                return true;
            }
        }
        return false;
    }
}