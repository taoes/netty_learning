package com.zhoutao123.demo.time;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeServerChannelHandleAdapter extends SimpleChannelInboundHandler<LocalDateTime> {

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    System.out.println("添加了新的连接信息：id = " + ctx.channel().id());
  }

  @Override
  protected void messageReceived(ChannelHandlerContext ctx, LocalDateTime msg) throws Exception {
    // 转换时间格式
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String dateFormat = msg.format(dateTimeFormatter);
    System.out.println("接收到参数:" + dateFormat);
    ctx.channel().writeAndFlush(Unpooled.copiedBuffer(dateFormat, Charset.defaultCharset()));
  }
}
