package com.zhoutao123.netty.base;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/** Netty ByteBuf 自动扩容测试 */
public class ByteBufAllocTest {

  public static void main(String[] args) {

    ByteBuf byteBuf = Unpooled.buffer(2);

    for (int i = 0; i < 17; i++) {
      byteBuf.writeByte(i);
      System.out.println("当前容量: " + byteBuf.capacity());
    }
  }
}
