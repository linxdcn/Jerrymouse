package cn.linxdcn.server;

import cn.linxdcn.http.Connector;
import cn.linxdcn.http.HttpRequest;
import cn.linxdcn.http.HttpResponse;
import cn.linxdcn.http.Response;
import org.apache.log4j.Logger;

import java.io.*;
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
            InputStream is = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            StringBuilder sb = new StringBuilder();
            String str = reader.readLine();
            sb.append(str);

            while (!str.equals("")) {
                str = reader.readLine();
                sb.append(str + "\r\n");
            }

            String requestHeader = sb.toString();

            Connector connector = new Connector(requestHeader);

            connector.run();

            Response res = connector.getContext().getResponse();

            OutputStream os = socket.getOutputStream();
            DataOutputStream output = new DataOutputStream(os);
            output.writeBytes(res.toString());

            socket.close();
        } catch (Exception e) {
            log.error("Runtime Error", e);
        }
    }
}
