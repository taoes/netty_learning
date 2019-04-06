package com.zhoutao123.demo.client;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

@ChannelHandler.Sharable
public class EchoClientHandle extends SimpleChannelInboundHandler<String> {

  /**
   * 连接创建成功的时候
   * @param ctx
   * @throws Exception
   */
  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    System.out.println("channelActive");
    ctx.channel().writeAndFlush(Unpooled.copiedBuffer("你好，这里是Netty客户端", CharsetUtil.UTF_8));
  }

  /**
   * 发生异常
   * @param ctx
   * @param cause
   */
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    System.out.println("exceptionCaught");
    cause.printStackTrace();
    ctx.close();
  }

  /**
   * 接收到服务端发过来的数据
   * @param ctx
   * @param msg
   */
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) {
    System.out.println("channelRead = " + msg);
  }

  @Override
  protected void messageReceived(ChannelHandlerContext ctx, String msg) {}
}
