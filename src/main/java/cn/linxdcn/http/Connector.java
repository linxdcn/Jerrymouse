package cn.linxdcn.http;

import org.apache.log4j.Logger;

import java.nio.channels.SelectionKey;

/**
 * Created by linxiaodong on 5/5/17.
 */
public class Connector implements Runnable {

    private static Logger logger = Logger.getLogger(HttpRequest.class);

    private Context context;

    private String requestHeader;

    private Handler handler;

    public Connector(String requestHeader) {
        this.requestHeader = requestHeader;

        context = new HttpContext();
        context.setContext(requestHeader);

        handler = new HttpHandler();
    }

    @Override
    public void run() {
        handler.init(context);
    }

    public Context getContext() {
        return context;
    }
}
