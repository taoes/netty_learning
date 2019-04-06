package com.zhoutao123.netty.base;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;
import java.util.Arrays;

/** 转换字符 */
public class StringByteBuf {

  public static void main(String[] args) {
    // 构建默认编码
    //    Charset defaultCharset = Charset.defaultCharset();
    Charset charset = Charset.forName("utf-8");
    ByteBuf stringByteBuf = Unpooled.copiedBuffer("hello world", charset);
    if (stringByteBuf.hasArray()) {
      byte[] array = stringByteBuf.array();
      // as this, arrayOffset === 0
      int startIndex = stringByteBuf.arrayOffset() + stringByteBuf.readerIndex();
      // return value = writeIndex - readIndex
      int length = stringByteBuf.readableBytes();
      byte[] newBytes = Arrays.copyOfRange(array, startIndex, length);
      System.out.println("数组支撑转换:" + new String(newBytes,charset));
    }

    if(!stringByteBuf.hasArray()){
      int lenght = stringByteBuf.readableBytes();
      byte[] bytes = new byte[lenght];
      stringByteBuf.getBytes(stringByteBuf.readerIndex(),bytes);
      System.out.println(new String(bytes,charset));
    }


    for (int i = 0; i < stringByteBuf.readableBytes(); i++) {
      System.out.println((char) stringByteBuf.getByte(i));
    }
  }
}
