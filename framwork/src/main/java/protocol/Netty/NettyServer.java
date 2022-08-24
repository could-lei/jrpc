package protocol.Netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    public static void start()  {
        NioEventLoopGroup boss=new NioEventLoopGroup(1);
        NioEventLoopGroup worker=new NioEventLoopGroup();
        try {
            new ServerBootstrap()
                    .group(boss,worker)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .channel(NioServerSocketChannel.class)
                    .handler(new ServerChannelDuplexHandler())
                    .childHandler(new ChannelInitializer("khd"))
                    .bind(8080)
                    .sync()
                    .addListener(future -> System.out.println("服务启动成功"))
                    .channel()
                    .closeFuture()
                    .sync()
                    .addListener(future -> System.out.println("端口关闭"));
        } catch (InterruptedException e) {
            e.printStackTrace();
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
