package cn.linxdcn.server;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.Set;

/**
 * Created by linxiaodong on 4/30/17.
 */
public class ServerSetting {

    private static Logger logger = Logger.getLogger(ServerSetting.class);

    private static final int DEFAULT_PORT = 8080;

    private static final String DEFAULT_ROOT = "webapps";

    private static final int MAX_THREAD = 3;

    int port;

    String root;

    int maxThread;

    String server;

    public ServerSetting() {
        Properties pro = null;
        try {
            pro = new Properties();
            pro.load(ServerSetting.class.getResourceAsStream("/config.properties"));
        } catch (IOException e) {
            logger.info("Config file error, using default value", e);
        }

        port = DEFAULT_PORT;
        root = DEFAULT_ROOT;
        maxThread = MAX_THREAD;

        if (pro != null) {
            Set<String> names = pro.stringPropertyNames();
            if (names.contains("port")) {
                port = Integer.valueOf(pro.getProperty("port"));
            }
            if (names.contains("root")) {
                root = pro.getProperty("root");
            }
            if (names.contains("maxThread")) {
                maxThread = Integer.valueOf(pro.getProperty("maxThread"));
            }

            server = pro.getProperty("server", "bio");
        }
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public void setMaxThread(int maxThread) {
        this.maxThread = maxThread;
    }

    public int getPort() {
        return port;
    }

    public String getRoot() {
        return root;
    }

    public int getMaxThread() {
        return maxThread;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
