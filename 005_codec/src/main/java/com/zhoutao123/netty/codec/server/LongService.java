package com.zhoutao123.netty.codec.server;

import com.zhoutao123.netty.codec.LongDecoder;
import com.zhoutao123.netty.codec.LongEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class LongService {

  public static void main(String[] args) {
    EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    EventLoopGroup workGroup = new NioEventLoopGroup();

    try {
      ServerBootstrap boot =
          new ServerBootstrap()
              .group(bossGroup, workGroup)
              .channel(NioServerSocketChannel.class)
              .childHandler(
                  new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                      ChannelPipeline pipeline = ch.pipeline();
                      pipeline.addLast(new LongDecoder());
                      pipeline.addLast(new LongEncoder());
                      pipeline.addLast(new ServerHandle());
                    }
                  });
      ChannelFuture sync = boot.bind(8080).sync();
      System.out.println("服务器端启动在8080 端口");
      sync.channel().closeFuture().sync();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      bossGroup.shutdownGracefully();
      workGroup.shutdownGracefully();
    }
  }
}
