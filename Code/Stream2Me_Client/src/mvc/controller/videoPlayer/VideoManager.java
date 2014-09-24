/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller.videoPlayer;

import java.util.ArrayList;

/**
 *
 * @author Bernhard
 */
public class VideoManager {
    ArrayList<VideoFrame> frames;
    VideoFrame defaultFrame;
    
    mvc.controller.videoMessages.VideoFrame defaultFrame2;

    public VideoManager() {
        frames =new ArrayList<VideoFrame>();
        defaultFrame =new VideoFrame("defaultFrame", "ALL");
        defaultFrame.setVisible(true);
        
//        defaultFrame2 =new mvc.controller.videoMessages.VideoFrame("defaultFrame");
//        defaultFrame2.setVisible(true);
    }
    
//    private boolean addVideoFrame(final String streamID) {
//        VideoFrame vf =new VideoFrame("VideoPlayer", streamID);
//        vf.setVisible(true);
//        return frames.add(vf);
//    }
//    
//    private boolean removeVideoFrame(String streamID) {
//        for (VideoFrame vf: frames) {
//            if (vf.getStreamID().equals(streamID)) {
//                vf.dispose();
//                return frames.remove(vf);
//            }
//        }
//        return false;
//    }
    
    public void write(final String streamID, final String image) {
        (new Thread(new Runnable() {
            @Override
            public void run() {
                defaultFrame.write(image);
                defaultFrame2.write(image);
//                boolean found =false;
//                for (VideoFrame vf: frames) {
//                    if (vf.getStreamID().equals(streamID)) {
//                        vf.write(image);
//                        found =true;
//                    }
//                }
//                if (!found) {
//                    addVideoFrame(streamID);
//                    write(streamID, image);
//                }
            }
        })).start();
    }
}
