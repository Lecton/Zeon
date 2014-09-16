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

    public StreamProperty(String[] targetIDs, String streamID) {
        this.targetIDs = targetIDs;
        this.streamID = streamID;
    }

    public String[] getTargetIDs() {
        return targetIDs;
    }

    public String getStreamID() {
        return streamID;
    }

    public ClientMatcher generateMatcher() {
        return new StreamMatcher(streamID, targetIDs);
    }
}
