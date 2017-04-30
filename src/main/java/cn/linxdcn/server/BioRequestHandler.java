package cn.linxdcn.server;

import cn.linxdcn.http.HttpRequest;
import cn.linxdcn.http.HttpResponse;
import org.apache.log4j.Logger;

import java.net.Socket;

/**
 * Created by linxiaodong on 4/29/17.
 */
public class BioRequestHandler implements Runnable{
    private static Logger log = Logger.getLogger(BioRequestHandler.class);

    private Socket socket;

    public BioRequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            HttpRequest req = new HttpRequest(socket.getInputStream());
            HttpResponse res = new HttpResponse(req);
            res.write(socket.getOutputStream());
            socket.close();
        } catch (Exception e) {
            log.error("Runtime Error", e);
        }
    }
}
