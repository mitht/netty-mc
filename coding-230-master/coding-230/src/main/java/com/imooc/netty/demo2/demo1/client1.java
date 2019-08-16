package com.imooc.netty.demo2.demo1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;

import java.util.concurrent.ExecutionException;
import static com.imooc.netty.ch12.thread.Constant.PORT;

/**
 * @Author: rht
 * @Despriction:
 * @Date:Created in  19-8-12 上午11:05
 * @Mail: wx-renht@dtdream.com
 */
public class client1 {
    private static final String SERVER_HOST = "127.0.0.1";

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new client1().start(PORT);
    }
    public void start(int port) throws ExecutionException, InterruptedException {
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        final Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
//                端口可以重复使用
                .option(ChannelOption.SO_REUSEADDR,true)
                //通过添加hanlder，我们可以监听Channel的各种动作以及状态的改变，包括连接，绑定，接收消息等。
                //handler在初始化时就会执行
                //而childHandler会在客户端成功connect后才执行，这是两者的区别。
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        //解码器,固定成度的解码器
                        ch.pipeline().addLast(new FixedLengthFrameDecoder(Long.BYTES));
                        ch.pipeline().addLast(ClientBusinessHandler1.INSTANCE);
                    }
                });

        for (int i = 0; i < 1000; i++) {
            bootstrap.connect(SERVER_HOST,port).get();
        }
    }


}
