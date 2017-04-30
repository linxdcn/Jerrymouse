package cn.linxdcn;

import cn.linxdcn.server.BioHttpServer;
import cn.linxdcn.server.BioRequestHandler;
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

    public static void main(String[] args) {
        new Thread(new BioHttpServer()).start();
    }
}
