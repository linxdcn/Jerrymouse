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

    public BioHttpServer() {
        setting = new ServerSetting();
    }

    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(setting.port);

            logger.info("Web server listening on port " + setting.port + " (press CTRL-C to quit)");

            ExecutorService executor = Executors.newFixedThreadPool(setting.maxThread);

            while (true) {
                executor.submit(new BioRequestHandler(ss.accept()));
            }
        }
        catch (IOException e) {
            logger.error("Startup Error", e);
        }

    }
}
