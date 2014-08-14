package com.stream2me.connection;

import android.util.Log;
import Messages.Message;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Connection {
	private final String host;
	private final int PORT;
	
	private EventLoopGroup group;
	private Channel channel;
	private ConnectionInitialiser ci;
	
	public Connection(String host, int PORT) {
		Log.v("Connection","creating connection object");
		this.host =host;
		this.PORT =PORT;
	}
	
	public void makeConnection() {
		Log.v("makeConnection", "making connection");
		try {
			group =new NioEventLoopGroup();
			ci =new ConnectionInitialiser();
			
			Bootstrap bootstrap =new Bootstrap()
				.group(group)
				.channel(NioSocketChannel.class)
				.handler(ci);
			channel =bootstrap.connect(host, PORT).sync().channel();
		} catch (InterruptedException e) {
			Log.e("Connection", "Interrupt exception in channel");
		}
		Log.v("makeConnection","connection is open? "+channel.isOpen());
	}
	
	public void close() {
		group.shutdownGracefully();
	}
	
	public void write(Message msg) {
		channel.writeAndFlush(msg);
	}
	
	public ConnectionInitialiser getInitialiser() {
		return ci;
	}
}
