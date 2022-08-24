package protocol.Netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandler extends SimpleChannelInboundHandler<Object> {
    /**
     * 监听端口读取的数据
     * @param channelHandlerContext
     * @param o
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        ByteBuf buf= (ByteBuf) o;
        byte[]byteArray=new byte[buf.readableBytes()];
        buf.getBytes(0,byteArray);

        String str=new String(byteArray);
        if (str.length()>0){
            System.out.println(str);
            System.out.flush();
        }
    }

    /**
     * 数据读取后的后置处理
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务端接受数据完毕");
    }

    /**
     * 客户端关闭连接
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().localAddress().toString());
        ctx.close();
    }

}
