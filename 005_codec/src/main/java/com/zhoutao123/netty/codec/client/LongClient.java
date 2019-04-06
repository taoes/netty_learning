package com.zhoutao123.netty.codec.client;

import com.zhoutao123.netty.codec.LongDecoder;
import com.zhoutao123.netty.codec.LongEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class LongClient {

  public static void main(String[] args) {
    EventLoopGroup workGroup = new NioEventLoopGroup();

    try {
      Bootstrap bootstrap =
          new Bootstrap()
              .group(workGroup)
              .remoteAddress(new InetSocketAddress("localhost", 8080))
              .channel(NioSocketChannel.class)
              .handler(
                  new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                      ChannelPipeline pipeline = ch.pipeline();
                      pipeline.addLast(new LongDecoder());
                      pipeline.addLast(new LongEncoder());
                      pipeline.addLast(new ClientHandle());
                    }
                  });
      ChannelFuture sync = bootstrap.connect().sync();
      sync.channel().closeFuture().sync();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      workGroup.shutdownGracefully();
    }
  }
}
