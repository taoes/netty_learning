package com.zhoutao123.demo.chat.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class Service {

  public static void main(String[] args) {
    Runtime.getRuntime()
        .addShutdownHook(
            new Thread() {
              @Override
              public void run() {
                try {
                  System.out.println("正在准备关闭服务器");
                  Thread.sleep(3000);
                } catch (InterruptedException e) {
                  e.printStackTrace();
                }
                System.out.println("服务器已关闭");
              }
            });

    EventLoopGroup boosGroup = new NioEventLoopGroup();
    EventLoopGroup workGroup = new NioEventLoopGroup();

    try {
      ServerBootstrap serverBootstrap =
          new ServerBootstrap()
              .group(workGroup, boosGroup)
              .channel(NioServerSocketChannel.class)
              .option(ChannelOption.SO_BACKLOG, 128)
              .childHandler(
                  new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                      ChannelPipeline pipeline = ch.pipeline();
                      pipeline.addLast(
                          new DelimiterBasedFrameDecoder(
                              1024, Unpooled.copiedBuffer("@@".getBytes())));
                      pipeline.addLast(new StringDecoder());
                      pipeline.addLast(new SelfHandle());
                    }
                  });
      ChannelFuture sync = serverBootstrap.bind(8099).sync();
      System.out.println("服务器启动成功在8099");
      sync.channel().closeFuture().sync();

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      workGroup.shutdownGracefully();
      boosGroup.shutdownGracefully();
    }
  }
}
