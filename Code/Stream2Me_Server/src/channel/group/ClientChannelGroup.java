package channel.group;

import client.Client;
import io.netty.channel.Channel;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.internal.ConcurrentSet;

/*
 * Copyright 2013 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

/**
 * The default {@link ChannelGroup} implementation.
 */
public class ClientChannelGroup extends DefaultChannelGroup {

    public ClientChannelGroup(EventExecutor executor) {
        super(executor);
    }

    public ClientChannelGroup(String name, EventExecutor executor) {
        super(name, executor);
    }

    public boolean add(ClientChannel channel) {
        boolean result =super.add(channel);
        return result;
    }

    @Override
    public boolean contains(Object o) {
        return super.contains(o);
    }
}
