package cn.linxdcn.server;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by linxiaodong on 4/30/17.
 */
public class BioHttpServer implements Runnable {

    private static Logger logger = Logger.getLogger(BioHttpServer.class);

    ServerSetting setting;

    ServerSocket ss;

    public BioHttpServer(ServerSetting setting) throws IOException {
        this.setting = setting;

        ss = new ServerSocket(setting.port);
    }

    @Override
    public void run() {

        logger.info("Web server listening on port " + setting.port + " (press CTRL-C to quit)");

        ExecutorService executor = Executors.newFixedThreadPool(setting.maxThread);

        try {
            while (!Thread.interrupted()) {
                executor.submit(new BioRequestHandler(ss.accept()));
            }
        }
        catch (IOException e) {
            logger.error("Runtime error", e);
        }

    }
}
