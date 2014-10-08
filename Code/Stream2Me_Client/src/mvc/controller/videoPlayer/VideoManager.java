/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller.videoPlayer;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import mvc.controller.StreamControl;

/**
 *
 * @author Bernhard
 */
public class VideoManager implements WindowListener {
    Map<String, VideoFrame> frames;
    VideoFrame defaultFrame;

    public VideoManager() {
        frames =new HashMap<String, VideoFrame>();
//        defaultFrame =new VideoFrame("defaultFrame", "ALL");
//        defaultFrame.setVisible(true);
    }
    
    public boolean addVideoFrame(final String streamID, String userID) {
        if (!frames.containsKey(streamID)) {
            VideoFrame vf =new VideoFrame("VideoPlayer", streamID, userID);
            vf.addWindowListener(this);
            vf.setVisible(true);
            return frames.put(streamID, vf) != null;
        }
        return true;
    }
    
    public boolean removeVideoFrame(String streamID) {
        return frames.remove(streamID) != null;
//        for (VideoFrame vf: frames) {
//            if (vf.getStreamID().equals(streamID)) {
//                vf.dispose();
//                return frames.remove(vf);
//            }
//        }
//        return false;
    }
    
    public void write(final String streamID, final String image) {
        (new Thread(new Runnable() {
            @Override
            public void run() {
//                defaultFrame.write(image);
                boolean found =false;
                VideoFrame vf =frames.get(streamID);
                if (vf != null) {
                    if (vf.getStreamID().equals(streamID)) {
                        vf.write(image);
                        found =true;
                    }
                }
//                for (VideoFrame vf: frames) {
////                    System.out.println(vf.getStreamID()+" --> "+streamID);
//                    if (vf.getStreamID().equals(streamID)) {
//                        vf.write(image);
//                        found =true;
//                    }
//                }
                System.out.println(found);
            }
        })).start();
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        VideoFrame vf =(VideoFrame)e.getSource();
        removeVideoFrame(vf.getStreamID());
        System.out.println("Closing");
        StreamControl.INSTANCE.closedVideoFrame(vf.getStreamID(), vf.getUserID());
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    public void stopAll() {
        Collection<VideoFrame> frameSet =frames.values();
        for (VideoFrame f: frameSet) {
            frames.remove(f);
            f.dispose();
        }
    }
}
