package com.zhouta123.demo.web_service;

import java.io.*;

public class HttpUtils {

  /**
   * 将制定文件路径转换为字节数组
   *
   * @param filePath 文件的路径
   * @return
   * @throws IOException 如果文件不存在将抛出此异常
   */
  public static byte[] getBytes(String filePath) throws IOException {
    byte[] buffer = null;
    File file = new File(filePath);
    return getBytes(file);
  }

  /**
   * 将制定文件转换bytes[]
   *
   * @param file
   * @return
   * @throws IOException
   */
  public static byte[] getBytes(File file) throws IOException {
    byte[] buffer = null;
    FileInputStream fis = new FileInputStream(file);
    ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
    byte[] b = new byte[1000];
    int n;
    while ((n = fis.read(b)) != -1) {
      bos.write(b, 0, n);
    }
    fis.close();
    bos.close();
    buffer = bos.toByteArray();
    return buffer;
  }
}
