package com.zhoutao123.demo.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.time.LocalDateTime;
import java.util.List;

public class TimeEncoder extends MessageToMessageDecoder<ByteBuf> {

  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
    //将ByteBuf转换为String
    String dataStr = msg.toString(CharsetUtil.UTF_8).replace("\r\n","");
    //将String转换为Integer
    Integer dataInteger = Integer.valueOf(dataStr);
    //获取当前时间N小时后的数据
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime dataLocalDatetime = now.plusHours(dataInteger);
    out.add(dataLocalDatetime);
  }
}

