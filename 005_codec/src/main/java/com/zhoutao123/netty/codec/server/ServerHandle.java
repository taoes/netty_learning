package com.zhoutao123.netty.codec.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandle extends SimpleChannelInboundHandler<Long> {
  @Override
  protected void messageReceived(ChannelHandlerContext ctx, Long msg) throws Exception {
    System.out.println("ServerHandle接收到数据：" + String.valueOf(msg));
    // 服务器端写回数据
    ctx.channel().writeAndFlush(123456L);
  }
}
