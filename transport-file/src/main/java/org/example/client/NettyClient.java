package org.example.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.example.domain.FileTransferProtocol;
import org.example.util.MsgUtil;

import java.io.File;

public class NettyClient {

    public static void main(String[] args) {
        //启动客户端
        ChannelFuture channelFuture = new NettyClient().connect("127.0.0.1", 7397);

        //文件信息
        File file = new File("C:\\Users\\zhouj2\\Downloads\\aclImdb_v1\\train\\unsup.rar");
        FileTransferProtocol fileTransferProtocol = MsgUtil.buildRequestTransferFile(file.getAbsolutePath(), file.getName(), file.length());

        //发送信息；请求传输文件
        channelFuture.channel().writeAndFlush(fileTransferProtocol);
    }

    //配置服务端NIO线程组
    private EventLoopGroup workerGroup = new NioEventLoopGroup();
    private Channel channel;

    public ChannelFuture connect(String inetHost, int inetPort) {
        ChannelFuture channelFuture = null;
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.AUTO_READ, true);
            b.handler(new MyChannelInitializer());
            channelFuture = b.connect(inetHost, inetPort).syncUninterruptibly();
            this.channel = channelFuture.channel();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != channelFuture && channelFuture.isSuccess()) {
                System.out.println("client start done.");
            } else {
                System.out.println("client start error. ");
            }
        }
        return channelFuture;
    }

    public void destroy() {
        if (null == channel) return;
        channel.close();
        workerGroup.shutdownGracefully();
    }

}