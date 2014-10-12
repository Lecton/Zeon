/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

import connection.bootstrap.Initialiser;
import core.database.DatabaseHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.util.Scanner;

/**
 *
 * @author Bernhard
 */
public class Server implements Runnable {
    private final int PORT;
    public static String name ="PubliCaptivation Server";
    private static Server server;
    
    public static void main(String[] args) {
        try {
            boolean offline =false;
            for (int i=0; i<args.length; i++) {
                String arg =args[i];
                if (arg.equals("-n")) {
                    if (i+1 < args.length) {
                        if (!args[i+1].startsWith("-")) {
                            Server.name =args[i+1];
                        }
                    }
                } else if (arg.equals("-offline")) {
                    offline =true;
                }
            }
            if (offline) {
                DatabaseHandler.setOffline();
            } else {
                boolean connected =DatabaseHandler.setOnline();
                if (!connected) {
                    DatabaseHandler.setOffline();
                }
            }
            server =new Server(2014);
            Thread serverThread =new Thread(server);
            serverThread.start();
            Scanner in =new Scanner(System.in);
            while (true) {
                String line =in.nextLine();
                if (line.equalsIgnoreCase("exit")) {
                    System.exit(0);
                }
            }
        } finally {
            server.close();
        }
    }

    public Server(int PORT) {
        this.PORT = PORT;
    }

    @Override
    public void run() {
        System.out.println("Starting "+name);
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
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            DatabaseHandler.database.close();
        }
    }
    
    public void close() {
        DatabaseHandler.database.close();
    }
}
