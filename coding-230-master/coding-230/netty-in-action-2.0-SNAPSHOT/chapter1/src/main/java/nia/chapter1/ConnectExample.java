package nia.chapter1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created by kerr.
 *
 * Listing 1.3 Asynchronous connect
 *
 * Listing 1.4 Callback in action
 */
public class ConnectExample {
    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();

    /**
     * Listing 1.3 Asynchronous connect
     *
     * Listing 1.4 Callback in action
     * */
    public static void connect() {
        Channel channel = CHANNEL_FROM_SOMEWHERE; //reference form somewhere
        // Does not block
        ChannelFuture future = channel.connect(
                new InetSocketAddress("192.168.0.1", 25));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                if (future.isSuccess()) {
                    //Pooled和Unpooled：pooled类型的bytebuf是在已经申请好的内存块取一块内存，而Unpooled是直接通过JDK底层代码申请
                    //创建yige byteBuf 持有数据
                    ByteBuf buffer = Unpooled.copiedBuffer(
                            "Hello", Charset.defaultCharset());
                    //将数据发送到远程节点
                    ChannelFuture wf = future.channel()
                            .writeAndFlush(buffer);
                    // ...
                } else {
                    //cause 原因
                    Throwable cause = future.cause();
                    cause.printStackTrace();
                }
            }
        });

    }
}