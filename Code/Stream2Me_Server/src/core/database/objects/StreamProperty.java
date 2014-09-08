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
    final int[] targetIDs;
    final int streamID;

    public StreamProperty(int[] targetIDs, int streamID) {
        this.targetIDs = targetIDs;
        this.streamID = streamID;
    }

    public int[] getTargetIDs() {
        return targetIDs;
    }

    public int getStreamID() {
        return streamID;
    }

    public ClientMatcher generateMatcher() {
        return new StreamMatcher(streamID, targetIDs);
    }
}
