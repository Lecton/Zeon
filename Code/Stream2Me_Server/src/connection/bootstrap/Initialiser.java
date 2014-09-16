/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connection.bootstrap;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.ssl.SslHandler;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

/**
 *
 * @author Bernhard
 */
public class Initialiser extends ChannelInitializer<SocketChannel> {
    private final SSLContext sslCtx;

    public Initialiser() {
        this.sslCtx = SSLContextFactory.getServerContext();
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline =ch.pipeline();
        
        SSLEngine engine =sslCtx.createSSLEngine();
        engine.setUseClientMode(false);
        
        pipeline.addLast("ssl", new SslHandler(engine));
        pipeline.addLast("decoder", new ObjectDecoder(ClassResolvers.cacheDisabled(getClass().getClassLoader())));
        pipeline.addLast("encoder", new ObjectEncoder());
        pipeline.addLast("handler", new Handler());
        pipeline.addLast("endOfPipe", new EndOfPipe());
    }
}
