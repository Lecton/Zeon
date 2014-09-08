/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package channel;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelProgressivePromise;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoop;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import java.net.SocketAddress;

/**
 *
 * @author Bernhard
 */
public class ClientChannel implements Channel {
    private final Channel  ch;
    private final int userID;
    private int groupID;

    public ClientChannel(Channel channel, int userID, int groupID) {
        this.ch =channel;
        this.userID =userID;
        this.groupID =groupID;
    }

    public int getUserID() {
        return userID;
    }

    public int getGroupID() {
        return groupID;
    }

    public Channel getChannel() {
        return ch;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    @Override
    public EventLoop eventLoop() {
        return ch.eventLoop();
    }

    @Override
    public Channel parent() {
        return ch.parent();
    }

    @Override
    public ChannelConfig config() {
        return ch.config();
    }

    @Override
    public boolean isOpen() {
        return ch.isOpen();
    }

    @Override
    public boolean isRegistered() {
        return ch.isRegistered();
    }

    @Override
    public boolean isActive() {
        return ch.isActive();
    }

    @Override
    public ChannelMetadata metadata() {
        return ch.metadata();
    }

    @Override
    public SocketAddress localAddress() {
        return ch.localAddress();
    }

    @Override
    public SocketAddress remoteAddress() {
        return ch.remoteAddress();
    }

    @Override
    public ChannelFuture closeFuture() {
        return ch.closeFuture();
    }

    @Override
    public boolean isWritable() {
        return ch.isWritable();
    }

    @Override
    public Channel.Unsafe unsafe() {
        return ch.unsafe();
    }

    @Override
    public ChannelPipeline pipeline() {
        return ch.pipeline();
    }

    @Override
    public ByteBufAllocator alloc() {
        return ch.alloc();
    }

    @Override
    public ChannelPromise newPromise() {
        return ch.newPromise();
    }

    @Override
    public ChannelProgressivePromise newProgressivePromise() {
        return ch.newProgressivePromise();
    }

    @Override
    public ChannelFuture newSucceededFuture() {
        return ch.newSucceededFuture();
    }

    @Override
    public ChannelFuture newFailedFuture(Throwable cause) {
        return ch.newFailedFuture(cause);
    }

    @Override
    public ChannelPromise voidPromise() {
        return ch.voidPromise();
    }

    @Override
    public ChannelFuture bind(SocketAddress localAddress) {
        return ch.bind(localAddress);
    }

    @Override
    public ChannelFuture connect(SocketAddress remoteAddress) {
        return ch.connect(remoteAddress);
    }

    @Override
    public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
        return ch.connect(remoteAddress, localAddress);
    }

    @Override
    public ChannelFuture disconnect() {
        return ch.disconnect();
    }

    @Override
    public ChannelFuture close() {
        return ch.close();
    }

    @Override
    public ChannelFuture deregister() {
        return ch.deregister();
    }

    @Override
    public ChannelFuture bind(SocketAddress localAddress, ChannelPromise promise) {
        return ch.bind(localAddress, promise);
    }

    @Override
    public ChannelFuture connect(SocketAddress remoteAddress, ChannelPromise promise) {
        return ch.connect(remoteAddress, promise);
    }

    @Override
    public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
        return ch.connect(remoteAddress, localAddress, promise);
    }

    @Override
    public ChannelFuture disconnect(ChannelPromise promise) {
        return ch.disconnect();
    }

    @Override
    public ChannelFuture close(ChannelPromise promise) {
        return ch.close(promise);
    }

    @Override
    public ChannelFuture deregister(ChannelPromise promise) {
        return ch.deregister(promise);
    }

    @Override
    public Channel read() {
        return ch.read();
    }

    @Override
    public ChannelFuture write(Object msg) {
        return ch.write(msg);
    }

    @Override
    public ChannelFuture write(Object msg, ChannelPromise promise) {
        return ch.write(msg, promise);
    }

    @Override
    public Channel flush() {
        return ch.flush();
    }

    @Override
    public ChannelFuture writeAndFlush(Object msg, ChannelPromise promise) {
        return ch.writeAndFlush(msg, promise);
    }

    @Override
    public ChannelFuture writeAndFlush(Object msg) {
        return ch.writeAndFlush(msg);
    }

    @Override
    public <T> Attribute<T> attr(AttributeKey<T> key) {
        return ch.attr(key);
    }

    @Override
    public int compareTo(Channel o) {
        return ch.compareTo(o);
    }
}