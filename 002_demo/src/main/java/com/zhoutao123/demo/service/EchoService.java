package com.zhoutao123.demo.service;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.Data;

/** @author tao */
@Data
public class EchoService {

  private int port;

  private EventLoopGroup boosGroup;

  private EventLoopGroup workGroup;

  public void start() throws Exception {
     boosGroup = new NioEventLoopGroup();
     workGroup = new NioEventLoopGroup();
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
                  // 字符串解码器
                  pipeline.addLast(new StringDecoder());
                  // 字符串编码器
                  pipeline.addLast(new StringEncoder());
                  // 服务端处理器
                  pipeline.addLast(new EchoServiceHandle());
                }
              });
      ChannelFuture sync = bootstrap.bind(port).sync();
      System.out.println("Netty Service start with " + port + "...");
      sync.channel().closeFuture().sync();
    } finally {
      workGroup.shutdownGracefully();
      boosGroup.shutdownGracefully();
    }
  }

  public static void main(String[] args) throws Exception {
    EchoService service = new EchoService();
    service.setPort(8080);
    service.start();
  }
}
