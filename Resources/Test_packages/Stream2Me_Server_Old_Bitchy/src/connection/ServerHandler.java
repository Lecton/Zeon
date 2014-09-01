package connection;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bernhard
 */
public class ServerHandler {
    final int port;

    public ServerHandler(int port) {
        this.port = port;
    }
    
    public void run() {
        EventLoopGroup bossGroup =new NioEventLoopGroup(5);
        EventLoopGroup workerGroup =new NioEventLoopGroup(1000);
        
        
        
        try {
            ServerBootstrap bootstrap =new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ConnectionInitializer());
            
            bootstrap.bind(port).channel().closeFuture().sync();
        }
        catch (InterruptedException ex) {
        }
        finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
