package com.zhouta123.demo.web_service;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.AsciiString;
import io.netty.handler.codec.http.*;
import io.netty.util.AttributeKey;

import java.io.File;
import java.io.IOException;

public class MyHttpRequestHandle extends SimpleChannelInboundHandler<FullHttpRequest> {

  private static AsciiString contentType = HttpHeaderValues.TEXT_PLAIN;

  private AttributeKey<String> attributeKey;

  public MyHttpRequestHandle(AttributeKey<String> attributeKey) {
    this.attributeKey = attributeKey;
  }

  @Override
  protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
    // 测试获取AttributeKey
    String s = ctx.channel().attr(attributeKey).get();
    System.out.println("获取到参数是:" + s);

    //    判断类型
    String uri = "/".equals(msg.uri()) ? "/index.html" : msg.uri();
    System.out.println(uri);

    File file = new File("/tmp" + uri);
    if (!file.exists()) {
      toNotFoundPage(ctx);
      return;
    }

    byte[] bytes = HttpUtils.getBytes(file);

    DefaultFullHttpResponse response =
        new DefaultFullHttpResponse(
            HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(bytes)); // 2

    HttpHeaders heads = response.headers();
    heads.add(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=UTF-8");
    heads.add(
        HttpHeaderNames.CONTENT_LENGTH, String.valueOf(response.content().readableBytes())); // 3
    heads.add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
    ctx.writeAndFlush(response);
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    super.channelReadComplete(ctx);
    ctx.flush();
  }

  /**
   * 转到404页面
   *
   * @param ctx
   */
  private void toNotFoundPage(ChannelHandlerContext ctx) throws IOException {

    byte[] bytes = HttpUtils.getBytes("/tmp/404.html");

    DefaultFullHttpResponse response =
        new DefaultFullHttpResponse(
            HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND, Unpooled.wrappedBuffer(bytes));

    HttpHeaders heads = response.headers();
    heads.add(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=UTF-8");
    heads.add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
    heads.add(HttpHeaderNames.CONTENT_LENGTH, String.valueOf(response.content().readableBytes()));
    ctx.writeAndFlush(response);
  }
}
