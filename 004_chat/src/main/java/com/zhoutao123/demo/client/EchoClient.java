package com.zhoutao123.demo.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/** @author tao */
@Data
@Accessors(chain = true)
@Slf4j
public class EchoClient {

  private int port;

  private String host;

  public void start() throws Exception {
    EventLoopGroup group = new NioEventLoopGroup();
    try {
      Bootstrap bootstrap = new Bootstrap();
      bootstrap
          .group(group)
          .channel(NioSocketChannel.class)
          .handler(
              new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                  ChannelPipeline pipeline = ch.pipeline();
                  // 字符串解码器
                  pipeline.addLast(new StringDecoder());
                  // 字符串编码器
                  pipeline.addLast(new StringEncoder());

                  pipeline.addLast(new EchoClientHandle());
                }
              });
      ChannelFuture sync = bootstrap.connect(new InetSocketAddress(host, port)).sync();
      sync.channel().closeFuture().sync();

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      group.shutdownGracefully().sync();
    }
  }

  public static void main(String[] args) throws Exception {
    EchoClient client = new EchoClient();
    client.setHost("127.0.0.1").setPort(8080);
    client.start();
  }
}
