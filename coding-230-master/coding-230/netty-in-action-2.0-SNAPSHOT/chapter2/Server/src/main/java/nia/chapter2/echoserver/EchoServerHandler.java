package nia.chapter2.echoserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Listing 2.1 EchoServerHandler
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
//标示一个channel-Handler可以被多个channel安全的共享
@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    //对于每个传入的消息都要调用
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        //将消息记录到控制台
        System.out.println(
                "Server received: " + in.toString(CharsetUtil.UTF_8));
        //将接受到消息写给发送者,而不冲刷出站消息
        ctx.write(in);
    }
    //最后一次对channel-read的调用是当前批量读取中的最后一条消息
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx)
            throws Exception {
        //Pooled和Unpooled：pooled类型的bytebuf是在已经申请好的内存块取一块内存，而Unpooled是直接通过JDK底层代码申请
        //将未决消息冲刷到远程节点,并且关闭该 Channel
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }
    //异常调用
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
        Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
