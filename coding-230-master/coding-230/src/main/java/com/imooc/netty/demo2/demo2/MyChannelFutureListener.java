package com.imooc.netty.demo2.demo2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import java.nio.charset.Charset;

/**
 * @Author: rht
 * @Despriction:
 * @Date:Created in  19-8-7 下午1:28
 * @Mail: wx-renht@dtdream.com
 */
public class MyChannelFutureListener implements ChannelFutureListener {
    @Override
    public void operationComplete(ChannelFuture future) throws Exception {
        System.out.println("123456");
        if (future.isSuccess()){
            ByteBuf byteBuf = Unpooled.copiedBuffer("4111", Charset.defaultCharset());

            future.channel().writeAndFlush(byteBuf);
        }else {

            Throwable cause = future.cause();

            cause.printStackTrace();
        }

    }
}
