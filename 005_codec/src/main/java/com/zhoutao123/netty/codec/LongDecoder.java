package com.zhoutao123.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/** Long 类型数据解码器 */
public class LongDecoder extends ByteToMessageDecoder {
  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    System.out.println("调用解码器的decode方法");
    System.out.println("ByteBuf可读字节数:" + in.readableBytes());
    if (in.readableBytes() >= 8) {
      out.add(in.readLong());
    }
  }
}
