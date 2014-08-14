package ChatClient;


import Messages.StringMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bernhard
 */
public class ChatClient {
    final String host;
    final int PORT;
    
    public static void main(String[] args) {
        new ChatClient("localhost", 8888).run();
    }

    public ChatClient(String host, int PORT) {
        this.host = host;
        this.PORT = PORT;
    }
    
    public void run() {
        EventLoopGroup group =new NioEventLoopGroup();
        
        try {
            Bootstrap bootstrap =new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChatClientInitializer());
            Channel channel =bootstrap.connect(host, PORT).sync().channel();
            BufferedReader in =new BufferedReader(new InputStreamReader(System.in));
            
            while (true) {
                try {
                    String line =in.readLine();
                    channel.writeAndFlush(new StringMessage(0, 0, "Client", line));
                    System.out.println("ECHO: "+line);
                } catch (IOException ex) {
                    System.err.println("Writing error");
                }
            }
        }
        catch (InterruptedException ex) {
            System.err.println("Interrupt Error");
        }
        finally {
            group.shutdownGracefully();
        }
    }
}
