package com.zhouta123.demo.web_service;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.util.AttributeKey;

import java.io.IOException;

public class HttpWebService {

  public static void main(String[] args) throws IOException {


    EventLoopGroup boosGroup = new NioEventLoopGroup(1);
    EventLoopGroup workGroup = new NioEventLoopGroup();
    try {
      AttributeKey<String> key = AttributeKey.newInstance("key");
      ServerBootstrap bootstrap = new ServerBootstrap();
      ServerBootstrap serverBootstrap =
          bootstrap
              .group(boosGroup, workGroup)
              .channel(NioServerSocketChannel.class)
              .childHandler(
                  new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                      ChannelPipeline pipeline = ch.pipeline();
                      pipeline.addLast(new HttpRequestDecoder());
                      pipeline.addLast(new HttpResponseEncoder());
                      pipeline.addLast(new HttpObjectAggregator(512_000));
                      pipeline.addLast(new MyHttpRequestHandle(key));
                    }
                  })
              .attr(key, "Seven")
              .option(ChannelOption.SO_BACKLOG, 128) // determining the number of connections queued
              .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE);

      ChannelFuture sync = serverBootstrap.bind(8080).sync();
      System.out.println("服务器启动在8080端口");
      sync.channel().closeFuture().sync();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      boosGroup.shutdownGracefully();
      workGroup.shutdownGracefully();
    }
  }
}
