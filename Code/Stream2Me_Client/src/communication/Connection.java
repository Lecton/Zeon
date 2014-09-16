/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package communication;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import messages.Message;

/**
 *
 * @author Bernhard
 */
public class Connection {
    protected static int PORT;
    protected static String HOST;
    
    private EventLoopGroup group;
    private Channel channel;
    private Thread poolThread;
    
    public Connection(String HOST, int PORT) {
        this.HOST =HOST;
        this.PORT =PORT;
    }
    
    public void makeConnection() throws InterruptedException {
        group =new NioEventLoopGroup(10);
        
        Bootstrap bootstrap =new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new Initialiser());
        channel =bootstrap.connect(HOST, PORT).sync().channel();
        
        poolThread =new Thread(new Pool());
        poolThread.start();
    }
    
    public void close() {
        group.shutdownGracefully();
        Pool.stop();
    }
    
    public void writeMessage(Message msg) {
        channel.writeAndFlush(msg);
    }
}