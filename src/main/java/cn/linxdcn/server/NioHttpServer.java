package cn.linxdcn.server;

import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by linxiaodong on 4/30/17.
 */
public class NioHttpServer implements Runnable {

    private static Logger logger = Logger.getLogger(NioHttpServer.class);

    private ServerSetting setting;

    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);

    public NioHttpServer(ServerSetting setting) throws IOException {
        this.setting = setting;

        selector = Selector.open();

        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(setting.port));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    @Override
    public void run() {
        logger.info("Web nio server listening on port " + setting.port + " (press CTRL-C to quit)");

        SelectionKey key = null;
        while (true) {
            try {
                selector.select();
                Iterator<SelectionKey> selectedKeys = selector.selectedKeys()
                        .iterator();
                while (selectedKeys.hasNext()) {
                    key = selectedKeys.next();
                    selectedKeys.remove();
                    if (!key.isValid()) {
                        continue;
                    }
                    if (key.isAcceptable()) {
                        accept(key);
                    } else if (key.isReadable()) {
                        read(key);
                    } else if (key.isWritable()) {
                        write(key);
                    }
                }
            } catch (Exception e) {
                if (key != null) {
                    key.cancel();
                    try {
                        key.channel().close();
                    }
                    catch (IOException ioe) {
                        logger.info("closed by exception" + key.channel(), ioe);
                    }

                }
                logger.error("closed" + key.channel(), e);
            }
        }
    }

    private void accept(SelectionKey key) throws IOException {
        SocketChannel socketChannel = serverSocketChannel.accept();
        logger.info("new connection:\t" + socketChannel);
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();

        // read the requestHeader
        byte[] bytes = null;
        int size = 0;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        while ((size = socketChannel.read(readBuffer)) > 0) {
            readBuffer.flip();
            bytes = new byte[size];
            readBuffer.get(bytes);
            baos.write(bytes);
            readBuffer.clear();
        }
        bytes = baos.toByteArray();
        String requestHeader = new String(bytes);
        if (requestHeader.length() > 0)
            new Thread(new NioRequestHandler(requestHeader, key)).start();
    }

    private void write(SelectionKey key) throws IOException{
        SocketChannel socketChannel = (SocketChannel) key.channel();
        key.cancel();
        socketChannel.close();
    }
}
