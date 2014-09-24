/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller.videoMessages;

import java.util.ArrayList;

/**
 *
 * @author Bernhard
 */
public class VideoManager {
    ArrayList<VideoFrame> frames;
    VideoFrame defaultFrame;

    public VideoManager() {
        frames =new ArrayList<VideoFrame>();
        defaultFrame =new VideoFrame("defaultFrame");
        defaultFrame.setVisible(true);
    }
    
    public void addVideoFrame(final String streamID) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                VideoFrame vf =new VideoFrame(streamID);
                vf.setVisible(true);
                frames.add(vf);
            }
        });
    }
    
    public void removeVideoFrame(String streamID) {
        for (VideoFrame vf: frames) {
            if (vf.getStreamID().equals(streamID)) {
                vf.dispose();
                frames.remove(vf);
            }
        }
    }
    
    public void write(final String streamID, final String image) {
        (new Thread(new Runnable() {
            @Override
            public void run() {
                defaultFrame.write(image);
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
