package com.imooc.netty.demo2.chart;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author: rht
 * @Despriction:
 * @Date:Created in  19-8-14 下午6:55
 * @Mail: wx-renht@dtdream.com
 */
public class SimpleChatClientHandler extends SimpleChannelInboundHandler<String> {
//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
//        System.out.println(msg);
//    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println(s);

    }
}
