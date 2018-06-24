package com.zhoutao123.demo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/**
 * 本文件由周涛创建,位于com.company包下 创建时间2018/4/20 11:46 邮箱:zhoutao@xiaodouwangluo.com 作用:实现丢弃服务
 *
 * @author tao
 */
public class DiscardServerHandle extends ChannelHandlerAdapter {

  /**
   * 接收到SOCKET的时候会调用此方法
   *
   * @param ctx
   * @param msg
   * @throws Exception
   */
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    try {
      // 获取到接收的内容，并且实现
      ByteBuf in = (ByteBuf) msg;
      String message = in.toString(CharsetUtil.UTF_8);
      System.out.println(message);
    } finally {
      ReferenceCountUtil.release(msg);
    }
  }

  /**
   * 有新的连接加入的时候
   *
   * @param ctx
   * @throws Exception
   */
  @Override
  public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    System.out.println("新增Channel ,ChannelId = " + ctx.channel().id());
  }

  /**
   * 有连接断开被移除的时候调用
   *
   * @param ctx
   * @throws Exception
   */
  @Override
  public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
    System.out.println("移除Channel ,ChannelId = " + ctx.channel().id());
  }

  /**
   * 发生异常的时候调用
   *
   * @param ctx
   * @param cause
   * @throws Exception
   */
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }
}
