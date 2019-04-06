package com.zhoutao123.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/** Long 类型数据编码器 */
public class LongEncoder extends MessageToByteEncoder<Long> {
  @Override
  protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
    System.out.println("调用编码器的encode方法");
    out.writeLong(msg);
  }
}
