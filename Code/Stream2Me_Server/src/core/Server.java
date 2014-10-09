/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

import connection.bootstrap.Initialiser;
import core.database.DatabaseHandler;
import core.database.online.Database;
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
    public static String name ="PubliCaptivation Server";
    
    public static void main(String[] argv) {
        boolean offline =false;
        for (int i=0; i<argv.length; i++) {
            String arg =argv[i];
            if (arg.equals("-n")) {
                if (i+1 < argv.length) {
                    if (!argv[i+1].startsWith("-")) {
                        Server.name =argv[i+1];
                    }
                }
            } else if (arg.equals("-offline")) {
                offline =true;
            }
        }
        
        System.out.println("Starting "+name);
        if (offline) {
            DatabaseHandler.setOffline();
        } else {
            boolean connected =DatabaseHandler.setOnline();
            if (!connected) {
                DatabaseHandler.setOffline();
            }
        }
        Thread serverThread =new Thread(new Server(2014));
        serverThread.start();
    }

    public Server(int PORT) {
        this.PORT = PORT;
    }

    @Override
    public void run() {
        DatabaseHandler.userHandler.generateGroups();
        
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
