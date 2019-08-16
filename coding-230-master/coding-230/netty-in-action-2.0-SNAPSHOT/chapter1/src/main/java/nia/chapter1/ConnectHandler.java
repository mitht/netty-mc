package nia.chapter1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by kerr.
 *
 * Listing 1.2 ChannelHandler triggered by a callback
 */
/*
*到达的; 归航的 inbound
* 适配器  adapter
*
* */
public class ConnectHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx)
            throws Exception {
        System.out.println(
                "Client " + ctx.channel().remoteAddress() + " connected");
    }
}