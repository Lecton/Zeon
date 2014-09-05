/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connection;

import userInterface.generalUI.GUI;
import userInterface.authentication.Login;
import utils.Log;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.io.IOException;
import messages.Message;

/**
 *
 * @author Bernhard
 */
public class Connection {
    private int PORT;
    private String host;
    
    private EventLoopGroup group;
    private Channel channel;
    private ConnectionInitializer ci;
    
    public Connection(String host, int PORT) {
        this.host =host;
        this.PORT =PORT;
    }
    
    public void makeConnection() throws InterruptedException {
        group =new NioEventLoopGroup();
        ci =new ConnectionInitializer();
        
        Bootstrap bootstrap =new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(ci);
        channel =bootstrap.connect(host, PORT).sync().channel();
        
    }
    
    public void setHandlerUserInterface(GUI userInterface) {
        ci.getHandler().setUserInterface(userInterface);
    }
    
    public void close() {
        group.shutdownGracefully();
    }
    
    private void writeMessage(Message msg) {
        channel.writeAndFlush(msg);
    }
    
    public void write(Message m) throws IOException {
        if (m.getTargetID() == Message.IGNORE) {
            Log.error(this.getClass(), "Message "+m.handle()+" ignored");
        } else if (m.getTargetID() == Message.SERVER) {
            writeMessage(m);
        } else if (m.getTargetID() == Message.ERROR) {
            Log.error(this.getClass(), "Message "+m.handle()+" error");
        } else if (m.getTargetID() == Message.ALL) {
            writeMessage(m);
        } else if (m.getTargetID() < 0) {
            Log.error(this.getClass(), "Unknown message type on "+m.handle());
        } else {
            writeMessage(m);
        }
    }
    
    public void writeSafe(Message m) {
        try {
            write(m);
        } catch (Exception ignored) {}
    }
    
    public void writeStackTrace(Message m) {
        try {
            write(m);
        } catch (IOException e) {
            Log.write(this.getClass(), "Write Stack Trace Exception");
            e.printStackTrace();
        }
    }

    public void setHandlerLoggedOut() {
        ci.getHandler().setPass(false);
    }

    public void setLoginOwner(Login loginOwner) {
        ci.getHandler().setOwner(loginOwner);
    }
}
