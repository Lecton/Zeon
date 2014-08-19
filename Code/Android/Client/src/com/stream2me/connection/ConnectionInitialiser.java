package com.stream2me.connection;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class ConnectionInitialiser extends ChannelInitializer<SocketChannel>{
	private ConnectionHandler ch =new ConnectionHandler();
	
	@Override
	protected void initChannel(SocketChannel sc) throws Exception {
		ChannelPipeline pipeline =sc.pipeline();
		
		pipeline.addLast("decoder", new ObjectDecoder(ClassResolvers.cacheDisabled(getClass().getClassLoader())));
		pipeline.addLast("endocer", new ObjectEncoder());
		pipeline.addLast("handler", ch);
	}
	
	public ConnectionHandler getHandler() {
		return ch;
	}
}
