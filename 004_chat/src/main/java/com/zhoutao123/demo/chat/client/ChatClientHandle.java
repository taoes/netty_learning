package com.zhoutao123.demo.chat.client;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ChatClientHandle extends SimpleChannelInboundHandler<String> {
  @Override
  protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
    System.out.println("接收到消息:" + msg);
    Channel channel = ctx.channel();
    channel.writeAndFlush(Unpooled.copiedBuffer("你好@@".getBytes()));
  }
}
