package com.zhoutao123.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 本文件由周涛创建,位于com.company包下 创建时间2018/4/20 11:46 邮箱:zhoutao@xiaodouwangluo.com 作用:创建运行服务
 *
 * @author tao
 */
public class DiscardServer {

  private int port;

  public DiscardServer(int port) {
    this.port = port;
  }

  public void run() throws InterruptedException {
    EventLoopGroup boos = new NioEventLoopGroup();
    EventLoopGroup worker = new NioEventLoopGroup();
    System.out.println("准备运行在端口:" + String.valueOf(this.port));

    try {

      ServerBootstrap serverBootstrap = new ServerBootstrap();
      serverBootstrap
          .group(boos, worker)
          .channel(NioServerSocketChannel.class)
          .option(ChannelOption.SO_BACKLOG, 128)
          .childHandler(
              new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                  socketChannel.pipeline().addLast(new DiscardServerHandle());
                }
              });

      ChannelFuture sync = serverBootstrap.bind(port).sync();
      sync.channel().closeFuture().sync();

    } finally {
      worker.shutdownGracefully();
      boos.shutdownGracefully();
    }
  }

  public static void main(String[] args) {
    DiscardServer server = new DiscardServer(8080);
    try {
      server.run();
    } catch (InterruptedException e) {
      System.out.println("发生了异常信息,异常信息如下所示：");
      e.printStackTrace();
    }
  }
}
