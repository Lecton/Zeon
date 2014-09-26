/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package communication;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.ssl.SslContext;
import java.util.logging.Logger;

/**
 *
 * @author Bernhard
 */
public class Initialiser extends  ChannelInitializer<SocketChannel> {
    private final static Logger LOGGER = Logger.getLogger(Initialiser.class.getName());
    
    private final SslContext sslCtx;

    public Initialiser() {
        this.sslCtx = TrustStore.getClientContext();
    }
    
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline =ch.pipeline();
        
        if (sslCtx != null) {
            pipeline.addLast("ssl", sslCtx.newHandler(ch.alloc(), Connection.HOST, Connection.PORT));
        }
        pipeline.addLast("decoder", new ObjectDecoder(ClassResolvers.cacheDisabled(getClass().getClassLoader())));
        pipeline.addLast("encoder", new ObjectEncoder());
        pipeline.addLast("handler", new Handler());
    }
    
}
