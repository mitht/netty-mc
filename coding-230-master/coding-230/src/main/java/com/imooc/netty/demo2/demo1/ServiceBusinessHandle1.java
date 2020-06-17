package com.imooc.netty.demo2.demo1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @Author: rht
 * @Despriction:
 * @Date:Created in  19-8-13 下午4:44
 * @Mail: wx-renht@dtdream.com
 */
@ChannelHandler.Sharable
public class ServiceBusinessHandle1 extends SimpleChannelInboundHandler<ByteBuf> {
    public static final ChannelHandler INSTANCE=new ServiceBusinessHandle1();

    /*
    ByteBuf分类
    Pooled和Unpooled：pooled类型的bytebuf是在已经申请好的内存块取一块内存，而Unpooled是直接通过JDK底层代码申请。
    Unsafe和非Unsafe：这里的Unsafe是JDK底层的对象，通过它能够直接操作到内存。
    Heap和Direct：一个是在堆上分配，一个是直接内存。Direct不受GC的控制。
    *
    * */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        ByteBuf data = Unpooled.directBuffer();
        data.writeBytes(msg);
        Object result = getResult(data);
        ctx.channel().writeAndFlush(result);
    }
    //模拟数据库取值的,耗时操作
    protected Object getResult(ByteBuf data){
        int level = ThreadLocalRandom.current().nextInt(1, 1000);
        int time;
        if (level <= 900) {
            time = 1;
        } else if (level <= 950) {
            time = 10;
        } else if (level <= 990) {
            time = 100;
        } else {
            time = 1000;
        }

        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }

        return data;
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // ignore
    }

//    @Override
//    protected void messageReceived(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
//        ByteBuf data = Unpooled.directBuffer();
//        data.writeBytes(msg);
//        Object result = getResult(data);
//        ctx.channel().writeAndFlush(result);
//    }
}
