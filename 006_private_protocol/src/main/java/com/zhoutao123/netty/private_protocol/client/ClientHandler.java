package com.zhoutao123.netty.private_protocol.client;

import com.zhoutao123.netty.private_protocol.QuestionsEntity;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<Integer> {

  /**
   * 接受到结果后打印数据，并关闭上下文
   *
   * @param ctx
   * @param msg
   * @throws Exception
   */
  @Override
  protected void messageReceived(ChannelHandlerContext ctx, Integer msg) throws Exception {
    System.out.println("客户端接收到答案:" + msg);
    ctx.close();
  }

  /**
   * 连接成功后发送数据
   *
   * @param ctx
   * @throws Exception
   */
  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    String question = "给定的所有数据的和是多少?";
    QuestionsEntity entity = new QuestionsEntity();
    entity.setQuestions(question);
    entity.setNumberCount(5);
    entity.setNumbers(new int[] {13, 2, 3, 4, 5});
    entity.setQuestionsLength(123);
    entity.setVerificationCode(123);
    ctx.channel().writeAndFlush(entity);
    System.out.println("客户端发送数据完成");
  }
}
