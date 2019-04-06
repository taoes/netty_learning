package com.zhoutao123.netty.private_protocol.service;

import com.zhoutao123.netty.private_protocol.QuestionsEntity;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServiceHandler extends SimpleChannelInboundHandler<QuestionsEntity> {

  /**
   * 读取数据，计算，并写回数据
   *
   * @param ctx
   * @param msg
   * @throws Exception
   */
  @Override
  protected void messageReceived(ChannelHandlerContext ctx, QuestionsEntity msg) throws Exception {
    if (msg.getQuestionsLength() != 123) {
      ctx.channel().writeAndFlush(0);
    } else {

      int[] numbers = msg.getNumbers();
      int sum = 1;
      for (int i = 0; i < numbers.length; i++) {
        sum *= numbers[i];
      }

      System.out.println(msg.getQuestions());
      System.out.println("答案是:" + sum);
      ctx.channel().writeAndFlush(sum);
    }
  }
}
