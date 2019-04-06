package com.zhoutao123.netty.private_protocol;

public class QuestionsEntity {

  // 代码省略了set/get方法，自行添加

  private int questionsLength;

  private String questions;

  private int numberCount;

  private int[] numbers;

  private int verificationCode;

  public QuestionsEntity() {}

  public QuestionsEntity(
      int questionsLength, String questions,int numberCount, int[] numbers, int verificationCode) {
    this.questionsLength = questionsLength;
    this.questions = questions;
    this.numberCount = numberCount;
    this.numbers = numbers;
    this.verificationCode = verificationCode;
  }

  public int getQuestionsLength() {
    return questionsLength;
  }

  public void setQuestionsLength(int questionsLength) {
    this.questionsLength = questionsLength;
  }

  public String getQuestions() {
    return questions;
  }

  public void setQuestions(String questions) {
    this.questions = questions;
  }

  public int[] getNumbers() {
    return numbers;
  }

  public void setNumbers(int[] numbers) {
    this.numbers = numbers;
  }

  public int getVerificationCode() {
    return verificationCode;
  }

  public void setVerificationCode(int verificationCode) {
    this.verificationCode = verificationCode;
  }

  public int getNumberCount() {
    return numberCount;
  }

  public void setNumberCount(int numberCount) {
    this.numberCount = numberCount;
  }
}
