package cn.linxdcn.server;

import cn.linxdcn.http.Connector;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Created by linxiaodong on 4/30/17.
 */
public class NioRequestHandler implements Runnable {

    private static Logger logger = Logger.getLogger(NioRequestHandler.class);

    private SelectionKey key;

    private String requestHeader;

    @Override
    public void run() {
        System.out.print("new connector");
        Connector connector = new Connector(requestHeader);
        connector.run();

        ByteBuffer buffer = ByteBuffer.allocate(1024 * 10);
        buffer.put(connector.getContext().getResponse().toString().getBytes());
        buffer.flip();
        try {
            Selector selector = key.selector();
            SocketChannel channel = (SocketChannel)key.channel();
            channel.register(selector, SelectionKey.OP_WRITE);
            selector.wakeup();
            channel.write(buffer);
        } catch (IOException e) {
            logger.error("Output response error", e);
        }
    }

    public NioRequestHandler(String requestHeader, SelectionKey key) {
        this.requestHeader = requestHeader;
        this.key = key;
    }
}
