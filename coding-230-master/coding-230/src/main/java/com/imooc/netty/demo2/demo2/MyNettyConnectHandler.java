package com.imooc.netty.demo2.demo2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Author: rht
 * @Despriction:
 * @Date:Created in  19-8-7 下午1:40
 * @Mail: wx-renht@dtdream.com
 */
public class MyNettyConnectHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println(ctx.channel().remoteAddress()+" test ");

    }
}
