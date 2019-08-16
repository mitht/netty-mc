package com.imooc.netty.demo2.chart;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @Author: rht
 * @Despriction:
 * @Date:Created in  19-8-14 下午4:12
 * @Mail: wx-renht@dtdream.com
 */
public class SimpleChatServerInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //framer 制定者；创作者,delimiter 分隔符
        pipeline.addLast("framer",new DelimiterBasedFrameDecoder(8192,Delimiters.lineDelimiter()));

        pipeline.addLast("decoder",new StringDecoder());

        pipeline.addLast("encoder",new StringEncoder());
        pipeline.addLast("handle",new SimpleChatServerHandler());

        System.out.println("SimpleChatClient:"+ch.remoteAddress() +"连接上");

    }
}
