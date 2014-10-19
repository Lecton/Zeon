/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.publicCaptivation.stream2me.client.communication;

import io.netty.channel.ChannelHandlerContext;
import messages.Message;

/**
 *
 * @author Bernhard
 */
public class PoolEntry {
    Message message;
    ChannelHandlerContext ctx;
    int counter;

    public PoolEntry(ChannelHandlerContext ctx, Message message, int counter) {
        this.ctx = ctx;
        this.message = message;
        this.counter =counter;
    }
}
