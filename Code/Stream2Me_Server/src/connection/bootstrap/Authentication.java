/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connection.bootstrap;

import core.Server;
import io.netty.channel.Channel;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.net.InetAddress;
import messages.ConsoleMessage;

/**
 *
 * @author Bernhard
 */
public class Authentication implements GenericFutureListener<Future<Channel>> {
    private SslHandler sslHandler;

    public Authentication(SslHandler sslHandler) {
        this.sslHandler = sslHandler;
    }

    @Override
    public void operationComplete(Future<Channel> future) throws Exception {
        System.out.println("Handshake success: "+future.isSuccess());
        if (future.isSuccess()) {
            future.get().write(new ConsoleMessage(
                    "Welcome to PubliCaptivation" +
                    " secure stream2Me service!", Server.name));
            future.get().write(new ConsoleMessage(
                    "Your session is protectd by "+
                    sslHandler.engine().getSession().getCipherSuite()+
                    " cipher suite.", Server.name));
            future.get().flush();
            Handler.connections.add(future.get());
        } else {
            try {
                future.get().close();
            } catch (Exception e) {
                
            }
        }
    }
}
