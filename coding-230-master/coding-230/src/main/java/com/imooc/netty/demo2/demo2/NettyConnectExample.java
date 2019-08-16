//package com.imooc.netty.demo2.demo2;
//
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelFuture;
//import io.netty.channel.socket.nio.NioSocketChannel;
//
//import java.net.InetSocketAddress;
//
///**
// * @Author: rht
// * @Despriction:
// * @Date:Created in  19-8-7 下午1:20
// * @Mail: wx-renht@dtdream.com
// */
//public class NettyConnectExample {
//    //他代表了数据的载体,可以打开或者关闭,连接或者断开
//    private static  final Channel NIOSOCKETCHANNEL = new NioSocketChannel();
//    private static  final String host = "10.160.1.148";
//    private static  final int port= 8888;
//
//
//    public static void connect(){
//
//        ChannelFuture future = NIOSOCKETCHANNEL.connect(new InetSocketAddress(host, port));
//
//        future.addListener(new MyChannelFutureListener());
//
//
//
//    }
//
//
//
//
//}
