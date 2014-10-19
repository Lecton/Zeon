/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.publicCaptivation.stream2me.client.communication;

import javax.net.ssl.SSLException;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

/**
 *
 * @author Bernhard
 */
public class Initialiser extends  ChannelInitializer<SocketChannel> {
    private SslContext sslCtx;

    public Initialiser() {
//        this.sslCtx = TrustStore.getClientContext();
    	try {
    		this.sslCtx =SslContext.newClientContext(InsecureTrustManagerFactory.INSTANCE);
    	} catch (SSLException e) {
    		
    	}
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
