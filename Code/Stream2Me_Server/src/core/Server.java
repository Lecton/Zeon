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
        int port =2014;
        try {
            boolean offline =false;
            for (int i=0; i<args.length; i++) {
                String arg =args[i];
                if (arg.equals("-n")) {
                    String temp ="";
                    for (int j=i+1; j<args.length; j++) {
                        if (!args[j].startsWith("-")) {
                            temp +=args[j]+" ";
                        } else {
                            break;
                        }
                    }
                    temp =temp.trim();
                    if (!temp.isEmpty()) {
                        Server.name =temp;
                    } else {
                        System.out.println("Name not accepted. Defaulting to "+Server.name);
                    }
                } else if (arg.equals("-p")) {
                    if (i+1 < args.length) {
                        if (!args[i+1].startsWith("-")) {
                            try {
                                port =Integer.parseInt(args[i+1]);
                                i++;
                            } catch (NumberFormatException e) {
                                System.out.println("Port setting failed. Defaulting to: "+port);
                            }
                        }
                    }
                } else if (arg.equals("--offline")) {
                    offline =true;
                } else if (arg.equals("--help")) {
                    System.out.println("Commands: ");
                    System.out.println("\t-n <Server name> [eg. -n Stream2Me]");
                    System.out.println("\t-p <Server port> [eg. -p 6060]");
                    System.out.println("\t--offline [Creates an offline server]");
                    System.out.println("");
                    System.exit(0);
                } else {
                    System.out.println(arg+" is not a valid command or input");
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
            server =new Server(port);
            Thread serverThread =new Thread(server);
            serverThread.start();
            Scanner in =new Scanner(System.in);
            while (true) {
                String line =in.nextLine();
                if (line.equalsIgnoreCase("exit")) {
                    System.exit(0);
                }
            }
        } catch (Exception e) {
            System.exit(-1);
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
        System.out.println("\ton PORT: "+PORT);
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
        catch (Exception ex) {
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            DatabaseHandler.database.close();
            System.exit(1);
        }
    }
    
    public void close() {
        DatabaseHandler.database.close();
    }
}
