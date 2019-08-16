package com.imooc.netty.demo2.chart;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @Author: rht
 * @Despriction:
 * @Date:Created in  19-8-14 下午5:04
 * @Mail: wx-renht@dtdream.com
 */

/*
1.SimpleChatServerHandler 继承自 SimpleChannelInboundHandler，这个类实现了ChannelInboundHandler接口，
ChannelInboundHandler 提供了许多事件处理的接口方法，然后你可以覆盖这些方法。
现在仅仅只需要继承 SimpleChannelInboundHandler 类而不是你自己去实现接口方法。

2.覆盖了 handlerAdded() 事件处理方法。每当从服务端收到新的客户端连接时，
客户端的 Channel 存入ChannelGroup列表中，并通知列表中的其他客户端 Channel

3.覆盖了 handlerRemoved() 事件处理方法。每当从服务端收到客户端断开时，
客户端的 Channel 移除 ChannelGroup 列表中，并通知列表中的其他客户端 Channel

4.覆盖了 channelRead0() 事件处理方法。每当从服务端读到客户端写入信息时，
将信息转发给其他客户端的 Channel。其中如果你使用的是 Netty 5.x 版本时，需要把 channelRead0() 重命名为messageReceived()

5.覆盖了 channelActive() 事件处理方法。服务端监听到客户端活动

6.覆盖了 channelInactive() 事件处理方法。服务端监听到客户端不活动

7.exceptionCaught() 事件处理方法是当出现 Throwable 对象才会被调用，即当 Netty 由于 IO 错误或者处理器在处理事件时抛出的异常时。在大部分情况下，
捕获的异常应该被记录下来并且把关联的 channel 给关闭掉。然而这个方法的处理方式会在遇到不同异常的情况下有不同的实现，比如你可能想在关闭连接之前发送一个错误码的响应消息。


* */
public class SimpleChatServerHandler extends SimpleChannelInboundHandler<String> {

   public static DefaultChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


//    1. handlerAdded，ChannelHandler添加到实际上下文中准备处理事件
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            channel.writeAndFlush("[SERVER] - " +incoming.remoteAddress()+" 加入\n");
        }
        channels.add(ctx.channel());
    }

//    2. handlerRemoved，将ChannelHandler从实际上下文中删除，不再处理事件

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            channel.writeAndFlush("[SERVER] - " +incoming.remoteAddress() +" 离开\n");
        }

        channels.remove(ctx.channel());
    }
//4. 调用channelRead0方法完成客户端数据的读取和相应

//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
//        Channel incoming = ctx.channel();
//        for (Channel channel : channels) {
//            if (channel != incoming){
//                channel.writeAndFlush("[" + incoming.remoteAddress() + "]" + msg + "\n");
//            }else {
//                channel.writeAndFlush("[you]" + msg + "\n");
//            }
//        }
//    }
//5.覆盖了 channelActive() 事件处理方法。服务端监听到客户端活动
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"在线");

    }
//6.覆盖了 channelInactive() 事件处理方法。服务端监听到客户端不活动
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"掉线");
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"异常");

        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            if (channel != incoming){
                channel.writeAndFlush("[" + incoming.remoteAddress() + "]" + msg + "\n");
            }else {
                channel.writeAndFlush("[you]" + msg + "\n");
            }
        }
    }
}
