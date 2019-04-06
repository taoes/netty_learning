package com.zhoutao123.netty.private_protocol.client;

import com.zhoutao123.netty.private_protocol.QuestionsEntity;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PrivateProtocolEncoder extends MessageToByteEncoder<QuestionsEntity> {
  @Override
  protected void encode(ChannelHandlerContext ctx, QuestionsEntity msg, ByteBuf out)
      throws Exception {
    byte[] questionBytes = msg.getQuestions().getBytes();
    // 写入题目长度
    out.writeInt(questionBytes.length);
    // 写入题目
    out.writeBytes(questionBytes);
    // 写入数字的个数
    out.writeInt(msg.getNumberCount());
    // 遍历写入数字
    for (int i = 0; i < msg.getNumberCount(); i++) {
      out.writeInt(msg.getNumbers()[i]);
    }
    // 写入校验码
    out.writeInt(msg.getVerificationCode());
    // 打印日志
    System.out.println("客户端编码完成");
  }
}
