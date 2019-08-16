//package authoritative.guide.demo1;
//
//import io.netty.bootstrap.ServerBootstrap;
//import io.netty.channel.ChannelFuture;
//import io.netty.channel.ChannelOption;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.nio.NioServerSocketChannel;
//
///**
// * @Author: rht
// * @Despriction:
// * @Date:Created in  19-8-15 下午3:52
// * @Mail: wx-renht@dtdream.com
// */
//public class TimeServer {
//
//    public void bind(int port) throws InterruptedException {
//        //包含了一组Nio线程,一个用于接受网络客户端的连接,
//        //另一个用于进行sockerChannel的网络读写
//        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
//        NioEventLoopGroup workGroup = new NioEventLoopGroup();
//        //辅助启动类
//        ServerBootstrap bootstrap = new ServerBootstrap();
//
//        try {
//
//
//            bootstrap.group(bossGroup, workGroup)
//                    .channel(NioServerSocketChannel.class)
//                    //积压; 储存 backlog
//                    //服务端处理客户端连接请求是顺序处理的，所以同一时间只能处理一个客户端连接，
//                    // 多个客户端来的时候，服务端将不能处理的客户端连接请求放在队列中等待处理，backlog参数指定了队列的大小
//                    .option(ChannelOption.SO_BACKLOG, 1024)
//                    //它的作用,主要处理网络io事件,例如记录日志,对消息进行编解码
//                    .childHandler(new ChildChannelHandler());
//            //同步阻塞
//            ChannelFuture future = bootstrap.bind(port).sync();
//            //等待服务器监听端口关闭
//            future.channel().closeFuture().sync();
//        }finally {
//            //优雅关机,释放线程池的资源
//            bossGroup.shutdownGracefully();
//            workGroup.shutdownGracefully();
//        }
//
//
//    }
//
//    public static void main(String[] args) throws InterruptedException {
//        int port=8080;
//        if (args !=null && args.length>0){
//            try {
//                port=Integer.valueOf(args[0]);
//            }catch (Exception e){
//
//            }
//            new TimeServer().bind(port);
//        }
//    }
//
//}
