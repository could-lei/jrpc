package protocol.Netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.List;

public class ChannelInitializer extends io.netty.channel.ChannelInitializer<SocketChannel> {
    private static final String delimiterStr = "$_-$";
    private static final int maxFrameLen = 1024 * 1024;//1M
    private static final ByteBuf delimiter = ByteBufAllocator.DEFAULT.directBuffer(4).writeBytes(delimiterStr.getBytes());
    private String peer;
    public ChannelInitializer(String peer){
        this.peer=peer;
    }
    private void check(ChannelHandlerContext ctx, String msg) throws Exception {
        if (msg.length() > maxFrameLen) {
            exceptionCaught(ctx, new TooLongFrameException("发送消息内容过长"));
        }
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new DelimiterBasedFrameDecoder(maxFrameLen, delimiter));
        pipeline.addLast(new StringEncoder());
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new ChannelDuplexHandler() {
            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                StringBuilder res = new StringBuilder();
                if (msg instanceof String) {
                    String str = (String) msg;
                    check(ctx, str);
                    res.append(str).append(delimiterStr);
                } else if (msg instanceof List && ((List) msg).get(0) instanceof String) {
                    for (String str : (List<String>) msg) {
                        check(ctx, str);
                        res.append(str).append(delimiterStr);
                    }
                } else {
                    exceptionCaught(ctx, new Error("当前只允许发送String和List<String>类型"));
                }
                ctx.write(res);
                ctx.flush();
            }

            @Override
            public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                super.channelReadComplete(ctx);
            }

            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                if (peer == "客户端") {
                    ctx.pipeline().write("已成功处理消息");
                    ctx.flush();
                }
            }
        });
    }
}
