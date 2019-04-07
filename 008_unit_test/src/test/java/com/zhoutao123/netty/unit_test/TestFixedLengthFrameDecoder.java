package com.zhoutao123.netty.unit_test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import org.junit.Assert;
import org.junit.Test;

/**
 * 使用 {@link EmbeddedChannel} 进行入站的测试
 *
 * <p>该测试用于对某个Decoder测试
 */
public class TestFixedLengthFrameDecoder {

  @Test
  public void test() {
    ByteBuf byteBuf = Unpooled.buffer();
    System.out.println("拷贝数据");
    for (int i = 0; i < 3; ++i) {
      byteBuf.writeByte(i);
    }

    ByteBuf input = byteBuf.duplicate();
    EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));

    Assert.assertTrue(channel.writeInbound(input.retain()));
    Assert.assertTrue(channel.finish());

    ByteBuf read = channel.readInbound();
    Assert.assertEquals(byteBuf.readSlice(3), read);
    read.release();

    Assert.assertNull(channel.readInbound());
    byteBuf.release();
  }
}
