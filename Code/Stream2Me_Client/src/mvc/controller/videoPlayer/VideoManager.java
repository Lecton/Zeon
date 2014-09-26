/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller.videoPlayer;

import communication.handlers.MessageFactory;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import mvc.controller.ContactListControl;
import mvc.controller.Control;
import mvc.controller.StreamControl;
import mvc.controller.UserControl;
import mvc.model.person.Receiver;
import mvc.view.generalUI.containers.Button;

/**
 *
 * @author Bernhard
 */
public class VideoManager implements WindowListener {
    ArrayList<VideoFrame> frames;
    VideoFrame defaultFrame;

    public VideoManager() {
        frames =new ArrayList<VideoFrame>();
//        defaultFrame =new VideoFrame("defaultFrame", "ALL");
//        defaultFrame.setVisible(true);
    }
    
    public boolean addVideoFrame(final String streamID, String userID) {
        VideoFrame vf =new VideoFrame("VideoPlayer", streamID, userID);
        vf.addWindowListener(this);
        vf.setVisible(true);
        return frames.add(vf);
    }
    
    public boolean removeVideoFrame(String streamID) {
        for (VideoFrame vf: frames) {
            if (vf.getStreamID().equals(streamID)) {
                vf.dispose();
                return frames.remove(vf);
            }
        }
        return false;
    }
    
    public void write(final String streamID, final String image) {
        (new Thread(new Runnable() {
            @Override
            public void run() {
//                defaultFrame.write(image);
                boolean found =false;
                for (VideoFrame vf: frames) {
//                    System.out.println(vf.getStreamID()+" --> "+streamID);
                    if (vf.getStreamID().equals(streamID)) {
                        vf.write(image);
                        found =true;
                    }
                }
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
}
