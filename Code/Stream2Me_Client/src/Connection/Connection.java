/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Connection;

import Connection.MessageLog.ConnectionObserver;
import Interface.ClientGUI.GUI;
import Interface.ClientLogin.Login;
import Utils.Log;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.io.IOException;

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
    
    private void writeMessage(Messages.Message msg) {
        channel.writeAndFlush(msg);
    }
    
    public void write(Messages.Message m) throws IOException {
        if (m.getTargetID() == Messages.Message.IGNORE) {
            Log.error(this, "Message "+m.handle()+" ignored");
        } else if (m.getTargetID() == Messages.Message.SERVER) {
            writeMessage(m);
        } else if (m.getTargetID() == Messages.Message.ERROR) {
            Log.error(this, "Message "+m.handle()+" error");
        } else if (m.getTargetID() == Messages.Message.ALL) {
            writeMessage(m);
        } else if (m.getTargetID() < 0) {
            Log.error(this, "Unknown message type on "+m.handle());
        } else {
            writeMessage(m);
        }
    }
    
    public void writeSafe(Messages.Message m) {
        try {
            write(m);
        } catch (Exception ignored) {}
    }
    
    public void writeStackTrace(Messages.Message m) {
        try {
            write(m);
        } catch (IOException e) {
            Log.write(this, "Write Stack Trace Exception");
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
