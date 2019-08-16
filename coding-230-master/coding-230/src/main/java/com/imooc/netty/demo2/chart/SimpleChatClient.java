package com.imooc.netty.demo2.chart;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @Author: rht
 * @Despriction:
 * @Date:Created in  19-8-14 下午7:04
 * @Mail: wx-renht@dtdream.com
 */
public class SimpleChatClient {

    public static void main(String[] args) {
        
    }
    public void run(String host,int port){

        NioEventLoopGroup group = new NioEventLoopGroup();


        try{

            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new SimpleChatClientInitializer());
            Channel channel = bootstrap.connect(host, port).sync().channel();

            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while (true){
                channel.writeAndFlush(in.readLine()+"\r\n");
            }

        }catch (Exception e){


        }finally {
//            group.shutdownGracefully();
        }



    }
    
    
    
}
