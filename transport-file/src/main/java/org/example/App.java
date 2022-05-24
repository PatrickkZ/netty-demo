package org.example;

import io.netty.channel.ChannelFuture;
import org.example.client.NettyClient;
import org.example.domain.FileTransferProtocol;
import org.example.server.NettyServer;
import org.example.util.MsgUtil;

import java.io.File;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // start server
        new NettyServer().bing(7397);

        // start client
        ChannelFuture channelFuture = new NettyClient().connect("127.0.0.1", 7397);

        //file info
        File file = new File("C:\\Users\\zhouj2\\Desktop\\example_data.rar");
        FileTransferProtocol fileTransferProtocol = MsgUtil.buildRequestTransferFile(file.getAbsolutePath(), file.getName(), file.length());

        //发送信息；请求传输文件
        channelFuture.channel().writeAndFlush(fileTransferProtocol);
    }
}
