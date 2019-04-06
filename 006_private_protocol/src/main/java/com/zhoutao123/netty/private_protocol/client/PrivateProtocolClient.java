package com.zhoutao123.netty.private_protocol.client;

import com.zhoutao123.netty.private_protocol.codec.IntegerDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/** 私有协议客户端 */
public class PrivateProtocolClient {

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
                                          //解码器
                                          pipeline.addLast(new IntegerDecoder());
                                          // 编码器
                                          pipeline.addLast(new PrivateProtocolEncoder());
                                          // 最终处理器
                                          pipeline.addLast(new ClientHandler());
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
