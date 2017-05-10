package cn.linxdcn;

import cn.linxdcn.server.BioHttpServer;
import cn.linxdcn.server.BioRequestHandler;
import cn.linxdcn.server.NioHttpServer;
import cn.linxdcn.server.ServerSetting;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by linxiaodong on 4/29/17.
 */
public class Bootstrap extends Thread {

    private static Logger logger = Logger.getLogger(Bootstrap.class);

    public static ServerSetting setting;

    public static void main(String[] args) {

        setting = new ServerSetting();

        try {
            if (setting.getServer().equals("bio"))
                new Thread(new BioHttpServer(setting)).start();
            else
                new Thread(new NioHttpServer(setting)).start();
        }
        catch (IOException e) {
            logger.error("Startup error", e);
        }
    }
}
