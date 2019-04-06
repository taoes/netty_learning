package com.zhoutao123.netty.base;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Iterator;

public class CompositeByteBufExample {

  public static void main(String[] args) {
    CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer();
    ByteBuf heapBuf = Unpooled.buffer(10);
    ByteBuf directBuf = Unpooled.directBuffer(8);

    // 新增ByteBuf
    compositeByteBuf.addComponents(heapBuf, directBuf);
    // 删除ByteBuf compositeByteBuf.removeComponent(0);
    // 遍历ByteBuf
    Iterator<ByteBuf> iterator = compositeByteBuf.iterator();
    while (iterator.hasNext()) {
      ByteBuf next = iterator.next();
      System.out.println(next.toString());
    }

    System.out.println("总Capacity = " + compositeByteBuf.capacity());
  }
}
