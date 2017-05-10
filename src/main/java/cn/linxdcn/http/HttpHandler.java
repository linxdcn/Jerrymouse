package cn.linxdcn.http;

import cn.linxdcn.Bootstrap;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by linxiaodong on 5/5/17.
 */
public class HttpHandler extends AbstractHandler {

    private static Logger logger = Logger.getLogger(HttpHandler.class);
    @Override
    public void doGet(Context context) {

        Request req = context.getRequest();
        Response res = context.getResponse();

        try {
            List<String> headers = res.getHeaders();
            fillHeaders(Status._200, res);
            File file = new File("." + "/" + Bootstrap.setting.getRoot() + req.getUri());
            if (file.isDirectory()) {
                headers.add(ContentType.HTML.toString());
                StringBuilder result = new StringBuilder("<html><head><title>Index of ");
                result.append(req.getUri());
                result.append("</title></head><body><h1>Index of ");
                result.append(req.getUri());
                result.append("</h1><hr><pre>");

                File[] files = file.listFiles();
                for (File subfile : files) {
                    String path = subfile.getPath().replaceAll(
                            "./" + Bootstrap.setting.getRoot(), "");
                    result.append(" <a href=\"" + path + "\">" + path + "</a>\n");
                }
                result.append("<hr></pre></body></html>");
                fillResponse(result.toString(), res);
            } else if (file.exists()) {
                setContentType(req.getUri(), headers);
                fillResponse(getBytes(file), res);
            } else {
                logger.info("File not found:" + req.getUri());
                fillHeaders(Status._404, res);
                fillResponse(Status._404.toString(), res);
            }
        } catch (Exception e) {
            logger.error("Response Error", e);
            fillHeaders(Status._400, res);
            fillResponse(Status._400.toString(), res);
        }
    }

    private void fillHeaders(Status status, Response res) {
        res.getHeaders().add("HTTP/1.0" + " " + status.toString());
        res.getHeaders().add("Connection: close");
        res.getHeaders().add("Server: jerrymouse");
    }

    private void fillResponse(String response, Response res) {
        res.setBody(response.getBytes());
    }

    private void fillResponse(byte[] response, Response res) {
        res.setBody(response);
    }

    private void setContentType(String uri, List<String> headers) {
        try {
            String ext = uri.substring(uri.indexOf(".") + 1);
            headers.add(ContentType.valueOf(ext.toUpperCase()).toString());
        } catch (Exception e) {
            logger.error("ContentType not found: " + e, e);
        }
    }

    private byte[] getBytes(File file) throws IOException {
        int length = (int) file.length();
        byte[] array = new byte[length];
        InputStream in = new FileInputStream(file);
        int offset = 0;
        while (offset < length) {
            int count = in.read(array, offset, (length - offset));
            offset += count;
        }
        in.close();
        return array;
    }
}
