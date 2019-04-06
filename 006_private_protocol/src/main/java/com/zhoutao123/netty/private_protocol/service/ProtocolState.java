package com.zhoutao123.netty.private_protocol.service;

/** 协议状态枚举类型 */
public enum ProtocolState {
  READ_QUESTIONS_LENGTH("读取题目长度"),
  READ_QUESTIONS("读取题目"),
  READ_NUMBER_COUNT("读取数字个数"),
  READ_NUMBER("读取数字"),
  READ_VERITY_CODE("读取校验码");

  private String message;

  ProtocolState(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
