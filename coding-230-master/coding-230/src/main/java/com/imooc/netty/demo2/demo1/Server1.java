package com.imooc.netty.demo2.demo1;

import com.imooc.netty.ch12.thread.Constant;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;

import static com.imooc.netty.ch12.thread.Constant.PORT;

/**
 * @Author: rht
 * @Despriction:
 * @Date:Created in  19-8-13 下午3:12
 * @Mail: wx-renht@dtdream.com
 */
public class Server1 {

    public static void main(String[] args) {
        //接受新连接线程，主要负责创建新连接
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        //负责读取数据的线程，主要用于读取数据以及业务逻辑处理
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        //商业
        NioEventLoopGroup businessGroup = new NioEventLoopGroup(1000);

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup,workerGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        //端口重复使用,打开
        bootstrap.childOption(ChannelOption.SO_REUSEADDR,true);
        //它的目的是添加handler，用来监听已经连接的客户端的Channel的动作和状态。
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new FixedLengthFrameDecoder(Long.BYTES));
                ch.pipeline().addLast(businessGroup,ServiceBusinessHandle1.INSTANCE);
            }
        });

        bootstrap.bind(Constant.PORT).addListener((ChannelFutureListener) future -> System.out.println("bind success in port: " + PORT));

    }






}
