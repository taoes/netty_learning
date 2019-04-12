package com.zhoutao123.demo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

public class DiscardChannelHandle extends SimpleChannelInboundHandler<ByteBuf> {
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

    // 获取数据
    String message = msg.toString(Charset.defaultCharset());
    System.out.println("接收到数据:" + message);
  }
}
