/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Utils;

/**
 *
 * @author Bernhard
 */
public class MessageUtils {
    public enum StreamType {AUDIO, VIDEO, MEDIA}
    private static int streamIDCount =0;
    
    public static String generateStreamID(String pre, StreamType type) {
        switch (type) {
            case AUDIO:
                return pre+"_as_"+(streamIDCount++);
            case VIDEO:
                return pre+"_vs_"+(streamIDCount++);
            case MEDIA:
                return pre+"_ms_"+(streamIDCount++);
            default:
                throw new IllegalArgumentException("Stream type not found");
        }
    }
    
    public static StreamType getStreamType(String streamID) {
        String[] segments =streamID.split("_");
        if (segments.length != 3) {
            throw new IllegalArgumentException("StreamID not valid");
        } else {
            if (segments[1].equalsIgnoreCase("as")) {
                return StreamType.AUDIO;
            } else if (segments[1].equalsIgnoreCase("vs")) {
                return StreamType.VIDEO;
            } else if (segments[1].equalsIgnoreCase("ms")) {
                return StreamType.MEDIA;
            } else {
                throw new IllegalArgumentException("Stream type not recognised");
            }
        }
    }
}
