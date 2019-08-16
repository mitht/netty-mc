//package authoritative.guide.demo1;
//
//import io.netty.channel.ChannelHandler;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.socket.SocketChannel;
//
///**
// * @Author: rht
// * @Despriction:
// * @Date:Created in  19-8-15 下午4:04
// * @Mail: wx-renht@dtdream.com
// */
//public class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
//
//
//    @Override
//    protected void initChannel(SocketChannel ch) throws Exception {
//            ch.pipeline().addLast(new TimeServerHandler());
//    }
//}
