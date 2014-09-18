/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core.database.objects;

import channel.group.matcher.ClientMatcher;
import channel.group.matcher.StreamMatcher;

/**
 *
 * @author Bernhard
 */
public class StreamProperty {
    final String[] targetIDs;
    final String streamID;
    final String groupID;

    public StreamProperty(String[] targetIDs, String streamID, String groupID) {
        this.targetIDs = targetIDs;
        this.streamID = streamID;
        this.groupID =groupID;
    }

    public String[] getTargetIDs() {
        return targetIDs;
    }

    public String getStreamID() {
        return streamID;
    }

    public String getGroupID() {
        return groupID;
    }

    public ClientMatcher generateMatcher() {
        return new StreamMatcher(streamID, targetIDs);
    }
}
