package cn.linxdcn.server;

/**
 * Created by linxiaodong on 4/30/17.
 */
public class ServerSetting {

    private static final int DEFAULT_PORT = 8080;

    private static final String DEFAULT_ROOT = "webapps";

    private static final int MAX_THREAD = 3;

    int port;

    String root;

    int maxThread;

    public ServerSetting() {
        port = DEFAULT_PORT;
        root = DEFAULT_ROOT;
        maxThread = MAX_THREAD;
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
}
