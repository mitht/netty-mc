package com.imooc.netty.ch3;

import com.imooc.netty.ch6.AuthHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.AttributeKey;

/**
 * @author
 */
public final class Server {

    public static void main(String[] args) throws Exception {
        // 1 创建线两个事件循环组
        // 一个是用于处理服务器端接收客户端连接的
        // 一个是进行网络通信的（网络读写的）
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 2 创建辅助工具类ServerBootstrap，用于服务器通道的一系列配置

            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup) // 绑定俩个线程组
                    //datagram数据报; 数据包; 数据报文; 數據報; 数据表;
                    .channel(NioServerSocketChannel.class)  // 指定NIO的模式.NioServerSocketChannel对应TCP, NioDatagramChannel对应UDP
                    .childOption(ChannelOption.TCP_NODELAY, true)//通过减少必须发送包的个数来增加网络软件系统的效率。
//                    .option(ChannelOption.SO_BACKLOG, 1024) // 设置TCP缓冲区
//                    .option(ChannelOption.SO_SNDBUF, 32 * 1024) // 设置发送缓冲大小
//                    .option(ChannelOption.SO_RCVBUF, 32 * 1024) // 这是接收缓冲大小
//                    .option(ChannelOption.SO_KEEPALIVE, true) // 保持连接
                    .childAttr(AttributeKey.newInstance("childAttr"), "childAttrValue")
                    .handler(new ServerHandler())
                    .childHandler(new ChannelInitializer<SocketChannel>() { // //SocketChannel建立连接后的管道
                        @Override
                        public void initChannel(SocketChannel ch) {
                            //                        // 3 在这里配置 通信数据的处理逻辑, 可以addLast多个...
                            ch.pipeline().addLast(new AuthHandler());
                            //..

                        }
                    });
            // 4 绑定端口, bind返回future(异步), 加上sync阻塞在获取连接处
        //synchronization  asynchronous 异步
            ChannelFuture f = b.bind(8888).sync();
            // 5 等待关闭, 加上sync阻塞在关闭请求处

            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}