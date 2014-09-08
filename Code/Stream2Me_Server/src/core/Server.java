/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

import connection.bootstrap.Initialiser;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 *
 * @author Bernhard
 */
public class Server implements Runnable {
    private final int PORT;
    
    public static void main(String[] argv) {
        System.out.println("Starting Server");
        Thread serverThread =new Thread(new Server(2014));
        serverThread.start();
    }

    public Server(int PORT) {
        this.PORT = PORT;
    }

    @Override
    public void run() {
        EventLoopGroup bossGroup =new NioEventLoopGroup();
        EventLoopGroup workerGroup =new NioEventLoopGroup();
        
        try {
            ServerBootstrap bootstrap =new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new Initialiser());
            
            bootstrap.bind(PORT).channel().closeFuture().sync();
        }
        catch (InterruptedException ex) {
        }
        finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
