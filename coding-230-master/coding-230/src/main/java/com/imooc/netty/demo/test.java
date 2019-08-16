package com.imooc.netty.demo;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: rht
 * @Despriction:
 * @Date:Created in  19-8-8 下午1:14
 * @Mail: wx-renht@dtdream.com
 */
public class test {

    public static void main(String[] args) throws Exception {
//
//        NettyOioServer nettyOioServer = new NettyOioServer();
//        nettyOioServer.server(9999);
//        Thread.sleep(55555555);
//        System.out.println("--------------------------");

       AtomicLong beginTime = new AtomicLong(0);
        long l = beginTime.addAndGet(1);

        System.out.println(l);
        boolean b = beginTime.compareAndSet(1, System.currentTimeMillis());
        System.out.println(b);
        System.out.println(beginTime);
    }

}
