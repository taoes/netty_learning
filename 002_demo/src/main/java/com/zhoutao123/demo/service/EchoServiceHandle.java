package com.zhoutao123.demo.service;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Netty 服务Handle
 *
 * @author tao
 */
@ChannelHandler.Sharable
public class EchoServiceHandle extends SimpleChannelInboundHandler<String> {

  /**
   * 接收到新的消息
   * @param ctx
   * @param msg
   * @throws Exception
   */
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    // 打印接收到的消息
    System.out.println("Netty服务端接收到"+ctx.channel().id()+"发来的消息 " + msg);
    // 回复消息
    ctx.channel().writeAndFlush("Send ----> 客户端" + ctx.channel().id() + "你好，接收到 ---->"+msg);
  }

  /**
   * 有新的连接加入
   * @param ctx
   * @throws Exception
   */
  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    System.out.println("接入新的Channel,id = " + ctx.channel().id());
  }

  /**
   * 服务端发生异常信息的时候
   * @param ctx
   * @param cause
   */
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    // 服务端发生异常
    System.out.println("Netty 服务端发生异常 ,异常信息：" + cause);
    ctx.close();
  }

  @Override
  protected void messageReceived(ChannelHandlerContext ctx, String msg) {}
}
