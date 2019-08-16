package com.imooc.netty.demo2.chart;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author: rht
 * @Despriction:
 * @Date:Created in  19-8-14 下午3:44
 * @Mail: wx-renht@dtdream.com
 */
//https://www.cnblogs.com/damowang/p/6226167.html



/*
1.NioEventLoopGroup是用来处理I/O操作的多线程事件循环器，
Netty 提供了许多不同的EventLoopGroup的实现用来处理不同的传输。
在这个例子中我们实现了一个服务端的应用，因此会有2个 NioEventLoopGroup 会被使用。第一个经常被叫做‘boss’，
用来接收进来的连接。第二个经常被叫做‘worker’，用来处理已经被接收的连接，一旦‘boss’接收到连接，就会把连接信息注册到‘worker’上。
如何知道多少个线程已经被使用，如何映射到已经创建的 Channel上都需要依赖于 EventLoopGroup 的实现，并且可以通过构造函数来配置他们的关系。

2.ServerBootstrap是一个启动 NIO 服务的辅助启动类。你可以在这个服务中直接使用 Channel，
但是这会是一个复杂的处理过程，在很多情况下你并不需要这样做。

3.这里我们指定使用NioServerSocketChannel类来举例说明一个新的 Channel 如何接收进来的连接。

4.这里的事件处理类经常会被用来处理一个最近的已经接收的 Channel。
SimpleChatServerInitializer 继承自ChannelInitializer是一个特殊的处理类，
他的目的是帮助使用者配置一个新的 Channel。也许你想通过增加一些处理类比如 SimpleChatServerHandler 来配置一个新的 Channel
或者其对应的ChannelPipeline来实现你的网络程序。当你的程序变的复杂时，可能你会增加更多的处理类到 pipline 上，然后提取这些匿名类到最顶层的类上。

5.你可以设置这里指定的 Channel 实现的配置参数。我们正在写一个TCP/IP 的服务端，
因此我们被允许设置 socket 的参数选项比如tcpNoDelay 和 keepAlive。
请参考ChannelOption和详细的ChannelConfig实现的接口文档以此可以对ChannelOption 的有一个大概的认识。

6.option() 是提供给NioServerSocketChannel用来接收进来的连接。childOption() 是提供给由父管道ServerChannel接收到的连接，
在这个例子中也是 NioServerSocketChannel。

7.我们继续，剩下的就是绑定端口然后启动服务。这里我们在机器上绑定了机器所有网卡上的 8080 端口。
当然现在你可以多次调用 bind() 方法(基于不同绑定地址)。

恭喜！你已经完成了基于 Netty 聊天服务端程序。
* */
public class SimpleChartServer {

    private int port;


    public SimpleChartServer(int port) {
        this.port = port;
    }
    
    public void run() {
        //ctrl + alt + f 更改 自动补全的名称
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new SimpleChatServerInitializer())
                    //BACKLOG用于构造服务端套接字ServerSocket对象，
                    // 标识当服务器请求处理线程全满时，
                    // 用于临时存放已完成三次握手的请求的队列的最大长度。
                    // 如果未设置或所设置的值小于1，Java将使用默认值50。
                    .option(ChannelOption.SO_BACKLOG, 128)

                    /*
                    当设置为true的时候，TCP会实现监控连接是否有效，当连接处于空闲状态的时候，
                    超过了2个小时，本地的TCP实现会发送一个数据包给远程的 socket，
                    如果远程没有发回响应，TCP会持续尝试11分钟，
                    知道响应为止，如果在12分钟的时候还没响应，TCP尝试关闭socket连接。
                    * */
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            System.out.println("SimpleChartServer 启动了");

            ChannelFuture sync = bootstrap.bind(port).sync();
            // 等待服务器  socket 关闭 。
            // 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
//            sync.channel().closeFuture().sync();


        }catch (Exception e){

        }finally {
//            workGroup.shutdownGracefully();
//            bossGroup.shutdownGracefully();
//
//            System.out.println("SimpleChartServer 关闭了");
        }

    }

    public static void main(String[] args) {
        int port;
        if (args.length>0){
            port=Integer.parseInt(args[0]);
        }else {
            port=8080;
        }

        new SimpleChartServer(port).run();

    }
}
