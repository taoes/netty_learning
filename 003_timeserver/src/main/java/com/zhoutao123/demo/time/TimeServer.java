package com.zhoutao123.demo.time;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TimeServer {

  public void start() throws Exception {
    EventLoopGroup boosGroup = new NioEventLoopGroup();
    EventLoopGroup workGroup = new NioEventLoopGroup();

    try {
      ServerBootstrap bootstrap = new ServerBootstrap();
      bootstrap
          .group(boosGroup, workGroup)
          .channel(NioServerSocketChannel.class)
          .option(ChannelOption.SO_BACKLOG, 128)
          .childHandler(
              new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                  ChannelPipeline pipeline = ch.pipeline();
                  //设置编码
                  pipeline.addLast(new TimeEncoder());
                  //设置服务处理
                  pipeline.addLast(new TimeServerChannelHandleAdapter());
                }
              });

      ChannelFuture sync = bootstrap.bind(9998).sync();
      System.out.println("Netty Server start with 9998 port");
      sync.channel().closeFuture().sync();
    } finally {
      workGroup.shutdownGracefully();
      boosGroup.shutdownGracefully();
    }
  }

  public static void main(String[] args) throws Exception {
    TimeServer server = new TimeServer();
    server.start();
  }
}
