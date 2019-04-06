package com.zhoutao123.netty.private_protocol.service;

import com.zhoutao123.netty.private_protocol.QuestionsEntity;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/** 私有协议处理 */
public class PrivateProtocolDecoder extends ReplayingDecoder<ProtocolState> {

  private int numberCount = -1;

  private int questionLength = -1;

  private String question = null;

  private int[] numbers = null;

  private int verificationCode = 0;

  /** 将此编码器的初始状态设置为 {@link ProtocolState#READ_QUESTIONS_LENGTH} */
  public PrivateProtocolDecoder() {
    super(ProtocolState.READ_QUESTIONS_LENGTH);
  }

  /**
   * 解码数据
   *
   * <p>checkpoint(S s) 是将当前的状态设置为指定的类型，后续后新的数据更新的时候，会继续执行之前的逻辑
   *
   * @param ctx 当前处理器的上下文
   * @param in ByteBuf
   * @param out 写入的数据，将发送送给下个入站处理器
   * @throws Exception
   */
  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

    switch (state()) {
      case READ_QUESTIONS_LENGTH:
        // 读取题目长度
        questionLength = in.readInt();
        checkpoint(ProtocolState.READ_QUESTIONS);
      case READ_QUESTIONS:
        // 读取题目
        if (in.hasArray()) {
          question = new String(in.array(), in.arrayOffset() + in.readerIndex(), questionLength);
        } else {
          byte[] bytes = new byte[questionLength];
          in.getBytes(in.readerIndex(), bytes);
          question = new String(bytes, 0, bytes.length);
          // 因为getBytes是绝对操作，不会移动readIndex，因此读取完成后，需要手动移动readIndex
          in.readBytes(questionLength);
        }

        checkpoint(ProtocolState.READ_NUMBER_COUNT);
      case READ_NUMBER_COUNT:
        // 读取数字长度
        numberCount = in.readInt();

        checkpoint(ProtocolState.READ_NUMBER);
      case READ_NUMBER:
        numbers = new int[numberCount];
        for (int i = 0; i < numberCount; i++) {
          numbers[i] = in.readInt();
        }

        checkpoint(ProtocolState.READ_VERITY_CODE);
      case READ_VERITY_CODE:
        // 读取校验码
        verificationCode = in.readInt();
        // 封装数据
        QuestionsEntity entity = new QuestionsEntity();
        entity.setQuestionsLength(questionLength);
        entity.setQuestions(question);
        entity.setNumbers(numbers);
        entity.setVerificationCode(verificationCode);
        out.add(entity);
        break;
      default:
        throw new IllegalArgumentException();
    }
  }
}
