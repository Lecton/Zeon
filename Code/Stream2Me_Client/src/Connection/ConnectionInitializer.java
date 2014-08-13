/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Connection;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 *
 * @author Bernhard
 */
class ConnectionInitializer extends  ChannelInitializer<SocketChannel>{
    private ConnectionHandler ch =new ConnectionHandler();

    @Override
    protected void initChannel(SocketChannel c) throws Exception {
        ChannelPipeline pipeline =c.pipeline();
        
        pipeline.addLast("decoder", new ObjectDecoder(ClassResolvers.cacheDisabled(getClass().getClassLoader())));
        pipeline.addLast("encoder", new ObjectEncoder());
        pipeline.addLast("handler", ch);
    }
    
    protected ConnectionHandler getHandler() {
        return ch;
    }
}
