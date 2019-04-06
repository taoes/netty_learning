package com.zhoutao123.netty.base;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Random;

public class ByteBufExample {

  public static void main2(String[] args) {

    ByteBuf byteBuf = Unpooled.buffer(10);
    writeData(byteBuf);

    for (int i = 0; i < byteBuf.capacity(); i++) {
      // get操作并不会移动readIndex
      System.out.print(byteBuf.getByte(i));
    }

    System.out.println();
    while (byteBuf.isReadable()) {
      // read开头或者skip开头则会移动readIndex
      System.out.print(byteBuf.readByte());
    }
    System.out.println();

    // 重置读写索引
    byteBuf.clear();
    writeData(byteBuf);
    System.out.println("当前可读索引：" + byteBuf.readerIndex());
    System.out.println("当前可写索引：" + byteBuf.writerIndex());
  }

  public static void writeData(ByteBuf byteBuf) {
    Random random = new Random(3);
    while (byteBuf.writableBytes() > 0) {
      byteBuf.writeByte(random.nextInt());
    }
  }

  public static void main(String[] args) {

    ByteBuf buffer = Unpooled.buffer(6);
    for (int i = 0; i < buffer.capacity(); i++) {
      buffer.setByte(i,i);
    }
    int index = buffer.indexOf(0,buffer.capacity(),Byte.valueOf("5"));
    System.out.println("查找到索引位置 = " + index);
    index = buffer.indexOf(0,buffer.capacity(),Byte.valueOf("9"));
    System.out.println("查找到索引位置 = " + index);
  }
}
