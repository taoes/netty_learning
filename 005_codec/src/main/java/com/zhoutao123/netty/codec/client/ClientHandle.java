package com.zhoutao123.netty.codec.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandle extends SimpleChannelInboundHandler<Long> {
  @Override
  protected void messageReceived(ChannelHandlerContext ctx, Long msg) throws Exception {
    System.out.println("接收到服务器消息:" + msg);
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    Channel channel = ctx.channel();
    channel.writeAndFlush(123456L);
  }
}
