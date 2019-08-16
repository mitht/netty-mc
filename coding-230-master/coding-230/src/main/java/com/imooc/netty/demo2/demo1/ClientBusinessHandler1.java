package com.imooc.netty.demo2.demo1;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: rht
 * @Despriction:
 * @Date:Created in  19-8-12 上午11:30
 * @Mail: wx-renht@dtdream.com
 */
@ChannelHandler.Sharable
public class ClientBusinessHandler1 extends SimpleChannelInboundHandler<ByteBuf> {

    public static final ChannelHandler INSTANCE=new ClientBusinessHandler1();
    private  static AtomicLong beginTime = new AtomicLong(0);
    private  static AtomicLong totalResponseTime = new AtomicLong(0);
    private  static AtomicLong totalRequest = new AtomicLong(0);

    private static final  Thread THREAD = new Thread(() -> {
        try {
            while (true){
                //持续时间,当前时间减去开始时间,等于持续时间
                long duration = System.currentTimeMillis() - beginTime.get();
                if (duration !=0){
//                        qps: 每秒查询率
                    System.out.println("qps: " +1000*totalRequest.get()/duration+" , " +
                            "avg response time :  " + ((float)totalResponseTime.get())/totalRequest.get());
                    Thread.sleep(2000);
                }
            }
        }catch (Exception e){

        }
    });

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        period 一段时间
        ctx.executor().scheduleAtFixedRate(()->{
            ByteBuf byteBuf = ctx.alloc().ioBuffer();
            byteBuf.writeLong(System.currentTimeMillis());
            ctx.channel().writeAndFlush(byteBuf);
        },0,1,TimeUnit.SECONDS);



        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        totalResponseTime.addAndGet(System.currentTimeMillis()-msg.readLong());
        totalRequest.incrementAndGet();
        if (beginTime.compareAndSet(0,System.currentTimeMillis())){
            THREAD.start();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
    }
}
