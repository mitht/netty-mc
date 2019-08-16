//package authoritative.guide.demo1;
//
//import io.netty.bootstrap.Bootstrap;
//import io.netty.channel.ChannelOption;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.nio.NioSocketChannel;
//
///**
// * @Author: rht
// * @Despriction:
// * @Date:Created in  19-8-15 下午5:21
// * @Mail: wx-renht@dtdream.com
// */
//public class TimeClient {
//
//    public void connect(int port,String host){
//
//        //配置客户端Nio 线程组
//        NioEventLoopGroup group = new NioEventLoopGroup();
//
//        try{
//
//            Bootstrap bootstrap = new Bootstrap();
//            bootstrap.group(group)
//                .channel(NioSocketChannel.class)
//                 //有数据发送时就马上发送，就将该选项设置为true关闭Nagle算法；false的话,不发送小包
//                .option(ChannelOption.TCP_NODELAY,true)
//
//            ;
//
//
//
//        }finally {
//
//        }
//
//
//
//    }
//
//
//
//}
