/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package channel.group;

import channel.ClientChannel;
import channel.group.matcher.ClientMatcher;
import channel.group.matcher.RemoveMatcher;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ServerChannel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.ChannelMatcher;
import io.netty.channel.group.ChannelMatchers;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.internal.ConcurrentSet;
import io.netty.util.internal.StringUtil;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import messages.Message;
import messages.MessageType;

/**
 *
 * @author Bernhard
 */
public class ClientChannelGroup extends AbstractSet<Channel> implements ChannelGroup  {
    private static final AtomicInteger nextId = new AtomicInteger();
    private final EventExecutor executor;
    private final String name;
    private final ConcurrentSet<ClientChannel> serverChannels = new ConcurrentSet<ClientChannel>();
    private final ConcurrentSet<ClientChannel> nonServerChannels = new ConcurrentSet<ClientChannel>();
    private final ChannelFutureListener remover = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            remove(future.channel());
        }
    };
    
    public ClientChannelGroup(EventExecutor executor) {
        this("group-0x" + Integer.toHexString(nextId.incrementAndGet()), executor);
    }
    
    public ClientChannelGroup(String name, EventExecutor executor) {
        if (name == null) {
            throw new NullPointerException("name");
        }
        this.name = name;
        this.executor = executor;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public boolean isEmpty() {
        return nonServerChannels.isEmpty() && serverChannels.isEmpty();
    }

    @Override
    public int size() {
        return nonServerChannels.size() + serverChannels.size();
    }

    @Override
    public boolean contains(Object o) {
        if (o instanceof Channel) {
            Channel c = (Channel) o;
            if (o instanceof ServerChannel) {
                ClientChannel[] serverCH =serverChannels.toArray(new ClientChannel[0]);
                for (ClientChannel cc: serverCH) {
                    if (cc.getChannel().equals(o)) {
                        return true;
                    }
                }
            } else {
                ClientChannel[] serverCH =nonServerChannels.toArray(new ClientChannel[0]);
                for (ClientChannel cc: serverCH) {
                    if (cc.getChannel().equals(o)) {
                        return true;
                    }
                }
            }
            return false;
        } else {
            return false;
        }
    }
    
    public boolean containsByID(Channel o, String userID) {
        Channel c = (Channel) o;
        if (o instanceof ServerChannel) {
            ClientChannel[] serverCH =serverChannels.toArray(new ClientChannel[0]);
            for (ClientChannel cc: serverCH) {
                if (cc.getUserID().equals(userID)) {
                    return true;
                }
            }
        } else {
            ClientChannel[] serverCH =nonServerChannels.toArray(new ClientChannel[0]);
            for (ClientChannel cc: serverCH) {
                if (cc.getUserID().equals(userID)) {
                    return true;
                }
            }
        }
        return false;
    }

    public ClientChannel containsAndRemove(Object o) {
        if (o instanceof Channel) {
            Channel c = (Channel) o;
            if (o instanceof ServerChannel) {
                ClientChannel[] serverCH =serverChannels.toArray(new ClientChannel[0]);
                for (ClientChannel cc: serverCH) {
                    if (cc.getChannel().equals(o)) {
                        serverChannels.remove(cc);
                        return cc;
                    }
                }
            } else {
                ClientChannel[] serverCH =nonServerChannels.toArray(new ClientChannel[0]);
                for (ClientChannel cc: serverCH) {
                    if (cc.getChannel().equals(o)) {
                        nonServerChannels.remove(cc);
                        return cc;
                    }
                }
            }
            return null;
        } else {
            return null;
        }
    }

    @Override
    public boolean add(Channel channel) {
        return false;
    }
    
    public int add(ClientChannel channel) {
        ConcurrentSet<ClientChannel> set =
            channel instanceof ServerChannel ? serverChannels : nonServerChannels;
        
//        System.out.println("CLIENTCHANNELGROUP: "+(channel instanceof ServerChannel));

        boolean contained =containsByID(channel, channel.getUserID());
        
        boolean added = set.add(channel);
        if (added) {
            channel.closeFuture().addListener(remover);
        }
        
        if (added) {
            if (contained) {
                return 2;
            }
            return 1;
        }
        return -1;
    }
    
    public boolean remove(RemoveMatcher matcher) {
        for (ClientChannel c: nonServerChannels) {
            if (matcher.matches(c)) {
                if (nonServerChannels.remove(c)) {
                    c.closeFuture().removeListener(remover);
                    return true;
                }
            }
        }
        for (Channel c: serverChannels) {
            if (matcher.matches(c)) {
                if (serverChannels.remove(c)) {
                    c.closeFuture().removeListener(remover);
                    return true;
                }
            }
        }
        
        return false;
    }

    @Override
    public boolean remove(Object o) {
        if (!(o instanceof Channel)) {
            return false;
        }
        boolean removed;
        Channel c = (Channel) o;
        if (c instanceof ServerChannel) {
            removed = serverChannels.remove(c);
        } else {
            removed = nonServerChannels.remove(c);
        }
        if (!removed) {
            return false;
        }

        c.closeFuture().removeListener(remover);
        return true;
    }

    @Override
    public void clear() {
        nonServerChannels.clear();
        serverChannels.clear();
    }

    @Override
    public Iterator<Channel> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        Collection<Channel> channels = new ArrayList<Channel>(size());
        channels.addAll(serverChannels);
        channels.addAll(nonServerChannels);
        return channels.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        Collection<Channel> channels = new ArrayList<Channel>(size());
        channels.addAll(serverChannels);
        channels.addAll(nonServerChannels);
        return channels.toArray(a);
    }

    @Override
    public ChannelGroupFuture close() {
        return close(ChannelMatchers.all());
    }

    @Override
    public ChannelGroupFuture disconnect() {
        return disconnect(ChannelMatchers.all());
    }

    @Override
    public ChannelGroupFuture deregister() {
        return deregister(ChannelMatchers.all());
    }

    @Override
    public ChannelGroupFuture write(Object message) {
        return write(message, ChannelMatchers.all());
    }

    // Create a safe duplicate of the message to write it to a channel but not affect other writes.
    // See https://github.com/netty/netty/issues/1461
    private static Object safeDuplicate(Object message) {
        if (message instanceof ByteBuf) {
            return ((ByteBuf) message).duplicate().retain();
        } else if (message instanceof ByteBufHolder) {
            return ((ByteBufHolder) message).duplicate().retain();
        } else {
            return ReferenceCountUtil.retain(message);
        }
    }

    @Override
    public ChannelGroupFuture write(Object message, ChannelMatcher matcher) {
        return null;
    }
    
    public void write(Message message, ChannelMatcher matcher) {
        if (message == null) {
            throw new NullPointerException("message");
        }
        if (matcher == null) {
            throw new NullPointerException("matcher");
        }

        Map<Channel, ChannelFuture> futures = new LinkedHashMap<Channel, ChannelFuture>(size());
        for (Channel c: nonServerChannels) {
            if (matcher.matches(c)) {
                futures.put(c, c.write(safeDuplicate(message)));
            }
        }
        for (Channel c: serverChannels) {
            if (matcher.matches(c)) {
                futures.put(c, c.write(safeDuplicate(message)));
            }
        }

        ReferenceCountUtil.release(message);
    }

    @Override
    public ChannelGroup flush() {
        return flush(ChannelMatchers.all());
    }

    @Override
    public ChannelGroupFuture flushAndWrite(Object message) {
        return writeAndFlush(message);
    }

    @Override
    public ChannelGroupFuture writeAndFlush(Object message) {
        return writeAndFlush(message, ChannelMatchers.all());
    }

    @Override
    public ChannelGroupFuture disconnect(ChannelMatcher matcher) {
        throw new UnsupportedOperationException("Disconnect with ChannelGroupFuture return not implemented");
    }

    @Override
    public ChannelGroupFuture close(ChannelMatcher matcher) {
        throw new UnsupportedOperationException("Close with ChannelGroupFuture return not implemented");
    }

    @Override
    public ChannelGroupFuture deregister(ChannelMatcher matcher) {
        throw new UnsupportedOperationException("Deregister with ChannelGroupFuture return not implemented");
    }
    
    public void deregister(ClientMatcher matcher) {
        if (matcher == null) {
            throw new NullPointerException("matcher");
        }

        for (Channel c: serverChannels) {
            if (matcher.matches(c)) {
                ((ClientChannel)c).setGroupID(ClientMatcher.DEFAULTGROUP);
            }
        }
        for (Channel c: nonServerChannels) {
            if (matcher.matches(c)) {
                ((ClientChannel)c).setGroupID(ClientMatcher.DEFAULTGROUP);
            }
        }
        //write to all in default group to be disconnected
    }

    @Override
    public ChannelGroup flush(ChannelMatcher matcher) {
        for (Channel c: nonServerChannels) {
            if (matcher.matches(c)) {
                c.flush();
            }
        }
        return this;
    }

    @Override
    public ChannelGroupFuture flushAndWrite(Object message, ChannelMatcher matcher) {
        return writeAndFlush(message, matcher);
    }

    @Override
    public ChannelGroupFuture writeAndFlush(Object message, ChannelMatcher matcher) {
        throw new UnsupportedOperationException("Write and Flush with ChannelGroupFuture return not implemented");
    }
    
    public int writeAndFlush(Message message, ClientMatcher matcher) {
        if (message == null) {
            throw new NullPointerException("message");
        }
        if (matcher == null) {
            throw new NullPointerException("matcher");
        }

        int count =0;
        for (Channel c: serverChannels) {
            try {
                if (matcher.matches(c)) {
                    c.writeAndFlush(safeDuplicate(message));
                    count++;
                }
            } catch (Exception e) {
                if (c.isOpen()) {
                    throw e;
                }
            }
        }
        
        for (Channel c: nonServerChannels) {
            try {
                if (matcher.matches(c)) {
                    c.writeAndFlush(safeDuplicate(message));
                    count++;
                }
            } catch (Exception e) {
                if (c.isOpen()) {
                    throw e;
                }
            }
        }
        
        if (message.handle() != MessageType.auido || 
                message.handle() != MessageType.video) {
            Logger.getLogger(ClientChannelGroup.class.getName()).log(Level.INFO, 
                         "Writing message "+message.handle()+": ("
                                 +message.getMessage()+") to group. "
                                 +count+" users written to.");
        }

        ReferenceCountUtil.release(message);
        return count;
    }

    @Override
    public int hashCode() {
        return System.identityHashCode(this);
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    @Override
    public int compareTo(ChannelGroup o) {
        int v = name().compareTo(o.name());
        if (v != 0) {
            return v;
        }

        return System.identityHashCode(this) - System.identityHashCode(o);
    }

    @Override
    public String toString() {
        return StringUtil.simpleClassName(this) + "(name: " + name() + ", size: " + size() + ')';
    }

    protected String getConnectionID(Channel c) {
        if (c instanceof ServerChannel) {
            ClientChannel[] serverCH =serverChannels.toArray(new ClientChannel[0]);
            for (ClientChannel cc: serverCH) {
                if (cc.getChannel().equals(c)) {
                    return cc.getConnectionID();
                }
            }
        } else {
            ClientChannel[] serverCH =nonServerChannels.toArray(new ClientChannel[0]);
            for (ClientChannel cc: serverCH) {
                if (cc.getChannel().equals(c)) {
                    return cc.getConnectionID();
                }
            }
        }
        return null;
    }

    protected ClientChannel getChannel(Channel c) {
        if (c instanceof ServerChannel) {
            ClientChannel[] serverCH =serverChannels.toArray(new ClientChannel[0]);
            for (ClientChannel cc: serverCH) {
                if (cc.getChannel().equals(c)) {
                    return cc;
                }
            }
        } else {
            ClientChannel[] serverCH =nonServerChannels.toArray(new ClientChannel[0]);
            for (ClientChannel cc: serverCH) {
                if (cc.getChannel().equals(c)) {
                    return cc;
                }
            }
        }
        return null;
    }
}
