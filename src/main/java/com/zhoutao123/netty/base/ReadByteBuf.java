package com.zhoutao123.netty.base;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/** 验证ByteBuf的clean操作并不会清除内容 */
public class ReadByteBuf {

  public static void main(String[] args) {
    // 申请空间
    ByteBuf byteBuf = Unpooled.buffer(10);
    // 写入7个数据
    for (int i = 0; i < 7; i++) {
      byteBuf.writeByte(i);
    }

    // 输出数据
    while (byteBuf.isReadable()) {
      System.out.print(byteBuf.readByte());
      System.out.print(",");
    }
    System.out.println();
    // 打印当前索引位置
    int writeIndex = byteBuf.writerIndex();
    // 重置索引
    byteBuf.clear();

    // 写入两个数据
    byteBuf.writeByte(10).writeByte(10);
    // 将writeIndex索引设置到{writeIndex}
    byteBuf.writerIndex(writeIndex);
    // 输出数据,可见0和1被覆盖了，其他的还是原来的数据
    while (byteBuf.isReadable()) {
      System.out.print(byteBuf.readByte());
      System.out.print(",");
    }
  }

}
