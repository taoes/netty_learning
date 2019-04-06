package com.zhoutao123.demo.chat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class ChatClient {

  public static void main(String[] args) throws InterruptedException {
    EventLoopGroup workGroup = new NioEventLoopGroup();
    try {

      Bootstrap bootstrap = new Bootstrap();
      bootstrap
          .group(workGroup)
          .channel(NioSocketChannel.class)
          .remoteAddress("localhost", 8099)
          .handler(
              new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                  ChannelPipeline pipeline = ch.pipeline();
                  pipeline.addLast(new StringDecoder());
                  pipeline.addLast(new ChatClientHandle());
                }
              });
      ChannelFuture sync = bootstrap.connect().sync();
      sync.channel().closeFuture().sync();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      workGroup.shutdownGracefully();
    }
  }
}
