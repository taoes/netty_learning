package com.zhoutao123.demo.chat.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;

public class SelfHandle extends SimpleChannelInboundHandler<String> {


  @Override
  protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
    System.out.println("接收到消息:" + msg);
    Channel channel = ctx.channel();
    channel.writeAndFlush(Unpooled.copiedBuffer("你也好".getBytes()));
  }

  @Override
  public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
    super.disconnect(ctx, promise);
    Channel channel = ctx.channel();
    System.out.println("连接：" + channel.id() + "关闭");
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    super.channelActive(ctx);
    System.out.println("连接以激活");
    Channel channel = ctx.channel();
    channel.writeAndFlush(Unpooled.copiedBuffer("热不是个办法".getBytes()));
  }
}
