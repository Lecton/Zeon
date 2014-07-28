//
//
//import Messages.Message;
//import java.awt.image.BufferedImage;
//import javax.swing.ImageIcon;
//
///**
// *
// * @author Bernhard
// */
//public class VideoStream extends Message {
//    private String img;
//    
//    /**
//     * Constructor to create and specify the variables of a video stream, including
//     * name of sender, ID, and recipient.
//     * @param Sender - the name of the sender of this message
//     * @param ID - the ID of the sender of this message
//     * @param to - the ID of the recipient of this message.
//     */
//    public VideoStream(String Sender, int ID, int to) {
//        this.Sender =Sender;
//        this.ID =ID;
//        this.to =to;
//    }
//    
//    /**
//     * Constructor to create and initialize a video stream by cloning an existing one.
//     * @param clone 
//     */
//    public VideoStream(VideoStream clone) {
//        this.Sender =clone.Sender;
//        this.ID =clone.ID;
//        this.to =clone.to;
//    }
//
//    @Override
//    public String getMessage() {
//        String value ="Video Message";
//        
//        return value;
//    }
//
//    public void setImg(String img) {
//        this.img = img;
//    }
//}