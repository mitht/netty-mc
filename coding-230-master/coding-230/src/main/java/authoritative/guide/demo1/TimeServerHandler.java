//package authoritative.guide.demo1;
//
//
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.ChannelHandlerAdapter;
//import io.netty.channel.ChannelHandlerContext;
//
//import java.util.Date;
//
///**
// * @Author: rht
// * @Despriction:
// * @Date:Created in  19-8-15 下午4:06
// * @Mail: wx-renht@dtdream.com
// */
//public class TimeServerHandler extends ChannelHandlerAdapter {
//
//
//
//      @Override
//      public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//
//            ByteBuf  buf = (ByteBuf) msg;
//            //获取缓冲区可读的字节数,根据可读的字节数创建byte数组
//            byte[] req = new byte[buf.readableBytes()];
//            //将缓冲区中的字节数组复制到新建的byte数组中
//            buf.readBytes(req);
//            //获取请求信息
//            String body = new String(req, "UTF-8");
//            System.out.println("the time server receive order : "+body);
//            String currentTime="QUERY TIME ORDER".equalsIgnoreCase(body)?new Date(System.currentTimeMillis()).toString():"BAD ORDER";
//
//            ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
//            //异步发送应答信息给客户端,write 方法并不直接将消息写入socketChannel中,只是把息发送到缓冲数组中,调用flush方法,
//            // 将发送缓冲区中的消息全部写到socketChannel中
//            ctx.write(resp);
//      }
//
//      @Override
//      public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//            //(消息发送队列)中的消息写入到socketChannel中发送给对方
//            ctx.flush();
//      }
//
//      @Override
//      public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//            ctx.close();
//      }
//}
